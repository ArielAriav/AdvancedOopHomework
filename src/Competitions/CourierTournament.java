package Competitions;

import Graphics.AnimalThread;
import Animals.Animal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The CourierTournament class represents a tournament where animals compete in a relay race format.
 * Each group of animals participates in a sequence, with the next group starting when the previous group finishes.
 * This class handles the setup and execution of the tournament, including the management of AnimalThreads and Referees.
 */
public class CourierTournament extends Tournament {
    private TournamentThread tournamentThread;
    private List<List<Animal>>  animals;
    private String groupType;
    private static AnimalThread[][] animalThreads;
    private static List<Referee> referees = new ArrayList<>();

    /**
     * Constructs a CourierTournament with the specified animals, start flag, group type, and tournament thread.
     *
     * @param animals List of animal groups participating in the tournament.
     * @param startFlag AtomicBoolean used to signal the start of the tournament.
     * @param groupType The type of the group (e.g., "Air", "Water", "Terrestrial").
     * @param tournamentThread The thread that manages the tournament's execution.
     */
    public CourierTournament(List<List<Animal>> animals, AtomicBoolean startFlag, String groupType, TournamentThread tournamentThread) {
        this.animals = animals;
        this.groupType = groupType;
        this.tournamentThread = tournamentThread;
        setup(animals, startFlag);
    }

    /**
     * Sets up the tournament by initializing the AnimalThreads and Referees for each group.
     *
     * @param animals List of animal groups participating in the tournament.
     * @param startFlag AtomicBoolean used to signal the start of the first group's race.
     */
    @Override
    protected void setup(List<List<Animal>> animals, AtomicBoolean startFlag) {
        AtomicBoolean startFlag1 = new AtomicBoolean(false);

        // Initialize the finishFlags and animalThreads arrays
        AtomicBoolean[][] finishFlags = new AtomicBoolean[animals.size()][];
        animalThreads = new AnimalThread[animals.size()][];

        try {
            for (int i = 0; i < animals.size(); i++) {
                finishFlags[i] = new AtomicBoolean[animals.get(i).size()];
                animalThreads[i] = new AnimalThread[animals.get(i).size()];

                for (int j = 0; j < animals.get(i).size(); j++) {
                    finishFlags[i][j] = new AtomicBoolean(false);

                    if (i == 0) {
                        // First group uses startFlag1
                        animalThreads[i][j] = new AnimalThread(animals.get(i).get(j), calculateNeededDistance(i), startFlag1, finishFlags[i][j]);
                    } else {
                        // Check if the previous group has at least as many animals as the current group
                        if (j < finishFlags[i - 1].length) {
                            // Safe to access the previous group's finishFlag
                            animalThreads[i][j] = new AnimalThread(animals.get(i).get(j), calculateNeededDistance(i), finishFlags[i - 1][j], finishFlags[i][j]);
                        } else {
                            // Handle the case where the previous group has fewer animals
                            // For example, use a default flag or skip this thread setup
                            //System.err.println("Mismatch in group sizes; skipping thread setup for this animal.");
                        }
                    }

                    Thread thread = new Thread(animalThreads[i][j]);
                    thread.start();
                }
            }

            // Start the tournament thread
            this.tournamentThread = new TournamentThread(startFlag1, animals.size(), "courier");
            Thread tournamentThread = new Thread(this.tournamentThread);
            tournamentThread.start();

        } catch (Exception e) {
            e.printStackTrace(); // Keep this to see what exactly is going wrong
        }
    }

    /**
     * Calculates the needed distance for each animal in a group based on the group type.
     *
     * @param index The index of the group in the animals list.
     * @return The distance each animal in the group needs to travel.
     */
    public double calculateNeededDistance(int index)
    {
        switch (groupType)
        {
            case "Air":
            {
                return 600.0 / animals.get(index).size();
            }
            case "Water":
            {
                return (554.0 / animals.get(index).size()) - 100;
            }
            case "Terrestrial":
            {
                return 2248.0/4;
            }

            default:
            {
                return 1000;
            }
        }
    }

    /**
     * Retrieves the array of AnimalThreads that are participating in the tournament.
     *
     * @return A 2D array of AnimalThreads representing all the animals in the tournament.
     */
    public static AnimalThread[][] getAnimalThreads() {
        return animalThreads;
    }

}