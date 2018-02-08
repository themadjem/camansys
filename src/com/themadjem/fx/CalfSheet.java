package com.themadjem.fx;

import com.themadjem.Cow;
import com.themadjem.Herd;
import com.themadjem.Logic;
import com.themadjem.Treatment;
import com.themadjem.utils.Constants;
import com.themadjem.utils.Util;
import themadjem.util.Input;
import themadjem.util.Output;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

@SuppressWarnings("unused")
public class CalfSheet extends JFrame implements ActionListener {

    public static final String NOTES_PATH = "";

    public final LocalTime THREE_PM = LocalTime.of(15, 0);
    private final Herd herd;
    private Cow cow;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel sumPanel;
    private JPanel treatPanel;
    private JTextPane treatTextPane;
    private JButton addTreatmentButton;
    private JLabel tagLabel;
    private JTextField dobField;
    private JTextField est34Field;
    private JTextField est12Field;
    private JTextField est14Field;
    private JTextField estWaterField;
    private JLabel statusLabel;
    private JTextArea noteArea;
    private JCheckBox ecolizerCheckBox;
    private JCheckBox inforceCheckBox;
    private JCheckBox calfGuardCheckBox;
    private JCheckBox multiminCheckBox;
    private JCheckBox weanedCheckBox;
    private JCheckBox Colostrum1CheckBox;
    private JCheckBox Colostrum2CheckBox;
    private JButton changeCalfButton;
    private JCheckBox dehornedCheckBox;
    private JButton addNewCalfButton;
    private JButton backButton;
    private JButton forwardButton;
    private JButton changeStatusButton;
    private JLabel herdTreatDateLabel;
    private JTextPane herdTreatPane;
    private JButton nextDateButton;
    private JButton prevDateButton;
    private JTextField estMoveField;
    private JTextField daysOldField;
    private JLabel nameLabel;
    private JCheckBox boviShieldCheckBox;
    private JButton enPrevDateButton;
    private JLabel enDateLabel;
    private JButton saveEmpNoteButton;
    private JButton enNextDateButton;
    private JTextArea enTextAreaAM;
    private JScrollPane amScrollPane;
    private JButton saveCalfNoteButton;
    private JRadioButton savedRadioButton;
    private JTextPane enTextPane;
    private JRadioButton enSavedRadio;
    private JLabel addedByLabel;
    private JCheckBox vision7CheckBox;
    private JTextField addedByField;
    @SuppressWarnings("CanBeFinal")
    private LocalDate selectedDate;
    private LocalDate enSelectedDate;
    private String startupMessage = "";

    public CalfSheet(Herd herd) {
        this.herd = herd;
        setContentPane(panel1);
        sortCows(herd);
        cow = herd.getCows().get(0);
        selectedDate = LocalDate.now(); //Gets today's date for the Herd Treatments Tab
        resetSheet();
        lockCheckboxes();
        loadStartupMsg();
        registerFunctions();

        //Saves herd on close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });


        changeCalfButton.addActionListener(e -> onChangeCalf());
        addTreatmentButton.addActionListener(e -> onAddTreatment());
        addNewCalfButton.addActionListener(e -> onAddCalf());
        backButton.addActionListener(e -> onBack());
        forwardButton.addActionListener(e -> onForward());
        changeStatusButton.addActionListener(e -> onChangeStatus());
        prevDateButton.addActionListener(e -> {
            selectedDate = onPrevDate(selectedDate);
            resetSheet();
        });
        nextDateButton.addActionListener(e -> {
            selectedDate = onNextDate(selectedDate);
            resetSheet();
        });
        saveEmpNoteButton.addActionListener(e -> onSaveEN());
        saveCalfNoteButton.addActionListener(e -> onSaveCalfNote());
        noteArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                savedRadioButton.setSelected(false);
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        enTextPane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                enSavedRadio.setSelected(false);
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        loadEmpNotes();
        setTitle("Calf Management System");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        savedRadioButton.addActionListener(e -> savedRadioButton.setSelected(!savedRadioButton.isSelected()));
        enSavedRadio.addActionListener(e -> enSavedRadio.setSelected(!enSavedRadio.isSelected()));
    }

