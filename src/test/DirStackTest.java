package test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import a2.DirStack;

public class DirStackTest {
  private DirStack dStack;
  private Stack<String> testStack;

  @Before
  public void setUp() throws Exception {
    dStack = new DirStack();
    testStack = new Stack<String>();
  }

  @Test
  public void testPushD() {
    /*
     * Testing the pushD command for change in size
     * 
     * Expected output is that the size of the DirStack increases by 1
     */
    dStack.pushD("taco");
    assertEquals(1, dStack.getStack().size());
  }

  public void testPushD1() {
    /*
     * Testing the pushD command for change in size 3 times in a row
     * 
     * Expected output is that the size of the DirStack increases by 3
     */
    dStack.pushD("taco");
    dStack.pushD("taco1");
    dStack.pushD("taco2");
    assertEquals(3, dStack.getStack().size());
  }

  public void testPushD2() {
    /*
     * Testing the pushD command for correct information
     * 
     * Expected output is that DirStack contains the same information as the
     * test stack
     */
    dStack.pushD("taco");
    dStack.pushD("taco1");
    dStack.pushD("taco2");
    testStack.push("taco");
    testStack.push("taco1");
    testStack.push("taco2");
    assertEquals(testStack, dStack.getStack());
  }

  @Test
  public void testPopD() {
    /*
     * Testing the popD command for change in size
     * 
     * Expected output is that the size of the DirStack decreases by 1
     */
    dStack.pushD("taco");
    dStack.popD();
    assertEquals(0, dStack.getStack().size());
  }

  @Test
  public void testPopD1() {
    /*
     * Testing the popD command for correct information popped (as per LIFO)
     * 
     * Expected output is that it pops the second string entered as per LIFO
     */
    dStack.pushD("taco");
    dStack.pushD("correct");
    String popped = dStack.popD();
    assertEquals("correct", popped);
  }

  public void testPopD2() {
    /*
     * Testing the popD command for consecutive pops and ensuring it has the
     * correct information
     * 
     * Expected output is that it is the same as the test stack
     */
    dStack.pushD("taco");
    dStack.pushD("taco1");
    dStack.pushD("taco2");
    dStack.popD();
    testStack.push("taco");
    testStack.push("taco1");
    assertEquals(testStack, dStack.getStack());
  }

}
