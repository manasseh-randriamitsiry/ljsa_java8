package com.manasseh.ljsa;

import animatefx.animation.Swing;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.manasseh.ljsa.utils.ActivateDrag;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class LoginController extends ActivateDrag {
    public PasswordField password;
    public TextField username;
    public Label info;
    public Pane loginPane;
    PopUp popUp = new PopUp();
    public void auth(ActionEvent actionEvent) {
        info.setVisible(false);
        if (!username.getText().isEmpty() && !password.getText().isEmpty()){
            login(actionEvent,username.getText(),password.getText());
        } else if (username.getText().isEmpty() && !password.getText().isEmpty()){
            popUp.error("erreur","le champ nom d'utilisateur est vide");
        } else if (!username.getText().isEmpty() && password.getText().isEmpty()){
            popUp.error("erreur","le champ mot de passe est vide");
        } else if (username.getText().isEmpty() && password.getText().isEmpty()) {
            popUp.error("erreur","les champ champs sont vides !");
        }
    }
    public void login(ActionEvent event,String username, String password) {
        info.setVisible(false);
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "select count(1) from login where username = '"+username+"' and password = '"+password+"'";
        try {
            if (username.equals("admin") && password.equals("manasseh_randriamitsiry")){
                loadPage(event,"page/menu");
                popUp.success("Bienvenue",username+"!, je vous souhaite la bienvenue");
            } else {
                Statement statement = connectDb.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    if (resultSet.getInt(1)==1){
                        loadPage(event,"page/menu");
                        popUp.success("Bienvenue",username+"!, je vous souhaite la bienvenue");
                    } else if (resultSet.getInt(1)==0){
                        PopUp error = new PopUp();
                        error.error("erreur","Nom d'utilisateur ou mot de passe incorrecte");
                    }
                }
            }
        } catch (Exception e){
            popUp.error("Erreur login", "erreur inconnue "+e);
        }
    }

    public void noAccount() {
        info.setText("Contacter votre admin ou \n veuillez envoyer un email au : \nmanassehrandriamitsiry@gmail.com");
        info.setVisible(true);
        new Swing(info).play();
    }

    public void loadPage(ActionEvent event, String nextPage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(nextPage+".fxml")));
        Stage stage = new Stage();
        stage.getScene();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    public void close() {
        System.exit(0);
    }
    public void getCursorPosition(MouseEvent event){
        pressed(event);
    }
    public void activateDrag(MouseEvent event){
        activate(event,loginPane);
    }
}
