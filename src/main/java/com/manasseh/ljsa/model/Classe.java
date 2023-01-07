package com.manasseh.ljsa.model;

public class Classe {
    public Integer id;
    public String classe;
    public Integer effectif;

    public Classe(Integer id, String classe, Integer effectif) {
        this.id = id;
        this.classe = classe;
        this.effectif = effectif;
    }

    public String getClasse() {
        return classe;
    }

    public Integer getEffectif() {
        return effectif;
    }

    public Integer getId() {
        return id;
    }
}
