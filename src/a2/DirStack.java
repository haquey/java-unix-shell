package a2;

import java.util.Stack;

public class DirStack {
  public Stack<String> directoryStack;

  /**
   * Constructor for the DirStack
   */
  public DirStack() {
    // make the directory stack
    directoryStack = new Stack<String>();
  }

  /**
   * The purpose of this method is to add an item to the directory stack
   * 
   * @param dir: the string value of the item the user wishes to add
   */
  public void pushD(String dir) {
    directoryStack.push(dir);
  }

  /**
   * The purpose of this method is to return the directoryStack, used primarily
   * for testing purposes
   * 
   * @return Stack<String> directoryStack the returned stack
   */
  public Stack<String> getStack() {
    return directoryStack;
  }

  /**
   * The purpose of this method is to return the last value of the
   * directoryStack as per LIFO
   * 
   * @return A string value which was the last item of the stack
   */
  public String popD() {
    return directoryStack.pop();
  }
}
