package com.manasseh.ljsa.DAO;

import com.manasseh.ljsa.model.Coefficient_premiere;
import com.manasseh.ljsa.model.Premiere;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class PremiereDAO extends DeleteDAO implements DAOInterface<Premiere>{
    PopUp popUp = new PopUp();
    String tableName = "premiere";
    String tableCoeffName = "premiere_note_coeff";
    @Override
    public ObservableList<Premiere> listAll() {
        ObservableList<Premiere> list = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT id,n_mat,trimestre,annee_scolaire,premiere.malagasy*premiere_note_coeff.malagasy as malagasy,premiere.francais*premiere_note_coeff.francais as francais,premiere.anglais*premiere_note_coeff.anglais as anglais,premiere.histogeo*premiere_note_coeff.histogeo as histogeo,premiere.eac*premiere_note_coeff.eac as eac,premiere.ses*premiere_note_coeff.ses as ses,premiere.spc*premiere_note_coeff.spc as spc,premiere.svt*premiere_note_coeff.svt as svt,premiere.mats*premiere_note_coeff.mats as mats,premiere.eps*premiere_note_coeff.eps as eps,premiere.tice*premiere_note_coeff.tice as tice,premiere.phylo*premiere_note_coeff.phylo as phylo from premiere,premiere_note_coeff;";
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                list.add(new Premiere(
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
                        resultSet.getFloat("phylo"),
                        resultSet.getString("n_mat"),
                        resultSet.getInt("trimestre"),
                        resultSet.getInt("annee_scolaire")
                ));
            }
        } catch (SQLException e) {
            popUp.error("erreur","Erreur de connection au base de donn??e. Veuillez contacter l'administrateur");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Premiere data) throws SQLException {
        String sql = "UPDATE "+tableName+" SET `malagasy` = ?, `francais` = ?, `anglais` = ?, `histogeo` = ?, `eac` = ?, `ses` = ?, `spc` = ?, `svt` = ?, `mats` = ?, `eps` = ?, `tice` = ?,`phylo` = ?, `n_mat` = ?, `trimestre` = ?, `annee_scolaire` = ? WHERE `id` = ?";
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
        statement.setFloat(12,data.getPhylo());
        statement.setString(13,data.getN_mat());
        statement.setInt(14,data.getTrimestre());
        statement.setInt(15,data.getAnnee_scolaire());
        statement.setInt(16,data.getId());
        try{
            int res = statement.executeUpdate();
            if (res>0) {
                popUp.success("Success","Mise ?? jour avec success");
            } else {
                popUp.error("Erreur ","Mise ?? jour avec erreur");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("Erreur","Reesayer");
        }
        statement.close();
    }

    @Override
    public void insert(Premiere data) throws SQLException {
        String sql = "INSERT INTO "+tableName+" VALUES ( NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
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
        statement.setFloat(12,data.getPhylo());
        statement.setString(13,data.getN_mat());
        statement.setInt(14,data.getTrimestre());
        statement.setInt(15,data.getAnnee_scolaire());
        try {
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                popUp.success("Success","insertion avec success");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("erreur ","Le numero matricule est dej?? utilis??");
        }
        statement.close();
    }

    public Integer getCoeffTotal() throws SQLException {
        int coeff = 1;
        String sql = "SELECT SUM(malagasy+francais+anglais+histogeo+eac+ses+spc+svt+mats+eps+tice+phylo) as total FROM "+tableCoeffName;
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            coeff = resultSet.getInt("total");
        }
        return coeff;
    }

    public void updateCoeff(Coefficient_premiere data) throws SQLException {
        String sql = "UPDATE premiere_note_coeff SET malagasy = ?,francais = ?,anglais = ?,histogeo = ?,eac = ?,ses = ?,spc = ?,svt = ?,mats = ?,eps = ?,tice = ?,phylo = ? WHERE 1";
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
        statement.setFloat(12,data.getPhylo());
        try{
            int res = statement.executeUpdate();
            if (res>0) {
                popUp.success("Success","Mise ?? jour avec success");
            } else {
                popUp.error("Erreur ","Mise ?? jour avec erreur");
            }
        } catch (SQLIntegrityConstraintViolationException e){
            popUp.error("Erreur","Reesayer");
        }
        statement.close();
    }

    public ObservableList<Coefficient_premiere> listCoeff(){
        ObservableList<Coefficient_premiere> listCoeff = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT * FROM "+tableCoeffName;
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                listCoeff.add(new Coefficient_premiere(
                        resultSet.getInt("malagasy"),
                        resultSet.getInt("francais"),
                        resultSet.getInt("anglais"),
                        resultSet.getInt("histogeo"),
                        resultSet.getInt("eac"),
                        resultSet.getInt("ses"),
                        resultSet.getInt("spc"),
                        resultSet.getInt("svt"),
                        resultSet.getInt("mats"),
                        resultSet.getInt("eps"),
                        resultSet.getInt("tice"),
                        resultSet.getInt("phylo")
                ));
            }
        } catch (SQLException ignored) {
            popUp.error("erreur","Erreur de connection au base de donn??e. Veuillez contacter l'administrateur");
        }
        return listCoeff;
    }
}
