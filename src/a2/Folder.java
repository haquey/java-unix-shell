package a2;

import java.util.*;

public class Folder extends Item {

  /**
   * The constructor
   * 
   * @param name - The name of the Folder (directory)
   * @param path - The absolute path of the Folder
   */
  public Folder(String name, String path) {
    // Calling the parent class
    super(name, path, new Vector(), new Vector<String>());
  }

  /**
   * Adds children (A Folder) to this Folder
   * 
   * @param child - A sub-Folder to this one
   */
  public void addChildren(Item child) {
    // Adding the object
    super.getChildren().addElement(child);
    // Adding the Folder name
    super.getChildrenNames().addElement(child.getName());
  }

  /**
   * removes children (A Folder) to this Folder
   * 
   * @param child - A sub-Folder to this one
   */
  public void removeChildren(Item child) {
    // removing the object
    super.getChildren().remove(child);
    // removing the Folder name
    super.getChildrenNames().remove(child.getName());
  }

  /**
   * Gets the child from the vector at the specified index; Returns null if no
   * children are present
   * 
   * @param index - an index value
   * @return - An item
   */
  public Item getChildren(int index) {
    if (super.getChildren().size() <= index) {
      return null;
    }
    return super.getChildren().get(index);
  }

  /**
   * Gets the a File that is present in the folder
   * 
   * @param name - The name of a file
   * @return result - A file
   */
  public File getFile(String name) {
    File result = null;
    // Checks for all the children in this Folder
    for (Item fileOrFolder : super.getChildren()) {
      if (fileOrFolder.getName().equals(name)) {
        result = (File) fileOrFolder;
      }
    }
    return (File) result;
  }

  /**
   * Gets the child name at the specified index; returns null if there are no
   * children
   * 
   * @param index - an index value
   * @return - A string representing the name of a object
   */
  public String getChildrenName(int index) {
    if (super.getChildrenNames().size() <= index) {
      return null;
    }
    return super.getChildrenNames().get(index);
  }

  /**
   * Return a string representation of the name and the path of the Folder
   */
  public String toString() {
    return super.getName() + super.getName();
  }
}
