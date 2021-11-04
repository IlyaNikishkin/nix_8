package ua.com.alevel.levelone;

import java.io.BufferedReader;
import java.io.IOException;

public class ChessKnight {

    private static int x;
    private static int y;
    private static int newX;
    private static int newY;
    private static String src;

    public static void run(BufferedReader reader) throws IOException {
        drawBoard(0, 0);
        System.out.println("Lvl-1.Task-2 Set the knight position on the board (example: \"c4\"):");
        src = reader.readLine().toLowerCase();
        try {
            x = src.charAt(0) - (int) 'a' + 1;
            y = Character.digit(src.charAt(1), 10);
        } catch (Exception e) {
        }
        if (verify(x, y)) {
            drawBoard(x, y);
            System.out.println("Set the knight to new position:");
            src = reader.readLine().toLowerCase();
            try {
                newX = src.charAt(0) - (int) 'a' + 1;
                newY = Character.digit(src.charAt(1), 10);
            } catch (Exception e) {
            }
            if (verify(newX, newY))
                if ((Math.abs(x - newX) == 1 && Math.abs(y - newY) == 2)
                        || (Math.abs(x - newX) == 2 && Math.abs(y - newY) == 1)) {
                    drawBoard(newX, newY);
                    System.out.println("Legal move");
                } else System.out.println("Illegal move. Knight moves in a L-shape only");
        }
    }

    private static void drawBoard(int posX, int posY) {
        for (int i = 8; i > 0; i--) {
            for (int j = 1; j < 9; j++) {
                if (posX == j && posY == i) System.out.print("  N");
                else if ((j + i) % 2 == 0) {
                    System.out.print("  #");
                } else System.out.print("  0");
            }
            System.out.println("  |" + (i) + "\t");
        }
        System.out.println("  ______________________\n  A  B  C  D  E  F  G  H");
    }

    private static boolean verify(int posX, int posY) {
        if (src.length() == 2 && posX > 0 && posX < 9 && posY > 0 && posY < 9)
            return true;
        else {
            System.out.println("Invalid input");
            return false;
        }
    }
}
