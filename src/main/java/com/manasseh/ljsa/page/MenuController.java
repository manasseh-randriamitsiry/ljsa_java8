package com.manasseh.ljsa.page;
import animatefx.animation.*;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.manasseh.ljsa.utils.ActivateDrag;
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


    public SplitMenuButton classes_btn;
    public BorderPane border_pane;
    public Button classe_btn;
    public Button categorie_btn;
    Parent root = null;
    public void home() {
        loadContent("home");
        home_btn.setStyle("-fx-background-color: #EBD26A");
        profs_btn.setStyle("-fx-background-color:none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        classes_btn.setText("classe");
    }
    public void etudiant() {
        loadContent("etudiant");
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: #EBD26A");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
    }
    public void terminale() {
        loadContent("terminale");
        categorie_btn.setStyle("-fx-background-color: #EBD26A");
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
    }
    public void premiere() {
        loadContent("premiere");
        categorie_btn.setStyle("-fx-background-color: #EBD26A");
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        home_btn.setStyle("-fx-background-color: none");
    }

    public void seconde() {
        loadContent("seconde");
        categorie_btn.setStyle("-fx-background-color: #EBD26A");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        home_btn.setStyle("-fx-background-color: none");
    }
    public void matiere(){
        loadContent("matiere");
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: #EBD26A");
        classe_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
    }
    public void profs() {
        loadContent("profs");
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: #EBD26A");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
    }
    public void classe() {
        loadContent("classe");
        home_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: #EBD26A");
        etudiant_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home();
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
