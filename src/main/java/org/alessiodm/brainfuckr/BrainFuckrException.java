package org.alessiodm.brainfuckr;

@SuppressWarnings("serial")
public class BrainFuckrException extends Exception {
	
	public static final BrainFuckrException BRACKETS_DONT_MATCH = new BrainFuckrException("Brackets don't match");
	public static final BrainFuckrException MEMORY_UNDERFLOW = new BrainFuckrException("Memory underflow");
	public static final BrainFuckrException MEMORY_OVERFLOW = new BrainFuckrException("Memory overflow");
	
	private BrainFuckrException(){
	}
	private BrainFuckrException(String msg){
		super(msg);
	}
}

