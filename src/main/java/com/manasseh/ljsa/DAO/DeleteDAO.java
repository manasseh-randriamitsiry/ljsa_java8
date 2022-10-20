package com.manasseh.ljsa.DAO;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.manasseh.ljsa.utils.PopUp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteDAO {
    public void delete(Integer id,String tableName,String idName) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ljsa","root","123456789");
        String sql = "DELETE FROM "+tableName+" WHERE "+idName+"="+id;
        PreparedStatement statement = connection.prepareStatement(sql);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("confirmer la suppression ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PopUp deleted = new PopUp();
                deleted.success("Supprimée","suppression avec success");
            }
        }
        statement.close();
        connection.close();
    }
}
