package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class CopyTest {
  // All of the arguments
  private String[] a;
  private JFileSystem jFileSystem;

  @Before
  public void setUp() throws Exception {

    jFileSystem = new JFileSystem();
    Folder rootfold = new Folder("/", "/");
    jFileSystem.setRoot(rootfold);

    String input = "";
    a = new String[2];
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
    a[0] = "x";
    cd = new CD(jFileSystem, a);
    cd.execute();
    a = new String[1];
    System.out.println(jFileSystem.getCurrPath());
    a[0] = "../v/../../././../qqq/./";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "/";
    cd = new CD(jFileSystem, a);
    a[0] = "/a/e/x/TESTCASE";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a[0] = "/a/e/z/TEST";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();


    Copy c = new Copy(jFileSystem, a);
    c.execute();

  }

  @Test
  public void testExecuteCopy() {
    /*
     * Testing to see if the object are copied properly
     * 
     * Expected output: a copy of the contents of /a in /b
     */
    a = new String[2];
    a[0] = "/a";
    a[1] = "/b";

    Copy c = new Copy(jFileSystem, a);
    c.execute();
    a = new String[] {"-r", "/b"};
    LS ls = new LS(this.jFileSystem, a);
    assertEquals(
        "\n/b:      e     f     q\n\n/b/e:      v     x   "
            + "  z\n\n/b/e/x:      TESTCASE\n\n/b/e/x/TESTCASE: \n\n/b/e/z:    "
            + "  TEST\n\n/b/e/z/TEST: \n\n/b/e/v: \n\n/b/f: \n\n/b/q: \n",
        ls.execute());
  }

  @Test
  public void testExecuteCopyParent() {
    /*
     * Testing to see if an error is given if the user tries to copy a parent
     * into its child
     * 
     * Expected output: an error
     */
    a = new String[2];
    a[0] = "/a";
    a[1] = "/a/e";

    Copy c = new Copy(jFileSystem, a);
    c.execute();

    assertEquals("Cannot copy a parent folder into it's child",
        c.getStringToOutput());
  }

  @Test
  public void testExecuteCopyParentFile() {
    /*
     * Testing to see if an error is given if the user tries to copy a parent
     * into its child
     * 
     * Expected output: an error
     */
    a = new String[2];
    a[0] = "/a";
    a[1] = "TESt.txt";

    Copy c = new Copy(jFileSystem, a);
    c.execute();
    assertEquals("Invalid newpath given",
        c.getStringToOutput());
  }
  @Test
  public void testExecuteDirInFile() {
    /*
     * Testing to see if an error is given if the user tries to copy a parent
     * into its child
     * 
     * Expected output: an error
     */
    a = new String[2];
    a[0] = "/b";
    a[1] = "TESt.txt";

    Copy c = new Copy(jFileSystem, a);
    c.execute();

    assertEquals("Invalid newpath given", c.getStringToOutput());
  }
}
