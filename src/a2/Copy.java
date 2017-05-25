package a2;

import java.util.Vector;
// If newPath does not exist, then it must be created
import a2.Mkdir;

public class Copy implements CommandInterface {
  // The main file system
  private JFileSystem insertedFileSystem;
  // The string array containing oldPath and newPath
  private String[] classPaths;
  // An mkdir instance so new directories can be created
  private Mkdir mkdir;
  // the specified "newPath"
  private String newPath;
  // the specified "oldtPath"; ie: what is going to be copied
  private String oldPath;
  // A general string representing an absolute path
  private String Path;
  // Used in the recursive function to ensure the original parent directory
  // isn't
  // copied
  private boolean firstPass = true;
  // The item representing the oldPath
  private Item oldPathType;
  // The item representing the newPath
  private Item newPathType;
  // a boolean representing is oldPath is valid or not
  private boolean oldPathValid = true;
  // Only 2 paths are allowed as parameters
  final int NUM_OF_ALLOWED_PATHS = 2;
  // A string containing error messages;
  private String stringToOutputTest;

  /**
   * The constructor; connects Copy to the jFileSystem and adds classPaths
   * 
   * @param insertedFileSystem - The main jFilesystem
   * @param classPaths - oldPath and newPath
   */
  public Copy(JFileSystem insertedFileSystem, String[] classPaths) {
    // Injecting the jFileSystem dependency
    this.insertedFileSystem = insertedFileSystem;
    this.classPaths = classPaths;
  }

  /**
   * Used to get the output of the command
   * 
   * @return stringToOutput
   */
  public String getStringToOutput() {
    return this.stringToOutputTest;
  }

  /**
   * The main function where the command will be executed; If 2 directories are
   * given (and if they exist) the contents of oldPath will be recursively
   * COPIED (ie: new items) into the specified newPath Folder. If newPath does
   * not exisit it will be created using mkdir If a File and a Folder are
   * specified then a new copy of File will be created in the Folder. If a File
   * and File are specified then the contents of the file at oldPath will be
   * copied into the newPath file. If newPath does not exist (and is denoted
   * with the .txt extension) is will be created If a Folder and a File are
   * specified then an error will be raised
   * 
   * @return returns an empty string
   */
  public String execute() {
    // ensuring only 2 paths are given
    if (classPaths.length == this.NUM_OF_ALLOWED_PATHS) {
      // Turing the oldPath into an absolute path
      try {
        oldPath = this.insertedFileSystem.getFullPath(classPaths[0]);
      } catch (InvalidPath e) {
        System.err.println(e);
        this.stringToOutputTest = e.getMessage();
      }
      // checking if oldPath is valid or not (since an error will never be
      // thrown
      // if getFullPath is called from Copy)
      if (!this.insertedFileSystem.checkValidPath(oldPath)) {
        System.err.println(classPaths[0] + " is not a valid path");
        this.oldPathValid = false;
      }

      // Turning the newPath into an absolute path; this case is a little
      // different since just b/c the item doesn't exist doesn't mean an error
      // should be raised just yet
      try {
        newPath = this.insertedFileSystem.getFullPath(classPaths[1]);
      } catch (InvalidPath e) {
        System.err.println(e);
        this.stringToOutputTest = e.getMessage();
      }
      // checking if newPath already exisits or not
      if (!this.insertedFileSystem.checkValidPath(this.newPath)) {
        // If the user specified a Folder, new Folder is created
        if (!classPaths[1].endsWith(".txt")) {
          String[] toCreate = new String[] {classPaths[1]};
          // creating the mkdir instance
          mkdir = new Mkdir(this.insertedFileSystem, toCreate);
          mkdir.execute();

        }
        // creating a new Empty file
        else {
          // since newPath is an absolute path now
          this.Path = this.newPath;
          File cpFile =
              new File(this.newPath.substring(this.newPath.lastIndexOf("/")));
          cpFile.setBody("");
          cpFile.setPath(this.Path);
          String parentPath =
              this.oldPath.substring(0, this.oldPath.lastIndexOf("/"));
          // saving the File path into the file system
          this.insertedFileSystem.addFullPath(this.Path);
          // Adding it do the parent if it exists
          if (this.insertedFileSystem.checkValidPath(parentPath)) {
            ((Folder) this.insertedFileSystem.getObject(parentPath))
                .addChildren(cpFile);
          } else {
            System.err.println("Invalid File path given");
            this.stringToOutputTest = "Invalid FilePath given";
          }
        }
      }
      // Getting the objects associated with the paths
      this.oldPathType = this.insertedFileSystem.getObject(this.oldPath);
      this.newPathType = this.insertedFileSystem.getObject(this.newPath);
      // If the original oldPath is not valid then do nothing
      if (!this.oldPathValid) {
        return "";
      } else if (this.newPathType == null) {
        System.err.println("Invalid newpath given");
        this.stringToOutputTest = "Invalid newpath given";
      }
      // If a parent folder is trying to be copied into its child
      else if (this.newPath.startsWith(this.oldPath)) {
        System.err.println("Cannot copy a parent folder into it's child");
        this.stringToOutputTest = "Cannot copy a parent folder into it's child";
      }
      // The File File case
      else if (oldPathType.getClass().equals(File.class)
          && newPathType.getClass().equals(File.class)) {
        // Setting the body of newPath File to the same text as oldPath File
        ((File) newPathType).setBody((((File) oldPathType).getBody()));
      }
      // The File Folder case
      else if (oldPathType.getClass().equals(File.class)
          && newPathType.getClass().equals(Folder.class)) {
        // Creating a new File object to be added into the newPath Folder
        this.Path = this.newPath;
        File cpFile =
            new File(this.newPath.substring(this.newPath.lastIndexOf("/")));
        cpFile.setBody(
            ((File) this.insertedFileSystem.getObject(this.oldPath)).getBody());
        if (this.Path.equals("/")) {
          cpFile.setPath("/" + this.oldPathType.getName());
          this.insertedFileSystem.addFullPath("/" + this.oldPathType.getName());
        } else {
          cpFile.setPath(this.Path + "/" + this.oldPathType.getName());
          this.insertedFileSystem
              .addFullPath(this.Path + "/" + this.oldPathType.getName());
        }
        // adding the new File as a child
        String parentPath =
            this.oldPath.substring(0, this.oldPath.lastIndexOf("/"));
        ((Folder) this.insertedFileSystem.getObject(Path)).addChildren(cpFile);
      }
      // The Folder File case
      else if (this.oldPathType.getClass().equals(Folder.class)
          && this.newPathType.getClass().equals((File.class))) {
        System.err.println("Cannot copy a directory into a File");
        this.stringToOutputTest = "Cannot copy a directory into a File";
      }
      // The Folder Folder case
      else {
        this.recurseCopy(0, this.oldPathType);
      }
    } else {
      System.err.println("This is not a valid command");
      this.stringToOutputTest = "This is not a valid command";
    }

    return "";
  }

