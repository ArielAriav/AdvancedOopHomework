package Graphics;

import Animals.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represents the panel where the competition takes place.
 * It manages the display and animation of animals participating in the competition.
 */
public class CompetitionPanel extends JPanel {
    private static ImageIcon backgroundImage; // Background image of the competition track.
    static private Animal[] animals; // Array of animals participating in the competition.
    private static String[] openCompetitions; // Array of competition names that are currently open.
    public static double getBackgroundWidth(){
        return backgroundImage.getIconWidth();
    }

    /**
     * Constructor for the CompetitionPanel class. Initializes the panel by setting up
     * the background image, layout, and initial configurations for the competition.
     */
    public CompetitionPanel() {
        // Load and set the background image from a specified path on the local file system.
        backgroundImage = new ImageIcon("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\competitionBackground2.png");

        // Initialize the list of animals participating in the competition to an empty array.
        animals = new Animal[0];

        // Set the layout of the panel to BorderLayout, allowing components to be added with positional constraints.
        setLayout(new BorderLayout());

        // Initialize the list of open competitions to an empty array.
        openCompetitions = new String[0];

        // Create a new JPanel to hold additional components or images, setting its layout to a 5-row grid.
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(5, 1));  // A grid layout allows for orderly placement in rows.
        imagePanel.setOpaque(false);  // Make the panel transparent so the background image can be seen.

        // Add the image panel to the center of this CompetitionPanel.
        add(imagePanel, BorderLayout.CENTER);
    }

    /**
     * Returns the preferred size of the component based on the dimensions of the background image.
     *
     * @return A {@link Dimension} with width and height set to the size of the background image.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
    }

    /**
     * Adds a new animal to the competition.
     * This method appends a new animal to the current array of animals participating in the competition.
     * It ensures that the array dynamically adjusts to accommodate any number of animals.
     * @param newAnimal the new animal to be added to the array of competitors.
     */
    public static void addAnimal(Animal newAnimal) {
        // Create a new array larger by one than the current array to accommodate the new animal.
        Animal[] newArr = new Animal[animals.length + 1];
        // Copy existing animals from the old array to the new array.
        for(int i = 0; i < animals.length; i++) {
            newArr[i] = animals[i];
        }
        // Add the new animal to the last position in the new array.
        newArr[animals.length] = newAnimal;
        // Update the reference of the static array to point to the new array.
        animals = newArr;
    }

    /**
     * Returns the array of animals participating in the competition.
     * @return array of animals.
     */
    public static Animal[] getAnimals() {
        return animals;
    }

    /**
     * Clears an animal from the competition by name.
     * @param animalName the name of the animal to clear.
     * @return true if the animal was found and cleared, false otherwise.
     */
    public boolean clearAnimal(String animalName) {
        int indexToRemove = -1; // Initialize with -1 to signify no animal found initially.

        // Loop through the list of animals to find the one with the given name.
        for (int i = 0; i < animals.length; i++) {
            if (animals[i].getName().equals(animalName)) {
                indexToRemove = i; // Store the index of the animal to be removed.
                break; // Break the loop once the animal is found.
            }
        }

        // Check if an animal was found.
        if (indexToRemove != -1) {
            Animal[] newAnimals = new Animal[animals.length - 1]; // Create a new array one less than the original.

            // Copy all animals except the one to be removed into the new array
            for (int i = 0, j = 0; i < animals.length; i++) {
                if (i != indexToRemove) {
                    newAnimals[j++] = animals[i];
                }
            }
            setAnimals(newAnimals); // Update the animals array to the new array without the removed animal.

            //remove animal from all groups table
            CompetitionFrame.getAnimalTableModel().removeAnimalByName(animalName);

            //remove animal from info table
            CompetitionFrame.getFrame().removeAnimalFromInfoTableByName(animalName);

            repaint(); // Repaint to update the UI
            return true; // Return true to indicate the animal was successfully removed.
        }
        return false; // Return false if no animal was found with the given name.
    }

    /**
     * Paints the background image and animals on the panel.
     * Positions and updates animals if the race has not started.
     *
     * @param g The {@link Graphics} object for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image scaled to the size of the panel.
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        // Position the animals if the race hasn't started.
        if (!CompetitionFrame.isRaceStarted()) {
            // Set start positions for courier animals
            for (int i = 0; i < AnimalTableModel.getCourierAnimalGroups().size(); i++) {
                List<Animal> courierGroup = AnimalTableModel.getCourierAnimalGroups().get(i);
                for (int k = 0; k < courierGroup.size(); k++) {
                    // Pass the group index to ensure unique positioning
                    courierGroup.get(k).setStartPointCourier(getHeight(), courierGroup.size(), k + 1);
                }
            }

            // Set start positions for regular animals
            for (int j = 0; j < AnimalTableModel.getRegularAnimalGroups().size(); j++) {
                List<Animal> regularGroup = AnimalTableModel.getRegularAnimalGroups().get(j);
                for (int k = 0; k < regularGroup.size(); k++) {
                    // Positioning logic for regular animals
                    regularGroup.get(k).setStartPoint(getHeight());
                }
            }
        }

        // Draw all animals
        for (Animal animal : animals) {
            animal.drawObject(g);
        }
    }

    /**
     * Returns the current array of open competition names.
     * This method provides access to the list of competitions that are currently available.
     * @return An array of strings representing the names of open competitions.
     */
    protected static String[] getOpenCompetitions() {
        return openCompetitions;
    }

    /**
     * Sets the list of open competitions to a new array of competition names.
     * This method is used to update the current state of open competitions in the system.
     * @param openCompetitions An array of strings representing the new list of open competition names.
     */
    public static void setOpenCompetitions(String[] openCompetitions) {
        CompetitionPanel.openCompetitions = openCompetitions;
    }

    /**
     * Updates the array of animals participating in the competition.
     * This method is critical for maintaining the current list of animals in the competition,
     * allowing it to be dynamically adjusted during runtime.
     * @param newAnimals An array of Animal objects representing the new list of competition participants.
     */
    protected static void setAnimals(Animal[] newAnimals) {
        animals = newAnimals;
    }
}
