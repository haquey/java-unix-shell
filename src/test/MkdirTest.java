package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;


public class MkdirTest {
  private JFileSystem jFileSystem;
  private String[] fileName;
  private String[] fileNames;
  private Mkdir mkdir1;
  private CD cd;
  private ProQuery runCommand;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    runCommand = new ProQuery(this.jFileSystem);

    fileName = new String[1];
    fileNames = new String[3];
  }

  @Test
  public void testExecuteSingleLocal() {
    /*
     * Testing if the given directory is made inside of the JFileSystem
     * 
     * Expected output is that the path was made (True)
     */
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a"));
  }

  @Test
  public void testExecuteMultipleLocal() {
    /*
     * Testing creating multiple directories inside of the JFileSystem
     * 
     * Expected output is that all the directories are made
     */
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertTrue(
        jFileSystem.checkValidPath("/a") && jFileSystem.checkValidPath("/a1")
            && jFileSystem.checkValidPath("/a2"));
  }

  @Test
  public void testExecuteLocalAndAbsolute() {
    /*
     * Testing creating directories by giving relative paths
     * 
     * Expected output is that all the directories are made and are a path
     */
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "/a/a1";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "/a/a1/a2";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a/a1/a2"));
  }

  @Test
  public void testExecuteLocalInvalid() {
    /*
     * Testing creating a directory with an invalid special character
     * 
     * Expected output is that the directory is not made
     */
    fileName[0] = "a****dsda";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertFalse(jFileSystem.checkValidPath("/a****dsda"));
  }

  @Test
  public void testExecuteSpecialInvalid() {
    /*
     * Testing creating a directory with both an invalid and valid special
     * character
     * 
     * Expected output is that the directory isn't made
     */
    fileName[0] = "a_as&%dsda";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertFalse(jFileSystem.checkValidPath("/a_as&%dsda"));
  }

  @Test
  public void testExecuteValidSpecial() {
    /*
     * Testing creating a directory with a valid special character
     * 
     * Expected output is that the directories is made
     */
    fileName[0] = "a__________dsda";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a__________dsda"));
  }

  @Test
  public void testExecuteValidAndInvalid() {
    /*
     * Testing creating a mix of directories with some being valid names and
     * some being invalid names
     * 
     * Expected output is that the directories is made
     */
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "b*";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "x_y";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(
        jFileSystem.checkValidPath("/a") && jFileSystem.checkValidPath("/x_y"));
  }

  @Test
  public void testExecuteInsideRelativePath() {
    /*
     * Testing making a directory inside of a relative path
     * 
     * Expected output is that the directory was made and the full path exists
     */
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    cd = new CD(jFileSystem, fileName);
    cd.execute();
    fileName[0] = "a1";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a/a1"));
  }

  public void testExecuteTestingDepth() {
    /*
     * Testing making a directory inside of a relative path of depth 2
     * 
     * Expected output is that the directory was made and the full path exists
     */
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "a/a1";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "a";
    cd = new CD(jFileSystem, fileName);
    cd.execute();
    fileName[0] = "a1/a2";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a/a1/a2"));
  }

  @Test
  public void testMkdirRedirectFailsToMakeFile() {
    /*
     * Test to determine if redirection the output of mkdir command
     * (nonexistent) will create a file
     * 
     * Expected no file called newDirectory.txt should be in the root folder of
     * the jFileSystem
     */
    runCommand.sortQuery("cd a > newDirectory.txt");
    Item file = jFileSystem.getObject("/newDirectory.txt");
    assertTrue(file == null);
  }

  @Test
  public void testMkdirRedirectFailsToAppendFile() {
    /*
     * Test to determine if redirection the output of mkdir command
     * (nonexistent) will create a file
     * 
     * Expected no file called newDirectory.txt should be in the root folder of
     * the jFileSystem
     */
    runCommand.sortQuery("cd a >> newDirectory.txt");
    Item file = jFileSystem.getObject("/newDirectory.txt");
    assertTrue(file == null);
  }

}
