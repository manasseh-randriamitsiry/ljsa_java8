package com.manasseh.ljsa.utils;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ActivateDrag {
    public double x;
    public double y;
    public void activate(MouseEvent event, Pane pane){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
    public void pressed(MouseEvent event){
        x = event.getSceneX();
        y = event.getSceneY();
    }

}
