package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class OutputToFileTest {
  private JFileSystem jFileSystem;
  private Cat cat;
  private File file1;
  private File file2;
  private File file3;
  private String[] fileNames = new String[1];

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

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
  public void testOverwriteGivenFileName() {
    /*
     * test to use the overwrite method to replace a given file's contents with
     * the String provided. The outfile given is the file's name.
     * 
     * Expected String that would be the String that was passed in to replace
     * the file's contents
     * 
     */
    fileNames[0] = "test1";
    OutputToFile.overwrite(jFileSystem, "Test passed.", "test1");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals("Test passed.", cat.execute());
  }

  @Test
  public void testOverwriteGivenFilePath() {
    /*
     * test to use the overwrite method to replace a given file's contents with
     * the String provided. The outfile given is the path leading to the file.
     * 
     * Expected String that would be the String that was passed in to replace
     * the file's contents
     * 
     */
    fileNames[0] = "test2";
    OutputToFile.overwrite(jFileSystem, "Test passed.", "/test2");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals("Test passed.", cat.execute());
  }

  @Test
  public void testOverwriteGivenFileComplicatedPath() {
    /*
     * test to use the overwrite method to replace a given file's contents with
     * the String provided. The outfile given is a complicated path leading to
     * the file.
     * 
     * Expected String that would be the String that was passed in to replace
     * the file's contents
     * 
     */
    fileNames[0] = "test3";
    OutputToFile.overwrite(jFileSystem, "Test passed.", "/.././test3");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals("Test passed.", cat.execute());
  }

  @Test
  public void testOverwriteGivenInvalidPath() {
    /*
     * test to give overwrite method an invalid path to show the contents of
     * original file has not changed
     * 
     * Expected String that would be the body of the original file
     * 
     */
    fileNames[0] = "test3";
    OutputToFile.overwrite(jFileSystem, "Test passed.", "/a/test3");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals("This is a test in test3,\nAnd it works.", cat.execute());
  }

  @Test
  public void testOverwriteCreateNewFile() {
    /*
     * test to use the overwrite method to create a new file containing the
     * contents of the string provided
     * 
     * Expected String that would be the String that was passed in
     * 
     */
    fileNames[0] = "test4";
    OutputToFile.overwrite(jFileSystem, "Test passed.", "test4");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals("Test passed.", cat.execute());
  }

  @Test
  public void testAppendGivenFileName() {
    /*
     * test to use the append method to add a String to a new line at the end of
     * a given file's contents. The outfile given is the file's name
     * 
     * Expected String that would be outputed is a String that is the
     * concatenation of the two file's contents separated by a new line
     * 
     */
    fileNames[0] = "test1";
    OutputToFile.append(jFileSystem, file2.getBody(), "test1");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals(file2.getBody(), cat.execute());
  }

  @Test
  public void testAppendGivenFilePath() {
    /*
     * test to use the append method to add a String to a new line at the end of
     * a given file's contents. The outfile given is the path leading to the
     * file
     * 
     * Expected String that would be outputed is a String that is the
     * concatenation of the two file's contents separated by a new line
     * 
     */
    fileNames[0] = "test2";
    OutputToFile.append(jFileSystem, file3.getBody(), "/test2");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals("This is a test in test2.\n" + file3.getBody(), cat.execute());
  }

  @Test
  public void testAppendGivenFileComplicatedPath() {
    /*
     * test to use the append method to add a String to a new line at the end of
     * a given file's contents. The outfile given is a complicated path leading
     * to the file
     * 
     * Expected String that would be outputed is a String that is the
     * concatenation of the two file's contents separated by a new line
     * 
     */
    fileNames[0] = "test3";
    OutputToFile.append(jFileSystem, file2.getBody(), "/.././test3");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals("This is a test in test3,\nAnd it works.\n" + file2.getBody(),
        cat.execute());
  }

  @Test
  public void testAppendGivenInvalidPath() {
    /*
     * test to give append method an invalid path to show the contents of
     * original file has not changed
     * 
     * Expected String that would be the body of the original file
     * 
     */
    fileNames[0] = "test3";
    OutputToFile.overwrite(jFileSystem, file2.getBody(), "/a/test3");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals("This is a test in test3,\nAnd it works.", cat.execute());
  }

  @Test
  public void testAppendCreateNewFile() {
    /*
     * test to use the append method to create a new file containing the
     * contents of the string provided
     * 
     * Expected String that would be the String that was passed
     * 
     */
    fileNames[0] = "test4";
    OutputToFile.append(jFileSystem, file2.getBody(), "test4");
    cat = new Cat(this.jFileSystem, fileNames);
    assertEquals(file2.getBody(), cat.execute());
  }

}
