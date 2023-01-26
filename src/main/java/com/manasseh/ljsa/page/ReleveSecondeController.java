package com.manasseh.ljsa.page;

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
import com.manasseh.ljsa.DAO.EtudiantDAO;
import com.manasseh.ljsa.DAO.SecondeDAO;
import com.manasseh.ljsa.model.Etudiant;
import com.manasseh.ljsa.model.Seconde;
import com.manasseh.ljsa.utils.AnneeLists;
import com.manasseh.ljsa.utils.AutoCompleteComboBoxListener;
import com.manasseh.ljsa.utils.PopUp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.itextpdf.io.font.constants.StandardFonts.TIMES_BOLD;
import static com.itextpdf.io.font.constants.StandardFonts.TIMES_ROMAN;

public class ReleveSecondeController implements Initializable {
    public Label releve_label;
    public TableColumn<Seconde, String> n_mat_column;
    public TableColumn<Seconde, String> mlg_column;
    public TableColumn<Seconde, String> frs_column;
    public TableColumn<Seconde, String> ang_column;
    public TableColumn<Seconde, String> hg_column;
    public TableColumn<Seconde, String> math_column;
    public TableColumn<Seconde, String> phys_column;
    public TableColumn<Seconde, String> svt_column;
    public TableColumn<Seconde, String> ses_column;
    public TableColumn<Seconde, String> eps_column;
    public TableColumn<Seconde, String> trimestre_column;
    public TableColumn<Seconde, String> total_column;
    public TableColumn<Seconde, String> moyenne_column;
    public TableColumn<Seconde, String> annee_column;
    public TableColumn<Seconde, String> action_column;
    public TableColumn<Seconde, String> eac_column;
    public TableColumn<Seconde, String> tice_column;
    public TableView<Seconde> seconde_table;
    public ComboBox<Object> nmat_input;
    public ComboBox<String> annee_debut;
    public ComboBox<String> annee_fin;

    EtudiantDAO etudiantDAO = new EtudiantDAO();
    SecondeDAO secondeDAO = new SecondeDAO();
    Seconde seconde = null;
    ObservableList<Seconde> list = FXCollections.observableArrayList();
    PopUp popUp = new PopUp();
    Etudiant etudiant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nmat_input.setItems(etudiantDAO.listSeconde());
        new AutoCompleteComboBoxListener<>(nmat_input);
        new AutoCompleteComboBoxListener<>(annee_debut);
        annee_debut.getItems().addAll(AnneeLists.getYearList(100));
        new AutoCompleteComboBoxListener<>(annee_fin);
        annee_fin.getItems().addAll(AnneeLists.getYearList(100));
        list.setAll(secondeDAO.listAll());
        seconde_table.setItems(list);

