package ua.com.alevel.tasks;

import ua.com.alevel.util.InvalidInputException;
import ua.com.alevel.util.TaskFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.com.alevel.util.TaskFiles.INPUT_DIR;
import static ua.com.alevel.util.TaskFiles.OUTPUT_DIR;

public class RoadPrice {

    public static final String INPUT_FILE = "input3.txt";
    public static final String OUTPUT_FILE = "output3.txt";
    private static final int INFINITE = Integer.MAX_VALUE;
    private static int numOfCities;

    public void run() {
        List<String> src = new ArrayList<>();
        try {
            src = TaskFiles.read(INPUT_DIR + INPUT_FILE);
        } catch (IOException e) {
            System.out.println(INPUT_FILE + " cannot be read");
        }
        List<Integer> result = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        int readingLine = 1;
        try {
            numOfCities = Integer.parseInt(src.get(0));
            if (numOfCities > 1000)
                throw new InvalidInputException();
            int[][] graph = new int[numOfCities][numOfCities];
            List<String> cities = new ArrayList<>();
            for (int i = 0; i < numOfCities; i++) {
                cities.add(src.get(readingLine++));
                int numOfNeighbours = Integer.parseInt(src.get(readingLine++));
                for (int j = 0; j < numOfNeighbours; j++) {
                    String[] neighbours = src.get(readingLine++).split(" ");
                    int cost = Integer.parseInt(neighbours[1]);
                    if (cost > 200000) {
                        throw new InvalidInputException();
                    } else {
                        graph[i][Integer.parseInt(neighbours[0]) - 1] = cost;
                    }
                }
            }
            if (cities.size() != numOfCities)
                throw new InvalidInputException();
            for (int i = 0; i < numOfCities; i++) {
                for (int j = 0; j < numOfCities; j++) {
                    if (graph[i][j] == 0) graph[i][j] = INFINITE;
                }
            }
            for (int i = 0; i < numOfCities; i++) {
                for (int j = 0; j < numOfCities; j++) {
                    if (graph[i][j] != graph[j][i])
                        throw new InvalidInputException();
                }
            }
            int waysToFind = Integer.parseInt(src.get(readingLine++));
            if (waysToFind > 100)
                throw new InvalidInputException();
            for (int i = 0; i < waysToFind; i++) {
                String[] paths = src.get(readingLine++).split(" ");
                int startPoint, endPoint;
                startPoint = cities.indexOf(paths[0]);
                endPoint = cities.indexOf(paths[1]);
                boolean[] visited = new boolean[numOfCities];
                result.add(findCheapestCost(startPoint, endPoint, visited, graph));
                if (result.get(result.size() - 1) == INFINITE) {
                    output.append("End and start points are disconnected\n");
                } else {
                    output.append(result.get(result.size() - 1)).append("\n");
                }
            }
            if (result.size() != waysToFind)
                throw new InvalidInputException();
        } catch (NumberFormatException | InvalidInputException | IndexOutOfBoundsException e) {
            output = new StringBuilder("Invalid input data");
        } finally {
            TaskFiles.write(OUTPUT_DIR + OUTPUT_FILE, output.toString(), true);
        }
    }

    private static int findCheapestCost(int startPoint, int endPoint, boolean[] visited, int[][] graph) {
        if (startPoint == endPoint)
            return 0;
        visited[startPoint] = true;
        int cost = INFINITE;
        for (int i = 0; i < numOfCities; i++) {
            if (graph[startPoint][i] != INFINITE && !visited[i]) {
                int current = findCheapestCost(i, endPoint, visited, graph);
                if (current < INFINITE) {
                    cost = Math.min(cost, graph[startPoint][i] + current);
                }
            }
        }
        visited[startPoint] = false;
        return cost;
    }
}
