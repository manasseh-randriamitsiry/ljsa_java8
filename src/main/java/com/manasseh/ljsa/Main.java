package com.manasseh.ljsa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//       FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("page/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String style = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
        stage.initStyle(StageStyle.UNDECORATED);
        scene.getStylesheets().add(style);
        stage.setTitle("ljsa");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}