package com.yurisa.gra.util;

public class StringUtil {

    public static String leftTrim(String originalText) {
        return originalText.replaceAll("^\\s+", "");
    }

    public static String rightTrim(String originalText) {
        return originalText.replaceAll("\\s+$", "");
    }

}
