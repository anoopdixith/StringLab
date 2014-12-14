package com.stringlab;

/*
 * This class is the main StringLab class with many String related operations.
 * 
 * @author: Anoop Dixith
 * 
 * @todo: validation() - done
 * 
 * @todo: equals() - done
 * 
 * @todo: transformation() - 
 * 
 * @todo: formatCheck()
 * 
 * @todo: anagramValidEnglish() - done
 * 
 * @todo: serialization - done
 * 
 * @todo: dynamic programming related
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import com.stringlab.holders.AllWords;
import com.stringlab.holders.TrieDictionary;


public class StringLab implements Serializable{
  
  private static final long serialVersionUID = -3233475039146606603L;
  private String string;
  public StringLab() {
    this.string = "";
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
    string.length();
  }

  public StringLab(char[] arr) {
    StringBuilder stringBuilder = new StringBuilder("");
    for (int i = 0; i < arr.length; i++) {
      stringBuilder.append(arr[i]);
    }
    this.string = stringBuilder.toString();
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
  
  public String getString() {
    return this.string;
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

  // This should be called first to build the Trie
  // before making use of any language specific methods
  public void buildDictionary() {
    try {
      this.dictionary = AllWords.buildDictionary();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isWord() {
    if (this.dictionary == null) {
      buildDictionary();
    }
    //System.out.println("Searching " + this.string);
    if (this.dictionary.search(this.string)) {
      return true;
    }
    return false;
  }

  public int matchKMP(String pattern1) {
    char[] text = this.string.toCharArray();
    char[] pattern = pattern1.toCharArray();
    // create sliding array.
    // slider[k] will have the length of the
    // longest prefix of pattern which is also a
    // proper suffix of pattern[0...k-1]
    int[] slider = new int[pattern.length];
    slider = getPrefixArray(pattern);

    // matching part
    int textLength = text.length;
    int patternLength = pattern.length;
    if (textLength == 0) {
      return -1;
    }
    int j = 0;
    int i = 0;
    while (i < textLength && j < patternLength) {
      if (text[i] == pattern[j]) {
        i++;
        j++;
      } else if (j == 0) {
        i++;
      } else {
        j = slider[j - 1];
      }
    }
    return (j == patternLength) ? (i - patternLength) : -1;
  }

  private int[] getPrefixArray(char[] pattern) {
    int length = pattern.length;
    int[] slider = new int[length];
    // first char's longest prefix is always 0
    slider[0] = 0;
    int j = 0;
    for (int i = 1; i < length; i++) {
      while (j > 0 && pattern[i] != pattern[j]) {
        j = slider[j - 1];
      }
      if (pattern[i] == pattern[j]) {
        j++;
      }
      slider[i] = j;
    }
    return slider;
  }

  public String randomString(int length) {
    if (length == 0)
      return "";
    return randomString(length, false, false, false);
  }

  public String randomString(int length, boolean onlyAlphabets, boolean onlyNumbers,
      boolean onlyAlphaNumeric) {
    char[] randomString = new char[length];
    int asciiBeg = 32, asciiEnd = 126, blockBeg = 58, blockEnd = 96;
    boolean blockEnable = false;
    // if the user has, may be by mistake, chosen true for both "onlylphabets" and
    // "onlyNumbers", then make onlyAlphaNumeric to be true even if it is false.
    if (onlyAlphabets && onlyNumbers) {
      onlyAlphaNumeric = true;
    }
    // using values from ASCII charts
    // Returns small letters only. Clients could use toUpper() for lower cases
    if (onlyAlphabets) {
      asciiBeg = 97;
      asciiEnd = 122;
    } else if (onlyNumbers) {
      asciiBeg = 48;
      asciiEnd = 57;
    }
    // if both (one of onlyAlphabets and onlyNumbers) and onlyAlphaNumeric are true,
    // alphaNumeric gets higher priority. (So, not "else if" in this case.
    if (onlyAlphaNumeric) {
      asciiBeg = 48;
      asciiEnd = 122;
      blockEnable = true;
    }

    Random rand = new Random();
    int randomNumber;
    for (int i = 0; i < length; i++) {
      do {
        randomNumber = rand.nextInt((asciiEnd - asciiBeg)) + asciiBeg;
      } while (blockEnable && randomNumber >= blockBeg && randomNumber <= blockEnd);
      randomString[i] = (char) randomNumber;
    }

    return charToString(randomString);
  }
  
  public ArrayList<ArrayList<String>> allAnagrams() {
    return allAnagrams(1);
  }
  
  public ArrayList<ArrayList<String>> allAnagrams(int minLength) {
    ArrayList<String> allPermutations = permutationDynamicProgramming();
    ArrayList<ArrayList<String>> anagrams = new ArrayList<ArrayList<String>>();
    if (this.dictionary == null) {
      buildDictionary();
    }
    for(String everyPermutation:allPermutations) {
      if(this.dictionary.search(everyPermutation)) {
        ArrayList<String> fullWord = new ArrayList<String>();
        fullWord.add(everyPermutation);
        anagrams.add(fullWord);
      }
      ArrayList<ArrayList<String>> allPossibleSplits = splitSpace(everyPermutation);
      for(ArrayList<String> eachList:allPossibleSplits) {
        for(int i=0; i < eachList.size();i++) {
          //don't give anything whose length is less than "minLength characters
          if((eachList.get(i).length() < minLength) || (eachList.get(i).length() == everyPermutation.length()) || (!this.dictionary.search(eachList.get(i)))) {
            break;
          }
          if(i == eachList.size()-1) {
            anagrams.add(eachList);
          }
        }
      }
    }
    return anagrams;
  }
  
  ArrayList<String> tempCombinations = new ArrayList<String>();
  ArrayList<String> elements = new ArrayList<String>();
  ArrayList<ArrayList<String>> allSplits = new ArrayList<ArrayList<String>>();

  public ArrayList<ArrayList<String>> splitSpace(String eachString) {
    StringBuilder each = new StringBuilder(eachString);
    //System.out.print(eachString + ",");
    elements.add(eachString);
    
    //System.out.println();
    allSplits.add(elements);
    //can't use elements.clear() here
    elements = new ArrayList<String>();
    
    for (int i = 1; i < each.length(); i++) {
      for (String temps : tempCombinations) {
        //System.out.print(temps + ",");
        elements.add(temps);
      }
      
      //System.out.print(each.substring(0, i) + ",");
      elements.add(each.substring(0, i));
      
      tempCombinations.add(each.substring(0, i));
      splitSpace(each.substring(i, each.length()));
    }
    if (tempCombinations.size() > 0)
      tempCombinations.remove(tempCombinations.size() - 1);
    
    return allSplits;
  }
  
  @Override
  public boolean equals(Object o) {
    StringLab toCheck = (StringLab)o;
    return this.string.equals(toCheck.string);  
  }
  
  @Override
  public int hashCode() {
    return this.string.hashCode();
  }
  
  /*
   * Although the elegant way is the below code, I've implemented it from scrratch
   * 
   boolean isRotation(String s1,String s2) {
      return (s1.length() == s2.length()) && ((s1+s1).indexOf(s2) != -1);
   }
   */
  
  public int isRotation(String input) {
    char[] stringArr = this.string.toCharArray();
    char[] inputArr = input.toCharArray();
    if(stringArr.length != inputArr.length)
      return -1;
    char[] doubled = new char[2 * input.length()];
    for(int i = 0; i < doubled.length; i++) {
      doubled[i] = i < inputArr.length?inputArr[i]:doubled[i-inputArr.length]; 
    }
    int k=0;
    int startingIndex = -1;
    for(int i=0; i < inputArr.length;i++) {
      k=0;
      for(int j=i; j < inputArr.length;j++) {
        if(doubled[j] != stringArr[k++])
          break;
        if(j == inputArr.length - 1) {
          startingIndex = i;
          return startingIndex;
        }
      }
    }
    return startingIndex;
  }
  
  //Not using any Tokenizer calls
  //Minimum requirement for an email id is taken as a@b.c
  public boolean isValidEmailAddress() {
    int[] isValid = generateAsciiArrayForLocalPart();
    boolean valid = false;
    char[] inputArr = this.string.toCharArray();
    if(inputArr.length < 5) {
      return valid;
    }
    int atCount = 0;
    int atPosition = -1;
    for(int i=0; i < inputArr.length; i++) {
      if(inputArr[i] == '@') {
        atCount++;
        if(atCount > 1 || ((i==0 || i >= inputArr.length - 3) && (inputArr[i] == '@')) || ((i == inputArr.length - 1) && (atCount == 0))) {
          System.out.println("Multiple or no @");
          return valid;
        }
        atPosition = i;
      }
    }
    char[]  localPart = new char[atPosition];
    char[]  domainPart = new char[inputArr.length - atPosition - 1];
    int temp =0;
    //split it into local n domain parts
    for(int i=0; i < inputArr.length; i++) {
      if(i < atPosition)
        localPart[i] = inputArr[i];
      if( i > atPosition)
        domainPart[temp++] = inputArr[i];
    }
    //http://rumkin.com/software/email/rules.php
    if(localPart.length > 63) {
      return valid;
    }
    
    //Check local part first
    //Checking from http://en.wikipedia.org/wiki/Email_address#Local_part
    int numberOfQuotes = 0;
    //will be used to check "comment section ()"
    Stack<Character> commentStack = new Stack<Character>();
    for(int i=0; i < localPart.length; i++) {
      if(isValid[i] == 0) {
        return valid;
      }
      if((i == 0 || i == localPart.length -1 ) && localPart[i] == '.') {
        return valid;
      }
      if(localPart[i] == '.' && localPart[i+1] == '.') {
        return valid;
      }
      if(localPart[i] == '\"') {
        if(i==0 && localPart[localPart.length - 1]!='\"') {
          return valid;
        }
        if(i==localPart.length -1 && localPart[0]!='\"') {
          return valid;
        }
        if(i !=0 && i != localPart.length -1) {
          if(numberOfQuotes % 2 == 0 && localPart[i-1] != '.' || localPart[i + 1] != '.'){
            return valid;
          }
          if(numberOfQuotes % 2 == 1) {
            if(!(localPart[i-1] == '\\' && localPart[i-2] == '\\' && localPart[i-3] == '\\')) {
              return valid;
            }
          }
          numberOfQuotes++;
        }
      }
      if(localPart[i] == '(' && i!=0 && localPart[localPart.length-1]!=')') {
        return valid;
      }
      if(localPart[i] == ')' && i!=localPart.length-1 && localPart[0]!='(') {
        return valid;
      }
      if(localPart[i] == '(') {
        if(commentStack.isEmpty()) {
          commentStack.push('(');
        }
        else {
          return valid;
        }
      }
      if(localPart[i] == ')') {
        if(commentStack.isEmpty() || commentStack.pop() != '(') {
          commentStack.push(')');
          return valid;
        }
        else {
          commentStack.push(')');
        }  
      }
      //if quotes have been closed, return false, as special characters
      //can appear only inside quotes
      if(isValid[localPart[i]] == 2 && numberOfQuotes % 2 == 0) {
        return valid;
      }
      if(localPart[i] == '\\') {
        if(!(localPart[i+1] == '\\' || localPart[i+1] == '\"')) {
          return valid;
        }
      }
      
    } //end of for loop
    //Make sure there is an ending double quote
    if(numberOfQuotes % 2 == 1) {
      return valid;
    }
    
    //Start checking the domain part
    int[] domainValidity = generateAscciForDomainPart();
    commentStack = new Stack<Character>();
    for(int i=0; i < domainPart.length; i++) {
      if(domainValidity[domainPart[i]] == 0) {
        return valid;
      }
      if(i < domainPart.length - 1 && domainPart[i] == '.' && domainPart[i+1] == i) {
        return valid;
      }
      if(domainPart[i] == '[' && i!= 0 && domainPart[domainPart.length - 1] != ']') {
        return valid;
      }
      if(domainPart[i] == ']' && i != domainPart.length -1 && domainPart[0] != '[') {
        return valid;
      }
      if(domainPart[i] == '[') {
        return isValidIp(domainPart);
      }
      if(localPart[i] == '(' && i!=0 && localPart[localPart.length-1]!=')') {
        return valid;
      }
      if(localPart[i] == ')' && i!=localPart.length-1 && localPart[0]!='(') {
        return valid;
      }
      if(localPart[i] == '(') {
        if(commentStack.isEmpty()) {
          commentStack.push('(');
        }
        else {
          return valid;
        }
      }
      if(localPart[i] == ')') {
        if(commentStack.isEmpty() || commentStack.pop() != '(') {
          commentStack.push(')');
          return valid;
        }
        else {
          commentStack.push(')');
        }  
      }
      
    }
    return true;
  }

  private boolean isValidIp(char[] domainPart) {
    boolean validity = false;
    String[] tokensIpv4 = domainPart.toString().split(".");
    String[] tokensIpv6 = domainPart.toString().split(":");
    if(tokensIpv4.length != 4 && (tokensIpv6.length != 4 || tokensIpv6.length != 7)) {
      return validity;
    }
    if(tokensIpv4.length == 4) {
      for(String eachPart:tokensIpv4) {
        int part;
        try {
          part = new StringLab(eachPart).aToI();
          if(part < 0 || part > 255) {
            return validity;
          }
        }
        catch (NumberOverflowException e) {
          e.getMessage();
        }
      }
    }
    //to-do proper IPv6 range check
    return true;
  }

  /*
   * This methd generates the ascii array where the values 
   * represent validity of character at that position in the array
   * to appear in the local part of the email address.
   * 0 - not permitted
   * 1 - valid
   * 2 - special character
   */
  private int[] generateAsciiArrayForLocalPart() {
    int[] asciiArray = new int[256];
    //A to Z
    for(int i= 65; i <= 90; i++) {
      asciiArray[i] = 1;
    }
    //a to z
    for(int i= 97; i <= 122; i++) {
      asciiArray[i] = 1;
    }
    //0 to 9
    for(int i= 48; i <= 57; i++) {
      asciiArray[i] = 1;
    }
    //non alpha numeric allowed characters
    asciiArray[33] = 1;
    asciiArray[35] = 1;
    asciiArray[36] = 1;
    asciiArray[37] = 1;
    asciiArray[38] = 1;
    asciiArray[39] = 1;
    asciiArray[42] = 1;
    asciiArray[43] = 1;
    asciiArray[45] = 1;
    asciiArray[61] = 1;
    asciiArray[63] = 1;
    asciiArray[94] = 1;
    asciiArray[95] = 1;
    asciiArray[96] = 1;
    asciiArray[123] = 1;
    asciiArray[124] = 1;
    asciiArray[125] = 1;
    asciiArray[126] = 1;
    asciiArray[34] = 1;
    asciiArray[40] = 1;
    asciiArray[41] = 1;
    
    //special characters
    asciiArray[32] = 2;
    //asciiArray[34] = 2; // shouldn't treat this as special character, strictly
    //asciiArray[40] = 2;// '(' is not a special character in strict sense
    //asciiArray[41] = 2;//  '(' is not a special character in strict sense
    asciiArray[44] = 2;
    asciiArray[58] = 2;
    asciiArray[59] = 2;
    asciiArray[60] = 2;
    asciiArray[62] = 2;
    asciiArray[64] = 2;
    asciiArray[91] = 2;
    asciiArray[92] = 2;
    asciiArray[93] = 2;
    
    return asciiArray;
  }
  
  private int[]  generateAscciForDomainPart() {
    int[] asciiArray = new int[256];
  //A to Z
    for(int i= 65; i <= 90; i++) {
      asciiArray[i] = 1;
    }
    //a to z
    for(int i= 97; i <= 122; i++) {
      asciiArray[i] = 1;
    }
    //0 to 9
    for(int i= 48; i <= 57; i++) {
      asciiArray[i] = 1;
    }
    //. and hiphen are valid
    asciiArray[45] = 1;
    asciiArray[56] = 1;
    
    //comments are valid too, but at the beginning and at end
    asciiArray[40] = 1;
    asciiArray[41] = 1;
    
    // '[', ']' and ':' are special, used in IP format names
    asciiArray[91] = 2;
    asciiArray[93] = 2;
    //colon is still set to zero because it should appear only INSIDE an ip address.
    //Not necessary to set this explicitly, though.
    asciiArray[58] = 0;
    return asciiArray;
  }
  
  /*Changes all the lower case letters in the string to upper case letters.
   * Doesn't modify non-uppercase letters
  */
  public String toUpperCase() {
    char[] stringArr = this.string.toCharArray();
    for(int i =0; i < stringArr.length; i++) {
      if(stringArr[i] - 'a' >= 0 &&  stringArr[i] - 'a' < 26) {
        stringArr[i] = (char)(stringArr[i] - 32);
      }
    }
    return new StringLab().charToString(stringArr);
  }
  
  public String toLowerCase() {
    char[] stringArr = this.string.toCharArray();
    for(int i =0; i < stringArr.length; i++) {
      if(stringArr[i] - 'A' >= 0 &&  stringArr[i] - 'A' < 26) {
        stringArr[i] = (char)(stringArr[i] + 32);
      }
    }
    return new StringLab().charToString(stringArr);
  }
  
  public String swichCase() {
    char[] stringArr = this.string.toCharArray();
    for(int i =0; i < stringArr.length; i++) {
      if(stringArr[i] - 'A' >= 0 &&  stringArr[i] - 'A' < 26) {
        stringArr[i] = (char)(stringArr[i] + 32);
      }
      else if(stringArr[i] - 'a' >= 0 &&  stringArr[i] - 'a' < 26) {
        stringArr[i] = (char)(stringArr[i] - 32);
      }
    }
    return new StringLab().charToString(stringArr);
  }
  
  public int matchTrivial(String pattern) {
    int matchIndex = -1;
    char[] haystack = string.toCharArray();
    char[] needle = pattern.toCharArray();
    for(int i=0; i <= haystack.length; i++) {
      for(int j=0; j <= needle.length; j++) {
        if(j == needle.length) {
          matchIndex = i - needle.length;
          return matchIndex;
        }
        if(i == haystack.length) {
          return matchIndex;
        }
        if(haystack[i] == needle[j]) {
          i++;
          continue;
        }
        j = 0;
        i = i -j +1;
      }
    }
    return matchIndex;
  }
}
