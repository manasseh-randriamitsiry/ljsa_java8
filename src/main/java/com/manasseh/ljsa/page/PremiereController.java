package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.DAO.PremiereDAO;
import com.manasseh.ljsa.model.Coefficient_premiere;
import com.manasseh.ljsa.model.Etudiant;
import com.manasseh.ljsa.model.Premiere;
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

public class PremiereController implements Initializable{
    public TableView<Premiere> premiere_table;
    public TableColumn<Premiere, Premiere> action_column;
    public Button afficher_ajout_btn, btn_action;
    public TextField recherche_input, svt_input, phylo_input, trimestre_input, ang_input, eps_input, hg_input, frs_input, math_input, mlg_input, pc_input, eac_input, ses_input, tice_input;
    public ComboBox<String> annee_input;
    public ComboBox<Object> n_mat_input;
    public TableColumn<Premiere,String> trimestre_column, annee_column, ses_column, ang_column, svt_column, tice_column, eps_column, frs_column, hg_column, math_column, mlg_column, phylo_column, n_mat_column, eac_column, phys_column, moyenne_column, total_column;
    public Label  id_label;
    public Pane action_pane;
    public Circle detail_btn;
    public Label premiere_label;
    public TableView<Coefficient_premiere> premiere_table_coeff;
    public TableColumn<Coefficient_premiere, String> mlg_column_coeff,frs_column_coeff,ang_column_coeff,hg_column_coeff,math_column_coeff,phys_column_coeff,svt_column_coeff,eac_column_coeff,tice_column_coeff,eps_column_coeff,ses_column_coeff,phylo_column_coeff;
    public Button trim_btn;
    public Button nmat_btn;
    public Button annee_btn;
    public Button define_coeff;
    ObservableList<Premiere> listPremiere = FXCollections.observableArrayList();
    ObservableList<Coefficient_premiere> listCoefficient = FXCollections.observableArrayList();
    PremiereDAO premiereDAO = new PremiereDAO();
    Premiere premiere = null;
    PopUp popUp = new PopUp();
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    Etudiant etudiant;
    Coefficient_premiere coefficientPremiere = null;
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
        ang_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnglais().toString()));
        mlg_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMalagasy().toString()));
        frs_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFrancais().toString()));
        hg_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHistogeo().toString()));
        math_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMats().toString()));
        phys_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSpc().toString()));
        svt_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSvt().toString()));
        eac_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEac().toString()));
        tice_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTice().toString()));
        eps_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEps().toString()));
        ses_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSes().toString()));
        phylo_column_coeff.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPhylo().toString()));

        // notes
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
        phylo_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPhylo().toString()));
        total_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTotal().toString()));
        trimestre_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTrimestre().toString()));
        annee_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnnee_scolaire().toString()));
        moyenne_column.setCellValueFactory(param -> {
            try {
                return new SimpleStringProperty(param.getValue().getMoy());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        Callback<TableColumn<Premiere,Premiere>, TableCell<Premiere,Premiere>> newColumn = (TableColumn<Premiere,Premiere> param) -> new TableCell<Premiere,Premiere>() {
            @Override
            public void updateItem(Premiere item, boolean empty) {
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
                        try {
                            premiere = getTableView().getItems().get(getIndex());
                            premiere_label.setText("Premiere: edition");
                            btn_action.setText("Mettre à jour");

                            id_label.setText(premiere.getId().toString());
                            mlg_input.setText(premiere.getMalagasy().toString());
                            frs_input.setText(premiere.getFrancais().toString());
                            ang_input.setText(premiere.getAnglais().toString());
                            hg_input.setText(premiere.getHistogeo().toString());
                            eac_input.setText(premiere.getEac().toString());
                            math_input.setText(premiere.getMats().toString());
                            ses_input.setText(premiere.getSes().toString());
                            pc_input.setText(premiere.getSpc().toString());
                            svt_input.setText(premiere.getSvt().toString());
                            tice_input.setText(premiere.getTice().toString());
                            eps_input.setText(premiere.getEps().toString());
                            phylo_input.setText(premiere.getPhylo().toString());
                            n_mat_input.getSelectionModel().select(premiere.getN_mat());
                            trimestre_input.setText(premiere.getTrimestre().toString());
                            annee_input.getSelectionModel().select(premiere.getAnnee_scolaire());

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
                            premiereDAO.delete(getTableView().getItems().get(getIndex()).getId(), "premiere", "id");
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
                        && verifyNote(Float.parseFloat(eac_input.getText()))
                        && verifyNote(Float.parseFloat(ses_input.getText()))
                        && verifyNote(Float.parseFloat(pc_input.getText()))
                        && verifyNote(Float.parseFloat(svt_input.getText()))
                        && verifyNote(Float.parseFloat(math_input.getText()))
                        && verifyNote(Float.parseFloat(eps_input.getText()))
                        && verifyNote(Float.parseFloat(tice_input.getText()))
                        && verifyNote(Float.parseFloat(phylo_input.getText()))
                ){
                    check();
                    try {
                        premiere = new Premiere(0,
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
                                Float.valueOf(phylo_input.getText()),
                                n_mat_input.getValue().toString(),
                                Integer.valueOf(trimestre_input.getText()),
                                Integer.valueOf(annee_input.getValue()));
                        premiereDAO.insert(premiere);
                        refresh();
                        clearInputs();
                        new FadeOutRight(action_pane).play();
                    } catch (NumberFormatException | SQLException e) {
                        popUp.error("erreur","Erreur, verifier que les notes sont des nombres puis essaye encore une fois");
                    }
                }
            }
            else if (btn_action.getText().equals("Mettre à jour")){
                if (verifyNote(Float.parseFloat(mlg_input.getText()))
                        && verifyNote(Float.parseFloat(frs_input.getText()))
                        && verifyNote(Float.parseFloat(ang_input.getText()))
                        && verifyNote(Float.parseFloat(hg_input.getText()))
                        && verifyNote(Float.parseFloat(eac_input.getText()))
                        && verifyNote(Float.parseFloat(ses_input.getText()))
                        && verifyNote(Float.parseFloat(pc_input.getText()))
                        && verifyNote(Float.parseFloat(svt_input.getText()))
                        && verifyNote(Float.parseFloat(math_input.getText()))
                        && verifyNote(Float.parseFloat(eps_input.getText()))
                        && verifyNote(Float.parseFloat(tice_input.getText()))
                        && verifyNote(Float.parseFloat(phylo_input.getText()))
                ){
                    try {
                        premiere = new Premiere(Integer.valueOf(id_label.getText()),
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
                                Float.valueOf(phylo_input.getText()),
                                n_mat_input.getValue().toString(),
                                Integer.valueOf(trimestre_input.getText()),
                                Integer.valueOf(annee_input.getValue()));
                        premiereDAO.update(premiere);
                        refresh();
                        clearInputs();
                    } catch (SQLException e) {
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
                    && verifyCoeff(Integer.valueOf(eac_input.getText()))
                    && verifyCoeff(Integer.valueOf(ses_input.getText()))
                    && verifyCoeff(Integer.valueOf(pc_input.getText()))
                    && verifyCoeff(Integer.valueOf(svt_input.getText()))
                    && verifyCoeff(Integer.valueOf(math_input.getText()))
                    && verifyCoeff(Integer.valueOf(eps_input.getText()))
                    && verifyCoeff(Integer.valueOf(tice_input.getText()))
                    && verifyCoeff(Integer.valueOf(phylo_input.getText()))
                ){
                    coefficientPremiere = new Coefficient_premiere(
                            Integer.valueOf(mlg_input.getText()),
                            Integer.valueOf(frs_input.getText()),
                            Integer.valueOf(ang_input.getText()),
                            Integer.valueOf(hg_input.getText()),
                            Integer.valueOf(eac_input.getText()),
                            Integer.valueOf(ses_input.getText()),
                            Integer.valueOf(pc_input.getText()),
                            Integer.valueOf(svt_input.getText()),
                            Integer.valueOf(math_input.getText()),
                            Integer.valueOf(eps_input.getText()),
                            Integer.valueOf(tice_input.getText()),
                            Integer.valueOf(phylo_input.getText())
                    );
                    try {
                        premiereDAO.updateCoeff(coefficientPremiere);
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

    public void afficherPaneAjout(){
        btn_action.setText("Ajouter +");
        premiere_label.setText("Premiere: Ajout");
        action_pane.setVisible(true);
        new FadeInRight(action_pane).play();
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
        phylo_input.setText("");
    }
    private void refresh(){
        listPremiere.clear();
        listPremiere.setAll(premiereDAO.listAll());
        listCoefficient.clear();
        listCoefficient.setAll(premiereDAO.listCoeff());
        premiere_table_coeff.setItems(listCoefficient);
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
        FilteredList<Premiere> filteredList = new FilteredList<>(listPremiere, a->true);
        recherche_input.textProperty().addListener((Observable,oldValue,newValue) -> filteredList.setPredicate(premiere -> newValue.isEmpty()
                || premiere.getAnglais().toString().contains(newValue)
                || premiere.getEac().toString().contains(newValue)
                || premiere.getMalagasy().toString().contains(newValue)
                || premiere.getN_mat().contains(newValue.toUpperCase())
                || premiere.getTice().toString().contains(newValue)
                || premiere.getMats().toString().contains(newValue)
                || premiere.getSpc().toString().contains(newValue)
                || premiere.getSvt().toString().contains(newValue)
                || premiere.getEps().toString().contains(newValue)
                || premiere.getPhylo().toString().contains(newValue)
                || premiere.getSes().toString().contains(newValue)
                || premiere.getAnnee_scolaire().toString().contains(newValue)));
        SortedList<Premiere> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(premiere_table.comparatorProperty());
        premiere_table.setItems(sortedList);
    }

    public void defineCoeff() {
        n_mat_input.setVisible(false);
        annee_input.setVisible(false);
        trimestre_input.setVisible(false);
        trim_btn.setVisible(false);
        nmat_btn.setVisible(false);
        annee_btn.setVisible(false);
        detail_btn.setVisible(false);

        coefficientPremiere = premiere_table_coeff.getItems().get(0);
        mlg_input.setText(coefficientPremiere.getMalagasy().toString());
        frs_input.setText(coefficientPremiere.getFrancais().toString());
        ang_input.setText(coefficientPremiere.getAnglais().toString());
        hg_input.setText(coefficientPremiere.getHistogeo().toString());
        eac_input.setText(coefficientPremiere.getEac().toString());
        math_input.setText(coefficientPremiere.getMats().toString());
        ses_input.setText(coefficientPremiere.getSes().toString());
        pc_input.setText(coefficientPremiere.getSpc().toString());
        svt_input.setText(coefficientPremiere.getSvt().toString());
        tice_input.setText(coefficientPremiere.getTice().toString());
        eps_input.setText(coefficientPremiere.getEps().toString());
        phylo_input.setText(coefficientPremiere.getPhylo().toString());
//
        btn_action.setText("Coef: edit");
        premiere_label.setText("Modification des coefficients");
//
        action_pane.setVisible(true);
        new FadeInRight(action_pane).play();
    }
}
