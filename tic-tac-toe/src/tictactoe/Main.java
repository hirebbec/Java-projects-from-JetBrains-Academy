package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static boolean winCheck(char[][] array, char symbol) {
        for (int i = 0; i < 3; i++){
            if (array[i][0] == symbol && array[i][1] == symbol && array[i][2] == symbol) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++){
            if (array[0][i] == symbol && array[1][i] == symbol && array[2][i] == symbol) {
                return true;
            }
        }
        if (array[1][1] == symbol && array[0][0] == symbol && array[2][2] == symbol) {
            return true;
        }
        if (array[1][1] == symbol && array[0][2] == symbol && array[2][0] == symbol) {
            return true;
        }
        return false;
    }

    public static void printer(char[][] array) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static void turn(char[][] array, char sym) {
        Scanner scanner = new Scanner(System.in);
        String tmp;
        System.out.println("Please enter coordinates");
        int i, j;
        while (true) {
            try {
                i = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
                continue;
            }
            try {
                j = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
                continue;
            }
            if (i < 1 || i > 3 | j < 1 || j > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (array[i - 1][j - 1] == 'O' || array[i - 1][j - 1] == 'X') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            break;
        }
        array[i - 1][j - 1] = sym;
    }

    public static boolean checker(char[][] array) {
        if (winCheck(array, 'O') == true) {
            System.out.println("O wins");
            return true;
        } else if (winCheck(array, 'X') == true) {
            System.out.println("X wins");
            return true;
        }
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (array[i][j] != ' ') {
                    count++;
                }
            }
        }
        if (count == 9) {
            System.out.println("Draw");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        char sym = 'X';
        char[][] array = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = ' ';
            }
        }
        while (checker(array) == false) {
            printer(array);
            turn(array, sym);
            if (sym == 'X') {
                sym = 'O';
            } else {
                sym = 'X';
            }
            printer(array);
        }
    }
}