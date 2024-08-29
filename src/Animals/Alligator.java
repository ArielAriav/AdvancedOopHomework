package Animals;

import Mobility.Point;
import Olympics.Medal;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Arrays;


/**
 * Represents an alligator, which is a type of animal that can live both in water and on land.
 * This class uses delegation to implement behaviors of both water and terrestrial animals.
 */
public class Alligator extends Animal implements WaterAnimals, IReptile, TerrestrialAnimal, ImageObserver {

    /**
     * Delegate for water animal behavior.
     */
    private WaterAnimal waterDelegate;

    /**
     * Delegate for terrestrial animal behavior.
     */
    private TerrestrialAnimals terrestrialDelegate;

    /**
     * The area where the alligator lives.
     */
    private String areaOfLiving; // The area where the alligator lives.

    /**
     * Constructs an Alligator object with default values.
     * Initializes the waterDelegate and terrestrialDelegate with default values.
     * Sets the sound of the alligator to "Roar".
     */
    public Alligator() {
        super();
        this.waterDelegate = new WaterAnimal();
        this.terrestrialDelegate = new TerrestrialAnimals();
        this.areaOfLiving = "Unknown";
        this.setSound("Roar");
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\alligator_EAST.png");
        setImg1(img);
    }

    /**
     * Constructs an Alligator object with specified attributes.
     *
     * @param inputName      The name of the alligator.
     * @param inputGender    The gender of the alligator.
     * @param inputWeight    The weight of the alligator.
     * @param inputSpeed     The speed of the alligator.
     * @param inputMedals    The medals won by the alligator (deep copied).
     * @param inputDiveDepth The diving depth of the alligator.
     * @param inputNoLegs    The number of legs of the alligator.
     * @param areaOfLiving   The area where the alligator lives.
     */
    public Alligator(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, double inputDiveDepth, int inputNoLegs, String areaOfLiving) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, new Point(50, 0));
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\alligator_EAST.png");
        setImg1(img);
        this.waterDelegate = new WaterAnimal(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputDiveDepth);
        this.terrestrialDelegate = new TerrestrialAnimals(inputName, inputGender, inputWeight, inputSpeed, inputMedals, inputNoLegs);
        this.areaOfLiving = areaOfLiving;
        this.setSound("Roar");

    }

    /**
     * Constructs a copy of an existing Alligator object.
     *
     * @param other The Alligator object to copy.
     */
    public Alligator(Alligator other) {
        super(other);
        loadImages("C:\\Users\\ariel\\intellijProjects\\AdvancedOopHomework\\src\\Graphics\\Images\\alligator_EAST.png");
        setImg1(img);
        this.waterDelegate = new WaterAnimal(other.waterDelegate);
        this.terrestrialDelegate = new TerrestrialAnimals(other.terrestrialDelegate);
        this.areaOfLiving = other.areaOfLiving;
        this.setSound("Roar");
    }

    /**
     * Returns the diving depth of the alligator.
     *
     * @return the diving depth of the alligator.
     */
    @Override
    public double getDiveDepth() {
        return waterDelegate.getDiveDepth();
    }

    /**
     * Sets the diving depth of the alligator.
     *
     * @param inputDiveDepth the new diving depth of the alligator.
     * @return true if the diving depth was set successfully, false otherwise.
     */
    @Override
    public boolean setDiveDepth(double inputDiveDepth) {
        return waterDelegate.setDiveDepth(inputDiveDepth);
    }

    /**
     * Makes the alligator dive by the specified amount.
     *
     * @param diveThisMuch the amount by which the alligator should dive.
     * @return true if the dive was successful, false otherwise.
     */
    @Override
    public boolean Dive(double diveThisMuch) {
        return waterDelegate.Dive(diveThisMuch);
    }

    /**
     * Returns the number of legs the alligator has.
     *
     * @return the number of legs the alligator has.
     */
    @Override
    public int getNoLegs() {
        return terrestrialDelegate.getNoLegs();
    }

    /**
     * Returns the area where the alligator lives.
     *
     * @return the area where the alligator lives.
     */
    protected String getAreaOfLiving() {
        return areaOfLiving;
    }

    /**
     * Sets the area where the alligator lives.
     *
     * @param inputAreaOfLiving the new area where the alligator lives.
     * @return true if the area of living was set successfully, false otherwise.
     */
    public boolean setAreaOfLiving(String inputAreaOfLiving) {
        if (inputAreaOfLiving == null || inputAreaOfLiving.isEmpty()) {
            System.out.println("Area of living cannot be null or empty.");
            return false;
        }
        this.areaOfLiving = inputAreaOfLiving;
        return true;
    }

    /**
     * Compares this alligator with another object for equality.
     * <p>
     * This method checks if the given object is an instance of the `Alligator` class and
     * compares all relevant fields of both the current and the provided `Alligator` object
     * to determine if they are equal.
     *
     * @param obj the object to compare with.
     * @return true if this alligator is equal to the specified object, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Alligator)) {
            return false;
        }
        Alligator other = (Alligator) obj;

        // Compare all fields including inherited ones
        return super.equals(other) &&
                this.areaOfLiving.equals(other.areaOfLiving) &&
                this.waterDelegate.equals(other.waterDelegate) &&
                this.terrestrialDelegate.equals(other.terrestrialDelegate) &&
                this.getOrientation() == other.getOrientation() &&
                this.getEnergy() == other.getEnergy() &&
                this.getEnergyPerMeter() == other.getEnergyPerMeter() &&
                this.getName().equals(other.getName()) &&
                this.getGender() == other.getGender() &&
                this.getWeight() == other.getWeight() &&
                this.getSpeed() == other.getSpeed() &&
                Arrays.equals(this.getMedals(), other.getMedals()) &&
                this.getLocation().equals(other.getLocation()) &&
                this.getSound().equals(other.getSound());
    }

    /**
     * Returns a string representation of the alligator.
     * <p>
     * This method provides a detailed string representation of the alligator's attributes,
     * including inherited fields, specific attributes of the `Alligator` class, and other
     * relevant information.
     *
     * @return a string representation of the alligator, including all relevant fields.
     */
    @Override
    public String toString() {
        return super.toString() +
                ", Area of Living: " + this.areaOfLiving +
                ", Dive Depth: " + getDiveDepth() +
                ", Number of Legs: " + getNoLegs() +
                ", Energy: " + getEnergy() +
                ", Max Energy: " + getMaxEnergy() +
                ", Energy per Meter: " + getEnergyPerMeter() +
                ", Name: " + getName() +
                ", Gender: " + getGender() +
                ", Weight: " + getWeight() +
                ", Speed: " + getSpeed() +
                ", Medals: " + Arrays.toString(getMedals()) +
                ", Position: " + getLocation() +
                ", Sound: " + getSound();
    }

    /**
     * Increases the speed of the alligator by the specified amount.
     *
     * @param speedToAdd The amount by which to increase the speed.
     * @return true if the speed was increased successfully, false otherwise.
     */
    @Override
    public boolean speedUp(int speedToAdd) {
        if (speedToAdd <= 0) {
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
     * Increases the energy level of this object by the specified amount.
     * If the energy level is already at maximum, this method does nothing.
     * If adding the specified amount of energy would exceed the maximum energy,
     * the energy level is set to the maximum value (100).
     *
     * @param energy the amount of energy to add. Must be a positive integer.
     * @return true if the energy was successfully updated, false if the energy level was already at maximum.
     */
    @Override
    public boolean eat(int energy) {
        if (this.getEnergy() == this.getMaxEnergy()) {
            return false;
        } else if (this.getEnergy() + energy > this.getMaxEnergy()) {
            this.setEnergy(100);
            return true;
        }
        this.setEnergy(this.getEnergy() + energy);
        return true;
    }

    /**
     * Returns the name of the animal.
     *
     * @return the name of the animal
     */
    @Override
    public String getAnimaleName() {
        return this.getName();
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }

    @Override
    public void setStartPoint(int panelHeight) {

        int y = calculateTrackY(this.getTrackNumber(), 4, panelHeight);
        switch (this.getTrackNumber()) {

            case 4:
                this.terrestrialDelegate.setLocation(new Mobility.Point(30, y + 10));
                this.waterDelegate.setLocation(new Mobility.Point(30, y + 10));
                this.setLocation(new Mobility.Point(30, y + 10));
                break;
            case 3:
                this.terrestrialDelegate.setLocation(new Mobility.Point(30, y + 30));
                this.waterDelegate.setLocation(new Mobility.Point(30, y + 30));
                this.setLocation(new Mobility.Point(30, y + 30));
                break;
            case 2:
                this.terrestrialDelegate.setLocation(new Mobility.Point(30, y + 40));
                this.waterDelegate.setLocation(new Mobility.Point(30, y + 40));
                this.setLocation(new Mobility.Point(30, y + 40));
                break;
            case 1:
                this.terrestrialDelegate.setLocation(new Mobility.Point(30, y + 55));
                this.waterDelegate.setLocation(new Mobility.Point(30, y + 55));
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
        int x = (int) (this.getNeededDistance() - (this.getNeededDistance()/numberParticipants) * thisParticipantNumber + 50);

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
                this.setLocation(new Mobility.Point(x , y + 55));
                break;
            default:
                // Optionally handle unexpected track numbers.
                break;
        }
    }


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
        return "Alligator";
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