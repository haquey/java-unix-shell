package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class ManTest {

  private JFileSystem jFileSystem;
  private ProQuery runCommand;
  private String[] manual = {"man.txt"};
  private Cat concatenate;

  @Before
  public void setUp() throws Exception {}

  {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    this.runCommand = new ProQuery(this.jFileSystem);
    this.concatenate = new Cat(this.jFileSystem, manual);
  }

  @Test
  public void testManMan() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "man" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "Man" class
     */
    String[] commandName = {"man"};
    Man commandManual = new Man(jFileSystem, commandName);
    Man testManual = new Man(jFileSystem, commandName);
    commandManual.execute();

    assertEquals(testManual.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManLS() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "ls" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "LS" class
     */
    String[] commandName = {"ls"};
    Man commandManual = new Man(jFileSystem, commandName);
    LS testLS = new LS(jFileSystem);
    commandManual.execute();

    assertEquals(testLS.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManPWD() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "pwd" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "PWD" class
     */
    String[] commandName = {"pwd"};
    Man commandManual = new Man(jFileSystem, commandName);
    PWD testPWD = new PWD(jFileSystem);
    commandManual.execute();

    assertEquals(testPWD.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManPopD() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "popd" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "PopD" class
     */
    String[] commandName = {"popd"};
    Man commandManual = new Man(jFileSystem, commandName);
    PopD testPopD = new PopD(jFileSystem);
    commandManual.execute();

    assertEquals(testPopD.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManExit() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "exit" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "Exit" class
     */
    String[] commandName = {"exit"};
    Man commandManual = new Man(jFileSystem, commandName);
    Exit testExit = new Exit(jFileSystem);
    commandManual.execute();

    assertEquals(testExit.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManCD() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "cd" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "CD" class
     */
    String[] commandName = {"cd"};
    Man commandManual = new Man(jFileSystem, commandName);
    CD testCD = new CD(jFileSystem, commandName);
    commandManual.execute();

    assertEquals(testCD.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManCat() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "cat" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "Cat" class
     */
    String[] commandName = {"cat"};
    Man commandManual = new Man(jFileSystem, commandName);
    Cat testCat = new Cat(jFileSystem, commandName);
    commandManual.execute();

    assertEquals(testCat.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManEcho() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "echo" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "Echo" class
     */
    String[] commandName = {"echo"};
    Man commandManual = new Man(jFileSystem, commandName);
    Echo testEcho = new Echo(jFileSystem, commandName);
    commandManual.execute();

    assertEquals(testEcho.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManMkdir() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "mkdir" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "Mkdir" class
     */
    String[] commandName = {"mkdir"};
    Man commandManual = new Man(jFileSystem, commandName);
    Mkdir testMkdir = new Mkdir(jFileSystem, commandName);
    commandManual.execute();

    assertEquals(testMkdir.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManPushD() {
    /*
     * Test to determine if the Man object behaves appropriately with the string
     * array element "pushd" inserted as a parameter
     * 
     * Expected output should be the string output of the manual method of the
     * "PushD" class
     */
    String[] commandName = {"pushd"};
    Man commandManual = new Man(jFileSystem, commandName);
    PushD testPushD = new PushD(jFileSystem, commandName);
    commandManual.execute();

    assertEquals(testPushD.manual(), commandManual.getStringToOutput());
  }

  @Test
  public void testManMultipleCommands() {
    /*
     * Test to determine if the Man object behaves appropriately when multiple
     * string array elements are inserted as the parameter
     * 
     * Expected behaviour should consist of no string (null) being sent to the
     * output class to be printed
     */
    String[] commandName = {"pushd exit popd"};
    Man commandManual = new Man(jFileSystem, commandName);
    commandManual.execute();

    assertEquals(null, commandManual.getStringToOutput());
  }

  @Test
  public void testManInvalidCommand() {
    /*
     * Test to determine if the Man object behaves appropriately when an invalid
     * string array element is inserted as the parameter
     * 
     * Expected behaviour should consist of no string (null) being sent to the
     * output class to be printed
     */
    String[] commandName = {"thisisnotavalidparameter"};
    Man commandManual = new Man(jFileSystem, commandName);
    commandManual.execute();

    assertEquals(null, commandManual.getStringToOutput());
  }

  @Test
  public void testManRedirectOverwriteManual() {
    /*
     * Test to determine if redirecting output of Man command. Creates a new
     * file called path.txt, adds the output to the body of the new file.
     * 
     * Expected content of path.txt should be a string containing the path of
     * the newly created working directory ("/a")
     */
    PWD testPWD = new PWD(jFileSystem);
    runCommand.sortQuery("man pwd > man.txt");
    concatenate = new Cat(runCommand.getFileSystem(), manual);
    assertEquals(testPWD.manual(), concatenate.execute());
  }

  @Test
  public void testPWDRedirectAppendNewFolder() {
    /*
     * Test to determine if appending the output of pwd command in new folder to
     * an existing path.txt file works. Creates a new file called path.txt, adds
     * both outputs to the body of the new file.
     * 
     * Expected content of path.txt should be a string containing the path of
     * the newly created working directory twice separated by a new line
     * ("/a\n/a")
     */
    PWD testPWD = new PWD(jFileSystem);
    runCommand.sortQuery("man pwd > man.txt");
    PopD testPopD = new PopD(jFileSystem);
    runCommand.sortQuery("man popd >> man.txt");
    concatenate = new Cat(runCommand.getFileSystem(), manual);
    assertEquals(testPWD.manual() + "\n" + testPopD.manual(),
        concatenate.execute());
  }
}
