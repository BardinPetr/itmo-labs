package ru.bardinpetr.itmo.lab2.auth.utils;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class Base64Helper {
    private final static String DELIMITER = "$";
    private final Base64.Encoder b64Encoder = Base64.getEncoder();
    private final Base64.Decoder b64Decoder = Base64.getDecoder();


    public String joinEncode(byte[]... data) {
        return Arrays
                .stream(data)
                .map(b64Encoder::encodeToString)
                .collect(Collectors.joining(DELIMITER));
    }

    public List<byte[]> splitDecode(String data) {
        return Arrays
                .stream(data.split("\\%s".formatted(DELIMITER)))
                .map(b64Decoder::decode)
                .toList();
    }
}
