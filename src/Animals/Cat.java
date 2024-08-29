package Animals;

import Olympics.Medal;

import java.awt.*;
import java.util.Arrays;

/**
 * Represents a cat, which is a type of terrestrial animal.
 * Extends the TerrestrialAnimals class to include attributes specific to cats.
 */
public class Cat extends TerrestrialAnimals {

    private boolean castrated; // Indicates whether the cat is castrated.

    /**
     * Constructs a Cat object with default values.
     * Initializes castrated to false.
     */
    public Cat() {
        super(); // Call the default constructor of the superclass TerrestrialAnimals
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_NORTH.png");
        setImg4(img);
        this.castrated = false;
        this.setSound("Meow");
    }

    /**
     * Constructs a Cat object with specified attributes, including castration status.
     *
     * @param inputName    The name of the cat.
     * @param inputGender  The gender of the cat.
     * @param inputWeight  The weight of the cat.
     * @param inputSpeed   The speed of the cat.
     * @param inputMedals  The medals won by the cat.
     * @param inputNoLegs       The number of legs of the cat.
     * @param inputCastrated    The castration status of the cat.
     */
    public Cat(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, int inputNoLegs, boolean inputCastrated) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputNoLegs); // Call the constructor of the superclass TerrestrialAnimals
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_NORTH.png");
        setImg4(img);
        this.castrated = inputCastrated;
        this.setSound("Meow");
    }

    /**
     * Copy constructor initializing a Cat object with the same attributes as another Cat object.
     *
     * @param other The Cat object to copy.
     */
    public Cat(Cat other) {
        super(other); // Call the copy constructor of the superclass TerrestrialAnimals
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\cat_NORTH.png");
        setImg4(img);
        this.castrated = other.castrated;
        this.setSound("Meow");
    }

    /**
     * Returns the castration status of the cat.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The castration status of the cat.
     */
    protected boolean isCastrated() {
        return castrated;
    }

    /**
     * Sets the castration status of the cat.
     *
     * @param castrated The new castration status of the cat.
     */
    public boolean setCastrated(boolean castrated) {
        this.castrated = castrated;
        return true;
    }

    /**
     * Compares this cat to another object for equality.
     * Returns true if the other object is a Cat with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this cat.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cat)) {
            return false;
        }
        Cat other = (Cat) obj;
        return super.equals(other) && castrated == other.castrated;
    }

    /**
     * Returns a string representation of the cat.
     *
     * @return A string representation including name, gender, weight, speed, medals, position, number of legs, and castration status.
     */
    @Override
    public String toString() {
        return "Cat{" +
                "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", weight=" + getWeight() +
                ", speed=" + getSpeed() +
                ", medals=" + Arrays.toString(getMedals()) +
                ", position=" + getLocation() +
                ", noLegs=" + getNoLegs() +
                ", castrated=" + castrated +
                '}';
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Cat";
    }

    /**
     * Renders the animal on the screen based on its current orientation and position.
     *
     * @param g The graphics context used for drawing.
     */
    @Override
    public void drawObject(Graphics g) {
        switch (this.getOrientation()) {
            case EAST:
                g.drawImage(this.getImg1(), getLocation().getX(), getLocation().getY() - this.getSize() / 10, this.getSize() * 2, this.getSize(), this.getPanel());
                break;
            case SOUTH:
                g.drawImage(this.getImg2(), getLocation().getX() + 45, getLocation().getY() - this.getSize() / 10, this.getSize(), this.getSize(), this.getPanel());
                break;
            case WEST:
                g.drawImage(this.getImg3(), getLocation().getX(), getLocation().getY() - this.getSize() / 10 - 20, this.getSize() * 2, this.getSize(), this.getPanel());
                break;
            case NORTH:
                g.drawImage(this.getImg4(), getLocation().getX() - this.getSize() / 2 + 35, getLocation().getY() - this.getSize() / 10, this.getSize(), this.getSize() * 2 - 15, this.getPanel());
                break;
        }
    }

}
