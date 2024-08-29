package Graphics;

import javax.swing.*;
import java.awt.*;

/**
 * The AddGroupDialog class provides a user interface dialog for adding a new group
 * to the competition. It allows users to enter the group's name and type, and ensures
 * that the group is added to the competition panel.
 */
public class AddGroupDialog extends JDialog {
    private JTextField groupNameField;
    private static JComboBox<String> groupTypeComboBox;
    private static JRadioButton regularButton;
    private JRadioButton courierButton;
    private ButtonGroup competitionTypeGroup;
    private JButton okButton;
    private AddCompetitionDialog parentDialog;
    private String selectedCompetitionType = null;
    private int courierGroupsCounter = 0;
    private int regularGroupsCounter = 0;
    private static JComboBox trackNumberComboBox;
    private static int selectedTrackNumber = -1;

    /**
     * Constructs a dialog for adding a new group to a competition.
     * This dialog provides input fields for entering the group's name and selecting its type.
     *
     * @param parentFrame  the parent frame to which this dialog is modal
     * @param parentDialog the parent dialog to which this dialog belongs
     */
    // Constructor for AddGroupDialog
    public AddGroupDialog(JFrame parentFrame, AddCompetitionDialog parentDialog) {
        super(parentFrame, "Add Group", true);
        this.parentDialog = parentDialog;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;

        // Group name
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter Group Name:"), gbc);
        gbc.gridx = 1;
        groupNameField = new JTextField(10);
        add(groupNameField, gbc);

        // Group type
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Select Group Type:"), gbc);
        gbc.gridx = 1;
        groupTypeComboBox = new JComboBox<>(new String[]{"-", "Air", "Water", "Terrestrial"});
        add(groupTypeComboBox, gbc);

        // Add ActionListener to update track numbers based on the selected group type
        groupTypeComboBox.addActionListener(e -> {
            String selectedGroupType = (String) groupTypeComboBox.getSelectedItem();
            updateTrackNumberComboBox(selectedGroupType);
        });

        // Competition type
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Select Competition Type:"), gbc);

        regularButton = new JRadioButton("Regular");
        courierButton = new JRadioButton("Courier");
        competitionTypeGroup = new ButtonGroup();
        competitionTypeGroup.add(regularButton);
        competitionTypeGroup.add(courierButton);

        JPanel competitionTypePanel = new JPanel();
        competitionTypePanel.add(regularButton);
        competitionTypePanel.add(courierButton);

        gbc.gridx = 1;
        add(competitionTypePanel, gbc);

        // Track number
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Select Track Number:"), gbc);
        gbc.gridx = 1;
        trackNumberComboBox = new JComboBox<>();
        add(trackNumberComboBox, gbc);

        // OK button
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Span two columns
        okButton = new JButton("OK");
        okButton.addActionListener(e -> onOkButtonPressed());
        add(okButton, gbc);

        pack();
        setLocationRelativeTo(parentFrame);
    }

    /**
     * Handles the logic for the "OK" button action within the AddGroupDialog.
     * This method validates the input fields, constructs a new group based on the provided inputs,
     * and adds it to the competition panel if valid. It ensures that all inputs are non-empty
     * and valid according to their constraints.
     */
    private void onOkButtonPressed() {
        String groupName = groupNameField.getText().trim();
        String groupType = (String) groupTypeComboBox.getSelectedItem();

        if (regularButton.isSelected()) {
            selectedCompetitionType = "regular";
            regularGroupsCounter += 1;
        } else if (courierButton.isSelected()) {
            selectedCompetitionType = "courier";
            courierGroupsCounter += 1;
        }

        if (groupType.equals("-") || groupType == null) {
            JOptionPane.showMessageDialog(null, "Group type cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (groupName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Group name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (competitionTypeGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Competition type cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectedTrackNumber = (Integer) trackNumberComboBox.getSelectedItem();

        parentDialog.addGroup(groupName, groupType, selectedCompetitionType, selectedTrackNumber);
        dispose();
        ((CompetitionFrame) parentDialog.getParent()).openAddAnimalDialogForGroup(parentDialog.getAnimalTableModel().getGroupCount() - 1);
    }

    /**
     * Retrieves the parent dialog associated with this dialog.
     *
     * @return the parent `AddCompetitionDialog` instance
     */
    public AddCompetitionDialog getParent(){
        return parentDialog;
    }

    /**
     * Updates the track number combo box based on the selected competition type.
     * This method configures the available track numbers for animals based on their
     * competition environment: terrestrial (land), marine (water), or aerial (air).
     *
     * @param competitionType the type of competition (e.g., "Land", "Water", "Air") which affects
     *                        the range of track numbers available.
     */
    private static void updateTrackNumberComboBox(String competitionType) {
        if (competitionType == null) {
            System.err.println("updateTrackNumberComboBox: competitionType is null.");
            return;
        }

        Integer[] tracks;
        switch (competitionType) {
            case "Terrestrial":
                tracks = new Integer[]{1};  // Only track 1 for terrestrial animals
                break;
            case "Water":
                tracks = new Integer[]{1, 2, 3, 4};  // Tracks 1 to 4 for marine animals
                break;
            case "Air":
                tracks = new Integer[]{1, 2, 3, 4, 5};  // Tracks 1 to 5 for air animals
                break;
            default:
                tracks = new Integer[]{1};  // Default to 1 if no match
                break;
        }
        trackNumberComboBox.setModel(new DefaultComboBoxModel<>(tracks));
    }
}
