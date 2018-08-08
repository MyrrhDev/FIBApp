package com.odysseus.fibapp.Asignaturas;

public class CalculoIES {

    private double control1;
    private double control2;
    private double control3;
    private double lab;
    static double notafinal;

    public static void calculate(double control1, double control2, double control3, double lab) {
        notafinal = (control1*0.15) + (control2*0.25) + (control3*0.4) + (lab*0.2);
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

    public double getControl3() {
        return control3;
    }

    public void setControl3(double control3) {
        this.control3 = control3;
    }

    public double getLab() {
        return lab;
    }

    public void setLab(double lab) {
        this.lab = lab;
    }

}
