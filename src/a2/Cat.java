package a2;

import java.util.Arrays;
import java.util.Vector;

public class Cat implements CommandInterface {
  // collaboration with the fileSystem
  private FileSystem fileSystem;
  // a String array to hold the parameters the user inputs into the constructor
  private String[] parameter;
  // a String to hold the output of the command
  private String stringToOutput = "";

  /**
   * The constructor
   * 
   * @param manager - The JFileSystem with all the files and folders
   * @param files - The string array with all the file names and paths the user
   *        enters
   */

  public Cat(JFileSystem manager, String[] files) {
    this.fileSystem = manager;
    this.parameter = files;
  }

  /**
   * This method will take the parameter passed into the constructor and read
   * the contents of those files in the current directory. If the files do not
   * exist or an invalid path is given, an error message will be printed. If the
   * command is redirected with "> OUTFILE", this method will redirect the
   * output to the OUTFILE. If the command is redirected with ">> OUTFILE", this
   * method will add the output to the body of the OUTFILE.
   * 
   * @return stringToOutput - an empty string if an invalid file name or path
   *         was given, otherwise the output of the cat command.
   */

  public String execute() {
    // reset the output before each execute
    stringToOutput = "";
    // get the current working directory
    Folder currFolder = fileSystem.getCurrFolder();
    // check if the user wants to read one file
    if (this.parameter.length == 1) {
      // get the file the user wants to read
      File file = (File) currFolder.getFile(parameter[0]);
      if (file == null) {
        // if the outfile is not a valid file name check if it is a valid path
        if (parameter[0].contains("/")) {
          // call the getOutputFromPath method to get the output if it is a
          // valid path
          stringToOutput = getOutputFromPath(parameter[0]);
        } else {
          // if it is not a valid path nor a valid file name print an error
          System.out.println("That was not a valid path or file name.");
        }
      } else {
        // if it is a valid file name, change the output to be the body of the
        // file
        stringToOutput = file.getBody();
      }
    } else {
      // if there is multiple files, ignore the redirection of output
      for (String eachFile : this.parameter) {
        // get the file the user wants to read
        File file = (File) currFolder.getFile(eachFile);
        if (file == null) {
          // if the outfile is not a valid file name check if it is a valid
          // path
          if (eachFile.contains("/")) {
            // run the getOutputFromPath method to get the output and add 3
            // extra lines to separate each file
            stringToOutput += getOutputFromPath(eachFile) + "\n\n\n\n";
          } else {
            // if it is not a valid name or a file path print an error
            System.out.println("That was not a valid path or file name.");
          }
        } else {
          // if the outfile is a valid file name print the contents of the
          // file
          // print three lines to separate each file being read
          stringToOutput += file.getBody() + "\n\n\n\n";
        }
      }
    }
    return stringToOutput;
  }

  /**
   * This method will take a String path and provide the body of the file at the
   * end of the path. If it is an invalid path an error will be printed
   * 
   * @param path - a String path leading to the file
   * 
   * @return stringToOutput - the body of the file. This would return an empty
   *         string if an invalid path is given
   */

  private String getOutputFromPath(String path) {
    // initialize the variable to be returned
    String output = "";
    // check if path given is the root folder
    if (path.equals("/") | path.equals("//")) {
      // print an error
      System.out.println("That was not a valid path");
    } else {
      // run the get FileFromPath method to obtain the File that will be working
      // with
      File file = getFileFromPath(path);
      // if the file is not found print an error
      if (file == null) {
        System.out.println("That was not a valid path or file name.");
      } else {
        // change the output to the body if a valid file is found
        output = file.getBody();
      }
    }
    return output;
  }

  /**
   * This method will take a String path and provide file at the end of the
   * path. If it is an invalid path null will be returned.
   * 
   * @param path - a String path leading to the file
   * 
   * @return file - the file at the end of the path. If the path is an invalid
   *         path, null will be returned.
   */

  private File getFileFromPath(String path) {
    // initialize the variable to be returned
    File file = null;
    try {
      // check if the path ends with "/"
      if (path.lastIndexOf("/") == path.length() - 1) {
        // remove the last "/"
        path = path.substring(0, path.length() - 1);
      }
      // get path of the folder containing the file that's required
      String folderPath = path.substring(0, path.lastIndexOf("/"));
      // get the name of the file
      String fileName = path.substring(path.lastIndexOf("/") + 1);
      // clean up the path so it's an absolute path
      String fullPath = fileSystem.getFullPath(folderPath);
      // get the folder containing the file that's required
      Folder fileFolder =
          (Folder) fileSystem.getObject(fileSystem.getFullPath(fullPath));
      // get the file
      file = (File) fileFolder.getFile(fileName);
    } catch (InvalidPath e) {
    }
    return file;
  }

  /**
   * This function returns the instructions on how to use the command cat.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "cat FILE1 [FILE2  …] - Displays the contents of the specified\n"
        + "files concatenated within the shell.\n";
  }
}
