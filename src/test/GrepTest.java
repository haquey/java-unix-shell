package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.Cat;
import a2.File;
import a2.Folder;
import a2.Grep;
import a2.JFileSystem;
import a2.Mkdir;

public class GrepTest {

  private JFileSystem jFileSystem;
  private Cat cat;
  private File file1;
  private File file2;
  private File file3;
  private File file4;
  private File file5;
  private String[] fileNames;
  private Grep grep;
  private Folder rootFolder;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    file1 = new File("file1");
    file1.setPath("/file1");
    rootFolder.addChildren(file1);
    file2 = new File("file2");
    file2.setPath("/file2");
    file2.setBody("This is a test in test2.");
    rootFolder.addChildren(file2);
    file3 = new File("file3");
    file3.setPath("/file3");
    file3.setBody("\na\nb\nc");
    rootFolder.addChildren(file3);
    jFileSystem.addFullPath("/file1");
    jFileSystem.addFullPath("/file2");
    jFileSystem.addFullPath("/file3");
  }

  @Test
  public void testExecuteOneLineFileWithValue() {
    /*
     * Testing checking a regex in which one line of the file has the regex
     * 
     * Expected output is that the line from the file are returned
     */
    String[] command = {"a", "/file3"};
    grep = new Grep(jFileSystem, command);
    assertEquals("a\n", grep.execute());
  }
  
  @Test
  public void testInvalidPath() {
    /*
     * Testing checking an invalid path
     * 
     * Expected output is that no lines are returned
     */
    String[] command = {"a", "/filesda3"};
    grep = new Grep(jFileSystem, command);
    assertEquals("", grep.execute());
  }
  
  @Test
  public void testInvalidRegex() {
    /*
     * Testing checking an invalid regex
     * 
     * Expected output is that no lines are returned
     */
    String[] command = {"[", "/file3"};
    grep = new Grep(jFileSystem, command);
    assertEquals("", grep.execute());
  }

  @Test
  public void testExecuteMultipleLineFileWithValue() {
    /*
     * Testing checking a regex in which multiple lines of the file has the
     * regex
     * 
     * Expected output is that the lines that have the regex are returned
     */
    file4 = new File("file4");
    file4.setPath("/file4");
    file4.setName("file4");
    file4.setBody("This is cool\nAnd I\nLike This is");
    rootFolder.addChildren(file4);
    jFileSystem.addFullPath("/file4");
    String[] command1 = {"This is", "/file4"};
    grep = new Grep(jFileSystem, command1);
    assertEquals("This is cool\nLike This is\n", grep.execute());
  }

  @Test
  public void testExecuteMultipleLineFileWithOneValue() {
    /*
     * Testing checking a regex in which it is a multiple line file and one line
     * contains the regex
     * 
     * Expected output is that the line with the regex is returned
     */
    file4 = new File("file4");
    file4.setPath("/file4");
    file4.setName("file4");
    file4.setBody("This is cool\nAnd I\nLike This is");
    rootFolder.addChildren(file4);
    jFileSystem.addFullPath("/file4");
    String[] command1 = {"And", "/file4"};
    grep = new Grep(jFileSystem, command1);
    assertEquals("And I\n", grep.execute());
  }

  @Test
  public void testExecuteDoesNotContainValue() {
    /*
     * Testing checking a regex in which the file contains no such value
     * 
     * Expected output is that no line is returned
     */
    file4 = new File("file4");
    file4.setPath("/file4");
    file4.setName("file4");
    file4.setBody("This is cool\nAnd I\nLike This is");
    rootFolder.addChildren(file4);
    jFileSystem.addFullPath("/file4");
    String[] command1 = {"this is", "/file4"};
    grep = new Grep(jFileSystem, command1);
    assertEquals("", grep.execute());
  }

  @Test
  public void testExecuteRegexAllLetters() {
    /*
     * Testing a regex that checks if a single alphabet char is there
     * 
     * Expected output is that all lines are with alphabet chars are there
     */
    file4 = new File("file4");
    file4.setPath("/file4");
    file4.setName("file4");
    file4.setBody("This is cool\nAnd I\nLike This is");
    rootFolder.addChildren(file4);
    jFileSystem.addFullPath("/file4");
    String[] command1 = {"[A-Z]", "/file4"};
    grep = new Grep(jFileSystem, command1);
    assertEquals("This is cool\nAnd I\nLike This is\n", grep.execute());
  }

  @Test
  public void testExecuteRecursive() {
    /*
     * Testing recursive regex output for a string case
     * 
     * Expected output is that 5 of the lines are output
     */
    String[] folder = {"folder1"};
    Mkdir dir = new Mkdir(jFileSystem, folder);
    dir.execute();

    file4 = new File("file4");
    file4.setPath("/folder1/file4");
    file4.setBody("This is a test in test4,\nAnd it works\nI like That");
    file4.setName("folder1");
    file5 = new File("file5");
    file5.setPath("/folder1/file5");
    file5.setName("folder1");
    file5.setBody("This is a test in test5,\nAnd it works\nI like This is");

    Folder directory = (Folder) jFileSystem.getObject("/folder1");
    directory.addChildren(file4);

    jFileSystem.addFullPath("/folder1/file4");

    directory.addChildren(file5);

    jFileSystem.addFullPath("/folder1/file5");

    String[] command = {"-R", "This is", "/folder1"};
    grep = new Grep(jFileSystem, command);
    
    String[] lines = grep.execute().split("\r\n|\r|\n");
    assertEquals(5, lines.length);
  }
  
  @Test
  public void testExecuteRecursiveRegexCheck() {
    /*
     * Testing recursive regex output for an alphabet case
     * 
     * Expected output is that 5 of the lines are output
     */
    String[] folder = {"folder1"};
    Mkdir dir = new Mkdir(jFileSystem, folder);
    dir.execute();

    file4 = new File("file4");
    file4.setPath("/folder1/file4");
    file4.setBody("This is a test in test4,\nAnd it works\nI like That");
    file4.setName("folder1");
    file5 = new File("file5");
    file5.setPath("/folder1/file5");
    file5.setName("folder1");
    file5.setBody("This is a test in test5,\nAnd it works\nI like This is");

    Folder directory = (Folder) jFileSystem.getObject("/folder1");
    directory.addChildren(file4);

    jFileSystem.addFullPath("/folder1/file4");

    directory.addChildren(file5);

    jFileSystem.addFullPath("/folder1/file5");

    String[] command = {"-R", "[A-Z]", "/folder1"};
    grep = new Grep(jFileSystem, command);
    
    String[] lines = grep.execute().split("\r\n|\r|\n");
    assertEquals(8, lines.length);
  }

}
