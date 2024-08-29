package Graphics;

import Animals.Animal;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The `AnimalTableModel` class represents the data model for a table that displays groups of animals.
 * Each column represents a group, and each row represents an animal or an "Add Animal" button.
 */
public class AnimalTableModel extends AbstractTableModel {
    private static List<List<Animal>> animalGroups; // All groups info
    private static List<List<Animal>> courierAnimalGroups; // courier groups info
    private static List<List<Animal>> regularAnimalGroups; // regular groups info
    private static List<String> groupNames; // To hold the names of all groups
    private static List<String> competitionsTypes; // (regular/courier)
    private static final int MAX_ANIMALS_PER_GROUP = 4;
    private static List<String> groupsTypes;
    private static List<Integer> groupsTrackNumbers;

    /**
     * Constructor to initialize the AnimalTableModel.
     * It sets up the animal groups and group names.
     */
    public AnimalTableModel() {
        this.animalGroups = new ArrayList<>();
        this.groupNames = new ArrayList<>(); // Initialize the group names list
        this.competitionsTypes = new ArrayList<>();
        this.courierAnimalGroups = new ArrayList<>();
        this.regularAnimalGroups = new ArrayList<>();
        this.groupsTypes = new ArrayList<>();
        this.groupsTrackNumbers = new ArrayList<>();

    }

    /**
     * Returns the number of rows in the table.
     * This includes the maximum number of animals per group plus one additional row for the "Add Animal" button.
     *
     * @return the number of rows in the table
     */
    @Override
    public int getRowCount() {
        return MAX_ANIMALS_PER_GROUP + 2; // Allow for up to MAX_ANIMALS_PER_GROUP animals + 1 for the button + 1 for the finish time
    }

    /**
     * Returns the number of columns in the table.
     * Each column corresponds to a group of animals.
     *
     * @return the number of columns in the table
     */
    @Override
    public int getColumnCount() {
        return animalGroups.size(); // Return the size of the animalGroups list
    }

    public static int getAnimalsCountPerGroup(int index){
        return animalGroups.get(index).size();
    }

    /**
     * Returns the name of the column at the specified index.
     * This corresponds to the name of the group.
     *
     * @param column the index of the column
     * @return the name of the column
     */
    @Override
    public String getColumnName(int column) {
        return groupNames.get(column); // Return the group name for the given column index
    }

    /**
     * Returns the value at the specified cell in the table.
     * If the cell corresponds to an animal, it returns the animal's name and type.
     * If the cell is in the last row, it returns a button to add a new animal.
     *
     * @param rowIndex the index of the row
     * @param columnIndex the index of the column
     * @return the value at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < MAX_ANIMALS_PER_GROUP) {
            List<Animal> group = animalGroups.get(columnIndex);
            if (rowIndex < group.size()) {
                Animal animal = group.get(rowIndex);
                return animal.getName() + " - " + animal.getType(); // Display animal name and type
            }
        } else if (rowIndex == MAX_ANIMALS_PER_GROUP) {
            return new JButton("Add Animal");
        } else if (rowIndex == MAX_ANIMALS_PER_GROUP + 1) {
            String groupName = groupNames.get(columnIndex);

            if (CompetitionFrame.getScore(groupName) != null) {
                return "Finish time: " + formatDateToString(CompetitionFrame.getScore(groupName).getScore(groupName));
            } else if(Tick.isRaceStarted()){
                return "Finish time: " + formatDateToString(new Date());
            } else{
                return "Finish time: -";
            }
        }
        return null;
    }

    /**
     * Returns the class type for the column.
     * This is used by the table to render the appropriate component for each cell.
     *
     * @param columnIndex the index of the column
     * @return the class type of the column
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return JButton.class; // Return JButton class for button columns
    }

    /**
     * Determines if a cell is editable.
     * Only the last row, which contains the "Add Animal" button, is editable.
     *
     * @param rowIndex the index of the row
     * @param columnIndex the index of the column
     * @return true if the cell is editable, false otherwise
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return rowIndex == MAX_ANIMALS_PER_GROUP; // Only the last row (button) is editable
    }

    /**
     * Adds a new group to the table model with the specified group name.
     *
     * @param groupName the name of the group to add
     */
    public void addGroup(String groupName, String competitionType, String groupType, Integer trackNumber) {
        List<Animal> newGroup = new ArrayList<>();
        if (!animalGroups.contains(newGroup)) {
            animalGroups.add(newGroup);
        }

        if (competitionType.equals("regular") && !regularAnimalGroups.contains(newGroup)) {
            regularAnimalGroups.add(newGroup); // Track this group in regular groups
        } else if (competitionType.equals("courier")) {
            if (!courierAnimalGroups.contains(newGroup)) {
                courierAnimalGroups.add(newGroup);
            }
        }

        // Other tracking lists
        competitionsTypes.add(competitionType);
        groupNames.add(groupName);
        groupsTypes.add(groupType);
        groupsTrackNumbers.add(trackNumber);

        fireTableStructureChanged();
    }

    /**
     * Returns the number of groups in the table.
     *
     * @return the number of groups
     */
    public int getGroupCount() {
        return animalGroups.size(); // Return the number of groups
    }

    /**
     * Returns the name of the group at the specified index.
     *
     * @param index the index of the group
     * @return the name of the group
     */
    public static String getGroupName(int index) {
        return groupNames.get(index); // Return the name of the group at the given index
    }


