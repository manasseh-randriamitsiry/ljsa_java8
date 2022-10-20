package com.manasseh.ljsa.page;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import com.manasseh.ljsa.DAO.EtudiantDAO;
//import manasseh.DAO.ProfDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Label nb_etudiant;
    public Label nb_profs;
    public Label taux_reuissite;
    public Pane menu_pane;
    EtudiantDAO etudiantDAO = new EtudiantDAO();
//    ProfDAO profDAO = new ProfDAO();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            nb_etudiant.setText(String.valueOf(etudiantDAO.nombreEtudiant()));
//            nb_profs.setText(String.valueOf(profDAO.nombreProf()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
