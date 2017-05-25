package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.CD;
import a2.DirStack;
import a2.Folder;
import a2.JFileSystem;
import a2.Mkdir;
import a2.PushD;

public class PushDTest {
  private JFileSystem jFileSystem;
  private PushD pushD;
  private String[] location;
  private DirStack dirStack;
  private Mkdir mkdir1;
  private Mkdir mkdir2;
  private Mkdir mkdir3;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    String[] path = {"a", "b", "c"};
    String[] path2 = {"/a/a1"};
    String[] path3 = {"/a/a1/a2"};
    mkdir1 = new Mkdir(jFileSystem, path);
    mkdir1.execute();
    mkdir2 = new Mkdir(jFileSystem, path2);
    mkdir2.execute();
    mkdir3 = new Mkdir(jFileSystem, path3);
    mkdir3.execute();

    location = new String[1];
    dirStack = new DirStack();
  }

  @Test
  public void testExecute() {
    /*
     * Testing that the DirStack is changed after pushing a value in
     * 
     * Expected output is that the jFileSystem's DirStack contains the same
     * values as the mock DirStack
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    dirStack.pushD("/");
    assertEquals(dirStack.getStack(), jFileSystem.getDirStack().getStack());

  }

  @Test
  public void testExecute1() {
    /*
     * Testing that the DirStack is changed after pushing multiple values in
     * 
     * Expected output is that the jFileSystem's DirStack contains the same
     * values as the mock DirStack
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    location[0] = "/a/a1";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    location[0] = "/";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    dirStack.pushD("/");
    dirStack.pushD("/a/a1/a2");
    dirStack.pushD("/a/a1");
    assertEquals(dirStack.getStack(), jFileSystem.getDirStack().getStack());
  }

  @Test
  public void testExecute2() {
    /*
     * Testing that the path is changed after pushing a path
     * 
     * Expected output is that the jFileSystem's path is changed to the pushed
     * path
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    dirStack.pushD("/a/a1/a2");
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute3() {
    /*
     * Testing pushing into a path that the user is already inside
     * 
     * Expected output is that the jFileSystem's path is changed to the pushed
     * path (not changed at all)
     */
    String[] filePath = {"/a/a1/a2"};
    CD cd = new CD(jFileSystem, filePath);
    cd.execute();
    pushD = new PushD(jFileSystem, filePath);
    pushD.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute4() {
    /*
     * Testing pushing into a path from a different path inside the full path
     * 
     * Expected output is that the jFileSystem's path is changed to the pushed
     * path
     */
    String[] filePath = {"/a/a1/a2"};
    CD cd = new CD(jFileSystem, filePath);
    cd.execute();
    location[0] = "/a/a1";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    assertEquals("/a/a1", jFileSystem.getCurrPath());
  }

  public void testExecute5() {
    /*
     * Testing pushing into a path from a different path inside the full path
     * going down two levels
     * 
     * Expected output is that the jFileSystem's path is changed to the pushed
     * path
     */
    String[] filePath = {"/a/a1/a2"};
    CD cd = new CD(jFileSystem, filePath);
    cd.execute();
    location[0] = "/a";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    assertEquals("/a", jFileSystem.getCurrPath());
  }

  public void testExecute6() {
    /*
     * Testing pushing into a path from a different path inside the full path
     * going down three levels
     * 
     * Expected output is that the jFileSystem's path is changed to the pushed
     * path
     */
    String[] filePath = {"/a/a1/a2"};
    CD cd = new CD(jFileSystem, filePath);
    cd.execute();
    location[0] = "/";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute7() {
    /*
     * Testing pushing an input that contains no / at the beginning
     * 
     * Expected output is that the jFileSystem's path is changed to the pushed
     * path
     */
    String[] filePath = {"a/a1/a2"};
    pushD = new PushD(jFileSystem, filePath);
    pushD.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute8() {
    /*
     * Testing that the DirStack is not changed after giving the command to push
     * an invalid file
     * 
     * Expected output is that the jFileSystem's DirStack contains the same
     * values as the mock DirStack
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    location[0] = "/a/a1";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    location[0] = "asdfghjytr";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    dirStack.pushD("/");
    dirStack.pushD("/a/a1/a2");
    assertEquals(dirStack.getStack(), jFileSystem.getDirStack().getStack());
  }



}
