package Graphics;

import Animals.*;
import Olympics.Medal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The AddAnimalDialog class provides a user interface dialog for adding a new animal
 * to the competition. This dialog allows users to select an animal type, enter its
 * attributes, and assign it to a track. The dialog dynamically enables and disables
 * input fields based on the selected animal type to ensure only relevant information
 * is entered. Upon successful input, the animal is added to the competition panel.
 */
public class AddAnimalDialog extends JDialog {
    private JComboBox<String> animalTypeComboBox; // ComboBox for selecting the type of animal.
    private JComboBox<Integer> trackNumberComboBox; // ComboBox for selecting the track number for the competition.
    private JButton okButton; // Button to submit the form data.
    private int selectedTrackNumber; // Stores the selected track number from the ComboBox.
    private int selectedGroup; // Index of the selected group

    // Text fields for entering various attributes of the animal.
    private JTextField weightField, diveDepthField, noLegsField, wingspanField, areaOflivingField, breedField, altitudeOfFlightField, familyField, lengthField, foodTypeField, speedField, animalNameField;
    private JCheckBox castratedField; // Checkbox for whether a cat is castrated or not.
    double selectedWeight, selectedWingspan, selectedDiveDepth, selectedAltitudeOfFlight, selectedLength, selectedSpeed; // Variables to store parsed values from text fields.
    int selectedNoLegs; // Variable to store the number of legs (for applicable animals).
    private String selectedAreaOfLiving, selectedbreed, selectedFamily, selectedFoodType, selectedAnimalType, selectedAnimalName; // Variables to store text input values.
    boolean selectedIscastrated; // Variable to store the state of the castrated checkbox.

    // ComboBoxes for selecting specific enums related to the animal's characteristics.
    private JComboBox<Animal.Gender> genderComboBox; // ComboBox for selecting the gender of the animal.
    private JComboBox<Dolphin.WaterType> waterTypeComboBox; // ComboBox for selecting the water type for dolphins.
    private JComboBox<Snake.Poisonous> snakePoisonousComboBox; // ComboBox for selecting the level of poisonousness for snakes.

    // Arrays to populate the ComboBoxes with enum values.
    private Animal.Gender[] genders = {Animal.Gender.MALE, Animal.Gender.FEMALE, Animal.Gender.HERMAPHRODITE}; // Available genders.
    private Dolphin.WaterType[] waterTypes = {Dolphin.WaterType.Sea, Dolphin.WaterType.Sweet}; // Types of water environments for dolphins.
    private Snake.Poisonous[] poisonousTypes = {Snake.Poisonous.LOW, Snake.Poisonous.MEDIUM, Snake.Poisonous.HIGH}; // Levels of poisonousness for snakes.

    // Variables to store selected items from the ComboBoxes.
    private Animal.Gender selectedGender; // Selected gender from the gender ComboBox.
    private Dolphin.WaterType selectedWaterType; // Selected water type from the water type ComboBox.
    private Snake.Poisonous selectedPoisonous; // Selected level of poisonousness from the poisonousness ComboBox.

    private double inputNeededDistance; // The distance required to travel from start to finish
    private AtomicBoolean startFlag = new AtomicBoolean(false);
    private AtomicBoolean finishFlag = new AtomicBoolean(false);

