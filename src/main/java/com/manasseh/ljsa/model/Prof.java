package com.manasseh.ljsa.model;

import java.time.LocalDate;

public class Prof {
    private final Integer id;
    private final String n_mat;
    private final String nom_prof;
    private final String prenom_prof;
    private final LocalDate date_nais;
    private final LocalDate date_prise_service;
    private final LocalDate date_cessation_service;

    public Prof(Integer id, String n_mat, String nom_prof, String prenom_prof, LocalDate date_nais, LocalDate date_prise_service, LocalDate date_cessation_service) {
        this.id = id;
        this.n_mat = n_mat;
        this.nom_prof = nom_prof;
        this.prenom_prof = prenom_prof;
        this.date_nais = date_nais;
        this.date_prise_service = date_prise_service;
        this.date_cessation_service = date_cessation_service;
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

    public LocalDate getDate_prise_service() {
        return date_prise_service;
    }

    public LocalDate getDate_cessation_service() {
        return date_cessation_service;
    }
}
