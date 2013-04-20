package org.alessiodm.brainfuckr;

import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This is a very simple BrainFuck interpreter.<br>
 * It is not implemented <em>comma</em> symbol (i.e., you cannot give input to the
 * interpreter).<br>
 * The implementation is based on a simple stack machine.
 * 
 * @author Alessio Della Motta
 */
public class BrainFuckr {

	private PrintStream out; // Output

	private char[] memory; // Memory (remember: char are unsigned)
	private final char[] input; // Input string

	private int pMem = 0; // Memory pointer
	private int pc = 0; // Program Counter
	private final Stack<Integer> biStack; // Bracket Index Stack

	public BrainFuckr(String input) {
		this(input, 1000, System.out);
	}

	public BrainFuckr(String input, int size) {
		this(input, size, System.out);
	}

	public BrainFuckr(String input, PrintStream out) {
		this(input, 1000, out);
	}
	
	public BrainFuckr(String input, int size, PrintStream out) {
		this.out = out;
		this.input = input.toCharArray();
		this.memory = new char[size];
		this.biStack = new Stack<Integer>();
	}

	
	public void execute() throws BrainFuckrException {
		while (pc < input.length) {
			char op = input[pc];
			switch (op) {
			case '>':
				gt();
				break;
			case '<':
				lt();
				break;
			case '-':
				minus();
				break;
			case '+':
				plus();
				break;
			case '.':
				dot();
				break;
			case '[':
				leftBracket();
				break;
			case ']':
				rightBracket();
				break;
			}
			pc++;
		}
		
		if(!biStack.isEmpty()){
			throw BrainFuckrException.BRACKETS_DONT_MATCH;
		}
	}

	protected void gt() throws BrainFuckrException {
		if (++pMem >= memory.length){
			throw BrainFuckrException.MEMORY_OVERFLOW;
		}
	}

	protected void lt() throws BrainFuckrException {
		if (--pMem < 0) {
			throw BrainFuckrException.MEMORY_UNDERFLOW;
		}
	}

	protected void plus() throws BrainFuckrException {
		memory[pMem]++;
	}

	protected void minus() throws BrainFuckrException {
		memory[pMem]--;
	}

	protected void dot() throws BrainFuckrException {
		out.print(memory[pMem]);
		out.flush();
	}

	protected void leftBracket() throws BrainFuckrException {
		boolean jumpAhead = memory[pMem] == 0;
		if (jumpAhead) {
			int counter = 1;
			boolean stop = false;
			while (!stop) {
				stop = counter == 0;
				if (pc++ > input.length) {
					throw BrainFuckrException.BRACKETS_DONT_MATCH;
				}

				if (input[pc] == '[') {
					counter++;
					continue;
				}

				if (input[pc] == ']') {
					counter--;
				}
			}
		} else {
			biStack.push(pc);
		}
	}

	protected void rightBracket() throws BrainFuckrException {
		boolean jumpBack = memory[pMem] != 0;
		try {
			if (jumpBack) {
				pc = biStack.peek();
			} else {
				biStack.pop();
			}
		} catch (EmptyStackException e) {
			throw BrainFuckrException.BRACKETS_DONT_MATCH;
		}
	}

}
