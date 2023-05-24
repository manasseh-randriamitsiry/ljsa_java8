package com.manasseh.ljsa.DAO;

import com.manasseh.ljsa.model.Coefficient_seconde;
import com.manasseh.ljsa.model.Seconde;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class SecondeDAO extends DeleteDAO implements DAOInterface<Seconde>{
    PopUp popUp = new PopUp();
    String tableName = "seconde";
    String tableCoeffName = "seconde_note_coeff";
    @Override
    public ObservableList<Seconde> listAll() {
        ObservableList<Seconde> list = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "select seconde.malagasy*seconde_note_coeff.c_malagasy as malagasy,seconde.francais*seconde_note_coeff.c_francais as francais,seconde.anglais*seconde_note_coeff.c_anglais as anglais,seconde.histogeo*seconde_note_coeff.c_histogeo as histogeo,seconde.eac*seconde_note_coeff.c_eac as eac,seconde.ses*seconde_note_coeff.c_ses as ses,seconde.spc*seconde_note_coeff.c_spc as spc,seconde.svt*seconde_note_coeff.c_svt as svt,seconde.mats*seconde_note_coeff.c_mats as mats,seconde.eps*seconde_note_coeff.c_eps as eps,seconde.tice*seconde_note_coeff.c_tice as tice, n_mat, trimestre, annee_scolaire,id FROM seconde,seconde_note_coeff;";
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
        } catch (SQLException e) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<Seconde> listAllFIlter(int startYear,int endYear, String nMat) {
        ObservableList<Seconde> list = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "select seconde.malagasy*seconde_note_coeff.c_malagasy as malagasy,seconde.francais*seconde_note_coeff.c_francais as francais,seconde.anglais*seconde_note_coeff.c_anglais as anglais,seconde.histogeo*seconde_note_coeff.c_histogeo as histogeo,seconde.eac*seconde_note_coeff.c_eac as eac,seconde.ses*seconde_note_coeff.c_ses as ses,seconde.spc*seconde_note_coeff.c_spc as spc,seconde.svt*seconde_note_coeff.c_svt as svt,seconde.mats*seconde_note_coeff.c_mats as mats,seconde.eps*seconde_note_coeff.c_eps as eps,seconde.tice*seconde_note_coeff.c_tice as tice, n_mat, trimestre, annee_scolaire,id FROM seconde,seconde_note_coeff where annee_scolaire between '"+startYear+"' and '"+endYear+"' and n_mat = '"+nMat+"' ;";
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
        } catch (SQLException e) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public void update(Seconde data) throws SQLException {
        String sql = "UPDATE "+tableName+" SET `malagasy` = ?, `francais` = ?, `anglais` = ?, `histogeo` = ?, `eac` = ?, `ses` = ?, `spc` = ?, `svt` = ?, `mats` = ?, `eps` = ?, `tice` = ?, `n_mat` = ?, `trimestre` = ?, `annee_scolaire` = ? WHERE `id` = ?";
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
    public void updateCoeff(Coefficient_seconde data) throws SQLException {
        String sql = "UPDATE `seconde_note_coeff` SET `c_malagasy`=?,`c_francais`=?,`c_anglais`=?,`c_histogeo`=?,`c_eac`=?,`c_ses`=?,`c_spc`=?,`c_svt`=?,`c_mats`=?,`c_eps`=?,`c_tice`=? WHERE 1";
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
    public Integer getCoeffTotal() throws SQLException {
        int coeff = 1;
        String sql = "SELECT SUM(c_anglais+c_eac+c_eps+c_francais+c_histogeo+c_malagasy+c_mats+c_ses+c_spc+c_svt+c_tice) as total FROM "+tableCoeffName;
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            coeff = resultSet.getInt("total");
        }
        return coeff;
    }
    @Override
    public void insert(Seconde data) throws SQLException {
        String sql = "INSERT INTO "+tableName+" VALUES ( NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
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
    public ObservableList<Coefficient_seconde> listCoeff(){
        ObservableList<Coefficient_seconde> listCoeff = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT * FROM "+tableCoeffName;
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                listCoeff.add(new Coefficient_seconde(
                        resultSet.getInt("c_malagasy"),
                        resultSet.getInt("c_francais"),
                        resultSet.getInt("c_anglais"),
                        resultSet.getInt("c_histogeo"),
                        resultSet.getInt("c_eac"),
                        resultSet.getInt("c_ses"),
                        resultSet.getInt("c_spc"),
                        resultSet.getInt("c_svt"),
                        resultSet.getInt("c_mats"),
                        resultSet.getInt("c_eps"),
                        resultSet.getInt("c_tice")
                ));
            }
        } catch (SQLException ignored) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
        return listCoeff;
    }
    public int getTotal() throws SQLException {
        int result = 0;
//        int current_year = Integer.valueOf(String.valueOf(Year.now()));
//        String sql = "select SUM(seconde.malagasy*seconde_note_coeff.malagasy+seconde.francais*seconde_note_coeff.francais+seconde.anglais*seconde_note_coeff.anglais+seconde.histogeo*seconde_note_coeff.histogeo+seconde.eac*seconde_note_coeff.eac+seconde.ses*seconde_note_coeff.ses+seconde.spc*seconde_note_coeff.spc+seconde.svt*seconde_note_coeff.svt+seconde.mats*seconde_note_coeff.mats+seconde.eps*seconde_note_coeff.eps+seconde.tice*seconde_note_coeff.tice)*5/(count(seconde.n_mat)*SUM(seconde_note_coeff.anglais+seconde_note_coeff.malagasy+seconde_note_coeff.francais+seconde_note_coeff.histogeo+seconde_note_coeff.eac+seconde_note_coeff.ses+seconde_note_coeff.spc+seconde_note_coeff.svt+seconde_note_coeff.mats+seconde_note_coeff.eps+seconde_note_coeff.tice)) as moyenne FROM seconde,seconde_note_coeff where seconde.annee_scolaire='"+current_year+"'";
        String sql = "select count(n_matricule) as total from etudiants,classe where classe.classe=etudiants.classe and classe.level='1'";
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            result = resultSet.getInt("total");
        }
        return result;
    }
    public ObservableList<Object> listNmatriculeSeconde(){
        ObservableList<Object> data = FXCollections.observableArrayList();
        DatabaseConnection connection = new DatabaseConnection();
        String query = "SELECT n_mat FROM seconde order by n_mat ASC";
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
}
