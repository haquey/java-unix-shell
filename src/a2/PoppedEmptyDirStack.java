package a2;

public class PoppedEmptyDirStack extends Exception {
  /**
   * This exception class represents when an attempt to pop an
   * empty directory stack is done
   * 
   * @param message - The error message
   * @param path - The error path
   */
  public PoppedEmptyDirStack(String message, String path) {
    // Calling on the parent Exception class
    super(path + message);
  }

}

