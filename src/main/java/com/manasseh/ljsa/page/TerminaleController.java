package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.model.Etudiant;
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
import com.manasseh.ljsa.DAO.TerminaleDAO;
import com.manasseh.ljsa.model.Terminale;
import com.manasseh.ljsa.utils.AutoCompleteComboBoxListener;
import com.manasseh.ljsa.utils.PopUp;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class TerminaleController implements Initializable{
    public TableView<Terminale> table_terminale;
    public TableColumn<Terminale, Terminale> action_column;
    public Button afficher_ajout_btn, btn_action;
    public TextField recherche_input, svt_input, trimestre_input, ang_input, eps_input, hg_input, frs_input, math_input, mlg_input, pc_input, phylo_input, ses_input;
    public ComboBox<String> annee_input;
    public ComboBox<Object> n_mat_input;
    public TableColumn<Terminale, String> trimestre_column, annee_column, ang_column, svt_column, ses_column, eps_column, frs_column, hg_column, math_column, mlg_column,n_mat_column, phylo_column, total_column, moyenne_column, phys_column;
    public Label  id_label, terminale_label;
    public Pane action_pane;
    public Circle detail_btn;
    ObservableList<Terminale> listTerminale = FXCollections.observableArrayList();
    TerminaleDAO terminaleDAO = new TerminaleDAO();
    Terminale terminale = null;
    PopUp popUp = new PopUp();
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    Etudiant etudiant;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        clearInputs();
        new FadeOutRight(action_pane).play();
        n_mat_input.setItems(etudiantDAO.listEtudiant());
        new AutoCompleteComboBoxListener<>(n_mat_input);
        new AutoCompleteComboBoxListener<>(annee_input);
        annee_input.getItems().addAll(getYearList(100));
        n_mat_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getN_mat()));
        ang_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnglais().toString()));
        mlg_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMalagasy().toString()));
        hg_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHistoGeo().toString()));
        phylo_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPhylosphie().toString()));
        eps_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEps().toString()));
        math_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMathematique().toString()));
        phys_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSpc().toString()));
        svt_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSvt().toString()));
        frs_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFrs().toString()));
        ses_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSes().toString()));
        total_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTotal().toString()));
        moyenne_column.setCellValueFactory(param -> {
            try {
                return new SimpleStringProperty(param.getValue().getMoyenne());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        trimestre_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTrimestre().toString()));
        annee_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnnee_scolaire().toString()));

        Callback<TableColumn<Terminale,Terminale>, TableCell<Terminale,Terminale>> newColumn = (TableColumn<Terminale,Terminale> param) -> new TableCell<Terminale,Terminale>() {
            @Override
            public void updateItem(Terminale item, boolean empty) {
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
                            terminale = getTableView().getItems().get(getIndex());
                            terminale_label.setText("Terminale: edition");
                            btn_action.setText("Mettre à jour");

                            id_label.setText(terminale.getId().toString());
                            mlg_input.setText(terminale.getMalagasy().toString());
                            frs_input.setText(terminale.getFrs().toString());
                            ang_input.setText(terminale.getAnglais().toString());
                            hg_input.setText(terminale.getHistoGeo().toString());
                            phylo_input.setText(terminale.getPhylosphie().toString());
                            math_input.setText( terminale.getMathematique().toString());
                            pc_input.setText(terminale.getSpc().toString());
                            svt_input.setText(terminale.getSvt().toString());
                            ses_input.setText(terminale.getSes().toString());
                            eps_input.setText(terminale.getEps().toString());
                            n_mat_input.getSelectionModel().select(terminale.getN_mat());
                            trimestre_input.setText(terminale.getTrimestre().toString());
                            annee_input.getSelectionModel().select(terminale.getAnnee_scolaire());
                            btn_action.setText("Mettre à jour");

                            action_pane.setVisible(true);
                            new FadeInRight(action_pane).play();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            popUp.error("Information", "Selectionner un champ avant de cliquer sur editer. Merci");
                        }
                    });
                    dltBtn.setOnAction(event -> {
                        action_pane.setVisible(false);
                        try {
                            terminaleDAO.delete(getTableView().getItems().get(getIndex()).getId(), "terminale", "id");
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
                    terminale = new Terminale(10,
                            Float.valueOf(mlg_input.getText()),
                            Float.valueOf(frs_input.getText()),
                            Float.valueOf(ang_input.getText()),
                            Float.valueOf(hg_input.getText()),
                            Float.valueOf(phylo_input.getText()),
                            Float.valueOf(eps_input.getText()),
                            Float.valueOf(math_input.getText()),
                            Float.valueOf(pc_input.getText()),
                            Float.valueOf(svt_input.getText()),
                            Float.valueOf(ses_input.getText()),
                            n_mat_input.getValue().toString(),
                            Integer.valueOf(trimestre_input.getText()),
                            Integer.valueOf(annee_input.getValue()));
                    terminaleDAO.insert(terminale);
                    refresh();
                    clearInputs();
                } catch (NumberFormatException | SQLException e) {
                    popUp.error("erreur","Erreur, verifier que les notes sont des nombres puis essaye encore une fois");
                }
            }
            else if (btn_action.getText().equals("Mettre à jour")){
                terminale = new Terminale(Integer.valueOf(id_label.getText()),
                        Float.valueOf(mlg_input.getText()),
                        Float.valueOf(frs_input.getText()),
                        Float.valueOf(ang_input.getText()),
                        Float.valueOf(hg_input.getText()),
                        Float.valueOf(phylo_input.getText()),
                        Float.valueOf(eps_input.getText()),
                        Float.valueOf(math_input.getText()),
                        Float.valueOf(pc_input.getText()),
                        Float.valueOf(svt_input.getText()),
                        Float.valueOf(ses_input.getText()),
                        n_mat_input.getValue().toString(),
                        Integer.valueOf(trimestre_input.getText()),
                        Integer.valueOf(annee_input.getValue()));
                try {
                    terminaleDAO.update(terminale);
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
        action_pane.setVisible(true);
        new FadeInRight(action_pane).play();
    }
    private void clearInputs() {
        mlg_input.setText("");
        frs_input.setText("");
        ang_input.setText("");
        hg_input.setText("");
        phylo_input.setText("");
        math_input.setText("");
        pc_input.setText("");
        svt_input.setText("");
        ses_input.setText("");
        eps_input.setText("");
        trimestre_input.setText("");
    }
    private void refresh(){
        listTerminale.clear();
        listTerminale.setAll(terminaleDAO.listAll());
        activerRecherche();
        clearInputs();
    }
    public void check(){
    }
    private void activerRecherche() {
        FilteredList<Terminale> filteredList = new FilteredList<>(listTerminale, a->true);
        recherche_input.textProperty().addListener((Observable,oldValue,newValue) -> filteredList.setPredicate(terminale -> newValue.isEmpty()
                || terminale.getAnglais().toString().contains(newValue)
                || terminale.getPhylosphie().toString().contains(newValue)
                || terminale.getMalagasy().toString().contains(newValue)
                || terminale.getN_mat().contains(newValue.toUpperCase())
                || terminale.getHistoGeo().toString().contains(newValue)
                || terminale.getMathematique().toString().contains(newValue)
                || terminale.getSpc().toString().contains(newValue)
                || terminale.getSvt().toString().contains(newValue)
                || terminale.getFrs().toString().contains(newValue)
                || terminale.getEps().toString().contains(newValue)
                || terminale.getSes().toString().contains(newValue)
                || terminale.getAnnee_scolaire().toString().contains(newValue)));
        SortedList<Terminale> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table_terminale.comparatorProperty());
        table_terminale.setItems(sortedList);
    }
    public static ArrayList<String> getYearList(int years) {
        ArrayList<String> yearList = new ArrayList<>(years);
        int startYear = Calendar.getInstance().get(Calendar.YEAR) - 30;
        if (startYear<1990){
            startYear = 1990;
        }
        for (int i = 0; i < years; i++)
            yearList.add(Integer.toString(startYear + i));
        return yearList;
    }
}
