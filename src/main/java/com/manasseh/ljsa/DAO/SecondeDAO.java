package com.manasseh.ljsa.DAO;

import com.manasseh.ljsa.model.Seconde;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class SecondeDAO extends DeleteDAO implements DAOInterface<Seconde>{
    PopUp popUp = new PopUp();
    @Override
    public ObservableList<Seconde> listAll() {
        ObservableList<Seconde> list = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT * FROM seconde";
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                list.add(new Seconde(
                        resultSet.getInt("id"),
                        resultSet.getFloat("malagasy"),
                        resultSet.getFloat("francais"),
                        resultSet.getFloat("anglais"),
                        resultSet.getFloat("histogeo"),
                        resultSet.getFloat("eac"),
                        resultSet.getFloat("ses"),
                        resultSet.getFloat("spc"),
                        resultSet.getFloat("svt"),
                        resultSet.getFloat("mats"),
                        resultSet.getFloat("eps"),
                        resultSet.getFloat("tice"),
                        resultSet.getString("n_mat"),
                        resultSet.getInt("trimestre"),
                        resultSet.getInt("annee_scolaire")
                ));
            }
        } catch (SQLException ignored) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
        return list;
    }

    @Override
    public void update(Seconde data) throws SQLException {
        String sql = "UPDATE `seconde` SET `malagasy` = ?, `francais` = ?, `anglais` = ?, `histogeo` = ?, `eac` = ?, `ses` = ?, `spc` = ?, `svt` = ?, `mats` = ?, `eps` = ?, `tice` = ?, `n_mat` = ?, `trimestre` = ?, `annee_scolaire` = ? WHERE `seconde`.`id` = ?";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setFloat(1, data.getMalagasy());
        statement.setFloat(2, data.getFrancais());
        statement.setFloat(3,data.getAnglais());
        statement.setFloat(4,data.getHistogeo());
        statement.setFloat(5,data.getEac());
        statement.setFloat(6,data.getSes());
        statement.setFloat(7,data.getSpc());
        statement.setFloat(8,data.getSvt());
        statement.setFloat(9,data.getMats());
        statement.setFloat(10,data.getEps());
        statement.setFloat(11,data.getTice());
        statement.setString(12,data.getN_mat());
        statement.setInt(13,data.getTrimestre());
        statement.setInt(14,data.getAnnee_scolaire());
        statement.setInt(15,data.getId());
        try{
            int res = statement.executeUpdate();
            if (res>0) {
                popUp.success("Success","Mise à jour avec success");
            } else {
                popUp.error("Erreur ","Mise à jour avec erreur");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("Erreur","Reesayer");
        }
        statement.close();
    }

    @Override
    public void insert(Seconde data) throws SQLException {
        String sql = "INSERT INTO seconde() VALUES ( NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setFloat(1, data.getMalagasy());
        statement.setFloat(2, data.getFrancais());
        statement.setFloat(3,data.getAnglais());
        statement.setFloat(4,data.getHistogeo());
        statement.setFloat(5,data.getEac());
        statement.setFloat(6,data.getSes());
        statement.setFloat(7,data.getSpc());
        statement.setFloat(8,data.getSvt());
        statement.setFloat(9,data.getMats());
        statement.setFloat(10,data.getEps());
        statement.setFloat(11,data.getTice());
        statement.setString(12,data.getN_mat());
        statement.setInt(13,data.getTrimestre());
        statement.setInt(14,data.getAnnee_scolaire());
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
}