    /**
     * Constructs a dialog for adding a new animal to a competition.
     * This dialog provides input fields for various animal attributes based on the selected competition type.
     * It dynamically updates available animal types and track numbers based on the competition type chosen.
     *
     * @param parentFrame             the parent frame to which this dialog is modal
     * @param selectedGroup           the index of the selected group
     */
    public AddAnimalDialog(JFrame parentFrame, int selectedGroup) {
        super(parentFrame, "Add Animal", true);
        this.selectedGroup = selectedGroup; // Initialize the selectedGroup
        this.selectedTrackNumber = AnimalTableModel.getGroupTrackNumber(selectedGroup);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;

       // Animal type
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Select Animal Type:"), gbc);
        gbc.gridx = 1;
        animalTypeComboBox = new JComboBox<>();
        add(animalTypeComboBox, gbc);
        updateAnimalTypeComboBox(extractTextInParentheses(AnimalTableModel.getGroupName(selectedGroup)));


        animalTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) animalTypeComboBox.getSelectedItem();
                updateFieldsVisibility(selectedType);
            }
        });

        // Animal name
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter Animal Name:"), gbc);
        gbc.gridx = 1;
        animalNameField = new JTextField(10);
        add(animalNameField, gbc);

        // Speed
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter Speed (non-negative):"), gbc);
        gbc.gridx = 1;
        speedField = new JTextField(10);
        add(speedField, gbc);

        // Weight
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter Weight (non-negative):"), gbc);
        gbc.gridx = 1;
        weightField = new JTextField(10);
        add(weightField, gbc);

        // Gender
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Choose Gender:"), gbc);
        gbc.gridx = 1;
        genderComboBox = new JComboBox<>(genders);
        add(genderComboBox, gbc);

        // Wingspan
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter Wingspan (non-negative):"), gbc);
        gbc.gridx = 1;
        wingspanField = new JTextField(10);
        add(wingspanField, gbc);

        // Dive Depth
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter Dive Depth (non-positive):"), gbc);
        gbc.gridx = 1;
        diveDepthField = new JTextField(10);
        add(diveDepthField, gbc);

        // num of legs
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter NoLegs (non-negative):"), gbc);
        gbc.gridx = 1;
        noLegsField = new JTextField(10);
        add(noLegsField, gbc);

        // area of living
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter Area of Living:"), gbc);
        gbc.gridx = 1;
        areaOflivingField = new JTextField(10);
        add(areaOflivingField, gbc);

        // Castrated
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter Is castrated:"), gbc);
        gbc.gridx = 1;
        castratedField = new JCheckBox();
        add(castratedField, gbc);

        // Breed
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter breed:"), gbc);
        gbc.gridx = 1;
        breedField = new JTextField(10);
        add(breedField, gbc);

        // Water type
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Choose water type:"), gbc);
        gbc.gridx = 1;
        waterTypeComboBox = new JComboBox<>(waterTypes);
        add(waterTypeComboBox, gbc);

        // Altitude of flight
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter altitude of flight (non-negative):"), gbc);
        gbc.gridx = 1;
        altitudeOfFlightField = new JTextField(10);
        add(altitudeOfFlightField, gbc);

        // Family
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter family name:"), gbc);
        gbc.gridx = 1;
        familyField = new JTextField(10);
        add(familyField, gbc);

        // Length
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter length (non-negative):"), gbc);
        gbc.gridx = 1;
        lengthField = new JTextField(10);
        add(lengthField, gbc);

        // Poisonous
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Choose Poisonous:"), gbc);
        gbc.gridx = 1;
        snakePoisonousComboBox = new JComboBox<>(poisonousTypes);
        add(snakePoisonousComboBox, gbc);

        // Food type
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Enter food type: "), gbc);
        gbc.gridx = 1;
        foodTypeField = new JTextField(10);
        add(foodTypeField, gbc);

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
     * Updates the animal type selection based on the specified competition type.
     * It populates the animal type combo box with relevant options according to whether the competition
     * is in the air, water, or on land.
     *
     * @param competitionType the type of competition (e.g., "Air", "Water", "Land") which determines
     *                        the kinds of animals that can participate.
     */
    private void updateAnimalTypeComboBox(String competitionType) {
        if (competitionType == null) {
            System.err.println("updateAnimalTypeComboBox: competitionType is null.");
            return;
        }

        String[] animals = {"-"};
        switch (competitionType) {
            case "Air":
                animals = new String[]{"-", "Eagle", "Pigeon"};
                break;
            case "Water":
                animals = new String[]{"-", "Alligator", "Dolphin", "Whale"};
                break;
            case "Terrestrial":
                animals = new String[]{"-", "Dog", "Cat", "Snake"};
                break;
        }
        animalTypeComboBox.setModel(new DefaultComboBoxModel<>(animals));
    }

    /**
     * Handles the logic for the "OK" button action within the AddAnimalDialog. This method
     * validates the input fields, constructs an animal object based on the provided inputs,
     * and adds it to the competition panel if valid. It ensures that all inputs are non-empty
     * and valid according to their constraints (e.g., non-negative numbers where required).
     * If any validation fails, an appropriate error message is displayed.
     */
    private void onOkButtonPressed() {
        selectedAnimalType = (String) animalTypeComboBox.getSelectedItem();
        selectedAnimalName = animalNameField.getText().trim(); // Use trim to remove leading and trailing spaces
        selectedGender = (Animal.Gender) genderComboBox.getSelectedItem();
        selectedAreaOfLiving = areaOflivingField.getText().trim();
        selectedIscastrated = castratedField.isSelected();
        selectedbreed = breedField.getText().trim();
        selectedWaterType = (Dolphin.WaterType) waterTypeComboBox.getSelectedItem();
        selectedFamily = familyField.getText().trim();
        selectedPoisonous = (Snake.Poisonous) snakePoisonousComboBox.getSelectedItem();
        selectedFoodType = foodTypeField.getText().trim();

        // Validate input before attempting to parse
        if (selectedAnimalType.equals("-")) {
            JOptionPane.showMessageDialog(this, "You must choose an animal type.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedAnimalName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Animal name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedAreaOfLiving.isEmpty() && areaOflivingField.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Area of living cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedbreed.isEmpty() && breedField.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Breed cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedFamily.isEmpty() && familyField.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Family cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedFoodType.isEmpty() && foodTypeField.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Food type cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (!weightField.getText().isEmpty()) {
                selectedWeight = Double.parseDouble(weightField.getText());
                if (selectedWeight < 0) {
                    JOptionPane.showMessageDialog(this, "Weight must be non-negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Weight cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!speedField.getText().isEmpty()) {
                selectedSpeed = Double.parseDouble(speedField.getText());
                if (selectedSpeed < 0 )  {
                    JOptionPane.showMessageDialog(this, "Speed must be non-negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Speed cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!wingspanField.getText().isEmpty()) {
                selectedWingspan = Double.parseDouble(wingspanField.getText());
                if (selectedWingspan < 0 && wingspanField.isEnabled()) {
                    JOptionPane.showMessageDialog(this, "Wingspan must be non-negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (wingspanField.isEnabled()) {
                JOptionPane.showMessageDialog(this, "Wingspan cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            if (!diveDepthField.getText().isEmpty()) {
                selectedDiveDepth = Double.parseDouble(diveDepthField.getText());
                if (selectedDiveDepth > 0 && diveDepthField.isEnabled()) {
                    JOptionPane.showMessageDialog(this, "DiveDepth must be non-positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (selectedDiveDepth < WaterAnimal.MAX_DIVE && diveDepthField.isEnabled()) {
                    JOptionPane.showMessageDialog(this, "Please re-enter dive depth, you have exceeded the maximum dive (-800).", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (diveDepthField.isEnabled()) {
                JOptionPane.showMessageDialog(this, "DiveDepth cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            if (!noLegsField.getText().isEmpty()) {
                selectedNoLegs = Integer.parseInt(noLegsField.getText());
                if (selectedNoLegs < 0 && noLegsField.isEnabled()) {
                    JOptionPane.showMessageDialog(this, "NoLegs must be non-negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (noLegsField.isEnabled()) {
                JOptionPane.showMessageDialog(this, "NoLegs cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!altitudeOfFlightField.getText().isEmpty()) {
                selectedAltitudeOfFlight = Double.parseDouble(altitudeOfFlightField.getText());
                if (selectedAltitudeOfFlight < 0 && altitudeOfFlightField.isEnabled()) {
                    JOptionPane.showMessageDialog(this, "altitude of flight must be non-negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (altitudeOfFlightField.isEnabled()) {
                JOptionPane.showMessageDialog(this, "altitude of flight cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!lengthField.getText().isEmpty()) {
                selectedLength = Double.parseDouble(lengthField.getText());
                if (selectedLength < 0 && lengthField.isEnabled()) {
                    JOptionPane.showMessageDialog(this, "Length must be non-negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (lengthField.isEnabled()) {
                JOptionPane.showMessageDialog(this, "Length cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // add animal to the system
            Animal animal = createAnimal(selectedAnimalType);
            if (animal != null) {
                animal.setTrackNumber(selectedTrackNumber);
                CompetitionPanel.addAnimal(animal);
                String category = CompetitionFrame.getCategoryFromAnimal(animal);
                DefaultTableModel model = CompetitionFrame.getInfoTableModel();
                model.addRow(new Object[]{selectedAnimalName, category, selectedAnimalType, selectedSpeed, animal.getEnergy(), 0, animal.getEnergyPerMeter()});
                ((CompetitionFrame) getParent()).getCompetitionPanel().repaint();


                AddCompetitionDialog parentDialog = CompetitionFrame.getDialog();
                if (parentDialog != null) {
                    parentDialog.addAnimalToGroup(selectedGroup, animal, AnimalTableModel.getGroupCompetitionType(selectedGroup));
                   // Animal added to the group

                }


            }

            //Reset the distances of all animals to 0 and update info table
            for(int i=0; i<AnimalTableModel.getAnimalGroups().size(); i++){
                for(int j=0; j<AnimalTableModel.getAnimalGroups().get(i).size(); j++){
                    AnimalTableModel.getAnimalGroups().get(i).get(j).setTotalDistance(0.0);
                    CompetitionFrame.updateAnimalInfo(AnimalTableModel.getAnimalGroups().get(i).get(j));
                }
            }

            dispose(); // Close the dialog

        } catch (NumberFormatException ex) {
            System.out.println("Failed to parse: " + weightField.getText() + ", " + speedField.getText() + ", " + wingspanField.getText() + ", " + diveDepthField.getText() + ", " + noLegsField.getText() + ", " + altitudeOfFlightField.getText() + ", " + lengthField.getText());
            System.out.println("Error message: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for weight and speed.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates an animal object based on the specified type with the class-level fields
     * that were set from user input in the dialog. This method supports the creation of
     * various types of animals, setting the relevant properties specific to each type.
     * If the type is unrecognized, it returns null.
     *
     * @param type The type of animal to create, as selected in the animalTypeComboBox.
     * @return An initialized Animal object of the specified type, or null if the type is not recognized.
     */
    private Animal createAnimal(String type) {
        switch (type) {
            case "Eagle":
                this.inputNeededDistance = 609.0;
                return new Eagle(selectedAnimalName, selectedGender, selectedWeight, selectedSpeed, new Medal[0], selectedWingspan, selectedAltitudeOfFlight);
            case "Pigeon":
                this.inputNeededDistance = 609.0;
                return new Pigeon(selectedAnimalName, selectedGender, selectedWeight, selectedSpeed, new Medal[0], selectedWingspan, selectedFamily);
            case "Alligator":
                this.inputNeededDistance = 554.0;
                return new Alligator(selectedAnimalName, selectedGender, selectedWeight, selectedSpeed, new Medal[0], selectedDiveDepth, selectedNoLegs, selectedAreaOfLiving);
            case "Dolphin":
                this.inputNeededDistance = 554.0;
                return new Dolphin(selectedAnimalName, selectedGender, selectedWeight, selectedSpeed, new Medal[0], selectedDiveDepth, selectedWaterType);
            case "Whale":
                this.inputNeededDistance = 554.0;
                return new Whale(selectedAnimalName, selectedGender, selectedWeight, selectedSpeed, new Medal[0], selectedDiveDepth, selectedFoodType);
            case "Dog":
                this.inputNeededDistance = 2248.0;
                return new Dog(selectedAnimalName, selectedGender, selectedWeight, selectedSpeed, new Medal[0], selectedNoLegs, selectedbreed);
            case "Cat":
                this.inputNeededDistance = 2248.0;
                return new Cat(selectedAnimalName, selectedGender, selectedWeight, selectedSpeed, new Medal[0], selectedNoLegs, selectedIscastrated);
            case "Snake":
                this.inputNeededDistance = 2248.0;
                return new Snake(selectedAnimalName, selectedGender, selectedWeight, selectedSpeed, new Medal[0], selectedNoLegs, selectedLength, selectedPoisonous);
            default:
                return null;  // Return null if the animal type is not recognized
        }
    }

    /**
     * Adjusts the visibility and availability of input fields in the dialog based on the selected animal type.
     * This method enables or disables fields that are relevant to the specific characteristics of the selected
     * animal type, ensuring that users can only input valid data for the type of animal they are creating.
     *
     * @param animalType The type of animal selected, used to determine which fields should be enabled.
     */
    private void updateFieldsVisibility(String animalType) {
        // Determine if the animal type is water animal.
        boolean isWaterAnimal = (animalType.equals("Dolphin") || animalType.equals("Alligator") || animalType.equals("Whale"));
        // Determine if the animal type is terrestrial animal.
        boolean isTerrestrialAnimal = (animalType.equals("Dog") || animalType.equals("Cat") || animalType.equals("Snake") || animalType.equals("Alligator"));
        // Determine if the animal type is air animal.
        boolean isAirAnimal = (animalType.equals("Eagle") || animalType.equals("Pigeon"));

        // Enable the dive depth field only for water animals.
        diveDepthField.setEnabled(isWaterAnimal);
        // Enable the number of legs field only for terrestrial animals.
        noLegsField.setEnabled(isTerrestrialAnimal);
        // Enable the wingspan field only for air animals.
        wingspanField.setEnabled(isAirAnimal);
        // Enable the castrated checkbox only for cats.
        castratedField.setEnabled(animalType.equals("Cat"));
        // Enable the breed field only for dogs.
        breedField.setEnabled(animalType.equals("Dog"));
        // Enable the water type combo box only for dolphins.
        waterTypeComboBox.setEnabled(animalType.equals("Dolphin"));
        // Enable the altitude of flight field only for eagles.
        altitudeOfFlightField.setEnabled(animalType.equals("Eagle"));
        // Enable the family field only for pigeons.
        familyField.setEnabled(animalType.equals("Pigeon"));
        // Enable the length field only for snakes.
        lengthField.setEnabled(animalType.equals("Snake"));
        // Enable the poisonous combo box only for snakes.
        snakePoisonousComboBox.setEnabled(animalType.equals("Snake"));
        // Enable the area of living field only for alligators.
        areaOflivingField.setEnabled(animalType.equals("Alligator"));
        // Enable the food type field only for whales.
        foodTypeField.setEnabled(animalType.equals("Whale"));
    }

    /**
     * Extracts the text found between the first pair of parentheses in a given string.
     * If the string contains no parentheses, or if the closing parenthesis comes before the opening one,
     * the method returns null.
     *
     * @param str the input string from which to extract the text
     * @return the text inside the first pair of parentheses, or null if the parentheses are not properly found
     */
    public static String extractTextInParentheses(String str) {
        int startIndex = str.indexOf('(');
        int endIndex = str.indexOf(')');

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return str.substring(startIndex + 1, endIndex);
        }
        return null;
    }
}
