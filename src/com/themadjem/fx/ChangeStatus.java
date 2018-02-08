package com.themadjem.fx;

import com.themadjem.Cow;

import javax.swing.*;
import java.awt.event.*;

class ChangeStatus extends JDialog implements ActionListener{
    private final Cow cow;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel tagLabel;
    private JRadioButton calfBarnRadioButton;
    private JRadioButton transitionBarnRadioButton;
    private JRadioButton movedRadioButton;
    private JRadioButton soldRadioButton;
    private JRadioButton expiredRadioButton;
    private JRadioButton otherRadioButton;
    private JTextField textField1;
    private final ButtonGroup group = new ButtonGroup();

    public ChangeStatus (Cow cow) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Status of " + cow.getFormattedTag());
        getRootPane().setDefaultButton(buttonOK);
        this.cow = cow;
        tagLabel.setText(cow.getFormattedTag());
        group.add(calfBarnRadioButton);
        calfBarnRadioButton.setActionCommand("Calf Barn");
        group.add(transitionBarnRadioButton);
        transitionBarnRadioButton.setActionCommand("Transition Barn");
        group.add(movedRadioButton);
        movedRadioButton.setActionCommand("Moved");
        group.add(soldRadioButton);
        soldRadioButton.setActionCommand("Sold");
        group.add(expiredRadioButton);
        expiredRadioButton.setActionCommand("Expired");
        group.add(otherRadioButton);
        group.setSelected(calfBarnRadioButton.getModel(),true);

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

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        if (group.getSelection().equals(otherRadioButton.getModel()))
            cow.setStatus(textField1.getText());
        else
            cow.setStatus(group.getSelection().getActionCommand());
        dispose();
    }

    private void onCancel() {
        //Close the window
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
