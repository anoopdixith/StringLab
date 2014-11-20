package com.stringlab.holders;

public class TrieDictionary {
  private Tries root;

  public Tries getRoot() {
    return root;
  }

  public void setRoot(Tries root) {
    this.root = root;
  }

  public TrieDictionary() {
    root = new Tries(' ');
  }

  public void insert(String word) {
    if(word == null) {
      return;
    }
    if (search(word) == true) {
      System.out.println("Word Already there " + word);
      return;
    }
    Tries current = root;
    for (char ch : word.toCharArray()) {
      //System.out.println("char in insert is " + ch);
      Tries child = current.subNode(ch);
      if (child != null) {
        //System.out.println("Child not null");
        current = child;
      } else {
        //System.out.println("Child null");
        current.getChildren().add(new Tries(ch));
        current = current.subNode(ch);
      }
      System.out.println();
      current.count++;
    }
    current.setWord(true);
  }

  public boolean search(String word) {
    Tries current = root;
    //System.out.println("Word is " + word);
    if(word == null) {
      return false;
    }
    for (char ch : word.toCharArray()) {
      //System.out.println("character while searching is " + ch);
      if (current.subNode(ch) == null) {
        //System.out.println("subnode null");
        return false;
      } else {
        current = current.subNode(ch);
      }
    }
    if (current.isWord() == true) {
      return true;
    }
    return false;
  }

  public void remove(String word) {
    if (!search(word)) {
      return;
    }
    Tries current = root;
    for (char ch : word.toCharArray()) {
      Tries child = root.subNode(ch);
      if (child.getCount() == 1) {
        current.getChildren().remove(child);
      } else {
        child.setCount(child.getCount() - 1);
        current = child;
      }
    }
    current.setWord(false);
  }
}
