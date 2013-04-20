package org.alessiodm.brainfuckr;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class BrainFuckrTest {
	private ByteArrayOutputStream baos;
	private PrintStream out;
	
	@Before
	public void prepare(){
		baos = new ByteArrayOutputStream();
		out = new PrintStream(baos);
	}
	
	@Test
	public void testHelloWorld() throws BrainFuckrException {
		String helloWorld = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
		BrainFuckr bf = new BrainFuckr(helloWorld, out);
		bf.execute();
		assertEquals("BrainFuckr went mad!", "Hello World!\n", baos.toString());
	}

	@Test
	public void testLeftBracketsDontMatch() {
		try {
			String program = "++++++++++[[[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
			BrainFuckr bf = new BrainFuckr(program, out);
			bf.execute();
			fail("We would expect a brackets don't match error...");
		}
		catch (BrainFuckrException e){
			assertEquals("Expected different exception", BrainFuckrException.BRACKETS_DONT_MATCH, e);
		}
	}
	
	@Test
	public void testRightBracketsDontMatch() {
		try {
			String program = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]]]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
			BrainFuckr bf = new BrainFuckr(program, out);
			bf.execute();
			fail("We would expect a brackets don't match error...");
		}
		catch (BrainFuckrException e){
			assertEquals("Expected different exception", BrainFuckrException.BRACKETS_DONT_MATCH, e);
		}
	}
	
	@Test
	public void testMemoryUnderflow() {
		try {
			String program = "<<<<<<";
			BrainFuckr bf = new BrainFuckr(program, 5, out);
			bf.execute();
			fail("We would expect a memory underflow error...");
		}
		catch (BrainFuckrException e){
			assertEquals("Expected different exception", BrainFuckrException.MEMORY_UNDERFLOW, e);
		}
	}
	
	@Test
	public void testMemoryOverflow() {
		try {
			String program = ">>>>>>>>";
			BrainFuckr bf = new BrainFuckr(program, 5, out);
			bf.execute();
			fail("We would expect a memory overflow error...");
		}
		catch (BrainFuckrException e){
			assertEquals("Expected different exception", BrainFuckrException.MEMORY_OVERFLOW, e);
		}
	}
}
