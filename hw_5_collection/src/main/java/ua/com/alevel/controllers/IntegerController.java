package ua.com.alevel.controllers;

import ua.com.alevel.util.MathSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IntegerController {

    private static final MathSet<Integer> mathSet = new MathSet<>();

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position;
        try {
            System.out.println("Integer MathSet");
            runNavigation();
            while ((position = reader.readLine()) != null && !position.equals("0")) {
                mathSetMethods(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println("""     
                                        
                1 - Add | 2 - Print | 3 - GetValueByIndex | 4 - Join | 5 - Intersection
                6 - Max | 7 - Min | 8 - Average | 9 - Median
                10 - SortDesc | 11 - SortDescByIndexes | 12 - SortDescByValue
                13 - SortAsc | 14 - SortAscByIndexes | 15 - SortAscByValue
                16 - Cut | 17 - Clear | 18 - ClearByNumbers | 0 - Back to Main Menu""");
    }

    private void mathSetMethods(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> add(reader);
            case "2" -> {
                out(mathSet);
                System.out.println();
            }
            case "3" -> getValueByIndex(reader);
            case "4" -> join(reader);
            case "5" -> intersection(reader);
            case "6" -> max();
            case "7" -> min();
            case "8" -> average();
            case "9" -> median();
            case "10" -> sortDesc();
            case "11" -> sortDescByIndexes(reader);
            case "12" -> sortDescByValue(reader);
            case "13" -> sortAsc();
            case "14" -> sortAscByIndexes(reader);
            case "15" -> sortAscByValue(reader);
            case "16" -> cut(reader);
            case "17" -> clean();
            case "18" -> clearByNumbers(reader);
        }
        runNavigation();
    }

    private void add(BufferedReader reader) {
        System.out.print("Enter integer numbers (example 4 -5 6 34): ");
        try {
            String src = reader.readLine();
            String[] str = src.split("\\s");
            Integer[] numbers = new Integer[str.length];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Integer.valueOf(str[i]);
            }
            mathSet.add(numbers);
            System.out.print("Result: ");
            out(mathSet);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private void join(BufferedReader reader) {
        System.out.print("Join with (example 4 -5 6 34): ");
        if (mathSet.size() != 0) {
            try {
                String src = reader.readLine();
                String[] str = src.split("\\s");
                Integer[] numbers = new Integer[str.length];
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = Integer.valueOf(str[i]);
                }
                MathSet<Integer> anotherMathSet = new MathSet<>(numbers);
                out(mathSet);
                System.out.print(" U ");
                out(anotherMathSet);
                System.out.print("\nResult: ");
                mathSet.join(anotherMathSet);
                out(mathSet);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void intersection(BufferedReader reader) {
        System.out.print("Intersection with (example 4 -5 6 34): ");
        if (mathSet.size() != 0) {
            try {
                String src = reader.readLine();
                String[] str = src.split("\\s");
                Integer[] numbers = new Integer[str.length];
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = Integer.valueOf(str[i]);
                }
                MathSet<Integer> anotherMathSet = new MathSet<>(numbers);
                out(mathSet);
                System.out.print(" ⋂ ");
                out(anotherMathSet);
                System.out.print("\nResult: ");
                mathSet.intersection(anotherMathSet);
                out(mathSet);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void getValueByIndex(BufferedReader reader) {
        System.out.print("Enter index: ");
        if (mathSet.size() != 0) {
            try {
                int index = Integer.parseInt(reader.readLine());
                System.out.println("Result: " + mathSet.get(index));
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void cut(BufferedReader reader) {
        if (mathSet.size() != 0) {
            try {
                System.out.print("Enter firstIndex: ");
                int firstIndex = Integer.parseInt(reader.readLine());
                System.out.print("Enter lastIndex: ");
                int lastIndex = Integer.parseInt(reader.readLine());
                System.out.print("Result: ");
                out(mathSet.cut(firstIndex, lastIndex));
                System.out.println("\n*warning: this is just a result of cutting " +
                        "but mathSet hasn't changed");
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void sortDescByIndexes(BufferedReader reader) {
        if (mathSet.size() != 0) {
            try {
                System.out.print("Enter firstIndex: ");
                int firstIndex = Integer.parseInt(reader.readLine());
                System.out.print("Enter lastIndex: ");
                int lastIndex = Integer.parseInt(reader.readLine());
                mathSet.sortDesc(firstIndex, lastIndex);
                System.out.print("Result: ");
                out(mathSet);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void sortDescByValue(BufferedReader reader) {
        if (mathSet.size() != 0) {
            System.out.print("Enter value: ");
            try {
                mathSet.sortDesc(Integer.parseInt(reader.readLine()));
                System.out.print("Result: ");
                out(mathSet);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void sortAscByIndexes(BufferedReader reader) {
        if (mathSet.size() != 0) {
            try {
                System.out.print("Enter firstIndex: ");
                int firstIndex = Integer.parseInt(reader.readLine());
                System.out.print("Enter lastIndex: ");
                int lastIndex = Integer.parseInt(reader.readLine());
                mathSet.sortAsc(firstIndex, lastIndex);
                System.out.print("Result: ");
                out(mathSet);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void sortAsc() {
        if (mathSet.size() != 0) {
            System.out.print("Result: ");
            mathSet.sortAsc();
            out(mathSet);
            System.out.println();
        } else System.out.println("MathSet is empty");
    }

    private void sortAscByValue(BufferedReader reader) {
        if (mathSet.size() != 0) {
            System.out.print("Enter value: ");
            try {
                mathSet.sortAsc(Integer.parseInt(reader.readLine()));
                System.out.print("Result: ");
                out(mathSet);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void sortDesc() {
        if (mathSet.size() != 0) {
            System.out.print("Result: ");
            mathSet.sortDesc();
            out(mathSet);
            System.out.println();
        } else System.out.println("MathSet is empty");
    }

    private void median() {
        if (mathSet.size() != 0) {
            System.out.print("Sorted mathSet: ");
            mathSet.sortAsc();
            out(mathSet);
            System.out.println("\nMedian = " + mathSet.getMedian());
        } else System.out.println("MathSet is empty");
    }

    private void max() {
        if (mathSet.size() != 0) {
            System.out.println("Max = " + mathSet.getMax());
        } else System.out.println("MathSet is empty");
    }

    private void min() {
        if (mathSet.size() != 0) {
            System.out.println("Min = " + mathSet.getMin());
        } else System.out.println("MathSet is empty");
    }

    private void average() {
        if (mathSet.size() != 0) {
            System.out.println("Average = " + mathSet.getAverage());
        } else System.out.println("MathSet is empty");
    }

    private void clearByNumbers(BufferedReader reader) {
        System.out.print("Enter numbers (example 4 -5 6 34): ");
        if (mathSet.size() != 0) {
            try {
                String src = reader.readLine();
                String[] str = src.split("\\s");
                Integer[] numbers = new Integer[str.length];
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = Integer.valueOf(str[i]);
                }
                mathSet.clear(numbers);
                System.out.print("Result: ");
                out(mathSet);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else System.out.println("MathSet is empty");
    }

    private void clean() {
        mathSet.clear();
        System.out.println("Cleaned");
    }

    private void out(MathSet<Integer> mathSet) {
        if (mathSet.size() == 0) System.out.println("MathSet is empty");
        else {
            System.out.print("[");
            for (int i = 0; i < mathSet.size(); i++) {
                if (i == mathSet.size() - 1) {
                    System.out.print(mathSet.get(i) + "]");
                } else System.out.print(mathSet.get(i) + ", ");
            }
        }
    }
}
