package Olympics;

/**
 * A class representing a medal in an Olympic competition
 */
public class Medal {

    /**
     * enum representing the type of medal
     */
    public enum Type {
        bronze, silver, gold
    }

    /**
     * The name of the competition
     */
    private String tournament;

    /**
     * The year he won the medal
     */
    private int year;

    /**
     * The type of the medal.
     */
    private Type medalType;

    /**
     * Default constructor initializing a Medal object with default values.
     * The default tournament name is an empty string,
     * the default year is the current year, and the default medal type is bronze.
     */
    public Medal(){
        this.tournament = "";
        this.year = java.time.Year.now().getValue();
        this.medalType = Type.bronze;
    }

    /**
     * Constructor initializing a Medal object with the given tournament name, year, and medal type.
     *
     * @param inputTournament The name of the competition.
     * @param inputYear The year the medal was won.
     * @param inputMedalType The type of the medal.
     * @throws IllegalArgumentException if inputTournament is null or empty, inputYear is invalid, or inputMedalType is null.
     */
    public Medal(String inputTournament, int inputYear, Type inputMedalType) {
        if (inputTournament == null || inputTournament.isEmpty()) {
            throw new IllegalArgumentException("Tournament name cannot be null or empty");
        }
        if (inputYear <= 0 || inputYear > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year must be a positive number and not in the future");
        }
        if (inputMedalType == null) {
            throw new IllegalArgumentException("Medal type cannot be null");
        }
        this.tournament = inputTournament;
        this.year = inputYear;
        this.medalType = inputMedalType;
    }

    /**
     * Copy constructor initializing a Medal object with the same attributes as another Medal object.
     *
     * @param other The Medal object to copy.
     */
    public Medal(Medal other) {
        this.tournament = other.tournament;
        this.year = other.year;
        this.medalType = other.medalType;
    }

    /**
     * Returns the name of the competition.
     * @return The name of the competition.
     */
    public String getTournament() {
        return tournament;
    }

    /**
     * Returns the year the medal was won.
     * @return The year the medal was won.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the type of the medal.
     * @return The type of the medal.
     */
    public Type getMedalType() {
        return medalType;
    }

    /**
     * Sets the name of the competition.
     * @param inputTournament The new name of the competition.
     * @return true if the tournament was set successfully, false otherwise.
     */
    public boolean setTournament(String inputTournament) {
        if (inputTournament == null || inputTournament.isEmpty()) {
            //invalid input
            System.out.println("Tournament name cannot be null or empty - tournament hasn't changed.");
            return false;
        }
        //else
        this.tournament = inputTournament;
        System.out.println("Tournament name has been set to " + this.tournament + " Successfully.");
        return true;
    }

    /**
     * Sets the year of the competition.
     * @param inputYear The new year of the competition.
     * @return true if the year was set successfully, false otherwise.
     */
    public boolean setYear(int inputYear) {
        if (inputYear <= 0 || inputYear > java.time.Year.now().getValue()) {
            System.out.println("Year must be a positive number and not in the future - year hasn't changed.");
            return false;
        }
        //else
        this.year = inputYear;
        System.out.println("Year has been set to " + this.year + " Successfully.");
        return true;
    }

    /**
     * Sets the type of the medal.
     * @param inputMedalType The new type of medal.
     * @return true if the medal type was set successfully, false otherwise.
     */
    public boolean setMedalType(Type inputMedalType) {
        if (inputMedalType == null) {
            System.out.println("Medal type cannot be null - Medal type hasn't changed.");
            return false;
        }
        //else
        this.medalType = inputMedalType;
        System.out.println("Medal type has been set to " + this.medalType + " Successfully.");
        return true;
    }

    /**
     * Compares this medal to the specified object. The result is true if: the argument is not null
     * and is a Medal object that represents the same tournament, year, and medal type as this object.
     *
     * @param obj the object to compare this Medal against.
     * @return true if the given object represents a Medal equivalent to this medal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Medal)){
            return false;
        }
        Medal medal = (Medal) obj;
        return (year == medal.year && tournament.equals(medal.tournament) && medalType == medal.medalType);
    }

    /**
     * Returns a string representation of the medal. The string representation consists of the tournament name,
     * the year, and the type of the medal.
     *
     * @return a string representation of the medal.
     */
    @Override
    public String toString() {
        return "Medal{" + "tournament='" + tournament + '\'' +
                ", year=" + year +
                ", medalType=" + medalType +
                '}';
    }
}
