package com.manasseh.ljsa.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import com.manasseh.ljsa.model.Etudiant;

import java.sql.*;

public class EtudiantDAO extends DeleteDAO implements DAOInterface<Etudiant> {
    PopUp popUp = new PopUp();

    @Override
    public void insert(Etudiant etudiant) throws SQLException {
        String sql = "insert into etudiants() values(NULL,?,?,?,?,?,?)";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,etudiant.getN_mat_etudiant().toUpperCase());
        statement.setString(2,etudiant.getNom_etudiant().toUpperCase());
        statement.setString(3,etudiant.getPrenom_etudiant().toLowerCase());
        statement.setString(4,etudiant.getClasse_etudiant().toLowerCase());
        statement.setString(5,etudiant.getSerie_etudiant().toUpperCase());
        statement.setString(6,String.valueOf(etudiant.getDate_nais_etudiant()));
        try {
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                popUp.success("Success","insertion avec success");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("erreur ","Le numero matricule est dejà utilisé");
        }
        statement.close();
    }
    @Override
    public ObservableList<Etudiant> listAll(){
        ObservableList<Etudiant> listEtudiant = FXCollections.observableArrayList();
        DatabaseConnection startConnection = new DatabaseConnection();
        Connection connection = startConnection.getConnection();
        String query = "select * from etudiants";
        listEtudiant.clear();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                listEtudiant.add(new Etudiant(
                        resultSet.getInt("id"),
                        resultSet.getString("n_matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("classe"),
                        resultSet.getString("serie"),
                        resultSet.getString("date_nais")
                ));
            }
        } catch (SQLException error){
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
        return listEtudiant;
    }
    @Override
    public void update(Etudiant etudiant) throws SQLException{
        String sql = "update etudiants set n_matricule = ?, nom=?, prenom=?, classe = ?, serie = ?,date_nais=? where id = ?";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,etudiant.getN_mat_etudiant().toUpperCase());
        statement.setString(2,etudiant.getNom_etudiant().toUpperCase());
        statement.setString(3,etudiant.getPrenom_etudiant().toLowerCase());
        statement.setString(4,etudiant.getClasse_etudiant());
        statement.setString(5,etudiant.getSerie_etudiant());
        statement.setString(6,String.valueOf(etudiant.getDate_nais_etudiant()));
        statement.setInt(7,etudiant.getId());
        try{
            int res = statement.executeUpdate();
            if (res>0) {
                popUp.success("Success","Mise à jour avec success");
            } else {
                popUp.error("Erreur ","Mise à jour avec erreur");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("Erreur","Le numero matricule "+ etudiant.getN_mat_etudiant() +" est dejà prise");
        }
        statement.close();
    }

    public int nombreEtudiant() throws SQLException {
        int nombre = 0;
        String sql = "select count(*) from etudiants";
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()){
            nombre = result.getInt(1);
        }
        return nombre;
    }

    public String getSerie(String n_matricule) throws SQLException {
        String serie = null;
        String sql = "select serie from etudiants where n_matricule = '"+n_matricule+"'";
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            serie = resultSet.getString("serie");
        }
        return serie;
    }

}
