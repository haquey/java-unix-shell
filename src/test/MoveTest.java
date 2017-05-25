package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class MoveTest {
  // jFileSystem for mv command to act upon
  private JFileSystem jFileSystem;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
  }

  @Test
  public void moveBetweenShallowExistingDirTest() {
    /*
     * Test to determine if moving a directory of shallow depth into another
     * directory functions
     * 
     * Expected output should be a string containing the name of the folder that
     * was moved as viewed by the LS command
     */
    String[] commandArgs = {"a", "b"};
    String[] changeDirArgs = {"b"};
    Mkdir makeDir = new Mkdir(jFileSystem, commandArgs);
    makeDir.execute();
    MV moveFile = new MV(jFileSystem, commandArgs);
    moveFile.execute();
    CD changeDir = new CD(jFileSystem, changeDirArgs);
    changeDir.execute();
    LS listSeg = new LS(jFileSystem);
    assertEquals(listSeg.execute(), listSeg.getStringToOutput());
  }

  @Test
  public void moveDeepExistingDirTest() {
    /*
     * Test to determine if moving a directory of with numerous children into
     * another directory functions
     * 
     * Expected output should be a string containing the names of the children
     * of the folder that was moved as viewed by the LS command (\nx\ny\nz)
     */
    String[] mkDirArgs1 = {"a", "b"};
    String[] changeDirArgs1 = {"a"};
    String[] mkDirArgs2 = {"x", "y", "z"};
    String[] changeDirBack = {".."};
    String[] changeDirArgs2 = {"b"};

    Mkdir makeDir = new Mkdir(jFileSystem, mkDirArgs1);
    makeDir.execute();
    CD changeDir = new CD(jFileSystem, changeDirArgs1);
    changeDir.execute();
    Mkdir mkDir2 = new Mkdir(jFileSystem, mkDirArgs2);
    mkDir2.execute();
    CD changeDirBack1 = new CD(jFileSystem, changeDirBack);
    changeDirBack1.execute();
    MV moveFile = new MV(jFileSystem, mkDirArgs1);
    moveFile.execute();
    CD changeDir2 = new CD(jFileSystem, changeDirArgs2);
    changeDir2.execute();
    CD changeDir3 = new CD(jFileSystem, changeDirArgs1);
    changeDir3.execute();
    LS listSeg = new LS(jFileSystem);
    assertEquals(listSeg.execute(), listSeg.getStringToOutput());

  }

  @Test
  public void moveExistingDirToNonExistingPathExistingParentTest() {
    /*
     * Test to determine if moving a directory from an existing path to a
     * non-existing path functions. The non-existing specified path has an
     * existing parent path.
     * 
     * Expected output should be a string containing the name of the folder that
     * was moved as viewed by the LS command (\nc)
     */
    String[] mkDirArgs1 = {"a", "b"};
    String[] changeDirArgs1 = {"a"};
    String[] mkDirArgs2 = {"c"};
    String[] cdBack = {".."};
    String[] mvArgs1 = {"a", "b/y"};
    String[] changeDirArgs2 = {"b/y"};

    Mkdir makeDir = new Mkdir(jFileSystem, mkDirArgs1);
    makeDir.execute();
    CD changeDir = new CD(jFileSystem, changeDirArgs1);
    changeDir.execute();
    Mkdir mkDir2 = new Mkdir(jFileSystem, mkDirArgs2);
    mkDir2.execute();
    CD changeDirBack1 = new CD(jFileSystem, cdBack);
    changeDirBack1.execute();
    MV moveFile = new MV(jFileSystem, mvArgs1);
    moveFile.execute();
    CD changeDir2 = new CD(jFileSystem, changeDirArgs2);
    changeDir2.execute();
    LS listSeg = new LS(jFileSystem);
    assertEquals(listSeg.execute(), listSeg.getStringToOutput());
  }

  @Test
  public void moveParentDirIntoSubDirTest() {
    /*
     * Test to determine if moving a parent directory to a subdirectory/child is
     * considered a valid command or performs any operations to the FileSystem.
     * 
     * Expected output should be a string containing the name of the folder that
     * would normally have been moved if it weren't a parent directory, as
     * viewed by the LS command (\nb)
     */
    String[] mkDirArgs1 = {"b"};
    String[] changeDirArgs1 = {"b"};
    String[] mkDirArgs2 = {"y"};
    String[] cdBack = {".."};
    String[] mvArgs1 = {"b", "b/y"};

    Mkdir makeDir = new Mkdir(jFileSystem, mkDirArgs1);
    makeDir.execute();
    CD changeDir = new CD(jFileSystem, changeDirArgs1);
    changeDir.execute();
    Mkdir mkDir2 = new Mkdir(jFileSystem, mkDirArgs2);
    mkDir2.execute();
    CD changeDirBack1 = new CD(jFileSystem, cdBack);
    changeDirBack1.execute();
    MV moveFile = new MV(jFileSystem, mvArgs1);
    moveFile.execute();
    LS listSeg = new LS(jFileSystem);
    assertEquals(listSeg.execute(), listSeg.getStringToOutput());
  }

  @Test
  public void moveDirToNonExistingPathAndParent() {
    /*
     * Test to determine if moving a directory to a specified non-existing path
     * within the File System is considered a valid command or performs any
     * operations.
     * 
     * Expected output should be a string containing the name of the folder that
     * would normally have been moved if it weren't a parent directory, as
     * viewed by the LS command (\nb)
     */
    String[] mkDirArgs1 = {"b"};
    String[] mvArgs1 = {"b", "a/y"};

    Mkdir makeDir = new Mkdir(jFileSystem, mkDirArgs1);
    makeDir.execute();
    MV moveFile = new MV(jFileSystem, mvArgs1);
    moveFile.execute();
    LS listSeg = new LS(jFileSystem);
    assertEquals(listSeg.execute(), listSeg.getStringToOutput());
  }
}
