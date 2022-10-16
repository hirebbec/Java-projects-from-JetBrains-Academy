package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Information {
    private String line;
    private ArrayList<String> words;

    public Information(String line) {
        this.line = line;
        this.words = new ArrayList<String>(Arrays.asList(line.split(" ")));
    }

    public String findWord(String word) {
        for (String str: words) {
            if (str.toUpperCase(Locale.ROOT).contains(word.toUpperCase(Locale.ROOT))) {
                return line;
            }
        }
        return null;
    }

    public String getLine() {
        return line;
    }

    public ArrayList<String> getWords() {
        return words;
    }


}