  /**
   * The recursive function and goes and copies every child directory from
   * oldPath into newPath
   * 
   * @param childIndex - represents which child is being copied right now
   * @param dirrOfFile - the object that is current being copied
   * 
   */
  public void recurseCopy(int childIndex, Item dirrOrFile) {
    // If the end of the subtree is reached
    if (dirrOrFile == null) {
      // do nothing; since calling getClass on a null would raise errors
    }
    // if dirrOrFile is a File
    else if (dirrOrFile.getClass().equals(File.class)) {
      // If the path of dirrOrFile don't match oldPath
      if (!dirrOrFile.getPath().equals(this.oldPath)) {
        // Creating the new File to be a copy of dirrOfFile
        if (!this.oldPath.equals("/")) {
          this.Path =
              this.newPath + "/" + dirrOrFile.getPath().split(this.oldPath)[1];
        } else {
          this.Path = "/" + dirrOrFile.getPath().split(this.oldPath)[1];
        }
        File cpFile = new File(dirrOrFile.getName());
        cpFile.setBody(((File) dirrOrFile).getBody());
        cpFile.setPath(this.Path);
        String parentPath =
            this.oldPath.substring(0, this.oldPath.lastIndexOf("/"));
        this.insertedFileSystem.addFullPath(this.Path);
        ((Folder) this.insertedFileSystem.getObject(parentPath))
            .addChildren(cpFile);
      }
    } else {
      if (!this.firstPass) {
        // If the path of dirrOrFile don't match oldPath
        if (!dirrOrFile.getPath().equals(this.oldPath)) {
          // Creating the new Folder which is a copy of dirrOrFile
          if (!this.newPath.equals("/")) {
            this.Path =
                this.newPath + dirrOrFile.getPath().split(this.oldPath)[1];
          } else {
            this.Path = "/" + dirrOrFile.getPath().split(this.oldPath)[1];
          }
          String[] arrayPath = new String[] {Path};
          this.mkdir = new Mkdir(this.insertedFileSystem, arrayPath);
          // If the object does not already exist
          if (!this.insertedFileSystem.checkValidPath(Path)) {
            mkdir.execute();
          }
        }
      }
      // getting the children of dirrOrFile
      Vector<Item> allChildren = dirrOrFile.getChildren();
      this.firstPass = false;
      // The recursive call that goes through all children
      if (allChildren != null && allChildren.size() != 0) {
        if (childIndex == 0) {
          this.recurseCopy(childIndex, allChildren.get(childIndex));
          this.recurseCopy(childIndex + 1, dirrOrFile);
        } else if (childIndex < allChildren.size()) {
          this.recurseCopy(0, allChildren.get(childIndex));
          this.recurseCopy(childIndex + 1, dirrOrFile);
        }
      }
    }

  }

  /**
   * The manual for this class; explaining in detail what Copy does
   * 
   * @return an explanation of how the cp command works
   */
  public String manual() {
    return "Take in 2 paths; the first one - oldPath - MUST exist "
        + "(unlike newPath). + If 2 directories are given (and if they exist)"
        + " the contents of oldPath will be recursively COPIED (ie: new items)"
        + " into the specified newPath Folder. If newPath does not exisit it "
        + "will be created using mkdir"
        + "If a File and a Folder are specified then a new copy of File will be"
        + " created in the Folder."
        + " If a File and File are specified then the contents of the file at"
        + " oldPath will be copied into the newPath file. If newPath does not"
        + " exist (and is denoted with the .txt extension) is will be created"
        + "If a Folder and a File are specified then an error will be raised";
  }

}
