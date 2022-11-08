package com.manasseh.ljsa.page;

import animatefx.animation.FadeInRight;
import animatefx.animation.FadeInRightBig;
import animatefx.animation.FadeOutRight;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.manasseh.ljsa.DAO.ClasseDAO;
import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.model.Etudiant;
import com.manasseh.ljsa.utils.PopUp;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import static com.itextpdf.io.font.constants.StandardFonts.TIMES_BOLD;
import static com.itextpdf.io.font.constants.StandardFonts.TIMES_ROMAN;

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
    public TableColumn<Etudiant, String> date_nais_column;
    public TableColumn<Etudiant,Etudiant> action_column;
    public TextField recherche_input;
    public Label etudiant_label;
    public ComboBox<Object> classe_input;
    public Label id_label;
    public Pane pane_etudiant;
    EtudiantDAO dao = new EtudiantDAO();
    ClasseDAO classeDAO = new ClasseDAO();
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
        refresh();
        new FadeOutRight(action_pane).play();
        classe_input.setItems(classeDAO.listClasse());
        numero_matricule_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getN_mat_etudiant()));
        nom_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNom_etudiant()));
        prenom_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPrenom_etudiant()));
        classe_column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getClasse_etudiant()));
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
                            date_nais_picker.getValue().toString());
                    System.out.println(classe_input.getValue().toString());
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
    public void setTexts(Integer id, String n_mat_etudiant, String nom_etudiant, String prenom_etudiant, String classe_etudiant, String date_nais_etudiant){
        id_label.setText(id.toString());
        numero_matricule_input.setText(n_mat_etudiant);
        nom_input.setText(nom_etudiant);
        prenom_input.setText(prenom_etudiant);
        classe_input.getSelectionModel().select(classe_etudiant);
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

    public void check(){
        if (!numero_matricule_input.getText().isEmpty()
                && !nom_input.getText().isEmpty()
                && !prenom_input.getText().isEmpty()
                && date_nais_picker.getValue() !=null
                && classe_input.getValue() != null){
            btn_action.setVisible(true);
        } else if (numero_matricule_input.getText().isEmpty()
                || nom_input.getText().isEmpty()
                || prenom_input.getText().isEmpty()
                || date_nais_picker.getValue() ==null
                || classe_input.getValue() == null){
            btn_action.setVisible(false);
        }
    }

    public void confirmerPdf() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Noter bien que seul les valeurs sur la table seront enregistré dans le pdf");

        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Compris");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Annuler");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            generatePdf();
        }
    }

    public void generatePdf() throws IOException {
        Stage stage = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showSaveDialog(stage);

        // creating the document
        PdfWriter writer = new PdfWriter(file+".pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Creating a table
        float [] pointColumnWidths = {60F, 175F, 175F,50F,70F};
        Table table = new Table(pointColumnWidths);

        // font setting
        PdfFont font = PdfFontFactory.createFont(TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(TIMES_BOLD);

        Text title = new Text("The Strange Case of Dr. Jekyll and Mr. Hyde").setFont(font);
        Text author = new Text("Robert Louis Stevenson").setFont(font);
        Paragraph p = new Paragraph().add(title).add(" by ").add(author);
        p.setTextAlignment(TextAlignment.RIGHT);
        document.add(p);

        // Adding cells to the table
        table.addCell(new Cell().add(new Paragraph("NºMAT").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(14)));
        table.addCell(new Cell().add(new Paragraph("NOM").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(14)));
        table.addCell(new Cell().add(new Paragraph("PRENOM").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(14)));
        table.addCell(new Cell().add(new Paragraph("CLASSE").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(14)));
        table.addCell(new Cell().add(new Paragraph("D_NAIS").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(14)));

        // set content of tablePdf from our tableView
        for (int i = 0; i<table_etudiant.getItems().size();i++){
            etudiant = table_etudiant.getItems().get(i);
            table.addCell(etudiant.getN_mat_etudiant()).setFont(font);
            table.addCell(etudiant.getNom_etudiant()).setFont(font);
            table.addCell(etudiant.getPrenom_etudiant()).setFont(font);
            table.addCell(etudiant.getClasse_etudiant()).setFont(font);
            table.addCell(etudiant.getDate_nais_etudiant()).setFont(font);
        }
        table.setPaddingTop(200F);
        Paragraph tot = new Paragraph("Total:"+ (long) table_etudiant.getItems().size());
        document.add(tot);
        document.add(table);

        // Closing the document
        document.close();
        popUp.success("Sauvegardé", file +"pdf");

    }


//    public void importPdf(){
//        Stage stage = new Stage();
//        FileChooser fil_chooser = new FileChooser();
//        File file = fil_chooser.showOpenDialog(stage);
//        try {
//            //Create PdfReader instance.
//            PdfReader pdfReader = new PdfReader(file.toURL());
//            //Get the number of pages in pdf.
//            int pages = pdfReader.getNumberOfPages();
//            //Iterate the pdf through pages.
//            for(int i=1; i<=pages; i++) {
////                Extract the page content using PdfTextExtractor.
//                String pageContent =
//                        PdfTextExtractor.getTextFromPage(pdfReader, i);
////                Print the page content on console.
//                System.out.println("Content on Page "
//                        + i + ": " + pageContent);
////                System.out.println(pageContent.);
//                ByteArrayOutputStream bao = new ByteArrayOutputStream();
//
//                etudiant = new Etudiant(0,String.valueOf(pageContent.indexOf(6)),String.valueOf(pageContent.indexOf(7)),String.valueOf(pageContent.indexOf(8)),String.valueOf(pageContent.indexOf(9)),String.valueOf(pageContent.indexOf(10)));
//                dao.insert(etudiant);
//            }
////            Close the PdfReader.
//            pdfReader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
