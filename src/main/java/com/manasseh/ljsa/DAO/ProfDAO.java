package com.manasseh.ljsa.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import com.manasseh.ljsa.model.Prof;
import java.sql.*;

public class ProfDAO extends DeleteDAO implements DAOInterface<Prof> {
    PopUp popUp = new PopUp();
    @Override
    public void insert(Prof prof) throws SQLException {
        String sql = "insert into profs() values(NULL,?,?,?,?,?,?)";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,prof.getN_mat().toUpperCase());
        statement.setString(2,prof.getNom_prof().toUpperCase());
        statement.setString(3,prof.getPrenom_prof().toLowerCase());
        statement.setString(4,String.valueOf(prof.getDate_nais()));
        statement.setString(5,String.valueOf(prof.getDate_prise_service()));
        statement.setString(6,String.valueOf(prof.getDate_cessation_service()));
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
    public ObservableList<Prof> listAll() {
        ObservableList<Prof> profList = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT * FROM profs";
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                profList.add(new Prof(
                        resultSet.getInt("id"),
                        resultSet.getString("n_matricule"),
                        resultSet.getString("nom_prof"),
                        resultSet.getString("prenom_prof"),
                        resultSet.getDate("date_nais").toLocalDate(),
                        resultSet.getDate("date_prise_service").toLocalDate(),
                        resultSet.getDate("date_cessation_service").toLocalDate()
                ));
            }
        } catch (SQLException ignored) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
        return profList;
    }

    @Override
    public void update(Prof prof) throws SQLException{
        String sql = "update profs set n_matricule = ?, nom_prof=?, prenom_prof=?,date_nais=?,date_prise_service=?,date_cessation_service=? where id = ?";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1,prof.getN_mat().toUpperCase());
        statement.setString(2,prof.getNom_prof().toUpperCase());
        statement.setString(3,prof.getPrenom_prof().toLowerCase());
        statement.setString(4,String.valueOf(prof.getDate_nais()));
        statement.setString(5,String.valueOf(prof.getDate_prise_service()));
        statement.setString(6,String.valueOf(prof.getDate_cessation_service()));
        statement.setInt(7,prof.getId());
        try{
            int res = statement.executeUpdate();
            if (res>0) {
                popUp.success("Success","Mise à jour avec success");
            } else {
                popUp.error("Erreur ","Mise à jour avec erreur");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("Erreur","Le numero matricule "+ prof.getN_mat() +" est dejà prise");
        }
        statement.close();
    }

    public int nombreProf() throws SQLException {
        int nombre = 0;
        String sql = "select count(*) from profs";
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()){
            nombre = result.getInt(1);
        }
        return nombre;
    }
}