        n_mat_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getN_mat()));
        ang_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnglais().toString()));
        mlg_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMalagasy().toString()));
        hg_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHistogeo().toString()));
        eac_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEac().toString()));
        eps_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEps().toString()));
        ses_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSes().toString()));
        math_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMats().toString()));
        phys_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSpc().toString()));
        svt_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSvt().toString()));
        frs_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFrancais().toString()));
        tice_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTice().toString()));
        total_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTotal().toString()));
        trimestre_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTrimestre().toString()));
        annee_column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnnee_scolaire().toString()));
        moyenne_column.setCellValueFactory(param -> {
            try {
                return new SimpleStringProperty(param.getValue().getMoyenne());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void confirmerPdf() throws IOException {
        if (check()){
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
    }

    public void generatePdf() throws IOException{
        try {
            Stage stage = new Stage();
            FileChooser fil_chooser = new FileChooser();
            File file = fil_chooser.showSaveDialog(stage);

            // creating the document
            PdfWriter writer = new PdfWriter(file+".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Creating a table
            float [] pointColumnWidths = {35F,35F,35F,35F,35F,35F,35F,35F,35F,35F,35F,35F,35F,35F};
            Table table = new Table(pointColumnWidths);

            // font setting
            PdfFont font = PdfFontFactory.createFont(TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(TIMES_BOLD);

            Text title = new Text("Listes des notes en seconde").setFont(font);
            Text de = new Text("du Mr ").setFont(font);
            etudiant = etudiantDAO.getByNmat(nmat_input.getValue().toString());
            Paragraph nom = new Paragraph(etudiant.getNom_etudiant()).setFont(font);
            nom.setTextAlignment(TextAlignment.RIGHT);
            Paragraph prenom = new Paragraph(etudiant.getPrenom_etudiant()).setFont(font);
            prenom.setTextAlignment(TextAlignment.RIGHT);
            Text ecole = new Text("Lycée Joël Sylvain").setFont(font);
            Paragraph p = new Paragraph().add(title);
            Paragraph d = new Paragraph().add(de);
            Paragraph e  = new Paragraph().add(ecole);
            e.setTextAlignment(TextAlignment.RIGHT);
            p.setTextAlignment(TextAlignment.RIGHT);
            d.setTextAlignment(TextAlignment.RIGHT);
            document.add(p);
            document.add(d);
            document.add(nom);
            document.add(prenom);
            document.add(e);

            // Adding cells to the table
//            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("NºMatricule").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(14)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("MLG").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("FRS").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("ANG").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("HG").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("MATH").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("PC").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("SVT").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("EAC").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("TICE").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("SES").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("EPS").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("TOT").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("MOY").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("TRI").setFont(bold).setTextAlignment(TextAlignment.CENTER).setFontSize(12)));

            // set content of tablePdf from our tableView
            for (int i = 0; i<seconde_table.getItems().size();i++){
                seconde = seconde_table.getItems().get(i);
//                table.addCell(seconde.getN_mat().toUpperCase()).setFont(font);
                table.addCell(String.valueOf(seconde.getMalagasy())).setFont(font);
                table.addCell(String.valueOf(seconde.getFrancais())).setFont(font);
                table.addCell(String.valueOf(seconde.getAnglais())).setFont(font);
                table.addCell(String.valueOf(seconde.getHistogeo())).setFont(font);
                table.addCell(String.valueOf(seconde.getMats())).setFont(font);
                table.addCell(String.valueOf(seconde.getSpc())).setFont(font);
                table.addCell(String.valueOf(seconde.getSvt())).setFont(font);
                table.addCell(String.valueOf(seconde.getEac())).setFont(font);
                table.addCell(String.valueOf(seconde.getTice())).setFont(font);
                table.addCell(String.valueOf(seconde.getSes())).setFont(font);
                table.addCell(String.valueOf(seconde.getEps())).setFont(font);
                table.addCell(String.valueOf(seconde.getTotal())).setFont(font);
                table.addCell(seconde.getMoyenne().toUpperCase()).setFont(font);
                table.addCell(String.valueOf(seconde.getTrimestre())).setFont(font);
            }
            table.setPaddingTop(200F);
//            Paragraph tot = new Paragraph("Total: "+ (long) seconde_table.getItems().size()).setFont(font);
            Paragraph annee = new Paragraph("Annee: "+annee_debut.getValue()+" au : "+annee_fin.getValue()).setFont(font);
//            document.add(tot);
            document.add(annee);
            document.add(table);

            // Closing the document
            document.close();
            if (file.getAbsolutePath().equals("null")){
                System.out.println("the path is null");
            } else popUp.success("Sauvegardé dans :", file +".pdf");
        } catch (java.lang.RuntimeException e){
            System.out.println("Runtime E: path null");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void filtrer() {
        if (check()){
            list.clear();
            list.setAll(secondeDAO.listAllFIlter(Integer.valueOf(annee_debut.getValue()), Integer.valueOf(annee_fin.getValue()), nmat_input.getValue().toString()));
        }
    }
    public boolean check(){
        if (nmat_input.getValue()==null){
            popUp.error("erreur", "Veuillez selectionner un numero matricule");
            return false;
        } else if (annee_debut.getValue()==null) {
            popUp.error("erreur", "Veuillez selectionner un annee de debut");
            return false;
        } else if (annee_fin.getValue()==null){
            popUp.error("erreur", "Veuillez selectionner un annee fin");
            return false;
        }
        return true;
    }
}
