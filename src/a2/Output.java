package a2;

import java.util.List;
import java.util.Vector;

public class Output {

  /**
   * This function display an error message claiming the command is invalid, if
   * an invalid command is given
   * 
   */

  public static void printError() {
    System.out.println("That was not a recognized command.");
  }
  
  /**
   * This function display an error message claiming the number entered is
   * invalid, if an invalid number is given
   * 
   */


  public static void printNumberError() {
    System.out.println("A numeric arguement is required.");
  }

  /**
   * This function display an error message claiming the path is invalid, if an
   * invalid path is given
   * 
   */

  public static void printPathError() {
    System.out.println("That was not a valid path.");
  }

  /**
   * This function displays an error message when trying to retrieve an item
   * from the directory stack when there are no items inside
   * 
   */

  public static void printDirectoryStackError() {
    System.out.println("The directory stack is empty.");
  }

  /**
   * This function display an error message claiming the file name is invalid,
   * if an invalid file name is given
   * 
   */

  public static void printFileNameError() {
    System.out.println("That was not a valid file name.");
  }


  /**
   * This function display a given string on a new line
   * 
   */

  public static void printString(String contents) {
    System.out.println(contents);
  }

  /**
   * This function display a given string on a single line
   * 
   */

  public static void printSingleLineString(String contents) {
    System.out.print(contents);
  }

  /**
   * This function display the contents of a given string List with each element
   * on a new line
   * 
   * @param contents: The List to be output
   */
  public static void printContents(List<String> contents) {
    for (String content : contents) {
      System.out.println(content);
    }
  }


  /**
   * This function display the contents of a given string vector with each
   * element on a new line
   * 
   */

  public static void printContents(Vector<String> contents) {
    for (String content : contents) {
      System.out.println(content);
    }
  }
}
