package com.themadjem.fx;

import com.themadjem.Cow;
import com.themadjem.Herd;
import themadjem.util.Output;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class SecretMenu extends JFrame {
    private JButton editStartupMessageButton;
    private JPanel panel1;
    private JButton editSelectedCalfInfoButton;
    private JButton showNotDehornedButton;
    private JButton unusedButton;
    private final CalfSheet sheet;

    SecretMenu(CalfSheet calfSheet, Herd herd, Cow cow) {
        this.sheet = calfSheet;
        setContentPane(panel1);
        setResizable(false);
        setLocationRelativeTo(null);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                calfSheet.resetSheet();
                dispose();
            }
        });

        editStartupMessageButton.addActionListener((e) -> sheet.editPopup());
        editSelectedCalfInfoButton.addActionListener((e) -> new EditCalf(cow));
        showNotDehornedButton.addActionListener((e) -> new Dehorning(herd));


        pack();
        setVisible(true);
        unusedButton.addActionListener(e -> Output.infoBox("Hello!", ""));
    }


}
