package com.example.cryptojavafx;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HelloController {
    Encoder encoder = new EnglishEncoder();

    @FXML
    protected void initialize() {
        keyInput.setText("0");

        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            updateChart(inputTextArea, inputChart);
        });
        outputTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            updateChart(outputTextArea, outputChart);
        });
    }

    @FXML
    private TextField keyInput;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private LineChart<String, Integer> inputChart;

    @FXML
    private LineChart<String, Integer> outputChart;

    @FXML
    protected void onMinusButtonClicked() {
        int key = ensureKey();
        key--;
        keyInput.setText(String.valueOf(key));

        encode();
    }
    @FXML
    protected void onPlusButtonClicked() {
        int key = ensureKey();
        key++;
        keyInput.setText(String.valueOf(key));

        encode();
    }

    @FXML
    protected void onKeyReleasedInKeyInput(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.ENTER) {
            encode();
        }
    }

    private void updateChart(TextArea textArea, LineChart<String, Integer> chart) {
        String text = textArea.getText();
        chart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        char[] alphabet = encoder.getAlphabet();
        int[] histogram = encoder.getHistogram(text);

        for (int i = 0; i < alphabet.length; i++) {
            series.getData().add(new XYChart.Data(Character.toString(alphabet[i]), histogram[i]));
        }

        chart.getData().add(series);
    }

    private int ensureKey() {
        try {
            return Integer.parseInt(keyInput.getText());
        } catch (NumberFormatException e) {
            keyInput.setText("0");
        }

        return 0;
    }

    private void encode() {
        int key = ensureKey();
        String text = inputTextArea.getText();
        String encoded = encoder.encode(text, key);
        outputTextArea.setText(encoded);
    }
}