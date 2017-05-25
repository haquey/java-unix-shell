package a2;

public class Exit implements CommandInterface {
  // initialize a String variable that's always an empty string for the execute
  // method to return
  private final String stringToOutput = "";

  /**
   * The constructor
   * 
   * @param jFileSystem - The JFileSystem with all the file and folder
   */

  public Exit(JFileSystem jFileSystem) {}

  /**
   * This method will terminate the console
   */

  public String execute() {
    // method to exit consoles
    System.exit(0);
    // return an empty String since exit command would never print anything
    // on the console
    return stringToOutput;
  }

  /**
   * This function return the instructions on how to use the command exit.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "exit - Terminates the session/program.\n";
  }

}
