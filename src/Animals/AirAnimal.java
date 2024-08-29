package Animals;

import Mobility.Point;
import Olympics.Medal;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;


/**
 * Represents an air animal, which is a type of animal that can fly.
 * Extends the Animal class to include attributes specific to air animals.
 */
public class AirAnimal extends Animal {

    /**
     * The wingspan of the air animal.
     */
    private double wingspan;

    /**
     * Constructs an AirAnimal object with default values.
     * Initializes wingspan to 0.0 and sets the initial location to (0, 100).
     */
    public AirAnimal(){
        super(); // Call the default constructor of the superclass Animal
        this.wingspan = 1.0;
        if (this instanceof AirAnimal) {
            this.setTrackNumber(new Random().nextInt(5) + 1);
        }
    }

    /**
     * Constructs an air animal with specified attributes, including wingspan.
     *
     * @param inputName    The name of the air animal.
     * @param inputGender  The gender of the air animal.
     * @param inputWeight  The weight of the air animal.
     * @param inputSpeed   The speed of the air animal.
     * @param inputMedals  The medals won by the air animal.
     * @param inputWingspan The wingspan of the air animal.
     *
     */
    public AirAnimal(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, double inputWingspan) {
        // Call the constructor of the superclass Animal
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, new Point(0, 100));
        this.wingspan = inputWingspan;
        if (this instanceof AirAnimal) {
            this.setTrackNumber(new Random().nextInt(5) + 1);
        }
    }

    /**
     * Constructs a copy of an existing AirAnimal object.
     *
     * @param other The AirAnimal object to copy.
     */
    public AirAnimal(AirAnimal other) {
        super(other); // Call the copy constructor of the superclass Animal
        // Copy the wingspan
        this.wingspan = other.wingspan;
        // Set the initial position to (0, 100) regardless of the original position
        if (this instanceof AirAnimal) {
            this.setTrackNumber(new Random().nextInt(5) + 1);
        }
    }

    /**
     * Returns the wingspan of the air animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The wingspan in meters.
     */
    protected double getWingspan() {
        return wingspan;
    }

    /**
     * Compares this air animal to another object for equality.
     * Returns true if the other object is an AirAnimal with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this air animal.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AirAnimal)) {
            return false;
        }
        AirAnimal other = (AirAnimal) obj;
        return super.equals(other) && Double.compare(other.wingspan, wingspan) == 0;
    }

    /**
     * Returns a string representation of the air animal.
     *
     * @return A string representation including the name, gender, weight, speed, medals, position, and wingspan of the air animal.
     */
    @Override
    public String toString() {
        return "AirAnimal{" +
                "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", weight=" + getWeight() +
                ", speed=" + getSpeed() +
                ", medals=" + Arrays.toString(getMedals()) +
                ", position=" + getLocation() +
                ", wingspan=" + wingspan +
                '}';
    }

    /**
     * Moves the animal to a specified location if conditions such as energy levels and track boundaries are met.
     *
     * @param otherLocation The Point representing the target location to move to.
     * @return boolean Returns true if the animal successfully moves; otherwise, false.
     *
     * Method Overview:
     * - Calculates the distance to the target location and the necessary X and Y adjustments based on speed.
     * - Ensures the move doesn't exceed track boundaries (e.g., x = 610).
     * - Checks energy levels to ensure the animal has enough energy for the move.
     * - If all conditions are satisfied, updates the animal's position and deducts the appropriate energy.
     */
    @Override
    public boolean move(Point otherLocation) {
        // Calculate the distance to the target location.
        double newDistance = calcDistance(otherLocation);

        // Calculate delta X and Y based on speed and direction.
        double deltaX = this.getSpeed() * (otherLocation.getX() - this.getLocation().getX()) / newDistance;
        double deltaY = this.getSpeed() * (otherLocation.getY() - this.getLocation().getY()) / newDistance;

        // Stop moving if the animal has reached the end of the track at x = 610.
        if (this.getLocation().getX() >= 610 || this.getLocation().getX() + deltaX >= 610) {
            this.setLocation(new Point(610, this.getLocation().getY())); // Set location at end of track.
            this.stopMoving(); // Stop the movement.
            return false;
        }

        if (this.canMove()) {
            if (otherLocation == null || (otherLocation.equals(this.getLocation()) && this.getLocation() != null)) {
                return false; // No movement if no target location or already at the target.
            }

            if (this.getEnergy() == 0 || this.getEnergy() - (this.getEnergyPerMeter() * newDistance) <= 0) {
                return false; // No movement if not enough energy.
            }

            // Calculate new point and move the animal.
            Point newPoint = new Point((int) (this.getLocation().getX() + deltaX), (int) (this.getLocation().getY() + deltaY));
            this.setLocation(newPoint);

            // Add the actual distance moved to total distance.
            double actualDistanceMoved = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            this.addTotalDistance(actualDistanceMoved);

            // Deduct the energy cost of the move.
            this.setEnergy((int) (this.getEnergy() - (this.getEnergyPerMeter() * Math.sqrt(deltaX * deltaX + deltaY * deltaY))));

            return true; // Movement was successful.
        }
        return false;
    }

    /**
     * Draws the animal on the panel based on its orientation.
     * The method selects an appropriate image and adjusts its position and size according to the animal's current orientation.
     *
     * @param g The Graphics object used for drawing.
     *
     * Details:
     * - EAST: Draws a wider image to represent movement to the east.
     * - SOUTH: Adjusts the image position slightly right for southward movement.
     * - WEST: Draws a wider image for westward movement, similar to east but in opposite direction.
     * - NORTH: Adjusts the image position and size for northward movement, making it taller.
     */
    @Override
    public void drawObject (Graphics g){
        if(this.getOrientation() == Orientation.EAST){// Animal move to east side
            g.drawImage(this.getImg1(), getLocation().getX(), getLocation().getY()-this.getSize()/10, this.getSize()*2, this.getSize(), this.getPanel());
        } else if (this.getOrientation() == Orientation.SOUTH) { // Animal move to the south side
            g.drawImage(this.getImg2(), getLocation().getX() + 30, getLocation().getY()-this.getSize()/10, this.getSize(), this.getSize(), this.getPanel());
        } else if (this.getOrientation() == Orientation.WEST) { // Animal move to the west side
            g.drawImage(this.getImg3(), getLocation().getX(), getLocation().getY()-this.getSize()/10, this.getSize()*2, this.getSize(), this.getPanel());
        } else if (this.getOrientation() == Orientation.NORTH) { // Animal move to the north side
            g.drawImage(this.getImg4(), getLocation().getX()-this.getSize()/2 +35, getLocation().getY()-this.getSize()/10, this.getSize(), this.getSize()*2 - 15, this.getPanel());
        }
    }

    /**
     * Sets the initial position of the animal based on its track number and the panel height.
     * The method calculates the vertical starting position and adjusts it according to the track number.
     *
     * @param panelHeight The height of the panel where the animal is placed.
     *
     * Details:
     * - Track 5: Starts the animal at a higher Y position for the fifth track.
     * - Track 4: Starts the animal at a slightly lower Y position for the fourth track.
     * - Track 3: Starts the animal at a moderate Y position for the third track.
     * - Track 2: Starts the animal at a lower Y position for the second track.
     * - Track 1: Starts the animal at the lowest Y position for the first track.
     */
    public void setStartPoint(int panelHeight) {
        // Calculate the vertical start position based on the track number.
        int y = calculateTrackY(this.getTrackNumber(), 5, panelHeight);

        // Set different starting Y positions based on the track number.
        if (this.getTrackNumber() == 5) {
            this.setLocation(new Mobility.Point(0, y + 45));
        } else if (this.getTrackNumber() == 4) {
            this.setLocation(new Mobility.Point(0, y + 35));
        } else if (this.getTrackNumber() == 3) {
            this.setLocation(new Mobility.Point(0, y + 20));
        } else if (this.getTrackNumber() == 2) {
            this.setLocation(new Mobility.Point(0, y + 15));
        } else if (this.getTrackNumber() == 1) {
            this.setLocation(new Mobility.Point(0, y + 5));

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
        int y = calculateTrackY(this.getTrackNumber(), 5, panelHeight);

        // Calculate the horizontal position (x) based on the participant number.
        int x = (int) (this.getNeededDistance() - (this.getNeededDistance()/numberParticipants) * thisParticipantNumber);

        // Set different starting Y positions based on the track number.
        if (this.getTrackNumber() == 5) {
            this.setLocation(new Mobility.Point(x, y + 45));
        } else if (this.getTrackNumber() == 4) {
            this.setLocation(new Mobility.Point(x, y + 35));
        } else if (this.getTrackNumber() == 3) {
            this.setLocation(new Mobility.Point(x, y + 20));
        } else if (this.getTrackNumber() == 2) {
            this.setLocation(new Mobility.Point(x, y + 15));
        } else if (this.getTrackNumber() == 1) {
            this.setLocation(new Mobility.Point(x, y + 5));
        }
    }

    /**
     * Returns a category string.
     * This static method returns the string "Air" as the category.
     *
     * @return A string representing the default category: "Water".
     */
    @Override
    public String getCategoryFromAnimal(){
        return "Air";
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Air animal";
    }

    /**
     * Returns the distance required for the animal to complete in a race or competition.
     * This distance is used to determine when the animal has reached its goal.
     *
     * @return the required distance for the animal to travel
     */
    public double getNeededDistance(){
        return 600.0;
    }
}
