package a2;

import java.net.*;
import java.io.*;

public class Curl implements CommandInterface {
  // collaboration with fileSystem
  private FileSystem fileSystem;
  // initialize a String variable to hold the URL provided to the constructor
  private String URL;
  // initialize a String variable that's always an empty string for the execute
  // method to return
  private final String stringToOutput = "";
  // initialize a String array to hold all the invalid characters in a file name
  private final String[] specialChar = new String[] {"/", "!", "@", "$", "&",
      "#", "*", "(", ")", "?", ":", "[", "]", "\"", "<", ">", "\'", "`", "\\",
      "|", "=", "{", "}", "/", ";", " "};

  /**
   * The constructor
   * 
   * @param manager - The JFileSystem with all the files and folders
   * @param parameter - The String array with all the arguments the user enters
   */

  public Curl(JFileSystem manager, String[] parameter) {
    this.fileSystem = manager;
    this.URL = parameter[0];
  }

  /**
   * This method will take the parameter passed into the constructor and create
   * a file containing the contents of the URL provided. If an invalid file name
   * or URL is provided, an error will be displayed on the console.
   * 
   * @return stringToOutput - an empty String since nothing is ever displayed on
   *         the console when the curl command is run unless it is an error.
   */

  public String execute() {
    try {
      // initialize a variable to check if the file name is valid
      boolean valid = true;
      // create a new URL object with the provided URL in the constructor
      URL webAddress = new URL(URL);
      // create a new buffered reader to read the contents of the URL
      BufferedReader in =
          new BufferedReader(new InputStreamReader(webAddress.openStream()));
      // get the name of the file from the URL
      String fileName = URL.substring(URL.lastIndexOf("/") + 1);
      // check if the file name is valid
      for (String eachChar : specialChar) {
        if (fileName.contains(eachChar)) {
          valid = false;
        }
      }
      if (valid) {
        // if the file name is valid make a new file with the same file name
        File file = new File(fileName);
        // add the file to the current working directory
        fileSystem.getCurrFolder().addChildren(file);
        // add the path to the file to the fileSystem
        if (fileSystem.getCurrPath().equals("/")) {
          fileSystem.addFullPath(fileSystem.getCurrPath() + fileName);
          file.setPath(fileSystem.getCurrPath() + fileName);
        } else {
          fileSystem.addFullPath(fileSystem.getCurrPath() + "/" + fileName);
          file.setPath(fileSystem.getCurrPath() + "/" + fileName);
        }
        // initialize a string inputLine
        String inputLine;
        // read the next line until there is no more lines to be read
        while ((inputLine = in.readLine()) != null)
          // add each new line to the body of the file
          file.addToBody(inputLine + "\n");
        // close the buffered reader
        in.close();
      } else {
        // if an invalid name is provided, display an error on the console
        System.out.println("That was not a valid file name.");
      }
    } catch (IOException e) {
      // if an invalid URL is provided, display an error on the console
      System.out.println("That was not a valid URL.");
    }
    return stringToOutput;
  }

  /**
   * This function returns the instructions on how to use the command curl.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "car URL - Creates a new file in the current working direction \n"
        + "containing the contents of the URL provided with the same name as\n"
        + " the file in the URL.\n";
  }


}
