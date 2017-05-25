package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.DirStack;
import a2.Folder;
import a2.JFileSystem;
import a2.Mkdir;
import a2.ProQuery;


public class ProQueryTest {
  private JFileSystem jFileSystem;
  private ProQuery process;
  private String query;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    process = new ProQuery(jFileSystem);
  }

  @Test
  public void testSortQueryEmpty() {
    /*
     * Testing entering a correct command no parameters
     * 
     * Expected output is that the command functioned as wanted, in this case
     * enters the correct path popped from the DirStack
     */
    String[] location = new String[1];
    location[0] = "mockfolder";
    Mkdir mkdir = new Mkdir(jFileSystem, location);
    mkdir.execute();
    DirStack dirStack = new DirStack();
    dirStack.pushD("/mockfolder");
    jFileSystem.setDirStack(dirStack);
    query = "popd";
    process.sortQuery(query);
    assertEquals("/mockfolder", process.getFileSystem().getCurrPath());
  }

  @Test
  public void testSortQuery() {
    /*
     * Testing entering a correct command with one parameter
     * 
     * Expected output is that the command functioned as wanted (in this case
     * made a directory)
     */
    query = "mkdir bananas";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/bananas"));
  }

  @Test
  public void testSortQuery1() {
    /*
     * Testing entering a correct command with multiple parameters
     * 
     * Expected output is that the command functioned as wanted (in this case
     * made the directories)
     */
    query = "mkdir bananas waka tests";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/tests"));
  }

  @Test
  public void testSortQuery2() {
    /*
     * Testing entering a correct command with multiple parameters then testing
     * a related command to make sure it affected the JFileSystem
     * 
     * Expected output is that the command functioned as wanted (in this case
     * made the directories) and changed the JFileSystem
     */
    query = "mkdir bananas waka tests";
    process.sortQuery(query);
    query = "mkdir /bananas/potassium";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/bananas/potassium"));
  }

  @Test
  public void testSortQuery3() {
    /*
     * Testing entering multiple correct commands with varying parameters then
     * testing related commands to make sure it affected the JFileSystem
     * 
     * Expected output is that the commands functioned as wanted (in this case
     * made the directories) and changed the JFileSystem
     */
    query = "mkdir a b c";
    process.sortQuery(query);
    query = "mkdir /a/a1";
    process.sortQuery(query);
    query = "cd /a/a1";
    process.sortQuery(query);
    assertEquals("/a/a1", process.getFileSystem().getCurrPath());
  }

  @Test
  public void testSortQuery4() {
    /*
     * Testing entering a command which has invalid params for the command
     * itself
     * 
     * Expected output is that the command does not do anything - command
     * functions as wanted
     */
    query = "mkdir *******";
    process.sortQuery(query);
    assertFalse(process.getFileSystem().checkValidPath("/*******"));
  }

  @Test
  public void testSortQuery5() {
    /*
     * Testing entering an invalid command
     * 
     * Expected output is that nothing happens and nothing is made
     */
    query = "mkdont banana";
    process.sortQuery(query);
    assertFalse(process.getFileSystem().checkValidPath("banana"));
  }

  @Test
  public void testSortQuery6() {
    /*
     * Testing entering correct commands, then an incorrect command
     * 
     * Expected output is that the correct commands function as wanted and that
     * the incorrect commands do not
     */
    query = "mkdir bananas waka tests";
    process.sortQuery(query);
    query = "mkdir /bananas/potassium";
    process.sortQuery(query);
    query = "cf /bananas/potassium";
    assertTrue("/" == process.getFileSystem().getCurrPath()
        && process.getFileSystem().checkValidPath("/bananas/potassium"));
  }

  @Test
  public void testSortQuery7() {
    /*
     * Testing if the class splits the commands properly with one parameter
     * 
     * Expected output is that the command is done and it changes the
     * JFileSystem
     */
    query = "mkdir                    bananas";
    process.sortQuery(query);
    query = "mkdir /bananas/potassium";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/bananas/potassium"));
  }

  @Test
  public void testSortQuery8() {
    /*
     * Testing if the class splits the commands properly with multiple
     * parameters
     * 
     * Expected output is that the command is done and it changes the
     * JFileSystem
     */
    query = "mkdir                    bananas           test";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/test"));
  }

  @Test
  public void testSortQuery9() {
    /*
     * Testing if the class splits the commands properly with varying parameters
     * and spaces in different areas
     * 
     * Expected output is that the commands are done and it changes the
     * JFileSystem
     */
    query = "mkdir a              b c";
    process.sortQuery(query);
    query = "mkdir /a/a1     ";
    process.sortQuery(query);
    query = "cd          /a/a1";
    process.sortQuery(query);
    assertEquals("/a/a1", process.getFileSystem().getCurrPath());
  }


}
