package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;


public class CDTest {

  private JFileSystem jFileSystem;
  private CD cd1;
  private Mkdir mkdir1;
  private Mkdir mkdir2;
  private Mkdir mkdir3;
  private Mkdir mkdir4;
  private String[] location;
  private ProQuery runCommand;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    this.runCommand = new ProQuery(this.jFileSystem);

    String[] path = {"a", "b", "c"};
    String[] path2 = {"/a/a1"};
    String[] path3 = {"/a/a1/a2"};
    String[] path4 = {"/a/a1/alternate"};
    mkdir1 = new Mkdir(jFileSystem, path);
    mkdir1.execute();
    mkdir2 = new Mkdir(jFileSystem, path2);
    mkdir2.execute();
    mkdir3 = new Mkdir(jFileSystem, path3);
    mkdir3.execute();
    mkdir4 = new Mkdir(jFileSystem, path4);
    mkdir4.execute();

    location = new String[1];

  }

  @Test
  public void testExecute() {
    /*
     * test to determine if changing directory into a depth 1 relative file path
     * works
     * 
     * Expected output of the path should be the full path of the directory cd'd
     * into
     * 
     */
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute1() {
    /*
     * testing changing directory into a depth 1 full path
     * 
     * Expected output of the path should be the full path of the directory cd'd
     * into
     */
    location[0] = "/a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute2() {
    /*
     * Testing changing directory into a depth 2 full path
     * 
     * Expected output of the path should be the full path of the directory cd'd
     * into
     */
    location[0] = "/a/a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute3() {
    /*
     * Testing changing directory into a depth 3 full path
     * 
     * Expected output of the path should be the full path of the directory cd'd
     * into
     */
    location[0] = "/a/a1/a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute4() {
    /*
     * Testing changing directory into a relative path 3 times
     * 
     * Expected output of the path should be the full path of the last directory
     * entered
     */
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute5() {
    /*
     * Testing entering a directory 3 levels deep and returning back on level
     * (testing the cd .. command)
     * 
     * Expected output of the path should be the full path of the 2nd level
     * directory (/a/a1)
     */
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "..";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute6() {
    /*
     * Testing changing directory into a relative path that is 3 levels deep
     * 
     * Expected output of the path should be the full path of the directory
     * entered
     */
    location[0] = "a/a1/a2/";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute7() {
    /*
     * Testing the cd . command and making sure it doesn't change the file path
     * 
     * Expected output of the path should be the full path of the directory
     * entered at the beginning
     */
    location[0] = "a/a1/a2/";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = ".";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute8() {
    /*
     * Testing changing directory into a non existent path
     * 
     * Expected output is that the file path does not change
     */
    location[0] = "random";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute9() {
    /*
     * Testing changing directory into a non existent path with concatenated
     * commands
     * 
     * Expected output is that the file path does not change from the last
     * entered path
     */
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "alternate";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "..";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "random";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute10() {
    /*
     * Testing changing directory into a 3 levels deep full path
     * 
     * Expected output is the full path of the directory entered
     */
    location[0] = "/a/a1/a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute11() {
    /*
     * Testing concatenated commands in one line
     * 
     * Expected output is the full path of a2
     */
    location[0] = "/a/a1/../a1/./a2/";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute12() {
    /*
     * Testing repeated return commands for the cd
     * 
     * Expected output is that the file path is the root folder
     */
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "../../../../../../";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute13() {
    /*
     * Testing entering an invalid file path
     * 
     * Expected output is that the file path does not change
     */
    location[0] = "/a/a1//a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute14() {
    /*
     * Testing entering a location //
     * 
     * Expected output is that the file path stays at /
     */
    location[0] = "//";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute15() {
    /*
     * Testing entering the // location from a different file path
     * 
     * Expected output is that the file path changes to the root folder
     */
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "//";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute16() {
    /*
     * Testing entering the / location from a different file path
     * 
     * Expected output is that the file path changes to the root folder
     */
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "/";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute17() {
    /*
     * testing changing directory into a depth 1 full path with /'s on both
     * sides
     * 
     * Expected output of the path should be the full path of the directory cd'd
     * into
     */
    location[0] = "/a/";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a", jFileSystem.getCurrPath());
  }

  @Test
  public void testCDRedirectFailsToMakeFile() {
    /*
     * Test to determine if redirection the output of cd command (nonexistent)
     * will create a file
     * 
     * Expected no file called newDirectory.txt should be in the root folder of
     * the jFileSystem
     */
    runCommand.sortQuery("cd a > newDirectory.txt");
    Item file = jFileSystem.getObject("/newDirectory.txt");
    assertTrue(file == null);
  }

  @Test
  public void testCDRedirectFailsToAppendFile() {
    /*
     * Test to determine if redirection the output of cd command (nonexistent)
     * will create a file
     * 
     * Expected no file called newDirectory.txt should be in the root folder of
     * the jFileSystem
     */
    runCommand.sortQuery("cd a >> newDirectory.txt");
    Item file = jFileSystem.getObject("/newDirectory.txt");
    assertTrue(file == null);
  }

}
