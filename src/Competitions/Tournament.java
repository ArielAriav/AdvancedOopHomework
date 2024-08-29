package Competitions;

import Animals.Animal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The `Tournament` class is an abstract base class for different types of tournaments.
 * It provides a common structure for setting up and managing tournaments, including a reference
 * to a `TournamentThread` that manages the tournament's execution.
 *
 * Subclasses must implement the `setup` method to initialize the specific tournament details.
 */
public abstract class Tournament {

    protected TournamentThread tournamentThread;

    /**
     * Retrieves the `TournamentThread` associated with this tournament.
     *
     * @return The `TournamentThread` object managing the tournament's execution.
     */
    public TournamentThread getTournamentThread()
    {
        return this.tournamentThread;
    }

    /**
     * Abstract method to be implemented by subclasses to set up the tournament.
     * This method is responsible for initializing the animal groups and any necessary
     * synchronization mechanisms before the tournament starts.
     *
     * @param animalsTable A list of animal groups participating in the tournament.
     * @param startFlag An `AtomicBoolean` used to signal the start of the tournament.
     */
    protected abstract void setup(List<List<Animal>> animalsTable, AtomicBoolean startFlag);
}