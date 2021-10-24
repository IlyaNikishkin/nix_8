package ua.com.alevel;

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
                        System.out.println("1. Enter your line:");
                        String src = reader.readLine();
                        if (src.isEmpty()) {
                            System.out.println("Your line is empty");
                        } else System.out.println(ReverseStringUtil.reverse(src));
                        preview();
                    }
                    break;
                    case "2": {
                        System.out.println("2. Enter \"String src\":");
                        String src = reader.readLine();
                        System.out.println("2. Enter \"String dest\":");
                        String dest = reader.readLine();
                        if (src.isEmpty() || dest.isEmpty()) {
                            System.out.println("You have entered an empty line(s)");
                        } else System.out.println(ReverseStringUtil.reverse(src, dest));
                        preview();
                    }
                    break;
                    case "3": {
                        String src;
                        int firstIndex = -1;
                        int lastIndex = -1;
                        System.out.println("3. Enter \"String src\":");
                        do {
                            src = reader.readLine();
                            if (src.length() < 2) {
                                System.out.println("3. The string contains <2 symbols. Enter \"String src\":");
                            }
                        } while (src.length() < 2);
                        System.out.println("The string contains " + src.length() + " symbols.\n" +
                                "3. Enter \"int firstIndex\":");
                        do {
                            try {
                                firstIndex = Integer.parseInt(reader.readLine());
                                if (firstIndex < 0 || firstIndex > src.length() - 2) {
                                    System.out.println("3. Enter a positive integer from 0 to " + (src.length() - 2) + ":");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("3. Not a number. Enter \"int firstIndex\":");
                            }
                        } while (firstIndex < 0 || firstIndex > src.length() - 2);
                        System.out.println("3. Enter \"int lastIndex\":");
                        do {
                            try {
                                lastIndex = Integer.parseInt(reader.readLine());
                                if (lastIndex <= firstIndex || lastIndex > src.length() - 1) {
                                    System.out.println("3. Enter a positive integer from " + (firstIndex + 1) +
                                            " to " + (src.length() - 1) + ":");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("3. Not a number. Enter \"int lastIndex\":");
                            }
                        } while (lastIndex <= firstIndex || lastIndex > src.length() - 1);
                        System.out.println(ReverseStringUtil.reverse(src, firstIndex, lastIndex));
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
        System.out.println("Select 1 to call reverse(String src);\n" +
                "Select 2 to call reverse(String src, String dest);\n" +
                "Select 3 to call reverse(String src, int firstIndex, int lastIndex);\n" +
                "Enter an item from the menu, or 0 to exit: ");
    }
}
