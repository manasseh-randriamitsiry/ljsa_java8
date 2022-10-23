package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import com.manasseh.ljsa.DAO.MatiereDao;
import com.manasseh.ljsa.model.Matiere;
import com.manasseh.ljsa.utils.PopUp;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MatiereController implements Initializable {
    public TableView<Matiere> table_matiere;
    public TableColumn<Matiere, String> abreviation_column;
    public TableColumn<Matiere, String> designation_column;
    public TableColumn<Matiere, String> description_column;
    public TableColumn<Matiere, Matiere> action_column;
    public TextField designation_input;
    public TextField abreviation_input;
    public TextArea description_input;
    public Button btn_action;
    public Pane action_pane;
    public Label id;
    public Label matiere_label;
    public Pane matiere_pane;
    ObservableList<Matiere> listMatiere = FXCollections.observableArrayList();
    MatiereDao dao = new MatiereDao();
    Matiere matiere = null;
    PopUp popUp = new PopUp();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new FadeOutRight(action_pane).play();

        listMatiere.clear();
        listMatiere.setAll(dao.listAll());
        table_matiere.setItems(listMatiere);
        designation_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDesignation()));
        description_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDescription()));
        abreviation_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getAbreviation()));
        action_column.setCellValueFactory(new PropertyValueFactory<>(""));
        //
        Callback<TableColumn<Matiere,Matiere>, TableCell<Matiere,Matiere>> newColumn = (TableColumn< Matiere, Matiere> param) -> new TableCell<Matiere,Matiere>() {
            @Override
            public void updateItem(Matiere item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    final Button editBtn = new Button("Editer");
                    final Button dltBtn = new Button("Supprimer");
                    dltBtn.setStyle("-fx-background-color:#FF6666");
                    editBtn.setOnAction(event -> {
                        try {
                            matiere = getTableView().getItems().get(getIndex());
                            matiere_label.setText("Matière: edition");
                            btn_action.setText("Mettre à jour");
                            setTexts(matiere.getId(),
                                    matiere.getDesignation(),
                                    matiere.getAbreviation(),
                                    matiere.getDescription());
                            action_pane.setVisible(true);
                            new FadeInRight(action_pane).play();
                        } catch (NullPointerException e) {
                            popUp.error("Information", "Selectionner un champ avant de cliquer sur editer. Merci");
                        }
                    });
                    dltBtn.setOnAction(event -> {
                        action_pane.setVisible(false);
                        try {
                            dao.delete(getTableView().getItems().get(getIndex()).getId(), "matiere", "id");
                            refreshTable();
                        } catch (SQLException | ClassNotFoundException exception) {
                            popUp.error("information","veuillez selectionner une colonne avant de cliquer sur editer");
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
        action_column.setCellFactory(newColumn);
        // toggle btn
        btn_action.setOnAction(event -> {
            if (btn_action.getText().equals("Ajouter +")){
                try {
                    matiere = new Matiere(10,designation_input.getText(),abreviation_input.getText(),description_input.getText());
                    dao.insert(matiere);
                    new FadeOutRight(action_pane).play();
                    refreshTable();
                    clearInputs();
                } catch (Exception e) {
                    popUp.error("erreur","Erreur, essaye encore une fois");
                }
            } else if (btn_action.getText().equals("Mettre à jour")){
                try {
                    matiere = new Matiere(Integer.valueOf(id.getText()),designation_input.getText(),abreviation_input.getText(),description_input.getText());
                    dao.update(matiere);
                    new FadeOutRight(action_pane).play();
                    refreshTable();
                    clearInputs();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    public void setTexts(Integer id, String designation, String abreviation, String description){
        this.id.setText(id.toString());
        designation_input.setText(designation);
        abreviation_input.setText(abreviation);
        description_input.setText(description);
    }

    private void clearInputs() {
        description_input.setText("");
        designation_input.setText("");
        description_input.setText("");
    }

    private void refreshTable() {
        listMatiere.clear();
        listMatiere.addAll(dao.listAll());
    }
    public void zoomText() {
        matiere_pane.setStyle("-fx-font-size:16px");
    }

    public void afficher_pane_ajout() {
        new FadeInRight(action_pane).play();
        action_pane.setVisible(true);
        matiere_label.setText("Matière: Ajout");
    }

    public void close() {
        System.exit(0);
    }
}
