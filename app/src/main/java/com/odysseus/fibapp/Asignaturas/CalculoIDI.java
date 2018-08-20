package com.odysseus.fibapp.Asignaturas;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CalculoIDI extends RealmObject {

    @PrimaryKey
    private String name;
    private double control1;
    private double control2;

    private double lab;
    static double notafinal;

    public CalculoIDI() {

    }

    public static void calculate(double control1, double control2, double lab) {
        notafinal = (control1*0.25) + (control2*0.5) + (lab*0.25);
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

    public double getLab() {
        return lab;
    }

    public void setLab(double lab) {
        this.lab = lab;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
