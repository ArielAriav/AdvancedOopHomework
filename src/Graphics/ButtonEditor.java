package Graphics;

import javax.swing.*;
import java.awt.*;

/**
 * ButtonEditor class extends DefaultCellEditor to create an editor for a cell that contains a JButton.
 * The button in the table cell opens a dialog for adding an animal to a specific group when pressed.
 */
public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;

    /**
     * Constructor for the ButtonEditor class.
     *
     * @param checkBox   The JCheckBox used to determine the editing state (required by DefaultCellEditor).
     * @param groupIndex The index of the group to which the animal will be added.
     * @param myFrame    The parent frame of the application (should be an instance of CompetitionFrame).
     */
    public ButtonEditor(JCheckBox checkBox, int groupIndex, JFrame myFrame) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(false);
        button.addActionListener(e -> {
            fireEditingStopped();
            // Get the parent frame
            if (myFrame instanceof CompetitionFrame) {
                CompetitionFrame competitionFrame = (CompetitionFrame) myFrame;
                competitionFrame.openAddAnimalDialogForGroup(groupIndex);
            }
        });
    }

    /**
     * Prepares the button for editing within the table cell.
     *
     * @param table      The JTable that is asking the editor to edit.
     * @param value      The value of the cell being edited.
     * @param isSelected Whether the cell is selected.
     * @param row        The row of the cell being edited.
     * @param column     The column of the cell being edited.
     * @return The component to be displayed in the editor (the button in this case).
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof JButton) {
            JButton btn = (JButton) value;
            label = btn.getText(); // Get the button text
        } else {
            label = (value == null) ? "" : value.toString(); // If the value is not a button, use the value as text
        }
        button.setText(label);
        return button;
    }

    /**
     * Returns the value contained in the editor (the label of the button).
     *
     * @return The label (text) of the button.
     */
    public Object getCellEditorValue() {
        return label; // Returns the text of the button
    }

    /**
     * Stops editing the cell and returns whether the editing was successful.
     *
     * @return true if editing was successfully stopped.
     */
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    /**
     * Notifies all listeners that editing has stopped.
     * This method is called when the button is pressed.
     */
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
