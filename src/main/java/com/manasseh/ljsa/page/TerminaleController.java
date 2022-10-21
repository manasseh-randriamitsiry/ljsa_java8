package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutRight;
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
import com.manasseh.ljsa.DAO.TerminaleDAO;
import com.manasseh.ljsa.model.Terminale;
import manasseh.utils.AutoCompleteComboBoxListener;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TerminaleController implements Initializable{
    public TableView<Terminale> table_terminale;
    public TableColumn<Terminale, Terminale> action_column;
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
            phylo_input,
            ses_input;
    public ComboBox annee_input,
            n_mat_input;
    public TableColumn<Terminale, String> trimestre_column,
            annee_column,
            ang_column,
            svt_column,
            ses_column,
            eps_column,
            frs_column,
            hg_column,
            math_column,
            mlg_column,
            n_mat_column,
            phylo_column,
            phys_column;
    public Label  id_label;
    public Pane action_pane;
    public Label terminale_label;

    ObservableList<Terminale> listTerminale = FXCollections.observableArrayList();
    TerminaleDAO terminaleDAO = new TerminaleDAO();
    Terminale terminale = null;

    PopUp popUp = new PopUp();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        clearInputs();
        new FadeOutRight(action_pane).play();
        String items[] = {"2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033"
                ,"2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047",
                "2048","2049","2050","2051","2052","2053","2054","2055","2056","2057","2058","2059","2060","2061"};
        listEtudiant();
        new AutoCompleteComboBoxListener<>(n_mat_input);
        new AutoCompleteComboBoxListener<>(annee_input);
        annee_input.getItems().addAll(items);
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
        trimestre_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTrimestre().toString()));
        annee_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnnee_scolaire().toString()));

        Callback<TableColumn<Terminale,Terminale>, TableCell<Terminale,Terminale>> newColumn = (TableColumn<Terminale,Terminale> param) -> {
                TableCell<Terminale, Terminale> tableCell = new TableCell<Terminale,Terminale>() {
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
                                    terminale = table_terminale.getSelectionModel().getSelectedItem();
                                    terminale_label.setText("Terminale: edition");
                                    btn_action.setText("Mettre à jour");
                                    setInputText(terminale.getId(),
                                            terminale.getMalagasy(),
                                            terminale.getFrs(),
                                            terminale.getAnglais(),
                                            terminale.getHistoGeo(),
                                            terminale.getPhylosphie(),
                                            terminale.getEps(),
                                            terminale.getMathematique(),
                                            terminale.getSpc(),
                                            terminale.getSvt(),
                                            terminale.getSes(),
                                            terminale.getN_mat(),
                                            terminale.getTrimestre(),
                                            terminale.getAnnee_scolaire()
                                            );
                                    action_pane.setVisible(true);
                                    new FadeInRight(action_pane).play();
                                } catch (NullPointerException e) {
                                    System.out.println(e);
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
                return tableCell;
        };
        action_column.setCellFactory(newColumn);
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
                            Integer.valueOf(annee_input.getValue().toString()));
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
                        Integer.valueOf(annee_input.getValue().toString()));
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
    }
    private void refresh(){
        listTerminale.clear();
        listTerminale.setAll(terminaleDAO.listAll());
        activerRecherche();
        clearInputs();
//        check();
    }

    public void check(){
//        if (!(mlg_input.getText()).isEmpty() &&
//                !(frs_input.getText()).isEmpty() &&
//                !(ang_input.getText()).isEmpty() &&
//                !(hg_input.getText()).isEmpty() &&
//                !(phylo_input.getText()).isEmpty() &&
//                !(eps_input.getText()).isEmpty() &&
//                !(math_input.getText()).isEmpty() &&
//                !(pc_input.getText()).isEmpty() &&
//                !(svt_input.getText()).isEmpty() &&
//                !(ses_input.getText()).isEmpty() &&
//                n_mat_input.getValue()!=null &&
//                !(trimestre_input.getText()).isEmpty() &&
//                annee_input.getValue()!=null
//        ){
//            btn_action.setVisible(true);
//        } else if (mlg_input.getText().isEmpty() ||
//                frs_input.getText().isEmpty() ||
//                ang_input.getText().isEmpty() ||
//                hg_input.getText().isEmpty() ||
//                phylo_input.getText().isEmpty() ||
//                eps_input.getText().isEmpty() ||
//                math_input.getText().isEmpty() ||
//                pc_input.getText().isEmpty() ||
//                svt_input.getText().isEmpty() ||
//                ses_input.getText().isEmpty() ||
//                n_mat_input.getValue() ==null ||
//                trimestre_input.getText().isEmpty() ||
//                annee_input.getValue()==null
//        ){
//            btn_action.setVisible(false);
//        }
    }
    private FilteredList<Terminale> activerRecherche() {
        FilteredList<Terminale> filteredList = new FilteredList<>(listTerminale, a->true);
        recherche_input.textProperty().addListener((Observable,oldValue,newValue) -> filteredList.setPredicate(terminale -> {
            if (newValue.isEmpty()
                    || terminale.getAnglais().toString().contains(newValue)
                    || terminale.getPhylosphie().toString().contains(newValue)
                    || terminale.getMalagasy().toString().contains(newValue)
                    || terminale.getN_mat().contains(newValue.toUpperCase())
                    || terminale.getHistoGeo().toString().contains(newValue)
                    || terminale.getMathematique().toString().contains(newValue)
                    || terminale.getSpc().toString().contains(newValue)
                    || terminale.getSvt().toString().contains(newValue)
                    || terminale.getEps().toString().contains(newValue)
                    || terminale.getSes().toString().contains(newValue)
                    || terminale.getAnnee_scolaire().toString().contains(newValue)
            ){
                return true;
            }else return false;
        }));
        SortedList<Terminale> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table_terminale.comparatorProperty());
        table_terminale.setItems(sortedList);
        return filteredList;
    }

    public void listEtudiant(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT n_matricule FROM etudiants";
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
                              Float phylosphie, Float eps, Float mathematique, Float spc, Float svt,
                              Float ses, String n_mat,Integer trimestre,Integer annee_scolaire){
        mlg_input.setText(malagasy.toString());
        frs_input.setText(frs.toString());
        ang_input.setText(anglais.toString());
        hg_input.setText(histoGeo.toString());
        phylo_input.setText(phylosphie.toString());
        math_input.setText(mathematique.toString());
        pc_input.setText(spc.toString());
        svt_input.setText(svt.toString());
        ses_input.setText(ses.toString());
        eps_input.setText(eps.toString());
        n_mat_input.getSelectionModel().select(n_mat);
        trimestre_input.setText(trimestre.toString());
        annee_input.getSelectionModel().select(annee_scolaire);
        btn_action.setText("Mettre à jour");
        id_label.setText(id.toString());
    }
}
