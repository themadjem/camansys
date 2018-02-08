package com.themadjem.fx;

import com.themadjem.Cow;
import themadjem.util.Output;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

class EditCalf extends JDialog {
    private final Cow cow;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dobField;
    private JCheckBox Colostrum1CheckBox;
    private JCheckBox Colostrum2CheckBox;
    private JCheckBox dehornedCheckBox;
    private JCheckBox weanedCheckBox;
    private JCheckBox ecolizerCheckBox;
    private JCheckBox inforceCheckBox;
    private JCheckBox calfGuardCheckBox;
    private JCheckBox multiminCheckBox;
    private JCheckBox boviShieldCheckBox;
    private JTextField est34Field;
    private JTextField est12Field;
    private JTextField est14Field;
    private JTextField estMoveField;
    private JTextField estWaterField;
    private JLabel tagLabel;
    private JLabel statusLabel;
    private JLabel nameLabel;
    private JButton changeNameButton;
    private JButton changeStatusButton;
    private JCheckBox vision7CheckBox;
    private JTextField leftDateTextField;
    private JButton recalculateWeaningButton;

    public EditCalf(Cow cow) {
        this.cow = cow;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        loadCalf();
        recalculateWeaningButton.addActionListener(e -> {
            LocalDate dob;
            try {
                dob = LocalDate.parse(dobField.getText());
            } catch (Exception e1) {
                Output.infoBox("Error parsing DOB, please check fields", "ERROR");
                return;
            }
            est34Field.setText(dob.plusWeeks(8).toString());
            est12Field.setText(dob.plusWeeks(10).toString());
            est14Field.setText(dob.plusWeeks(11).toString());
            estWaterField.setText(dob.plusWeeks(12).toString());
            estMoveField.setText(dob.plusWeeks(13).toString());


        });

        changeNameButton.addActionListener(e -> {
            String name = themadjem.util.Input.getStrInPane("Enter new Name:", "Name");
            nameLabel.setText(name);
        });
        changeStatusButton.addActionListener(e -> {
            new ChangeStatus(cow);
            statusLabel.setText(cow.getStatus());
        });

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        // call onCancel() when [X] is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        setTitle("Editing calf: " + cow.getFormattedTag());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private void loadCalf() {
        tagLabel.setText(cow.getFormattedTag());
        dobField.setText(cow.getDOB().toString());
        leftDateTextField.setText(cow.getLeftDateStr());
        nameLabel.setText(cow.getName());
        statusLabel.setText(cow.getStatus());
        est34Field.setText(cow.getEst34Date().toString());
        est12Field.setText(cow.getEst12Date().toString());
        est14Field.setText(cow.getEstWaterDate().toString());
        estWaterField.setText(cow.getEstWaterDate().toString());
        estMoveField.setText(cow.getEstMoveDate().toString());

        Colostrum1CheckBox.setSelected(cow.hadColostrum1());
        Colostrum2CheckBox.setSelected(cow.hadColostrum2());
        dehornedCheckBox.setSelected(cow.isDehorned());
        weanedCheckBox.setSelected(cow.isWeaned());

        ecolizerCheckBox.setSelected(cow.hadEcolizer());
        calfGuardCheckBox.setSelected(cow.hadCalfGuard());
        inforceCheckBox.setSelected(cow.hadInforce());
        multiminCheckBox.setSelected(cow.hadMultimin());
        boviShieldCheckBox.setSelected(cow.hadBovishield());
        vision7CheckBox.setSelected(cow.hadVision7());
    }

    private void onOK() {
        cow.setData(
                dobField.getText(),
                nameLabel.getText(),
                Colostrum1CheckBox.isSelected(),
                Colostrum2CheckBox.isSelected(),
                dehornedCheckBox.isSelected(),
                weanedCheckBox.isSelected(),
                statusLabel.getText(),
                ecolizerCheckBox.isSelected(),
                calfGuardCheckBox.isSelected(),
                inforceCheckBox.isSelected(),
                multiminCheckBox.isSelected(),
                boviShieldCheckBox.isSelected(),
                vision7CheckBox.isSelected(),
                leftDateTextField.getText()
        );
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
