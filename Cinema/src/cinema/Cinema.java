package cinema;

import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    public static void cinemaPrinter(char[][] array) {
        System.out.print("\nCinema:\n  ");
        for (int i = 0; i < array[0].length; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (j == 0) {
                    System.out.print(i + 1 + " ");
                }
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static char[][] cinemaCreate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int y = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int x = scanner.nextInt();
        char[][] array = new char[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                array[i][j] = 'S';
            }
        }
        return array;
    }

    public static int profitCounter(char[][] array) {
        if (array.length * array[0].length <= 60) {
            return (10 * array.length * array[0].length);
        } else {
            double tmp = Double.valueOf((double) array[0].length / 2.0);
            return (int) ((Math.floor(tmp) * 10 + (Math.ceil(tmp)) * 8) * array.length);
        }
    }

    public static int getPrice(char[][] array, int i, int j) {
        if (array[0].length * array.length <= 60) {
            return 10;
        } else {
            double tmp = (double) array[0].length / 2.0;
            System.err.println(Math.floor(tmp));
            if (i <= Math.floor(tmp)) {
                return 10;
            } else {
                return 8;
            }
        }
    }

    public static void buyTicket(char[][] array) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a row number:");
            int i = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int j = scanner.nextInt();
            if (j < 1 || j > array[0].length || i < 1 || i > array.length) {
                System.out.println("Wrong input!");
                continue;
            }
            if (array[i - 1][j - 1] == 'B') {
                System.out.println("That ticket has already been purchased!\n");
                continue;
            }
            array[i - 1][j - 1] = 'B';
            System.out.println("\nTicket price: $" + getPrice(array, i , j) + '\n');
            break;
        }
    }

    public static void menu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static void statistic(char[][] array) {
        int purchasedTickets = 0;
        int freeTickets = 0;
        int currentBank = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array[0].length - 1; j++) {
                if (array[i][j] == 'B') {
                    purchasedTickets++;
                    currentBank += getPrice(array, i + 1, j + 1);
                } else {
                    freeTickets++;
                }
            }
        }
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: ");
        System.out.printf("%.2f%%\n", (double)purchasedTickets / (double)(purchasedTickets + freeTickets) * 100);
        System.out.println("Current income: $" + currentBank);
        System.out.println("Total income: $" + profitCounter(array) + '\n');
    }

    public static void main(String[] args) {
        char[][] array = cinemaCreate();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            menu();
            int num = scanner.nextInt();
            switch (num) {
                case 1:
                    cinemaPrinter(array);
                    break;
                case 2:
                    buyTicket(array);
                    break;
                case 3:
                    statistic(array);
                    break;
                case 0:
                    return;
            }
        }
    }
}