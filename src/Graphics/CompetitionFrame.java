package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.table.DefaultTableModel;
import Animals.*;
import Competitions.*;
import java.util.List;

/**
 * The CompetitionFrame class represents the main frame for the competition application.
 * It provides the graphical user interface (GUI) for managing competitions, adding animals,
 * and starting/stopping the race. This class is responsible for initializing the components,
 * setting up the menu and control panels, and handling user interactions with the application.
 */
public class CompetitionFrame extends JFrame {
    JTable infoTable = new JTable(infoTableModel);
    private final int ENERGY_TO_ADD = 100;
    static CompetitionPanel competitionPanel;
    private static Timer raceTimer;
    private static JButton playButton; // Declare playButton at class level for accessibility
    private static boolean raceStarted = false;
    private static AddCompetitionDialog dialog;
    private static DefaultTableModel infoTableModel = new DefaultTableModel(new String[]{"Animal", "Category", "Type", "Speed", "Energy Amount", "Distance", "Energy Consumption"}, 0);
    private static CompetitionFrame instance;
    private static AnimalTableModel animalTableModel;
    protected static java.util.List<AnimalThread> courierAnimalThreads = new ArrayList<>(); // List of AnimalThreads for handling animal movements.
    protected static java.util.List<AnimalThread> regularAnimalThreads = new ArrayList<>(); // List of AnimalThreads for handling animal movements.
    private static List<Referee> allReferees = new ArrayList<>();
    private static List<Scores> allScores = new ArrayList<>();
    private static Tick tick;

    /**
     * Constructs a new CompetitionFrame.
     * Sets the title, size, and default close operation for the frame.
     * Initializes the competition panel and adds it to the center of the frame.
     * Sets up the menu and control panel for the frame.
     */
    public CompetitionFrame() {

        // Initialize the AnimalTableModel once and reuse it
        animalTableModel = new AnimalTableModel();

        instance = this;
        setTitle("Competition");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        competitionPanel = new CompetitionPanel();
        add(competitionPanel, BorderLayout.CENTER);
        setupMenuAndControls();
    }

    /**
     * Sets up the menu bar and control panel for the frame.
     * Creates and sets the menu bar using the setupMenuBar method.
     * Creates and adds the control panel to the bottom (south) of the frame using the setupControlPanel method.
     */
    private void setupMenuAndControls() {
        JMenuBar menuBar = setupMenuBar();
        setJMenuBar(menuBar);
        JPanel controlPanel = setupControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets up the menu bar for the frame.
     * Creates and configures the "File" menu with an "Exit" menu item that closes the application.
     * Creates and configures the "Help" menu with a "Help" menu item that displays help information.
     * Adds the "File" and "Help" menus to the menu bar.
     *
     * @return The configured JMenuBar instance.
     */
    private JMenuBar setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("Help");
        helpMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Home work 2\nGUI", "Help", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(helpMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    /**
     * Sets up the control panel with various buttons for interacting with the competition.
     * Creates buttons for adding competitions, adding animals, clearing animals, feeding animals,
     * displaying information, starting/stopping the race, and exiting the application.
     * Adds action listeners to each button to handle their respective actions.
     * Adds the buttons to the control panel.
     *
     * @return The configured JPanel instance containing the control buttons.
     */
    private JPanel setupControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(2, 7)); // Adjusted to 2 rows

        JButton addCompetitionButton = new JButton("Manage Competition");
        JButton clearButton = new JButton("Clear");
        JButton eatButton = new JButton("Eat");
        JButton infoButton = new JButton("Info");
        JButton exitButton = new JButton("Exit");
        playButton = new JButton("Start Race"); // Initialize the play button here

        addCompetitionButton.addActionListener(this::onAddCompetitionPressed);
        clearButton.addActionListener(this::promptForAnimalNameAndClear);
        eatButton.addActionListener(this::showAnimalSelectionDialog);
        infoButton.addActionListener(this::showInfoDialog);
        exitButton.addActionListener(e -> System.exit(0));
        playButton.addActionListener(this::toggleRace);

