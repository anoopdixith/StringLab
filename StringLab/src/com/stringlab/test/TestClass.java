package com.stringlab.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.stringlab.InvalidFormatException;
import com.stringlab.NumberOverflowException;
import com.stringlab.StringLab;
import com.stringlab.holders.TrieDictionary;

public class TestClass {
	public static void main(String args[]) throws InvalidFormatException {
		char[] arr = {'a', 'b','c'};
		String str = "abcd";
		String strNum = "123.344";
		//StringLab stringLab = new StringLab(str);
		//System.out.println(stringLab.isAnagramCase("3214483."));
		//Set<String> perms = stringLab.allSubsetsUnique();
		//this is how you should initiate an iterator
		//ArrayList<String> perms = stringLab.permutationDynamicProgramming();
		//Iterator<String> iterator = perms.iterator();
		//while(iterator.hasNext()) {
			//System.out.println(iterator.next());
		//}
		long number = 5578368473211364421l;
		
		//StringLab stringLab = new StringLab("Aanik");
		//System.out.println("word exists: " + stringLab.isWord());
		
		//System.out.println("===================================");
		
		//TrieDictionary dict = new TrieDictionary();
		//dict.insert("A");
		//dict.insert("a");
		//dict.insert("aa");
		//dict.insert("ab");
		//dict.insert("aal");
		//dict.insert("aalii");
		//dict.insert("aam");
	    //dict.insert("Aani");
	    //dict.insert("aardvark");

		//System.out.println("Exists " + dict.search("aa"));
		//System.out.println("============================");
		//System.out.println("Exists " + dict.search("ab"));
		
		
		//System.out.println(stringLab.numberToWord(number));
		/*
		try {
			System.out.println(stringLab.aToF());
		} catch (NumberOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//stringLab.printAll();
		//System.out.println();
		
		StringLab lab1 = new StringLab("geeksforgeekshello");
		System.out.println("String forge is found at " + lab1.matchKMP("eeks"));
	}
}
