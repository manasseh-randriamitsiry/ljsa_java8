package com.manasseh.ljsa.DAO;
import javafx.collections.ObservableList;
import java.sql.SQLException;
public interface DAOInterface<T> {
    ObservableList<T> listAll();
    void update(T data) throws SQLException;
    void insert(T data) throws SQLException;

}
