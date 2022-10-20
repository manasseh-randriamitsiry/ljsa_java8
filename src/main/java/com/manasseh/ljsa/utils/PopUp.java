package com.manasseh.ljsa.utils;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class PopUp {
    public void error(String title, String content){
        Notifications.create()
                .title(title)
                .text(content)
                .position(Pos.TOP_RIGHT)
                .showError();
    }

    public void success(String title, String content){
        Notifications.create()
                .title(title)
                .text(content)
                .position(Pos.TOP_RIGHT)
                .showInformation();
    }
}
