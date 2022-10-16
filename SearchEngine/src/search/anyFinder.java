package search;

import java.io.FileNotFoundException;
import java.util.*;

public class anyFinder extends Finder{
    public anyFinder(String argc) throws FileNotFoundException {
        super(argc);
    }

    @Override
    public void search() {
        ArrayList<String> searchWords;
        HashSet<String> lines = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a name or email to search all suitable people.");
        Scanner scanner1 = new Scanner(System.in);
        String str = scanner1.nextLine();
        searchWords = new ArrayList<String>(Arrays.asList(str.split(" ")));
        for (String word: searchWords) {
            if (map.containsKey(word.toUpperCase(Locale.ROOT))) {
                for (Integer i : map.get(word.toUpperCase(Locale.ROOT))) {
                    lines.add(list.get(i).getLine());
                }
            }
        }
        for (String line: lines){
            System.out.println(line);
        }
    }
}
