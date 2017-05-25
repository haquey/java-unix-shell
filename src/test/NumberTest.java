package test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import a2.CD;
import a2.FileSystem;
import a2.Folder;
import a2.History;
import a2.JFileSystem;
import a2.Mkdir;
import a2.ProQuery;

public class NumberTest {
  private JFileSystem jFileSystem;
  private ProQuery runCommand;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    this.runCommand = new ProQuery(this.jFileSystem);
  }

  @Test
  public void testExecuteCDHistory() 
  {
    /*
     * Testing if !number can correctly use the previous history command
     * to cd back
     * 
     * Expected output is that the current path of the filesystem is that path
     * is desired directory
     */
    runCommand.sortQuery("mkdir a b c");
    runCommand.sortQuery("cd a");
    runCommand.sortQuery("cd ..");
    runCommand.sortQuery("!2");
    JFileSystem fs = runCommand.getFileSystem();
    assertEquals(fs.getCurrPath(), "/a");
  }
  
  @Test
  public void testExecuteHistoryChange() 
  {
    /*
     * Testing if !number does not go into the history and that the command
     * it did went to the history
     * 
     * Expected output the history is the command !number executed
     */
    runCommand.sortQuery("history");
    runCommand.sortQuery("history");
    runCommand.sortQuery("!1");
    Vector<String> historyList = runCommand.getHistory().getList();
    Vector<String> comparison = new Vector<String>();
    comparison.add("1. history");
    comparison.add("2. history");
    comparison.add("3. history");
    assertEquals(comparison.get(comparison.size()-1),
        historyList.get(historyList.size()-1));
  }
  
  @Test
  public void testExecuteInvalidInput() 
  {
    /*
     * Testing if !number does nothing if the input is invalid
     * 
     * Expected output is that nothing happens
     */
    runCommand.sortQuery("mkdir a b c");
    runCommand.sortQuery("cd a");
    runCommand.sortQuery("cd ..");
    runCommand.sortQuery("!asdas");
    JFileSystem fs = runCommand.getFileSystem();
    assertEquals(fs.getCurrPath(), "/");
  }
  
  @Test
  public void testExecuteInvalidNumber() 
  {
    /*
     * Testing if !number does nothing if the input is invalid
     * 
     * Expected output is that nothing happens
     */
    runCommand.sortQuery("mkdir a b c");
    runCommand.sortQuery("cd a");
    runCommand.sortQuery("cd ..");
    runCommand.sortQuery("!0");
    JFileSystem fs = runCommand.getFileSystem();
    assertEquals(fs.getCurrPath(), "/");
  }
  
  @Test
  public void testExecuteInvalidNumberOverLimit() 
  {
    /*
     * Testing if !number does nothing if the input is invalid
     * 
     * Expected output is that nothing happens
     */
    runCommand.sortQuery("mkdir a b c");
    runCommand.sortQuery("cd a");
    runCommand.sortQuery("cd ..");
    runCommand.sortQuery("!100");
    JFileSystem fs = runCommand.getFileSystem();
    assertEquals(fs.getCurrPath(), "/");
  }
  
  

}
