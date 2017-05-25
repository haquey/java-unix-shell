package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class HistoryTest {

  private JFileSystem jFileSystem;
  private Vector<String> testVecStr;
  private List<String> testListStr;
  private History commandHistory;
  private ProQuery runCommand;
  private String[] history = {"history.txt"};
  private Cat concatenate;

  @Before
  public void setUp() throws Exception {}

  {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    this.testVecStr = new Vector<String>();
    this.testListStr = new Vector<String>();
    this.commandHistory = new History();
    this.runCommand = new ProQuery(this.jFileSystem);
    this.concatenate = new Cat(this.jFileSystem, history);
  }

  @Test
  public void testHistoryOneCommandShowAll() {
    /*
     * Test to determine if printing all history with only one command given
     * works
     * 
     * Expected behaviour should be the History class sending a Vector[String]
     * to the output class with each element formatted appropriately
     */
    testVecStr.add("1. mkdir a b c");
    commandHistory.addInput("mkdir a b c");
    commandHistory.execute();

    assertEquals(testVecStr, commandHistory.getList());
  }

  @Test
  public void testHistoryMultipleCommandsShowAll() {

    /*
     * Test to determine if printing all history with multiple commands given
     * works
     * 
     * Expected behaviour should be the History class sending a Vector<String>
     * to the output class with each element formatted appropriately
     */
    testVecStr.add("1. mkdir a b c");
    testVecStr.add("2. cd c");
    testVecStr.add("3. pwd");
    commandHistory.addInput("mkdir a b c");
    commandHistory.addInput("cd c");
    commandHistory.addInput("pwd");
    commandHistory.execute();

    assertEquals(testVecStr, commandHistory.getList());
  }

  @Test
  public void testHistoryMultipleInvalidCommandsShowAll() {
    /*
     * Test to determine if printing all history with multiple invalid commands
     * given works
     * 
     * Expected behaviour should be the History class sending a Vector<String>
     * to the output class with each element formatted appropriately
     */
    testVecStr.add("1. random");
    testVecStr.add("2. thisisinvalid");
    testVecStr.add("3. apples");
    commandHistory.addInput("random");
    commandHistory.addInput("thisisinvalid");
    commandHistory.addInput("apples");
    commandHistory.execute();

    assertEquals(testVecStr, commandHistory.getList());
  }

  @Test
  public void testHistoryOneCommandShowPastOne() {
    /*
     * Test to determine if printing history of past one command with one
     * command given works
     * 
     * Expected behaviour should be the History class sending a List<String> to
     * the output class with each element formatted appropriately
     */
    testListStr.add("1. mkdir a b c");
    commandHistory.addInput("mkdir a b c");
    String[] numPast = {"1"};
    commandHistory.execute(numPast);

    assertEquals(testListStr, commandHistory.getSubList());
  }

  @Test
  public void testHistoryOneCommandShowPastTwo() {
    /*
     * Test to determine if printing history of past two commands with one
     * command given works
     * 
     * Expected behaviour should be the History class sending a List<String> to
     * the output class with each element formatted appropriately (should behave
     * the same as printing history of past one command)
     */
    testListStr.add("1. mkdir a b c");
    commandHistory.addInput("mkdir a b c");
    String[] numPast = {"2"};
    commandHistory.execute(numPast);

    assertEquals(testListStr, commandHistory.getSubList());
  }

  @Test
  public void testHistoryFiveCommandShowPastThree() {
    /*
     * Test to determine if printing history of past three commands with five
     * command given (invalid or valid) works
     * 
     * Expected behaviour should be the History class sending a List<String> to
     * the output class with each element formatted appropriately
     */
    testListStr.add("3. cd b");
    testListStr.add("4. fourthentry");
    testListStr.add("5. pwd");

    commandHistory.addInput("mkdir a b c");
    commandHistory.addInput("thisisatest");
    commandHistory.addInput("cd b");
    commandHistory.addInput("fourthentry");
    commandHistory.addInput("pwd");

    String[] numPast = {"3"};
    commandHistory.execute(numPast);

    assertEquals(testListStr, commandHistory.getSubList());
  }

  @Test
  public void testHistoryRedirectOneCommandShowAll() {
    /*
     * Test to determine if redirecting output of history command after using
     * mkdir command. Creates a new file called history.txt, adds the output to
     * the body of the new file.
     * 
     * Expected content of history.txt should be a string of the commands that
     * were just used
     */
    runCommand.getHistory().addInput("mkdir a b c");
    runCommand.sortQuery("history > history.txt");
    concatenate = new Cat(runCommand.getFileSystem(), history);
    assertEquals("1. mkdir a b c\n2. history > history.txt\n",
        concatenate.execute());
  }

  @Test
  public void testHistoryRedirectAppendOneCommandShowAll() {
    /*
     * Test to determine if if appending the outputof history command after
     * using mkdir command and cd command. Creates a new file called
     * history.txt, adds both outputs to the body of the new file.
     * 
     * Expected content of history.txt should be a string of the commands that
     * were just used
     */
    runCommand.getHistory().addInput("mkdir a b c");
    runCommand.sortQuery("history > history.txt");
    runCommand.getHistory().addInput("cd a");
    runCommand.sortQuery("history >> history.txt");
    concatenate = new Cat(runCommand.getFileSystem(), history);
    assertEquals(
        "1. mkdir a b c\n2. history > history.txt\n\n1. mkdir a b c\n"
            + "2. history > history.txt\n3. cd a\n4. history >> history.txt\n",
        concatenate.execute());
  }
}
