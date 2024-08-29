package Graphics;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * ButtonRenderer is a custom renderer for rendering buttons in a JTable cell.
 * It extends JButton and implements the TableCellRenderer interface.
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

    /**
     * Constructor for ButtonRenderer.
     * Sets the button to be non-opaque (transparent background).
     */
    public ButtonRenderer() {
        setOpaque(false);
    }

    /**
     * This method is called by the JTable to get the component that should be
     * rendered in the cell. It prepares the button component to be displayed.
     *
     * @param table      The JTable that is asking the renderer to draw.
     * @param value      The value of the cell to be rendered (usually the button).
     * @param isSelected Whether the cell is selected.
     * @param hasFocus   Whether the cell has focus.
     * @param row        The row index of the cell being drawn.
     * @param column     The column index of the cell being drawn.
     * @return The component used for drawing the cell (the button).
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JButton) { // If the value is a JButton
            JButton button = (JButton) value;
            setText(button.getText()); // Copy the button's text
            setBackground(button.getBackground()); // Copy the button's background color
        } else {
            setText(value != null ? value.toString() : ""); // Set text from the value if it's not a button
        }

        // Check if the text is "Add Animal" and set the color accordingly
        if (this.getText().equals("Add Animal")) {
            this.setForeground(Color.RED);
        } else {
            this.setForeground(Color.BLACK); // Reset the color for other text
        }

        return this; // Return this button as the renderer component
    }
}

