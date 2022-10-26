package com.manasseh.ljsa.page;

import animatefx.animation.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import com.manasseh.ljsa.DAO.ProfDAO;
import com.manasseh.ljsa.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import com.manasseh.ljsa.utils.PopUp;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfController implements Initializable{
    public Pane profsPane;
    public Label professeur;
    public Button btn_ajout_prof;
    public Button btn_action;
    public TableView<Prof> table_prof;
    public Pane action_pane;
    public TextField numero_matricule_input;
    public TextField nom_prof_input;
    public TextField prenom_prof_input;
    public TableColumn<Prof,String> n_mat_column;
    public TableColumn<Prof,String> nom_column;
    public TableColumn<Prof,String> prenom_column;
    public TableColumn<Prof,String> date_nais_column;
    public TableColumn<Prof,Prof> actionColumn;
    public DatePicker date_nais_picker;
    public Label id;
    public TextField recherche_input;
    ObservableList<Prof> profList = FXCollections.observableArrayList();
    ProfDAO dao = new ProfDAO();
    Prof prof = null;
    PopUp popUp = new PopUp();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        check();
        new FadeOutRight(action_pane).play();
        refreshTable();

        n_mat_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getN_mat()));
        nom_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNom_prof()));
        prenom_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPrenom_prof()));
        date_nais_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDate_nais().toString()));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        // creation de bouton ajout et suppression
        Callback<TableColumn<Prof, Prof>, TableCell<Prof, Prof>> cellFactory=  (TableColumn<Prof, Prof> param) -> new TableCell<Prof,Prof>() {
            @Override
            public void updateItem(Prof item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    final Button editBtn = new Button("Editer");
                    final Button dltBtn = new Button("Supprimer");
                    dltBtn.setStyle("-fx-background-color:#FF6666");
                    editBtn.setOnAction(event -> {
                        action_pane.setVisible(true);
                        try {
                            prof = getTableView().getItems().get(getIndex());
                            professeur.setText("Professeur: edition");
                            btn_action.setText("Mettre à jour");
                            ProfController.this.setText(
                                    prof.getId(),
                                    prof.getN_mat(),
                                    prof.getNom_prof(),
                                    prof.getPrenom_prof(),
                                    prof.getDate_nais());
                            new FadeInRight(action_pane).play();
                        } catch (NullPointerException e) {
                            popUp.error("Information", "Selectionner un champ avant de cliquer sur editer. Merci");
                        }
                    });
                    dltBtn.setOnAction(event -> {
                        action_pane.setVisible(false);
                        try {
                            dao.delete(getTableView().getItems().get(getIndex()).getId(), "profs", "id");
                            refreshTable();
                        } catch (SQLException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    HBox hb = new HBox();
                    hb.setSpacing(2);
                    hb.setStyle("-fx-alignment:center");
                    hb.getChildren().addAll(editBtn, dltBtn);
                    setGraphic(hb);
                    setText(null);
                }
            }
        };
        actionColumn.setCellFactory(cellFactory);

        // bouton action toggle
        btn_action.setOnAction(event -> {
            if (btn_action.getText().equals("Ajouter")){
                try {
                    prof = new Prof(0,numero_matricule_input.getText(),nom_prof_input.getText(),prenom_prof_input.getText(),date_nais_picker.getValue());
                    dao.insert(prof);
                    new FadeOutRight(action_pane).play();
                    refreshTable();
                    clearInputs();
                } catch (Exception e) {
                    popUp.error("erreur","Erreur, essaye encore une fois");
                }
            } else if (btn_action.getText().equals("Mettre à jour")){
                try {
                    prof = new Prof(Integer.valueOf(id.getText()),numero_matricule_input.getText(),nom_prof_input.getText(),prenom_prof_input.getText(),date_nais_picker.getValue());
                    dao.update(prof);
                    new FadeOutRight(action_pane).play();
                    refreshTable();
                    clearInputs();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void activerRecherche(){
        FilteredList<Prof> filteredList = new FilteredList<>(profList,prof -> true);
        recherche_input.textProperty().addListener((Observable,oldValue,newValue)-> filteredList.setPredicate(prof -> prof.getNom_prof().toUpperCase().contains(newValue.toUpperCase())
                    || prof.getPrenom_prof().toLowerCase().contains(newValue.toLowerCase())
                    || prof.getN_mat().toUpperCase().contains(newValue.toUpperCase())
                    || prof.getDate_nais().toString().contains(newValue)));
        SortedList<Prof> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table_prof.comparatorProperty());
        table_prof.setItems(sortedData);
    }
    public void refreshTable() {
        profList.clear();
        profList.setAll(dao.listAll());
        activerRecherche();
    }
    public void clearInputs(){
        numero_matricule_input.setText("");
        nom_prof_input.setText("");
        prenom_prof_input.setText("");
    }
    public void ajoutProfesseur(){
        action_pane.setVisible(true);
        clearInputs();
        professeur.setText("Professeur: ajout");
        btn_action.setText("Ajouter");
        new FadeInRightBig(action_pane).play();
    }
    public void check(){
        if (!numero_matricule_input.getText().isEmpty()
                && !nom_prof_input.getText().isEmpty()
                && !prenom_prof_input.getText().isEmpty()
                && date_nais_picker.getValue() !=null){
            btn_action.setVisible(true);
        } else if (numero_matricule_input.getText().isEmpty()
                || nom_prof_input.getText().isEmpty()
                || prenom_prof_input.getText().isEmpty()
                || date_nais_picker.getValue() !=null){
            btn_action.setVisible(false);
        }
    }
    public void setText(Integer id,String n_mat,String nom,String prenom, LocalDate date_nais){
        numero_matricule_input.setText(n_mat);
        nom_prof_input.setText(nom);
        prenom_prof_input.setText(prenom);
        date_nais_picker.setValue(date_nais);
        this.id.setText(String.valueOf(id));
    }
    public void close() {
        System.exit(0);
    }
    public void zoomText() {
        profsPane.setStyle("-fx-font-size:14px");
    }

}