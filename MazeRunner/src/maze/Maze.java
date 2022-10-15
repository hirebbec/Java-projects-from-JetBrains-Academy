package maze;

import java.util.Random;
import java.util.Scanner;

public class Maze {
    private int[][] maze;
    private int[][] savedMaze;
    private int start_x;
    private int start_y;
    private int exit_x;
    private int exit_y;
    private int size_x;
    private int size_y;
    Random random =  new Random();
    Scanner scanner = new Scanner(System.in);

    Maze() {
        System.out.println("Please, enter the size of a maze");
        size_y = scanner.nextInt();
        size_x = size_y;
        savedMaze =  new int[size_y][size_x];
        maze = new int[size_y][size_x];
        for (int i = 0; i < size_y; i++) {
            for (int j = 0; j < size_x; j++) {
                maze[i][j] = 1;
            }
        }
    }

    public int getExit_x() {
        return exit_x;
    }

    public int getExit_y() {
        return exit_y;
    }

    public void setExit_x(int exit_x) {
        this.exit_x = exit_x;
    }

    public void setExit_y(int exit_y) {
        this.exit_y = exit_y;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }

    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }

    public int[][] getMaze() {
        return maze;
    }

    public int getSize_x() {
        return size_x;
    }

    public int getSize_y() {
        return size_y;
    }

    Maze(int[][] mat, int x, int y) {
        size_x = x;
        size_y = y;
        maze = mat;
    }

    private void save() {
        for (int i = 0; i < size_y; i++) {
            for (int j = 0; j < size_x; j++) {
                savedMaze[i][j] = maze[i][j];
            }
        }
    }

    public void startGenerate() {
        //Начало на горизонтали
        if (random.nextInt(2) % 2 == 0) {
            if (random.nextInt(2) % 2 == 0) {
                //Сверху
                start_y = 0;
            } else {
                //Снизу
                start_y = size_y - 1;
            }
            start_x = random.nextInt(size_x - 2) + 1;
        }
        //Начало на вертикали
        else {
            if (random.nextInt(2) % 2 == 0) {
                //Слева
                start_x = 0;
            } else {
                //Справа
                start_x = size_x - 1;
            }
            start_y = random.nextInt(size_y - 2) + 1;
        }
        maze[start_y][start_x] = 0;
        save();
    }

    public void escapeGenerate() {
        //1 - up
        //2 - right
        //3 - down
        //4 - left
        int x = -1;
        int y = -1;
        int step;
        while (!(y == 0 || x == 0 || x == size_x - 1 || y == size_y - 1)) {
            x = start_x;
            y = start_y;
            for (int i = 0; i < (size_x * size_y); i++) {
                step = random.nextInt(4) + 1;
                if (step == 1 && checkStep(y - 1, x)) {
                    maze[y - 1][x] = 0;
                    y--;
                } else if (step == 2 && checkStep(y, x + 1)) {
                    maze[y][x + 1] = 0;
                    x++;
                } else if (step == 3 && checkStep(y + 1, x)) {
                    maze[y + 1][x] = 0;
                    y++;
                } else if (step == 4 && checkStep(y, x - 1)) {
                    maze[y][x - 1] = 0;
                    x--;
                }
                if ((y == 0 || x == 0 || x == size_x - 1 || y == size_y - 1) && !(x == start_x && y == start_y)) {
                    exit_x = x;
                    exit_y = y;
                    save();
                    return;
                }
            }
            clearMap();
        }
    }

    public void tunnelsGenerate() {
        int step;
        while (!checkMazeComplete()) {
            clearMap();
            for (int i = 0; i < 1000 * size_y * size_x; i++) {
                int x = random.nextInt(size_x - 2) + 1;
                int y = random.nextInt(size_y - 2) + 1;
                if (maze[y][x] != 1)
                    continue;
                step = random.nextInt(4) + 1;
                if (step == 1 && checkStepTunnels(y - 1, x)) {
                    maze[y - 1][x] = 0;
                } else if (step == 2 && checkStepTunnels(y, x + 1)) {
                    maze[y][x + 1] = 0;
                } else if (step == 3 && checkStepTunnels(y + 1, x)) {
                    maze[y + 1][x] = 0;
                } else if (step == 4 && checkStepTunnels(y, x - 1)) {
                    maze[y][x - 1] = 0;
                }
                if (checkMazeComplete()) {
                    break;
                }
            }
        }
    }

    private boolean checkStep(int y, int x) {
        if (y > size_y - 1 || y < 0 || x < 0 || x > size_x - 1 || maze[y][x] == 0) {
            return false;
        } else if ((y == 0 && x == start_x + 1) || (y == size_y - 1 && x == start_x - 1) || (y == 0 && x == start_x - 1) || (y == size_y - 1 && x == start_x + 1) ||
                (y == start_y - 1 && x == 0) || (y == start_y + 1 && x == 0) || (y == start_y + 1 && x == size_x - 1) || (y == start_y - 1 && x == size_x - 1)) {
            return false;
        } else if ((y != size_y - 1 && y != 0 && x != size_x - 1 && x != 0) && (maze[y + 1][x] + maze[y - 1][x] + maze[y][x + 1] + maze[y][x - 1]) != 3 ) {
            return false;
        }
        return true;
    }

    private boolean checkStepTunnels(int y, int x) {
        if (y > size_y - 2 || y < 1 || x < 1 || x > size_x - 2 || maze[y][x] == 0) {
            return false;
        }
        if (random.nextInt(10) != 4) {
            if ((maze[y + 1][x] + maze[y - 1][x] + maze[y][x + 1] + maze[y][x - 1]) == 3) {
                return true;
            }
        } else {
            if ((maze[y + 1][x] + maze[y - 1][x] + maze[y][x + 1] + maze[y][x - 1]) == 2) {
                return true;
            }
        }
        return false;
    }



    private void clearMap() {
        for (int i = 0; i < size_y; i++) {
            for (int j = 0; j < size_x; j++) {
                maze[i][j] = savedMaze[i][j];
            }
        }
    }

    private boolean checkMazeComplete() {
        for (int i = 1; i < size_y - 1; i++) {
            for (int j = 1; j < size_x - 1; j++) {
                if (sum3x3(i, j) == 9) {
                    return false;
                }
            }
        }
        return true;
    }

    private int sum3x3(int i, int j) {
        return maze[i][j] + maze[i - 1][j - 1] + maze[i - 1][j] + maze[i - 1][j + 1] +
                maze[i][j - 1] + maze[i][j + 1] + maze[i + 1][j - 1] + maze[i + 1][j] + maze[i + 1][j + 1];
    }

    public void printMaze() {
        for (int i = 0; i < size_y; i++) {
            for (int j = 0; j < size_x; j++) {
                if (maze[i][j] == 1) {
                    System.out.print("\u2588\u2588");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
