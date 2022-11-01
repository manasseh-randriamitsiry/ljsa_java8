package com.manasseh.ljsa.page;

import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.DAO.PremiereDAO;
import com.manasseh.ljsa.model.Premiere;
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

public class RelevePremiereController implements Initializable {
    public Label releve_label;
    public TableColumn<Premiere,String> n_mat_column;
    public TableColumn<Premiere,String> mlg_column;
    public TableColumn<Premiere,String> frs_column;
    public TableColumn<Premiere,String> ang_column;
    public TableColumn<Premiere,String> hg_column;
    public TableColumn<Premiere,String> math_column;
    public TableColumn<Premiere,String> phys_column;
    public TableColumn<Premiere,String> svt_column;
    public TableColumn<Premiere,String> phylo_column;
    public TableColumn<Premiere,String> ses_column;
    public TableColumn<Premiere,String> eps_column;
    public TableColumn<Premiere,String> trimestre_column;
    public TableColumn<Premiere,String> total_column;
    public TableColumn<Premiere,String> moyenne_column;
    public TableColumn<Premiere,String> annee_column;
    public TableColumn<Premiere,String> action_column;
    public TableColumn<Premiere,String> eac_column;
    public TableColumn<Premiere,String> tice_column;
    public TableView<Premiere> premiere_table;
    public ComboBox<Object> nmat_input;
    public ComboBox<String> annee_debut;
    public ComboBox<String> annee_fin;
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    PremiereDAO premiereDAO = new PremiereDAO();
    ObservableList<Premiere> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nmat_input.setItems(etudiantDAO.listEtudiant());
        new AutoCompleteComboBoxListener<>(nmat_input);
        new AutoCompleteComboBoxListener<>(annee_debut);
        annee_debut.getItems().addAll(AnneeLists.getYearList(100));
        new AutoCompleteComboBoxListener<>(annee_fin);
        annee_fin.getItems().addAll(AnneeLists.getYearList(100));
        list.setAll(premiereDAO.listAll());
        premiere_table.setItems(list);

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

    }
}
