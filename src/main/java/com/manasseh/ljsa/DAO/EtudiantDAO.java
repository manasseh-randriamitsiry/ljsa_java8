package com.manasseh.ljsa.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import com.manasseh.ljsa.model.Etudiant;
import javafx.scene.chart.PieChart;

import java.sql.*;

public class EtudiantDAO extends DeleteDAO implements DAOInterface<Etudiant> {
    PopUp popUp = new PopUp();
    String tableName = "etudiants";
    @Override
    public void insert(Etudiant etudiant) throws SQLException {
        String sql = "insert into "+tableName+" values(NULL,?,?,?,?,?)";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,etudiant.getN_mat_etudiant().toUpperCase());
        statement.setString(2,etudiant.getNom_etudiant().toUpperCase());
        statement.setString(3,etudiant.getPrenom_etudiant().toLowerCase());
        statement.setString(4,etudiant.getClasse_etudiant().toUpperCase());
        statement.setString(5,String.valueOf(etudiant.getDate_nais_etudiant()));
        try {
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                popUp.success("Success","insertion avec success");
            }
        } catch (SQLIntegrityConstraintViolationException e){
//            e.printStackTrace();
            popUp.error("erreur ","Le numero matricule est dejà utilisé");
        }
        statement.close();
    }
    @Override
    public ObservableList<Etudiant> listAll(){
        ObservableList<Etudiant> listEtudiant = FXCollections.observableArrayList();
        DatabaseConnection startConnection = new DatabaseConnection();
        Connection connection = startConnection.getConnection();
        String query = "select * from "+ tableName;
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
                        resultSet.getString("date_nais")
                ));
            }
        } catch (SQLException error){
            popUp.error("erreu","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
            error.printStackTrace();
        }
        return listEtudiant;
    }
    @Override
    public void update(Etudiant etudiant) throws SQLException{
        String sql = "update "+tableName+" set n_matricule = ?, nom=?, prenom=?, classe = ?,date_nais=? where id = ?";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,etudiant.getN_mat_etudiant().toUpperCase());
        statement.setString(2,etudiant.getNom_etudiant().toUpperCase());
        statement.setString(3,etudiant.getPrenom_etudiant().toLowerCase());
        statement.setString(4,etudiant.getClasse_etudiant().toUpperCase());
        statement.setString(5,String.valueOf(etudiant.getDate_nais_etudiant()));
        statement.setInt(6,etudiant.getId());
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
        String sql = "select count(*) from "+tableName;
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()){
            nombre = result.getInt(1);
        }
        return nombre;
    }
    public Etudiant getByNmat(String n_matricule){
        DatabaseConnection connection = new DatabaseConnection();
        Etudiant etudiant = null;
        String query = "SELECT * FROM "+tableName+" where n_matricule = '"+n_matricule+"'";
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                etudiant = new Etudiant(
                        resultSet.getInt("id"),
                        resultSet.getString("n_matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("classe"),
                        resultSet.getString("date_nais")
                );
            }
        } catch (SQLException error) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
            error.printStackTrace();
        }
        return etudiant;
    }
    public ObservableList<Object> listSeconde(){
        ObservableList<Object> data = FXCollections.observableArrayList();
        DatabaseConnection connection = new DatabaseConnection();
        String query = "SELECT etudiants.n_matricule FROM etudiants,classe WHERE classe.classe=etudiants.classe and classe.level='1' order by n_matricule ASC";
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                data.add(resultSet.getString(1));
            }
        } catch (SQLException error) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
            error.printStackTrace();
        }
        return data;
    }
    public ObservableList<Object> listPremiere(){
        ObservableList<Object> data = FXCollections.observableArrayList();
        DatabaseConnection connection = new DatabaseConnection();
        String query = "SELECT etudiants.n_matricule FROM etudiants,classe WHERE classe.classe=etudiants.classe and classe.level='2' order by n_matricule ASC";
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                data.add(resultSet.getString(1));
            }
        } catch (SQLException error) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
            error.printStackTrace();
        }
        return data;
    }
    public ObservableList<Object> listTerminal(){
        ObservableList<Object> data = FXCollections.observableArrayList();
        DatabaseConnection connection = new DatabaseConnection();
        String query = "SELECT etudiants.n_matricule FROM etudiants,classe WHERE classe.classe=etudiants.classe and classe.level='3' order by n_matricule ASC";
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                data.add(resultSet.getString(1));
            }
        } catch (SQLException error) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
            error.printStackTrace();
        }
        return data;
    }
    public ObservableList<PieChart.Data> chartEtudiant(){
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<PieChart.Data> pieChartdata= FXCollections.observableArrayList();
        String query = "select classe,count(n_matricule) as nombre from etudiants group by classe;";
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                pieChartdata.add(new PieChart.Data(resultSet.getString(1),Integer.parseInt(resultSet.getString(2))));
            }
        } catch (SQLException error) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
            error.printStackTrace();
        }
        return pieChartdata;
    }

}
