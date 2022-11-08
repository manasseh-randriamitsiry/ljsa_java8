package com.manasseh.ljsa.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.model.Matiere;
import com.manasseh.ljsa.utils.PopUp;
import java.sql.*;

public class MatiereDao extends DeleteDAO implements DAOInterface<Matiere>{
    PopUp popUp = new PopUp();
    String tableName = "matiere";
    @Override
    public ObservableList<Matiere> listAll() {
        ObservableList<Matiere> list = FXCollections.observableArrayList();
        DatabaseConnection startConnection = new DatabaseConnection();
        Connection connection = startConnection.getConnection();
        String query = "select * from "+tableName;
        list.clear();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                list.add(new Matiere(
                        resultSet.getInt("id"),
                        resultSet.getString("designation"),
                        resultSet.getString("abreviation"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException error){
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
        return list;
    }
    @Override
    public void update(Matiere matiere) throws SQLException {
        String sql = "update "+tableName+" set designation = ?, abreviation=?, description=? where id = ?";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,matiere.getDesignation());
        statement.setString(2,matiere.getAbreviation().toUpperCase());
        statement.setString(3,matiere.getDescription().toLowerCase());
        statement.setInt(4,matiere.getId());
        try{
            int res = statement.executeUpdate();
            if (res>0) {
                popUp.success("Success","Mise à jour avec success");
            } else {
                popUp.error("Erreur ","Mise à jour avec erreur");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("Erreur","Le matiere "+ matiere.getDesignation() +" est dejà enregisté");
        }
        statement.close();
    }
    @Override
    public void insert(Matiere matiere) throws SQLException {
        String sql = "insert into "+tableName+" values(NULL,?,?,?)";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,matiere.getDescription());
        statement.setString(2,matiere.getAbreviation().toUpperCase());
        statement.setString(3,matiere.getDescription().toLowerCase());
        try {
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                popUp.success("Success","insertion avec success");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("erreur ","Erreur durant l'ajout.");
        }
        statement.close();
    }
}
