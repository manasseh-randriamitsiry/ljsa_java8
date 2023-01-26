package com.manasseh.ljsa.model;

public class Classe {
    public Integer id;
    public String classe;
    public Integer effectif;
    public Integer level;

    public Classe(Integer id, String classe, Integer effectif,Integer level) {
        this.id = id;
        this.classe = classe;
        this.effectif = effectif;
        this.level = level;
    }

    public Integer getLevel() {
        return level;
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
