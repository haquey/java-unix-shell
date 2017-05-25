package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class CurlTest {
  private JFileSystem jFileSystem;
  private ProQuery runCommand;
  private Cat concatenate;
  private String[] files = {"URLContent.txt"};
  private Curl curl;
  private String[] url = new String[1];

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    this.runCommand = new ProQuery(this.jFileSystem);
    this.concatenate = new Cat(this.jFileSystem, files);
  }

  @Test
  public void test() {
    /*
     * test to use curl to read an URL's txt file
     * 
     * Expected String that would be outputed is the contents of the txt file
     * provided in the URL
     * 
     */
    url[0] = "ftp://anonftp.burlington.com/helloworld.txt";
    curl = new Curl(jFileSystem, url);
    curl.execute();
    files[0] = "helloworld.txt";
    this.concatenate = new Cat(this.jFileSystem, files);

    assertEquals(
        "Anonymous FTP Site\n------------------\nUnauthorized Use of This Site"
            + " is Strictly Prohibited!\n",
        concatenate.execute());
  }

  @Test
  public void test1() {
    /*
     * test to use curl to read an URL's html contents
     * 
     * Expected String that would be outputed is the contents of the URL
     * 
     */
    url[0] =
        "http://opensource.apple.com//source/SpamAssassin/SpamAssassin-127.2/"
            + "SpamAssassin/t/data/etc/hello.txt";
    curl = new Curl(jFileSystem, url);
    curl.execute();
    files[0] = "hello.txt";
    this.concatenate = new Cat(this.jFileSystem, files);
    String expected =
        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict" + "//EN\"\n"
            + "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\""
            + "en\">\n" + "<head>\n" + "<title>hello.txt</title>\n"
            + "<style type=\"text/css\">\n"
            + ".enscript-comment { font-style: italic; color: rgb(178,34,34); }\n"
            + ".enscript-function-name { font-weight: bold; color: rgb(0,0,255);"
            + " }\n"
            + ".enscript-variable-name { font-weight: bold; color: rgb(184,134,11);"
            + " }\n"
            + ".enscript-keyword { font-weight: bold; color: rgb(160,32,240); }\n"
            + ".enscript-reference { font-weight: bold; color: rgb(95,158,160); }\n"
            + ".enscript-string { font-weight: bold; color: rgb(188,143,143); }\n"
            + ".enscript-builtin { font-weight: bold; color: rgb(218,112,214); }\n"
            + ".enscript-type { font-weight: bold; color: rgb(34,139,34); }\n"
            + ".enscript-highlight { text-decoration: underline; color: 0; }\n"
            + "</style>\n" + "</head>\n" + "<body id=\"top\">\n"
            + "<h1 style=\"margin:8px;\" id=\"f1\">hello.txt&nbsp;&nbsp;&nbsp;"
            + "<span style="
            + "\"font-weight: normal; font-size: 0.5em;\">[<a href=\"?txt\">"
            + "plain text</a>]</span></h1>\n" + "<hr/>\n" + "<div></div>\n"
            + "<pre>\n" + "hello world\n" + "</pre>\n" + "<hr />\n"
            + "</body></html>\n";
    assertEquals(expected, concatenate.execute());
  }

  @Test
  public void testPWDRedirectFailsToMakeFile() {
    /*
     * Test to determine if redirection the output of curl command (nonexistent)
     * will create a file
     * 
     * Expected no file called URLContent.txt should be in the root folder of
     * the jFileSystem
     */
    runCommand.sortQuery(
        "curl ftp://anonftp.burlington.com/helloworld.txt > URLContent.txt");
    Item file = jFileSystem.getObject("/URLContent.txt");
    assertTrue(file == null);
  }

  @Test
  public void testCDRedirectFailsToAppendFile() {
    /*
     * Test to determine if redirection the output of curl command (nonexistent)
     * will create a file
     * 
     * Expected no file called URLContent.txt should be in the root folder of
     * the jFileSystem
     */
    runCommand.sortQuery(
        "curl ftp://anonftp.burlington.com/helloworld.txt >> URLContent.txt");
    Item file = jFileSystem.getObject("/URLContent.txt");
    assertTrue(file == null);
  }
}
