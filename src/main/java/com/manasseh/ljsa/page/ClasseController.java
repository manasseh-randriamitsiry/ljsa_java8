package com.manasseh.ljsa.page;

import animatefx.animation.*;
import com.manasseh.ljsa.DAO.ClasseDAO;
import com.manasseh.ljsa.model.Classe;
import com.manasseh.ljsa.utils.AnneeLists;
import com.manasseh.ljsa.utils.PopUp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClasseController implements Initializable {
    public TableColumn<Classe,String> classe_column;
    public TableColumn<Classe, String> effectif_column;
    public TableColumn<Classe, Classe> action_column;
    public TableView<Classe> table_coefficient;
    public Pane action_pane;
    public TextField classe_input;
    public Button btn_action;
    public Label id;
    public Pane coefficient_pane;
    public Label mode_label;
    public Label coefficient_label;
    public TextField effectifs_input;
    public ComboBox<Object> level_input;
    public TableColumn<Classe,String> level_column;
    ObservableList<Classe> classeList = FXCollections.observableArrayList();
    Classe classe = null;
    ClasseDAO classeDAO = new ClasseDAO();
    PopUp popUp = new PopUp();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new FadeOutUp(action_pane).play();
        refreshTable();
        level_input.getItems().addAll(1,2,3);
        classe_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getClasse()));
        effectif_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getEffectif().toString()));
        level_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getLevel().toString()));
        action_column.setCellValueFactory(new PropertyValueFactory<>(""));

        // creation de bouton ajout et suppression
        Callback<TableColumn<Classe, Classe>, TableCell<Classe, Classe>> cellFactory=  (TableColumn<Classe, Classe> param) -> new TableCell<Classe, Classe>() {
            @Override
            public void updateItem(Classe item, boolean empty) {
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
                            classe = getTableView().getItems().get(getIndex());
                            mode_label.setText("Classe: edition");
                            coefficient_label.setText("Classe: edition");
                            btn_action.setText("Mettre à jour");
                            ClasseController.this.setText(
                                    classe.getId(),
                                    classe.getClasse(),
                                    classe.getEffectif(),
                                    classe.getLevel());
                            new FadeInDown(action_pane).play();
                        } catch (NullPointerException e) {
                            popUp.error("Information", "Selectionner un champ avant de cliquer sur editer. Merci");
                        }
                    });
                    dltBtn.setOnAction(event -> {
                        action_pane.setVisible(false);
                        try {
                            classeDAO.delete(getTableView().getItems().get(getIndex()).getId(), "classe", "id");
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
        action_column.setCellFactory(cellFactory);

        // btn_action
        btn_action.setOnAction(event -> {
            if (btn_action.getText().equals("Ajouter +")){
                try {
                    mode_label.setText("Classe: Ajout");
                    coefficient_label.setText("Classe: edition");
                    classe = new Classe(0,classe_input.getText(),Integer.valueOf(effectifs_input.getText()),Integer.valueOf(level_input.getValue().toString()));
                    classeDAO.insert(classe);
                    new FadeOutUp(action_pane).play();
                    refreshTable();
                    clearInputs();
                } catch (Exception e) {
                    e.printStackTrace();
                    popUp.error("erreur","Erreur, essaye encore une fois");
                }
            } else if (btn_action.getText().equals("Mettre à jour")){
                classe = new Classe(Integer.valueOf(id.getText()),
                        classe_input.getText(),
                        Integer.valueOf(effectifs_input.getText()),
                        Integer.valueOf(level_input.getValue().toString())
                );
                try {
                    classeDAO.update(classe);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                new FadeOutUp(action_pane).play();
                refreshTable();
                clearInputs();
            }
        });
    }

    private void setText(Integer id, String classe, Integer totalCoeff,Integer level) {
        this.id.setText(id.toString());
        this.classe_input.setText(classe);
        this.effectifs_input.setText(totalCoeff.toString());
        this.level_input.getSelectionModel().select(level);
    }

    private void clearInputs() {
        classe_input.setText("");
        effectifs_input.setText("");
    }

    private void refreshTable() {
        classeList.clear();
        classeList.setAll(classeDAO.listAll());
        table_coefficient.setItems(classeList);
    }

    public void afficherPaneAjout() {
        btn_action.setText("Ajouter +");
        mode_label.setText("Classe: Ajout");
        coefficient_label.setText("Classe: Ajout");
        new FadeInUp(action_pane).play();
        clearInputs();
    }

}
