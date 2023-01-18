package com.manasseh.ljsa.page;
import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.stage.StageStyle;

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
    public Button releve_btn;
    Parent root = null;
    public void home() {
        loadContent("home");
        home_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(home_btn).play();
        profs_btn.setStyle("-fx-background-color:none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
        classes_btn.setText("classe");
    }
    public void etudiant() {
        loadContent("etudiant");
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(etudiant_btn).play();
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: none");
    }
    public void terminale() {
        loadContent("terminale");
        categorie_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(categorie_btn).play();
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: none");
    }
    public void premiere() {
        loadContent("premiere");
        categorie_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(categorie_btn).play();
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        home_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: none");
    }

    public void seconde() {
        loadContent("seconde");
        categorie_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(categorie_btn).play();
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        home_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: none");
    }
    public void matiere(){
        loadContent("matiere");
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(matiere_btn).play();
        classe_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: none");
    }
    public void profs() {
        loadContent("profs");
        home_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(profs_btn).play();
        etudiant_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: none");
    }
    public void classe() {
        loadContent("classe");
        home_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(classe_btn).play();
        etudiant_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: none");
    }
    public void releveSeconde() {
        loadContent("releveSeconde");
        home_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(releve_btn).play();
    }
    public void relevePremiere() {
        loadContent("relevePremiere");
        home_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(releve_btn).play();
    }
    public void releveTerminale() {
        loadContent("releveTerminale");
        home_btn.setStyle("-fx-background-color: none");
        classe_btn.setStyle("-fx-background-color: none");
        etudiant_btn.setStyle("-fx-background-color: none");
        profs_btn.setStyle("-fx-background-color: none");
        matiere_btn.setStyle("-fx-background-color: none");
        categorie_btn.setStyle("-fx-background-color: none");
        releve_btn.setStyle("-fx-background-color: #EBD26A");
        new ZoomIn(releve_btn).play();
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

    public void logout(ActionEvent actionEvent) {
        try{
            loadPage(actionEvent,"../login");
        } catch (Exception e){
            e.printStackTrace();
        }
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
}
