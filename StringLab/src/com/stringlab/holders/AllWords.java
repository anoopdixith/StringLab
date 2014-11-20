package com.stringlab.holders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.stringlab.holders.TrieDictionary;

public class AllWords {
  public static TrieDictionary buildDictionary() throws IOException {
    TrieDictionary dictionary = new TrieDictionary();
    try(BufferedReader br = new BufferedReader(new FileReader("/Users/anoop/Documents/words.txt"))) {
      String line = "dummybeginningelementqazrtfgt";
      while (line != null) {
        line = br.readLine();
        //System.out.println("inserting " + line);
        dictionary.insert(line);
      }
    }
    return dictionary;
  }
}
