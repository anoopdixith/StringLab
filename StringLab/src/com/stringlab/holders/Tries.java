package com.stringlab.holders;

import java.util.LinkedList;

public class Tries {
	private char data;
	private boolean isWord;
    private LinkedList<Tries> children;	
    public int count = 0;
    
	public char getData() {
		return data;
	}
	public void setData(char data) {
		this.data = data;
	}
	public boolean isWord() {
		return isWord;
	}
	public void setWord(boolean isWord) {
		this.isWord = isWord;
	}
	public LinkedList<Tries> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<Tries> children) {
		this.children = children;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public Tries(char c) {
		this.data = c;
		children = new LinkedList<Tries>();
		isWord = false;
		count = 0;
	}
	
	public Tries subNode(char c) {
	    //System.out.println("char in subNode is " + c);
	    //System.out.println("children length is " + children.size());
		if(children != null) {
			for(Tries eachChild: children) {
				if(eachChild.data == c) {
					return eachChild;
				}
			}
		}
		return null;
	}
}
