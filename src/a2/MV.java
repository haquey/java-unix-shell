package a2;

public class MV implements CommandInterface {
  // Injected JFileSystem
  private JFileSystem insertedFileSystem;
  // User submitted arguments
  private String[] classPaths;
  // String returned by execute method to send to output
  private String stringToOutput = "";

  /**
   * Constructs a MV object which takes a JFileSystem for it to manipulate. Also
   * takes in an oldpath and newpath as parameters, that are used in the execute
   * method.
   * 
   * @param jFileSystem - FileSystem that the commands will act upon.
   * @param classPaths - Used to move items between specified paths
   */
  public MV(JFileSystem insertedFileSystem, String[] classPaths) {
    // Contains the jFileSystem to be manipulated
    this.insertedFileSystem = insertedFileSystem;
    // Contains paths of items to be moved around
    this.classPaths = classPaths;
  }

  /**
   * Takes the item at oldpath and moves it to the newpath destination. Moving
   * between pre-existing files causes the destination file to be overwritten.
   * Moving between pre-existing folders/directories causes the destination
   * directory to be the new parent of the first. Files/folders can also be
   * renamed when specifying new destination paths.
   * 
   * @param jFileSystem - FileSystem that the commands will act upon.
   * @param classPaths - Used to move items between specified paths
   * @return stringToOutput - Used by ProQuery for printing/file redirection
   */
  public String execute() {
    try {
      // Check for whether specified destination item is a child of the current
      // item. Also check to see if the initial paths exist within the file
      // system
      if (classPaths.length == 2 && checkPathExists(classPaths[0])
          && !isParent(classPaths[0], classPaths[1])) {

        // Get the fullpath for the existing path given
        String existingItemPath = insertedFileSystem.getFullPath(classPaths[0]);
        // Get the item at the existing path given
        Item existingItem = insertedFileSystem.getObject(existingItemPath);
        // Get the fullpath for the parent dir of the existing path given
        String parentPathExisting =
            existingItemPath.substring(0, existingItemPath.lastIndexOf("/"));
        // Get the item at the existing parent path
        Item parentExisting = insertedFileSystem.getObject(parentPathExisting);
        // Get the fullpath for the destination path given
        String specifiedItemPath =
            insertedFileSystem.getFullPath(classPaths[1]);
        // Get the fullpath for the parent of the destination path given
        String parentPathSpecified =
            specifiedItemPath.substring(0, specifiedItemPath.lastIndexOf("/"));

        // See if destination paths exist
        if (checkPathExists(classPaths[1])) {
          // Get the item at the destination path since it exists
          Item specifiedItem = insertedFileSystem.getObject(specifiedItemPath);
          // Get the item at the parent of the destination path since it exists
          Item parentSpecified =
              insertedFileSystem.getObject(parentPathSpecified);
          // If the destination path is a folder treat it as a parent
          if (specifiedItem instanceof Folder) {
            ((Folder) parentExisting).removeChildren(existingItem);
            setNewSpecifiedPath(existingItem, specifiedItemPath,
                existingItem.getPath());
            ((Folder) specifiedItem).addChildren(existingItem);

            // If the destination path and the current path are both files,
            // change the current's name to the destination and overwrite it
          } else if (existingItem instanceof File
              && specifiedItem instanceof File) {
            ((Folder) parentExisting).removeChildren(existingItem);
            setNewSpecifiedPath(existingItem, parentPathSpecified,
                existingItem.getPath());
            ((Folder) parentSpecified).removeChildren(specifiedItem);
            String fileName = specifiedItemPath.substring(
                specifiedItemPath.lastIndexOf("/") + 1,
                specifiedItemPath.length());
            existingItem.setName(fileName);
            ((Folder) parentSpecified).addChildren(existingItem);
            // If trying to place a Folder in a File
          } else {
            System.err.println("Cannot overwrite non-directory with directory");
          }

        } else {
          // If the destination path doesn't exist in the file system, rename
          // the current path from foldername to foldername or filename to
          // filename
          if ((isFile(specifiedItemPath) && existingItem instanceof File)
              | (!isFile(specifiedItemPath)
                  && existingItem instanceof Folder)) {
            // Acquire the filname of the destination path
            String fileName = specifiedItemPath.substring(
                specifiedItemPath.lastIndexOf("/") + 1,
                specifiedItemPath.length());
            // Appropriately rename the folder and change filepaths
            moveFileToNonExistingItemPath(fileName, parentPathSpecified,
                existingItem, parentExisting);

            // Print error message if trying to move folder to file
          } else {
            System.err.println("Cannot overwrite non-directory with directory");
          }
        }
        // Print error if current path is parent of destination
      } else if (isParent(classPaths[0], classPaths[1])) {
        System.err.println("Cannot move parent directory into a subdirectory");
        // Print error if invalid path parameters givn
      } else {
        System.err.println("Invalid file path given");
      }


    } catch (

    InvalidPath e) {
      System.err.println("Invalid file path given");
    }

    return stringToOutput;
  }


