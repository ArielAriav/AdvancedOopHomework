package Animals;

import Mobility.Point;
import Olympics.Medal;
import java.awt.*;
import java.util.Arrays;

/**
 * Represents a terrestrial animal, which is an animal that primarily lives on land.
 * Extends the Animal class to inherit common attributes and behaviors of animals.
 */
public class TerrestrialAnimals extends Animal implements TerrestrialAnimal {

    private int noLegs; // The number of legs of the terrestrial animal.

    /**
     * Constructs a TerrestrialAnimals object with default values.
     * Initializes noLegs to 0.
     */
    public TerrestrialAnimals() {
        super(); // Call the default constructor of the superclass Animal
        this.noLegs = 0;
        this.setLocation(new Mobility.Point(0, 0));
        if (this instanceof TerrestrialAnimal) {
            this.setTrackNumber(0);
        }
    }

    /**
     * Constructs a TerrestrialAnimals object with specified values for all attributes.
     *
     * @param inputName    The name of the terrestrial animal.
     * @param inputGender  The gender of the terrestrial animal.
     * @param inputWeight  The weight of the terrestrial animal.
     * @param inputSpeed   The speed of the terrestrial animal.
     * @param inputMedals  The medals won by the terrestrial animal.
     * @param inputNoLegs The number of legs of the terrestrial animal.
     *
     *
     */
    public TerrestrialAnimals(String inputName, Gender inputGender, double inputWeight, double inputSpeed, Medal[] inputMedals, int inputNoLegs) {
        super(inputName, inputGender, inputWeight, inputSpeed, inputMedals, new Point(0, 0)); // Call the constructor of the superclass Animal
        this.setLocation(new Mobility.Point(0, 0));
        this.noLegs = inputNoLegs;
        this.setTrackNumber(0);
    }

    /**
     * Copy constructor initializing a TerrestrialAnimals object with the same attributes as another TerrestrialAnimals object.
     *
     * @param other The TerrestrialAnimals object to copy.
     */
    public TerrestrialAnimals(TerrestrialAnimals other) {
        super(other); // Call the copy constructor of the superclass Animal
        this.setLocation(new Mobility.Point(0, 0));
        this.noLegs = other.noLegs;
        this.setTrackNumber(0);
    }

    /**
     * Returns the number of legs of the terrestrial animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The number of legs.
     */
    @Override
    public int getNoLegs() {
        return noLegs;
    }

    /**
     * Compares this terrestrial animal to another object for equality.
     * Returns true if the other object is a TerrestrialAnimals with the same attributes, false otherwise.
     *
     * @param obj The object to compare with this terrestrial animal.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TerrestrialAnimals)) {
            return false;
        }
        TerrestrialAnimals other = (TerrestrialAnimals) obj;
        return super.equals(other) && this.noLegs == other.noLegs;
    }

    /**
     * Returns a string representation of the terrestrial animal.
     *
     * @return A string representation including name, gender, weight, speed, medals, position, and number of legs.
     */
    @Override
    public String toString() {
        return "TerrestrialAnimals{" +
                "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", weight=" + getWeight() +
                ", speed=" + getSpeed() +
                ", medals=" + Arrays.toString(getMedals()) +
                ", position=" + getLocation() +
                ", noLegs=" + noLegs +
                '}';
    }

