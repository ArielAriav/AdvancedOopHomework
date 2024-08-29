package Animals;

import Mobility.Point;
import Olympics.Medal;
import java.awt.*;
import java.util.Arrays;

/**
 * Represents a water animal that extends the Animal class.
 */
public class WaterAnimal extends Animal implements WaterAnimals{

    /**
     * The maximum depth to which the water animal can dive.
     */
    public static final int MAX_DIVE = -800;

    private double diveDepth; // The current diving depth of the water animal.

    /**
     * Default constructor initializing a WaterAnimal object with default values.
     * Initializes diveDepth to 0.0.
     */
    public WaterAnimal() {
        super(); // Call the default constructor of the superclass Animal
        this.diveDepth = 0.0;

    }

    /**
     * Constructor initializing a WaterAnimal object with specified values for all attributes.
     *
     * @param inputName      The name of the water animal.
     * @param inputGender    The gender of the water animal.
     * @param inputWeight    The weight of the water animal.
     * @param inputSpeed     The speed of the water animal.
     * @param inputMedals    The medals won by the water animal (deep copied).
     * @param inputDiveDepth The diving depth of the water animal.
     *
     */
    public WaterAnimal(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, double inputDiveDepth) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, new Point(50, 0)); // Call the constructor of the superclass Animal
        this.diveDepth = inputDiveDepth;

    }

    /**
     * Copy constructor initializing a WaterAnimal object with the same attributes as another WaterAnimal object.
     *
     * @param other The WaterAnimal object to copy.
     */
    public WaterAnimal(WaterAnimal other) {
        super(other); // Call the copy constructor of the superclass Animal
        this.diveDepth = other.diveDepth;

    }

    /**
     * Returns the diving depth of the water animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The diving depth of the water animal.
     */
    @Override
    public double getDiveDepth() {
        return diveDepth;
    }

    /**
     * Sets the diving depth of the water animal.
     *
     * @param inputDiveDepth The new diving depth of the water animal.
     * @return true if the diving depth was set successfully, false otherwise.
     */
    @Override
    public boolean setDiveDepth(double inputDiveDepth) {
        if (inputDiveDepth > 0) {
            System.out.println("Error: The diving depth must be less than or equal to 0");
            return false;
        }
        this.diveDepth = inputDiveDepth;
        return true;
    }

    /**
     * Compares this water animal to another object for equality.
     * Returns true if the other object is a WaterAnimal with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this water animal.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WaterAnimal)) {
            return false;
        }
        WaterAnimal other = (WaterAnimal) obj;
        return super.equals(other) && Double.compare(other.diveDepth, diveDepth) == 0;
    }

    /**
     * Returns a string representation of the water animal. Includes name, gender, weight, speed,
     * medals, position, and diving depth.
     *
     * @return A string representation of the water animal.
     */
    @Override
    public String toString() {
        return "WaterAnimal{" +
                "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", weight=" + getWeight() +
                ", speed=" + getSpeed() +
                ", medals=" + Arrays.toString(getMedals()) +
                ", position=" + getLocation() +
                ", diveDepth=" + diveDepth +
                '}';
    }

    /**
     * Attempts to dive the water animal by the specified amount.
     *
     * @param diveThisMuch The depth to dive, positive or negative.
     * @return true if the dive was successful (within limits), false otherwise.
     */
    @Override
    public boolean Dive(double diveThisMuch) {
        if (diveThisMuch > 0) {
            System.out.println("Depth to dive should be negative or zero.");
            return false;
        }

        // Calculate the potential new dive depth
        double newDiveDepth = this.diveDepth + diveThisMuch;

        // Check if the new dive depth exceeds the maximum allowed
        if (newDiveDepth < MAX_DIVE) {
            System.out.println("Can't dive more than -800 total");
            return false;
        }

        this.diveDepth = newDiveDepth;
        return true;
    }

    /**
     * Attempts to move the animal to a specified location.
     * This method calculates the movement vector based on the current speed and direction,
     * checks energy levels, and updates the position if movement is possible.
     * Movement is constrained by the track's boundaries and the animal's energy.
     *
     * @param otherLocation The target location to move to.
     * @return boolean True if the animal moves successfully, false otherwise.
     */
    @Override
    public boolean move(Point otherLocation) {

        // Calculate the distance to the new location
        double newDistance = calcDistance(otherLocation);

        double deltaX = this.getSpeed() * (otherLocation.getX() - this.getLocation().getX()) / newDistance;
        double deltaY = this.getSpeed() * (otherLocation.getY() - this.getLocation().getY()) / newDistance;

        // Stop moving logic for animal reaching the end of the track.
        if (this.getLocation().getX() >= 585 || this.getLocation().getX() + deltaX >= 585) {
            // reached the end of the track
            this.setLocation(new Point(585, this.getLocation().getY()));
            this.stopMoving();
            return false;
        }

        if (this.canMove()) {
        // Check if the new location is null or the same as the current location
        if (otherLocation == null || (otherLocation.equals(this.getLocation()) && this.getLocation() != null)) {
            return false;
        }

        // Check if there is enough energy to move to the new location
        if (this.getEnergy() == 0 || this.getEnergy() - (this.getEnergyPerMeter() * newDistance) <= 0) {
            return false; // Return false if not enough energy for the move
        }

        Point newPoint = new Point((int) (this.getLocation().getX() + deltaX), (int) (this.getLocation().getY() + deltaY));

        // Update the animal's location
        this.setLocation(newPoint);
        // Update the total distance moved
        this.addTotalDistance(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
        this.setEnergy((int) (this.getEnergy() - (this.getEnergyPerMeter() * Math.sqrt(deltaX * deltaX + deltaY * deltaY))));

        return true; // Return true to indicate successful movement
    }
    return false;
    }

    /**
     * Sets the starting point for an animal based on its track number.
     * This method calculates the y-coordinate for the starting position using the track number and the total panel height,
     * then adjusts the position slightly for visual spacing between tracks.
     *
     * @param panelHeight The height of the panel on which the tracks are drawn, used to determine the vertical position.
     */
    @Override
    public void setStartPoint(int panelHeight) {

        int y = calculateTrackY(this.getTrackNumber(), 4, panelHeight);
        switch (this.getTrackNumber()) {

            case 4:
                this.setLocation(new Mobility.Point(30, y + 10));
                break;
            case 3:
                this.setLocation(new Mobility.Point(30, y + 30));
                break;
            case 2:
                this.setLocation(new Mobility.Point(30, y + 40));
                break;
            case 1:
                this.setLocation(new Mobility.Point(30, y + 55));
                break;
        }
    }

    /**
     * Sets the starting position for an animal in a courier competition.
     * The position is determined based on the number of participants and the
     * current participant's index.
     *
     * @param panelHeight The height of the competition panel, used to determine vertical positioning.
     * @param numberParticipants The total number of participants in the courier competition.
     * @param thisParticipantNumber The index of the current participant.
     */
    @Override
    public void setStartPointCourier(int panelHeight, int numberParticipants, int thisParticipantNumber) {
        // Calculate the vertical start position (y) based on the track number.
        int y = calculateTrackY(this.getTrackNumber(), 4, panelHeight);

        // Calculate the horizontal position (x) based on the participant number.
        int x = (int) (this.getNeededDistance() - (this.getNeededDistance()/numberParticipants) * thisParticipantNumber + 40);

        switch (this.getTrackNumber()) {
            case 4:
                this.setLocation(new Mobility.Point(x, y + 10));
                break;
            case 3:
                this.setLocation(new Mobility.Point(x, y + 30));
                break;
            case 2:
                this.setLocation(new Mobility.Point(x, y + 40));
                break;
            case 1:
                this.setLocation(new Mobility.Point(x, y + 55));
                break;
            default:
                // Optionally handle unexpected track numbers.
                break;
        }
    }



    /**
     * Draws the animal on the panel according to its orientation.
     * Each orientation (East, South, West, North) has a different image associated with it,
     * and the method adjusts the image's drawing position based on these orientations.
     *
     * @param g The Graphics object used for drawing the image on the panel.
     */
    @Override
    public void drawObject (Graphics g){
        if(this.getOrientation() == Orientation.EAST){ // Animal move to east side
            g.drawImage(this.getImg1(), getLocation().getX(), getLocation().getY()-this.getSize()/10, this.getSize()*2, this.getSize(), this.getPanel());
        } else if (this.getOrientation()  == Orientation.SOUTH) { // Animal move to the south side
            g.drawImage(this.getImg2(), getLocation().getX() + 30, getLocation().getY()-this.getSize()/10, this.getSize(), this.getSize(), this.getPanel());
        } else if (this.getOrientation()  == Orientation.WEST) { // Animal move to the west side
            g.drawImage(this.getImg3(), getLocation().getX(), getLocation().getY()-this.getSize()/10, this.getSize()*2, this.getSize(), this.getPanel());
        } else if (this.getOrientation()  == Orientation.NORTH) { // Animal move to the north side
            g.drawImage(this.getImg4(), getLocation().getX()-this.getSize()/2 +35, getLocation().getY()-this.getSize()/10, this.getSize(), this.getSize()*2 - 15, this.getPanel());
        }
    }

    /**
     * Returns a category string.
     * This static method returns the string "Water" as the category.
     *
     * @return A string representing the default category: "Water".
     */
    @Override
    public String getCategoryFromAnimal(){
        return "Water";
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Water animal";
    }

    /**
     * Returns the distance required for the animal to complete in a race or competition.
     * This distance is used to determine when the animal has reached its goal.
     *
     * @return the required distance for the animal to travel
     */
    public double getNeededDistance(){
        return 554.0;
    }
}

