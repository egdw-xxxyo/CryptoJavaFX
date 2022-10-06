package com.example.cryptojavafx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class EnglishEncoder implements Encoder {
    private static final char smallA = 'a';
    private static final char smallZ = 'z';
    private static final char bigA = 'A';
    private static final char bigZ = 'Z';
    private static final int alphabetSize = smallZ - smallA +1;
    public static final String TXT = ".txt";

    @Override
    public char encode(char c, int shift) {
        if (!inAlphabet(c)) {
            return c;
        }
        shift = normalizeShift(shift);

        if (isSmallLetter(c)) {
            char result = (char) (c + shift);
            if (result > smallZ) {
                result -= alphabetSize;
            }
            return result;
        }

        char result = (char) (c + shift);
        if (result > bigZ) {
            result -= alphabetSize;
        }
        return result;
    }

    @Override
    public char decode(char c, int shift) {
        if (!inAlphabet(c)) {
            return c;
        }

        shift = normalizeShift(shift);

        if (isSmallLetter(c)) {
            char result = (char) (c - shift);
            if (result < smallA) {
                result += alphabetSize;
            }
            return result;
        } else {
            char result = (char) (c - shift);
            if (result < bigA) {
                result += alphabetSize;
            }
            return result;
        }
    }

    public int normalizeShift(int shift) {
        shift = shift % alphabetSize;
        if (shift < 0) {
            shift += alphabetSize;
        }

        return shift;
    }

    public boolean inAlphabet(char c) {
        return isSmallLetter(c) || isBigLetter(c);
    }

    public boolean isSmallLetter(char c) {
        return c >= smallA && c <= smallZ;
    }

    public boolean isBigLetter(char c) {
        return c >= bigA && c <= bigZ;
    }

    @Override
    public int bruteForceKey(String text, String exampleText) {
        Map<Integer, Long> keyToDiffMap = new HashMap<>();

        StaticAnalizer staticAnalizer = new StaticAnalizer();

        int[] exampleHistogram = staticAnalizer.getCharHistogram(exampleText, bigA, smallZ);

        for (int i = 1; i <= alphabetSize; i++) {
            String encoded = decode(text, i);
            int[] charHistogram = staticAnalizer.getCharHistogram(encoded, bigA, smallZ);
            long diff = staticAnalizer.compareHistograms(exampleHistogram, charHistogram);

            keyToDiffMap.put(i, diff);
        }

        Optional<Map.Entry<Integer, Long>> min = keyToDiffMap.entrySet().stream().min((o1, o2) -> (int) (o1.getValue() - o2.getValue()));

        return min.get().getKey();
    }

    @Override
    public char[] getAlphabet() {
        char[] result = new char[alphabetSize * 2];

        for (int i = 0; i < alphabetSize; i++) {
            result[i] = (char) (smallA + i);
            result[i + alphabetSize] = (char) (bigA + i);
        }
        return result;
    }

    @Override
    public int[] getHistogram(String text) {
        StaticAnalizer staticAnalizer = new StaticAnalizer();
        int[] part1 = staticAnalizer.getCharHistogram(text, smallA, smallZ);
        int[] part2 = staticAnalizer.getCharHistogram(text, bigA, bigZ);

        return IntStream.concat(Arrays.stream(part1), Arrays.stream(part2)).toArray();
    }
}