  /**
   * Checks to see if the given relative of full path exists within the
   * jFileSystem.
   * 
   * @param path - A given relative/full path
   * @return checkPath - Returns true or false depending on path existing
   */
  private boolean checkPathExists(String path) {
    // Initialize as false
    boolean checkPath = false;
    try {
      // If path exists, return true
      checkPath = insertedFileSystem
          .checkValidPath(insertedFileSystem.getFullPath(path));
    } catch (InvalidPath e) {
      e.printStackTrace();
    }
    return checkPath;
  }

  /**
   * Moves the given item to the specified destination path if the destination's
   * parent path exists.
   * 
   * @param movedItemName - Name of item at the destination path
   * @param specifiedParentPath - Parent path of the destination path
   * @param currItem - Item found at the existing path
   */
  private void moveFileToNonExistingItemPath(String movedItemName,
      String specifiedParentPath, Item currItem, Item parentCurrItem) {
    // Check to see if the parent path exists
    if (checkPathExists(specifiedParentPath)) {
      // Acquire parent object
      Item parentSpecified = insertedFileSystem.getObject(specifiedParentPath);
      // Remove currItem from its current parent
      ((Folder) parentCurrItem).removeChildren(currItem);
      // Change currItem's name to the destination file/folder
      currItem.setName(movedItemName);
      // Appropriately change its filepath and any children it might have
      setNewSpecifiedPath(currItem, specifiedParentPath, currItem.getPath());
      ((Folder) parentSpecified).addChildren(currItem);
      // Print error message if parent path does not exist
    } else {
      System.err.println("Invalid file path given");
    }
  }

  /**
   * Takes an Item and assigns it a new specified path and all of its possible
   * children a corresponding path while deleting its old paths from the
   * jFileSystem.
   * 
   * @param newItem - Item whose current path is subject to change
   * @param newItemParentPath - Parent path of the destination path for newItem
   * @param existingPath - newItem's current path before changing
   */
  private void setNewSpecifiedPath(Item newItem, String newItemParentPath,
      String existingPath) {
    // Remove the existing path from the jFileSystem
    insertedFileSystem.removePaths(existingPath);
    // Create the new destination path by taking the destination's parent
    // and adding the destination's filename/folder at the end
    String newPath = newItemParentPath + "/" + newItem.getName();
    // Set the item's new path and load the new path into the jFileSystem
    newItem.setPath(newPath);
    insertedFileSystem.addFullPath(newPath);
    // Recursively apply the same to all possible children
    if (newItem instanceof Folder && !newItem.getChildren().isEmpty()) {
      for (Item child : newItem.getChildren()) {
        setNewSpecifiedPath(child, newPath, child.getPath());
      }
    }
  }

  /**
   * Determines if a specified path contains a file object.
   * 
   * @param filePath - Destination path where the item resides
   * @return isFile - Returns whether or not the path points to a file object
   */
  private boolean isFile(String filePath) {
    // Initialize as false
    boolean isFile = false;
    String fileName =
        filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
    // If the item name at the end contains a file extension, it must be a file
    if (fileName.contains(".")) {
      isFile = true;
    }
    return isFile;
  }

  /**
   * Determines if an oldPath is the parent of a specified newPath.
   * 
   * @param oldPath - Path where the item currently resides
   * @param newPath - Destination path for the item
   * @return isParent - Returns whether or not the oldPath is a parent directory
   */
  private boolean isParent(String oldPath, String newPath) {
    boolean isParent = false;
    try {
      // Acquire full paths if given relative paths
      String oldFullPath = insertedFileSystem.getFullPath(oldPath);
      String newFullPath = insertedFileSystem.getFullPath(newPath);
      // oldPath is a parent
      isParent = newFullPath.startsWith(oldFullPath);
    } catch (InvalidPath e) {
      System.err.println("Invalid path given");
    }
    return isParent;
  }

  /**
   * The purpose of this method is to return a string containing the
   * documentation/information regarding the "mv" command.
   * 
   * @return String
   */
  public String manual() {
    return "mv OLDPATH NEWPATH - Moves an item from a specified oldpath to\n"
        + "newpath. The paths may be relative or absolute. If OLDPATH and\n"
        + "NEWPATH are files, OLDPATH's file will overwrite and take the name\n"
        + "of NEWPATH's. If NEWPATH is a directory, the item in OLDPATH will\n"
        + "be moved to the directory. Items at OLDPATH can be renamed using\n"
        + "mv if the item at NEWPATH doesn't exist, but its parent directory\n"
        + "does.";
  }

}
