package com.themadjem.fx;

import com.themadjem.utils.Constants;
import com.themadjem.Cow;
import com.themadjem.Herd;
import com.themadjem.utils.Util;
import themadjem.util.Input;
import themadjem.util.Output;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;

class NewCalf extends JDialog {
    private final Herd herd;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel tagLabel;
    private JButton changeButton;
    private JTextField dobField;
    private JCheckBox ecolizerCheckBox;
    private JCheckBox inforceCheckBox;
    private JCheckBox calfGuardCheckBox;
    private JCheckBox multiminCheckBox;
    private JComboBox<String> addedByComboBox;
    private JButton bullButton;
    private JButton heiferButton;

    public NewCalf(Herd herd) {
        this.herd = herd;
        setContentPane(contentPane);
        setModal(true);
        setTitle("New Calf");
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        addedByComboBox.setModel(Constants.USERS_MODEL);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        changeButton.addActionListener(e -> onChange());
        heiferButton.addActionListener(e -> onHeifer());
        bullButton.addActionListener(e -> onBull());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        dobField.setText(LocalDate.now().toString());
        tagLabel.setText(String.valueOf(herd.getCows().get(herd.getCows().size() - 1).getTagNumber() + 1));

        pack();
        setVisible(true);
    }

    private void onBull() {
        tagLabel.setText(Util.formatTag(getNextBull()));
    }

    private void onHeifer() {
        tagLabel.setText(Util.formatTag(getNextHeifer()));

    }

    private void onChange() {
        int i = 0;
        String s = null;
        try {
            s = Input.getStrInPane("Enter number:", "New Calf");
            i = Integer.parseInt(s);
        } catch (NumberFormatException e1) {
            if (s == null || s.isEmpty())
                return;
            Output.infoBox("Invalid Number!", "ERROR");
            onChange();
        }

        final int finalI = i;
        herd.getCows().forEach((cow) -> {
            if (cow.getTagNumber() == finalI) {
                Output.infoBox("That calf already exists!", "ERROR");
                onChange();
            }
        });
        tagLabel.setText(Util.formatTag(i));
    }

    private int getNextHeifer() {
        List<Cow> cows = herd.getCows(); //get List of cows
        Cow c = cows.get(herd.getCows().size() - 1); // get the last heifer in the herd
        return c.getTagNumber() + 1; //return last heifer's tag number +1
    }

    private int getNextBull() {
        int lastTag = -1;
        for (Cow c : herd.getCows()) {
            int tag = c.getTagNumber();
            if (tag >= lastTag && tag < 3000) {
                lastTag = tag;
            } else {
                return lastTag + 1;
            }
        }
        return lastTag;
    }

    /**
     * Add new Calf to the system and close the window
     */
    private void onOK() {
        herd.addCow(
                new Cow(Integer.parseInt(tagLabel.getText()),
                        dobField.getText(),
                        ecolizerCheckBox.isSelected(),
                        calfGuardCheckBox.isSelected(),
                        inforceCheckBox.isSelected(),
                        multiminCheckBox.isSelected(),
                        (String) addedByComboBox.getSelectedItem()));
        dispose();
    }

    /**
     * Do nothing and dispose of the window
     */
    private void onCancel() {
        dispose();
    }
}
