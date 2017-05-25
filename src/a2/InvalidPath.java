package a2;

public class InvalidPath extends Exception {
  /**
   * This exception class represents when an invalid path is given
   * 
   * @param message - The error message
   * @param path - The error path
   */
  public InvalidPath(String message, String path) {
    // Calling on the parent Exception class
    super(path + message);
  }

}
