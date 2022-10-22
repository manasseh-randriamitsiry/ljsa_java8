package com.manasseh.ljsa.page;

import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    public AreaChart<?, ?> area_chart;
    public CategoryAxis x;
    public NumberAxis y;
    public Label taux_reuissite;
    public Pane menu_pane;
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    ProfDAO profDAO = new ProfDAO();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            nb_etudiant.setText(String.valueOf(etudiantDAO.nombreEtudiant()));
            nb_profs.setText(String.valueOf(profDAO.nombreProf()));
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data<>("2",34));
            area_chart.getData().add(series);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
