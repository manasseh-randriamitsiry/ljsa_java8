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
        String query = "SELECT id,n_mat,trimestre,annee_scolaire,premiere.malagasy*premiere_note_coeff.c_malagasy as malagasy,premiere.francais*premiere_note_coeff.c_francais as francais,premiere.anglais*premiere_note_coeff.c_anglais as anglais,premiere.histogeo*premiere_note_coeff.c_histogeo as histogeo,premiere.eac*premiere_note_coeff.c_eac as eac,premiere.ses*premiere_note_coeff.c_ses as ses,premiere.spc*premiere_note_coeff.c_spc as spc,premiere.svt*premiere_note_coeff.c_svt as svt,premiere.mats*premiere_note_coeff.c_mats as mats,premiere.eps*premiere_note_coeff.c_eps as eps,premiere.tice*premiere_note_coeff.c_tice as tice,premiere.phylo*premiere_note_coeff.c_phylo as phylo from premiere,premiere_note_coeff;";
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
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
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
            popUp.error("erreur ","Le numero matricule est dejà utilisé");
        }
        statement.close();
    }

    public Integer getCoeffTotal() throws SQLException {
        int coeff = 1;
        String sql = "SELECT SUM(c_malagasy+c_francais+c_anglais+c_histogeo+c_eac+c_ses+c_spc+c_svt+c_mats+c_eps+c_tice+c_phylo) as total FROM "+tableCoeffName;
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            coeff = resultSet.getInt("total");
        }
        return coeff;
    }

    public void updateCoeff(Coefficient_premiere data) throws SQLException {
        String sql = "UPDATE premiere_note_coeff SET c_malagasy = ?,c_francais = ?,c_anglais = ?,c_histogeo = ?,c_eac = ?,c_ses = ?,c_spc = ?,c_svt = ?,c_mats = ?,c_eps = ?,c_tice = ?,c_phylo = ? WHERE 1";
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
                popUp.success("Success","Mise à jour avec success");
            } else {
                popUp.error("Erreur ","Mise à jour avec erreur");
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
                        resultSet.getInt("c_tice"),
                        resultSet.getInt("c_phylo")
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
        String sql = "select count(n_matricule) as total from etudiants,classe where classe.classe=etudiants.classe and classe.level='2'";
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            result = resultSet.getInt("total");
        }
        return result;
    }
    public ObservableList<Object> listNmatriculePremiere(){
        ObservableList<Object> data = FXCollections.observableArrayList();
        DatabaseConnection connection = new DatabaseConnection();
        String query = "SELECT n_mat FROM premiere order by n_mat ASC";
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
