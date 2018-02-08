package com.themadjem.utils;

import javax.swing.*;
import java.time.LocalTime;

/**
 * @author Jesse Maddox
 * Copyright 2/5/2018
 */
public final class Constants {
    public static final int LEFT = 0b10000000000;
    public static final int WEANED = 0b01000000000;
    public static final int DEHORNED = 0b00100000000;
    public static final int COLOSTRUM1 = 0b00010000000;
    public static final int COLOSTRUM2 = 0b00001000000;
    public static final int ECOLIZER = 0b00000100000;
    public static final int CALFGURAD = 0b00000010000;
    public static final int INFORCE = 0b00000001000;
    public static final int MULTIMIN = 0b00000000100;
    public static final int BOVISHIELD = 0b00000000010;
    public static final int VISION7 = 0b00000000001;
    public static final LocalTime MORNING = LocalTime.of(15, 0);
    public static final String COWS_PATH = "res/cows.json";
    public static final String NOTE_PATH = "res/EN.txt";
    public static final String MSG_PATH = "res/msg.txt";
    private static final String[] USERS = {
            "JM", // Jesse Maddox
            "MM", // Melony Maddox
            "JL", // Jarell Lenaway
            "JJ", // Jeff Jernstadt
            "RM", // Roxanne
    };
    public static final ComboBoxModel<String> USERS_MODEL = new JComboBox<>(USERS).getModel();
    private static final String[] MEDS = {
            "Prevail",
            "Excenel",
            "Tet",
            "Polyflex",
            "Micotil",
            "Draxxin",
            "Nuflor",
            "Tiamine",
            "Zactran",
            "Baytril",
            "Other"
    };
    public static final ComboBoxModel<String> MEDS_MODEL = new JComboBox<>(MEDS).getModel();
}
