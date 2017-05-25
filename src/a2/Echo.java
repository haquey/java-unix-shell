package a2;

import java.util.Arrays;

public class Echo implements CommandInterface {
  // a String array to hold the parameters the user inputs into the constructor
  private String[] echoParameters;
  // a String to hold the output of the command
  private String stringToOutput = "";

  /**
   * The constructor
   * 
   * @param jFileSystem - The JFileSystem with all the files and folders
   * @param echoParameters - The string array with the String the user wants to
   *        print
   */
  public Echo(JFileSystem jFileSystem, String[] echoParameters) {
    this.echoParameters = echoParameters;
  }

  /**
   * This method will return the String that the user wishes to print formatted
   * without the parenthesis
   * 
   * @return stringToOutput - the output of the command, the string the user
   *         wishes to print without the parenthesis
   */
  public String execute() {
    // If the String is a single word check if it's a valid string
    if (echoParameters.length == 1) {
      // if it is a valid String make the output the string without the
      // parenthesis
      if (echoParameters[0].startsWith("\"")
          && echoParameters[0].endsWith("\"")) {
        stringToOutput =
            echoParameters[0].substring(1, echoParameters[0].length() - 1);
      } else {
        // if it is not an valid String print an error
        System.out.println("That was not a valid String");
      }
    } else {
      // check if the message is a valid String
      if (echoParameters[0].startsWith("\"")
          && echoParameters[echoParameters.length - 1].endsWith("\"")) {
        // if it is a valid String join the sentence together
        for (String eachString : echoParameters) {
          stringToOutput += eachString + " ";
        }
        // change the output to the message and remove the parenthesis
        stringToOutput =
            stringToOutput.substring(1, stringToOutput.length() - 2);
      } else {
        // if it is not an valid String print an error
        System.out.println("That was not a valid String");
      }
    }
    return stringToOutput;
  }

  /**
   * This function returns the instructions on how to use the command echo.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "cd String - prints the String";
  }

}
