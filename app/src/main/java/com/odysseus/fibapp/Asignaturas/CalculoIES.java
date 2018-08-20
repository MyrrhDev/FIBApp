package com.odysseus.fibapp.Asignaturas;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CalculoIES extends RealmObject {

    @PrimaryKey
    private String name;
    private double control1;
    private double control2;
    private double fhc1;
    private double fhc2;
    private double fhc3;
    private double participacion;
    static double notafinal;

    public CalculoIES() {

    }


    public static void calculate(double control1, double control2, double fhc1, double fhc2, double fhc3, double participacion) {
        notafinal = (control1*0.10) + (control2*0.15) + (fhc1*0.25) + (fhc2*0.15) + (fhc3*0.25) +(participacion*0.1);
    }

    public static double getNotafinal() {
        return notafinal;
    }

    public void setNotafinal(double notafinal) {
        this.notafinal = notafinal;
    }

    public double getControl1() {
        return control1;
    }

    public void setControl1(double control1) {
        this.control1 = control1;
    }

    public double getControl2() {
        return control2;
    }

    public void setControl2(double control2) {
        this.control2 = control2;
    }

    public double getFhc1() {
        return fhc1;
    }

    public void setFhc1(double fhc1) {
        this.fhc1 = fhc1;
    }

    public double getFhc2() {
        return fhc2;
    }

    public void setFhc2(double fhc2) {
        this.fhc2 = fhc2;
    }

    public double getFhc3() {
        return fhc3;
    }

    public void setFhc3(double fhc3) {
        this.fhc3 = fhc3;
    }

    public double getParticipacion() {
        return participacion;
    }

    public void setParticipacion(double participacion) {
        this.participacion = participacion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
