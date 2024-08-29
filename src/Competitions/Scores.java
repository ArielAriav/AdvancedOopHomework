package Competitions;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The `Scores` class is responsible for managing a collection of scores
 * in a synchronized manner, ensuring thread safety during concurrent access.
 * Each score is represented as a name paired with the time it was recorded.
 *
 * The class provides methods to add a new score and retrieve all recorded scores.
 */
public class Scores {
    private Map<String, Date> scores; // A map to which we will add the string and the time

    /**
     * Constructs a new `Scores` object, initializing the internal map as a synchronized map.
     * This ensures that all operations on the map are thread-safe, making the class safe for use
     * in concurrent environments.
     */
    public Scores() {
        this.scores = Collections.synchronizedMap(new HashMap<>()); // Initialize the map as a synchronized map
        //This ensures that all operations on the map are thread-safe.
    }

    /**
     * Adds the name with the current time to the map.
     * @param name The name to add
     */
    public void add(String name) {
        // Add the name with the current time to the map
        scores.put(name, new Date());
    }

    /**
     * Retrieves all the scores from the map.
     *
     * @return  A Map containing the scores, where the keys are the names
     *         and the values are the Date objects representing the time
     *         each score was recorded.
     */
    public Map<String, Date> getAll(){
        return scores;
    }

    /**
     * Retrieves the recorded time for a specific name from the scores map.
     *
     * @param name The name whose score time is to be retrieved.
     * @return The Date object representing the time associated with the given name,
     *         or null if the name is not found in the scores map.
     */
    public Date getScore(String name) {
        return scores.get(name);
    }
}
