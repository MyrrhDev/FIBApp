package com.odysseus.fibapp.Asignaturas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.odysseus.fibapp.R;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CalculoCI extends RealmObject {

    @PrimaryKey
    private String name;
    private double control1;
    private double control2;
    private double control3;
    private double lab;
    static double notafinal;

    public void calculate(double control1, double control2, double control3, double lab) {
        notafinal = ((((control1 + control2 + control3)/3)*0.7) + (lab*0.3));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
