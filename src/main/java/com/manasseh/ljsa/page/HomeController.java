package com.manasseh.ljsa.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import com.manasseh.ljsa.DAO.EtudiantDAO;
import  com.manasseh.ljsa.DAO.ProfDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Label nb_etudiant;
    public Label nb_profs;

    public Label taux_reuissite;
    public Pane menu_pane;
    public PieChart pie_chart;
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    ProfDAO profDAO = new ProfDAO();
    ObservableList<Object> listEtudiant = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            nb_etudiant.setText(String.valueOf(etudiantDAO.nombreEtudiant()));
            nb_profs.setText(String.valueOf(profDAO.nombreProf()));
            listEtudiant.add(etudiantDAO.chartEtudiant());

            pie_chart.setLabelsVisible(true);
            pie_chart.setData(etudiantDAO.chartEtudiant());
            pie_chart.setTitle("Representation des etudiants au LJSA");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
