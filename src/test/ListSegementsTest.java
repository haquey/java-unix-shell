package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.CD;
import a2.Folder;
import a2.JFileSystem;
import a2.LS;
import a2.Mkdir;

public class ListSegementsTest {
  private JFileSystem jFileSystem;
  private String[] fileArg;
  private String[] fileArgs;
  private Mkdir mkdir1;
  private CD cd;
  private LS ls;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    fileArg = new String[1];
    this.fileArgs = new String[3];
    this.ls = new LS(jFileSystem);
    String[] a = new String[2];
    a[0] = "a";
    a[1] = "b";
    Mkdir mkdir = new Mkdir(jFileSystem, a);
    a = new String[1];
    a[0] = "a";
    CD cd = new CD(jFileSystem, a);
    mkdir.execute();
    cd.execute();

    a = new String[3];
    a[0] = "e";
    a[1] = "f";
    a[2] = "q";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "e";
    cd = new CD(jFileSystem, a);
    cd.execute();
    a = new String[3];
    a[0] = "x";
    a[1] = "z";
    a[2] = "v";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "/a/e/v/TestCase";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "x";
    cd = new CD(jFileSystem, a);
    cd.execute();
  }

  @Test
  public void testExecuteEmpty() {
    /*
     * Testing a basic no argument call to ls; prints contents of current dir
     * 
     * Expected output: a \n since there is nothing in the current working dir
     */
    this.ls = new LS(jFileSystem);
    ls.execute();
    assertEquals("\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecuteDoubleDots() {
    /*
     * Testing the "..", which should return the contents of the parent dir
     * 
     * Expected output: The contents of the current working dir
     */
    this.fileArg[0] = "..";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("..:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecuteLocalPathDoubleDots() {
    /*
     * Testing a local path with both ".." and a name specification
     * 
     * Expected output: The contents of the specified directory
     */
    this.fileArg[0] = "../../e/";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("../../e/:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecuteSingleDots() {
    /*
     * Testing to see if proper out put is given when a "." is added (which does
     * nothing)
     * 
     * Expected output: The contents of the specified directory
     */
    this.fileArg[0] = ".././../e/";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals(".././../e/:      v     x     z\n",
        this.ls.getStringToOutput());
  }

  @Test
  public void testExecuteDoubleDots2() {
    /*
     * Testing a local path with names in between ".."'s
     * 
     * Expected output: The contents of the specified directory
     */
    this.fileArg[0] = ".././../e/../../b";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals(".././../e/../../b: \n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecuteAbsolutePath() {
    /*
     * Testing an absolute file path
     * 
     * Expected output: The contents of the specified directory
     */
    this.fileArg[0] = "/a/e/";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("/a/e/:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecuteSlashAtEnd() {
    /*
     * Testing a local with a "/" at the end
     * 
     * Expected output: The contents of the specified directory
     */
    String[] a = new String[1];
    a[0] = "/a";
    cd = new CD(jFileSystem, a);
    cd.execute();
    this.fileArg[0] = "e/";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("e/:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecuteInvalidArg() {
    /*
     * A failure case
     * 
     * Expected output: An error message for an invalid path
     */
    this.fileArg[0] = "FAIL_ARG";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("/a/e/x/FAIL_ARG is not a valid path\n",
        this.ls.getStringToOutput());
  }

  @Test
  public void testExecuteMultipleArgs() {
    /*
     * Testing multiple arguments at once
     * 
     * Expected output: The contents of the specified directories
     */
    this.fileArgs[0] = "/a/e/";
    this.fileArgs[1] = "/";
    this.fileArgs[2] = "/a/e/v/";
    this.ls = new LS(jFileSystem, this.fileArgs);
    ls.execute();
    boolean Equal = this.ls.getStringToOutput()
        .equals("/a/e/:      v     x     z\n/:      a     b\n/a/e/v/:      "
            + "TestCase\n");
    assertTrue(Equal);
  }

  @Test
  public void testExecuteRecursive() {
    /*
     * Testing the -r function on one argument
     * 
     * Expected output: The contents of the entire file system
     */
    this.fileArgs = new String[2];
    this.fileArgs[0] = "-r";
    this.fileArgs[1] = "/";
    this.ls = new LS(jFileSystem, this.fileArgs);
    String output = ls.execute();

    assertEquals(
        "\n/:      a     b\n\n/a:      e     f  "
            + "   q\n\n/a/e:      v     x     z\n\n/a/e/x: \n\n/a/e/z:"
            + " \n\n/a/e/v:  "
            + "    TestCase\n\n/a/e/v/TestCase: \n\n/a/f: \n\n/a/q: \n\n/b: \n",
        output);
  }

  @Test
  public void testExecuteRecursiveMultiple() {
    /*
     * Testing the -r function on multiple argument
     * 
     * Expected output: The contents of the args and their children
     */
    this.fileArgs = new String[3];
    this.fileArgs[0] = "-R";
    this.fileArgs[1] = "/";
    this.fileArgs[2] = "/b";
    this.ls = new LS(jFileSystem, this.fileArgs);
    String output = ls.execute();

    assertEquals("\n/:      a     b\n\n/a:      e     f     q\n\n/a/e:     "
        + " v     x     z\n\n/a/e/x: \n\n/a/e/z: \n\n/a/e/v:     "
        + " TestCase\n\n/a/e/v/TestCase: \n\n/a/f: \n\n/a/q: \n\n/b:"
        + " \n\n/b: \n", output);
  }
}
