package ru.jewelline.asana4j.examples;

import ru.jewelline.asana4j.utils.Base64;

import javax.xml.bind.DatatypeConverter;

public class Base64Tool implements Base64 {
    @Override
    public String encode(String stringToEncode) {
        if (stringToEncode != null) {
            return DatatypeConverter.printBase64Binary(stringToEncode.getBytes());
        }
        return null;
    }

    @Override
    public String decode(String stringToDecode) {
        if (stringToDecode != null) {
            byte[] bytes = DatatypeConverter.parseBase64Binary(stringToDecode);
            if (bytes != null) {
                return new String(bytes);
            }
        }
        return null;
    }
}
