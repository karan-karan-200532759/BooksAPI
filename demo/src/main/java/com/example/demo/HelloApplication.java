package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("searchView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 650, 540);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Book LookUp!");
        root.requestFocus();;

        stage.getIcons().add(new Image("book.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}