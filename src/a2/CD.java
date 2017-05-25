package a2;

import a2.FileSystem;
import a2.Folder;
import a2.JFileSystem;
import a2.Output;


public class CD implements CommandInterface {
  // collaboration with the fileSystem
  private FileSystem fileSystem;
  // initialize a String array variable to hold the path given in the
  // constructor
  private String path;
  // initialize a String variable that's always an empty string for the execute
  // method to return
  private final String stringToOutput = "";

  /**
   * The constructor
   * 
   * @param manager - The JFileSystem with all the files and folders
   * @param parameter - The String array with all the arguments the user enters
   */

  public CD(JFileSystem manager, String[] parameter) {
    this.fileSystem = manager;
    // set the path to be the first element of the parameter, since it should
    // only traverse through one path at a time
    this.path = parameter[0];
  }

  /**
   * This method will take the parameter passed into the constructor and
   * traverse into the path given if it is a valid path. If it is not a valid
   * path, an error will be displayed on the console.
   * 
   * @return stringToOutput - an empty String since nothing is ever displayed on
   *         the console when the cd command is run unless it is an error.
   */

  public String execute() {
    try {
      // get a clean path without the dots
      String fullPath = fileSystem.getFullPath(path);
      // set the String representation of the current directory in the
      // fileSystem
      fileSystem.setFullPath(fullPath);
      // set the current folder in the fileSystem
      fileSystem.setCurrFolder((Folder) fileSystem.getObject(fullPath));
    } catch (InvalidPath e) {
      // if not a valid path was given print an error message
      System.out.println("That was not a valid path.");
    }
    return stringToOutput;
  }

  /**
   * This function returns the instructions on how to use the command cat.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "cd DIR - Changes the current directory to DIR, which may be a\n"
        + "specified full path or a path relative to the current directory.\n"
        + "'..' refers to the parent directory while '.' refers to the\n"
        + " current.\n";
  }
}
