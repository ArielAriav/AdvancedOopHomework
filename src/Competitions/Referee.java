package Competitions;

import Animals.Animal;
import Graphics.AnimalTableModel;
import Graphics.AnimalThread;
import Graphics.CompetitionFrame;
import Graphics.Tick;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Referee class represents a referee that monitors the progress of a specific group in a tournament.
 * The referee checks if any animal in the group has finished its race, updates the scores accordingly, and signals when the group has completed its race.
 * The Referee class runs as a separate thread, continuously checking the status of the animals in its assigned group.
 */
public class Referee implements Runnable {
    private static int refereeCount = 0; // Static variable to count the number of Referees
    private final String groupName; // Name of the group the referee is waiting for
    private final AtomicBoolean finishFlag = new AtomicBoolean(false); // Unique flag for each team
    private final String competitionType;
    private Thread thread;

    /**
     * Constructs a Referee with the specified group name and competition type.
     *
     * @param groupName The name of the group to wait for.
     * @param competitionType The type of competition (regular/courier).
     */
    public Referee(String groupName, String competitionType) {
        this.groupName = groupName;
        this.competitionType = competitionType;
        this.thread = new Thread(this);

        synchronized (Referee.class) {
            refereeCount++; // Increment the counter when a new Referee is created
            //System.out.println("Current number of Referees: " + refereeCount); // Print the number of Referees
        }
    }

    /**
     * The main run method for the Referee thread. It continuously checks if an animal in the group has finished,
     * and if so, it updates the scores and stops the thread.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (hasAnimalArrived()) {

                // Pass the score information to the CompetitionFrame
                CompetitionFrame.addScore(groupName);

                // finishFlag.set(true); // Mark the finishFlag as true for this group
                break; // Exit the loop after adding
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                return;
            }
        }

        synchronized (Referee.class) {
            refereeCount--; // Decrement the counter when a Referee thread finishes
            //System.out.println("Referee finished. Current number of Referees: " + refereeCount);
        }
    }

    /**
     * Checks if the specified group's animal has arrived at its destination.
     *
     * @return true if the animal has arrived, false otherwise.
     */
    private boolean hasAnimalArrived() {
        AnimalThread[] regularAnimalThreads = RegularTournament.getAnimalThreads();
        AnimalThread[][] courierAnimalThreads = CourierTournament.getAnimalThreads();
        int groupIndex = AnimalTableModel.getGroupIndex(groupName); // Get the index of the current group by its name

        double tolerance = 100; // Tolerance level to account for floating-point precision issues
        double courierTolerance = 15;
        if (competitionType.equals("regular")) {
            // Check only for the current group in the regular competition
            if (groupIndex >= 0 && groupIndex < regularAnimalThreads.length) {
                Animal animal = regularAnimalThreads[groupIndex].getParticipant();

                if (Math.abs(animal.getTotalDistance() - animal.getNeededDistance()) < tolerance) {
                    return true; // The animal has arrived for this group
                }
            }
        }  else if (competitionType.equals("courier")) {
            for (AnimalThread[] courierAnimalThread : courierAnimalThreads) {
                // Ensure that the courierAnimalThread array is not null and has at least one element
                if (courierAnimalThread != null && courierAnimalThread.length > 0) {
                    int lastIndex = courierAnimalThread.length - 1;

                    // Ensure the first AnimalThread is not null
                    if (courierAnimalThread[0] != null) {

                        if (Math.abs(courierAnimalThread[0].getParticipant().getTotalDistance() -
                                Tick.calculateNeededDistance(AnimalTableModel.getGroupIndex(groupName),
                                        courierAnimalThread[0].getParticipant().getCategoryFromAnimal(),
                                        0)) < courierTolerance) {
                            return true; // Last animal in the courier group has arrived
                        }
                    } else {
                        //System.err.println("Error: courierAnimalThread[" + lastIndex + "] is null.");
                    }
                }
            }
        }
        return false; // No animal has arrived yet for this group
    }

    /**
     * Retrieves the name of the group this Referee is responsible for.
     *
     * @return The group name as a String.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Retrieves the thread associated with this Referee.
     *
     * @return The Thread object.
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Starts the Referee's thread, initiating the process of monitoring the group's progress.
     */
    public void start() {
        thread.start();
    }
}
