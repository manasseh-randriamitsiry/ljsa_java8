package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.DAO.SecondeDAO;
import com.manasseh.ljsa.model.Etudiant;
import com.manasseh.ljsa.model.Seconde;
import com.manasseh.ljsa.utils.AnneeLists;
import com.manasseh.ljsa.utils.PopUp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import com.manasseh.ljsa.utils.AutoCompleteComboBoxListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class SecondeController implements Initializable{
    public TableView<Seconde> seconde_table;
    public TableColumn<Seconde, Seconde> action_column;
    public Button afficher_ajout_btn, btn_action;
    public TextField recherche_input, svt_input, trimestre_input, ang_input, eps_input, hg_input, frs_input, math_input, mlg_input, pc_input, eac_input, ses_input, tice_input;
    public ComboBox<String> annee_input;
    public ComboBox<Object> n_mat_input;
    public TableColumn<Seconde, String> trimestre_column, annee_column, ses_column, ang_column, svt_column, tice_column, eps_column, frs_column, moyenne_column, total_column, hg_column, math_column, mlg_column, n_mat_column, eac_column, phys_column;
    public Label  id_label,seconde_label;
    public Pane action_pane;
    public Circle detail_btn;
    ObservableList<Seconde> listseconde = FXCollections.observableArrayList();
    SecondeDAO secondeDAO = new SecondeDAO();
    Seconde seconde = null;
    PopUp popUp = new PopUp();
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    Etudiant etudiant;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        clearInputs();
        check();
        n_mat_input.setItems(etudiantDAO.listEtudiant());
        new FadeOutRight(action_pane).play();
        new AutoCompleteComboBoxListener<>(n_mat_input);
        new AutoCompleteComboBoxListener<>(annee_input);
        annee_input.getItems().addAll(AnneeLists.getYearList(100));
        n_mat_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getN_mat()));
        ang_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnglais().toString()));
        mlg_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMalagasy().toString()));
        hg_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHistogeo().toString()));
        eac_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEac().toString()));
        eps_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEps().toString()));
        ses_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSes().toString()));
        math_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMats().toString()));
        phys_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSpc().toString()));
        svt_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSvt().toString()));
        frs_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFrancais().toString()));
        tice_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTice().toString()));
        total_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTotal().toString()));
        trimestre_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTrimestre().toString()));
        annee_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnnee_scolaire().toString()));
        moyenne_column.setCellValueFactory(param -> {
            try {
                return new SimpleStringProperty(param.getValue().getMoyenne());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        Callback<TableColumn<Seconde,Seconde>, TableCell<Seconde,Seconde>> newColumn = (TableColumn<Seconde,Seconde> param) -> new TableCell<Seconde,Seconde>() {
            @Override
            public void updateItem(Seconde item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    final Button editBtn = new Button("Editer");
                    final Button dltBtn = new Button("Supprimer");
                    dltBtn.setStyle("-fx-background-color:#FF6666");
                    editBtn.setOnAction(event -> {
                        check();
                        seconde = getTableView().getItems().get(getIndex());
                        seconde_label.setText("Seconde: edition");
                        btn_action.setText("Mettre à jour");

                        id_label.setText(seconde.getId().toString());
                        mlg_input.setText(seconde.getMalagasy().toString());
                        frs_input.setText(seconde.getFrancais().toString());
                        ang_input.setText(seconde.getAnglais().toString());
                        hg_input.setText(seconde.getHistogeo().toString());
                        eac_input.setText(seconde.getEac().toString());
                        math_input.setText(seconde.getMats().toString());
                        ses_input.setText(seconde.getSes().toString());
                        pc_input.setText(seconde.getSpc().toString());
                        svt_input.setText(seconde.getSvt().toString());
                        tice_input.setText(seconde.getTice().toString());
                        eps_input.setText(seconde.getEps().toString());
                        n_mat_input.getSelectionModel().select(seconde.getN_mat());
                        trimestre_input.setText(seconde.getTrimestre().toString());
                        annee_input.getSelectionModel().select(seconde.getAnnee_scolaire());

                        action_pane.setVisible(true);
                        new FadeInRight(action_pane).play();
                    });
                    dltBtn.setOnAction(event -> {
                        action_pane.setVisible(false);
                        try {
                            secondeDAO.delete(getTableView().getItems().get(getIndex()).getId(), "seconde", "id");
                            refresh();
                        } catch (SQLException | ClassNotFoundException exception) {
                            popUp.error("information", "veuillez selectionner une colonne avant de cliquer sur editer");
                        }
                    });
                    HBox hb = new HBox();
                    hb.setSpacing(5);
                    hb.setStyle("-fx-alignment:center");
                    hb.getChildren().addAll(editBtn, dltBtn);
                    setGraphic(hb);
                    setText(null);
                }
            }
        };
        action_column.setCellFactory(newColumn);

        // detail pop up
        detail_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                FXMLLoader loader = new FXMLLoader ();
                loader.setLocation(getClass().getResource("detailEtudiant.fxml"));
                try {
                    loader.load();
                } catch (IOException |NullPointerException ex ) {
                    popUp.error("Information", "Selectionner un numero matricule avant de cliquer. Merci");
                }
                DetailController detail = loader.getController();
                etudiant = etudiantDAO.getByNmat((String) n_mat_input.getValue());
                detail.setInputText(etudiant);
                Parent parent = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.initOwner(((Node)event.getSource()).getScene().getWindow());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                refresh();
            } catch (NullPointerException e) {
                popUp.error("Information", "Selectionner un numero matricule avant de cliquer. Merci");
            }
        });

        btn_action.setOnAction(event -> {
            if (btn_action.getText().equals("Ajouter +")){
                try {
                    seconde = new Seconde(10,
                            Float.valueOf(mlg_input.getText()),
                            Float.valueOf(frs_input.getText()),
                            Float.valueOf(ang_input.getText()),
                            Float.valueOf(hg_input.getText()),
                            Float.valueOf(eac_input.getText()),
                            Float.valueOf(ses_input.getText()),
                            Float.valueOf(pc_input.getText()),
                            Float.valueOf(svt_input.getText()),
                            Float.valueOf(math_input.getText()),
                            Float.valueOf(eps_input.getText()),
                            Float.valueOf(tice_input.getText()),
                            n_mat_input.getValue().toString(),
                            Integer.valueOf(trimestre_input.getText()),
                            Integer.valueOf(annee_input.getValue()));
                    secondeDAO.insert(seconde);
                    new FadeOutRight(action_pane).play();
                    refresh();
                    clearInputs();
                } catch (NumberFormatException | SQLException e) {
                    popUp.error("erreur","Erreur, verifier que les notes sont des nombres puis essaye encore une fois");
                }
            }
            else if (btn_action.getText().equals("Mettre à jour")){
                seconde = new Seconde(Integer.valueOf(id_label.getText()),
                        Float.valueOf(mlg_input.getText()),
                        Float.valueOf(frs_input.getText()),
                        Float.valueOf(ang_input.getText()),
                        Float.valueOf(hg_input.getText()),
                        Float.valueOf(eac_input.getText()),
                        Float.valueOf(ses_input.getText()),
                        Float.valueOf(pc_input.getText()),
                        Float.valueOf(svt_input.getText()),
                        Float.valueOf(math_input.getText()),
                        Float.valueOf(eps_input.getText()),
                        Float.valueOf(tice_input.getText()),
                        n_mat_input.getValue().toString(),
                        Integer.valueOf(trimestre_input.getText()),
                        Integer.valueOf(annee_input.getValue()));
                try {
                    secondeDAO.update(seconde);
                    refresh();
                    clearInputs();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                new FadeOutRight(action_pane).play();
                action_pane.setVisible(true);
            }
        });
    }

    public void afficherPaneAjout(){
        btn_action.setText("Ajouter +");
        seconde_label.setText("Seconde: Ajout");
        new FadeInRight(action_pane).play();
        clearInputs();
    }
    private void clearInputs() {
        mlg_input.setText("");
        frs_input.setText("");
        ang_input.setText("");
        hg_input.setText("");
        eac_input.setText("");
        math_input.setText("");
        pc_input.setText("");
        svt_input.setText("");
        tice_input.setText("");
        eps_input.setText("");
        ses_input.setText("");
    }
    private void refresh(){
        listseconde.clear();
        listseconde.setAll(secondeDAO.listAll());
        activerRecherche();
        clearInputs();
    }
    public void check(){
        if (n_mat_input.getValue() == null){
            detail_btn.setVisible(false);
        } else if (n_mat_input.getValue() !=null){
            detail_btn.setVisible(true);
        }
    }
    private void activerRecherche() {
        FilteredList<Seconde> filteredList = new FilteredList<>(listseconde, a->true);
        recherche_input.textProperty().addListener((Observable,oldValue,newValue) -> filteredList.setPredicate(seconde -> newValue.isEmpty()
                || seconde.getAnglais().toString().contains(newValue)
                || seconde.getEac().toString().contains(newValue)
                || seconde.getMalagasy().toString().contains(newValue)
                || seconde.getN_mat().contains(newValue.toUpperCase())
                || seconde.getTice().toString().contains(newValue)
                || seconde.getMats().toString().contains(newValue)
                || seconde.getSpc().toString().contains(newValue)
                || seconde.getSvt().toString().contains(newValue)
                || seconde.getEps().toString().contains(newValue)
                || seconde.getSes().toString().contains(newValue)
                || seconde.getAnnee_scolaire().toString().contains(newValue)));
        SortedList<Seconde> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(seconde_table.comparatorProperty());
        seconde_table.setItems(sortedList);
    }
}
