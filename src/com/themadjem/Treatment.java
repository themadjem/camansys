package com.themadjem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.themadjem.utils.Util;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class Treatment {
    public static final int AM = 0;
    public static final int PM = 1;


    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("med")
    @Expose
    private String med;
    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("symptoms")
    @Expose
    private String symptoms;
    @SerializedName("admin")
    @Expose
    private String admin;

    /**
     * No args constructor for use in serialization
     */
    public Treatment() {
    }

    /**
     * @param amount   amount of medicine given
     * @param symptoms symptoms shown
     * @param admin    initials of who administered the medicine
     * @param med      medicine given
     * @param date     date given
     * @param time     time given AM (0)/PM (1)
     */
    public Treatment(String date, int time, String med, double amount, String symptoms, String admin) {
        super();
        this.date = date;
        this.time = time;
        this.med = med;
        this.amount = amount;
        this.symptoms = symptoms;
        this.admin = admin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public String getMed() {
        return med;
    }

    public double getAmount() {
        return amount;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getAdmin() {
        return admin;
    }

    public String toHerdString(Cow cow) {
        return String.format("%s | %s  | %s\t|%s\t|%s", admin, cow.getFormattedTag(), med, amount, symptoms);
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder("").append(admin).append(" | ")
                .append(Util.formatDate(LocalDate.parse(date)))
                .append(' ');
        if (time == AM) s.append("AM");
        else s.append("PM");
        s.append(" | ").append(med).append("\t|").append(amount).append("\t|").append(symptoms);
        return s.toString();
    }
}