    private void loadStartupMsg() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Constants.MSG_PATH));
            String line;
            while ((line = br.readLine()) != null) {
                startupMessage = String.format("%s%s", startupMessage, line);
            }
            br.close();
            if (!startupMessage.equalsIgnoreCase("")) {
                Output.infoBox(startupMessage, "Important!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void registerFunctions() {
        Util.registerKeyAction(panel1, KeyEvent.VK_F1, e -> editPopup());
        Util.registerKeyAction(panel1, KeyEvent.VK_F2, e -> {
            new EditCalf(cow);
            resetSheet();
        });
        Util.registerKeyAction(panel1, KeyEvent.VK_F3, e -> new Dehorning(herd));
        Util.registerKeyAction(panel1, KeyEvent.VK_F12, e -> {
            new SecretMenu(this, herd, cow);
            resetSheet();
        });
    }

    private void onClose() {
        Logic.saveCows(herd);
        onSaveEN();
        onSaveCalfNote();
    }

    private void onSaveCalfNote() {
        cow.setNotes(noteArea.getText());
        savedRadioButton.setSelected(true);
    }

    void editPopup() {
        startupMessage = Input.getStrInPane("Enter message to be displayed on startup", "Startup message");
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(Constants.MSG_PATH));
            br.write(startupMessage);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onSaveEN() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(Constants.NOTE_PATH));
            out.write(enTextPane.getText());
            out.flush();
            out.close();
        } catch (IOException e) {
            Output.infoBox("An error has occurred while saving Employee Notes", "ERROR");
        }
        enSavedRadio.setSelected(true);
    }

    private void loadEmpNotes() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(Constants.NOTE_PATH));

            String line;
            while ((line = in.readLine()) != null) {
                Util.writeln(line, enTextPane);
            }
            in.close();
            enTextPane.setEditable(true);
        } catch (IOException e) {
            enTextPane.setText("");
        }
        enSavedRadio.setSelected(true);
    }


    public Cow getCow() {
        return cow;
    }

    /**
     * Sorts calves by tag number
     *
     * @param herd herd
     */
    private void sortCows(Herd herd) {
        herd.getCows().sort(Comparator.comparingInt(Cow::getTagNumber));
    }

    /**
     * Locks the checkboxes forcing the user to use the Edit Calf window to make these changes
     */
    private void lockCheckboxes() {
        ecolizerCheckBox.addActionListener(e -> ecolizerCheckBox.setSelected(!ecolizerCheckBox.isSelected()));
        inforceCheckBox.addActionListener(e -> inforceCheckBox.setSelected(!inforceCheckBox.isSelected()));
        calfGuardCheckBox.addActionListener(e -> calfGuardCheckBox.setSelected(!calfGuardCheckBox.isSelected()));
        multiminCheckBox.addActionListener(e -> multiminCheckBox.setSelected(!multiminCheckBox.isSelected()));
        weanedCheckBox.addActionListener(e -> weanedCheckBox.setSelected(!weanedCheckBox.isSelected()));
        dehornedCheckBox.addActionListener(e -> dehornedCheckBox.setSelected(!dehornedCheckBox.isSelected()));
        Colostrum1CheckBox.addActionListener(e -> Colostrum1CheckBox.setSelected(!Colostrum1CheckBox.isSelected()));
        Colostrum2CheckBox.addActionListener(e -> Colostrum2CheckBox.setSelected(!Colostrum2CheckBox.isSelected()));
        boviShieldCheckBox.addActionListener(e -> boviShieldCheckBox.setSelected(!boviShieldCheckBox.isSelected()));
        vision7CheckBox.addActionListener((e -> vision7CheckBox.setSelected(!vision7CheckBox.isSelected())));
    }

    private void onChangeStatus() {
        new ChangeStatus(cow);
        resetSheet();
    }

    private void onChangeCalf() {
        cow = getCalf();
        resetSheet();
    }

    /**
     * Called when the Add calf button is pressed.
     * Displays the New Calf dialogue
     * Adds the new calf to the herd and then sorts the cows
     */
    private void onAddCalf() {
        new NewCalf(herd);
        cow = Util.getLastInList(herd.getCows());
        sortCows(herd);
        resetSheet();
    }

    /**
     * Moves to the next calf in the list
     */
    private void onForward() {
        int idx = herd.getCows().indexOf(cow) + 1;
        if (idx >= herd.getCows().size()) idx = 0;
        cow = herd.getCows().get(idx);
        resetSheet();
    }

    /**
     * Moves to the previous calf in the list
     */
    private void onBack() {
        int idx = herd.getCows().indexOf(cow) - 1;
        if (idx < 0) idx = herd.getCows().size() - 1;
        cow = herd.getCows().get(idx);
        resetSheet();
    }

    /**
     * Goes to the previous date for the given LocalDate
     */
    private LocalDate onPrevDate(LocalDate date) {
        return date.minusDays(1);
    }

    /**
     * Goes to the next date for the given localDate
     */
    private LocalDate onNextDate(LocalDate date) {
        return date.plusDays(1);
    }

    /**
     * Called when the Add Treatment button is pressed
     */
    private void onAddTreatment() {
        new NewTreatment(cow, this);
    }

    /**
     * Shows a dialogue box to select a new calf
     *
     * @return Cow of the entered number
     */
    private Cow getCalf() {
        int request;
        try {
            String s = Input.getStrInPane("Which calf would you like to look at?", "Calf Selector");
            if (s == null) {
                return cow;
            }
            request = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Output.infoBox("Please enter a cow's numerical tag number only!", "ERROR");
            return getCalf();
        }
        for (Cow c : herd.getCows()) {
            if (c.getTagNumber() == request) {
                return c;
            }
        }
        Output.infoBox("Unknown Cow!", "ERROR");
        return getCalf();
    }

    /**
     * Resets all the info on the page to match the currently selected cow
     */
    void resetSheet() {
        tagLabel.setText(cow.getFormattedTag());
        statusLabel.setText(cow.getStatus());
        dobField.setText(Util.formatDate(cow.getDOB()));
        dobField.setEditable(false);
        est34Field.setText(Util.formatDate(cow.getEst34Date()));
        est34Field.setEditable(false);
        est12Field.setText(Util.formatDate(cow.getEst12Date()));
        est12Field.setEditable(false);
        est14Field.setText(Util.formatDate(cow.getEstWaterDate()));
        est14Field.setEditable(false);
        estWaterField.setText(Util.formatDate(cow.getEstWaterDate()));
        estWaterField.setEditable(false);
        estMoveField.setText(Util.formatDate(cow.getEstMoveDate()));
        estMoveField.setEditable(false);

        nameLabel.setText(cow.getName());

        showAge(daysOldField);
        daysOldField.setEditable(false);


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

        herdTreatDateLabel.setText(Util.formatDate(selectedDate));
        addedByLabel.setText(cow.getAddedBy());

        loadTreatments();
        loadHerdTreatments();
        noteArea.setText(cow.getNotes());
        savedRadioButton.setSelected(true);
    }

    /**
     * Displays to the given jtf how many days old the cow is
     *
     * @param jtf field
     */
    private void showAge(JTextField jtf) {
        long days = cow.getAge_Days();

        jtf.setText(days + " days old");// TODO: 11/2/2017 months+days
    }

    /**
     * Loads all treatments from all herd for the current day and writes them to the Herd Treatment Log
     */
    private void loadHerdTreatments() {
        Util.clearPane(herdTreatPane);
        herd.getCows().forEach(currentCow -> currentCow.getTreatments().forEach(treatment -> {
            if (treatment.getDate().equalsIgnoreCase(String.valueOf(selectedDate))) {
                Util.writeln(treatment.toHerdString(currentCow), herdTreatPane);
            }
        }));
    }

    /**
     * Loads treatments from Cow's List and writes them to the Treatments Log
     */
    private void loadTreatments() {
        Util.clearPane(treatTextPane);
        for (Treatment t : cow.getTreatments()) {
            Util.writeln(t.toString(), treatTextPane);
        }
    }

    /**
     * Called when a checkbox is changed. Resets all the cow's boolean properties and saves the herd
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ecolizerCheckBox.setSelected(ecolizerCheckBox.isSelected());
        inforceCheckBox.setSelected(inforceCheckBox.isSelected());
        calfGuardCheckBox.setSelected(calfGuardCheckBox.isSelected());
        multiminCheckBox.setSelected(multiminCheckBox.isSelected());
        weanedCheckBox.setSelected(weanedCheckBox.isSelected());
        dehornedCheckBox.setSelected(dehornedCheckBox.isSelected());
        Colostrum1CheckBox.setSelected(Colostrum1CheckBox.isSelected());
        Colostrum2CheckBox.setSelected(Colostrum2CheckBox.isSelected());
        changeCalfButton.setSelected(changeCalfButton.isSelected());
        boviShieldCheckBox.setSelected(boviShieldCheckBox.isSelected());
        vision7CheckBox.setSelected(vision7CheckBox.isSelected());
    }

}
