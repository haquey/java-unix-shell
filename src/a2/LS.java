package a2;

import java.awt.Component;
import java.awt.List;
import java.util.Vector;

public class LS implements CommandInterface {
  // The jFileSystem
  private FileSystem Manager;
  // The arguments that the user enters
  private String[] args;
  private String arg = "";
  private String fileOriginalArg = "";
  private String stringToOutput = "";
  private String stringToOutputTest = "";
  private Boolean recursive = false;
  private boolean invalid = false;

  /**
   * The constructor, taking ins a JFileSystem and a string array of arguments
   * 
   * @param fileManager - The JFilemMnager with all the directories and files
   * @param name - The argument(s)
   */
  public LS(JFileSystem fileManager, String[] name) {
    this.Manager = fileManager;
    this.args = name;
  }

  /**
   * Used to get the output of the command
   * 
   * @return stringToOutput
   */
  public String getStringToOutput() {
    return this.stringToOutputTest;
  }

  /**
   * Secondary constructor if no arguments are given
   * 
   * @param fileManager
   */
  public LS(JFileSystem fileManager) {
    this.Manager = fileManager;
    this.args = new String[1];
    // Setting a blank command
    this.args[0] = "";
  }

  /**
   * A method responsible for displaying the contents of the local directory
   * Prints each of the contents on a newline (as per A2a hand out)
   * 
   * @return contents - Returns a string containing the contents
   */
  public String executeNoArg() {
    String contents = "";
    // Getting the current folder
    Folder currFolder = (Folder) Manager.getObject(Manager.getCurrPath());
    // A vector representing all the names of the children directories to this
    // one
    Vector childNames;
    // If there are no children
    if (currFolder == null) {
      childNames = null;
    } else {
      // Getting the string vector of all the children stored in the folder
      childNames = currFolder.getChildrenNames();
    }
    // Sorts the child names in alphabetical order
    if (childNames != null) {
      java.util.Collections.sort(childNames);
    }
    int i = 0;
    if (childNames != null) {
      // Adding each of the children names to a new string
      while (i < childNames.size()) {
        contents = contents + "\n" + childNames.get(i);
        i++;
      }
    }
    return contents;
  }

  /**
   * A method responsible for displaying the contents of a directory or file at
   * the given path
   * 
   * @param arg - The absolute path
   * @return contents - A string representation of all the children
   */
  public String executeFullPath(String arg, boolean slashAtEnd) {
    String contents = "";
    // Checking if the path given is valid
    if (Manager.checkValidPath(arg)) {
      // Determining the type of object that has been specified
      if (Manager.getObject(arg).getClass().equals(Folder.class)) {
        // Getting the current folder
        Folder currFolder = (Folder) Manager.getObject(arg);
        // If the folder does not exist
        if (currFolder.equals(null)) {
          System.err.println("That was not a valid path.");
        } else {
          // All the children in the specified folder
          Vector childNames = currFolder.getChildrenNames();
          if (childNames != null) {
            // Sorting them alphabetically
            java.util.Collections.sort(childNames);
          }
          int i = 0;
          // Adding the child names to a string and formating it
          if (childNames != null) {
            while (i < currFolder.getChildren().size()) {
              contents = contents + "     " + childNames.get(i);
              i++;
            }
          }
          // Finishing up the formating
          if (!this.recursive) {
            contents = this.fileOriginalArg + ": " + contents + "\n";
          } else {
            contents = "\n" + currFolder.getPath() + ": " + contents + "\n";
          }
        }
      } else {
        // If the path specified is to a file
        // If there must not be a slash at the end of the file name
        if (slashAtEnd) {
          contents = this.fileOriginalArg + "\n";
        } else {
          System.err.println("That was not a valid path.");
        }
      }
    } else {
      // If an incorrect path is given
      System.err.println("That was not a valid path.");
    }
    return contents;
  }

  /**
   * Recursively goes through all children of dirrOrFile and displaying it
   * contents as it goes
   * 
   * @param childIndex - the index of the child currently being worked on
   * @param dirrOrFile - an Item
   * @return - A string representing the contents of the entire sub-tree (ie
   *         dirrOrFile and all its children)
   */
  public String recurseLS(int childIndex, Item dirrOrFile) {
    // If the end of the subtree is reached
    String result = "";
    if (dirrOrFile == null || dirrOrFile.getClass().equals(File.class)) {
      result = "";
    } else {
      // Getting all the children
      Vector<Item> allChildren = dirrOrFile.getChildren();
      // making sure it has children
      if (allChildren != null && allChildren.size() != 0) {
        // If the first child is being called
        if (childIndex == 0) {
          // The recursive call
          result = this.executeFullPath(((Folder) dirrOrFile).getPath(), true)
              + this.recurseLS(childIndex, allChildren.get(childIndex))
              + this.recurseLS(childIndex + 1, dirrOrFile);
        }
        // does the same for each child
        else if (childIndex < allChildren.size()) {
          // the recursive call
          result = this.recurseLS(0, allChildren.get(childIndex))
              + this.recurseLS(childIndex + 1, dirrOrFile);
        }
      }
      // If there are no children the contents must still be shown
      else {
        result = this.executeFullPath(((Folder) dirrOrFile).getPath(), true);
      }
    }

    return result;
  }

  /**
   * The method that will be called by ProQuery. Determines what kind of
   * argument the user has entered; Runs all the arguments that the user has
   * entered
   * 
   * @return
   */
  public String execute() {
    // Runs all elements in the string array
    for (int indexarg = 0; indexarg < this.args.length; indexarg++) {
      boolean slashAtEnd = false;
      this.arg = args[indexarg];
      this.fileOriginalArg = args[indexarg];
      // If the -r command is specified
      if (this.arg.equals("-r") || this.arg.equals("-R")) {
        indexarg++;
        // If there are path specified
        if (args.length != 1) {
          this.arg = args[indexarg];
          this.fileOriginalArg = args[indexarg];
        } else {
          this.arg = "";
          this.fileOriginalArg = "";
        }
        this.recursive = true;
      }
      this.stringToOutput = "";
      if (!arg.equals("")) {
        try {
          // Getting the absolute version of the path
          arg = this.Manager.getFullPath(arg);
        } catch (InvalidPath e) {
          System.err.println(e);
          this.stringToOutputTest += e.getMessage() + "\n";
          this.invalid = true;
        }
      }
      if (!this.invalid){
        // If a file is specified
        if (arg.contains(".txt")) {
          this.stringToOutput += arg;
        }
        // if the argument is blank
        else if (arg.equals("")) {
          slashAtEnd = true;
          if (!this.recursive) {
            this.stringToOutput += executeNoArg();
          } else {
            this.stringToOutput += recurseLS(0, this.Manager.getCurrFolder());
          }
        }
        // If there are arguments specified (ie: a path)
        else {
          if (!this.recursive) {
            this.stringToOutput += executeFullPath(arg, slashAtEnd);
          } else {
            this.stringToOutput += recurseLS(0, this.Manager.getObject(arg));
          }
        }
        // Using the output class to print the string
        if (!this.stringToOutput.endsWith("\n")) {
          this.stringToOutputTest += this.stringToOutput + "\n";
        } else {
          this.stringToOutputTest += this.stringToOutput;
        }
      }

    }
    return stringToOutputTest;
  }

  /**
   * A manual explaining how ls works
   * 
   * @return a string detailing how ls works
   */
  public String manual() {
    return "ls [PATH  …] - Print the contents of the specified files or\n"
        + "directories. If no path is given, print the contents of the\n"
        + "current file or directory.\n";
  }
}
