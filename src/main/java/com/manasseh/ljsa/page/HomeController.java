package com.manasseh.ljsa.page;

import com.manasseh.ljsa.DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Label nb_etudiant;
    public Label nb_profs;
    public Label taux_reuissite;
    public Pane menu_pane;
    public PieChart pie_chart;
    public Label taux_reuissite_seconde;
    public Label annee_seconde;
    public Label taux_reuissite_premiere;
    public Label annee_premiere;
    public Label taux_reuissite_terminale;
    public Label annee_terminale;
    EtudiantDAO etudiantDAO = new EtudiantDAO();
    SecondeDAO secondeDAO = new SecondeDAO();
    PremiereDAO premiereDAO = new PremiereDAO();
    TerminaleDAO terminaleDAO = new TerminaleDAO();
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

//            int current_year = Integer.valueOf(String.valueOf(Year.now()));
//            new ZoomIn(taux_reuissite_seconde).play();
            taux_reuissite_seconde.setText(String.valueOf(secondeDAO.getTotal()));
            annee_seconde.setText("");
            
            taux_reuissite_premiere.setText(String.valueOf(premiereDAO.getTotal()));
            annee_premiere.setText("");
//            new ZoomIn(taux_reuissite_premiere).play();
            
            taux_reuissite_terminale.setText(String.valueOf(terminaleDAO.getTotal()));
            annee_terminale.setText("");
//            new ZoomIn(taux_reuissite_terminale).play();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
