package a2;

import java.util.List;
import java.util.Vector;

public class History implements CommandInterface // need execute method()
{
  // variable declaration
  private Vector<String> inputHistory;
  private Vector<String> output;
  private int commandNumber = 0;
  private int commandLocation;
  private int location;
  private List<String> subHistory;
  private String outputToString = "";

  // Default constructor
  public History() {
    // make a new list
    this.inputHistory = new Vector<String>();
  }

  /**
   * The purpose of this method is to remove the last item in the history
   */
  public void popHistory() {
    // remove the element
    this.inputHistory.remove(this.inputHistory.size() - 1);
    // lower the command number
    this.commandNumber--;
  }

  /**
   * The purpose of this method is to add an element to the list
   * 
   * @param input, the string value of the item to be added to the list
   */
  public void addInput(String input) {
    // iterate the command number
    commandNumber++;
    // add the command input with the appropriate formatting
    this.inputHistory.add(commandNumber + ". " + input);

  }

  /**
   * The purpose of this method is to output the command history through the
   * output class
   * 
   * @return outputToString, contains the contents of the history
   */
  public String execute() {
    outputToString = printContents(inputHistory);
    return outputToString;
  }

  /**
   * The purpose of this method is to output the previous strLocation commands
   * as given by the user and output using the output class
   * 
   * @param strLocation, will contain one value which will be the amount of
   *        previous commands the user wishes to see
   * 
   * @return outputToString, contains the contents of the sublist history
   */
  public String execute(String[] strLocation) {
    // Determine if the given string can be turned into an integer
    // If not, display an error message
    try {
      // convert the given string array into an integer
      location = Integer.parseInt(strLocation[0]);

      // makes sure the user cannot enter a negative number
      if (location >= 0) {
        // if the user chose a location larger than the size of the history
        // vector, choose zero instead - as per command terminal functionality
        commandLocation = Math.max(inputHistory.size() - location, 0);

        // storing history from given command number to the end of the
        // history
        subHistory = inputHistory.subList(commandLocation, inputHistory.size());

        // creating a vector string to output that displays the history
        // appropriately from a location specified or 0 if need be
        outputToString = printContents(subHistory);
      } else {
        Output.printError();
      }
    } catch (Exception e) {
      Output.printNumberError();
    }
    return outputToString;
  }

  /**
   * A method to get the full inputHistory solely for testing
   * 
   * @return a vector<String> containing the history
   */
  public Vector<String> getList() {
    return this.inputHistory;
  }

  /**
   * A method to get the input history sublist solely for testing WILL NOT WORK
   * unless the history variable is instantiated
   * 
   * @return a List<String> containing the sublist history
   */
  public List<String> getSubList() {
    return this.subHistory;
  }

  /**
   * A method to return the manual of the history command and what it does
   * 
   * @return String, this string contains the manual
   */
  public String manual() {
    return "history [number] - Prints out past/recently input commands during\n"
        + "the session. The given value specifies the last number of commands\n"
        + "to output.\n";

  }

  /**
   * This function adds every value of a List to a string in an appropriate
   * format
   * 
   * @param List<String> contents, the vector to parse through and add all
   *        contents
   * 
   * @return history, a string that contains all elements of the List
   * 
   */

  public String printContents(List<String> contents) {
    // String to hold values
    String history = "";
    // Storing all values of the List to the string with appropriate format
    for (int i = 0; i < contents.size(); i++) {
      history += contents.get(i) + "\n";
    }
    return history;
  }


  /**
   * This function adds every value of a vector to a string in an appropriate
   * format
   * 
   * @param Vector<String> contents, the vector to parse through and add all
   *        contents
   * 
   * @return history, a string that contains all elements of the vector
   * 
   */

  public String printContents(Vector<String> contents) {
    // String to hold values
    String history = "";
    // Storing the values of the vector to the string with appropriate format
    for (int i = 0; i < contents.size(); i++) {
      history += contents.get(i) + "\n";
    }
    return history;
  }

}
