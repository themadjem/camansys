package com.themadjem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;

public class Herd {

    @SerializedName("cows")
    @Expose
    private ArrayList<Cow> cows = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     */
    public Herd() {
    }

    /**
     * @param cows list of cows
     */
    public Herd(ArrayList<Cow> cows) {
        super();
        this.cows = cows;

    }

    public ArrayList<Cow> getCows() {
        return cows;
    }

    public void addCow(Cow newCow) {
        cows.add(newCow);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cows", cows).toString();
    }

}
