package ua.com.alevel.view.controller;

import ua.com.alevel.calendar.Date;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    private static final String[] MONTH = {"january", "february", "march", "april", "may",
            "june", "july", "august", "september", "october", "november", "december"};
    private static final String[] FORMATS = {"dd/mm/yy", "m/d/yyyy", "mmm-d-yy", "dd-mmm-yyyy"};

    public static void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position;
        int inputMethod = 1;
        int outputMethod = 1;
        try {
            System.out.println("");
            runNavigation();
            while ((position = reader.readLine()) != null) {
                switch (position) {
                    case "1" -> {
                        formatPreview();
                        try {
                            int m = Integer.parseInt(reader.readLine());
                            if (m < 5 && m > 0) {
                                inputMethod = m;
                                formatInfo(inputMethod);
                            } else System.out.println("Недопустимый ввод");
                        } catch (NumberFormatException e) {
                            System.out.println("Недопустимый ввод");
                        }
                    }
                    case "2" -> {
                        formatPreview();
                        try {
                            int m = Integer.parseInt(reader.readLine());
                            if (m < 5 && m > 0) {
                                outputMethod = m;
                                formatInfo(outputMethod);
                            } else System.out.println("Недопустимый ввод");
                        } catch (NumberFormatException e) {
                            System.out.println("Недопустимый ввод");
                        }
                    }
                    case "3" -> {
                        System.out.print("Введите дату1 (" + FORMATS[inputMethod - 1] + "): ");
                        String strMinuend = reader.readLine().trim();
                        if (isDate(strMinuend, inputMethod)) {
                            System.out.print("Введите дату2 (" + FORMATS[inputMethod - 1] + "): ");
                            String strSubtrahend = reader.readLine().trim();
                            if (isDate(strSubtrahend, inputMethod)) {
                                try {
                                    Date minuend = new Date(strMinuend, FORMATS[inputMethod - 1]);
                                    Date subtrahend = new Date(strSubtrahend, FORMATS[inputMethod - 1]);
                                    long difference = Math.abs(minuend.time() - subtrahend.time());
                                    System.out.println("Разница\n"
                                            + "в мсек: " + difference + "\n"
                                            + "в сек: " + difference / 1000 + "\n"
                                            + "в мин: " + difference / 60000 + "\n"
                                            + "в часах: " + difference / 3600000 + "\n"
                                            + "в днях: " + difference / 86400000);
                                    if (minuend.getYear() > subtrahend.getYear()) {
                                        if (minuend.timeWithoutYears() >= subtrahend.timeWithoutYears()) {
                                            System.out.println("в годах: " + (minuend.getYear() - subtrahend.getYear()));
                                        } else
                                            System.out.println("в годах: " + (minuend.getYear() - subtrahend.getYear() - 1));
                                    } else if (minuend.getYear() < subtrahend.getYear()) {
                                        if (minuend.timeWithoutYears() <= subtrahend.timeWithoutYears()) {
                                            System.out.println("в годах: " + (subtrahend.getYear() - minuend.getYear()));
                                        } else
                                            System.out.println("в годах: " + (subtrahend.getYear() - minuend.getYear() - 1));
                                    } else System.out.println("в годах: 0");
                                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                                    System.out.println("Недопустимый ввод");
                                }
                            }
                        }
                    }
                    case "4" -> {
                        System.out.print("Введите дату (" + FORMATS[inputMethod - 1] + "): ");
                        String stringDate = reader.readLine().trim();
                        if (isDate(stringDate, inputMethod)) {
                            try {
                                Date date = new Date(stringDate, FORMATS[inputMethod - 1]);
                                long ms = 0;
                                long sec = 0;
                                long min = 0;
                                long hour = 0;
                                long day = 0;
                                long year = 0;
                                System.out.print("Введите число милисекунд, которое хотите прибавить: ");
                                String str = reader.readLine().trim();
                                if (!str.isEmpty()) ms = Long.parseLong(str);
                                System.out.print("Введите число секунд, которое хотите прибавить: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) sec = Long.parseLong(str);
                                System.out.print("Введите число минут, которое хотите прибавить: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) min = Long.parseLong(str);
                                System.out.print("Введите число часов, которое хотите прибавить: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) hour = Long.parseLong(str);
                                System.out.print("Введите число дней, которое хотите прибавить: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) day = Long.parseLong(str);
                                System.out.print("Введите число лет, которое хотите прибавить: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) year = Long.parseLong(str);
                                if (date.addTime(ms, sec, min, hour, day, year)) {
                                    System.out.println(date.toString(FORMATS[outputMethod - 1]));
                                } else System.out.println("Результат выходит за рамки календаря по верхнему пределу");
                            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                                System.out.println("Недопустимый ввод");
                            }
                        }
                    }
                    case "5" -> {
                        System.out.print("Введите дату (" + FORMATS[inputMethod - 1] + "): ");
                        String stringDate = reader.readLine().trim();
                        if (isDate(stringDate, inputMethod)) {
                            try {
                                Date date = new Date(stringDate, FORMATS[inputMethod - 1]);
                                long ms = 0;
                                long sec = 0;
                                long min = 0;
                                long hour = 0;
                                long day = 0;
                                long year = 0;
                                System.out.print("Введите число милисекунд, которое хотите вычесть: ");
                                String str = reader.readLine().trim();
                                if (!str.isEmpty()) ms = Long.parseLong(str);
                                System.out.print("Введите число секунд, которое хотите вычесть: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) sec = Long.parseLong(str);
                                System.out.print("Введите число минут, которое хотите вычесть: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) min = Long.parseLong(str);
                                System.out.print("Введите число часов, которое хотите вычесть: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) hour = Long.parseLong(str);
                                System.out.print("Введите число дней, которое хотите вычесть: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) day = Long.parseLong(str);
                                System.out.print("Введите число лет, которое хотите вычесть: ");
                                str = reader.readLine().trim();
                                if (!str.isEmpty()) year = Long.parseLong(str);
                                if (date.addTime(-ms, -sec, -min, -hour, -day, -year)) {
                                    System.out.println(date.toString(FORMATS[outputMethod - 1]));
                                } else System.out.println("Результат выходит за рамки календаря по нижнему пределу");
                            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                                System.out.println("Недопустимый ввод");
                            }
                        }
                    }
                    case "6" -> {
                        System.out.println("Введите даты (" + FORMATS[inputMethod - 1] + ") через 'Enter' (0 - завершить ввод): ");
                        List<Date> dates = new ArrayList<>();
                        try {
                            while (true) {
                                String src = reader.readLine().trim();
                                if (!src.equals("0")) {
                                    if (isDate(src, inputMethod)) {
                                        dates.add(new Date(src, FORMATS[inputMethod - 1]));
                                    } else System.out.println("Недопустимый ввод");
                                } else break;
                            }
                            if (dates.size() != 0) {
                                String finalOutputMethod = FORMATS[outputMethod - 1];
                                List<Date> sortedDates = dates.stream()
                                        .sorted(Comparator.comparing(Date::time)
                                                .thenComparing(Date::time))
                                        .collect(Collectors.toList());
                                System.out.println("По возрастанию:");
                                sortedDates.forEach(i -> System.out.println(i.toString(finalOutputMethod)));
                                System.out.println("По убыванию:");
                                for (int i = sortedDates.size() - 1; i >= 0; i--) {
                                    System.out.println(sortedDates.get(i).toString(finalOutputMethod));
                                }
                            }
                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            System.out.println("Недопустимый ввод");
                        }
                    }
                    case "0" -> System.exit(0);
                }
                runNavigation();
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private static void runNavigation() {
        System.out.println("""
                                
                1 - Формат ввода (по умолчанию dd/mm/yy) | 2 - Формат вывода (по умолчанию dd/mm/yy)
                3 - Разница дат | 4 - Прибавить к дате | 5 - Вычесть из даты | 6 - Сортировать даты
                0 - Выход""");
    }

    private static void formatPreview() {
        System.out.println("""
                1 - dd/mm/yy
                2 - m/d/yyyy
                3 - mmm-d-yy
                4 - dd-mmm-yyyy""");
    }

    private static void formatInfo(int method) {
        switch (method) {
            case 1 -> System.out.println("dd/mm/yy (example: 01/12/07)");
            case 2 -> System.out.println("m/d/yyyy (example: 3/4/0621)");
            case 3 -> System.out.println("mmm-d-yy (example: March-4-08)");
            case 4 -> System.out.println("dd-mmm-yyyy (example: 09-April-0789)");
        }
    }

    private static boolean isDate(String str, int method) {
        boolean validFormat = true;
        int days = 1;
        int month = 1;
        int years = 0;
        String[] date = str.split(" ");
        if (date.length != 2 && date.length != 1) {
            validFormat = DateError();
        } else {
            switch (method) {
                case 1 -> {
                    String[] dateValue = split(date[0], "/");
                    if (dateValue.length != 3
                            || (!dateValue[0].isEmpty() && dateValue[0].length() != 2)
                            || (!dateValue[1].isEmpty() && dateValue[1].length() != 2)
                            || (dateValue[2].length() == 1)) {
                        validFormat = DateError();
                    } else try {
                        if (!dateValue[0].isEmpty()) {
                            days = Integer.parseInt(dateValue[0]);
                        }
                        if (!dateValue[1].isEmpty()) {
                            month = Integer.parseInt(dateValue[1]);
                        }
                        if (!dateValue[2].isEmpty()) {
                            years = Integer.parseInt(dateValue[2]);
                            validFormat = LimitError(years);
                        }
                    } catch (NumberFormatException e) {
                        validFormat = DateError();
                    }
                }
                case 2 -> {
                    String[] dateValue = split(date[0], "/");
                    if (dateValue.length != 3 || (!dateValue[2].isEmpty() && dateValue[2].length() < 4)) {
                        validFormat = DateError();
                    } else try {
                        if (!dateValue[1].isEmpty()) {
                            days = Integer.parseInt(dateValue[1]);
                        }
                        if (!dateValue[0].isEmpty()) {
                            month = Integer.parseInt(dateValue[0]);
                        }
                        if (!dateValue[2].isEmpty()) {
                            years = Integer.parseInt(dateValue[2]);
                            validFormat = LimitError(years);
                        }
                    } catch (NumberFormatException e) {
                        validFormat = DateError();
                    }
                }
                case 3 -> {
                    String[] dateValue = split(date[0], "-");
                    if (dateValue.length != 3 || (dateValue[2].length() == 1)) {
                        validFormat = DateError();
                    } else try {
                        if (!dateValue[1].isEmpty()) {
                            days = Integer.parseInt(dateValue[1]);
                        }
                        if (!dateValue[0].isEmpty()) {
                            month = Arrays.asList(MONTH).indexOf(dateValue[0].toLowerCase());
                        }
                        if (!dateValue[2].isEmpty()) {
                            years = Integer.parseInt(dateValue[2]);
                            validFormat = LimitError(years);
                        }
                    } catch (NumberFormatException e) {
                        validFormat = DateError();
                    }
                }
                case 4 -> {
                    String[] dateValue = split(date[0], "-");
                    if (dateValue.length != 3
                            || (!dateValue[0].isEmpty() && dateValue[0].length() != 2)
                            || (!dateValue[2].isEmpty() && dateValue[2].length() < 4)) {
                        validFormat = DateError();
                    } else try {
                        if (!dateValue[0].isEmpty()) {
                            days = Integer.parseInt(dateValue[0]);
                        }
                        if (!dateValue[1].isEmpty()) {
                            month = Arrays.asList(MONTH).indexOf(dateValue[1].toLowerCase());
                        }
                        if (!dateValue[2].isEmpty()) {
                            years = Integer.parseInt(dateValue[2]);
                            validFormat = LimitError(years);
                        }
                    } catch (NumberFormatException e) {
                        validFormat = DateError();
                    }
                }
            }
            if (validFormat) {
                if (days < 1 || month < 1 || month > 12 || years < 0) {
                    validFormat = DateError();
                }
                boolean leapYear = (years % 4 == 0 && years % 100 != 0) || years % 400 == 0;
                if ((month == 1 && days > 31) || (month == 2 && days > 29 && leapYear)
                        || (month == 2 && days > 28 && !leapYear)
                        || (month == 3 && days > 31)
                        || (month == 4 && days > 30)
                        || (month == 5 && days > 31)
                        || (month == 6 && days > 30)
                        || (month == 7 && days > 31)
                        || (month == 8 && days > 31)
                        || (month == 9 && days > 30)
                        || (month == 10 && days > 31)
                        || (month == 11 && days > 30)
                        || (month == 12 && days > 31)) {
                    validFormat = DateError();
                }
            }
            if (date.length == 2) {
                String[] timeValue = split(date[1], ":");
                try {
                    if (!timeValue[0].isEmpty()) {
                        int hour = Integer.parseInt(timeValue[0]);
                        if (hour < 0 || hour > 23) {
                            validFormat = DateError();
                        } else {
                            if (!timeValue[1].isEmpty()) {
                                int min = Integer.parseInt(timeValue[1]);
                                if (min < 0 || min > 59) {
                                    validFormat = DateError();
                                } else {
                                    if (timeValue.length > 2) {
                                        if (!timeValue[2].isEmpty()) {
                                            int sec = Integer.parseInt(timeValue[2]);
                                            if (sec < 0 || sec > 59) {
                                                validFormat = DateError();
                                            } else if (timeValue.length > 3) {
                                                if (!timeValue[3].isEmpty()) {
                                                    int ms = Integer.parseInt(timeValue[3]);
                                                    if (ms < 0 || ms > 999) {
                                                        validFormat = DateError();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    validFormat = DateError();
                }
            }
        }
        return validFormat;
    }

    private static boolean DateError() {
        System.out.println("Недопустимый ввод");
        return false;
    }

    private static boolean LimitError(long year) {
        if ((year + 1) * 365.25 > 106751991167L) {
            System.out.println("Дата выходит за пределы календаря");
            return false;
        }
        return true;
    }

    private static String[] split(String str, String separator) {
        String[] splitStr = (str + " ").split(separator);
        if (splitStr[splitStr.length - 1] != null && splitStr[splitStr.length - 1].length() > 0) {
            splitStr[splitStr.length - 1] = splitStr[splitStr.length - 1].substring(0, splitStr[splitStr.length - 1].length() - 1);
        }
        return splitStr;
    }
}
