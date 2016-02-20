package ru.jewelline.asana.core.utils;

public interface Base64 {
    String encode(String stringToEncode);

    String decode(String stringToDecode);
}
