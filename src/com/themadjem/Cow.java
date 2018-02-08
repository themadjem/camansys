package com.themadjem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.themadjem.fx.LeftSheet;
import com.themadjem.utils.Constants;
import com.themadjem.utils.Util;
import org.apache.commons.lang3.builder.ToStringBuilder;
import themadjem.util.Output;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Cow {

    public static final String TRANS_BARN = "Transition Barn";
    public static final String CALF_BARN = "Calf Barn";
    @SerializedName("tagNumber")
    @Expose
    private int tagNumber;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("leftDate")
    @Expose
    private String leftDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bools")
    @Expose
    private int bools;
    @SerializedName("treatments")
    @Expose
    private List<Treatment> treatments;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("addedBy")
    @Expose
    private String addedBy;

    /**
     * No args constructor for use in serialization
     */
    public Cow() {
    }

    /**
     * Constructor used when creating a Cow Object from a JSON file
     *
     * @param tagNumber  tag number
     * @param dOB        birthday
     * @param name       name
     * @param leftDate   date the calf left the herd (exp/sold)
     * @param status     living status
     * @param bools      boolean values for left, weaned, dehorned,
     *                   colostrum1, colostrum2, ecolizer, inforce,
     *                   calfguard, multimin, bovishield, and vision7
     * @param treatments list of treatments
     * @param notes      notes
     * @param addedBy    who added
     */
    public Cow(
            int tagNumber,
            String dOB,
            String name,
            String leftDate,
            String status,
            int bools,
            List<Treatment> treatments,
            String notes,
            String addedBy
    ) {
        super();
        this.tagNumber = tagNumber;
        this.name = name;
        this.dOB = dOB;
        this.leftDate = leftDate;
        this.status = status;
        this.bools = bools;
        this.treatments = treatments;
        this.notes = notes;
        this.addedBy = addedBy;
    }

    /**
     * Used by the NewCalf
     *
     * @param tagNumber     param_thing
     * @param dOB           param_thing
     * @param vacEcolizer   param_thing
     * @param vacCalfGuard  param_thing
     * @param vacInforce    param_thing
     * @param vacMultimin   param_thing
     * @param vacBoviShield param_thing
     * @param addedBy       param_thing
     */
    public Cow(
            int tagNumber,
            String dOB,
            boolean vacEcolizer,
            boolean vacCalfGuard,
            boolean vacInforce,
            boolean vacMultimin,
            String addedBy
    ) {
        this.tagNumber = tagNumber;
        this.name = "";
        this.bools = 0;
        this.dOB = dOB;
        this.status = CALF_BARN;
        this.treatments = new ArrayList<>();
        this.leftDate = "";
        this.notes = "";
        this.addedBy = addedBy;
        setEcolizer(vacEcolizer);
        setCalfGuard(vacCalfGuard);
        setVacInforce(vacInforce);
        setVacMultimin(vacMultimin);
    }

    public long getAge_Days() {
        if (hasLeft())
            return getDOB().until(getLeftDate(), ChronoUnit.DAYS);
        else
            return getDOB().until(LocalDate.now(), ChronoUnit.DAYS);
    }

    public LocalDate getLeftDate() {
        try {
            return LocalDate.parse(leftDate);
        } catch (Exception e) {
            Output.infoBox("Error parsing Left Date!", "ERROR");
            return LocalDate.now();
        }
    }

    public void setLeftDate(String leftDate) {
        this.leftDate = leftDate;
    }

    public int getTagNumber() {
        return tagNumber;
    }

    public LocalDate getDOB() {
        return LocalDate.parse(dOB);
    }

    /*
     * Retrieves date of birth for reference
     * e34d to 8 weeks after dob (8 weeks on whole milk)
     * e12d to 10 weeks after dob (2 weeks on 3/4 milk)
     * e14d to 11 weeks after dob (1 week on 1/2 milk)
     * ewd to 12 weeks after dob (1 week on 1/4 milk)
     * emd to 13 weeks after dob (1 week on water)
     * moved to transition at 14 weeks old
     */

    public String getName() {
        return name;
    }

    /**
     * @return 8 weeks after dob (8 weeks on whole milk)
     */
    public LocalDate getEst34Date() {
        return getDOB().plusWeeks(8);
    }

    /**
     * @return 10 weeks after dob (2 weeks on 3/4 milk)
     */
    public LocalDate getEst12Date() {
        return getDOB().plusWeeks(10);
    }

    /**
     * @return 11 weeks after dob (1 week on 1/2 milk)
     */
    public LocalDate getEst14Date() {
        return getDOB().plusWeeks(11);

    }

    /**
     * @return 12 weeks after dob (1 week on 1/4 milk)
     */
    public LocalDate getEstWaterDate() {
        return getDOB().plusWeeks(12);
    }

    /**
     * @return 12 weeks after dob (1 week on water)
     */
    public LocalDate getEstMoveDate() {
        return getDOB().plusWeeks(13);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        boolean left = status.equalsIgnoreCase("expired") || status.equalsIgnoreCase("sold");
        if (left) new LeftSheet(this);
        this.setLeft(left);
    }

    public boolean hadColostrum1() {
        return (bools & Constants.COLOSTRUM1) != 0;
    }

    public boolean hadColostrum2() {
        return (bools & Constants.COLOSTRUM2) != 0;
    }

    public boolean hadEcolizer() {
        return (bools & Constants.ECOLIZER) != 0;
    }

    public boolean hadCalfGuard() {
        return (bools & Constants.CALFGURAD) != 0;
    }

    public boolean hadInforce() {
        return (bools & Constants.INFORCE) != 0;
    }

    public boolean hadMultimin() {
        return (bools & Constants.MULTIMIN) != 0;
    }

    public boolean hadBovishield() {
        return (bools & Constants.BOVISHIELD) != 0;
    }

    public boolean isWeaned() {
        return (bools & Constants.WEANED) != 0;
    }

    public void setWeaned(boolean weaned) {
        if (isWeaned() != weaned)
            bools ^= Constants.WEANED;
    }

    public boolean isDehorned() {
        return (bools & Constants.DEHORNED) != 0;
    }

    public void setDehorned(boolean dehorned) {
        if (isDehorned() != dehorned) bools ^= Constants.DEHORNED;
    }

    public void setLeft(boolean left) {
        if (hasLeft() != left) bools ^= Constants.LEFT;
    }

    public void setColostrum1(boolean colostrum1) {
        if (hadColostrum1() != colostrum1) bools ^= Constants.COLOSTRUM1;
    }

    public void setColostrum2(boolean colostrum2) {
        if (hadColostrum2() != colostrum2) bools ^= Constants.COLOSTRUM2;
    }

    public void setEcolizer(boolean vacEcolizer) {
        if (hadEcolizer() != vacEcolizer) bools ^= Constants.ECOLIZER;
    }

    public void setCalfGuard(boolean vacCalfGuard) {
        if (hadCalfGuard() != vacCalfGuard) bools ^= Constants.CALFGURAD;
    }

    public void setVacInforce(boolean vacInforce) {
        if (hadInforce() != vacInforce) bools ^= Constants.INFORCE;
    }

    public void setVacMultimin(boolean vacMultimin) {
        if (hadMultimin() != vacMultimin) bools ^= Constants.MULTIMIN;
    }

    public void setVacBoviShield(boolean vacBoviShield) {
        if (hadBovishield() != vacBoviShield) bools ^= Constants.BOVISHIELD;
    }

    public void setVision7(boolean vision7) {
        if (hadVision7() != vision7) bools ^= Constants.VISION7;
    }

    public boolean hadVision7() {
        return (bools & Constants.VISION7) != 0;
    }

    public boolean hasLeft() {
        return (bools & Constants.LEFT) != 0;
    }

    public String getLeftDateStr() {
        return leftDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void appendNotes(String append) {
        this.notes = notes + append;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public String getFormattedTag() {
        return Util.formatTag(tagNumber);
    }

    public void setData(
            String dOB,
            String name,
            boolean colostrum1,
            boolean colostrum2,
            boolean dehorned,
            boolean weaned,
            String status,
            boolean ecolizer,
            boolean calfGuard,
            boolean inforce,
            boolean multimin,
            boolean boviShield,
            boolean vision7,
            String leftDate) {
        this.dOB = dOB;
        this.name = name;
        this.status = status;
        this.leftDate = leftDate;

        setColostrum1(colostrum1);
        setColostrum2(colostrum2);
        setDehorned(dehorned);
        setWeaned(weaned);
        setEcolizer(ecolizer);
        setCalfGuard(calfGuard);
        setVacInforce(inforce);
        setVacMultimin(multimin);
        setVacBoviShield(boviShield);
        setVision7(vision7);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tagNumber", tagNumber)
                .append("dOB", dOB)
                .append("leftDate", leftDate)
                .append("status", status)
                .append("bools", bools)
                .append("treatments", treatments)
                .append("notes", notes)
                .append("addedBy", addedBy)
                .toString().replace('\n', '|');
    }
}
