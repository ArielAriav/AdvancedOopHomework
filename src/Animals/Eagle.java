package Animals;

import Olympics.Medal;

/**
 * Represents an Eagle, which is a type of air animal.
 * This class extends AirAnimal.
 */
public class Eagle extends AirAnimal {

    static final int MAX_ALTITUDE = 1000; // The max altitude possible.

    private double altitudeOfFlight; // The altitude of flight of the eagle.

    /**
     * Constructs an Eagle object with default values.
     * Initializes altitudeOfFlight to 0.0.
     */
    public Eagle() {
        super(); // Call the default constructor of the superclass AirAnimal
        this.altitudeOfFlight = 0.0;
        this.setSound("Clack-wack-chack");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\eagle_EAST.png");
        setImg1(img);
    }

    /**
     * Constructs an Eagle object with specified attributes.
     *
     * @param inputName           The name of the eagle.
     * @param inputGender         The gender of the eagle.
     * @param inputWeight         The weight of the eagle.
     * @param inputSpeed          The speed of the eagle.
     * @param inputMedals         The medals won by the eagle (deep copied).
     * @param inputWingspan       The wingspan of the eagle.
     * @param inputAltitudeOfFlight The altitude of flight of the eagle.
     *
     */
    public Eagle(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, double inputWingspan, double inputAltitudeOfFlight) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputWingspan); // Call the constructor of the superclass AirAnimal
        this.altitudeOfFlight = inputAltitudeOfFlight;
        this.setSound("Clack-wack-chack");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\eagle_EAST.png");
        setImg1(img);
    }

    /**
     * Constructs a copy of an existing Eagle object.
     *
     * @param other The Eagle object to copy.
     */
    public Eagle(Eagle other) {
        super(other); // Call the copy constructor of the superclass AirAnimal
        this.altitudeOfFlight = other.altitudeOfFlight;
        this.setSound("Clack-wack-chack");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\eagleFinal.png");
        setImg1(img);
    }

    /**
     * Sets the altitude of flight of the eagle.
     *
     * @param altitudeOfFlight The new altitude of flight.
     * @return true if the altitude was set successfully, false otherwise.
     */
    public boolean setAltitudeOfFlight(double altitudeOfFlight) {
        if (altitudeOfFlight < 0 || altitudeOfFlight > MAX_ALTITUDE) {
            System.out.println("Altitude of flight must be between 0 and " + MAX_ALTITUDE);
            return false;
        }
        this.altitudeOfFlight = altitudeOfFlight;
        return true;
    }

    /**
     * Returns the altitude of flight of the eagle.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The altitude of flight.
     */
    protected double getAltitudeOfFlight() {
        return altitudeOfFlight;
    }

    /**
     * Compares this eagle to another object for equality.
     * Returns true if the other object is an Eagle with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this eagle.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Eagle)) {
            return false;
        }
        Eagle other = (Eagle) obj;
        return super.equals(other) && Double.compare(other.altitudeOfFlight, altitudeOfFlight) == 0;
    }

    /**
     * Returns a string representation of the eagle.
     *
     * @return A string representation of the eagle.
     */
    @Override
    public String toString() {
        return super.toString() + ", Altitude of Flight: " + altitudeOfFlight;
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Eagle";
    }
}
