package com.manasseh.ljsa.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.manasseh.ljsa.model.Terminale;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import java.sql.*;

public class TerminaleDAO extends DeleteDAO implements DAOInterface<Terminale>{
    PopUp popUp = new PopUp();
    String tableName = "terminale";
    @Override
    public ObservableList<Terminale> listAll() {
        ObservableList<Terminale> listTerminales = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "select * from "+tableName;
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
        String sql = "INSERT INTO "+tableName+" VALUES ( NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setFloat(1, newTerminale.getMalagasy());
        statement.setFloat(2, newTerminale.getFrs());
        statement.setFloat(3,newTerminale.getAnglais());
        statement.setFloat(4,newTerminale.getHistoGeo());
        statement.setFloat(5,newTerminale.getPhylosphie());
        statement.setFloat(6,newTerminale.getMathematique());
        statement.setFloat(7,newTerminale.getSpc());
        statement.setFloat(8,newTerminale.getSvt());
        statement.setFloat(9,newTerminale.getSes());
        statement.setFloat(10,newTerminale.getEps());
        statement.setString(11,newTerminale.getN_mat());
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


}
