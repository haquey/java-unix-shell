package a2;

public class PopD implements CommandInterface {
  private JFileSystem Manager;
  private DirStack directoryStack;
  private String[] location;
  private String output = "";

  /** 
   * basic constructor for the PushD class
   * @param fileManager takes in a fileManager to manipulate the DirStack
   */
  public PopD(JFileSystem fileManager) {
    this.Manager = fileManager;
    this.directoryStack = Manager.getDirStack();
    this.location = new String[1];
  }

  /**
   * The purpose of this method is to do the PopD command, which gets a file
   * path from the DirStack as per LIFO, removing it from the stack and changes
   * the directory to that filepath
   */
  public String execute() {

    try {
      // pop the last saved location from the DirStack
      location[0] = directoryStack.popD();
      // updated the FileSystem DirStack object
      Manager.setDirStack(directoryStack);
      // change the current directory to the given location
      CD changeDirectory = new CD(Manager, location);
      changeDirectory.execute();
    } catch (Exception e) {
      Output.printDirectoryStackError();
    }
    return output;
  }

  /**
   * The purpose of this method is to display the manual for this class for the
   * Man class
   */
  public String manual() {
    return "popd - Removes the top directory on the directory stack and makes\n"
        + "it the current working directory. If no directory exists, an error\n"
        + "message is returned.\n";
  }
}
