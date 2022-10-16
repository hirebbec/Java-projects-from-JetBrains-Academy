package search;

import java.io.FileNotFoundException;
import java.util.*;

public class allFinder extends Finder {
    public allFinder(String argc) throws FileNotFoundException {
        super(argc);
    }

    @Override
    public void search() {
        ArrayList<String> searchWords;
        HashMap<Integer, Integer> lines = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a name or email to search all suitable people.");
        Scanner scanner1 = new Scanner(System.in);
        String str = scanner1.nextLine();
        searchWords = new ArrayList<String>(Arrays.asList(str.split(" ")));
        for (String word: searchWords) {
            if (map.containsKey(word.toUpperCase(Locale.ROOT))) {
                for (Integer i : map.get(word.toUpperCase(Locale.ROOT))) {
                    if (!lines.containsKey(i)) {
                        lines.put(i, 1);
                    } else {
                        lines.replace(i, lines.get(i) + 1);
                    }
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (lines.containsKey(i) && lines.get(i) == searchWords.size()){
                System.out.println(list.get(i).getLine());
            }
        }
    }
}
