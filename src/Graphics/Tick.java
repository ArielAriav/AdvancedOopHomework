package Graphics;

import Animals.Animal;
import Competitions.Referee;
import Mobility.Point;

import javax.swing.*;
import java.util.List;
import static Graphics.CompetitionFrame.competitionPanel;

/**
 * The Tick class is responsible for managing the race logic in the competition application.
 * It handles the starting, stopping, and updating of the race progress for animals in different groups,
 * including both regular and courier race types. The class also manages threads related to animals, referees,
 * and tournaments to ensure they are running correctly and updates the GUI accordingly.
 */
public class Tick implements Runnable {
    private static List<List<Animal>> animalGroups;
    private static boolean raceFinished = false;
    private static List<Referee> referees;
    private static boolean isRaceStarted = false;

    /**
     * Constructs a Tick instance with the specified animal groups and referees.
     * Initializes the animal groups and referees that will be monitored during the race.
     *
     * @param animalGroupsTable The list of animal groups participating in the race.
     * @param referees The list of referees overseeing the race.
     */
    Tick(List<List<Animal>> animalGroupsTable, List<Referee> referees) {
        animalGroups = animalGroupsTable;
        this.referees = referees;
    }

    /**
     * The main run method for the Tick thread. It starts or stops the race based on the current state
     * of the race timer and the play button. If the race is ongoing, it continuously updates the race progress.
     */
    @Override
    public void run() {
        if (CompetitionFrame.getRaceTimer() == null || !CompetitionFrame.getRaceTimer().isRunning()
                || (CompetitionFrame.getPlayButtonText().equals("Start Race") && !raceFinished)) {

            startRace();
            isRaceStarted = true;

        } else { // The race stopped
            stopRace();
        }
    }

