package com.example.cryptojavafx;

public class StaticAnalizer {
    public int[] getCharHistogram(String text, char rangeFrom, char rangeTo) {

        int[] result = new int[rangeTo - rangeFrom + 1];

        for (char c : text.toCharArray()) {
            if (c >= rangeFrom && c <= rangeTo) {
                result[c - rangeFrom]++;
            }
        }

        return normalize(result);
    }

    public long compareHistograms(int[] h1, int[] h2) {
        long diff = 0;

        for (int i = 0; i < h1.length; i++) {
            diff += Math.abs(h1[i] - h2[i]);
        }

        return diff;
    }

    private int[] normalize(int[] arr) {
        int max = 0;
        for (int n : arr) {
            if (max < n) {
                max = n;
            }
        }

        double devisor = max / 100.0;

        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = (int) (arr[i] / devisor);
        }

        return result;
    }
}
