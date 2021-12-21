package ua.com.alevel.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TaskFiles {

    public static final String FILE_DIR = "src/main/resources/files/";
    public static final String INPUT_DIR = FILE_DIR + "input/";
    public static final String OUTPUT_DIR = FILE_DIR + "output/";

    public static List<String> read(String pathToFile) throws IOException {
        return Files.readAllLines(Paths.get(pathToFile));
    }

    public static void write(String path, String str, boolean rewriting) {
        Path current = Paths.get(path);
        try {
            if (!Files.exists(current) || rewriting) {
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(str);
                fileWriter.close();
            }
        } catch (IOException exception) {
            System.out.println(path + " cannot be written");
        }
    }

    public static void createDirectories(String... paths) {
        for (String path : paths) {
            Path current = Paths.get(path);
            try {
                if (!Files.exists(current)) {
                    Files.createDirectories(current);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
