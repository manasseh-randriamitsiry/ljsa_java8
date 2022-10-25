package com.manasseh.ljsa.model;

import java.text.DecimalFormat;

public class Premiere {
    private final Integer id;
    private final Float malagasy;
    private final Float francais;
    private final Float anglais;
    private final Float histogeo;
    private final Float eac;
    private final Float ses;
    private final Float spc;
    private final Float svt;
    private final Float mats;
    private final Float eps;
    private final Float tice;
    private final Float phylo;
    private final String n_mat;
    private final Integer trimestre;
    private final Integer annee_scolaire;

    public Premiere(Integer id, Float malagasy, Float francais, Float anglais, Float histogeo, Float eac, Float ses, Float spc, Float svt, Float mats, Float eps, Float tice,Float phylo, String n_mat, Integer trimestre, Integer annee_scolaire) {
        this.id = id;
        this.malagasy = malagasy;
        this.francais = francais;
        this.anglais = anglais;
        this.histogeo = histogeo;
        this.eac = eac;
        this.spc = spc;
        this.svt = svt;
        this.mats = mats;
        this.eps = eps;
        this.tice = tice;
        this.phylo = phylo;
        this.ses = ses;
        this.n_mat = n_mat;
        this.trimestre = trimestre;
        this.annee_scolaire = annee_scolaire;
    }

    public Integer getId() {
        return id;
    }

    public Float getSes() {
        return ses;
    }

    public Float getMalagasy() {
        return malagasy;
    }

    public Float getFrancais() {
        return francais;
    }

    public Float getAnglais() {
        return anglais;
    }

    public Float getHistogeo() {
        return histogeo;
    }

    public Float getEac() {
        return eac;
    }

    public Float getSpc() {
        return spc;
    }
    public Float getTotal(){
        return this.malagasy +
        this.francais+
        this.anglais +
        this.histogeo +
        this.eac +
        this.spc +
        this.svt +
        this.mats +
        this.eps +
        this.tice +
        this.phylo +
        this.ses ;
    }
    public String getMoy(){
        DecimalFormat df = new DecimalFormat("###.##");
        float sum = (this.malagasy +
                this.francais+
                this.anglais +
                this.histogeo +
                this.eac +
                this.spc +
                this.svt +
                this.mats +
                this.eps +
                this.tice +
                this.phylo +
                this.ses )/25;
        return df.format(sum);
    }



    public Float getSvt() {
        return svt;
    }

    public Float getMats() {
        return mats;
    }

    public Float getEps() {
        return eps;
    }

    public Float getTice() {
        return tice;
    }

    public String getN_mat() {
        return n_mat;
    }

    public Integer getTrimestre() {
        return trimestre;
    }

    public Float getPhylo() {
        return phylo;
    }

    public Integer getAnnee_scolaire() {
        return annee_scolaire;
    }
}
