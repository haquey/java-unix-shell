package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class PWDTest {
  private JFileSystem jFileSystem;
  private PWD printWorkingDir;
  private ProQuery runCommand;
  private String[] paths = {"path.txt"};
  private Cat concatenate;

  @Before
  public void setUp() throws Exception {}

  {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    this.printWorkingDir = new PWD(this.jFileSystem);
    this.runCommand = new ProQuery(this.jFileSystem);
    this.concatenate = new Cat(this.jFileSystem, paths);
  }

  @Test
  public void testPWDRootFolder() {
    /*
     * Test to determine if printing the current directory of the root folder
     * works
     * 
     * Expected output should be a string containing the path of the current
     * working directory ("/")
     */
    assertEquals("/", printWorkingDir.execute());
  }

  @Test
  public void testPWDNewFolder() {
    /*
     * Test to determine if printing the current directory of a newly created
     * folder with depth 1 works
     * 
     * Expected output should be a string containing the path of the newly
     * created working directory ("/a")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("cd a");
    printWorkingDir = new PWD(runCommand.getFileSystem());
    assertEquals("/a", printWorkingDir.execute());
  }

  @Test
  public void testPWDBranchingNewFolder() {
    /*
     * Test to determine if printing the current directory of a newly created
     * folder with depth 1 works alongside a branching folder
     * 
     * Expected output should be a string containing the path of the newly
     * created working directory ("/a")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("mkdir b");
    runCommand.sortQuery("cd a");
    printWorkingDir = new PWD(runCommand.getFileSystem());
    assertEquals("/a", printWorkingDir.execute());
  }

  @Test
  public void testPWDDeepNewFolder() {
    /*
     * Test to determine if printing the current directory of a newly created
     * folder with depth 2 works
     * 
     * Expected output should be a string containing the path of the newly
     * created working directory ("/a/b")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("cd a");
    runCommand.sortQuery("mkdir b");
    runCommand.sortQuery("cd b");
    printWorkingDir = new PWD(runCommand.getFileSystem());
    assertEquals("/a/b", printWorkingDir.execute());
  }

  @Test
  public void testPWDBranchingDeepNewFolder() {
    /*
     * Test to determine if printing the current directory of a newly created
     * folder with depth 1 works alongside a branching folder
     * 
     * Expected output should be a string containing the path of the newly
     * created working directory ("/a")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("mkdir b");
    runCommand.sortQuery("mkdir c");
    runCommand.sortQuery("cd b");
    runCommand.sortQuery("mkdir d");
    runCommand.sortQuery("mkdir e");
    runCommand.sortQuery("mkdir f");
    runCommand.sortQuery("cd f");
    printWorkingDir = new PWD(runCommand.getFileSystem());
    assertEquals("/b/f", printWorkingDir.execute());
  }

  @Test
  public void testPWDRedirectOverwriteNewFolder() {
    /*
     * Test to determine if redirecting output of pwd command in new folder
     * works. Creates a new file called path.txt, adds the output to the body of
     * the new file.
     * 
     * Expected content of path.txt should be a string containing the path of
     * the newly created working directory ("/a")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("cd a");
    runCommand.sortQuery("pwd > path.txt");
    concatenate = new Cat(runCommand.getFileSystem(), paths);
    assertEquals("/a", concatenate.execute());
  }

  @Test
  public void testPWDRedirectAppendNewFolder() {
    /*
     * Test to determine if appending the output of pwd command in new folder to
     * an existing path.txt file works. Creates a new file called
     * path.txt, adds both outputs to the body of the new file.
     * 
     * Expected content of path.txt should be a string containing the path of
     * the newly created working directory twice separated by a new line
     * ("/a\n/a")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("cd a");
    runCommand.sortQuery("pwd > path.txt");
    runCommand.sortQuery("pwd >> path.txt");
    concatenate = new Cat(runCommand.getFileSystem(), paths);
    assertEquals("/a\n/a", concatenate.execute());
  }
}
