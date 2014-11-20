package com.stringlab;

/*
 * This class is the mail StringLab class with 
 * many String related operations.
 * 
 * @author: Anoop Dixith
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.stringlab.holders.AllWords;
import com.stringlab.holders.TrieDictionary;


public class StringLab {
  private String string;
  private int stringLength;

  public StringLab() {
    this.string = "";
    this.stringLength = 0;
  }

  private ArrayList<String> allPermutations = new ArrayList<String>();
  private ArrayList<String> allSubsets = new ArrayList<String>();

  private String[] onesNumbers = {"", "one", "two", "three", "four", "five", "six", "seven",
      "eight", "nine"};
  private String[] onesNumbersNext = {"", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
      "sixteen", "seventeen", "eighteen", "nineteen"};
  private String[] tensNumbers = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty",
      "seventy", "eighty", "ninety"};

  private String[] bigNumbers = {"", "thousand", "million", "billion", "trillion", "quadrillion",
      "quintillion", "sextillion", "septillion", "octillion", "nonillion", "decillion"};

  private TrieDictionary dictionary;

  public StringLab(String string) {
    this.string = string;
    this.stringLength = string.length();
  }

  public StringLab(char[] arr) {
    StringBuilder stringBuilder = new StringBuilder("");
    for (int i = 0; i < arr.length; i++) {
      stringBuilder.append(arr[i]);
    }
    this.string = stringBuilder.toString();
    this.stringLength = arr.length;
  }

  public String charToString(char[] arr) {
    StringBuilder stringBuilder = new StringBuilder("");
    for (int i = 0; i < arr.length; i++) {
      stringBuilder.append(arr[i]);
    }
    return stringBuilder.toString();
  }

  public void printString() {
    System.out.print(string);
  }

  public void printAll() {
    System.out.println("String is " + string);
    System.out.println("Length of the string is " + string.length());
    System.out.println("Hashcode of the string is " + string.hashCode());
    System.out.println("Reversed steing is " + new StringLab(string).reverseString());
    System.out.println("Is he string a palindrome? " + new StringLab(string).isPalindrome());
  }

  /*
   * "addf" --> "fdda"
   */
  public String reverseString() {
    char[] charRep = string.toCharArray();
    for (int i = 0; i < charRep.length / 2; i++) {
      char temp = charRep[i];
      charRep[i] = charRep[charRep.length - i - 1];
      charRep[charRep.length - i - 1] = temp;
    }
    return charToString(charRep);
  }

  public boolean isPalindrome() {
    return string.equals(new StringLab(string).reverseString());
  }

  public int aToI() throws NumberOverflowException {
    int number = 0;
    int pow = 1;
    char[] charRep = string.toCharArray();
    for (int i = charRep.length - 1; i >= 0; i--) {
      if (!(charRep[i] - '0' < '0' || charRep[i] - '0' > '9')) {
        throw new NumberFormatException();
      }
      if (i > 0 && charRep[i] == '-') {
        throw new NumberFormatException();
      }
      if (i == 0 && charRep[i] == '-') {
        number *= -1;
      } else {
        long num = (long) number;
        if ((num + pow * (charRep[i] - '0')) > Integer.MAX_VALUE) {
          throw new NumberOverflowException("Integer overflow");
        }
        number += pow * (charRep[i] - '0');
        pow *= 10;
      }
    }
    return number;
  }

  public static String iToS(int number) {
    StringBuilder stringBuilder = new StringBuilder("");
    // 123
    while (number > 0) {
      int rem = number % 10;
      stringBuilder.append(rem);
      number /= 10;
    }
    return stringBuilder.reverse().toString();
  }

  public float aToF() throws NumberOverflowException, InvalidFormatException {
    return aToF(-1);
  }

  public float aToF(int decimalPlaces) throws NumberOverflowException, InvalidFormatException {
    int number;
    int fraction;
    String[] decimalArray = string.split("\\.");
    if (decimalArray.length == 0) {
      return (float) (aToI());
    }
    if (decimalArray.length > 2) {
      throw new InvalidFormatException("Input not a valid float value");
    }
    number = new StringLab(decimalArray[0]).aToI();
    fraction = new StringLab(decimalArray[1]).aToI();

    // 123.4532
    int numberOfDecimalPlaces = decimalPlaces;
    if (numberOfDecimalPlaces == -1) {
      numberOfDecimalPlaces = iToS(fraction).length();
    }
    long isOverflow = (long) Math.pow(10, numberOfDecimalPlaces);
    if (isOverflow > Integer.SIZE) {
      throw new NumberOverflowException("Number of decimal places overflow exception");
    }
    float value = number + (float) (fraction / Math.pow(10, numberOfDecimalPlaces));
    return value;
  }

  public String sortStringSimple() {
    char[] charRep = string.toCharArray();
    int[] intRep = new int[string.length()];
    for (int i = 0; i < charRep.length; i++) {
      intRep[i] = charRep[i] - '0';
    }
    Arrays.sort(intRep);
    // implement counting sort - best approach
    for (int i = 0; i < intRep.length; i++) {
      charRep[i] = (char) (intRep[i] + '0');
    }
    return charToString(charRep);
  }

  /*
   * Counting sort as we know strings can have one of 256 chars.
   */
  public String sortStringCounting() {
    char[] charRep = string.toCharArray();
    int[] ascii = new int[256];
    char[] finalRep = new char[charRep.length];
    for (int i = 0; i < charRep.length; i++) {
      ascii[charRep[i]]++;
    }
    int j = 0;
    for (int i = 0; i < ascii.length; i++) {
      while (ascii[i] > 0) {
        finalRep[j++] = (char) i;
        ascii[i]--;
      }
    }
    return charToString(finalRep);
  }

  public ArrayList<String> allPermutationsBacktracking() {

    return permute(this.string, "");
  }

  private ArrayList<String> permute(String s, String chosen) {

    if (s.length() == 0) {
      allPermutations.add(chosen);
    } else {
      for (int i = 0; i < s.length(); i++) {
        String chosenLetter = s.substring(i, i + 1);
        s = s.substring(0, i) + s.substring(i + 1, s.length()); // remove firstLetter from s
        chosen += chosenLetter;

        // explore
        permute(s, chosen);

        // unchoose
        chosen = chosen.substring(0, chosen.length() - 1);
        s = s.substring(0, i) + chosenLetter + s.substring(i);
      }
    }
    return allPermutations;
  }

  public ArrayList<String> permutationDynamicProgramming() {
    ArrayList<String> allPermutations = new ArrayList<String>();
    char[] stringArray = this.string.toCharArray();
    if (stringArray.length == 0) {
      return null;
    }
    if (stringArray.length == 1) {
      allPermutations.add(new StringBuilder(stringArray[0]).toString());
      return allPermutations;
    }
    if (stringArray.length == 2) {
      allPermutations.add(new StringBuilder().append(stringArray[0]).append(stringArray[1])
          .toString());
      allPermutations.add(new StringBuilder().append(stringArray[1]).append(stringArray[0])
          .toString());
      return allPermutations;
    } else {
      ArrayList<String> innerPermutations =
          new StringLab(this.string.substring(0, this.string.length() - 1))
              .permutationDynamicProgramming();
      char newChar = stringArray[stringArray.length - 1];
      for (String each : innerPermutations) {
        char[] eachArray = each.toCharArray();
        int length = each.length();
        for (int pos = 0; pos <= length; pos++) {
          StringBuilder tempString = new StringBuilder();
          if (pos == 0) {
            tempString = tempString.append(newChar).append(each);
          } else if (pos == length) {
            tempString = tempString.append(each).append(newChar);
          } else {
            tempString =
                tempString.append(eachArray, 0, pos).append(newChar)
                    .append(eachArray, pos, length - pos);
          }
          allPermutations.add(tempString.toString());
        }

      }
    }
    return allPermutations;
  }

  public ArrayList<String> allSubsets() {
    return allSubsets(this.string);
  }

  public Set<String> allSubsetsUnique() {
    ArrayList<String> allSubSets = allSubsets(this.string);
    Set<String> subsetSet = new HashSet<String>(allSubSets);
    return subsetSet;
  }

  private ArrayList<String> allSubsets(String str) {
    int length = str.length();
    char[] arr = str.toCharArray();
    for (int i = 0; i < Math.pow(2, length); i++) {
      boolean[] presentOrNot = new boolean[arr.length];
      char[] binary = binaryRep(i).toCharArray();
      for (int j = 0; j < binary.length; j++) {
        if (binary[j] == '1') {
          presentOrNot[binary.length - 1 - j] = true;
        }
      }
      StringBuilder subsetString = new StringBuilder();
      for (int k = 0; k < presentOrNot.length; k++) {
        if (presentOrNot[k] == true) {
          subsetString.append(arr[k]);
        }
      }
      allSubsets.add(subsetString.toString());
    }

    return this.allSubsets;
  }

  private String binaryRep(int i) {
    StringBuilder binary = new StringBuilder();
    while (i > 0) {
      int rem = i % 2;
      if (rem == 1) {
        binary.append('1');
      } else {
        binary.append('0');
      }
      i /= 2;
    }
    return binary.reverse().toString();
  }

  public boolean isAnagramIgnoreCase(String str) {
    return sortStringCounting().equalsIgnoreCase(new StringLab(str).sortStringCounting());
  }

  public boolean isAnagramCase(String str) {
    return sortStringCounting().equals(new StringLab(str).sortStringCounting());
  }

  public String numberToWord(long i) {
    if (i == 0) {
      return "zero";
    }
    String wordRep = new String("");
    StringBuilder threeDigitRep = new StringBuilder();
    long rem = 0;
    int j = 0;
    while (i > 0) {
      rem = i % 1000;
      // System.out.println("rem is " + rem);
      threeDigitRep = getThreeDigitRep(rem);
      wordRep = threeDigitRep + " " + bigNumbers[j++] + " " + wordRep;
      i /= 1000;
    }
    return wordRep.toString();
  }

  private StringBuilder getThreeDigitRep(long num) {
    StringBuilder threeDigitRep = new StringBuilder("");
    // get hundredth position
    long hundred = num / 100;
    if (hundred != 0) {
      threeDigitRep.append(onesNumbers[(int) hundred]).append(" ").append("hundred ");
    }
    num %= 100;
    long ten = num / 10;
    // System.out.println("ten is " + ten);
    if (ten == 0) {
      long one = num;
      threeDigitRep.append(onesNumbers[(int) one]);
    } else if (ten == 1) {
      long one = num % 10;
      threeDigitRep.append(onesNumbersNext[(int) one]);
    } else {
      threeDigitRep.append(tensNumbers[(int) ten]).append(" ");
      long one = num % 10;
      // System.out.println("one is " + one);
      threeDigitRep.append(onesNumbers[(int) one]);
    }
    return threeDigitRep;
  }

  //This should be called first to build the Trie
  //before making use of any language specific methods
  public void buildDictionary() {
    try {
      this.dictionary = AllWords.buildDictionary();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public boolean isWord() {
    if(this.dictionary == null) {
      buildDictionary();
    }
    System.out.println("Searching " + this.string);
    if(this.dictionary.search(this.string)) {
        return true;
    }
    return false;
  }
  
  public int matchKMP(String pattern1) {
    char[] text = this.string.toCharArray();
    char[] pattern = pattern1.toCharArray();
    //create sliding array.
    //slider[k] will have the length of the 
    //longest prefix of pattern which is also a 
    //proper suffix of pattern[0...k-1]
    int[] slider = new int[pattern.length];
    slider = getPrefixArray(pattern);
    
    //matching part
    int textLength = text.length;
    int patternLength = pattern.length;
    if(textLength == 0) {
      return -1;
    }
    int j =0;
    int i =0;
    while(i < textLength && j < patternLength) {
      if(text[i] == pattern[j]) {
        i++;
        j++;
      }
      else if(j ==0) {
        i++;
      }
      else {
        j = slider[j-1];
      }
    }
    return (j == patternLength)? (i - patternLength) :-1;
  }

  private int[] getPrefixArray(char[] pattern) {
    int length = pattern.length;
    int[] slider = new int[length];
    //first char's longest prefix is always 0
    slider[0] = 0;
    int j=0;
    for(int i=1; i < length; i++) {
      while(j > 0 && pattern[i] != pattern[j]) {
        j = slider[j-1];
      }
      if(pattern[i] == pattern[j]) {
        j++;
      }
      slider[i] = j;
    }
    return slider;
  }
}
