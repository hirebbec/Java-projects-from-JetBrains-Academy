package battleship;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        int turn = 1;
        Scanner scanner = new Scanner(System.in);
        Map map1 = new Map();
        Map map2 = new Map();
        System.out.println("Player 1, place your ships on the game field");
        map1.pasteShips();
        map1.printMap();
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        System.out.println("Player 2, place your ships on the game field");
        map2.pasteShips();
        map2.printMap();
        map1.theGameIsStarts();
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        while (true) {
            if (turn == 1) {
                map2.printGameMap();
                System.out.println("---------------------");
                map1.printMap();
                System.out.println("Player 1, it's your turn:");
                map2.shot();

                if (map2.checkWin()) {
                    break;
                }
                turn = 2;
            } else if (turn == 2) {
                map1.printGameMap();
                System.out.println("---------------------");
                map2.printMap();
                System.out.println("Player 2, it's your turn:");
                map1.shot();
                if (map1.checkWin()) {
                    break;
                }
                turn = 1;
            }
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
}