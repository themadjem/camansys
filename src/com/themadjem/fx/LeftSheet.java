package com.themadjem.fx;

import com.themadjem.Cow;
import themadjem.util.Output;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class LeftSheet extends JDialog {
    private final Cow cow;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dateField;
    private JTextField reasonField;

    public LeftSheet(Cow cow) {
        this.cow = cow;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        dateField.setText(LocalDate.now().toString());
        setTitle("Left Calf");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private void onOK() {
        // add your code here
        LocalDate leftDate;
        try {
            leftDate = LocalDate.parse(dateField.getText());
        } catch (Exception e) {
            Output.infoBox("Error parsing Date", "ERROR");
            return;
        }
        cow.setLeftDate(leftDate.toString());
        cow.appendNotes("\n" + cow.getStatus() + " : " + reasonField.getText());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
