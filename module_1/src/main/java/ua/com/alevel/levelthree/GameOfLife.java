package ua.com.alevel.levelthree;

import java.io.BufferedReader;
import java.io.IOException;

public class GameOfLife {

    private final static int MAX_SIZE = 50;
    private static int[][] world;
    private static int n;
    private static int m;
    private static int step = 0;

    public static void run(BufferedReader reader) throws IOException {
        String event;
        System.out.println("Lvl-3.Task-1");
        do {
            System.out.println("1 - New world | 2 - Next step | 3 - Select step | 0 - Exit");
            event = reader.readLine();
            switch (event) {
                case "1" -> {
                    try {
                        System.out.print("New world. Enter size of the world field n*m (from 1 to 50)\nn = ");
                        n = Integer.parseInt(reader.readLine());
                        System.out.print("m = ");
                        m = Integer.parseInt(reader.readLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                    }
                    if (n > 0 && n < MAX_SIZE && m > 0 && m < MAX_SIZE) {
                        world = new int[n][m];
                        initializeRandomWorld();
                        step = 1;
                        drawWorld();
                    } else System.out.println("Invalid input");
                }
                case "2" -> {
                    if (step != 0) {
                        toNextStep();
                        drawWorld();
                    } else System.out.println("World is not found");
                }
                case "3" -> {
                    if (step == 0) {
                        System.out.println("World is not found");
                    } else {
                        int h = 0;
                        System.out.print("Select step, h = ");
                        try {
                            h = Integer.parseInt(reader.readLine());
                        } catch (Exception e) {
                            System.out.println("Invalid input");
                        }
                        if (h > step) {
                            for (int k = step; k < h; k++) {
                                toNextStep();
                                drawWorld();
                            }
                        } else System.out.println("Incorrect input. h <= Step");
                    }
                }
            }
        } while (!event.equals("0"));
    }

    private static void initializeRandomWorld() {
        step = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                world[i][j] = (int) (Math.random() * 2);
            }
        }
    }

    private static void drawWorld() {
        System.out.println("Step " + step);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print("  " + world[i][j]);
            }
            System.out.println("\t");
        }
    }

    private static void toNextStep() {
        int[][] next = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (numberOfNeighbours(i, j) < 2 && world[i][j] == 1)
                    next[i][j] = 0;
                if ((numberOfNeighbours(i, j) == 2 || numberOfNeighbours(i, j) == 3) && world[i][j] == 1)
                    next[i][j] = 1;
                if (numberOfNeighbours(i, j) > 3 && world[i][j] == 1)
                    next[i][j] = 0;
                if (numberOfNeighbours(i, j) == 3 && world[i][j] == 0)
                    next[i][j] = 1;
            }
        }
        for (int i = 0; i < next.length; i++) {
            System.arraycopy(next[i], 0, world[i], 0, next[i].length);
        }
        step++;
    }

    private static int numberOfNeighbours(int i, int j) {
        int live = 0;
        if (i - 1 >= 0 && j - 1 >= 0) live += world[i - 1][j - 1];
        if (i - 1 >= 0) live += world[i - 1][j];
        if (i - 1 >= 0 && j + 1 < m) live += world[i - 1][j + 1];
        if (j + 1 < m) live += world[i][j + 1];
        if (j - 1 >= 0) live += world[i][j - 1];
        if (i + 1 < n && j + 1 < m) live += world[i + 1][j + 1];
        if (i + 1 < n) live += world[i + 1][j];
        if (i + 1 < n && j - 1 >= 0) live += world[i + 1][j - 1];
        return live;
    }
}
