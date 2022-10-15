package battleship;

import java.util.Scanner;

public class Map {
    private char[][] map;
    private char[][] gameMap;

    public Map() {
        map = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = '~';
            }
        }

        gameMap = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameMap[i][j] = '~';
            }
        }
    }

    public void printMap() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 0) {
                    System.out.print((char) ('A' + i) + " ");
                }
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printGameMap() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 0) {
                    System.out.print((char) ('A' + i) + " ");
                }
                System.out.print(gameMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void pasteShips() {
        Scanner scanner = new Scanner(System.in);
        Coordinate coordinate1;
        Coordinate coordinate2;
        int[] ships = {5, 4, 3, 3, 2};
        String[] message = {"Enter the coordinates of the Aircraft Carrier (5 cells):", "Enter the coordinates of the Battleship (4 cells):",
                "Enter the coordinates of the Submarine (3 cells):", "Enter the coordinates of the Cruiser (3 cells):", "Enter the coordinates of the Destroyer (2 cells):"};
        for (int i = 0; i < 5; i++) {
            printMap();
            System.out.println(message[i] + "\n");
            while (true) {
                String c1 = scanner.next();
                String c2 = scanner.next();
                try {
                    coordinate1 = new Coordinate(c1);
                    coordinate2 = new Coordinate(c2);
                    checkLength(coordinate1, coordinate2, ships[i]);
                    checkPosition(coordinate1, coordinate2);
                    printShip(coordinate1, coordinate2);
                    break;
                } catch (ArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
        }
    }

    private void checkLength(Coordinate c1, Coordinate c2, int len) throws ArgumentException {
        if (c1.x == c2.x) {
            if (Math.abs(c1.y - c2.y) == len - 1) {
                return;
            }
        } else if (c2.y == c1.y) {
            if (Math.abs(c1.x - c2.x) == len - 1) {
                return;
            }
        }
        throw new ArgumentException();
    }

    private void checkPosition(Coordinate c1, Coordinate c2) throws ArgumentException {
        if (c1.y == c2.y) {
            for (int i = Math.min(c1.x, c2.x); i < Math.max(c1.x, c2.x) + 1; i++) {
                if (!checkField(i, c1.y)) {
                    throw new ArgumentException();
                }
            }
        } else if (c2.x == c1.x) {
            for (int i = Math.min(c1.y, c2.y); i < Math.max(c1.y, c2.y) + 1; i++) {
                if (!checkField(c2.x, i)) {
                    throw new ArgumentException();
                }
            }
        }
    }

    private boolean checkField(int x, int y) {
        char c = 'O';
        if (map[y][x] == c) {
            return false;
        }
        if (x == 0 && y == 0) {
            if (map[y][x + 1] == c || map[y + 1][x] == c || map[y + 1][x + 1] == c) {
                return false;
            }
        } else if (x == 9 && y == 9) {
            if (map[y][x - 1] == c || map[y - 1][x] == c || map[y - 1][x - 1] == c) {
                return false;
            }
        } else if (x == 0 && y == 9) {
            if (map[y - 1][x] == c || map[y][x + 1] == c || map[y - 1][x + 1] == c) {
                return false;
            }
        } else if (x == 9 && y == 0) {
            if (map[y][x - 1] == c || map[y + 1][x] == c || map[y + 1][x - 1] == c) {
                return false;
            }
        } else if (y == 0) {
            if (map[y][x + 1] == c || map[y + 1][x] == c || map[y + 1][x + 1] == c || map[y][x - 1] == c || map[y + 1][x - 1] == c) {
                return false;
            }
        } else if (x == 0) {
            if (map[y][x + 1] == c || map[y + 1][x] == c || map[y + 1][x + 1] == c || map[y - 1][x] == c || map[y - 1][x + 1] == c) {
                return false;
            }
        } else if (x == 9) {
            if (map[y][x - 1] == c || map[y - 1][x] == c || map[y - 1][x - 1] == c || map[y + 1][x - 1] == c || map[y + 1][x] == c) {
                return false;
            }
        } else if (y == 9) {
            if (map[y][x - 1] == c || map[y - 1][x] == c || map[y - 1][x - 1] == c || map[y - 1][x + 1] == c || map[y][x + 1] == c) {
                return false;
            }
        } else {
            if (map[y][x - 1] == c || map[y - 1][x] == c || map[y - 1][x - 1] == c || map[y - 1][x + 1] == c || map[y][x + 1] == c ||
                    map[y + 1][x - 1] == c || map[y + 1][x] == c || map[y + 1][x + 1] == c) {
                return false;
            }
        }
        return true;
    }

    private void printShip(Coordinate c1, Coordinate c2) {
        if (c1.x == c2.x) {
            for (int i = Math.min(c1.y, c2.y); i < Math.max(c1.y, c2.y) + 1; i++) {
                map[i][c1.x] = 'O';
            }
        } else if (c1.y == c2.y) {
            for (int i = Math.min(c1.x, c2.x); i < Math.max(c1.x, c2.x) + 1; i++) {
                map[c1.y][i] = 'O';
            }
        }
    }

    public void theGameIsStarts() {
        System.out.println("The game starts!");
    }

    public void shot() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                Coordinate c = new Coordinate(scanner.next());
                if (map[c.y][c.x] == 'O' || map[c.y][c.x] == 'X') {
                    gameMap[c.y][c.x] = 'X';
                    map[c.y][c.x] = 'X';
                    if (!checkWin()) {
                        checkShot(c.x, c.y);
                    }
                } else if (map[c.y][c.x] == '~' || map[c.y][c.x] == 'M') {
                    gameMap[c.y][c.x] = 'M';
                    map[c.y][c.x] = 'M';
                    System.out.println("You missed!");
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    private void checkShot(int x, int y) {
        if (checkField(x, y)) {
            System.out.println("You sank a ship!");
        } else {
            System.out.println("You hit a ship!");
        }
    }

    public boolean checkWin() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameMap[i][j] == 'X') {
                    count++;
                }
            }
        }
        if (count == 17) {
            return true;
        } else {
            return false;
        }
    }
}