    /**
     * Starts the race by resetting the distances of all animals to 0, resetting their state,
     * and starting the race timer. The method also initializes the race logic for both regular and courier races.
     */
    private void startRace() {
        // Reset the distances of all animals to 0 and update info table
         for (int i = 0; i < AnimalTableModel.getAnimalGroups().size(); i++) {
            for (int j = 0; j < AnimalTableModel.getAnimalGroups().get(i).size(); j++) {
                AnimalTableModel.getAnimalGroups().get(i).get(j).setTotalDistance(0.0);
                CompetitionFrame.updateAnimalInfo(AnimalTableModel.getAnimalGroups().get(i).get(j));
            }
        }

        // Reset the state of all animals and the race finished flag
        raceFinished = false;

        for (int i = 0; i < AnimalTableModel.getAnimalGroups().size(); i++) {
            for (Animal animal : AnimalTableModel.getAnimalGroups().get(i)) {
                animal.reset();
            }
        }

        CompetitionFrame.setRaceTimer(new Timer(100, event -> {
            boolean needsRepaint = false;

            for (int groupIndex = 0; groupIndex < animalGroups.size(); groupIndex++) {
                List<Animal> group = animalGroups.get(groupIndex);

                // Regular race logic
                 if (AnimalTableModel.getGroupCompetitionType(groupIndex).equals("regular")) {
                    for (int animalIndex = 0; animalIndex < group.size(); animalIndex++) {
                        if (group.get(animalIndex).getEnergy() > 0) {
                            Mobility.Point nextLocation = new Mobility.Point(
                                    (int) (group.get(animalIndex).getLocation().getX() + group.get(animalIndex).getSpeed()),
                                    group.get(animalIndex).getLocation().getY()
                            );
                            // Move the animal and set the repaint flag if it successfully moves
                            if (group.get(animalIndex).move(nextLocation)) {
                                needsRepaint = true;
                            }
                        }

                        CompetitionFrame.updateAnimalInfo(group.get(animalIndex));
                    }
                }

                // Courier race logic
                else if (AnimalTableModel.getGroupCompetitionType(groupIndex).equals("courier")) {
                    boolean previousAnimalFinished = true;

                    if (AnimalTableModel.getGroupType(groupIndex).equals("Terrestrial")) {
                        // Start from the first animal and move forwards
                        for (int animalIndex = 0; animalIndex < group.size(); animalIndex++) {
                            Animal animal = group.get(animalIndex);

                            if(animalIndex==3 && animal.getLocation().equals(new Point(0,4))) {
                                animal.stopMoving();
                            }
                            // Check if previous animal has finished before moving the current one
                            if (previousAnimalFinished && animal.getEnergy() > 0) {
                                Mobility.Point nextLocation = new Mobility.Point(
                                        (int) (animal.getLocation().getX() + animal.getSpeed()),
                                        animal.getLocation().getY()
                                );

                                if(animal.canMove()){
                                    if (animal.move(nextLocation)) {
                                        needsRepaint = true;
                                    }

                                    // If the current animal has reached the needed distance, it signals it's done
                                    if (Math.abs(animal.getTotalDistance() - calculateNeededDistance(groupIndex,
                                            AnimalTableModel.getGroupType(groupIndex),
                                            AnimalTableModel.getAnimalGroups().get(groupIndex).indexOf(animal))) < 50) {
                                        previousAnimalFinished = true;
                                        animal.stopMoving();
                                    } else {
                                        previousAnimalFinished = false;  // This animal is still running
                                    }

                                }
                                CompetitionFrame.updateAnimalInfo(animal);
                            }
                        }
                    } else {
                        // Start from the last animal and move backwards
                        for (int animalIndex = group.size() - 1; animalIndex >= 0; animalIndex--) {
                            Animal animal = group.get(animalIndex);

                            // Check if previous animal has finished before moving the current one
                            if (previousAnimalFinished && animal.getEnergy() > 0) {
                                Mobility.Point nextLocation = new Mobility.Point(
                                        (int) (animal.getLocation().getX() + animal.getSpeed()),
                                        animal.getLocation().getY()
                                );

                                if (animal.move(nextLocation)) {
                                    needsRepaint = true;
                                }

                                // If the current animal has reached the needed distance, it signals it's done
                                if (animal.getTotalDistance() >= calculateNeededDistance(groupIndex, AnimalTableModel.getGroupType(groupIndex),
                                        AnimalTableModel.getAnimalGroups().get(groupIndex).indexOf(animal))) {
                                    previousAnimalFinished = true;
                                    animal.stopMoving();

                                } else {
                                    previousAnimalFinished = false;  // This animal is still running
                                }

                                CompetitionFrame.updateAnimalInfo(animal);
                            }
                        }
                    }
                }
            }

            if (needsRepaint) {
                competitionPanel.repaint(); // Repaint only if needed
            }


            boolean raceCompleted = true;
            for (int i = 0; i < AnimalTableModel.getAnimalGroups().size(); i++) {
                for (Animal animal : AnimalTableModel.getAnimalGroups().get(i)) {
                    if (animal.canMove()) {
                        raceCompleted = false;
                        break;
                    }
                }
            }

            if(areAnimalThreadsRunning() || areRefereeThreadsRunning() || areTournamentThreadsRunning()){
             raceCompleted = false;
            }

            if (raceCompleted) {
                stopRace(); // Stop the race and display the message
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "The race is finished!\n To see the finish times, please click on 'Add Competition'.");
                });
            }
        }));

        CompetitionFrame.getRaceTimer().start();
        CompetitionFrame.setPlayButton("Stop Race");
        CompetitionFrame.setRaceStarted(true);
    }

    /**
     * Stops the race by stopping the race timer and updating the GUI.
     * Resets the play button to "Start Race" and marks the race as not started.
     */
    private void stopRace() {
        if (CompetitionFrame.getRaceTimer() != null) {
            CompetitionFrame.getRaceTimer().stop();
            CompetitionFrame.setRaceTimer(null); // Clear the timer reference
        }
        CompetitionFrame.setPlayButton("Start Race");
        CompetitionFrame.setRaceStarted(false);

        competitionPanel.repaint();
    }

    /**
     * Calculates the needed distance for an animal in a specific group and position.
     * The distance is based on the group type (Air, Water, Terrestrial) and the animal's position in the group.
     *
     * @param index The index of the group.
     * @param groupType The type of the group (Air, Water, Terrestrial).
     * @param animalIndex The index of the animal within the group.
     * @return The needed distance that the animal must travel to complete the race.
     */
    public static double calculateNeededDistance(int index, String groupType, int animalIndex) {
        switch (groupType) {
            case "Air": {
                return 600.0 / AnimalTableModel.getAnimalGroups().get(index).size();
            }
            case "Water": {
                return (560.0 / AnimalTableModel.getAnimalGroups().get(index).size());
            }
            case "Terrestrial": {
                if (animalIndex == 0) {
                    return 610;
                } else if (animalIndex == 1) {
                    return 420;
                } else if (animalIndex == 2) {
                    return 630;
                } else {
                    return 2250.0 / 4;
                }
            }
            default: {
                return 1000;
            }
        }
    }

    /**
     * Checks if there are any AnimalThread threads currently running.
     * Iterates through the active threads in the system to find any threads related to animals.
     *
     * @return True if any AnimalThread threads are running, false otherwise.
     */
    private boolean areAnimalThreadsRunning() {
        // Get the current thread group
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        // Get the parent group (root group)
        while (currentGroup.getParent() != null) {
            currentGroup = currentGroup.getParent();
        }

        // Estimate the number of active threads in the system
        int activeThreads = currentGroup.activeCount();
        // Get all active threads
        Thread[] threads = new Thread[activeThreads];
        currentGroup.enumerate(threads, true);

        // Check if there are any AnimalThread threads running
        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                if (thread.getName().contains("AnimalThread") || thread.getClass().getName().equals("Graphics.AnimalThread")) {
                    return true; // Found an AnimalThread thread that is running
                }
            }
        }
        return false; // No AnimalThread threads found running
    }

    /**
     * Checks if there are any Referee threads currently running.
     * Iterates through the list of referees to determine if any referee threads are still active.
     *
     * @return True if any Referee threads are running, false otherwise.
     */
    private boolean areRefereeThreadsRunning() {
        boolean refereesRunning = false;

        for (Referee referee : referees) {
            Thread refereeThread = referee.getThread();
            if (refereeThread != null && refereeThread.isAlive()) {
                refereesRunning = true; // Found a Referee thread that is running
            }
        }

        return refereesRunning;
    }

    /**
     * Checks if there are any TournamentThread threads currently running.
     * Scans all active threads in the system to find any threads related to tournaments.
     * This method checks for threads named "TournamentThread" or belonging to the "Competitions.TournamentThread" class.
     *
     * @return True if any TournamentThread threads are running, false otherwise.
     */
    private boolean areTournamentThreadsRunning() {
        // Get the current thread group
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        // Get the parent group (root group)
        while (currentGroup.getParent() != null) {
            currentGroup = currentGroup.getParent();
        }

        // Estimate the number of active threads in the system
        int activeThreads = currentGroup.activeCount();
        // Get all active threads
        Thread[] threads = new Thread[activeThreads];
        currentGroup.enumerate(threads, true);

        // Check if there are any TournamentThread threads running
        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                if (thread.getName().contains("TournamentThread") || thread.getClass().getName().equals("Competitions.TournamentThread")) {
                    return true; // Found a TournamentThread thread that is running
                }
            }
        }
        return false; // No TournamentThread threads found running
    }

    public static boolean isRaceFinished() {
        return raceFinished;
    }

    public static boolean isRaceStarted(){
        return isRaceStarted;
    }

}