package a2;

import java.util.*;

public class Mkdir implements CommandInterface {
  // The JFileSystem
  private FileSystem Manager;
  // The arguments that the user submits
  private String[] names;
  // List of all the special characters that cannot be used
  private String[] specialChar =
      new String[] {"!", "@", "$", "&", "#", "*", "(", ")", "?", ":", "[", "]",
          "\"", "<", ">", "\'", "`", "\\", "|", "=", "{", "}", ";"};
  // The string that is to be used by ProQuery (the ">" and ">>" cases)
  private String stringToOutput = "";

  /**
   * The constructor
   * 
   * @param fileManager - The JFileSystem with all the file and folder
   * @param arg - The string array with all the arguments the user enters
   */
  public Mkdir(JFileSystem fileManager, String[] arg) {
    this.Manager = fileManager;
    this.names = arg;

  }

  /**
   * Used to get the output of the command
   * 
   * @return stringToOutput
   */
  public String getStringToOutput() {
    return this.stringToOutput;
  }

  /**
   * Creates a directory based off of a absolute path (starting from "/" the
   * root)
   * 
   * @param name - the absolute path
   * @throws InvalidPath
   */
  public void executeFullPath(String name) throws InvalidPath {
    for (int i = 0; i < specialChar.length; i++) {
      if (name.contains(specialChar[i])) {
        throw new InvalidPath(" is not a valid path", name);
      }
    }
    // The parent path (everything up to and excluding the last "/")
    String parentPath = name.substring(0, name.lastIndexOf("/"));
    // If the parent path is a valid existing path
    if (Manager.checkValidPath(parentPath)) {
      // Used to find the name of the directory
      int currDirIndex = name.lastIndexOf("/");
      // Getting the name of the directory
      String currDir = name.substring(currDirIndex + 1, name.length());
      // Creating the new folder with its name and its path
      Folder newFolder = new Folder(currDir, name);
      // Adding the full path to the FileSystem
      Manager.addFullPath(name);
      // Getting the parent folder form using the parent path
      Folder parentFolder = (Folder) Manager.getObject(parentPath);
      // Adding the new folder as a child folder to the parent
      parentFolder.addChildren(newFolder);
    } else if (parentPath.equals("")) { // If folder is being created at root
      int currDirIndex = name.lastIndexOf("/");
      String currDir = name.substring(currDirIndex + 1, name.length());
      Folder newFolder = new Folder(currDir, name);
      Manager.addFullPath(name);
      Folder parentFolder = (Folder) Manager.getObject("/");
      parentFolder.addChildren(newFolder);

    } else {
      // If it is an invalid parent path
      this.stringToOutput = "That was not a valid path.";
      // Output.printPathError();
      throw new InvalidPath(" is not a valid path", name);
    }
  }

  /**
   * The method that will be called by ProQuery. Checks which kind of creation
   * is required and runs the appropriate method. Runs all arguments provided
   * Also print errors if the directory already exists
   * 
   * @return an empty string (to comply with ProQeury's implementation)
   */
  public String execute() {
    // Runs for all arguments in the string array
    for (int index = 0; index < this.names.length; index++) {
      // Current argument
      String name = this.names[index];

      // Converts the given path into an absolute path
      try {
        name = this.Manager.getFullPath(name);
      } catch (InvalidPath e) {
        System.err.println(e);
      }
      // If the path already exists raise an error
      if (this.Manager.checkValidPath(name) || name.equals("")) {
        Output.printPathError();
      } else {
        try {
          // Create the directory
          this.executeFullPath(name);
        } catch (InvalidPath e) {
          System.err.println(e);
        }
      }

    }
    return "";
  }

  /**
   * The manual explaining how to use the mkdir command
   * 
   * @return returns a string with the explanation of how mkdir works
   */
  public String manual() {
    return "mkdir DIR … - Creates directories, each of which may be relative\n"
        + "to the current directory or may be a full path. The names given\n"
        + "to the specified directories cannot contain special characters.";
  }
}
