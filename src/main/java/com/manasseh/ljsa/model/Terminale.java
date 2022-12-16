package com.manasseh.ljsa.model;

import com.manasseh.ljsa.DAO.TerminaleDAO;
import com.manasseh.ljsa.page.TerminaleController;

import java.sql.SQLException;
import java.text.DecimalFormat;

public class Terminale extends TerminaleController {
    private final Integer id;
    private final Float malagasy;
    private final Float frs;
    private final Float anglais;
    private final Float histoGeo;
    private final Float phylosphie;
    private final Float eps;
    private final Float mathematique;
    private final Float spc;
    private final Float svt;
    private final Float ses;
    private final String n_mat;
    private final Integer trimestre;
    private final Integer annee_scolaire;
    TerminaleDAO terminaleDAO = new TerminaleDAO();

    public Terminale(Integer id, Float malagasy, Float frs, Float anglais, Float histoGeo, Float phylosphie, Float eps, Float mathematique, Float spc, Float svt, Float ses, String n_mat, Integer trimestre, Integer annee_scolaire) {
        this.id = id;
        this.malagasy = malagasy;
        this.frs = frs;
        this.anglais = anglais;
        this.histoGeo = histoGeo;
        this.phylosphie = phylosphie;
        this.eps = eps;
        this.mathematique = mathematique;
        this.spc = spc;
        this.svt = svt;
        this.ses = ses;
        this.n_mat = n_mat;
        this.trimestre = trimestre;
        this.annee_scolaire = annee_scolaire;
    }
    public Float getTotal(){
        return (this.malagasy +
        this.frs +
        this.anglais +
        this.histoGeo +
        this.phylosphie +
        this.eps +
        this.mathematique +
        this.spc +
        this.svt +
        this.ses);
    }
    public String getMoyenne() throws SQLException {
        DecimalFormat df = new DecimalFormat("###.##");
        int coeff = terminaleDAO.getCoeffTotal();
        Float sum = getTotal()/coeff;
        return df.format(sum);
    }
    public Integer getId() {
        return id;
    }

    public Float getMalagasy() {
        return malagasy;
    }

    public Float getFrs() {
        return frs;
    }

    public Float getAnglais() {
        return anglais;
    }

    public Float getHistoGeo() {
        return histoGeo;
    }

    public Float getPhylosphie() {
        return phylosphie;
    }

    public Float getEps() {
        return eps;
    }

    public Float getMathematique() {
        return mathematique;
    }

    public Float getSpc() {
        return spc;
    }

    public Float getSvt() {
        return svt;
    }

    public Float getSes() {
        return ses;
    }

    public String getN_mat() {
        return n_mat;
    }

    public Integer getTrimestre() {
        return trimestre;
    }

    public Integer getAnnee_scolaire() {
        return annee_scolaire;
    }
}
