package Graphics;

import Animals.Animal;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The `AnimalThread` class represents a thread responsible for controlling the movement of an animal
 * in a competition. It manages the animal's progress from start to finish, updating its location
 * and checking if it has reached the required distance or can no longer move.
 *
 * The thread waits for a start signal, then continuously updates the animal's position
 * until it either reaches the required distance or can no longer move. The thread also
 * updates a finish flag once the animal has completed its movement.
 */
public class AnimalThread implements Runnable {
    private static final int SLEEPTIME=50;
    private Animal participant; // The animal we promote
    private double neededDistance; // The distance required to travel from start to finish
    private AtomicBoolean startLock; // Shared lock object to synchronize start
    private static AtomicBoolean finishFlag; // A flag that signals that the animal has traveled the required distance and finished

    /**
     * Constructs an `AnimalThread` with the specified participant, needed distance, start lock, and finish flag.
     *
     * @param participant The animal that this thread controls.
     * @param neededDistance The distance the animal needs to travel to finish.
     * @param startLock The shared lock object used to synchronize the start of the thread.
     * @param finishFlag The flag that indicates when the animal has finished its race.
     */
    public AnimalThread(Animal participant, double neededDistance, AtomicBoolean startLock, AtomicBoolean finishFlag) {
        this.participant = participant;
        this.neededDistance = neededDistance;
        if(startLock == null){
            this.startLock = new AtomicBoolean(true);
        } else{
            this.startLock = startLock;
        }
        this.finishFlag = finishFlag;
    }

    /**
     * The main run method for the thread. It waits for the start signal, then updates the animal's
     * position until it either reaches the needed distance or can no longer move.
     * Once the animal finishes, the finish flag is set and notified.
     */
    @Override
    public void run() {
        while (true) {
            // Wait for the tournament to start
            synchronized (startLock) {
                while (!startLock.get()) {
                    try {
                        startLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            synchronized (participant) {
                Mobility.Point nextLocation = new Mobility.Point(
                        (int) (participant.getLocation().getX() + participant.getSpeed()),
                        participant.getLocation().getY());
                if(!participant.move(nextLocation)){
                    synchronized (finishFlag) {
                        finishFlag.set(true);
                        finishFlag.notifyAll();
                    }
                }
                CompetitionFrame.getFrame().repaint();

                if(!participant.canMove()){
                    break;
                }
                if (participant.getTotalDistance() >= neededDistance) {
                    synchronized (finishFlag) {
                        finishFlag.set(true);
                        finishFlag.notifyAll();
                    }
                    break;
                }

                try {
                    Thread.sleep(SLEEPTIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets the participant (animal) associated with this thread.
     *
     * @return The animal participating in the competition.
     */
    public Animal getParticipant() {
        return participant;
    }
}