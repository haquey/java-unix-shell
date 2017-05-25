package test;
import a2.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import a2.CD;
import a2.DirStack;
import a2.File;
import a2.Folder;
import a2.InvalidPath;
import a2.JFileSystem;
import a2.Mkdir;

public class JFileSystemTest {
  private JFileSystem jFileSystem;
  private String[] fileName;
  private String[] fileNames;
  private Mkdir mkdir1;
  private CD cd;
  private Folder newFolder;
  private Folder rootFolder;
  private DirStack testStack;


  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    this.rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    newFolder = new Folder("Test", "/Test");
    fileName = new String[1];
    fileNames = new String[3];
    this.testStack = new DirStack();
  }

  @Test
  public void testgetCurrPath() {
    /*
     * Testing if proper path is returned
     * 
     * Expected output: the current working directories path
     */
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testgetObject() {
    /*
     * Testing if proper object (either File or Folder or null) is returned
     * 
     * Expected output: The path of the retrieved object
     */
    fileNames = new String[5];
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    fileNames[3] = "/a/Test1";
    fileNames[4] = "a1/Test2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertEquals("/a1/Test2",
        ((Folder) jFileSystem.getObject("/a1/Test2")).getPath());
  }

  @Test
  public void testgetObject2() {
    /*
     * Testing another case for get object with a LOCAL name instead
     * 
     * Expected output: the name of the retrieved object
     */
    fileNames = new String[5];
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    fileNames[3] = "/a/Test1";
    fileNames[4] = "a1/Test2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertEquals("a", ((Folder) jFileSystem.getObject("a")).getName());
  }

  @Test
  public void testgetObject4() {
    /*
     * Testing another case for get object with a FAIL case
     * 
     * Expected output: A null
     */
    fileNames = new String[5];
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    fileNames[3] = "/a/Test1";
    fileNames[4] = "a1/Test2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertEquals(null, ((Folder) jFileSystem.getObject("FAIL_CASE")));
  }

  @Test
  public void testSetFullPath() {
    /*
     * Seeing if the path is update properly
     * 
     * Expected output: The updated path as the current path
     */
    jFileSystem.setFullPath("/testPath");
    assertEquals("/testPath", jFileSystem.getCurrPath());
  }

  @Test
  public void testadd() {
    /*
     * Seeing if the folder is added properly
     * 
     * Expected output: The path of the added folder in JFileSystem
     */
    jFileSystem.add(newFolder);
    assertEquals("/Test", jFileSystem.getFullPaths().get(1));
  }

  @Test
  public void testaddFullPath() {
    /*
     * Testing if the path is added properly
     * 
     * Expected output: The path of the added folder in JFileSystem
     */
    jFileSystem.addFullPath(newFolder.getPath());
    assertEquals("/Test", jFileSystem.getFullPaths().get(1));
  }

  @Test
  public void testCheckValidPath() {
    /*
     * Seeing if it can confirm a valid path exists
     * 
     * Expected output: a True boolean
     */
    jFileSystem.addFullPath(newFolder.getPath());
    assertTrue(jFileSystem.checkValidPath("/Test"));
  }

  @Test
  public void testCheckValidPath1() {
    /*
     * Seeing if it can confirm a path does not exists
     * 
     * Expected output: a False boolean
     */
    jFileSystem.addFullPath(newFolder.getPath());
    assertFalse(jFileSystem.checkValidPath("/FAIL_CASE"));
  }

  @Test
  public void testSetCurrFolder() {
    /*
     * Seeing if the curr folder is set properly
     * 
     * Expected output: the path of the new current folder
     */
    jFileSystem.setCurrFolder(newFolder);
    assertEquals("/Test", jFileSystem.getCurrFolder().getPath());
  }

  @Test
  public void testsetRootFolder() {
    // Was set in the set up
    /*
     * Determining if root was set up properly
     * 
     * Expected output: The root folder
     */
    assertEquals(this.rootFolder, jFileSystem.getRootFolder());
  }

  @Test
  public void testgetRootFolder() {
    /*
     * Seeing if the root folder can be properly retrieved
     * 
     * Expected output: The root folder
     */
    assertEquals(this.rootFolder, jFileSystem.getRootFolder());
  }

  @Test
  public void testsetDirStack() {
    /*
     * Testing if the dirstack can be properly set
     * 
     * Expected output: The new DirStack
     */
    jFileSystem.setDirStack(testStack);
    assertEquals(testStack, jFileSystem.getDirStack());
  }

  @Test
  public void testgetDirStack() {
    /*
     * Testing whether the current dirstack can be retrieved
     * 
     * Expected output: The newTestStack
     */
    DirStack newTestStack = new DirStack();
    jFileSystem.setDirStack(newTestStack);
    assertEquals(newTestStack, jFileSystem.getDirStack());
  }
  
  @Test
  public void testgetFullPath(){
    /*
     * Testing whether the getFullPath function works
     * 
     * Expected output: The corresponding absolute path
     */
    String absolute = "";
    this.jFileSystem.setFullPath("/");
    this.jFileSystem.addFullPath("/a/a1/a2");
    try {
      absolute = jFileSystem.getFullPath("/a/a1/../a1/./a2");
    } catch (InvalidPath e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals("/a/a1/a2", absolute);
  }
  @Test
  public void testgetFullPathFail(){
    /*
     * Testing whether the getFullPath function works
     * 
     * Expected output: The corresponding absolute path
     */
    String absolute = "";
    boolean failed = false;
    this.jFileSystem.setFullPath("/");
    this.jFileSystem.addFullPath("/a/a1/a2");
    try {
      absolute = jFileSystem.getFullPath("/a/a1/../a1/./a2/FAIL");
    } catch (InvalidPath e) {
      // TODO Auto-generated catch block
      failed = true;
    }

    assertTrue(failed);
  
  }
}