    /**
     * Moves the animal according to its orientation and updates its position based on its speed.
     * The movement is only allowed if the animal can move (i.e., it has sufficient energy and is not instructed to stop).
     *
     * @param otherLocation The intended location to move to, currently not used in calculations directly.
     * @return true if the move is successful, false otherwise.
     */
    @Override
    public boolean move(Point otherLocation) {
        // Adjust orientation and check boundaries before moving
        if(!fitOrienBasedOnLoc(otherLocation)){
            return false;
        }

        if (this.canMove()) {
            int x = this.getLocation().getX();
            int y = this.getLocation().getY();

            // Validate the new location
            if (otherLocation == null || (otherLocation.equals(this.getLocation()) && this.getLocation() != null)) {
                return false;
            }

            double newDistance = calcDistance(otherLocation);
            if (this.getEnergy() == 0 || this.getEnergy() - (this.getEnergyPerMeter() * newDistance) <= 0) {
                return false;
            }

            // Calculate movement deltas based on orientation
            double deltaX = 0, deltaY = 0;
            switch (this.getOrientation()) {
                case EAST:
                    deltaX = this.getSpeed();
                    deltaY = 0;
                    break;
                case WEST:
                    deltaX = -this.getSpeed();
                    deltaY = 0;
                    break;
                case NORTH:
                    deltaX = 0;
                    deltaY = -this.getSpeed();
                    break;
                case SOUTH:
                    deltaX = 0;
                    deltaY = this.getSpeed();
                    break;
            }

            if(x + (int) deltaX >= 0 && y + (int) deltaY >= 0){
                // Apply the calculated deltas to get the new position
                Point newPoint = new Point(x + (int) deltaX, y + (int) deltaY);

                // Update the animal's position
                this.setLocation(newPoint);
                // Update the total distance moved
                this.addTotalDistance(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
                // Reduce energy based on movement
                this.setEnergy((int) (this.getEnergy() - (this.getEnergyPerMeter() * Math.sqrt(deltaX * deltaX + deltaY * deltaY))));

                return true;
            } else {
                return false;
            }

        }
        return false;
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
                g.drawImage(this.getImg3(), getLocation().getX(), getLocation().getY() - this.getSize() / 10 - 14, this.getSize() * 2, this.getSize(), this.getPanel());
                break;
            case NORTH:
                g.drawImage(this.getImg4(), getLocation().getX() - this.getSize() / 2 + 35, getLocation().getY() - this.getSize() / 10, this.getSize(), this.getSize() * 2 - 15, this.getPanel());
                break;
        }
    }

    /**
     * Adjusts the orientation of the animal based on its current position and intended new location.
     *
     * @param otherLocation The target location which influences the orientation adjustments.
     */
    public boolean fitOrienBasedOnLoc(Point otherLocation) {
        double newDistance = calcDistance(otherLocation);

        double deltaX = this.getSpeed() * (otherLocation.getX() - this.getLocation().getX()) / newDistance;
        double deltaY = this.getSpeed() * (otherLocation.getY() - this.getLocation().getY()) / newDistance;

        int x = this.getLocation().getX();
        int y = this.getLocation().getY();

        // Adjust orientation based on position and intended movement direction
        if ((x >= 675 || x + deltaX >= 675) && (y == 0 || y + deltaY == 0) && this.getOrientation() == Animal.Orientation.EAST) {
            this.setOrien(Animal.Orientation.SOUTH);
        } else if ((x >= 670 || x + deltaX >= 670) && (y >= 450 || y + deltaY >= 450) && this.getOrientation() == Animal.Orientation.SOUTH) {
            this.setOrien(Animal.Orientation.WEST);
        } else if ((x == 0 || x + deltaX == 0) && (y >= 450 || y + deltaY >= 450) && this.getOrientation() == Animal.Orientation.WEST) {
            this.setOrien(Animal.Orientation.NORTH);
        } else if ((x == 0 || x + deltaX == 0) && (y == 0 || y + deltaY == 0) && this.getOrientation() == Animal.Orientation.NORTH) {
            // reached the end of the track
            this.stopMoving();
            return false;
        }
        return true;
    }

    /**
     * Initializes the starting point of the animal based on the height of the panel.
     * currently sets the location to the origin.
     *
     * @param panelHeight The height of the panel to consider for positioning, not currently used.
     */
    public void setStartPoint(int panelHeight) {
        this.setLocation(new Point(0, 0));
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
        if(thisParticipantNumber == 1){
            this.setLocation(new Point(0, 0));
            this.setOrien(Orientation.EAST);
        } else if(thisParticipantNumber == 2){
            this.setLocation(new Point(666, 0));
            this.setOrien(Orientation.SOUTH);
        } else if(thisParticipantNumber == 3){
            this.setLocation(new Point(666, 440));
            this.setOrien(Orientation.WEST);
        } else if(thisParticipantNumber == 4){
            this.setLocation(new Point(0, 400));
            this.setOrien(Orientation.NORTH);
        }
    }


    /**
     * Returns a category string.
     * This static method returns the string "Terrestrial" as the category.
     *
     * @return A string representing the default category: "Water".
     */
    @Override
    public String getCategoryFromAnimal(){
        return "Terrestrial";
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    @Override
    public String getType(){
        return "Terrestrial animal";
    }

    /**
     * Returns the distance required for the animal to complete in a race or competition.
     * This distance is used to determine when the animal has reached its goal.
     *
     * @return the required distance for the animal to travel
     */
    public double getNeededDistance(){
        return 2248.0;
    }
}
