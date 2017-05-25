package a2;

public class File extends Item {
  private String body = "";

  /**
   * The constructor
   * 
   * @param name - the name of the file
   */

  public File(String name) {
    super(name, "somePath", null, null);
  }

  /**
   * This method adds a given string to the body of the file
   * 
   * @param append - a string added to the body of the file
   */

  public void addToBody(String append) {
    this.body += append;
  }

  /**
   * This method changes the body of the file to the new given body
   * 
   * @param - a string that is the new body of the file
   */

  public void setBody(String body) {
    this.body = body;
  }

  /**
   * This method returns the body of the file
   * 
   * @return this.body - the body of the file
   */

  public String getBody() {
    return this.body;
  }

}
