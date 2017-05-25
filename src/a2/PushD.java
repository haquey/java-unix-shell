package a2;

public class PushD implements CommandInterface {
  private JFileSystem Manager;
  private DirStack directoryStack;
  private String[] location;
  private String output = "";

  /**
   * basic constructor for the PushD class
   * 
   * @param fileManager takes in a fileManager to manipulate its DirStack
   * @param location takes a location to cd into
   */
  public PushD(JFileSystem fileManager, String[] location) {
    this.Manager = fileManager;
    this.directoryStack = fileManager.getDirStack();
    this.location = location;
  }

  /**
   * The purpose of this method is to do the PushD command, which enters a given
   * string into a DirStack and changes the directory to the given string path
   */
  public String execute() {
    // If the given location doesn't start with a slash, add the slash
    if (location[0].charAt(0) != '/') {
      location[0] = "/" + location[0];
    }
    // makes sure the given path is valid before pushing
    if (Manager.checkValidPath(location[0])) {
      // get the current location and store it
      String savedDirectory = Manager.getCurrPath();
      // push the current location into the temporary directoryStack
      directoryStack.pushD(savedDirectory);
      // send the new directory stack to the FileSystem
      Manager.setDirStack(directoryStack);
      // change the current directory to the given location
      CD changeDirectory = new CD(Manager, location);
      changeDirectory.execute();
    } else {
      Output.printPathError();
    }
    return output;
  }

  /**
   * The purpose of this method is display the manual for the class - used for
   * the Man class
   */
  public String manual() {
    return "pushd DIR - Saves the old current working directory in the\n"
        + "directory stack so that it can be returned at a later time using\n"
        + "the 'popd' command. The specified DIR becomes the new current\n"
        + "working directory.\n";
  }
}
