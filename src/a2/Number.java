package a2;

import java.util.Vector;

public class Number implements CommandInterface {
  private String commandNumber;
  private ProQuery process;
  private JFileSystem fileManager;
  private History commandHistory;
  private String output = "";
  private static final int REPLACELOCATION = 3;

  /**
   * The constructor for the class
   * 
   * @param fileManager
   * @param process
   * @param number
   */
  public Number(JFileSystem fileManager, ProQuery process, String number) {
    // contains a proquery instance
    this.process = process;
    // A string instance that holds the command to be re-executed
    this.commandNumber = number;
    // Uses the given file system
    this.fileManager = fileManager;
  }

  @Override
  /**
   * Does the command at the history location given, executes the !number class
   * 
   * @return output returns an empty string
   */
  public String execute() {
    // Stores the command location
    int commandLocation = 0;
    // check if the parameter is a number
    try {
      commandLocation = Integer.parseInt(commandNumber);
      if (commandLocation > 0) {
        // because our vectors start from 0, reduce the number by one
        commandLocation = commandLocation - 1;
        // Get History from ProQuery
        History commandHistory = process.getHistory();
        Vector<String> allCommands = commandHistory.getList();
        if (commandLocation <= allCommands.size())
        {
          // Get the command at the specified user location
          String query =
              allCommands.get(commandLocation).substring(REPLACELOCATION);

          // remove the !number command from the history
          commandHistory.popHistory();

          // set the ProQuery history
          process.setHistory(commandHistory);
          // do the past command
          process.sortQuery(query);
        }
        else
        {
          System.out.println("A number within the history should be chosen");
        }
      } else {
        System.out.println("A number greater than 0 must be chosen");
      }
    } catch (NumberFormatException e) {
      System.out.println("A number must be given");
    }
    return output;

  }

  /**
   * Method returns the manual of how the !number command actually works
   */
  @Override
  public String manual() {
    return "!number - This command will recall any of previous history\n"
        + "by its number(>=1) +preceded by an exclamation point and will then\n"
        + "execute the command associated with that history number";
  }



}
