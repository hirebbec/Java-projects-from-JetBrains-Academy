package search;

import javax.script.ScriptContext;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Finder selectStrategy(String argc) throws FileNotFoundException {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        if (str.equals("ANY")) {
            return new anyFinder(argc);
        } else if (str.equals("ALL")) {
            return new allFinder(argc);
        } else if (str.equals("NONE")) {
            return new noneFinder(argc);
        }
        else return null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Menu menu = new Menu();
        Finder finder = new anyFinder(args[1]);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            menu.printMenu();
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1:
                    finder = selectStrategy(args[1]);
                    finder.search();
                    break;
                case 2:
                    finder.printData();
                    break;
                case 0:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }
}

/*
Dwight Joseph djo@gmail.com
Rene Webb webb@gmail.com
Katie Jacobs
Erick Harrington harrington@gmail.com
Myrtle Medina
Erick Burgess
 */