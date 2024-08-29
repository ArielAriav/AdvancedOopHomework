package Graphics;

import Animals.Animal;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * The AddCompetitionDialog class provides a dialog for setting up a new competition.
 * Users can select the type of competition, add groups of animals, and finalize the competition setup.
 */
public class AddCompetitionDialog extends JDialog {

    private JComboBox<String> competitionTypeComboBox;
    private JButton okButton;
    private JButton addGroupButton;
    private JTable animalTable;
    private AnimalTableModel animalTableModel;
    private static String selectedCompetitionType;
    private static final int MAX_GROUPS = 10;
    private Map<Integer, String> groupTypes;
    private JFrame currentTopFrame;

    /**
     * Constructs a new AddCompetitionDialog.
     *
     * @param parentFrame       the parent frame to which this dialog is attached
     * @param animalTableModel  the data model representing the groups and animals
     */
    public AddCompetitionDialog(JFrame parentFrame, AnimalTableModel animalTableModel) {
        super(parentFrame, "Add Competition", true);
        setLayout(new BorderLayout());

        currentTopFrame = parentFrame;

        groupTypes = new HashMap<>();
        this.animalTableModel = animalTableModel;

        // Competition type combo box (Air, Water, Terrestrial)
        JPanel middlePanel = new JPanel(new GridLayout(2, 1));
        middlePanel.add(new JLabel("Select Group Type:"));
        competitionTypeComboBox = new JComboBox<>(new String[]{"-", "Air", "Water", "Terrestrial"});
        middlePanel.add(competitionTypeComboBox);
        add(middlePanel, BorderLayout.CENTER);

        // Animal groups table
        animalTable = new JTable(animalTableModel);
        animalTable.setDefaultRenderer(JButton.class, new ButtonRenderer());
        addAnimalButtonsToTable();
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(animalTable), BorderLayout.CENTER);

        // Buttons for adding groups
        addGroupButton = new JButton("Add Group +");
        addGroupButton.addActionListener(e -> addGroup());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addGroupButton);

        tablePanel.add(buttonPanel, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.CENTER);

        // OK button
        okButton = new JButton("OK");
        okButton.addActionListener(e -> finalizeCompetition());

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(okButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocationRelativeTo(parentFrame);
    }

    /**
     * Opens a dialog to add a new group to the competition.
     * If the maximum number of groups is reached, it shows an error message.
     */
    private void addGroup() {
        if (animalTableModel.getGroupCount() < MAX_GROUPS) {
            AddGroupDialog groupDialog = new AddGroupDialog((JFrame) getParent(), this);
            groupDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Maximum number of groups reached.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new group to the competition with the specified name, type, and competition type.
     *
     * @param groupName        the name of the group
     * @param groupType        the type of group (e.g., Air, Water, Land)
     * @param competitionType  the type of competition (e.g., Regular, Courier)
     */
    public void addGroup(String groupName, String groupType, String competitionType, Integer trackNum) {
        animalTableModel.addGroup(groupName + " (" + groupType + ")", competitionType, groupType, trackNum);
        groupTypes.put(animalTableModel.getGroupCount() - 1, groupType);
        addAnimalButtonsToTable();
    }

    /**
     * Adds "Add Animal" buttons to each group column in the table.
     * Each button allows the user to add a new animal to the corresponding group.
     */
    private void addAnimalButtonsToTable() {
        for (int i = 0; i < animalTableModel.getGroupCount(); i++) {
            animalTable.getColumnModel().getColumn(i).setCellRenderer(new ButtonRenderer());
            animalTable.getColumnModel().getColumn(i).setCellEditor(new ButtonEditor(new JCheckBox(), i, currentTopFrame));
        }
    }

    /**
     * Returns the type of the selected group based on the group index.
     *
     * @param groupIndex  the index of the group
     * @return the type of the selected group (e.g., Air, Water, Land)
     */
    public String getSelectedGroupType(int groupIndex) {
        return groupTypes.get(groupIndex);
    }

    /**
     * Returns the animal table model used by this dialog.
     *
     * @return the animal table model
     */
    public AnimalTableModel getAnimalTableModel() {
        return animalTableModel;
    }

    /**
     * Finalizes the competition by setting the selected competition type and closing the dialog.
     */
    private void finalizeCompetition() {
        selectedCompetitionType = (String) competitionTypeComboBox.getSelectedItem();

        // Check if any terrestrial courier group has less than 4 animals
        for (int i = 0; i < animalTableModel.getGroupCount(); i++) {
            String groupType = animalTableModel.getGroupType(i);
            String competitionType = animalTableModel.getCompetitionType(i);

            if (groupType.equals("Terrestrial") && competitionType.equals("courier")) {
                int animalCount = animalTableModel.getNumberOfAnimalInTheGroup(i);
                if (animalCount < 4) {
                    JOptionPane.showMessageDialog(this,
                            "Each terrestrial courier group must have 4 animals. \n please add more animals to " + animalTableModel.getGroupName(i) + ".",
                            "Insufficient Animals",
                            JOptionPane.WARNING_MESSAGE);
                    return; // Do not close the dialog if the condition is not met
                }
            }
        }
        dispose();
    }

    /**
     * Adds an animal to the specified group based on the competition type.
     * This method updates both the general animal group and the specific competition group (courier or regular).
     * If the animal is not already present in the group, it is added.
     *
     * @param groupIndex The index of the group to which the animal should be added.
     * @param animal The Animal object to be added to the group.
     * @param competitionType The type of competition ("courier" or "regular") to which the animal belongs.
     */
    public void addAnimalToGroup(int groupIndex, Animal animal, String competitionType) {

        // Add the animal to the general animal group if the index is valid
        if (groupIndex >= 0 && groupIndex < animalTableModel.getGroupCount()) {
            List<Animal> generalGroup = animalTableModel.getAnimalGroups().get(groupIndex);
            if (!generalGroup.contains(animal)) {
                generalGroup.add(animal);
            }
        }

        // Add the animal to the appropriate competition group based on its type
        if (competitionType.equals("courier")) {
            groupIndex = AnimalTableModel.getCourierGroupIndex(groupIndex);
            if (groupIndex < AnimalTableModel.getCourierAnimalGroups().size()) {
                List<Animal> courierGroup = AnimalTableModel.getCourierAnimalGroups().get(groupIndex);
                if (!courierGroup.contains(animal)) {
                    courierGroup.add(animal);
                }
            } else {
                System.err.println("Error: Invalid courier group index " + groupIndex);
            }
        } else if (competitionType.equals("regular")) {
            groupIndex = AnimalTableModel.getRegularGroupIndex(groupIndex);
            if (groupIndex < AnimalTableModel.getRegularAnimalGroups().size()) {
                List<Animal> regularGroup = AnimalTableModel.getRegularAnimalGroups().get(groupIndex);
                if (!regularGroup.contains(animal)) {
                    regularGroup.add(animal);
                }
            } else {
                System.err.println("Error: Invalid regular group index " + groupIndex);
            }
        }

        animalTableModel.fireTableDataChanged();
    }
}