        controlPanel.add(addCompetitionButton);
        controlPanel.add(clearButton);
        controlPanel.add(eatButton);
        controlPanel.add(infoButton);
        controlPanel.add(playButton);
        controlPanel.add(exitButton);

        return controlPanel;
    }

    /**
     * Opens the AddAnimalDialog for a specific group.
     * Validates the conditions for adding animals to the selected group based on competition type and group size.
     *
     * @param groupIndex The index of the group to which the animal is to be added.
     */
    public void openAddAnimalDialogForGroup(int groupIndex) {
        // Check if the group already has an animal and the competition type is regular
        if (animalTableModel.getNumberOfAnimalInTheGroup(groupIndex) >= 1 && "regular".equals(animalTableModel.getCompetitionType(groupIndex))) {
            // let the user know he can't add more animals to this group
            JOptionPane.showMessageDialog(
                    this,
                    "A regular competition group cannot contain more than one animal.\nPlease choose a different group.",
                    "Competition Type Error",
                    JOptionPane.ERROR_MESSAGE
            );
            // The user is automatically returned to the previous window after clicking Ok

            //Check if the group already has "max per group" members
        } else if(AnimalTableModel.getAnimalsCountPerGroup(groupIndex) >= AnimalTableModel.getMaxAnimalsPerGroup()){
            // let the user know he can't add more animals to this group
            JOptionPane.showMessageDialog(
                    this,
                    "A group cannot have more than " + AnimalTableModel.getMaxAnimalsPerGroup() + " animals.",
                    "Number of participants Error",
                    JOptionPane.ERROR_MESSAGE
            );
            // The user is automatically returned to the previous window after clicking Ok
        }else {
            // Open the AddAnimalDialog if the conditions are met
            AddCompetitionDialog parentDialog = getDialog();
            String groupType = parentDialog.getSelectedGroupType(groupIndex);
            AddAnimalDialog addAnimalDialog = new AddAnimalDialog(this, groupIndex);
            addAnimalDialog.setVisible(true);
        }
    }

    /**
     * Toggles the start of the race for both regular and courier tournaments, and initializes the referees for each group.
     * This method handles the initialization and starting of threads responsible for managing the competition, including
     * the tick thread, regular tournament thread, courier tournament thread, and referee threads.
     *
     * @param e The ActionEvent triggered by the UI component that invokes this method.
     */
    private void toggleRace(ActionEvent e) {

        // Start the tick thread
        Tick tick = new Tick(AnimalTableModel.getAnimalGroups(), allReferees);  // Pass referees here
        this.tick = tick;
        new Thread(tick).start();

        // Start the regular tournament
        if (!AnimalTableModel.getRegularAnimalGroups().isEmpty()) {
            AtomicBoolean startFlag = new AtomicBoolean(false);
            TournamentThread regularTournamentThread = new TournamentThread(startFlag, AnimalTableModel.getRegularAnimalGroups().size(), "regular");
            Thread thread = new Thread(regularTournamentThread);
            thread.start();

            RegularTournament regularTournament = new RegularTournament(
                    AnimalTableModel.getRegularAnimalGroups(),
                    AnimalTableModel.getRegularAnimalGroups().size(),
                    "regular",
                    regularTournamentThread,
                    startFlag
            );
            startFlag.set(true); // Signal the start of the regular tournament
        }

        // Start the courier tournament
        if (!AnimalTableModel.getCourierAnimalGroups().isEmpty()) {
            AtomicBoolean startFlag = new AtomicBoolean(false);
            TournamentThread courierTournamentThread = new TournamentThread(startFlag, AnimalTableModel.getCourierAnimalGroups().size(), "courier");
            new Thread(courierTournamentThread).start();

            // Determine group type more reliably
            String groupType = null;
            for (List<Animal> group : AnimalTableModel.getCourierAnimalGroups()) {
                if (!group.isEmpty()) {
                    groupType = group.get(0).getCategoryFromAnimal();  // Assuming all animals in the group have the same category
                    break;  // Use the first non-empty group
                }
            }

            if (groupType != null) {
                CourierTournament courierTournament = new CourierTournament(
                        AnimalTableModel.getCourierAnimalGroups(),
                        startFlag,
                        groupType,
                        courierTournamentThread
                );
                startFlag.set(true); // Signal the start of the courier tournament
            } else {
                // Handle the case where no valid group type was found (e.g., log an error or throw an exception)
                System.err.println("No valid group type found for the courier tournament.");
            }
        }

        // Initialize referees for each group
        for (int i = 0; i < AnimalTableModel.getAnimalGroups().size(); i++) {
            String competitionType = AnimalTableModel.getGroupCompetitionType(i);
            Referee referee = new Referee(AnimalTableModel.getGroupName(i), competitionType);
            Thread refereeThread = new Thread(referee);
            refereeThread.start();
            CompetitionFrame.addReferee(referee);  // add the referee
        }
    }

    /**
     * Prompts the user to select an animal from a list and attempts to clear the selected animal from the display.
     * If there are no animals available, shows an error message.
     * If an animal is selected and confirmed, it is removed from the competition panel and a success message is shown.
     * If no animal with the selected name is found, shows an error message.
     *
     * @param e The ActionEvent triggered by the clear button click.
     */
    private void promptForAnimalNameAndClear(ActionEvent e) {
        if (competitionPanel.getAnimals().length > 0) {
            JComboBox<String> animalComboBox = new JComboBox<>();
            for (Animal animal : competitionPanel.getAnimals()) {
                animalComboBox.addItem(animal.getName());
            }

            int result = JOptionPane.showConfirmDialog(this, animalComboBox, "Select Animal to Clear", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String selectedAnimalName = (String) animalComboBox.getSelectedItem();
                boolean cleared = competitionPanel.clearAnimal(selectedAnimalName);
                if (cleared) {
                    JOptionPane.showMessageDialog(this, selectedAnimalName + " cleared from the system successfully", "Animal Cleared", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No animal found with the name: " + selectedAnimalName, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No animals available.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Opens the AddCompetitionDialog to allow the user to add a new competition.
     *
     * @param e The ActionEvent triggered by the add competition button click.
     */
    private void openAddCompetitionDialog(ActionEvent e) {
        dialog = new AddCompetitionDialog(this, animalTableModel);
        dialog.setVisible(true);
    }


    /**
     * Displays a dialog to select an animal and allows the selected animal to eat and gain energy.
     *
     * @param e The ActionEvent triggered by the eat button click.
     */
    private void showAnimalSelectionDialog(ActionEvent e) {
        if (competitionPanel.getAnimals().length > 0) {
            JComboBox<String> animalComboBox = new JComboBox<>();
            for (Animal animal : competitionPanel.getAnimals()) {
                animalComboBox.addItem(animal.getName());
            }

            int result = JOptionPane.showConfirmDialog(this, animalComboBox, "Select Animal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String selectedAnimalName = (String) animalComboBox.getSelectedItem();
                Animal selectedAnimal = findAnimalByName(selectedAnimalName);
                boolean ate = selectedAnimal.eat(ENERGY_TO_ADD);
                if (ate) {
                    JOptionPane.showMessageDialog(this, selectedAnimal.getName() + " ate and gained 100 energy", "Energy Status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, selectedAnimal.getName() + " is already at maximum energy", "Energy Status", JOptionPane.INFORMATION_MESSAGE);
                }
                updateAnimalInfo(selectedAnimal);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No animals available.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays a dialog containing a table with information about the competition.
     *
     * @param e The ActionEvent triggered by the info button click.
     */
    private void showInfoDialog(ActionEvent e) {
        JFrame infoFrame = new JFrame("Competition Info");
        infoFrame.setSize(600, 400);
        infoFrame.add(new JScrollPane(infoTable));
        infoFrame.setVisible(true);
    }

    /**
     * Finds and returns an animal by its name.
     *
     * @param name The name of the animal to find.
     * @return The Animal object with the given name, or null if no animal with that name exists.
     */
    private Animal findAnimalByName(String name) {
        for (Animal animal : competitionPanel.getAnimals()) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Updates the information of a specific animal in the info table.
     *
     * @param animal The animal whose information needs to be updated.
     */
    public static void updateAnimalInfo(Animal animal) {
        for (int i = 0; i < infoTableModel.getRowCount(); i++) {
            String animalName = (String) infoTableModel.getValueAt(i, 0);
            if (animalName.equals(animal.getName())) {
                infoTableModel.setValueAt(animal.getEnergy(), i, 4);
                infoTableModel.setValueAt(animal.getTotalDistance(), i, 5);
                infoTableModel.setValueAt(animal.getEnergyPerMeter(), i, 6);
                break;
            }
        }
        competitionPanel.repaint();
    }

    /**
     * Returns the table model that holds the information about the animals in the competition.
     * This static method allows other parts of the application to access the table model.
     *
     * @return The DefaultTableModel containing animal information.
     */
    public static DefaultTableModel getInfoTableModel() {
        return infoTableModel;
    }

    /**
     * Retrieves the category of the given animal.
     * This static method calls the `getCategoryFromAnimal` method on the provided animal instance to determine its category.
     *
     * @param animal The animal instance whose category is to be determined.
     * @return A string representing the category of the animal.
     */
    public static String getCategoryFromAnimal(Animal animal) {
        return animal.getCategoryFromAnimal();
    }

    /**
     * Retrieves the CompetitionPanel associated with this frame.
     * This method provides access to the CompetitionPanel instance used in this frame.
     *
     * @return The CompetitionPanel instance.
     */
    protected CompetitionPanel getCompetitionPanel() {
        return competitionPanel;
    }

    /**
     * Checks if the race has started.
     * This static method returns the current status of the race.
     *
     * @return True if the race has started, false otherwise.
     */
    protected static boolean isRaceStarted() {
        return raceStarted;
    }

    /**
     * Handles the action of adding an animal.
     * If the race is currently active, it shows an error message.
     * Otherwise, it opens the AddAnimalDialog.
     *
     * @param e The ActionEvent triggered by the button press.
     */
    public void onAddAnimalPressed(ActionEvent e) {
        if (isRaceStarted()) {
            JOptionPane.showMessageDialog(competitionPanel, "Please stop the race before adding a new animal.", "Race Active", JOptionPane.ERROR_MESSAGE);
        } else {
            openAddAnimalDialogForGroup(getSelectedGroupIndex());
        }
    }

    /**
     * Handles the action of adding a competition.
     * If the race is currently active, it shows an error message.
     * Otherwise, it opens the AddCompetitionDialog.
     *
     * @param e The ActionEvent triggered by the button press.
     */
    private void onAddCompetitionPressed(ActionEvent e) {
        if (isRaceStarted()) {
            JOptionPane.showMessageDialog(this, "Please stop the race before adding a new competition.", "Race Active", JOptionPane.ERROR_MESSAGE);
        } else {
            openAddCompetitionDialog(e);
        }
    }

    /**
     * Retrieves the current instance of the AddCompetitionDialog.
     *
     * @return the current AddCompetitionDialog instance.
     */
    public static AddCompetitionDialog getDialog() {
        return dialog;
    }

    /**
     * Retrieves the index of the currently selected group in the info table.
     *
     * @return the index of the selected column in the info table.
     *         Returns -1 if no column is selected.
     */
    private int getSelectedGroupIndex() {
        return infoTable.getSelectedColumn();
    }

    /**
     * Retrieves the current instance of the CompetitionFrame.
     *
     * @return the singleton instance of the CompetitionFrame.
     */
    public static CompetitionFrame getFrame() {
        return instance;
    }

    /**
     * Retrieves the current race timer.
     * This static method returns the Timer object associated with the race.
     *
     * @return The Timer object representing the current race timer.
     */
    public static Timer getRaceTimer(){
        return raceTimer;
    }

    /**
     * Sets the text of the play button.
     * This static method updates the text displayed on the play button (Start/Stop).
     *
     * @param text The new text to be displayed on the play button.
     */
    public static void setPlayButton(String text){
        playButton.setText(text);
    }

    /**
     * Sets the race started status.
     * This static method updates the boolean flag indicating whether the race has started.
     *
     * @param isRaceStarted True if the race has started, false otherwise.
     */
    public static void setRaceStarted(boolean isRaceStarted) {
        raceStarted = isRaceStarted;
    }

    /**
     * Sets the race timer.
     * This static method updates the Timer object used to manage the race timing.
     *
     * @param raceTimer The Timer object to be set for the race.
     */
    public static void setRaceTimer(Timer raceTimer) {
        CompetitionFrame.raceTimer = raceTimer;
    }

    /**
     * Retrieves the text displayed on the play button.
     * This static method returns the current text shown on the play button (e.g., "Start Race", "Stop Race").
     *
     * @return The current text displayed on the play button.
     */
    public static String getPlayButtonText() {
        return playButton.getText();
    }

    /**
     * Retrieves the AnimalTableModel instance.
     * This static method provides access to the AnimalTableModel, which manages the data for the animals in the competition.
     *
     * @return The AnimalTableModel instance used in the competition.
     */
    public static AnimalTableModel getAnimalTableModel(){
        return animalTableModel;
    }

    /**
     * Removes an animal from the infoTable based on its name.
     *
     * @param animalName The name of the animal to be removed.
     */
    public void removeAnimalFromInfoTableByName(String animalName) {
        // Iterate through each row in the infoTableModel
        for (int i = 0; i < infoTableModel.getRowCount(); i++) {
            String currentAnimalName = (String) infoTableModel.getValueAt(i, 0); // Get the animal name in the current row

            // Check if the current row contains the animal with the specified name
            if (currentAnimalName.equals(animalName)) {
                infoTableModel.removeRow(i); // Remove the row from the table model
                return; // Exit the method after removing the animal
            }
        }
    }

    public static void addReferee(Referee referee){
        allReferees.add(referee);
    }

    public static List<Referee> getAllReferees(){
        return allReferees;
    }

    /**
     * Retrieves the Referee associated with a specific team name.
     *
     * @param teamName The name of the team for which to find the referee.
     * @return The Referee associated with the specified team name, or null if no such referee exists.
     */
    public static Referee getReferee(String teamName) {
        for (Referee referee : allReferees) {
            if (referee.getGroupName().equals(teamName)) {
                return referee;
            }
        }
        System.err.println("Error: No Referee found for team: " + teamName);
        return null; // Return null if no referee is found for the specified team name
    }

    /**
     * Retrieves the list of all Scores objects.
     *
     * @return A List containing all the Scores objects that have been recorded.
     */
    public static List<Scores> getAllScores(){
        return allScores;
    }

    /**
     * Creates a new Scores object, adds the specified group name to it, and stores it in the list of all scores.
     *
     * @param groupName The name of the group to be added to the new Scores object.
     */
    public synchronized static void addScore(String groupName){
        //System.out.println("add Score to group: " + groupName);
        Scores score = new Scores();
        score.add(groupName);
        allScores.add(score);
    }

    /**
     * Adds an existing Scores object to the list of all scores.
     *
     * @param score The Scores object to be added to the list.
     */
    public static void addScoreObj(Scores score){
        allScores.add(score);
    }

    /**
     * Retrieves the Scores object associated with the specified group name.
     *
     * @param groupName The name of the group whose Scores object is to be retrieved.
     * @return The Scores object associated with the specified group name, or null if not found.
     */
    public static Scores getScore(String groupName) {
        Scores scoreObj = null;

        for (Scores score : allScores) {
            if (score.getScore(groupName) != null) {
                scoreObj = score;
                break;
            }
        }

        if (scoreObj != null) {
            return scoreObj;
        } else {
            return null;
        }
    }

    /**
     * Retrieves the singleton instance of the Tick object.
     *
     * @return The Tick instance.
     */
    public static Tick getTickInstance(){
        return tick;
    }

    /**
     * The main method serves as the entry point for the application.
     * It initializes and displays the CompetitionFrame.
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        // Create an instance of the CompetitionFrame
        CompetitionFrame frame = new CompetitionFrame();
        // Make the frame visible on the screen
        frame.setVisible(true);
    }
}
