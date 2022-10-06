package com.example.cryptojavafx;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface Encoder {
    String TXT = ".txt";

    char encode(char c, int shift);

    char decode(char c, int shift);

    int bruteForceKey(String text, String exampleText);

    default String encode(String string, int shift) {
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            sb.append(encode(c, shift));
        }

        return sb.toString();
    }

    default String decode(String string, int shift) {
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            sb.append(decode(c, shift));
        }

        return sb.toString();
    }

    default String addSuffixToFileName(String fileName, String suffix) {
        return fileName.substring(0, fileName.length() - 4) + suffix + TXT;
    }

    default String readString(String filePath) {
        String text = null;
        try {
            text = String.join("\n", Files.readAllLines(Paths.get(filePath, "")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    default void writeStirng(Path filePath, String text) {
        try {
            Files.write(filePath, text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default void encodeFile(String filePath, int key) {
        String text = readString(filePath);
        Path outFilePath = Paths.get(addSuffixToFileName(filePath,"_encoded"), "");

        String encodedText = encode(text, key);
        writeStirng(outFilePath, encodedText);
    }

    default void decodeFile(String filePath, int key) {
        String text = readString(filePath);
        Path outFilePath = Paths.get(addSuffixToFileName(filePath,"_decoded"), "");

        String decodedText = decode(text, key);
        writeStirng(outFilePath, decodedText);
    }

    char[] getAlphabet();

    int[] getHistogram(String text);
}
