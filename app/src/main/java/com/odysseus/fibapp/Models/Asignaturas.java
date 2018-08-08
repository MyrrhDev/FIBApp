package com.odysseus.fibapp.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Asignaturas {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}