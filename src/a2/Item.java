package a2;

import java.util.Vector;

public class Item {
  // The children of this item; Can either be Folders or Files
  private Vector<Item> children;
  // The name of all the children in this item
  private Vector<String> childrenNames;
  // The name of this item
  private String name;
  // The absolute path of the item
  private String path;

  /**
   * The constructor which creates an item
   * 
   * @param name - The name of the item
   * @param path - the absolute path of the item
   * @param children - The children associated with this item (initially null)
   * @param childrenName - The names of the children associated with this item
   *        (initially null)
   */
  public Item(String name, String path, Vector children,
      Vector<String> childrenName) {
    this.name = name;
    this.path = path;
    this.children = children;
    this.childrenNames = childrenName;
  }

  /**
   * Returns the absolute path for this file
   * 
   * @return path - an absolute path
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets the path for this Item (since things like Move class can change it)
   * 
   * @param path - an absolute path
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Returns all children of this item in the form of a <Item> vector
   * 
   * @return children - An <item> vector
   */
  public Vector<Item> getChildren() {
    return children;
  }

  /**
   * Returns all children names of this item in the form of a <String> vector
   * 
   * @return childrenNames - An <String> vector
   */
  public Vector<String> getChildrenNames() {
    return childrenNames;
  }

  /**
   * Returns the name of this Item
   * 
   * @return name - the name of this Item
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name for this Item (since things like Move can rename things)
   * 
   * @param name - the new name for this
   */
  public void setName(String name) {
    this.name = name;
  }
}
