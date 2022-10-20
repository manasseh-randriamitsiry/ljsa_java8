package manasseh.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class LoadPage {
    public void loadPage(ActionEvent event, String nextPage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(nextPage+".fxml")));
        Stage stage = new Stage();
        stage.getScene();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
}
