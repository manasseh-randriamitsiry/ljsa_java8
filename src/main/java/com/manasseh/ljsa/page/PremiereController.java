package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
import com.manasseh.ljsa.DAO.PremiereDAO;
import com.manasseh.ljsa.model.Premiere;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import com.manasseh.ljsa.utils.AutoCompleteComboBoxListener;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PremiereController implements Initializable{
    public TableView<Premiere> premiere_table;
    public TableColumn<Premiere, Premiere> action_column;
    public Button afficher_ajout_btn,
            btn_action;
    public TextField recherche_input,
            svt_input,
            phylo_input,
            trimestre_input,
            ang_input,
            eps_input,
            hg_input,
            frs_input,
            math_input,
            mlg_input,
            pc_input,
            eac_input,
            ses_input,
            tice_input;
    public ComboBox<Object> annee_input,
            n_mat_input;
    public TableColumn<Premiere, String> trimestre_column,
            annee_column,
            ses_column,
            ang_column,
            svt_column,
            tice_column,
            eps_column,
            frs_column,
            hg_column,
            math_column,
            mlg_column,
            phylo_column,
            n_mat_column,
            eac_column,
            phys_column,
            moyenne_column,
            total_column;
    public Label  id_label;
    public Pane action_pane;
    public Label premiere_label;

    ObservableList<Premiere> listPremiere = FXCollections.observableArrayList();
    PremiereDAO premiereDAO = new PremiereDAO();
    Premiere premiere = null;

    PopUp popUp = new PopUp();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        clearInputs();
        new FadeOutRight(action_pane).play();
        String[] items = {"2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033"
                ,"2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047",
                "2048","2049","2050","2051","2052","2053","2054","2055","2056","2057","2058","2059","2060","2061"};
        listEtudiant();
        new AutoCompleteComboBoxListener<>(n_mat_input);
        new AutoCompleteComboBoxListener<>(annee_input);
        annee_input.getItems().addAll(items);
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
        moyenne_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMoy()));
        trimestre_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTrimestre().toString()));
         annee_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnnee_scolaire().toString()));

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
                        try {
                            premiere = getTableView().getItems().get(getIndex());
                            premiere_label.setText("Premiere: edition");
                            btn_action.setText("Mettre à jour");
                            setInputText(premiere.getId(),
                                    premiere.getMalagasy(),
                                    premiere.getFrancais(),
                                    premiere.getAnglais(),
                                    premiere.getHistogeo(),
                                    premiere.getEac(),
                                    premiere.getSes(),
                                    premiere.getSpc(),
                                    premiere.getSvt(),
                                    premiere.getMats(),
                                    premiere.getEps(),
                                    premiere.getTice(),
                                    premiere.getPhylo(),
                                    premiere.getN_mat(),
                                    premiere.getTrimestre(),
                                    premiere.getAnnee_scolaire()
                                    );
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
        btn_action.setOnAction(event -> {
            if (btn_action.getText().equals("Ajouter +")){
                try {
                    premiere = new Premiere(10,
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
                            Integer.valueOf(annee_input.getValue().toString()));
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
                        Integer.valueOf(annee_input.getValue().toString()));
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

    public void listEtudiant(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT n_matricule FROM etudiants where classe='première' order by n_matricule ASC";
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ObservableList<Object> data = FXCollections.observableArrayList();
            while (resultSet.next()){
                data.add(resultSet.getString(1));
            }
            n_mat_input.setItems(data);
        } catch (SQLException ignored) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
    }

    private void setInputText(Integer id, Float malagasy, Float frs, Float anglais, Float histoGeo,
                              Float eac, Float ses, Float spc, Float svt,Float mathematique,
                              Float eps,Float tice,Float phylo, String n_mat,Integer trimestre,Integer annee_scolaire){
        mlg_input.setText(malagasy.toString());
        frs_input.setText(frs.toString());
        ang_input.setText(anglais.toString());
        hg_input.setText(histoGeo.toString());
        eac_input.setText(eac.toString());
        math_input.setText(mathematique.toString());
        ses_input.setText(ses.toString());
        pc_input.setText(spc.toString());
        svt_input.setText(svt.toString());
        tice_input.setText(tice.toString());
        eps_input.setText(eps.toString());
        phylo_input.setText(phylo.toString());
        n_mat_input.getSelectionModel().select(n_mat);
        trimestre_input.setText(trimestre.toString());
        annee_input.getSelectionModel().select(annee_scolaire);
        btn_action.setText("Mettre à jour");
        id_label.setText(id.toString());
    }
}
