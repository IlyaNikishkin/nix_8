package ua.com.alevel;

import ua.com.alevel.tasks.DateConverter;
import ua.com.alevel.tasks.FirstUniqueName;
import ua.com.alevel.tasks.RoadPrice;

import static ua.com.alevel.util.TaskFiles.*;

public class ProgramRun {

    public static void run() {
        createTaskDataIfDoesNotExist();
        new DateConverter().run();
        new FirstUniqueName().run();
        new RoadPrice().run();
        String inputDir = "Input directory: " + System.getProperty("user.dir") + "/" + INPUT_DIR;
        String outputDir = "Output directory: " + System.getProperty("user.dir") + "/" + OUTPUT_DIR;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            inputDir = inputDir.replace('/', '\\');
            outputDir = outputDir.replace('/', '\\');
        }
        System.out.println(inputDir + "\n" + outputDir);
    }

    private static void createTaskDataIfDoesNotExist() {
        createDirectories(FILE_DIR, INPUT_DIR, OUTPUT_DIR);
        write(INPUT_DIR + DateConverter.INPUT_FILE, """
                2020/04/05 valid, "04-05-2021" valid
                05/04/2022 valid
                04/05-2023 ignoring
                31/04/024 valid
                05/400/2025 ignoring
                13-28-1999 ignoring
                2000/02/29 valid
                2026/04/5020 ignoring
                2027/11/24 valid, 08-2028 ignoring
                15-08-2029 ignoring
                15-08-202934657879890 ignoring""", false);
        write(INPUT_DIR + FirstUniqueName.INPUT_FILE, """
                eM6l2Wl5;rlv21Qjlo|M)zQ;Xoh|-oqye^Xo[b3LG%|1x^nqd0edbpAJk-=+$,j9[.N:!
                Name is a construction of 3+ letters and case insensitive""", false);
        write(INPUT_DIR + RoadPrice.INPUT_FILE, """
                4
                gdansk
                2
                2 1
                3 3
                bydgoszcz
                3
                1 1
                3 1
                4 4
                torun
                3
                1 3
                2 1
                4 1
                warszawa
                2
                2 4
                3 1
                2
                gdansk warszawa
                bydgoszcz warszawa""", false);
    }
}
