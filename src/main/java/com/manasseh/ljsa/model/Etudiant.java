package com.manasseh.ljsa.model;

public class Etudiant {
    public Integer id;
    public String n_mat_etudiant;
    public String nom_etudiant;
    public String prenom_etudiant;
    public String classe_etudiant;
    public String serie_etudiant;
    public String date_nais_etudiant;

    public Etudiant(Integer id, String n_mat_etudiant, String nom_etudiant, String prenom_etudiant, String classe_etudiant, String serie_etudiant, String date_nais_etudiant) {
        this.id = id;
        this.n_mat_etudiant = n_mat_etudiant;
        this.nom_etudiant = nom_etudiant;
        this.prenom_etudiant = prenom_etudiant;
        this.classe_etudiant = classe_etudiant;
        this.serie_etudiant = serie_etudiant;
        this.date_nais_etudiant = date_nais_etudiant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getN_mat_etudiant() {
        return n_mat_etudiant;
    }

    public void setN_mat_etudiant(String n_mat_etudiant) {
        this.n_mat_etudiant = n_mat_etudiant;
    }

    public String getNom_etudiant() {
        return nom_etudiant;
    }

    public void setNom_etudiant(String nom_etudiant) {
        this.nom_etudiant = nom_etudiant;
    }

    public String getPrenom_etudiant() {
        return prenom_etudiant;
    }

    public void setPrenom_etudiant(String prenom_etudiant) {
        this.prenom_etudiant = prenom_etudiant;
    }

    public String getClasse_etudiant() {
        return classe_etudiant;
    }

    public void setClasse_etudiant(String classe_etudiant) {
        this.classe_etudiant = classe_etudiant;
    }

    public String getSerie_etudiant() {
        return serie_etudiant;
    }

    public void setSerie_etudiant(String serie_etudiant) {
        this.serie_etudiant = serie_etudiant;
    }

    public String getDate_nais_etudiant() {
        return date_nais_etudiant;
    }

    public void setDate_nais_etudiant(String date_nais_etudiant) {
        this.date_nais_etudiant = date_nais_etudiant;
    }
}
