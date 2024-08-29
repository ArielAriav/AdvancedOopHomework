package Animals;

import Olympics.Medal;
import java.util.Arrays;

/**
 * Represents a dog, which is a type of terrestrial animal.
 * Extends the TerrestrialAnimals class to include attributes specific to dogs.
 */
public class Dog extends TerrestrialAnimals {

    private String breed; // The breed of the dog.

    /**
     * Constructs a Dog object with default values.
     * Initializes breed to "Labrador retriever".
     */
    public Dog() {
        super();
        this.breed = "Labrador retriever";
        this.setSound("Woof Woof");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_NORTH.png");
        setImg4(img);
    }

    /**
     * Constructs a Dog object with specified attributes, including breed.
     *
     * @param inputName    The name of the dog.
     * @param inputGender  The gender of the dog.
     * @param inputWeight  The weight of the dog.
     * @param inputSpeed   The speed of the dog.
     * @param inputMedals  The medals won by the dog.
     * @param inputNoLegs       The number of legs of the dog.
     * @param inputBreed        The breed of the dog.
     *
     *
     */
    public Dog(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, int inputNoLegs, String inputBreed) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputNoLegs); // Call the constructor of the superclass TerrestrialAnimals
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_NORTH.png");
        setImg4(img);
        this.breed = inputBreed;
        this.setSound("Woof Woof");
    }

    /**
     * Copy constructor initializing a Dog object with the same attributes as another Dog object.
     *
     * @param other The Dog object to copy.
     */
    public Dog(Dog other) {
        super(other); // Call the copy constructor of the superclass TerrestrialAnimals
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\dog_NORTH.png");
        setImg4(img);
        this.breed = other.breed;
        this.setSound("Woof Woof");
    }

    /**
     * Retrieves the breed of the animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return A String representing the breed of the animal.
     */
    protected String getBreed(){
        return breed;
    }

    /**
     * Compares this dog to another object for equality.
     * Returns true if the other object is a Dog with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this dog.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Dog)) {
            return false;
        }
        Dog other = (Dog) obj;
        return super.equals(other) && breed.equals(other.breed);
    }

    /**
     * Returns a string representation of the dog.
     *
     * @return A string representation including name, gender, weight, speed, medals, position, number of legs, and breed.
     */
    @Override
    public String toString() {
        return "Dog{" +
                "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", weight=" + getWeight() +
                ", speed=" + getSpeed() +
                ", medals=" + Arrays.toString(getMedals()) +
                ", position=" + getLocation() +
                ", noLegs=" + getNoLegs() +
                ", breed='" + breed + '\'' +
                '}';
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Dog";
    }
}
