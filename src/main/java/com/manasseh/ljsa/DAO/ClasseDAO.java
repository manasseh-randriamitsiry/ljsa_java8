package com.manasseh.ljsa.DAO;

import com.manasseh.ljsa.model.Classe;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ClasseDAO extends DeleteDAO implements DAOInterface<Classe>{
    PopUp popUp = new PopUp();
    String tableName = "classe";
    @Override
    public ObservableList<Classe> listAll() {
        ObservableList<Classe> profList = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT * FROM "+tableName;
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                profList.add(new Classe(
                        resultSet.getInt("id"),
                        resultSet.getString("classe"),
                        resultSet.getInt("effectif"),
                        resultSet.getInt("level")
                ));
            }
        } catch (SQLException ignored) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
        return profList;
    }
    public ObservableList<Object> listClasse(){
        ObservableList<Object> listClasse = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT classe FROM "+tableName;
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                listClasse.add(resultSet.getString(1));
            }

        } catch (SQLException ignored) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
        return listClasse;
    }
    @Override
    public void update(Classe data) throws SQLException {
        String sql = "UPDATE "+tableName+" SET `classe` = ?, `effectif` = ?,`level` = ?  WHERE `id` = ?;";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,data.getClasse().toUpperCase());
        statement.setInt(2,data.getEffectif());
        statement.setInt(3,data.getLevel());
        statement.setInt(4,data.getId());
        try{
            int res = statement.executeUpdate();
            if (res>0) {
                popUp.success("Success","Mise à jour avec success");
            } else {
                popUp.error("Erreur ","Mise à jour avec erreur");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("Erreur","Le classe "+ data.getClasse() +" est dejà definis");
            e.printStackTrace();
        }
        statement.close();
    }
    @Override
    public void insert(Classe data) throws SQLException {
        String sql = "insert into "+tableName+" values(NULL,?,?,?)";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,data.getClasse().toUpperCase());
        statement.setInt(2,data.getEffectif());
        statement.setInt(3,data.getLevel());
        try {
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                popUp.success("Success","insertion avec success");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("erreur ","Classe dejà enregistré");
            e.printStackTrace();
        }
        statement.close();
    }

}
