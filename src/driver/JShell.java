// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: gaoxu1
// UT Student #: 1002309502
// Author: Xu Sheng Gao
//
// Student2:
// UTORID user_name: haqueya1
// UT Student #: 1002418225
// Author: Yasir Haque
//
// Student3:
// UTORID user_name: vasant42
// UT Student #: 1002299327
// Author: Marion Vasantharajah
//
// Student4:
// UTORID user_name: shahid41
// UT Student #: 1002385741
// Author: Adnan Shahid
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import a2.*;

public class JShell {

  public static void main(String[] args) {
    // variable declaration
    JFileSystem jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    ProQuery shellQuery = new ProQuery(jFileSystem);
    String input;



    try {
      // Create a line reader
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      // Assess user input endlessly (intended to stop with the exit command)
      // and send the input to into the ProQuery
      while (true) {
        input = br.readLine();
        shellQuery.sortQuery(input);

      }

    } catch (IOException e) {
      e.printStackTrace();
    }



  }

}
