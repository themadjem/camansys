package com.themadjem.utils;

import org.jetbrains.annotations.NotNull;
import themadjem.util.Output;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author Jesse Maddox
 * Copyright 5/28/2017
 */
@SuppressWarnings("unused")
public class Util {
    private static final NumberFormat NF = new DecimalFormat("0000");
    private static final DecimalFormat DF = new DecimalFormat("#0.00");
    private static final DateTimeFormatter LOG_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");

    /**
     * Returns the given local date in a "MM-dd-YYYY" format
     * 2017-12-31 -> 12-31-2017
     *
     * @param localDate Date
     * @return Formatted String
     */
    public static String formatDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("MM-dd-YY"));
    }

    /**
     * Returns a String of a given Double limited to two decimal places
     *
     * @param d double
     * @return string with two decimals
     */
    static String formatDec(double d) {
        return String.valueOf(DF.format(d));
    }

    /**
     * Returns a parsed integer from a JTextField
     *
     * @param j TextField
     * @return integer from field
     */
    static int getIntFromJTF(@NotNull JTextField j) {
        return getInt(j.getText());
    }

    /**
     * Returns a parsed integer from a String
     *
     * @param s String
     * @return integer from field
     */
    private static int getInt(@NotNull String s) {
        if (s.equalsIgnoreCase("") || s.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Output.infoBox("Error in parsing int\n" + e.getMessage(), "ERROR");
            return 0;
        }
    }

    public static <T> T getLastInList(ArrayList<T> list) {
        int len = list.size();
        return list.get(len - 1);

    }

    /**
     * Returns a double from a given string
     *
     * @param s s
     * @return d
     */
    static double getDouble(@NotNull String s) {
        return Double.parseDouble(s);
    }

    /**
     * Appends the given message to a given JTextPane with a given Color
     * Copied from StackOverFlow.com
     *
     * @param textPane pane
     * @param msg      message
     * @param c        color
     */
    private static void appendToPane(@NotNull JTextPane textPane, String msg, @SuppressWarnings("SameParameterValue") Color c) {
        textPane.setEditable(true);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(msg);
        textPane.setEditable(false);
    }

    /**
     * Clears all text from the given
     *
     * @param textPane pane
     */
    public static void clearPane(@NotNull JTextPane textPane) {
        textPane.setEditable(true);
        textPane.setText("");
        textPane.setEditable(false);
    }

    /**
     * Writes the given message to the given TextPane and appends a \n newline character
     *
     * @param msg message
     * @param t   textpane
     */
    public static void writeln(String msg, @NotNull JTextPane t) {
        appendToPane(t, msg, Color.BLACK);
        appendToPane(t, "\n", Color.BLACK);
    }

    public static String formatTag(int tagNumber) {
        return NF.format(tagNumber);
    }

    public static void registerKeyAction(JComponent jComponent, int keyCode, ActionListener action) {
        jComponent.registerKeyboardAction(action, KeyStroke.getKeyStroke(keyCode, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
}
