package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class Finder {
    ArrayList<Information> list;
    ArrayList<String> allWords = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> map = new HashMap<>();

    public Finder(String argc) throws FileNotFoundException {
        this.list = new ArrayList<Information>();
        collectData(argc);
        smartStorage();
    }

    //Collection data
    private void collectData(String fileName) throws FileNotFoundException {
        File inFile = new File(fileName);
        Scanner scanner = new Scanner(inFile);
        while (scanner.hasNext()) {
            addInformation(scanner.nextLine());
        }
        for (Information inf: list) {
            allWords.addAll(inf.getWords());
        }
    }

    private void addInformation(String line) {
        list.add(new Information(line));
    }

    //Searching
    public abstract void search();

    private ArrayList<String> findWord(String word) {
        ArrayList<String> data = new ArrayList<String>();
        for (Information inf: list) {
            if (inf.findWord(word) != null) {
                data.add(inf.findWord(word));
            }
        }
        if (data.size() == 0) {
            data.add("No matching people found.");
        }
        return data;
    }

    //PrintData
    public void printData() {
        for (Information data: list) {
            System.out.println(data.getLine());
        }
        System.out.println();
    }

    private void smartStorage(){
        Scanner scanner = new Scanner(System.in);
        for (String str: allWords) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (Information inf: list) {
                if (inf.findWord(str) != null) {
//                    System.out.println(str + " " + inf.getLine() + " " + list.indexOf(inf));
//                    scanner.nextLine();
                    tmp.add(list.indexOf(inf));
                }
            }
            map.put(str.toUpperCase(Locale.ROOT), tmp);
        }
    }

}











