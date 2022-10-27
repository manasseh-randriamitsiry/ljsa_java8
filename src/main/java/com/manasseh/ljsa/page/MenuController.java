package com.manasseh.ljsa.page;
import animatefx.animation.*;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import manasseh.utils.ActivateDrag;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController extends ActivateDrag implements Initializable {
    public Button home_btn;
    public Button etudiant_btn;
    public Button profs_btn;
    public StackPane body;
    public Button matiere_btn;
    public SplitMenuButton classe_btn;
    public BorderPane border_pane;
    Parent root = null;
    public void home() {
        loadContent("home");
        home_btn.setStyle("-fx-background-color: #996699");
        profs_btn.setStyle("-fx-background-color: #99CC66");
        etudiant_btn.setStyle("-fx-background-color: #99CC66");
        matiere_btn.setStyle("-fx-background-color: #99CC66");
        classe_btn.setText("classe");
    }
    public void etudiant() {
        loadContent("etudiant");
        home_btn.setStyle("-fx-background-color: #99CC66");
        home_btn.setStyle("-fx-background-color: #99CC66");
        profs_btn.setStyle("-fx-background-color: #99CC66");
        etudiant_btn.setStyle("-fx-background-color: #996699");
        matiere_btn.setStyle("-fx-background-color: #99CC66");
        classe_btn.setText("classe");
    }
    public void terminale() {
        loadContent("terminale");
        classe_btn.setText("Classe >Terminale");
        home_btn.setStyle("-fx-background-color: #99CC66");
        home_btn.setStyle("-fx-background-color: #99CC66");
        profs_btn.setStyle("-fx-background-color: #99CC66");
        etudiant_btn.setStyle("-fx-background-color: #99CC66");
        matiere_btn.setStyle("-fx-background-color: #99CC66");
    }
    public void premiere() {
        loadContent("premiere");
        classe_btn.setText("Classe >Premiere");
        home_btn.setStyle("-fx-background-color: #99CC66");
        home_btn.setStyle("-fx-background-color: #99CC66");
        profs_btn.setStyle("-fx-background-color: #99CC66");
        etudiant_btn.setStyle("-fx-background-color: #99CC66");
        matiere_btn.setStyle("-fx-background-color: #99CC66");
    }

    public void seconde() {
        loadContent("seconde");
        classe_btn.setText("Classe >Seconde");
        home_btn.setStyle("-fx-background-color: #99CC66");
        home_btn.setStyle("-fx-background-color: #99CC66");
        profs_btn.setStyle("-fx-background-color: #99CC66");
        etudiant_btn.setStyle("-fx-background-color: #99CC66");
        matiere_btn.setStyle("-fx-background-color: #99CC66");
    }
    public void matiere(){
        loadContent("matiere");
        home_btn.setStyle("-fx-background-color: #99CC66");
        home_btn.setStyle("-fx-background-color: #99CC66");
        profs_btn.setStyle("-fx-background-color: #99CC66");
        etudiant_btn.setStyle("-fx-background-color: #99CC66");
        matiere_btn.setStyle("-fx-background-color: #996699");
        classe_btn.setText("classe");
    }
    public void profs() {
        loadContent("profs");
        home_btn.setStyle("-fx-background-color: #99CC66");
        home_btn.setStyle("-fx-background-color: #99CC66");
        profs_btn.setStyle("-fx-background-color: #996699");
        etudiant_btn.setStyle("-fx-background-color: #99CC66");
        matiere_btn.setStyle("-fx-background-color: #99CC66");
        classe_btn.setText("classe");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            loadContent("home");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void loadContent(String page) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        } catch (IOException e){
            e.printStackTrace();
        }
        new ZoomIn(root).play();
        body.getChildren().removeAll(root);
        body.getChildren().setAll(root);
    }

    public void iconified() {
        Stage stage = (Stage) home_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    public void close() {
        System.exit(0);
    }

    public void activateDrag(MouseEvent event) {
        activate(event, border_pane);
    }

    public void getCursorPosition(MouseEvent event) {
        pressed(event);
    }

}
