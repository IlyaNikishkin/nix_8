package ua.com.alevel.tasks;

import ua.com.alevel.util.TaskFiles;

import java.io.IOException;

import static ua.com.alevel.util.TaskFiles.*;

public class DateConverter {

    public static final String INPUT_FILE = "input1.txt";
    public static final String OUTPUT_FILE = "output1.txt";

    public void run() {
        String src = "";
        StringBuilder output = new StringBuilder();
        try {
            src = TaskFiles.read(INPUT_DIR + INPUT_FILE).toString();
        } catch (IOException e) {
            System.out.println(INPUT_FILE + " cannot be read");
        }
        for (int i = 0; i < src.length(); i++) {
            if (Character.isDigit(src.charAt(i))) {
                int j = i;
                int sep = 0;
                i++;
                while (Character.isDigit(src.charAt(i)) || src.charAt(i) == '/' || src.charAt(i) == '-') {
                    if (src.charAt(i) == '/') sep++;
                    else if (src.charAt(i) == '-') sep--;
                    i++;
                }
                if (sep == 2 || sep == -2) {
                    int month;
                    int day;
                    int year;
                    String[] date;
                    if (sep == 2) {
                        date = src.substring(j, i).split("/");
                        if (date.length == 3) {
                            if (date[1].length() == 2) {
                                try {
                                    if (date[0].length() == 2 && date[2].length() > 2) {
                                        month = Integer.parseInt(date[1]);
                                        day = Integer.parseInt(date[0]);
                                        year = Integer.parseInt(date[2]);
                                        if (month > 0 && month < 13
                                                && day > 0
                                                && day < 32
                                                && (date[2].length() > 2 || (year > day && year > month))) {
                                            output.append(date[2]).append(date[1]).append(date[0]).append("\n");
                                        }
                                    } else if (date[0].length() > 2 && date[2].length() == 2) {
                                        month = Integer.parseInt(date[1]);
                                        day = Integer.parseInt(date[2]);
                                        year = Integer.parseInt(date[0]);
                                        if (month > 0 && month < 13
                                                && day > 0
                                                && day < 32
                                                && (date[0].length() > 2 || (year > day && year > month))) {
                                            output.append(date[0]).append(date[1]).append(date[2]).append("\n");
                                        }
                                    }
                                } catch (NumberFormatException ignored) {
                                }
                            }
                        }
                    } else {
                        date = src.substring(j, i).split("-");
                        if (date.length == 3) {
                            if (date[0].length() == 2 && date[1].length() == 2 && date[2].length() > 1) {
                                try {
                                    month = Integer.parseInt(date[0]);
                                    day = Integer.parseInt(date[1]);
                                    year = Integer.parseInt(date[2]);
                                    if (month > 0 && month < 13 && day > 0 && day < 32 && year >= 0) {
                                        output.append(date[2]).append(date[0]).append(date[1]).append("\n");
                                    }
                                } catch (NumberFormatException ignored) {
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!output.isEmpty()) {
            TaskFiles.write(OUTPUT_DIR + OUTPUT_FILE, output.toString(),true);
        }
    }
}
