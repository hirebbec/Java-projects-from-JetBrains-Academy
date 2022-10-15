package minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Map {
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();
    private int x = 9;
    private int y = 9;
    private int _minesCount;
    private int minesOnMap = 0;
    private char[][] gameMap;
    private char[][] visibleMap;

    Map(){
        generateMap();
    };

    private void generateMap() {
        System.out.print("How many mines do you want on the field? ");
        _minesCount = scanner.nextInt();
        int k = 0;
        gameMap = new char[y][x];
        visibleMap = new char[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                gameMap[i][j] = '.';
                visibleMap[i][j] = '.';
            }
        }
        printMap();
        System.out.println("Set/unset mines marks or claim a cell as free:");
        int _j = scanner.nextInt();
        int _i = scanner.nextInt();
        String tmp = scanner.next();
        while (minesOnMap < _minesCount) {
            int i = random.nextInt(9);
            int j = random.nextInt(9);
            if (gameMap[i][j] == '.' && !(i == _i - 1 && _j - 1 == j)) {
                gameMap[i][j] = 'X';
                minesOnMap++;
            }
            System.out.println(minesOnMap);
        }
        if (tmp.equals("free")) {
            free(_i - 1, _j - 1);
        } else {
            mine(_i - 1, _j - 1);
        }
    }

    public void updateMap(){
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (gameMap[i][j] != 'X') {
                    if (numberMinesNearby(i, j) > 0) {
                        gameMap[i][j] = (char)(numberMinesNearby(i, j) + '0');
                    }
                }
            }
        }
    }

    public void printMap() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < y; i++) {
            System.out.print((i + 1) + "|");
            System.out.print(visibleMap[i]);
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    private int numberMinesNearby(int y, int x) {
        if (visibleMap[y][x] == '/' ) {
            return -1;
        }
        if (x == 0 && y == 0) {
            return mineDetector(gameMap[y][x + 1]) + mineDetector(gameMap[y + 1][x]) + mineDetector(gameMap[y + 1][x + 1]);
        } else if (x == 8 && y == 8) {
            return mineDetector(gameMap[y][x - 1]) + mineDetector(gameMap[y - 1][x]) + mineDetector(gameMap[y - 1][x - 1]);
        } else if (x == 0 && y == 8) {
            return mineDetector(gameMap[y - 1][x]) + mineDetector(gameMap[y][x + 1]) + mineDetector(gameMap[y - 1][x + 1]);
        } else if (x == 8 && y == 0) {
            return mineDetector(gameMap[y][x - 1]) + mineDetector(gameMap[y + 1][x]) + mineDetector(gameMap[y + 1][x - 1]);
        } else if (y == 0) {
            return mineDetector(gameMap[y][x + 1]) + mineDetector(gameMap[y + 1][x]) + mineDetector(gameMap[y + 1][x + 1]) + mineDetector(gameMap[y][x - 1]) + mineDetector(gameMap[y + 1][x - 1]);
        } else if (x == 0) {
            return mineDetector(gameMap[y][x + 1]) + mineDetector(gameMap[y + 1][x]) + mineDetector(gameMap[y + 1][x + 1]) + mineDetector(gameMap[y - 1][x]) + mineDetector(gameMap[y - 1][x + 1]);
        } else if (x == 8) {
            return mineDetector(gameMap[y][x - 1]) + mineDetector(gameMap[y - 1][x]) + mineDetector(gameMap[y - 1][x - 1]) + mineDetector(gameMap[y + 1][x - 1]) + mineDetector(gameMap[y + 1][x]);
        } else if (y == 8) {
            return mineDetector(gameMap[y][x - 1]) + mineDetector(gameMap[y - 1][x]) + mineDetector(gameMap[y - 1][x - 1]) + mineDetector(gameMap[y - 1][x + 1]) + mineDetector(gameMap[y][x + 1]);
        } else {
            return mineDetector(gameMap[y][x - 1]) + mineDetector(gameMap[y - 1][x]) + mineDetector(gameMap[y - 1][x - 1]) + mineDetector(gameMap[y - 1][x + 1]) + mineDetector(gameMap[y][x + 1]) +
                    mineDetector(gameMap[y + 1][x - 1]) + mineDetector(gameMap[y + 1][x]) + mineDetector(gameMap[y + 1][x + 1]);
        }
    }

    private int mineDetector(char c) {
        if (c == 'X') {
            return 1;
        }
        return 0;
    }

    public void StartGame() {
//        endGame();
        while (!checkWin()) {
            printMap();
            System.out.println("Set/unset mines marks or claim a cell as free:");
            int j = scanner.nextInt();
            int i = scanner.nextInt();
            i--;
            j--;
            if (scanner.next().equals("free")) {
                if (!free(i, j)) {
                    return;
                }
            } else {
                mine(i, j);
            }
        }
//        endGame();
        printMap();
        System.out.println("Congratulations! You found all the mines!");
    }

    private boolean checkWin() {
        int minesCount = 0;
        int freeCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameMap[i][j] == 'X' && visibleMap[i][j] == '*') {
                    minesCount++;
                } else if (gameMap[i][j] != 'X' && visibleMap[i][j] != '*' && visibleMap[i][j] != '.') {
                    freeCount++;
                }
            }
        }
        if (minesCount == _minesCount) {
            return true;
        } else if (81 - _minesCount == freeCount) {
            return true;
        }
        return false;
    }

    private boolean free(int i, int j) {
        if (gameMap[i][j] == 'X'){
            endGame();
            printMap();
            System.out.println("You stepped on a mine and failed!");
            return false;
        } else {
            if (numberMinesNearby(i, j) == 0) {
                visibleMap[i][j] = '/';
                openNearby(i, j);
            } else {
                visibleMap[i][j] = (char)(numberMinesNearby(i, j) + '0');
            }
        }
        return true;
    }

    private void endGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameMap[i][j] == 'X') {
                    visibleMap[i][j] = 'X';
                }
            }
        }
    }

    private void openNearby(int y, int x) {
//        System.out.println(x + 1 + " " + (y + 1) + " = " + visibleMap[y][x]);
//        printMap();
//        scanner.next();
        if (x == 0 && y == 0) {
            if (numberMinesNearby(y, x + 1) == 0) {
                visibleMap[y][x + 1] = '/';
                openNearby(y, x + 1);
            } else if (numberMinesNearby(y, x + 1) > 0){
                visibleMap[y][x + 1] = (char) (numberMinesNearby(y, x + 1) + '0');
            }
            if (numberMinesNearby(y + 1, x) == 0) {
                visibleMap[y + 1][x] = '/';
                openNearby(y + 1, x);
            } else if (numberMinesNearby(y + 1, x) > 0){
                visibleMap[y + 1][x] = (char) (numberMinesNearby(y + 1, x) + '0');
            }
            if (numberMinesNearby(y + 1, x + 1) == 0) {
                visibleMap[y + 1][x + 1] = '/';
                openNearby(y + 1, x + 1);
            } else if (numberMinesNearby(y + 1, x + 1) > 0){
                visibleMap[y + 1][x + 1] = (char) (numberMinesNearby(y + 1, x + 1) + '0');
            }
        } else if (x == 8 && y == 8) {
            if (numberMinesNearby(y, x - 1) == 0) {
                visibleMap[y][x - 1] = '/';
                openNearby(y, x - 1);
            } else if (numberMinesNearby(y, x - 1) > 0){
                visibleMap[y][x - 1] = (char) (numberMinesNearby(y, x - 1) + '0');
            }
            if (numberMinesNearby(y - 1, x) == 0) {
                visibleMap[y - 1][x] = '/';
                openNearby(y - 1, x);
            } else if (numberMinesNearby(y - 1, x) > 0){
                visibleMap[y - 1][x] = (char) (numberMinesNearby(y - 1, x) + '0');
            }
            if (numberMinesNearby(y - 1, x - 1) == 0) {
                visibleMap[y - 1][x - 1] = '/';
                openNearby(y - 1, x - 1);
            } else if (numberMinesNearby(y - 1, x - 1) > 0){
                visibleMap[y - 1][x - 1] = (char) (numberMinesNearby(y - 1, x - 1) + '0');
            }
        } else if (x == 0 && y == 8) {
            if (numberMinesNearby(y - 1, x) == 0) {
                visibleMap[y - 1][x] = '/';
                openNearby(y - 1, x);
            } else if (numberMinesNearby(y - 1, x) > 0){
                visibleMap[y - 1][x] = (char) (numberMinesNearby(y - 1, x) + '0');
            }
            if (numberMinesNearby(y, x + 1) == 0) {
                visibleMap[y][x + 1] = '/';
                openNearby(y, x + 1);
            } else if (numberMinesNearby(y, x + 1) > 0){
                visibleMap[y][x + 1] = (char) (numberMinesNearby(y, x + 1) + '0');
            }
            if (numberMinesNearby(y - 1, x + 1) == 0) {
                visibleMap[y - 1][x + 1] = '/';
                openNearby(y - 1, x + 1);
            } else if (numberMinesNearby(y - 1, x + 1) > 0){
                visibleMap[y - 1][x + 1] = (char) (numberMinesNearby(y - 1, x + 1) + '0');
            }
        } else if (x == 8 && y == 0) {
            if (numberMinesNearby(y, x - 1) == 0) {
                visibleMap[y][x - 1] = '/';
                openNearby(y, x - 1);
            } else if (numberMinesNearby(y, x - 1) > 0){
                visibleMap[y][x - 1] = (char) (numberMinesNearby(y, x - 1) + '0');
            }
            if (numberMinesNearby(y + 1, x) == 0) {
                visibleMap[y + 1][x] = '/';
                openNearby(y + 1, x);
            } else if (numberMinesNearby(y + 1, x) > 0){
                visibleMap[y + 1][x] = (char) (numberMinesNearby(y + 1, x) + '0');
            }
            if (numberMinesNearby(y + 1, x - 1) == 0) {
                visibleMap[y + 1][x - 1] = '/';
                openNearby(y + 1, x - 1);
            } else if (numberMinesNearby(y + 1, x - 1) > 0){
                visibleMap[y + 1][x - 1] = (char) (numberMinesNearby(y + 1, x - 1) + '0');
            }
        } else if (y == 0) {
            if (numberMinesNearby(y, x + 1) == 0) {
                visibleMap[y][x + 1] = '/';
                openNearby(y, x + 1);
            } else if (numberMinesNearby(y, x + 1) > 0){
                visibleMap[y][x + 1] = (char) (numberMinesNearby(y, x + 1) + '0');
            }
            if (numberMinesNearby(y + 1, x) == 0) {
                visibleMap[y + 1][x] = '/';
                openNearby(y + 1, x);
            } else if (numberMinesNearby(y + 1, x) > 0){
                visibleMap[y + 1][x] = (char) (numberMinesNearby(y + 1, x) + '0');
            }
            if (numberMinesNearby(y + 1, x + 1) == 0) {
                visibleMap[y + 1][x + 1] = '/';
                openNearby(y + 1, x + 1);
            } else if (numberMinesNearby(y + 1, x + 1) > 0){
                visibleMap[y + 1][x + 1] = (char) (numberMinesNearby(y + 1, x + 1) + '0');
            }
            if (numberMinesNearby(y, x - 1) == 0) {
                visibleMap[y][x- 1] = '/';
                openNearby(y, x - 1);
            } else if (numberMinesNearby(y, x - 1) > 0){
                visibleMap[y][x - 1] = (char) (numberMinesNearby(y, x - 1) + '0');
            }
            if (numberMinesNearby(y + 1, x - 1) == 0) {
                visibleMap[y + 1][x - 1] = '/';
                openNearby(y + 1, x - 1);
            } else if (numberMinesNearby(y + 1, x - 1) > 0){
                visibleMap[y + 1][x - 1] = (char) (numberMinesNearby(y + 1, x - 1) + '0');
            }
        } else if (x == 0) {
            if (numberMinesNearby(y, x + 1) == 0) {
                visibleMap[y][x + 1] = '/';
                openNearby(y, x + 1);
            } else if (numberMinesNearby(y, x + 1) > 0){
                visibleMap[y][x + 1] = (char) (numberMinesNearby(y, x + 1) + '0');
            }
            if (numberMinesNearby(y + 1, x) == 0) {
                visibleMap[y + 1][x] = '/';
                openNearby(y + 1, x);
            } else if (numberMinesNearby(y + 1, x) > 0){
                visibleMap[y + 1][x] = (char) (numberMinesNearby(y + 1, x) + '0');
            }
            if (numberMinesNearby(y + 1, x + 1) == 0) {
                visibleMap[y + 1][x + 1] = '/';
                openNearby(y + 1, x + 1);
            } else if (numberMinesNearby(y + 1, x + 1) > 0){
                visibleMap[y + 1][x + 1] = (char) (numberMinesNearby(y + 1, x + 1) + '0');
            }
            if (numberMinesNearby(y - 1, x) == 0) {
                visibleMap[y - 1][x] = '/';
                openNearby(y - 1, x);
            } else if (numberMinesNearby(y - 1, x) > 0){
                visibleMap[y - 1][x] = (char) (numberMinesNearby(y - 1, x) + '0');
            }
            if (numberMinesNearby(y - 1, x + 1) == 0) {
                visibleMap[y - 1][x + 1] = '/';
                openNearby(y - 1, x + 1);
            } else if (numberMinesNearby(y - 1, x + 1) > 0){
                visibleMap[y - 1][x + 1] = (char) (numberMinesNearby(y - 1, x + 1) + '0');
            }
        } else if (x == 8) {
            if (numberMinesNearby(y, x - 1) == 0) {
                visibleMap[y][x - 1] = '/';
                openNearby(y, x - 1);
            } else if (numberMinesNearby(y, x - 1) > 0){
                visibleMap[y][x - 1] = (char) (numberMinesNearby(y, x - 1) + '0');
            }
            if (numberMinesNearby(y - 1, x) == 0) {
                visibleMap[y - 1][x] = '/';
                openNearby(y - 1, x);
            } else if (numberMinesNearby(y - 1, x) > 0){
                visibleMap[y - 1][x] = (char) (numberMinesNearby(y - 1, x) + '0');
            }
            if (numberMinesNearby(y - 1, x - 1) == 0) {
                visibleMap[y - 1][x - 1] = '/';
                openNearby(y - 1, x - 1);
            } else if (numberMinesNearby(y - 1, x - 1) > 0){
                visibleMap[y - 1][x - 1] = (char) (numberMinesNearby(y - 1, x - 1) + '0');
            }
            if (numberMinesNearby(y + 1, x - 1) == 0) {
                visibleMap[y + 1][x - 1] = '/';
                openNearby(y + 1, x - 1);
            } else if (numberMinesNearby(y + 1, x - 1) > 0){
                visibleMap[y + 1][x - 1] = (char) (numberMinesNearby(y + 1, x - 1) + '0');
            }
            if (numberMinesNearby(y + 1, x) == 0) {
                visibleMap[y + 1][x] = '/';
                openNearby(y + 1, x);
            } else if (numberMinesNearby(y + 1, x) > 0){
                visibleMap[y + 1][x] = (char) (numberMinesNearby(y + 1, x) + '0');
            }
        } else if (y == 8) {
            if (numberMinesNearby(y, x - 1) == 0) {
                visibleMap[y][x - 1] = '/';
                openNearby(y, x - 1);
            } else if (numberMinesNearby(y, x - 1) > 0){
                visibleMap[y][x - 1] = (char) (numberMinesNearby(y, x - 1) + '0');
            }
            if (numberMinesNearby(y - 1, x) == 0) {
                visibleMap[y - 1][x] = '/';
                openNearby(y - 1, x);
            } else if (numberMinesNearby(y - 1, x) > 0){
                visibleMap[y - 1][x] = (char) (numberMinesNearby(y - 1, x) + '0');
            }
            if (numberMinesNearby(y - 1, x - 1) == 0) {
                visibleMap[y - 1][x - 1] = '/';
                openNearby(y - 1, x - 1);
            } else if (numberMinesNearby(y - 1, x - 1) > 0){
                visibleMap[y - 1][x - 1] = (char) (numberMinesNearby(y - 1, x - 1) + '0');
            }
            if (numberMinesNearby(y - 1, x + 1) == 0) {
                visibleMap[y - 1][x + 1] = '/';
                openNearby(y - 1, x + 1);
            } else if (numberMinesNearby(y - 1, x + 1) > 0){
                visibleMap[y - 1][x + 1] = (char) (numberMinesNearby(y - 1, x + 1) + '0');
            }
            if (numberMinesNearby(y, x + 1) == 0) {
                visibleMap[y][x + 1] = '/';
                openNearby(y, x + 1);
            } else if (numberMinesNearby(y, x + 1) > 0){
                visibleMap[y][x + 1] = (char) (numberMinesNearby(y, x + 1) + '0');
            }
        } else {
            if (numberMinesNearby(y, x - 1) == 0) {
                visibleMap[y][x - 1] = '/';
                openNearby(y, x - 1);
            } else if (numberMinesNearby(y, x - 1) > 0){
                visibleMap[y][x - 1] = (char) (numberMinesNearby(y, x - 1) + '0');
            }
            if (numberMinesNearby(y - 1, x) == 0) {
                visibleMap[y - 1][x] = '/';
                openNearby(y - 1, x);
            } else if (numberMinesNearby(y - 1, x) > 0){
                visibleMap[y - 1][x] = (char) (numberMinesNearby(y - 1, x) + '0');
            }
            if (numberMinesNearby(y - 1, x - 1) == 0) {
                visibleMap[y - 1][x - 1] = '/';
                openNearby(y - 1, x - 1);
            } else if (numberMinesNearby(y - 1, x - 1) > 0){
                visibleMap[y - 1][x - 1] = (char) (numberMinesNearby(y - 1, x - 1) + '0');
            }
            if (numberMinesNearby(y - 1, x + 1) == 0) {
                visibleMap[y - 1][x + 1] = '/';
                openNearby(y - 1, x + 1);
            } else if (numberMinesNearby(y - 1, x + 1) > 0){
                visibleMap[y - 1][x + 1] = (char) (numberMinesNearby(y - 1, x + 1) + '0');
            }
            if (numberMinesNearby(y, x + 1) == 0) {
                visibleMap[y][x + 1] = '/';
                openNearby(y, x + 1);
            } else if (numberMinesNearby(y, x + 1) > 0){
                visibleMap[y][x + 1] = (char) (numberMinesNearby(y, x + 1) + '0');
            }
            if (numberMinesNearby(y + 1, x - 1) == 0) {
                visibleMap[y + 1][x - 1] = '/';
                openNearby(y + 1, x - 1);
            } else if (numberMinesNearby(y + 1, x - 1) > 0){
                visibleMap[y + 1][x - 1] = (char) (numberMinesNearby(y + 1, x - 1) + '0');
            }
            if (numberMinesNearby(y + 1, x) == 0) {
                visibleMap[y + 1][x] = '/';
                openNearby(y + 1, x);
            } else if (numberMinesNearby(y + 1, x) > 0){
                visibleMap[y + 1][x] = (char) (numberMinesNearby(y + 1, x) + '0');
            }
            if (numberMinesNearby(y + 1, x + 1) == 0) {
                visibleMap[y + 1][x + 1] = '/';
                openNearby(y + 1, x + 1);
            } else if (numberMinesNearby(y + 1, x + 1) > 0){
                visibleMap[y + 1][x + 1] = (char) (numberMinesNearby(y + 1, x + 1) + '0');
            }
        }
    }

    private void mine(int i, int j) {
        if (visibleMap[i][j] == '*') {
            visibleMap[i][j] = '.';
        } else {
            visibleMap[i][j] = '*';
        }
    }
}
