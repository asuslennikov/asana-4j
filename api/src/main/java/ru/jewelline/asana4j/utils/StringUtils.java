package ru.jewelline.asana4j.utils;

import java.nio.charset.Charset;

public final class StringUtils {
    private StringUtils() {
    }

    public static String getSubstring(String data, String startMarker, String endMarker) {
        if (data != null && startMarker != null && endMarker != null) {
            int startMarkerPosition = data.indexOf(startMarker);
            if (startMarkerPosition >= 0) {
                int startSubStringPosition = startMarkerPosition + startMarker.length();
                return data.substring(startSubStringPosition, getEndSubStringPosition(data, startSubStringPosition, endMarker));
            }
        }
        return null;
    }

    private static int getEndSubStringPosition(String data, int startSubstringPosition, String endMarker) {
        int endSubStringPosition = data.indexOf(endMarker, startSubstringPosition);
        if (endSubStringPosition < 0) {
            endSubStringPosition = data.length();
        }
        return endSubStringPosition;
    }

    public static boolean emptyOrOnlyWhiteSpace(String candidate) {
        return candidate == null || nonNullOnlyWhiteSpace(candidate);
    }

    private static boolean nonNullOnlyWhiteSpace(String candidate) {
        for (int i = 0; i < candidate.length(); i++) {
            if (!Character.isWhitespace(candidate.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Charset getCharset() {
        return Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset();
    }
}
