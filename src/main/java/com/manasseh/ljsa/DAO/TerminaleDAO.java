package com.manasseh.ljsa.DAO;

import com.manasseh.ljsa.model.Coefficient_terminale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.manasseh.ljsa.model.Terminale;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import java.sql.*;

public class TerminaleDAO extends DeleteDAO implements DAOInterface<Terminale>{
    PopUp popUp = new PopUp();
    String tableName = "terminale";
    String tableCoeffName = "terminale_note_coeff";
    @Override
    public ObservableList<Terminale> listAll() {
        ObservableList<Terminale> listTerminales = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT id,nmat,trimestre,annee_scolaire,terminale.mlg*terminale_note_coeff.c_malagasy as mlg,terminale.frs*terminale_note_coeff.c_francais as frs,terminale.anglais*terminale_note_coeff.c_anglais as anglais,terminale.histogeo*terminale_note_coeff.c_histogeo as histogeo,terminale.phylo*terminale_note_coeff.c_phylo as phylo,terminale.math*terminale_note_coeff.c_mats as math,terminale.spc*terminale_note_coeff.c_spc as spc,terminale.svt*terminale_note_coeff.c_svt as svt,terminale.ses*terminale_note_coeff.c_ses as ses,terminale.eps*terminale_note_coeff.c_eps as eps from terminale,terminale_note_coeff";
        listTerminales.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
             listTerminales.add(new Terminale(
                     resultSet.getInt("id"),
                     resultSet.getFloat("mlg"),
                     resultSet.getFloat("frs"),
                     resultSet.getFloat("anglais"),
                     resultSet.getFloat("histogeo"),
                     resultSet.getFloat("phylo"),
                     resultSet.getFloat("eps"),
                     resultSet.getFloat("math"),
                     resultSet.getFloat("spc"),
                     resultSet.getFloat("svt"),
                     resultSet.getFloat("ses"),
                     resultSet.getString("nmat"),
                     resultSet.getInt("trimestre"),
                     resultSet.getInt("annee_scolaire")
             ));
            }
        } catch (SQLException error){
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
        return listTerminales;
    }

    @Override
    public void update(Terminale update) throws SQLException {
        String sql = "UPDATE "+tableName+" SET `mlg` = ?, `frs` = ?, `anglais` = ?, `histogeo` = ?, `phylo` = ?, " +
                "`math` = ?, `spc` = ?, `svt` = ?, `ses` = ?, `eps` = ?, `nmat` = ?, `trimestre` = ?, `annee_scolaire` = ? WHERE `id` = ?";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setFloat(1,update.getMalagasy());
        statement.setFloat(2,update.getFrs());
        statement.setFloat(3,update.getAnglais());
        statement.setFloat(4,update.getHistoGeo());
        statement.setFloat(5,update.getPhylosphie());
        statement.setFloat(6,update.getMathematique());
        statement.setFloat(7,update.getSpc());
        statement.setFloat(8,update.getSvt());
        statement.setFloat(9,update.getSes());
        statement.setFloat(10,update.getEps());
        statement.setString(11,update.getN_mat());
        statement.setInt(12,update.getTrimestre());
        statement.setInt(13,update.getAnnee_scolaire());
        statement.setInt(14,update.getId());
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
    public void insert(Terminale newTerminale) throws SQLException {
        String sql = "INSERT INTO `terminale`(`id`, `nmat`, `mlg`, `frs`, `anglais`, `histogeo`, `phylo`, `math`, `spc`, `svt`, `ses`, `eps`, `trimestre`, `annee_scolaire`) VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setString(1, newTerminale.getN_mat());
        statement.setFloat(2,newTerminale.getMalagasy());
        statement.setFloat(3,newTerminale.getFrs());
        statement.setFloat(4,newTerminale.getAnglais());
        statement.setFloat(5,newTerminale.getHistoGeo());
        statement.setFloat(6,newTerminale.getPhylosphie());
        statement.setFloat(7,newTerminale.getMathematique());
        statement.setFloat(8,newTerminale.getSpc());
        statement.setFloat(9,newTerminale.getSvt());
        statement.setFloat(10,newTerminale.getSes());
        statement.setFloat(11,newTerminale.getEps());
        statement.setInt(12,newTerminale.getTrimestre());
        statement.setInt(13,newTerminale.getAnnee_scolaire());
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
        String sql = "SELECT SUM(c_malagasy+c_francais+c_anglais+c_histogeo+c_phylo+c_mats+c_spc+c_svt+c_ses+c_eps) as total FROM "+tableCoeffName;
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            coeff = resultSet.getInt("total");
        }
        return coeff;
    }

    public void updateCoeff(Coefficient_terminale data) throws SQLException {
        String sql = "UPDATE terminale_note_coeff SET c_malagasy = ?,c_francais = ?,c_anglais = ?,c_histogeo = ?,c_phylo = ?,c_eps = ?,c_mats = ?,c_spc = ?,c_svt = ?,c_ses = ? WHERE 1";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setFloat(1, data.getMalagasy());
        statement.setFloat(2, data.getFrs());
        statement.setFloat(3,data.getAnglais());
        statement.setFloat(4,data.getHistoGeo());
        statement.setFloat(5,data.getPhylosphie());
        statement.setFloat(6,data.getEps());
        statement.setFloat(7,data.getMathematique());
        statement.setFloat(8,data.getSpc());
        statement.setFloat(9,data.getSvt());
        statement.setFloat(10,data.getSes());

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

    public ObservableList<Coefficient_terminale> listCoeff(){
        ObservableList<Coefficient_terminale> listCoeff = FXCollections.observableArrayList();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT * FROM "+tableCoeffName;
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                listCoeff.add(new Coefficient_terminale(
                    resultSet.getInt("c_malagasy"),
                    resultSet.getInt("c_francais"),
                    resultSet.getInt("c_anglais"),
                    resultSet.getInt("c_histogeo"),
                    resultSet.getInt("c_phylo"),
                    resultSet.getInt("c_eps"),
                    resultSet.getInt("c_mats"),
                    resultSet.getInt("c_spc"),
                    resultSet.getInt("c_svt"),
                    resultSet.getInt("c_ses")
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
        String sql = "select count(n_matricule) as total from etudiants,classe where classe.classe=etudiants.classe and classe.level='3'";
        DatabaseConnection connection = new DatabaseConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            result = resultSet.getInt("total");
        }
        return result;
    }
    public ObservableList<Object> listNmatriculeTerminale(){
        ObservableList<Object> data = FXCollections.observableArrayList();
        DatabaseConnection connection = new DatabaseConnection();
        String query = "SELECT nmat FROM terminale order by nmat ASC";
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
