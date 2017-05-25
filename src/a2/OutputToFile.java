package a2;

import a2.File;
import a2.Folder;
import a2.JFileSystem;
import a2.Output;

public class OutputToFile {
  // initialize a String array to hold all the invalid characters in a file name
  private static String[] specialChar = new String[] {"/", "!", "@", "$", "&",
      "#", "*", "(", ")", "?", ":", "[", "]", "\"", "<", ">", "\'", "`", "\\",
      "|", "=", "{", "}", "/", ";", " "};

  /**
   * This method will replace the contents of the outfile given to the
   * parameters of the method with the output given to the parameter
   * 
   * @param fileSystem - The JFileSystem with all the files and folders
   * @param output - the String that will replace the body of the outfile
   * @param outfile - the File that will have its contents replaced by the
   *        output
   */

  public static void overwrite(JFileSystem fileSystem, String output,
      String outfile) {
    // find the file that the user is going to be working with
    File replaceFile = findFile(fileSystem, outfile);
    // if the file name has an invalid character print an error message
    if (replaceFile == null) {
      System.out.println("That was not a valid file name or path.");
    } else {
      // replace the contents of the file with the String
      replace(replaceFile, output);
    }
  }

  /**
   * This method will add the output to the end of a outfile given to the
   * parameters of the method.
   * 
   * @param fileSystem - The JFileSystem with all the files and folders
   * @param output - the String that will be added to the body of the outfile
   * @param outfile - the File that will have the output added to its contents
   */

  public static void append(JFileSystem fileSystem, String output,
      String outfile) {
    // find the file that the user is going to be working with
    File appendFile = findFile(fileSystem, outfile);
    // if the file name has an invalid character print an error message
    if (appendFile == null) {
      System.out.println("That was not a valid file name or path.");
    } else {
      // add the String to the body of the file
      append(appendFile, output);
    }
  }

  /**
   * This method will check if the outfile given is a valid file name or a path.
   * If the outfile given is a path, this method will traverse the path and find
   * the file. If an invalid file is given or an invalid path is given null will
   * be returned.
   * 
   * @param fileSystem - The JFileSystem with all the file and folder
   * @param outfile - the file that is requested
   * 
   * @return file - the File matching of the outfile given to the parameters or
   *         null if an invalid file name or path is given
   */

  private static File findFile(JFileSystem fileSystem, String outfile) {
    // get the current working directory
    Folder currFolder = fileSystem.getCurrFolder();
    // initialize a variable to hold the return
    File file;
    // initialize a variable to check if the outfile is a valid file name
    boolean valid = true;
    // loop through each character of the file name
    for (String eachChar : specialChar) {
      // check if file name contains any illegal characters
      if (outfile.contains(eachChar)) {
        // if the file name contains illegal characters falsify the valid
        // variable
        valid = false;
      }
    }
    // check if the outfile is a valid file name
    if (valid) {
      // call the helper function to get the file
      file = findFileHelper(currFolder, outfile);
      // add the path to the file to the fileSystem
      if (fileSystem.getCurrPath().equals("/")) {
        fileSystem.addFullPath(fileSystem.getCurrPath() + outfile);
      } else {
        fileSystem.addFullPath(fileSystem.getCurrPath() + "/" + outfile);
      }
      // check if the outfile is a path
    } else if (outfile.contains("/")) {
      // find the path to the folder where the file is kept
      String outfileLocation = outfile;
      // check if the path is the root and not a valid file name
      if (outfile.equals("/") | outfile.equals("//")) {
        // if the outfile is the root return null
        file = null;
        // check if the path to the parents folder is a valid path
      } else {
        try {
          if (outfileLocation.lastIndexOf("/") == outfileLocation.length()
              - 1) {
            // remove the last "/"
            outfileLocation =
                outfileLocation.substring(0, outfileLocation.length() - 1);
          }
          // find the path to the folder where the file is kept
          outfileLocation = outfile.substring(0, outfile.lastIndexOf("/"));
          // get the folder object where the file is kept
          Folder outfileFolder = (Folder) fileSystem
              .getObject(fileSystem.getFullPath(outfileLocation));
          // get the file name
          String fileName = outfile.substring(outfile.lastIndexOf("/") + 1);
          // call the helper function to get the file, or create a new file
          file = findFileHelper(outfileFolder, fileName);
          // add the path to the file to the fileSystem
          if (outfileLocation.equals("/")) {
            fileSystem.addFullPath(outfileLocation + fileName);
            file.setPath(outfileLocation + fileName);
          } else {
            fileSystem.addFullPath(outfileLocation + "/" + fileName);
            file.setPath(outfileLocation + "/" + fileName);
          }
        } catch (InvalidPath e) {
          // if the path is invalid return null
          file = null;
        }
      }
    } else {
      // if there is an invalid character return null
      file = null;
    }
    // return the file
    return file;
  }

  /**
   * Given a folder, this method will return a file that is given to the
   * parameters that exists in the folder or will create a new file if the file
   * does not exist in the folder provided. This method will then return the
   * file
   * 
   * @param fileSystem - The JFileSystem with all the file and folder
   * @param outfile - the file that is requested
   * 
   * @return file - the File matching of the outfile given to the parameters
   */


  private static File findFileHelper(Folder currFolder, String outfile) {
    // call the getFile method to get the file
    File file = currFolder.getFile(outfile);
    // check if the file exists
    if (file == null) {
      // if the file does not exist make a new file
      file = new File(outfile);
      // add the file to the given folder
      currFolder.addChildren(file);
    }
    return file;
  }


  /**
   * This method will replace the body of the file with the string provided in
   * the parameter given to the constructor
   * 
   * @param file - the file that will have its contents replaced by the output
   * @param body - the string that will replace the body of the file
   */

  private static void replace(File file, String body) {
    file.setBody(body);
  }

  /**
   * This method will add a given line to the end of the given file
   * 
   * @param file - the file that will have the output added to its contents
   * @param body - the string that will be added to the body of the file
   */

  private static void append(File file, String body) {
    if (!(file.getBody().equals(""))) {
      file.addToBody("\n" + body);
    } else {
      file.addToBody(body);
    }
  }
}
