package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.DAO.PremiereDAO;
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
    ObservableList<Premiere> listPremiere = FXCollections.observableArrayList();
    PremiereDAO premiereDAO = new PremiereDAO();
    Premiere premiere = null;
    PopUp popUp = new PopUp();
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    Etudiant etudiant;
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
            else if (btn_action.getText().equals("Mettre à jour")){
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
                try {
                    premiereDAO.update(premiere);
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
}
