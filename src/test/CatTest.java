package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;


public class CatTest {
  private JFileSystem jFileSystem;
  private Cat cat;
  private File file1;
  private File file2;
  private File file3;
  private String[] fileNames;
  private String stringToOutput;
  private ProQuery runCommand;
  private Cat concatenate;
  private String[] files = {"fileContent.txt"};

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    this.runCommand = new ProQuery(this.jFileSystem);
    this.concatenate = new Cat(this.jFileSystem, files);

    file1 = new File("test1");
    rootFolder.addChildren(file1);
    file2 = new File("test2");
    file2.setBody("This is a test in test2.");
    rootFolder.addChildren(file2);
    file3 = new File("test3");
    file3.setBody("This is a test in test3,\nAnd it works.");
    rootFolder.addChildren(file3);
  }

  @Test
  public void testCatSingleExistingEmptyFile() {
    /*
     * test to use cat to read an existing new file's contents
     * 
     * Expected String that would be outputed is an empty String
     * 
     */
    fileNames = new String[1];
    fileNames[0] = "test1";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.execute(), "");
  }

  @Test
  public void testCatSingleExistingFile() {
    /*
     * test to use cat to read an existing file's contents that holds a String
     * 
     * Expected String that would be outputed is a String that is the file's
     * body
     * 
     */
    fileNames = new String[1];
    fileNames[0] = "test2";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.execute(), "This is a test in test2.");
  }

  @Test
  public void testCatSingleExistingMultipleLinesFile() {
    /*
     * test to use cat to read an existing file's contents that hold multiple
     * lines
     * 
     * Expected String that would be outputed is a String that is the file's
     * body
     * 
     */
    fileNames = new String[1];
    fileNames[0] = "test3";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.execute(), "This is a test in test3,\nAnd it works.");
  }

  @Test
  public void testCatSingleNonexistingFile() {
    /*
     * test to use cat to read an non-existing file's contents
     * 
     * Expected String that would be outputed is an empty String
     */
    fileNames = new String[1];
    fileNames[0] = "test4";
    cat = new Cat(jFileSystem, fileNames);
    stringToOutput = cat.execute();
    assertEquals(stringToOutput, "");
  }

  // read two existing files
  @Test
  public void testCatMultipleExistingFiles() {
    /*
     * test to use cat to read multiple existing file's contents
     * 
     * Expected String that would be outputed is a concatenation of test2's body
     * and test3's body with 3 empty lines at the end of each files
     */
    fileNames = new String[2];
    fileNames[0] = "test2";
    fileNames[1] = "test3";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.execute(),
        "This is a test in test2.\n\n\n\nThis is a test in test3,"
            + "\nAnd it works.\n\n\n\n");
  }

  // read existing and non-existing files
  @Test
  public void testCatMultipleExistingNonexistingFiles() {
    /*
     * test to use cat to read multiple existing and non-existing file's
     * contents
     * 
     * Expected String that would be outputed is a concatenation of test2's body
     * and test3's body with 3 empty lines at the end of each files
     */
    fileNames = new String[3];
    fileNames[0] = "test2";
    fileNames[1] = "test4";
    fileNames[2] = "test3";
    cat = new Cat(jFileSystem, fileNames);
    stringToOutput = cat.execute();
    assertEquals(stringToOutput,
        "This is a test in test2.\n\n\n\nThis is a test in test3,"
            + "\nAnd it works.\n\n\n\n");
  }

  @Test
  public void testCatMultipleExistingNonexistingEmptyFiles() {
    /*
     * test to use cat to read multiple existing and non-existing file's
     * contents
     * 
     * Expected String that would be outputed is a concatenation of test2's body
     * and test3's body with 3 empty lines at the end of each files
     */
    fileNames = new String[4];
    fileNames[0] = "test1";
    fileNames[1] = "test2";
    fileNames[2] = "test4";
    fileNames[3] = "test3";
    cat = new Cat(jFileSystem, fileNames);
    stringToOutput = cat.execute();
    assertEquals(stringToOutput,
        "\n\n\n\nThis is a test in test2.\n\n\n\nThis is a test in test3,"
            + "\nAnd it works.\n\n\n\n");
  }

  @Test
  public void testCatRedirectExistingFile() {
    /*
     * Test to determine if redirecting output of cat command of an existing
     * file works. Creates a new file called fileContent.txt, adds the output to
     * the body of the new file.
     * 
     * Expected content of fileContent.txt should be a string containing the
     * contents of the file test2
     */
    runCommand.sortQuery("cat test2 > fileContent.txt");
    concatenate = new Cat(runCommand.getFileSystem(), files);
    assertEquals("This is a test in test2.", concatenate.execute());
  }

  @Test
  public void testCatRedirectAppendExistingFile() {
    /*
     * Test to determine if appending the output of cat command of existing file
     * to an existing path.txt file works. Creates a new file called
     * fileContent.txt, adds both outputs to the body of the new file.
     * 
     * Expected content of fileContent.txt should be a string containing the
     * contents of the two files, test2 and test3
     */
    runCommand.sortQuery("cat test2 > fileContent.txt");
    runCommand.sortQuery("cat test3 >> fileContent.txt");
    concatenate = new Cat(runCommand.getFileSystem(), files);
    assertEquals("This is a test in test2.\nThis is a test in test3,"
        + "\nAnd it works.", concatenate.execute());
  }
}
