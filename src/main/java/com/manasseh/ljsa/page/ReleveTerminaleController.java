package com.manasseh.ljsa.page;

import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.DAO.TerminaleDAO;
import com.manasseh.ljsa.model.Terminale;
import com.manasseh.ljsa.utils.AnneeLists;
import com.manasseh.ljsa.utils.AutoCompleteComboBoxListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReleveTerminaleController implements Initializable {
    public Label releve_label;
    public TableColumn<Terminale, String> n_mat_column;
    public TableColumn<Terminale, String> mlg_column;
    public TableColumn<Terminale, String> frs_column;
    public TableColumn<Terminale, String> ang_column;
    public TableColumn<Terminale, String> hg_column;
    public TableColumn<Terminale, String> math_column;
    public TableColumn<Terminale, String> phys_column;
    public TableColumn<Terminale, String> svt_column;
    public TableColumn<Terminale, String> phylo_column;
    public TableColumn<Terminale, String> ses_column;
    public TableColumn<Terminale, String> eps_column;
    public TableColumn<Terminale, String> trimestre_column;
    public TableColumn<Terminale, String> total_column;
    public TableColumn<Terminale, String> moyenne_column;
    public TableColumn<Terminale, String> annee_column;
    public TableView<Terminale> table_terminale;
    public ComboBox<Object> nmat_input;
    public ComboBox<String> annee_debut;
    public ComboBox<String> annee_fin;
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    TerminaleDAO terminaleDAO = new TerminaleDAO();
    ObservableList<Terminale> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nmat_input.setItems(etudiantDAO.listTerminal());
        new AutoCompleteComboBoxListener<>(nmat_input);
        new AutoCompleteComboBoxListener<>(annee_debut);
        annee_debut.getItems().addAll(AnneeLists.getYearList(100));
        new AutoCompleteComboBoxListener<>(annee_fin);
        annee_fin.getItems().addAll(AnneeLists.getYearList(100));
        list.setAll(terminaleDAO.listAll());
        table_terminale.setItems(list);

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
    }
}
