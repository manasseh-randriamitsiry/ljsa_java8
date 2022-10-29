package com.manasseh.ljsa.model;

public class Classe {
    public Integer id;
    public String classe;
    public Integer totalCoeff;

    public Classe(Integer id, String classe, Integer totalCoeff) {
        this.id = id;
        this.classe = classe;
        this.totalCoeff = totalCoeff;
    }

    public String getClasse() {
        return classe;
    }

    public Integer getTotalCoeff() {
        return totalCoeff;
    }

    public Integer getId() {
        return id;
    }
}
