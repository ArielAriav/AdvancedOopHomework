package Animals;

import Olympics.Medal;

import java.awt.*;

/**
 * Represents a Snake, which is a type of terrestrial animal that can be poisonous.
 * This class extends TerrestrialAnimals and implements the IReptile interface.
 */
public class Snake extends TerrestrialAnimals implements IReptile {

    /**
     * Enum representing whether the snake is poisonous or not.
     */
    public enum Poisonous {
        LOW, MEDIUM, HIGH
    }

    private double length; //The length of the snake.
    private Poisonous poisonous; // Is the snake is poisonous or not

    /**
     * Constructs a Snake object with default values.
     * Initializes length to 0.0 and poisonous to NO.
     */
    public Snake() {
        super(); // Call the default constructor of the superclass TerrestrialAnimals
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_NORTH.png");
        setImg4(img);
        this.length = 0.0;
        this.poisonous = Poisonous.LOW;
        this.setSound("ssssssss");
    }

    /**
     * Constructs a Snake object with specified attributes.
     *
     * @param inputName       The name of the snake.
     * @param inputGender     The gender of the snake.
     * @param inputWeight     The weight of the snake.
     * @param inputSpeed      The speed of the snake.
     * @param inputMedals     The medals won by the snake (deep copied).
     * @param inputNoLegs     The number of legs of the snake (should be 0).
     * @param inputLength     The length of the snake.
     * @param isPoisonous     Indicates if the snake is poisonous.
     *
     */
    public Snake(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, int inputNoLegs, double inputLength, Poisonous isPoisonous) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputNoLegs); // Call the constructor of the superclass TerrestrialAnimals
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_NORTH.png");
        setImg4(img);
        this.length = inputLength;
        this.poisonous = isPoisonous;
        this.setSound("ssssssss");
    }

    /**
     * Constructs a copy of an existing Snake object.
     *
     * @param other The Snake object to copy.
     */
    public Snake(Snake other) {
        super(other); // Call the copy constructor of the superclass TerrestrialAnimals
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_EAST.png");
        setImg1(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_SOUTH.png");
        setImg2(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_WEST.png");
        setImg3(img);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\snake_NORTH.png");
        setImg4(img);
        this.length = other.length;
        this.poisonous = other.poisonous;
        this.setSound("ssssssss");
    }

    /**
     * Sets the length of the snake.
     *
     * @param length The new length of the snake.
     * @return true if the length was set successfully, false otherwise.
     */
    public boolean setLength(double length) {
        if (length <= 0) {
            System.out.println("Length must be greater than 0.");
            return false;
        }
        this.length = length;
        return true;
    }

    /**
     * Returns the length of the snake.
     *
     * @return The length of the snake.
     */
    protected double getLength() {
        return length;
    }

    /**
     * Returns if the snake is poisonous or not.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The poisonous status of the snake.
     */
    protected Poisonous getPoisonous() {
        return poisonous;
    }

    /**
     * Compares this snake to another object for equality.
     * Returns true if the other object is a Snake with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this snake.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Snake)) {
            return false;
        }
        Snake other = (Snake) obj;
        return super.equals(other) && Double.compare(other.length, length) == 0 && poisonous == other.poisonous;
    }

    /**
     * Returns a string representation of the snake. The string representation includes
     * the length, and whether the snake is poisonous.
     *
     * @return a string representation of the snake.
     */
    @Override
    public String toString() {
        return super.toString() + " Snake{" +
                "length=" + length +
                ", poisonous=" + poisonous +
                '}';
    }

    /**
     * Increases the speed of the snake by the specified amount.
     *
     * @param speedToAdd The amount by which to increase the speed.
     * @return true if the speed was increased successfully, false otherwise.
     */
    @Override
    public boolean speedUp(int speedToAdd) {
        if(speedToAdd <= 0) {
            System.out.println("Speed must be greater than 0.");
            return false;
        } else if (this.getSpeed() + speedToAdd > MAX_SPEED) {
            System.out.println("Total speed must be less than " + MAX_SPEED);
            return false;
        }
        this.setSpeed(this.getSpeed() + speedToAdd);
        return true;
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Snake";
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
