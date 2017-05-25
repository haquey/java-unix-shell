package a2;

public class PWD implements CommandInterface {
  // collaboration with the fileSystem
  private JFileSystem fileSystem;
  // initialize a String variable that's always an empty string for the execute
  // method to return
  private String stringToOutput;

  /**
   * The constructor
   * 
   * @param manager - The JFileSystem with all the file and folder
   */

  public PWD(JFileSystem manager) {
    this.fileSystem = manager;
  }

  /**
   * This method gets the current working path of in the filesystem and prints
   * it on the console. If the command is redirected with "> OUTFILE", this
   * method will redirect the output to the OUTFILE. If the command is
   * redirected with ">> OUTFILE", this method will add the output to the body
   * of the OUTFILE.
   */

  public String execute() {
    // get the path of the working directory as a string
    stringToOutput = fileSystem.getCurrPath();
    return stringToOutput;
  }

  /**
   * This function returns the instructions on how to use the command pwd.
   * 
   * @return a string telling users the how the command works
   */
  public String manual() {
    return "pwd - Prints the current working directory with its whole path.\n";
  }
}
