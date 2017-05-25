package a2;

import java.util.Vector;
import java.util.*;

public class JFileSystem implements FileSystem {

  // A vector that holds all the full paths of each entity in JShell
  private Vector<String> fullPaths;
  // A string representing the current working directory
  private String currDir;
  // The current working folder
  private Folder currFolder;
  // The head of the tree which contains all Folders and Files
  private Folder rootFolder;
  // DirStack collaboration
  private DirStack dirStack;

  /**
   * The constructor
   */
  public JFileSystem() {
    // this.Manager = new Vector();
    this.fullPaths = new Vector();
    // Adding the two most basic paths
    this.fullPaths.addElement("/");
    // The root directory
    this.currDir = "/";
    // Creating a DirStack
    dirStack = new DirStack();
  }

  /**
   * Removes a specified path from the file System
   * 
   * @param path - the absolute path to be removed
   */

  public void removePaths(String path) {
    this.fullPaths.remove(path);
  }


  /**
   * This function return the path of the current working directory.To be used
   * with various command classes.
   * 
   * @return currDirr - The absolute path of the current working directory
   */
  public String getCurrPath() {
    return this.currDir;
  }

  /**
   * This method accepts an absolute path and sets that as the current working
   * directory. To only be used by CD
   * 
   * @param newDir - Represents the new absolute path for the new working
   *        directory
   */
  public void setFullPath(String newDir) {
    this.currDir = newDir;
  }

  /**
   * This function accepts an absolute path or a name of an object in the local
   * directory and returns the corresponding directory or File according the the
   * path. Traverses the Trees that exists at root. To be used with various
   * command classes.
   * 
   * @param name - A string representing an absolute path or a name of a local
   *        directory/file
   * @return - An object at the location of the specified absolute path
   */
  public Item getObject(String name) {
    // The object that is to be returned
    Item result = null;
    // If an absolute path is provided
    if (name.startsWith("/")) {
      result = getObjRecurs(name, "/", this.rootFolder);

    } else { // If a local directory name is given
      // turning the local directory name into an absolute path
      if (!this.currDir.equals("/")) {
        name = this.currDir + "/" + name;
      } else { // If the current directory is at root
        name = "/" + name;
      }
      result = getObjRecurs(name, "/", this.rootFolder);
    }
    return result;
  }

  /**
   * This is the recursive helper function to getObject() which searches through
   * the tree. With each recursive step it checks a new child, and adds to the
   * previous path until either, the desired object is found or the end of the
   * subtree is reached.
   * 
   * @param name - The absolute path of the desired directory or file
   * @param currName - The current path built by the previous recursive steps
   * @param dirrOrFile
   * @return result - Can either be the desired object or a null type
   */
  public Item getObjRecurs(String name, String currName, Item dirrOrFile) {
    // Base case if the desired name matches the name built by the recursive
    // steps
    if (currName.equals(name)) {
      return dirrOrFile;
    }
    // If the end of the subtree is reached
    else if (dirrOrFile == null || dirrOrFile.equals(File.class)) {
      Item result = null;
    }
    // A vector representing all the children in the tree
    Vector<Item> allChildren = dirrOrFile.getChildren();
    // Object representing the result of the recursive step
    Item result = null;
    // Runs for all children of the node and while an object is not found and
    // iff the node has children
    for (int i = 0; result == null && allChildren != null
        && i < allChildren.size(); i++) {
      // If the specified child is a Folder
      if (allChildren.get(i).getClass().equals(Folder.class)) {
        // the recursive call on the specified child as well as adding onto
        // the current path
        if (currName.equals("/")) {
          result = getObjRecurs(name, "/" + allChildren.get(i).getName(),
              allChildren.get(i));
        } else {
          result =
              getObjRecurs(name, currName + "/" + allChildren.get(i).getName(),
                  allChildren.get(i));
        }

      }
      // If the specified child is a File
      else {
        // Sending in a null as the child since, files have no children
        if (currName.equals("/")) {
          result = getObjRecurs(name, "/" + allChildren.get(i).getName(),
              allChildren.get(i));
        } else {
          result =
              getObjRecurs(name, currName + "/" + allChildren.get(i).getName(),
                  allChildren.get(i));
        }

      }

    }
    return result;
  }

  /**
   * Adds a folder at ROOT only into Manager, as well as its corresponding
   * absolute path. To be used only with Mkdir.
   * 
   * @param newFolder - A Folder at root
   */
  public void add(Folder newFolder) {
    fullPaths.add(newFolder.getPath());
  }

  /**
   * Returns a vector contained all the absolute paths in JShell
   * 
   * @return A vector containing all the absolute paths in JShell
   */
  public Vector<String> getFullPaths() {
    return this.fullPaths;
  }

  /**
   * Checks whether the specified absolute path is valid and exist or not.
   * 
   * @param path - An absolute path
   * @return A boolean representing whether or not the path is valid
   */
  public boolean checkValidPath(String path) {
    return this.fullPaths.contains(path);
  }

  /**
   * Sets the current working folder. To be used by CD only.
   * 
   * @param folder - representing the new current working folder
   */
  public void setCurrFolder(Folder folder) {
    this.currFolder = folder;
  }


  /**
   * Sets the root of the tree. ONLY DONE AT THE BEGINNING OF THE PROGRAM
   * 
   * @param root - A folder representing the root of the tree of Files
   */
  public void setRoot(Folder root) {
    this.rootFolder = root;
  }

  /**
   * returns the current working folder
   * 
   * @return the current working folder
   */
  public Folder getCurrFolder() {
    return this.currFolder;
  }

