package Competitions;

import Animals.Animal;
import Graphics.AnimalTableModel;
import Graphics.AnimalThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The RegularTournament class represents a standard tournament where multiple groups of animals compete in a race.
 * Each group of animals competes independently, with their progress tracked and managed by a set of threads.
 * This class handles the setup and execution of the tournament, including the creation of AnimalThreads and the initialization of Referees.
 */
public class RegularTournament extends Tournament {
    private TournamentThread tournamentThread;
    private AtomicBoolean[][] finishFlags; // Each animal in each group has its own finish flag
    private static List<Referee> referees = new ArrayList<>();
    private static AnimalThread[] animalThreads;

    /**
     * Constructs a RegularTournament with the specified animals, number of groups, competition type, tournament thread, and start flag.
     *
     * @param animalsTable List of animal groups participating in the tournament.
     * @param groups The number of groups participating in the tournament.
     * @param competitionType The type of the competition (e.g., "regular").
     * @param tournamentThread The thread that manages the tournament's execution.
     * @param startFlag AtomicBoolean used to signal the start of the tournament.
     */
    public RegularTournament(List<List<Animal>> animalsTable, int groups, String competitionType, TournamentThread tournamentThread, AtomicBoolean startFlag) {
        // Initialize the finishFlags array for each group and each animal
        finishFlags = new AtomicBoolean[animalsTable.size()][];

        for (int i = 0; i < animalsTable.size(); i++) {
            finishFlags[i] = new AtomicBoolean[animalsTable.get(i).size()];
            for (int j = 0; j < animalsTable.get(i).size(); j++) {
                finishFlags[i][j] = new AtomicBoolean(false);  // Each animal has its own finish flag
            }
        }

        setup(animalsTable, startFlag);
        this.tournamentThread = tournamentThread;
    }

    /**
     * Sets up the tournament by initializing the AnimalThreads and Referees for each group.
     *
     * @param animalsTable List of animal groups participating in the tournament.
     * @param startFlag AtomicBoolean used to signal the start of the race.
     */
    @Override
    public void setup(List<List<Animal>> animalsTable, AtomicBoolean startFlag) {
        animalThreads = new AnimalThread[animalsTable.size()];

        // Initialize and start AnimalThreads for each group
        for (int i = 0; i < animalsTable.size(); i++) {
            for (int j = 0; j < animalsTable.get(i).size(); j++) {
                double neededDistance = AnimalTableModel.getRegularAnimalGroups().get(i).get(j).getNeededDistance();
                Animal animal = animalsTable.get(i).get(j);

                // Create and start a new AnimalThread for the new animal
                AnimalThread animalThread = new AnimalThread(animal, neededDistance, startFlag, finishFlags[i][j]);
                animalThreads[i] = animalThread;
                new Thread(animalThread).start();
            }
        }

        // Start the tournament thread
        this.tournamentThread = new TournamentThread(startFlag, animalsTable.size(), "regular");
        Thread tournamentThread = new Thread(this.tournamentThread);
        tournamentThread.start();
    }

    /**
     * Retrieves the Referee associated with a given group name.
     *
     * @param groupName The name of the group to search for.
     * @return The Referee associated with the given group name, or null if not found.
     */
    public static Referee getReferee(String groupName) {
        if(referees != null && !referees.isEmpty()) {
            for(Referee referee : referees) {
                if(referee.getGroupName().equals(groupName)){
                    return referee;
                }
            }
        }
        return null;
    }

    /**
     * Returns the array of AnimalThreads currently running in the tournament.
     *
     * @return An array of AnimalThread objects.
     */
    public static AnimalThread[] getAnimalThreads() {
        return animalThreads;
    }
}
