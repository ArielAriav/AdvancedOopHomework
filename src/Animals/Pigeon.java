package Animals;

import Olympics.Medal;

/**
 * Represents a Pigeon, which is a type of air animal.
 * This class extends AirAnimal.
 */
public class Pigeon extends AirAnimal {

    private String family; // The family of the pigeon.

    /**
     * Constructs a Pigeon object with default values.
     * Initializes family to "Unknown".
     */
    public Pigeon() {
        super(); // Call the default constructor of the superclass AirAnimal
        this.family = "Unknown";
        this.setSound("Arr-rar-rar-rar-raah");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\pigeonFinal.png");
        setImg1(img);
    }

    /**
     * Constructs a Pigeon object with specified attributes.
     *
     * @param inputName     The name of the pigeon.
     * @param inputGender   The gender of the pigeon.
     * @param inputWeight   The weight of the pigeon.
     * @param inputSpeed    The speed of the pigeon.
     * @param inputMedals   The medals won by the pigeon (deep copied).
     * @param inputWingspan The wingspan of the pigeon.
     * @param inputFamily   The family of the pigeon.
     *
     */
    public Pigeon(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, double inputWingspan, String inputFamily) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputWingspan); // Call the constructor of the superclass AirAnimal
        this.family = inputFamily;
        this.setSound("Arr-rar-rar-rar-raah");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\pigeonFinal.png");
        setImg1(img);
    }

    /**
     * Constructs a copy of an existing Pigeon object.
     *
     * @param other The Pigeon object to copy.
     */
    public Pigeon(Pigeon other) {
        super(other); // Call the copy constructor of the superclass AirAnimal
        this.family = other.family;
        this.setSound("Arr-rar-rar-rar-raah");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\pigeonFinal.png");
        setImg1(img);
    }

    /**
     * Sets the family of the pigeon.
     *
     * @param family The new family of the pigeon.
     * @return true if the family was set successfully, false otherwise.
     * @throws IllegalArgumentException if family is null or empty.
     */
    public boolean setFamily(String family) {
        if (family == null || family.isEmpty()) {
            System.out.println("Family cannot be null or empty.");
            return false;
        }
        this.family = family;
        return true;
    }

    /**
     * Returns the family of the pigeon.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The family of the pigeon.
     */
    protected String getFamily() {
        return family;
    }

    /**
     * Compares this pigeon to another object for equality.
     * Returns true if the other object is a Pigeon with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this pigeon.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pigeon)) {
            return false;
        }
        Pigeon other = (Pigeon) obj;
        return super.equals(other) && family.equals(other.family);
    }

    /**
     * Returns a string representation of the pigeon.
     *
     * @return A string representation of the pigeon.
     */
    @Override
    public String toString() {
        return super.toString() + ", Family: " + family;
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Pigeon";
    }
}
