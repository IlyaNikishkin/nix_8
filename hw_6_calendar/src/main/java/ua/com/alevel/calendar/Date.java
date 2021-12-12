package ua.com.alevel.calendar;

import java.util.Arrays;

public class Date {

    private static final int[] DAYS_OFFSET = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    private static final int[] DAYS_OFFSET_LEAP_YEAR = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};
    private static final String[] MONTHS = {"january", "february", "march", "april", "may",
            "june", "july", "august", "september", "october", "november", "december"};
    private long time;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;
    private int ms;

    public Date(String stringValue, String format) {
        time = toMilliseconds(stringValue, format);
    }

    public boolean addTime(long milliseconds, int seconds, int minutes, int hours, int days, int years) {
        long tempTime = time;
        int tempMonth = month;
        int tempYear;
        int tempDay;
        int tempHour;
        int tempMin;
        int tempSec;
        int tempMs;
        tempTime = tempTime + milliseconds + 1000 * (seconds + 60 * (minutes + 60 * (hours + 24L * days)));
        tempMs = (int) (tempTime % 1000);
        long rest = (tempTime - tempMs) / 1000;
        tempSec = (int) (rest % 60);
        rest = (rest - tempSec) / 60;
        tempMin = (int) (rest % 60);
        rest = (rest - tempMin) / 60;
        tempHour = (int) (rest % 24);
        tempDay = (int) ((rest - tempHour) / 24);
        rest = tempDay % (4 * 25 * (366 + 3 * 365) - 3);
        int integer = tempDay / (4 * 25 * (366 + 3 * 365) - 3);
        tempYear = integer * 400;
        tempDay = (int) rest;
        rest = tempDay % (25 * (366 + 3 * 365) - 1);
        integer = tempDay / (25 * (366 + 3 * 365) - 1);
        tempYear = tempYear + integer * 100;
        tempDay = (int) rest;
        rest = tempDay % (366 + 3 * 365);
        integer = tempDay / (366 + 3 * 365);
        tempYear = tempYear + integer * 4;
        tempDay = (int) rest;
        if (tempDay < 0 || tempHour < 0 || tempMin < 0 || tempSec < 0 || tempMs < 0) {
            return false;
        } else {
            if (tempDay <= 366) {
                rest = tempDay % 366;
                integer = tempDay / 366;
            } else {
                tempDay = tempDay - 366;
                tempYear++;
                rest = tempDay % 365;
                integer = tempDay / 365;
            }
            tempYear = tempYear + integer;
            tempDay = (int) rest;
            if ((tempYear % 4 == 0 && tempYear % 100 != 0) || tempYear % 400 == 0) {
                for (int i = 1; i < DAYS_OFFSET_LEAP_YEAR.length; i++) {
                    if (tempDay >= DAYS_OFFSET_LEAP_YEAR[i - 1] && tempDay < DAYS_OFFSET_LEAP_YEAR[i]) {
                        tempMonth = i;
                        break;
                    }
                }
                tempDay = tempDay - DAYS_OFFSET_LEAP_YEAR[tempMonth - 1] + 1;
            } else {
                for (int i = 1; i < DAYS_OFFSET.length; i++) {
                    if (tempDay >= DAYS_OFFSET[i - 1] && tempDay < DAYS_OFFSET[i]) {
                        tempMonth = i;
                        break;
                    }
                }
                tempDay = tempDay - DAYS_OFFSET[tempMonth - 1] + 1;
            }
            tempYear = tempYear + years;
            if (tempYear >= 0) {
                time = tempTime;
                year = tempYear;
                month = tempMonth;
                day = tempDay;
                hour = tempHour;
                min = tempMin;
                sec = tempSec;
                ms = tempMs;
                return true;
            } else return false;
        }
    }

    public long timeWithoutYears() {
        int days = day - 1 + DAYS_OFFSET[month - 1];
        if (month > 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) days++;
        }
        return ms + 1000 * (sec + 60 * (min + 60 * (hour + 24L * days)));
    }

    public String toString(String format) {
        String stringDate = "";
        String stringDay;
        String stringMonth;
        String stringYear;
        String stringHour;
        String stringMin;
        String stringSec;
        String stringMs;
        switch (format) {
            case "dd/mm/yy" -> {
                if (day < 10) stringDay = "0" + day;
                else stringDay = String.valueOf(day);
                if (month < 10) stringMonth = "0" + month;
                else stringMonth = String.valueOf(month);
                if (year < 10) stringYear = "0" + year;
                else stringYear = String.valueOf(year);
                stringDate = stringDay + "/" + stringMonth + "/" + stringYear;
            }
            case "m/d/yyyy" -> {
                if (year < 1000 && year > 99) {
                    stringYear = "0" + year;
                } else if (year < 100 && year > 9) {
                    stringYear = "00" + year;
                } else if (year < 10) {
                    stringYear = "000" + year;
                } else stringYear = String.valueOf(year);
                stringDate = month + "/" + day + "/" + stringYear;
            }
            case "mmm-d-yy" -> {
                if (year < 10) stringYear = "0" + year;
                else stringYear = String.valueOf(year);
                stringMonth = MONTHS[month - 1].substring(0, 1).toUpperCase() + MONTHS[month - 1].substring(1);
                stringDate = stringMonth + "-" + day + "-" + stringYear;
            }
            case "dd-mmm-yyyy" -> {
                if (day < 10) stringDay = "0" + day;
                else stringDay = String.valueOf(day);
                if (year < 1000 && year > 99) {
                    stringYear = "0" + year;
                } else if (year < 100 && year > 9) {
                    stringYear = "00" + year;
                } else if (year < 10) {
                    stringYear = "000" + year;
                } else stringYear = String.valueOf(year);
                stringMonth = MONTHS[month - 1].substring(0, 1).toUpperCase() + MONTHS[month - 1].substring(1);
                stringDate = stringDay + "-" + stringMonth + "-" + stringYear;
            }
        }
        if (hour != 0 || min != 0 || sec != 0 || ms != 0) {
            if (hour < 10) stringHour = "0" + hour;
            else stringHour = String.valueOf(hour);
            if (min < 10) stringMin = "0" + min;
            else stringMin = String.valueOf(min);
            stringDate = stringDate + " " + stringHour + ":" + stringMin;
            if (ms != 0) {
                if (sec < 10) stringSec = "0" + sec;
                else stringSec = String.valueOf(sec);
                if (ms < 10) stringMs = "00" + ms;
                else if (ms < 100) {
                    stringMs = "0" + ms;
                } else stringMs = String.valueOf(ms);
                stringDate = stringDate + ":" + stringSec + ":" + stringMs;
            }
            if (sec != 0 && ms == 0) {
                if (sec < 10) stringSec = "0" + sec;
                else stringSec = String.valueOf(sec);
                stringDate = stringDate + ":" + stringSec;
            }
        }
        return stringDate;
    }

    public long time() {
        return time;
    }

    public int getYear() {
        return year;
    }

    private long toMilliseconds(String stringValue, String format) {
        String[] dateBlocks = stringValue.split("\s");
        switch (format) {
            case "dd/mm/yy" -> {
                String[] dateValue = split(dateBlocks[0], "/");
                if (dateValue[0].isEmpty()) day = 1;
                else day = Integer.parseInt(dateValue[0]);
                if (dateValue[1].isEmpty()) month = 1;
                else month = Integer.parseInt(dateValue[1]);
                if (dateValue[2].isEmpty()) year = 0;
                else year = Integer.parseInt(dateValue[2]);
            }
            case "m/d/yyyy" -> {
                String[] dateValue = split(dateBlocks[0], "/");
                if (dateValue[1].isEmpty()) day = 1;
                else day = Integer.parseInt(dateValue[0]);
                if (dateValue[0].isEmpty()) month = 1;
                else month = Integer.parseInt(dateValue[1]);
                if (dateValue[2].isEmpty()) year = 0;
                else year = Integer.parseInt(dateValue[2]);
            }
            case "mmm-d-yy" -> {
                String[] dateValue = split(dateBlocks[0], "-");
                if (dateValue[1].isEmpty()) day = 1;
                else day = Integer.parseInt(dateValue[1]);
                if (dateValue[0].isEmpty()) month = 1;
                else month = Arrays.asList(MONTHS).indexOf(dateValue[0].toLowerCase()) + 1;
                if (dateValue[2].isEmpty()) year = 0;
                else year = Integer.parseInt(dateValue[2]);
            }

            case "dd-mmm-yyyy" -> {
                String[] dateValue = split(dateBlocks[0], "-");
                if (dateValue[0].isEmpty()) day = 1;
                else day = Integer.parseInt(dateValue[0]);
                if (dateValue[1].isEmpty()) month = 1;
                else month = Arrays.asList(MONTHS).indexOf(dateValue[1].toLowerCase()) + 1;
                if (dateValue[2].isEmpty()) year = 0;
                else year = Integer.parseInt(dateValue[2]);
            }
        }
        hour = 0;
        min = 0;
        sec = 0;
        ms = 0;
        if (dateBlocks.length == 2) {
            String[] timeValue = split(dateBlocks[1], ":");
            if (!timeValue[0].isEmpty()) {
                hour = Integer.parseInt(timeValue[0]);
            }
            if (!timeValue[1].isEmpty()) {
                min = Integer.parseInt(timeValue[1]);
            }
            if (timeValue.length > 2) {
                if (!timeValue[2].isEmpty()) sec = Integer.parseInt(timeValue[2]);
            }
            if (timeValue.length > 3) {
                if (!timeValue[3].isEmpty()) ms = Integer.parseInt(timeValue[3]);
            }
        }
        int leapYearDays = (year + 3) / 4 + (year + 399) / 400 - (year + 99) / 100;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            time = ms + 1000 * (sec + 60 * (min + 60 * (hour + 24 * (day - 1 + DAYS_OFFSET_LEAP_YEAR[month - 1] + 365L * year + leapYearDays))));
        } else {
            time = ms + 1000 * (sec + 60 * (min + 60 * (hour + 24 * (day - 1 + DAYS_OFFSET[month - 1] + 365L * year + leapYearDays))));
        }
        return time;
    }

    private static String[] split(String str, String separator) {
        String[] splitStr = (str + " ").split(separator);
        if (splitStr[splitStr.length - 1] != null && splitStr[splitStr.length - 1].length() > 0) {
            splitStr[splitStr.length - 1] = splitStr[splitStr.length - 1].substring(0, splitStr[splitStr.length - 1].length() - 1);
        }
        return splitStr;
    }
}
