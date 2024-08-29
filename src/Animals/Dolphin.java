package Animals;

import Olympics.Medal;

/**
 * Represents a Dolphin, which is a type of water animal.
 * This class extends WaterAnimal.
 */
public class Dolphin extends WaterAnimal {

    /**
     * Enum representing the type of water the dolphin lives in.
     */
    public enum WaterType {
        Sea, Sweet
    }

    private WaterType waterType; // The type of water the dolphin lives in.

    /**
     * Constructs a Dolphin object with default values.
     * Initializes waterType to WaterType.Sea.
     */
    public Dolphin() {
        super(); // Call the default constructor of the superclass WaterAnimal
        this.waterType = WaterType.Sea;
        this.setSound("Click-Click");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dolphin_EAST.png");
        setImg1(img);
    }

    /**
     * Constructs a Dolphin object with specified attributes.
     *
     * @param inputName       The name of the dolphin.
     * @param inputGender     The gender of the dolphin.
     * @param inputWeight     The weight of the dolphin.
     * @param inputSpeed      The speed of the dolphin.
     * @param inputMedals     The medals won by the dolphin (deep copied).
     * @param inputDiveDepth  The diving depth of the dolphin.
     * @param inputWaterType  The type of water the dolphin lives in.
     *
     */
    public Dolphin(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, double inputDiveDepth, WaterType inputWaterType) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputDiveDepth); // Call the constructor of the superclass WaterAnimal
        this.waterType = inputWaterType;
        this.setSound("Click-Click");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dolphin_EAST.png");
        setImg1(img);
    }

    /**
     * Constructs a copy of an existing Dolphin object.
     *
     * @param other The Dolphin object to copy.
     */
    public Dolphin(Dolphin other) {
        super(other); // Call the copy constructor of the superclass WaterAnimal
        this.waterType = other.waterType;
        this.setSound("Click-Click");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dolphin_EAST.png");
        setImg1(img);
    }

    /**
     * Returns the water type of the dolphin.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The water type.
     */
    protected WaterType getWaterType() {
        return waterType;
    }

    /**
     * Sets the water type of the dolphin.
     *
     * @param waterType The new water type.
     * @return true if the water type was set successfully, false otherwise.
     */
    public boolean setWaterType(WaterType waterType) {
        if (waterType == null) {
            System.out.println("Water type cannot be null.");
            return false;
        }
        this.waterType = waterType;
        return true;
    }

    /**
     * Compares this Dolphin to another object for equality.
     * Returns true if the other object is a Dolphin with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this Dolphin.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Dolphin)) {
            return false;
        }
        Dolphin other = (Dolphin) obj;
        return super.equals(other) && this.waterType == other.waterType;
    }

    /**
     * Returns a string representation of the Dolphin.
     *
     * @return A string representing the Dolphin.
     */
    @Override
    public String toString() {
        return super.toString() + ", Water Type: " + waterType;
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Dolphin";
    }
}
