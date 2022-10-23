package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
import com.manasseh.ljsa.DAO.SecondeDAO;
import com.manasseh.ljsa.model.Seconde;
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
import manasseh.utils.AutoCompleteComboBoxListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SecondeController implements Initializable{
    public TableView<Seconde> seconde_table;
    public TableColumn<Seconde, Seconde> action_column;
    public Button afficher_ajout_btn,
            btn_action;
    public TextField recherche_input,
            svt_input,
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
    public TableColumn<Seconde, String> trimestre_column,
            annee_column,
            ses_column,
            ang_column,
            svt_column,
            tice_column,
            eps_column,
            frs_column,
            moyenne_column,
            total_column,
            hg_column,
            math_column,
            mlg_column,
            n_mat_column,
            eac_column,
            phys_column;
    public Label  id_label;
    public Pane action_pane;
    public Label seconde_label;

    ObservableList<Seconde> listseconde = FXCollections.observableArrayList();
    SecondeDAO secondeDAO = new SecondeDAO();
    Seconde seconde = null;

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
         total_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTotal().toString()));
         moyenne_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMoyenne()));
        trimestre_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTrimestre().toString()));
         annee_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnnee_scolaire().toString()));

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
                        try {
                            seconde = getTableView().getItems().get(getIndex());
                            seconde_label.setText("Seconde: edition");
                            btn_action.setText("Mettre à jour");
                            setInputText(seconde.getId(),
                                    seconde.getMalagasy(),
                                    seconde.getFrancais(),
                                    seconde.getAnglais(),
                                    seconde.getHistogeo(),
                                    seconde.getEac(),
                                    seconde.getSes(),
                                    seconde.getSpc(),
                                    seconde.getSvt(),
                                    seconde.getMats(),
                                    seconde.getEps(),
                                    seconde.getTice(),
                                    seconde.getN_mat(),
                                    seconde.getTrimestre(),
                                    seconde.getAnnee_scolaire()
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
                            Integer.valueOf(annee_input.getValue().toString()));
                    secondeDAO.insert(seconde);
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
                        Integer.valueOf(annee_input.getValue().toString()));
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
    }
    private void refresh(){
        listseconde.clear();
        listseconde.setAll(secondeDAO.listAll());
        activerRecherche();
        clearInputs();
    }
    public void check(){
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

    public void listEtudiant(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT n_matricule FROM etudiants where classe='seconde' order by n_matricule ASC";
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
                              Float eps,Float tice, String n_mat,Integer trimestre,Integer annee_scolaire){
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
        n_mat_input.getSelectionModel().select(n_mat);
        trimestre_input.setText(trimestre.toString());
        annee_input.getSelectionModel().select(annee_scolaire);
        btn_action.setText("Mettre à jour");
        id_label.setText(id.toString());
    }
}
