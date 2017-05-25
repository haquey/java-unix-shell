package a2;

import java.util.*;

/**
 * An interface holding all of jFilesystems methods
 */
public interface FileSystem {
  public String getCurrPath();

  public void setFullPath(String newDir);

  public Item getObject(String name);

  public Item getObjRecurs(String name, String currName, Item dirrOrFile);

  public void add(Folder newFolder);

  public Vector<String> getFullPaths();

  public boolean checkValidPath(String path);

  public void setCurrFolder(Folder folder);

  public Folder getCurrFolder();

  public void addFullPath(String path);

  public void setRoot(Folder root);

  public Folder getRootFolder();

  public DirStack getDirStack();

  public void setDirStack(DirStack stack);
  
  public String getFullPath(String path) throws InvalidPath;
  
  public String removeSingleDots(String path);
  
  public String removeDots(String name);
  
  public void removePaths(String path);
}


