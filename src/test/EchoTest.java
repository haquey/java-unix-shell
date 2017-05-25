package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class EchoTest {
  private JFileSystem jFileSystem;
  private Echo echo;
  private String[] parameters;
  private ProQuery runCommand;
  private Cat concatenate;
  private String[] strings = {"output.txt"};


  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    this.runCommand = new ProQuery(this.jFileSystem);
    this.concatenate = new Cat(this.jFileSystem, strings);
  }

  // print a string
  @Test
  public void testEchoPrintSingleString() {
    /*
     * test to using Echo to print a string
     * 
     * 
     * Expected output string should match the String given in the parameters of
     * the constructor
     */
    parameters = new String[1];
    parameters[0] = "\"Passed.\"";
    echo = new Echo(jFileSystem, parameters);
    assertEquals(echo.execute(), "Passed.");
  }

  @Test
  public void testEchoPrintMessageString() {
    /*
     * test to using Echo to print a message
     * 
     * 
     * Expected output string should match the String given in the parameters of
     * the constructor concatenated
     */
    parameters = new String[4];
    parameters[0] = "\"This";
    parameters[1] = "is";
    parameters[2] = "a";
    parameters[3] = "test.\"";
    echo = new Echo(jFileSystem, parameters);
    assertEquals(echo.execute(), "This is a test.");
  }

  public void testPrintEmptyString() {
    /*
     * test to using Echo to print an empty string
     * 
     * Expected output string should be an empty String
     */
    parameters = new String[1];
    parameters[0] = "\"\"";
    echo = new Echo(jFileSystem, parameters);
    assertEquals(echo.execute(), "");
  }

  @Test
  public void testPrintInvalidSingleString() {
    /*
     * test to using Echo to print an invalid word
     * 
     * Expected output string should be an empty String
     */
    parameters = new String[1];
    parameters[0] = "\"Hi";
    echo = new Echo(jFileSystem, parameters);
    assertEquals(echo.execute(), "");
  }

  @Test
  public void testPrintInvalidMessageString() {
    /*
     * test to using Echo to print an invalid message
     * 
     * Expected output string should be an empty String
     */
    parameters = new String[1];
    parameters[0] = "This test has failed.\"";
    echo = new Echo(jFileSystem, parameters);
    assertEquals(echo.execute(), "");
  }

  @Test
  public void testEchoRedirectOverwriteString() {
    /*
     * Test to determine if redirecting output of pwd command in new folder
     * works. Creates a new file called path.txt, adds the output to the body of
     * the new file.
     * 
     * Expected content of path.txt should be a string containing the path of
     * the newly created working directory ("/a")
     */
    runCommand.sortQuery("echo \"This is a test.\" > output.txt");
    concatenate = new Cat(runCommand.getFileSystem(), strings);
    assertEquals("This is a test.", concatenate.execute());
  }

  @Test
  public void testEchoRedirectAppendString() {
    /*
     * Test to determine if appending the output of pwd command in new folder to
     * an existing path.txt file works. Creates a new file called path.txt, adds
     * both outputs to the body of the new file.
     * 
     * Expected content of path.txt should be a string containing the path of
     * the newly created working directory twice separated by a new line
     * ("/a\n/a")
     */
    runCommand.sortQuery("echo \"This is a test.\" > output.txt");
    runCommand.sortQuery("echo \"The test has passed.\" >> output.txt");
    concatenate = new Cat(runCommand.getFileSystem(), strings);
    assertEquals("This is a test.\nThe test has passed.",
        concatenate.execute());
  }

  @Test
  public void testEchoRedirectErrorString() {
    /*
     * Test to determine if redirecting an invalid String will not create a file
     * 
     * Expected no file called output.txt should be in the root folder of the
     * jFileSystem
     */
    runCommand.sortQuery("echo \"This is a test. > output.txt");
    Item file = jFileSystem.getObject("/output.txt");
    assertTrue(file == null);
  }
}
