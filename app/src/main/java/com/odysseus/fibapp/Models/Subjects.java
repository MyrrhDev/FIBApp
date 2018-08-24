package com.odysseus.fibapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subjects {

    @SerializedName("codi_assig")
    @Expose
    private String codiAssig;
    @SerializedName("grup")
    @Expose
    private String grup;
    @SerializedName("dia_setmana")
    @Expose
    private Integer diaSetmana;
    @SerializedName("inici")
    @Expose
    private String inici;
    @SerializedName("durada")
    @Expose
    private Integer durada;
    @SerializedName("tipus")
    @Expose
    private String tipus;
    @SerializedName("aules")
    @Expose
    private String aules;

    public String getCodiAssig() {
        return codiAssig;
    }

    public void setCodiAssig(String codiAssig) {
        this.codiAssig = codiAssig;
    }

    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }

    public Integer getDiaSetmana() {
        return diaSetmana;
    }

    public void setDiaSetmana(Integer diaSetmana) {
        this.diaSetmana = diaSetmana;
    }

    public String getInici() {
        return inici;
    }

    public void setInici(String inici) {
        this.inici = inici;
    }

    public Integer getDurada() {
        return durada;
    }

    public void setDurada(Integer durada) {
        this.durada = durada;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getAules() {
        return aules;
    }

    public void setAules(String aules) {
        this.aules = aules;
    }

}