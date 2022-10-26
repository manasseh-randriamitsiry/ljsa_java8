package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeInRightBig;
import animatefx.animation.FadeOutRight;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.model.*;
import com.manasseh.ljsa.utils.DatabaseConnection;
import com.manasseh.ljsa.utils.PopUp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EtudiantController implements Initializable {
    public Pane action_pane;
    public TextField numero_matricule_input;
    public TextField nom_input;
    public TextField prenom_input;
    public Button btn_action;
    public DatePicker date_nais_picker;
    public TableView<Etudiant> table_etudiant;
    public TableColumn<Etudiant,String> numero_matricule_column;
    public TableColumn<Etudiant,String> nom_column;
    public TableColumn<Etudiant,String> prenom_column;
    public TableColumn<Etudiant,String> classe_column;
    public TableColumn<Etudiant,String> serie_column;
    public TableColumn<Etudiant, String> date_nais_column;
    public TableColumn<Etudiant,Etudiant> action_column;
    public TextField recherche_input;
    public Label etudiant_label;
    public ComboBox<Object>  serie_input;
    public ComboBox<Object> classe_input;
    public Label id_label;
    public Pane pane_etudiant;
    EtudiantDAO dao = new EtudiantDAO();
    Etudiant etudiant;
    PopUp popUp = new PopUp();
    ObservableList<Etudiant> listEtudiant = FXCollections.observableArrayList();
    private void refresh(){
        listEtudiant.clear();
        listEtudiant.setAll(dao.listAll());
        activerRecherche();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listSerie();
        String[] items = {"Seconde","Première","Terminale"};
        classe_input.getItems().addAll(items);
        refresh();
        numero_matricule_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getN_mat_etudiant()));
        nom_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNom_etudiant()));
        prenom_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPrenom_etudiant()));
        classe_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getClasse_etudiant()));
        serie_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSerie_etudiant()));
        date_nais_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDate_nais_etudiant()));

        Callback<TableColumn<Etudiant,Etudiant>, TableCell<Etudiant,Etudiant>> newColumn = (TableColumn<Etudiant, Etudiant> param) -> new TableCell<Etudiant,Etudiant>() {
            @Override
            public void updateItem(Etudiant item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    final Button editBtn = new Button("Editer");
                    final Button dltBtn = new Button("Supprimer");
                    dltBtn.setStyle("-fx-background-color:#FF6666");
                    editBtn.setOnAction(event -> {
                        try {
                            etudiant = getTableView().getItems().get(getIndex());
                            etudiant_label.setText("Etudiant: edition");
                            btn_action.setText("Mettre à jour");
                            setTexts(etudiant.getId(),
                                    etudiant.getN_mat_etudiant(),
                                    etudiant.getNom_etudiant(),
                                    etudiant.getPrenom_etudiant(),
                                    etudiant.getClasse_etudiant(),
                                    etudiant.getSerie_etudiant(),
                                    etudiant.getDate_nais_etudiant());
                            action_pane.setVisible(true);
                            new FadeInRight(action_pane).play();
                        } catch (NullPointerException e) {
                            popUp.error("Information", "Selectionner un champ avant de cliquer sur editer. Merci");
                        }
                    });
                    dltBtn.setOnAction(event -> {
                        action_pane.setVisible(false);
                        try {
                            dao.delete(getTableView().getItems().get(getIndex()).getId(), "etudiants", "id");
                            refresh();
                            } catch (java.sql.SQLIntegrityConstraintViolationException exception ) {
                            popUp.error("information", "Erreur durant le suppression, etudiant en cours d'utilisation");
                        } catch (SQLException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    HBox hb = new HBox();
                    hb.setSpacing(2);
                    hb.setStyle("-fx-alignment:center");
                    hb.getChildren().addAll(editBtn, dltBtn);
                    setGraphic(hb);
                    setText(null);
                }
            }
        };
        action_column.setCellFactory(newColumn);
        // fin creation de bouton

        btn_action.setOnAction(event -> {
            if (btn_action.getText().equals("Ajouter")){
                try {
                    etudiant = new Etudiant(0,
                            numero_matricule_input.getText(),
                            nom_input.getText(),
                            prenom_input.getText(),
                            classe_input.getValue().toString(),
                            serie_input.getValue().toString(),
                            date_nais_picker.getValue().toString());
                    dao.insert(etudiant);
                    new FadeOutRight(action_pane).play();
                    refresh();
                    clearInputs();
                } catch (Exception e) {
                    popUp.error("erreur","Erreur, essaye encore une fois");
                }
            } else if (btn_action.getText().equals("Mettre à jour")){
                etudiant = new Etudiant(Integer.valueOf(id_label.getText()),
                        numero_matricule_input.getText(),
                        nom_input.getText(),
                        prenom_input.getText(),
                        classe_input.getValue().toString(),
                        serie_input.getValue().toString(),
                        date_nais_picker.getValue().toString());
                try {
                    dao.update(etudiant);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                new FadeOutRight(action_pane).play();
                    refresh();
                clearInputs();
            }
        });

    }
    public void setTexts(Integer id, String n_mat_etudiant, String nom_etudiant, String prenom_etudiant, String classe_etudiant, String serie_etudiant, String date_nais_etudiant){
        id_label.setText(id.toString());
        numero_matricule_input.setText(n_mat_etudiant);
        nom_input.setText(nom_etudiant);
        prenom_input.setText(prenom_etudiant);
        classe_input.getSelectionModel().select(classe_etudiant);
        serie_input.getSelectionModel().select(serie_etudiant);
        date_nais_picker.setValue(LocalDate.parse(date_nais_etudiant));
    }
    private void clearInputs() {
        numero_matricule_input.setText("");
        nom_input.setText("");
        prenom_input.setText("");
    }
    public void activerRecherche(){
        FilteredList<Etudiant> filteredList = new FilteredList<>(listEtudiant,a->true);
        recherche_input.textProperty().addListener((Observable,oldValue,newValue) -> filteredList.setPredicate(etudiant -> {
            if (newValue.isEmpty()
                    || etudiant.getNom_etudiant().toUpperCase().contains(newValue.toUpperCase())
                    || etudiant.getPrenom_etudiant().toLowerCase().contains(newValue.toLowerCase())
                    || etudiant.getN_mat_etudiant().toUpperCase().contains(newValue.toUpperCase())
                    || etudiant.getClasse_etudiant().toUpperCase().contains(newValue.toUpperCase())
                    || etudiant.getSerie_etudiant().toUpperCase().contains(newValue.toUpperCase())
            ){
                return true;
            }else return etudiant.getDate_nais_etudiant().contains(newValue);
        }));
        SortedList<Etudiant> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table_etudiant.comparatorProperty());
        table_etudiant.setItems(sortedList);
    }
    public void ajoutEtudiant() {
        action_pane.setVisible(true);
        etudiant_label.setText("Etudiant: Ajout");
        btn_action.setText("Ajouter");
        clearInputs();
        new FadeInRightBig(action_pane).play();
    }
    public void listSerie(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String query = "SELECT abreviation FROM serie";
        try {
            Statement statement = connectDb.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ObservableList<Object> data = FXCollections.observableArrayList();
            while (resultSet.next()){
                data.add(resultSet.getString(1));
            }
            serie_input.setItems(data);
        } catch (SQLException ignored) {
            popUp.error("erreur","Erreur de connection au base de donnée. Veuillez contacter l'administrateur");
        }
    }
    public void check(){
        if (!numero_matricule_input.getText().isEmpty()
                && !nom_input.getText().isEmpty()
                && !prenom_input.getText().isEmpty()
                && date_nais_picker.getValue() !=null
                && serie_input.getValue() != null
                && classe_input.getValue() != null){
            btn_action.setVisible(true);
        } else if (numero_matricule_input.getText().isEmpty()
                || nom_input.getText().isEmpty()
                || prenom_input.getText().isEmpty()
                || date_nais_picker.getValue() ==null
                || serie_input.getValue() == null
                || classe_input.getValue() == null){
            btn_action.setVisible(false);
        }
    }

    public void generatePdf() throws IOException, DocumentException, SQLException {
        Stage stage = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showSaveDialog(stage);

        Document document = new Document();
        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(file + ".pdf")));
        document.open();

        Paragraph header = new Paragraph("test header");
        document.add(header);
        PdfPTable table = new PdfPTable(6);
        PdfPCell nmat = new PdfPCell(new Phrase("NºMatricule\t"));
        table.addCell(nmat);
        PdfPCell nom = new PdfPCell(new Phrase("Nom"));
        table.addCell(nom);
        nom.setColspan(2);
        PdfPCell prenom = new PdfPCell(new Phrase("Prenom"));
        table.addCell(prenom);
        PdfPCell classe = new PdfPCell(new Phrase("Classe"));
        table.addCell(classe);
        PdfPCell serie = new PdfPCell(new Phrase("Serie"));
        table.addCell(serie);
        serie.setColspan(2);
        PdfPCell date_nais = new PdfPCell(new Phrase("Date Nais"));
        table.addCell(date_nais);
        table.setHeaderRows(1);

        DatabaseConnection con = new DatabaseConnection();
        Connection connection = con.getConnection();
        String query = "select n_matricule, nom,prenom,classe,serie,date_nais from etudiants;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            table.addCell(resultSet.getString("n_matricule"));
            table.addCell(resultSet.getString("nom"));
            table.addCell(resultSet.getString("prenom"));
            table.addCell(resultSet.getString("classe"));
            table.addCell(resultSet.getString("serie"));
            table.addCell(resultSet.getString("date_nais"));
        }
        document.addTitle("Liste des Etudiants");
        document.add(table);
        popUp.success("Success", "Creation de PDF avec success au :"+file+".pdf");
        document.close();
    }
}