    /**
     * Returns the list of all animal groups in the table.
     *
     * @return the list of animal groups
     */
    public static List<List<Animal>> getAnimalGroups() {
        return animalGroups;
    }

    /**
     * Retrieves the list of animal groups participating in a courier tournament.
     * Each group in the list corresponds to a set of animals competing in the courier-style race.
     *
     * @return A list of animal groups for the courier tournament.
     */
    public static List<List<Animal>> getCourierAnimalGroups() {
        return courierAnimalGroups;
    }

    /**
     * Retrieves the list of animal groups participating in a regular tournament.
     * Each group in the list corresponds to a set of animals competing in the regular-style race.
     *
     * @return A list of animal groups for the regular tournament.
     */
    public static List<List<Animal>> getRegularAnimalGroups() {
        return regularAnimalGroups;
    }


    /**
     * Retrieves the number of animals in a specific group.
     *
     * @param index the index of the group
     * @return the number of animals in the group at the specified index
     */
    public static int getNumberOfAnimalInTheGroup(int index) {
        return animalGroups.get(index).size();
    }

    /**
     * Retrieves the competition type for a specific group.
     *
     * @param index the index of the group
     * @return the competition type for the group at the specified index
     */
    public static String getCompetitionType(int index) {
        return competitionsTypes.get(index);
    }

    /**
     * Sets the competition type for a specific group.
     *
     * @param groupIndex the index of the group
     * @param type the competition type to set for the group
     */
    public void setCompetitionType(int groupIndex, String type){
        competitionsTypes.set(groupIndex, type);
    }


    /**
     * Retrieves the index of a group based on its name.
     *
     * @param groupName The name of the group whose index is to be retrieved.
     * @return The index of the group in the list, or -1 if the group is not found.
     */
    public static int getGroupIndex(String groupName) {
        return groupNames.indexOf(groupName);
    }

    /**
     * Retrieves the competition type of a group based on its index.
     *
     * @param index The index of the group in the list.
     * @return The competition type of the group as a String.
     */
    public static String getGroupCompetitionType(int index) {
        return competitionsTypes.get(index);
    }

    /**
     * Retrieves the type of a group (e.g., "Air", "Water", "Terrestrial") based on its index.
     *
     * @param index The index of the group in the list.
     * @return The type of the group as a String.
     */
    public static String getGroupType(int index) {
        return groupsTypes.get(index);
    }

    /**
     * Retrieves the track number assigned to a group based on its index.
     *
     * @param index The index of the group in the list.
     * @return The track number assigned to the group.
     */
    public static int getGroupTrackNumber(int index) {
        return groupsTrackNumbers.get(index);
    }

    /**
     * Retrieves the maximum number of animals that can be in a group.
     *
     * @return The maximum number of animals allowed per group.
     */
    public static int getMaxAnimalsPerGroup() {
        return MAX_ANIMALS_PER_GROUP;
    }


    /**
     * Formats a Date to a specific string format "HH:mm:ss" to return only the time.
     * @param date The date to format
     * @return The formatted time as a string
     */
    public static String formatDateToString(Date date) {
        // Define the time format you want
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        // Format and return only the time as a string
        return timeFormat.format(date);
    }

    /**
     * Removes an animal from the table by its name. If the animal is the only one
     * in its group, the group is also removed.
     *
     * @param animalName The name of the animal to remove.
     */
    public void removeAnimalByName(String animalName) {
        for (int groupIndex = 0; groupIndex < animalGroups.size(); groupIndex++) {
            List<Animal> group = animalGroups.get(groupIndex);

            for (int animalIndex = 0; animalIndex < group.size(); animalIndex++) {
                Animal animal = group.get(animalIndex);

                if (animal.getName().equals(animalName)) {
                    group.remove(animalIndex); // Remove the animal from the group

                    // If the group is empty after removal, remove the group
                    if (group.isEmpty()) {
                        removeGroupByIndex(groupIndex);
                    } else {
                        fireTableDataChanged(); // Update the table model after the change
                    }
                    return;
                }
            }
        }
        System.err.println("Error: Animal with name " + animalName + " not found.");
    }

    /**
     * Removes a group from the table by its index.
     *
     * @param groupIndex The index of the group to remove.
     */
    private void removeGroupByIndex(int groupIndex) {
        // Remove the group from the lists
        animalGroups.remove(groupIndex);
        groupNames.remove(groupIndex);
        competitionsTypes.remove(groupIndex);
        groupsTypes.remove(groupIndex);
        groupsTrackNumbers.remove(groupIndex);

        // Update regular or courier animal groups if applicable
        if (groupIndex < regularAnimalGroups.size()) {
            regularAnimalGroups.remove(groupIndex);
        } else if (groupIndex < courierAnimalGroups.size()) {
            courierAnimalGroups.remove(groupIndex);
        }

        fireTableStructureChanged(); // Update the table structure after the change
    }

    public static int getCourierGroupIndex(int generalIndex) {
        List<Animal> group = animalGroups.get(generalIndex);
        return courierAnimalGroups.indexOf(group);
    }

    public static int getRegularGroupIndex(int generalIndex) {
        List<Animal> group = animalGroups.get(generalIndex);
        return regularAnimalGroups.indexOf(group);
    }

}
