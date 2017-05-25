package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class FileTest {

  private JFileSystem jFileSystem;
  private String body;
  private File file;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    body = "";
    file = new File("testFile");
  }

  @Test
  public void testAddToBody1() {
    /*
     * test to use addToBody() method to add an empty string to an empty body
     * of an existing file
     * 
     * Expected body of the file is an empty String
     * 
     */
    file.addToBody(body);
    assertEquals(file.getBody(), "");
  }

  @Test
  public void testAddToBody2() {
    /*
     * test to use addToBody() method to add a string to an empty body of an
     * existing file
     * 
     * Expected body of the file is the String passed into addToBody()
     * 
     */
    file.addToBody("test");
    assertEquals(file.getBody(), "test");
  }

  @Test
  public void testAddToBody3() {
    /*
     * test to use addToBody() method to add a string to an empty body of an
     * existing file. use addToBody() method again to add the string to the
     * modified body of the existing file
     * 
     * Expected body of the file is the String passed into the addToBody()
     * method and the String passed into second addToBody() method
     * 
     */
    file.addToBody("testing testing ");
    file.addToBody("123");
    assertEquals(file.getBody(), "testing testing 123");
  }

  @Test
  public void testGetName() {
    /*
     * test to use getName() method to get the name of the file
     * 
     * Expected return should be equal to the name of the file
     * 
     */
    assertEquals(file.getName(), "testFile");
  }

  @Test
  public void testSetBody1() {
    /*
     * test to use setBody() method to make the contents of the existing file
     * an empty String
     * 
     * Expected body of the file is an empty String
     * 
     */
    file.setBody(body);
    assertEquals(file.getBody(), "");
  }

  @Test
  public void testSetBody2() {
    /*
     * test to use setBody() method to make the contents of the existing file
     * the String passed into the parameters of setBody() method.
     * 
     * Expected body of the file is the String passed into the parameters of the
     * setBody() method.
     * 
     */
    file.setBody("test");
    assertEquals(file.getBody(), "test");
  }

  @Test
  public void testSetBody3() {
    /*
     * test to use setBody() method to make the contents of the existing file
     * the String passed into the parameters of setBody() method. Use setBody()
     * method again with a different String to make the contents of the existing
     * file the String of the second setBody() parameter.
     * 
     * Expected body of the file is the String passed into the parameters of the
     * second setBody() method.
     * 
     */
    file.setBody("testing testing ");
    file.setBody("123");
    assertEquals(file.getBody(), "123");
  }

}
