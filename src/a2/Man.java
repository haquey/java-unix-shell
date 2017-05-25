package a2;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class Man implements CommandInterface {
  // JFileSystemw which the ProQuery will pass in
  private JFileSystem jFileSystem;
  // The command whose documentation will be printed onto the shell
  private String[] commandKey;
  // The string that will be sent to the output class to be printed to shell
  private String stringToOutput;

  /**
   * Constructs a Man object whose purpose is to provide documentation for a
   * specified command class.
   * 
   * @param jFileSystem FileSystem that the commands may act upon.
   * @param commandKey Specified command whose documentation will be printed.
   */
  public Man(JFileSystem jFileSystem, String[] commandKey) {
    this.jFileSystem = jFileSystem;
    this.commandKey = commandKey;
  }

  /**
   * The purpose of this method is to allow user to print the documentation of a
   * command class that was specified during the construction of the Man object.
   */
  public String execute() {
    // Initialize a hashtable for commands without parameters
    Hashtable<String, String> commandNoPara = new Hashtable<String, String>();
    // Initialize a hashtable for commands with parameters
    Hashtable<String, String> commandWithPara = new Hashtable<String, String>();

    // Populate the hashtable for commands without parameters
    commandNoPara.put("ls", "a2.ListSegments");
    commandNoPara.put("exit", "a2.Exit");
    commandNoPara.put("pwd", "a2.PWD");
    commandNoPara.put("popd", "a2.PopD");

    // Populate the hastable for commands with parameters
    commandWithPara.put("cd", "a2.CD");
    commandWithPara.put("cat", "a2.Cat");
    commandWithPara.put("echo", "a2.Echo");
    commandWithPara.put("man", "a2.Man");
    commandWithPara.put("mkdir", "a2.Mkdir");
    commandWithPara.put("pushd", "a2.PushD");
    commandWithPara.put("grep", "a2.Grep");
    commandWithPara.put("curl", "a2.Curl");
    commandWithPara.put("cp", "a2.Copy");
    commandWithPara.put("mv", "a2.Move");
    commandWithPara.put("history", "a2.History");

    try {
      // If one command is given and it is a command which requires no
      // parameters
      if (commandKey.length == 1 && commandNoPara.containsKey(commandKey[0])) {
        // Acquire the commands class name in string form
        String commandName = commandNoPara.get(commandKey[0]);


        // Create an instance of the specified command whose constructor
        // requires only a JFileSystem
        CommandInterface commandInstance =
            (CommandInterface) Class.forName(commandName)
                .getConstructor(JFileSystem.class).newInstance(jFileSystem);
        // Call the manual method of the instance and send the acquired string
        // to the output to be printed
        stringToOutput = commandInstance.manual();




        // If one command is given and it is a command which requires parameters
      } else if (commandKey.length == 1
          && commandWithPara.containsKey(commandKey[0])) {

        // Acquire the commands class name in string form
        String commandName = commandWithPara.get(commandKey[0]);


        // Create an instance of the specified command whose constructor
        // requires a JFileSystem and a string array
        CommandInterface commandInstance =
            (CommandInterface) Class.forName(commandName)
                .getConstructor(JFileSystem.class, String[].class)
                .newInstance(jFileSystem, commandKey);
        // Call the manual method of the instance and send the acquired string
        // to the output to be printed
        stringToOutput = commandInstance.manual();



        // If numerous commands were specified, or an invalid command name
      } else {
        // Notify the user that they did not provide a valid command
        Output.printError();
      }
      // Catch any unwanted cases/exceptions
    } catch (ClassNotFoundException e) {
      Output.printError();

    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return stringToOutput;
  }

  /**
   * The purpose of this method is to return a string containing the
   * documentation/information regarding the "man" command.
   * 
   * @return String
   */
  public String manual() {
    return "man CMD - Displays documentation regarding the specified command\n";
  }


  /**
   * The purpose of this method is to return a string containing the entry that
   * was sent through the Output class to be printed on the shell.
   * 
   * @return stringToOutput String that is to be printed on the console/shell
   */
  public String getStringToOutput() {
    return stringToOutput;
  }
}
