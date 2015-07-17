package ru.jewelline.asana4j.se;

import ru.jewelline.asana4j.utils.Base64;

public class Base64AndroidUtil implements Base64 {
    @Override
    public String encode(String stringToEncode) {
        if (stringToEncode == null){
            return null;
        }
        return android.util.Base64.encodeToString(stringToEncode.getBytes(),
                android.util.Base64.NO_PADDING | android.util.Base64.NO_WRAP);
    }

    @Override
    public String decode(String stringToDecode) {
        if (stringToDecode == null){
            return null;
        }
        return android.util.Base64.encodeToString(stringToDecode.getBytes(),
                android.util.Base64.NO_PADDING | android.util.Base64.NO_WRAP);
    }
}
