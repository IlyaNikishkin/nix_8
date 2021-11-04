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
                    case "1" -> {
                        new UniqueNumberCounter().run(reader);
                        preview();
                    }
                    case "2" -> {
                        new ChessKnight().run(reader);
                        preview();
                    }
                    case "3" -> {
                        new AreaOfTriangle().run(reader);
                        preview();
                    }
                    case "4" -> {
                        new BracketBalanceChecker().run(reader);
                        preview();
                    }
                    case "5" -> {
                        new TreeDepth().run(reader);
                        preview();
                    }
                    case "6" -> {
                        new GameOfLife().run(reader);
                        preview();
                    }
                    case "0" -> System.exit(0);
                    default -> preview();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void preview() {
        System.out.println("""
                Enter 1 to open Lvl-1.Task-1
                Enter 2 to open Lvl-1.Task-2
                Enter 3 to open Lvl-1.Task-3
                Enter 4 to open Lvl-2.Task-1
                Enter 5 to open Lvl-2.Task-2
                Enter 6 to open Lvl-3.Task-1
                Enter 0 to exit:\s""");
    }
}
