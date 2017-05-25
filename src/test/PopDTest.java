package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.CD;
import a2.DirStack;
import a2.Folder;
import a2.JFileSystem;
import a2.Mkdir;
import a2.PopD;
import a2.PushD;


public class PopDTest {
  private JFileSystem jFileSystem;
  private PopD popD;
  private PushD pushD;
  private String[] location;
  private DirStack dirStack;
  private Mkdir mkdir1;
  private Mkdir mkdir2;
  private Mkdir mkdir3;
  private Mkdir mkdir4;
  private CD cd;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
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

    dirStack = new DirStack();
    location = new String[1];

  }

  @Test
  public void testExecute() {
    /*
     * Testing that the JFileSystems DirStack was changed after popping
     * 
     * Expected output is that the DirStack contains the same items as the mock
     * DirStack
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    popD = new PopD(jFileSystem);
    popD.execute();
    assertEquals(dirStack.getStack(), jFileSystem.getDirStack().getStack());
  }

  @Test
  public void testExecute1() {
    /*
     * Testing that the path was changed to the location that was popped
     * 
     * Expected output is that the current path is the same as the popped string
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    popD = new PopD(jFileSystem);
    popD.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute2() {
    /*
     * Testing if, after entering a different path, the path was changed to the
     * path that was popped
     * 
     * Expected output is that the current path is the same as the popped string
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    location[0] = "/a";
    cd = new CD(jFileSystem, location);
    cd.execute();
    popD = new PopD(jFileSystem);
    popD.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute3() {
    /*
     * Testing that after pushing multiple paths, the last entered path was the
     * one that was popped to as per LIFO
     * 
     * Expected output is that the current path is the same as the popped string
     * or the last pushed string
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    location[0] = "/a/a1/alternate";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    location[0] = "/a";
    cd = new CD(jFileSystem, location);
    cd.execute();
    popD = new PopD(jFileSystem);
    popD.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute4() {
    /*
     * Testing popping multiple lines and that the current path is the last path
     * popped
     * 
     * Expected output is that the current path is the same as the last popped
     * string
     */
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    location[0] = "/a";
    cd = new CD(jFileSystem, location);
    cd.execute();
    popD = new PopD(jFileSystem);
    popD.execute();
    popD = new PopD(jFileSystem);
    popD.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute5() {
    /*
     * Testing popping an empty DirStack
     * 
     * Expected output is that the current path didn't change
     */
    popD = new PopD(jFileSystem);
    popD.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testExecute6() {
    /*
     * Testing popping an empty DirStack making sure contents remain the same
     * 
     * Expected output is that the DirStack is the same as an empty DirStack
     */
    popD = new PopD(jFileSystem);
    popD.execute();
    assertEquals(dirStack.getStack(), jFileSystem.getDirStack().getStack());
  }


}
