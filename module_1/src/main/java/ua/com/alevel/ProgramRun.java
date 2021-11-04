package ua.com.alevel;

import ua.com.alevel.levelone.AreaOfTriangle;
import ua.com.alevel.levelone.ChessKnight;
import ua.com.alevel.levelone.UniqueNumberCounter;
import ua.com.alevel.levelthree.GameOfLife;
import ua.com.alevel.leveltwo.BracketBalanceChecker;
import ua.com.alevel.leveltwo.treedepth.TreeDepth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProgramRun {

    public static void run() {
        preview();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;
        try {
            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1": {
                        UniqueNumberCounter.run(reader);
                        preview();
                    }
                    break;
                    case "2": {
                        ChessKnight.run(reader);
                        preview();
                    }
                    break;
                    case "3": {
                        AreaOfTriangle.run(reader);
                        preview();
                    }
                    break;
                    case "4": {
                        BracketBalanceChecker.run(reader);
                        preview();
                    }
                    break;
                    case "5": {
                        TreeDepth.run(reader);
                        preview();
                    }
                    break;
                    case "6": {
                        GameOfLife.run(reader);
                        preview();
                    }
                    break;
                    case "0": {
                        System.exit(0);
                    }
                    break;
                    default: {
                        preview();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void preview() {
        System.out.println("Enter 1 to open Lvl-1.Task-1\n"
                + "Enter 2 to open Lvl-1.Task-2\n"
                + "Enter 3 to open Lvl-1.Task-3\n"
                + "Enter 4 to open Lvl-2.Task-1\n"
                + "Enter 5 to open Lvl-2.Task-2\n"
                + "Enter 6 to open Lvl-3.Task-1\n"
                + "Enter 0 to exit: ");
    }
}
