package com.manasseh.ljsa.model;

import java.time.LocalDate;

public class Prof {
    private final Integer id;
    private final String n_mat;
    private final String nom_prof;
    private final String prenom_prof;
    private final LocalDate date_nais;

    public Prof(Integer id, String n_mat, String nom_prof, String prenom_prof, LocalDate date_nais) {
        this.id = id;
        this.n_mat = n_mat;
        this.nom_prof = nom_prof;
        this.prenom_prof = prenom_prof;
        this.date_nais = date_nais;
    }

    public Integer getId() {
        return id;
    }

    public String getN_mat() {
        return n_mat;
    }

    public String getNom_prof() {
        return nom_prof;
    }

    public String getPrenom_prof() {
        return prenom_prof;
    }

    public LocalDate getDate_nais() {
        return date_nais;
    }
}
