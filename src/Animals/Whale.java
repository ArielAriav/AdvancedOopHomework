package Animals;

import Olympics.Medal;

/**
 * Represents a Whale, which is a type of water animal.
 * This class extends WaterAnimal and includes attributes specific to whales.
 */
public class Whale extends WaterAnimal {

    private String foodType; // The type of food the whale eats.

    /**
     * Constructs a Whale object with default values.
     * Calls the default constructor of the superclass WaterAnimal.
     */
    public Whale() {
        super(); // Call the default constructor of the superclass WaterAnimal
        this.foodType = "Unknown";
        this.setSound("Splash");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\whale_EAST_F.png");
        setImg1(img);
    }

    /**
     * Constructs a Whale object with specified attributes.
     *
     * @param inputName       The name of the whale.
     * @param inputGender     The gender of the whale.
     * @param inputWeight     The weight of the whale.
     * @param inputSpeed      The speed of the whale.
     * @param inputMedals     The medals won by the whale (deep copied).
     * @param inputDiveDepth  The diving depth of the whale.
     * @param foodType        The type of food the whale eats.
     *
     */
    public Whale(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, double inputDiveDepth, String foodType) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputDiveDepth); // Call the constructor of the superclass WaterAnimal
        this.foodType = foodType;
        this.setSound("Splash");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\whale_EAST_F.png");
        setImg1(img);
    }

    /**
     * Constructs a copy of an existing Whale object.
     *
     * @param other The Whale object to copy.
     */
    public Whale(Whale other) {
        super(other); // Call the copy constructor of the superclass WaterAnimal
        this.foodType = other.foodType;
        this.setSound("Splash");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\whale_EAST_F.png");
        setImg1(img);
    }

    /**
     * Returns the type of food the whale eats.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The type of food.
     */
    protected String getFoodType() {
        return foodType;
    }

    /**
     * Sets the type of food the whale eats.
     *
     * @param inputFoodType The new type of food.
     * @return true if the food type was set successfully, false otherwise.
     */
    public boolean setFoodType(String inputFoodType) {
        if (inputFoodType == null || inputFoodType.isEmpty()) {
            System.out.println("Food type cannot be null or empty.");
            return false;
        }
        this.foodType = inputFoodType;
        return true;
    }

    /**
     * Compares this whale to another object for equality.
     * Returns true if the other object is a Whale with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this whale.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Whale)) {
            return false;
        }
        Whale other = (Whale) obj;
        return super.equals(other) && this.foodType.equals(other.foodType);
    }

    /**
     * Returns a string representation of the whale.
     *
     * @return A string representing the whale's attributes.
     */
    @Override
    public String toString() {
        return super.toString() + ", Food Type: " + this.foodType;
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Whale";
    }

}
