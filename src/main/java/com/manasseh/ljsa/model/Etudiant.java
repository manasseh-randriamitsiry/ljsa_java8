package com.manasseh.ljsa.model;

public class Etudiant {
    private Integer id;
    private final String n_mat_etudiant;
    private final String nom_etudiant;
    private final String prenom_etudiant;
    private final String classe_etudiant;
    private final String serie_etudiant;
    private final String date_nais_etudiant;

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

    public String getNom_etudiant() {
        return nom_etudiant;
    }

    public String getPrenom_etudiant() {
        return prenom_etudiant;
    }

    public String getClasse_etudiant() {
        return classe_etudiant;
    }

    public String getSerie_etudiant() {
        return serie_etudiant;
    }

    public String getDate_nais_etudiant() {
        return date_nais_etudiant;
    }

}
