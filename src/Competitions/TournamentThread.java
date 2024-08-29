package Competitions;

import Graphics.CompetitionFrame;
import java.util.Map;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The `TournamentThread` class is responsible for managing the execution of a tournament.
 * It controls the start of the tournament by notifying all participating threads and periodically
 * updates the UI with the current status of the competition. The thread continues running until all
 * groups in the tournament have finished their race.
 */
public class TournamentThread implements Runnable {
    private AtomicBoolean startLock; // Shared lock object
    private static Scores scores = new Scores();
    private volatile boolean running = true;
    private final int groups;
    private final String competitionType;


    /**
     * Constructs a TournamentThread with the specified start lock, number of groups, and competition type.
     *
     * @param startLock An `AtomicBoolean` used to signal the start of the tournament.
     * @param groups The number of groups participating in the tournament.
     * @param competitionType The type of the competition (e.g., "regular", "courier").
     */
    public TournamentThread(AtomicBoolean startLock, int groups, String competitionType) {
        this.competitionType = competitionType;
        this.startLock = startLock;
        this.groups = groups;
    }

    /**
     * The main execution method of the thread, which starts the tournament and periodically updates
     * the UI with the current status of the competition. The thread continues running until all groups
     * have finished.
     */
    @Override
    public void run() {

        // Notify all waiting threads to start the competition
        synchronized (startLock) {
            startLock.notifyAll(); // Notify all waiting AnimalThreads to start
        }

        // Periodically update the UI with the current status of the competition
        while (running) {
            // Retrieve the scores and update the UI
            Map<String, Date> allScores = scores.getAll();

            // Check if all groups have finished
            if (allScores.size() >= groups) {
                // All groups have finished; exit the loop
                break;
            }

            // Update the UI with current scores
            // This would involve interaction with the UI components
            CompetitionFrame.getDialog().repaint();

            // Sleep or delay before the next update
            try {
                Thread.sleep(1000); // Update the UI every second (or adjust as needed)
            } catch (InterruptedException e) {
                // Handle thread interruption, possibly cleanup resources
                Thread.currentThread().interrupt();
                break; // Exit loop on interruption
            }
        }
    }
}