  /**
   * Gets the root folder
   * 
   * @return rootFolder - Represents the root of the tree
   */
  public Folder getRootFolder() {
    return this.rootFolder;
  }

  /**
   * Adds the an absolute path to fullPaths. To be used only in Mkdir for
   * non-root Folders.
   * 
   * @param path - An absolute path of a Folder
   */
  public void addFullPath(String path) {
    this.fullPaths.addElement(path);
  }

  /**
   * Get the DirStack created here; To be used with other command classes return
   * - dirStack
   */
  public DirStack getDirStack() {
    return this.dirStack;
  }

  /**
   * Setter used to set/overwrite the current DirStack
   * 
   * @param stack - A DirStack object
   */
  public void setDirStack(DirStack stack) {
    this.dirStack = stack;
  }

  /**
   * Converts Absolute paths or relative paths into absolute paths
   * 
   * @param path - A path; can be absolute or relative
   * @throws InvalidPath - If the path is invalid (ie: not in jFileSystem)
   *         throws is ignored for Mkdir, Move, and Copy since they work with
   *         Items that may not exist
   * @return path - An absolute version of path
   */
  public String getFullPath(String path) throws InvalidPath {
    // Saving the original path for error output purposes
    String originalPath = path;
    // Removing the single dots (ie: "/.")
    path = this.removeSingleDots(path);
    // Removing the "/" at the end
    if (path.endsWith("/") && !path.equals("/")) {
      path = path.substring(0, path.length() - 1);
    }
    if (path.contains("..")) {
      // Getting the parent of the current path
      if (path.equals("..")) {
        if ((this.getCurrPath().split("/").length > 2)) {
          path = this.getCurrPath().substring(0,
              this.getCurrPath().lastIndexOf("/"));
        } else {
          path = "/";
        }
      } else {
        // Removing all the ".." and outputting the corresponding path
        path = this.removeDots(path);
      }
    }
    // If it just a normal local path
    else if (!path.startsWith("/")) {
      if (!this.currDir.equals("/")) {
        path = this.currDir + "/" + path;
      } else {
        path = this.currDir + path;
      }
    }
    // Only checks if its a valid file that exists if this method is not called
    // from Mkdir, Move, and Copy
    if (!this.fullPaths.contains(path)
        && !(new Exception().getStackTrace()[1].getClassName()
            .equals("a2.Mkdir"))
        && !(new Exception().getStackTrace()[1].getClassName().equals("a2.MV"))
        && !new Exception().getStackTrace()[1].getClassName()
            .equals("a2.Copy")) {
      throw new InvalidPath(" is not a valid path", path);
    }
    return path;
  }

  /**
   * Removes any "."'s from the argument and handles multiple different cases
   * involving it
   * 
   * @param name - the argument that needs to be created
   * @return name - the argument but without any "."'s
   */
  public String removeSingleDots(String name) {
    // Since the "." operator does not really do anything significant it can
    // be removed from the path at it should still be equivalent to if the
    // "." was not there
    if (name.contains("/./") || name.endsWith("/.") || name.startsWith("/.")
        || name.startsWith("./")) {
      if (name.startsWith("./") && !this.getCurrPath().equals("/")) {
        name = name.substring(2, name.length());
      } else if (name.startsWith("./") && this.getCurrPath().equals("/")) {
        name = name.substring(1, name.length());
      }
      CharSequence operator = "/./";
      while (name.contains(operator)) {
        name = name.replace(operator, "/");
      }
      if (name.endsWith("/.")) {
        name = name.substring(0, name.length() - 2);
      }
    }
    return name;
  }

  /**
   * Accepts a local path is ".." inside as part of it and returns the
   * corresponding full path
   * 
   * @param name - the argument from the user
   * @return The absolute path of the folder that the user specified
   */
  public String removeDots(String name) {
    // Counting starts from the beginning of the string and moves to the end
    int headIndex = 0;
    // The number of occurrences of the specified string
    int numOfOccurences = 0;
    // Loop goes until the end of the string
    while (headIndex != -1) {
      // The first index of ".." from start to endIndex
      headIndex = name.indexOf("..", headIndex);
      // If the specified string ("..") is still found within the above
      // substring
      if (headIndex != -1) {
        // Increment the number of occurrences
        numOfOccurences++;
        // Increasing the search by 2, (since ".." has a length of 2)
        headIndex += 2;
      }
    }
    // Getting the current working (local) path
    String path = this.getCurrPath();
    int indexDots = name.indexOf("/");
    // Runs for the number of times ".." is in the argument
    for (int i = 0; i < numOfOccurences; i++) {
      // If name still contains ".." and but does not start with a ".." then
      // name is in the form ../DIR_NAME/../ , so the DIR_NAME must be added
      // to the absolute path and "i" must be decremented
      if (!name.startsWith("..") && name.contains("..")) {
        if (!path.equals("/")) {
          path = path + "/" + name.substring(0, indexDots);
        } else {
          path = "/" + name.substring(0, indexDots);
        }
        i--;
      } else {
        try { // If the number of ".." reaches past the root and error is thrown
          // Cutting a section of the current path off
          if (path.lastIndexOf("/") == path.indexOf("/")) {
            path = "/";
          } else {
            path = path.substring(0, path.lastIndexOf("/"));
          }
        } catch (Exception e) {
          // Returning an invalid path
          path = "//////////";
          break;
        }
      }
      // Removing a piece of the argument off
      name = name.substring(indexDots + 1, name.length());
      indexDots = name.indexOf("/");

    }
    // Returning the absolute path
    if (path.equals("/") && !name.equals("..")) {
      return path + name;
    } else if (path.equals("/") || name.equals("..")) {
      return path;
    } else {
      return path + "/" + name;
    }
  }
}
