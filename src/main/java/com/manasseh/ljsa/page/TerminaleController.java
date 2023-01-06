package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.model.*;
import com.manasseh.ljsa.utils.AnneeLists;
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
import com.manasseh.ljsa.utils.AutoCompleteComboBoxListener;
import com.manasseh.ljsa.utils.PopUp;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public Button define_coeff;
    public TableColumn<Coefficient_terminale,String> eps_column_coeff, ses_column_coeff, phylo_column_coeff,svt_column_coeff,phys_column_coeff,math_column_coeff,hg_column_coeff,ang_column_coeff,frs_column_coeff,mlg_column_coeff;
    public TableView<Coefficient_terminale> table_terminale_coeff;
    public Button nmat_btn;
    public Button annee_btn;
    public Button trim_btn;
    ObservableList<Terminale> listTerminale = FXCollections.observableArrayList();
    ObservableList<Coefficient_terminale> list_coefficient = FXCollections.observableArrayList();
    TerminaleDAO terminaleDAO = new TerminaleDAO();
    Terminale terminale = null;
    PopUp popUp = new PopUp();
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    Etudiant etudiant;
    Coefficient_terminale  coefficientTerminale = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        clearInputs();
        check();
        new FadeOutRight(action_pane).play();
        n_mat_input.setItems(etudiantDAO.listEtudiant());
        new AutoCompleteComboBoxListener<>(n_mat_input);
        new AutoCompleteComboBoxListener<>(annee_input);
        annee_input.getItems().addAll(AnneeLists.getYearList(100));

        // coefficient
        mlg_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMalagasy().toString()));
        frs_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFrs().toString()));
        ang_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnglais().toString()));
        hg_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHistoGeo().toString()));
        math_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMathematique().toString()));
        phys_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSpc().toString()));
        svt_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSvt().toString()));
        phylo_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPhylosphie().toString()));
        ses_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSes().toString()));
        eps_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEps().toString()));

        // notes
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
                            check();
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
                if (verifyNote(Float.parseFloat(mlg_input.getText()))
                        && verifyNote(Float.parseFloat(frs_input.getText()))
                        && verifyNote(Float.parseFloat(ang_input.getText()))
                        && verifyNote(Float.parseFloat(hg_input.getText()))
                        && verifyNote(Float.parseFloat(ses_input.getText()))
                        && verifyNote(Float.parseFloat(pc_input.getText()))
                        && verifyNote(Float.parseFloat(svt_input.getText()))
                        && verifyNote(Float.parseFloat(math_input.getText()))
                        && verifyNote(Float.parseFloat(eps_input.getText()))
                        && verifyNote(Float.parseFloat(phylo_input.getText()))
                ){
                    check();
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
                        e.printStackTrace();
                    }
                }
            }
            else if (btn_action.getText().equals("Mettre à jour")){
                if (verifyNote(Float.parseFloat(mlg_input.getText()))
                        && verifyNote(Float.parseFloat(frs_input.getText()))
                        && verifyNote(Float.parseFloat(ang_input.getText()))
                        && verifyNote(Float.parseFloat(hg_input.getText()))
                        && verifyNote(Float.parseFloat(ses_input.getText()))
                        && verifyNote(Float.parseFloat(pc_input.getText()))
                        && verifyNote(Float.parseFloat(svt_input.getText()))
                        && verifyNote(Float.parseFloat(math_input.getText()))
                        && verifyNote(Float.parseFloat(eps_input.getText()))
                        && verifyNote(Float.parseFloat(phylo_input.getText()))
                ){
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
                    } catch (SQLException | NumberFormatException e ) {
                        popUp.error("Erreur", "verifier les valeurs");
                        throw new RuntimeException(e);
                    }
                    new FadeOutRight(action_pane).play();
                    action_pane.setVisible(true);
                }
            }
            else if (btn_action.getText().equals("Coef: edit")) {
                if (verifyCoeff(Integer.valueOf(mlg_input.getText()))
                        && verifyCoeff(Integer.valueOf(frs_input.getText()))
                        && verifyCoeff(Integer.valueOf(ang_input.getText()))
                        && verifyCoeff(Integer.valueOf(hg_input.getText()))
                        && verifyCoeff(Integer.valueOf(ses_input.getText()))
                        && verifyCoeff(Integer.valueOf(pc_input.getText()))
                        && verifyCoeff(Integer.valueOf(svt_input.getText()))
                        && verifyCoeff(Integer.valueOf(math_input.getText()))
                        && verifyCoeff(Integer.valueOf(eps_input.getText()))
                        && verifyCoeff(Integer.valueOf(phylo_input.getText()))
                ){
                    coefficientTerminale = new Coefficient_terminale(
                            Integer.valueOf(mlg_input.getText()),
                            Integer.valueOf(frs_input.getText()),
                            Integer.valueOf(ang_input.getText()),
                            Integer.valueOf(hg_input.getText()),
                            Integer.valueOf(phylo_input.getText()),
                            Integer.valueOf(eps_input.getText()),
                            Integer.valueOf(math_input.getText()),
                            Integer.valueOf(pc_input.getText()),
                            Integer.valueOf(svt_input.getText()),
                            Integer.valueOf(ses_input.getText())
                    );

                    try {
                        terminaleDAO.updateCoeff(coefficientTerminale);
                        refresh();
                        clearInputs();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    new FadeOutRight(action_pane).play();
                    action_pane.setVisible(true);
                    switchShow();
                }
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
        list_coefficient.clear();
        list_coefficient.setAll(terminaleDAO.listCoeff());
        table_terminale_coeff.setItems(list_coefficient);
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

    public void switchShow(){
        n_mat_input.setVisible(true);
        annee_input.setVisible(true);
        trimestre_input.setVisible(true);
        trim_btn.setVisible(true);
        nmat_btn.setVisible(true);
        annee_btn.setVisible(true);
        detail_btn.setVisible(true);
    }
    public boolean verifyNote(float note){
        if (Math.round(note)<0){
            popUp.error("Note negatif", "Les notes doit etre superieur a 0");
            return false;
        } else if (note>20){
            popUp.error("Note trop grand", "Les notes doit etre entre 0 et 20");
            return false;
        }
        return true;
    }
    public boolean verifyCoeff(int coeff){
        if (coeff<0){
            popUp.error("Coefficient negatif", "Les Coefficient doit etre superieur a 0");
            return false;
        } else if (coeff>20){
            popUp.error("Coefficient trop grand", "Les Coefficient doit etre entre 0 et 20");
            return false;
        }
        return true;
    }
    public void defineCoeff() {
        n_mat_input.setVisible(false);
        annee_input.setVisible(false);
        trimestre_input.setVisible(false);
        trim_btn.setVisible(false);
        nmat_btn.setVisible(false);
        annee_btn.setVisible(false);
        detail_btn.setVisible(false);

        coefficientTerminale = table_terminale_coeff.getItems().get(0);
        mlg_input.setText(coefficientTerminale.getMalagasy().toString());
        frs_input.setText(coefficientTerminale.getFrs().toString());
        ang_input.setText(coefficientTerminale.getAnglais().toString());
        hg_input.setText(coefficientTerminale.getHistoGeo().toString());
//        eac_input.setText(coefficientTerminale.getEac().toString());
        math_input.setText(coefficientTerminale.getMathematique().toString());
        ses_input.setText(coefficientTerminale.getSes().toString());
        pc_input.setText(coefficientTerminale.getSpc().toString());
        svt_input.setText(coefficientTerminale.getSvt().toString());
        phylo_input.setText(coefficientTerminale.getPhylosphie().toString());
        eps_input.setText(coefficientTerminale.getEps().toString());
//
        btn_action.setText("Coef: edit");
        terminale_label.setText("Modification des coefficients");
//
        action_pane.setVisible(true);
        new FadeInRight(action_pane).play();
    }
}
