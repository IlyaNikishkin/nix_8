package ua.com.alevel.util;

public final class UsdUtil {

    public static Double toUsd(long l) {
        return l / 100 + (double) (l % 100) / 100;
    }

    public static long toCent(Double d) {
        return (long) (d * 100);
    }
}
