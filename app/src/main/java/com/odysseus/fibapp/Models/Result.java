package com.odysseus.fibapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odysseus.fibapp.R;

public class Result {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("guia")
    @Expose
    private String guia;
    @SerializedName("grup")
    @Expose
    private String grup;
    @SerializedName("sigles")
    @Expose
    private String sigles;
    @SerializedName("codi_upc")
    @Expose
    private int codiUpc;
    @SerializedName("semestre")
    @Expose
    private String semestre;
    @SerializedName("credits")
    @Expose
    private double credits;
    @SerializedName("nom")
    @Expose
    private String nom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }

    public String getSigles() {
        return sigles;
    }

    public void setSigles(String sigles) {
        this.sigles = sigles;
    }

    public int getCodiUpc() {
        return codiUpc;
    }

    public void setCodiUpc(int codiUpc) {
        this.codiUpc = codiUpc;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



}