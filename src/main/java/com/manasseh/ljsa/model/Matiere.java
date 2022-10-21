package com.manasseh.ljsa.model;

public class Matiere {
   private final Integer id;
   private final String designation;
   private final String abreviation;
   private final String description;

    public Matiere(Integer id, String designation, String abreviation, String description) {
        this.id = id;
        this.designation = designation;
        this.abreviation = abreviation;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public String getDescription() {
        return description;
    }
}
