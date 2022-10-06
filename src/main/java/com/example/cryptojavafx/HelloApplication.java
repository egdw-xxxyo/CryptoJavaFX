package com.example.cryptojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            launch();
        }else {
            cli(args);
        }
    }

    public static void cli(String[] args) {
        String command = args[0];

        Encoder encoder = new EnglishEncoder();

        if ("encode".equals(command)) {
            String filePath = args[1];
            int key = Integer.parseInt(args[2]);
            encoder.encodeFile(filePath, key);
        } else if ("decode".equals(command)) {
            String filePath = args[1];
            int key = Integer.parseInt(args[2]);
            encoder.decodeFile(filePath, key);
        } else if ("bruteForce".equals(command)) {

        } else {
            throw new IllegalArgumentException(String.format("Isn't a command '%s'", command));
        }
    }
}