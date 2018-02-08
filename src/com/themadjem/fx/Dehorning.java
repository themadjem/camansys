package com.themadjem.fx;

import com.themadjem.Herd;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

class Dehorning extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textArea1;
    private final ArrayList<String> notDehorned;

    Dehorning(Herd herd) {
        notDehorned = new ArrayList<>();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Not Dehorned");

        findNotDehorned(herd);

        buttonOK.addActionListener(e -> onClose());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

    }

    private void findNotDehorned(Herd herd) {
        herd.getCows().forEach(c -> {
            if (!c.isDehorned()) {
                notDehorned.add(c.getFormattedTag());
            }
        });
        notDehorned.forEach(t -> textArea1.append(t + '\n'));
    }

    private void onClose() {
        dispose();
    }
}
