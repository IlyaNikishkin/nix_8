package ua.com.alevel.tasks;

import ua.com.alevel.util.TaskFiles;
import java.io.IOException;

import static ua.com.alevel.util.TaskFiles.INPUT_DIR;
import static ua.com.alevel.util.TaskFiles.OUTPUT_DIR;

public class FirstUniqueName {

    public static final String INPUT_FILE = "input2.txt";
    public static final String OUTPUT_FILE = "output2.txt";
    private static final int NAME_MIN_SIZE = 3;

    public void run() {
        String src = "";
        String firstUniqueName = "no result";
        try {
            src = TaskFiles.read(INPUT_DIR + INPUT_FILE).toString();
        } catch (IOException e) {
            System.out.println(INPUT_FILE + " cannot be read");
        }
        if (!src.isBlank()) {
            try {
                String[] names = src.split("\\P{L}+");
                names[0] = null;
                for (int i = 1; i < names.length; i++) {
                    if (names[i] != null && names[i].length() >= NAME_MIN_SIZE) {
                        boolean match = false;
                        for (int j = i + 1; j < names.length; j++) {
                            if (names[j] != null && names[j].length() >= NAME_MIN_SIZE) {
                                if (names[i].equalsIgnoreCase(names[j])) {
                                    match = true;
                                    names[j] = null;
                                }
                            } else names[j] = null;
                        }
                        if (match) {
                            names[i] = null;
                        }
                    } else names[i] = null;
                }
                for (String name : names) {
                    if (name != null) {
                        firstUniqueName = name;
                        break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                firstUniqueName = "no word in input file";
            }
            TaskFiles.write(OUTPUT_DIR + OUTPUT_FILE, firstUniqueName, true);
        }
    }
}
