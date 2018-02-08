package com.themadjem.fx;

import com.themadjem.utils.Constants;
import com.themadjem.Cow;
import com.themadjem.Treatment;
import themadjem.util.Output;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;

class NewTreatment extends JDialog {

    private final Cow cow;
    private final CalfSheet calfSheet;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel tagLabel;
    private JComboBox<String> medComboBox;
    private JTextField amountField;
    private JTextField dateField;
    private JComboBox timeComboBox;
    private JTextField otherField;
    private JLabel otherLabel;
    private JComboBox<String> adminComboBox;
    private JTextArea symptomArea;

    public NewTreatment(Cow cow, CalfSheet calfSheet) {// TODO: 10/5/2017 Add a symptoms field!
        this.cow = cow;
        this.calfSheet = calfSheet;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        dateField.setText(LocalDate.now().toString());
        tagLabel.setText(cow.getFormattedTag());
        medComboBox.setModel(Constants.MEDS_MODEL);
        adminComboBox.setModel(Constants.USERS_MODEL);

        if (LocalTime.now().isAfter(Constants.MORNING)) timeComboBox.setSelectedIndex(1);
        else timeComboBox.setSelectedIndex(0);

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

        setTitle("New treatment for " + cow.getFormattedTag());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void onOK() {
        // add new Treatment to the list of Treatments
        try {
            int time;

            if (String.valueOf(timeComboBox.getSelectedItem()).equalsIgnoreCase("AM")) time = Treatment.AM;
            else time = Treatment.PM;

            String med = String.valueOf(medComboBox.getSelectedItem());
            if (med.equalsIgnoreCase("other")) med = otherField.getText();
            cow.addTreatment(new Treatment(
                    dateField.getText(),
                    time,
                    med,
                    Double.parseDouble(amountField.getText()),
                    symptomArea.getText(),
                    (String) adminComboBox.getSelectedItem()

            ));
            calfSheet.resetSheet();
        } catch (Exception e) {
            Output.infoBox("An error has occurred\nPlease check all fields", "ERROR");
            return;
        }
        dispose();
    }

    private void onCancel() {
        // close window and do nothing
        dispose();
    }


}
