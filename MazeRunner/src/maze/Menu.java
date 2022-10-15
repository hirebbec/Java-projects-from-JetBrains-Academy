package maze;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Maze maze = null;
    private File file;
    private ArrayList<Step> escape = new ArrayList<Step>();
    private int[][] mazeWithEscape = null;

    static class Step {
        int x;
        int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        Step(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Walker {
        ArrayList<Step> path;
        public Walker(ArrayList<Step> path) {
            this.path = path;
        }

        public void setPath(ArrayList<Step> path) {
            this.path = path;
        }

        public ArrayList<Step> getPath() {
            return path;
        }
    }

    Menu() {};

    public boolean PrintMenu() throws IOException {
        if (maze == null) {
            System.out.println("=== Menu ===\n" +
                    "1. Generate a new maze\n" +
                    "2. Load a maze\n" +
                    "0. Exit");
        } else {
            System.out.println("=== Menu ===\n" +
                    "1. Generate a new maze\n" +
                    "2. Load a maze\n" +
                    "3. Save the maze\n" +
                    "4. Display the maze\n" +
                    "5. Find the escape\n" +
                    "0. Exit");
        }
        switch (scanner.nextInt()) {
            case 1:
                maze = new Maze();
                maze.startGenerate();
                maze.escapeGenerate();
                maze.tunnelsGenerate();
                maze.printMaze();
                break;
            case 2:
                scanner.nextLine();
                String fileName = scanner.nextLine();
                try {
                    file = new File(fileName);
                    loadMaze();
                    break;
                } catch (FileNotFoundException e) {
                    System.out.println("The file " + fileName + " does not exist");
                    break;
                }
            case 3:
                SaveMaze();
                break;
            case 4:
                maze.printMaze();
                break;
            case 5:
                escape = new ArrayList<Step>();
                findStart();
                findEnd();
                escape = findEscape(maze.getStart_x(), maze.getStart_y(), escape, mazeCopy(maze.getMaze()));
                generateMazeWithEscape();
                printMaze(mazeWithEscape);
                break;
            case 0:
                System.out.println("Bye!");
                return false;
            default:
                System.out.println("Incorrect option. Please try again");
        }
        return true;
    }

    private void loadMaze() throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<String>();
        Scanner scanner1 = new Scanner(file);
        while (scanner1.hasNext()) {
            list.add(scanner1.nextLine());
        }
        String[] array = new String[list.size()];
        array = list.toArray(array);
        int x = array[0].length();
        int y = array.length;
        int[][] mat = new int[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                mat[i][j] = array[i].charAt(j) - '0';
            }
        }
        maze = new Maze(mat, x, y);
//        maze.printMaze();
    }

    private void SaveMaze() throws IOException {
        scanner.nextLine();
        String fileName = scanner.nextLine();
        FileWriter writer = new FileWriter(new File(fileName));
        for (int i = 0; i < maze.getSize_y(); i++) {
            for (int j = 0; j < maze.getSize_x(); j++) {
                writer.write((char)(maze.getMaze()[i][j] + '0'));
            }
            writer.append('\n');
        }
        writer.flush();
    }

    private void findStart() {
        for (int i = 0; i < maze.getSize_x(); i++) {
            if (maze.getMaze()[0][i] == 0) {
                maze.setStart_x(i);
                maze.setStart_y(0);
                return;
            } else if (maze.getMaze()[maze.getSize_y() - 1][i] == 0) {
                maze.setStart_x(i);
                maze.setStart_y(maze.getSize_y() - 1);
                return;
            }
        }
        for (int i = 0; i < maze.getSize_y(); i++) {
            if (maze.getMaze()[i][0] == 0) {
                maze.setStart_x(0);
                maze.setStart_y(i);
                return;
            } else if (maze.getMaze()[i][maze.getSize_x() - 1] == 0) {
                maze.setStart_x(maze.getSize_x() - 1);
                maze.setStart_y(i);
                return;
            }
        }
    }

    private void findEnd() {
        for (int i = 0; i < maze.getSize_x(); i++) {
            if (maze.getMaze()[0][i] == 0 && !(maze.getStart_x() == i && maze.getStart_y() == 0)) {
                maze.setExit_x(i);
                maze.setExit_y(0);
                return;
            } else if (maze.getMaze()[maze.getSize_y() - 1][i] == 0 && !(maze.getStart_x() == i && maze.getStart_y() == maze.getSize_y() - 1)) {
                maze.setExit_x(i);
                maze.setExit_y(maze.getSize_y() - 1);
                return;
            }
        }
        for (int i = 0; i < maze.getSize_y(); i++) {
            if (maze.getMaze()[i][0] == 0 && !(maze.getStart_x() == 0 && maze.getStart_y() == i)) {
                maze.setExit_x(0);
                maze.setExit_y(i);
                return;
            } else if (maze.getMaze()[i][maze.getSize_x() - 1] == 0 && !(maze.getStart_x() == maze.getSize_x() - 1 && maze.getStart_y() == i)) {
                maze.setExit_x(maze.getSize_x() - 1);
                maze.setExit_y(i);
                return;
            }
        }
    }

    private ArrayList<Step> findEscape(int x, int y, ArrayList<Step> escape, int[][] tmp) {
        ArrayList<Walker> walkers = new ArrayList<Walker>();
        ArrayList<Step> path = new ArrayList<Step>();
        int[][] mazeCopy = mazeCopy(tmp);
        mazeCopy[y][x] = 2;
        path.add(new Step(x, y));
        walkers.add(new Walker(path));
        while (true) {
            for (Walker walker: walkers) {
                int X = walker.path.get(walker.path.size() - 1).getX();
                int Y = walker.path.get(walker.path.size() - 1).getY();
                if (X == maze.getExit_x() && Y == maze.getExit_y()) {
                    return walker.path;
                }
            }
            for (int i = 0; i < walkers.size(); i++) {
                Walker walker = walkers.get(i);
                int X = walker.path.get(walker.path.size() - 1).getX();
                int Y = walker.path.get(walker.path.size() - 1).getY();
                if (X - 1 >= 0 && mazeCopy[Y][X - 1] == 0) {
                    mazeCopy[Y][X - 1] = 2;
                    ArrayList<Step> newPath = new ArrayList<Step>(walker.path);
                    newPath.add(new Step(X - 1, Y));
                    walkers.add(new Walker(newPath));
                }
                if (X + 1 < mazeCopy[0].length && mazeCopy[Y][X + 1] == 0) {
                    mazeCopy[Y][X + 1] = 2;
                    ArrayList<Step> newPath = new ArrayList<Step>(walker.path);
                    newPath.add(new Step(X + 1, Y));
                    walkers.add(new Walker(newPath));
                }
                if (Y - 1 >= 0 && mazeCopy[Y - 1][X] == 0) {
                    mazeCopy[Y - 1][X] = 2;
                    ArrayList<Step> newPath = new ArrayList<Step>(walker.path);
                    newPath.add(new Step(X, Y - 1));
                    walkers.add(new Walker(newPath));
                }
                if (Y + 1 < tmp.length && mazeCopy[Y + 1][X] == 0) {
                    mazeCopy[Y + 1][X] = 2;
                    ArrayList<Step> newPath = new ArrayList<Step>(walker.path);
                    newPath.add(new Step(X, Y + 1));
                    walkers.add(new Walker(newPath));
                }
            }
        }
    }

    int[][] mazeCopy(int[][] maze) {
        int[][]copy = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                copy[i][j] = maze[i][j];
            }
        }
        return copy;
    }

    private void generateMazeWithEscape() {
        mazeWithEscape = new int[maze.getSize_y()][maze.getSize_x()];
        for (int i = 0; i < maze.getSize_y(); i++) {
            for (int j = 0; j < maze.getSize_x(); j++) {
                mazeWithEscape[i][j] = maze.getMaze()[i][j];
            }
        }
        for (Step step: escape) {
            mazeWithEscape[step.getY()][step.getX()] = 2;
        }
    }


    private void printMaze(int[][] Maze) {
        for (int i = 0; i < Maze.length; i++) {
            for (int j = 0; j < Maze[0].length; j++) {
                if (Maze[i][j] == 0) {
                    System.out.print("  ");
                } else if (Maze[i][j] == 1) {
                    System.out.print("\u2588\u2588");
                } else {
                    System.out.print("//");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
