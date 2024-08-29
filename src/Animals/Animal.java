package Animals;

import Graphics.*;
import Mobility.ILocatable;
import Mobility.Mobile;
import Mobility.Point;
import Olympics.Medal;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;


/**
 * Abstract class representing an animal in the competition.
 * The Animal class extends Mobile and implements various interfaces to provide
 * functionalities such as drawing, moving, cloning, and location handling.
 */
public abstract class Animal extends Mobile implements IAnimal, Cloneable, ILocatable, IMoveable, IDrawable {

    //________________________________________________Fields___________________________________________________________
    /**
     * Enum representing the orientation of the animal.
     * The possible orientations are EAST, WEST, NORTH, and SOUTH.
     */
    public enum Orientation {
        EAST, WEST, NORTH, SOUTH
    }

    /**
     * Enum representing the gender of the animal.
     */
    public enum Gender {
        MALE, FEMALE, HERMAPHRODITE
    }

    public static final int MAX_ENERGY = 10000;
    private static int idCounter = 0; // Static counter to assign unique IDs
    private final int id; // Unique ID for each animal
    private int trackNumber; // indicating its lane in the competition.
    private boolean canMove = true; // Flag indicating whether the animal is capable of moving.
    protected BufferedImage img = null;
    private final int size = 65; // The size of the animal
    private Orientation orien; // The current orientation of the animal
    private int energy; // The current energy that the animal has
    private int maxEnergy = MAX_ENERGY;
    private int energyPerMeter; // The amount of energy consumed by the animal per meter moved
    private CompetitionPanel pan; // The panel on which the animal will be drawn
    private BufferedImage img1, img2, img3, img4; //  Buffered images representing different views of the animal
    private IDrawable iDrawable; // An instance of an object implementing the IDrawable interface.
    private IMoveable iMoveable; // An instance of an object implementing the IMoveable interface.
    private Cloneable iCloneable; // An instance of an object implementing the Cloneable interface.
    private String name;    // The private name of the animal.
    private Gender gender;  // The gender of the animal.
    private double weight;  // The weight of the animal.
    private double speed;   // The speed of the animal.
    private Medal[] medals;  // All the medals won by the animal.
    private String sound; // The sound the animal make.


    //_________________________________________________Constructors___________________________________________________
    /**
     * Default constructor initializing an Animal object with default values.
     * Initializes name to an empty string, gender to MALE,
     * weight and speed to 0.0, medals to an empty array, position to (0, 0),
     * orientation to EAST, and other default values for size, energy, etc.
     */
    public Animal() {
        super(); // Call the default constructor of the superclass Mobile
        this.energy = 10000;
        this.energyPerMeter = 1; // Default value for energy consumption per meter
        this.orien = Orientation.EAST; // Default orientation
        this.id = idCounter++;
        this.name = "";
        this.gender = Gender.MALE;
        this.weight = 0.0;
        this.speed = 0.0;
        this.medals = new Medal[0];
        this.sound = "";
        this.pan = null;
        this.img1 = null;
        this.img2 = null;
        this.img3 = null;
        this.img4 = null;
        this.iDrawable = null;
        this.iMoveable = null;
        this.iCloneable = null;

    }

    /**
     * Constructor initializing an Animal object with specified values for all attributes.
     *
     * @param name       The name of the animal.
     * @param gender     The gender of the animal.
     * @param weight     The weight of the animal.
     * @param speed      The speed of the animal.
     * @param medals     The medals won by the animal (deep copied).
     * @param location   The position of the animal (deep copied).
     *
     */
    public Animal(String name, Gender gender, double weight, double speed, Medal[] medals, Point location) {
        super(location); // Call the constructor of the superclass Mobile
        this.energy = 10000;
        this.energyPerMeter = 1; // Default value for energy consumption per meter
        this.orien = Orientation.EAST; // Default orientation
        this.id = idCounter++;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.speed = speed;
        this.sound = "";

        // Deep copy of medals array
        this.medals = new Medal[medals.length];
        for (int i = 0; i < medals.length; i++) {
            this.medals[i] = new Medal(medals[i]);
        }

        this.pan = null;
        this.img1 = null;
        this.img2 = null;
        this.img3 = null;
        this.img4 = null;
        this.iDrawable = null;
        this.iMoveable = null;
        this.iCloneable = null;
        this.setLocation(location);
    }

    /**
     * Copy constructor initializing an Animal object with the same attributes as another Animal object.
     *
     * @param other The Animal object to copy.
     */
    public Animal(Animal other) {
        super(other); // Call the copy constructor of the superclass Mobile
        this.energy = other.energy;
        this.energyPerMeter = other.energyPerMeter;
        this.orien = Orientation.EAST; // Default orientation
        this.id = idCounter++;
        this.name = other.name;
        this.gender = other.gender;
        this.weight = other.weight;
        this.speed = other.speed;
        this.sound = other.sound;

        // Deep copy of medals array
        this.medals = new Medal[other.medals.length];
        for (int i = 0; i < other.medals.length; i++) {
            this.medals[i] = new Medal(other.medals[i]);
        }
        // Deep copy of position
        setLocation(new Point(other.getLocation().getX(), other.getLocation().getY()));

        this.pan = other.pan;
        this.img1 = other.img1;
        this.img2 = other.img2;
        this.img3 = other.img3;
        this.img4 = other.img4;
        this.iDrawable = other.iDrawable;
        this.iMoveable = other.iMoveable;
        this.iCloneable = other.iCloneable;
    }

    //_______________________________________________________Getters___________________________________________________

    /**
     * Returns the name of the animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The name of the animal.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the gender of the animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The gender of the animal.
     */
    protected Gender getGender() {
        return gender;
    }

    /**
     * Returns the weight of the animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The weight of the animal.
     */
    protected double getWeight() {
        return weight;
    }

    /**
     * Returns the speed of the animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return The speed of the animal.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Returns the medals won by the animal.
     * <p>
     * This method is protected, meaning it can be accessed within the same package
     * or by subclasses of the class it is defined in.
     * </p>
     * @return An array of medals won by the animal.
     */
    protected Medal[] getMedals() {
        return medals;
    }

    /**
     * Returns the sound that the animal makes.
     *
     * @return a string representing the sound of the animal.
     */
    public String getSound() { return sound; }

    /**
     * Gets the current orientation of the animal.
     *
     * @return the current orientation of the animal.
     */
    public Orientation getOrientation() { return orien; }

    /**
     * Gets the current energy of the animal.
     *
     * @return the current energy of the animal.
     */
    public int getEnergy() { return energy; }

    /**
     * Gets the maximum energy of the animal.
     *
     * @return the maximum energy of the animal.
     */
    public int getMaxEnergy() { return maxEnergy; }

    /**
     * Gets the amount of energy consumed by the animal per meter moved.
     *
     * @return the amount of energy consumed by the animal per meter moved.
     */
    public int getEnergyPerMeter() { return energyPerMeter; }

    /**
     * Gets the panel on which the animal will be drawn.
     *
     * @return the panel on which the animal will be drawn.
     */
    public CompetitionPanel getPanel() { return pan; }

    /**
     * Gets the first buffered image representing a view of the animal.
     *
     * @return the first buffered image representing a view of the animal.
     */
    public BufferedImage getImg1() { return img1; }

    /**
     * Gets the second buffered image representing a view of the animal.
     *
     * @return the second buffered image representing a view of the animal.
     */
    public BufferedImage getImg2() { return img2; }

    /**
     * Gets the third buffered image representing a view of the animal.
     *
     * @return the third buffered image representing a view of the animal.
     */
    public BufferedImage getImg3() { return img3; }

    /**
     * Gets the fourth buffered image representing a view of the animal.
     *
     * @return the fourth buffered image representing a view of the animal.
     */
    public BufferedImage getImg4() { return img4; }

    /**
     * Retrieves the track number assigned to the animal.
     * The track number is used to determine the animal's position in competitive events.
     *
     * @return The current track number assigned to this animal.
     */
    public int getTrackNumber() {
        return trackNumber;
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

    /**
     * Checks if the animal is able to move.
     * @return true if the animal can move, false otherwise.
     */
    public boolean canMove() {
        return this.canMove;
    }

    /**
     * Retrieves the size of the animal.
     * This size could represent any dimension of the animal as defined in its context, such as height or width.
     *
     * @return The size of the animal.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the type of this object as a string.
     *
     * @return a string representing the type of this object, which is "Animal"
     */
    public String getType(){
        return "Animal";
    }

    /**
     * Returns the distance required for the animal to complete in a race or competition.
     * This distance is used to determine when the animal has reached its goal.
     *
     * @return the required distance for the animal to travel
     */
    public double getNeededDistance(){
        return 500;
    }

    public void reset() {
        this.setTotalDistance(0.0);
        this.energy = this.maxEnergy; // Reset energy to maxEnergy or any initial value
        this.canMove = true;
    }

    //_______________________________________________________Setters___________________________________________________
    /**
     * Sets the name of the animal.
     *
     * @param inputName The new name of the animal.
     * @return true if the name was set successfully, false otherwise.
     */
    public boolean setName(String inputName) {
        if (inputName == null || inputName.isEmpty()) {
            System.out.println("Error: Name cannot be null or empty");
            return false;
        }
        this.name = inputName;
        return true;
    }

    /**
     * Sets the gender of the animal.
     *
     * @param inputGender The new gender of the animal.
     * @return true if the gender was set successfully, false otherwise.
     */
    public boolean setGender(Gender inputGender) {
        if (inputGender == null) {
            System.out.println("Error: Gender cannot be null");
            return false;
        }
        this.gender = inputGender;
        return true;
    }

    /**
     * Sets the weight of the animal.
     *
     * @param weight The new weight of the animal.
     * @return true if the weight was set successfully, false otherwise.
     */
    public boolean setWeight(double weight) {
        if (weight <= 0) {
            System.out.println("Error: Weight must be greater than 0");
            return false;
        }
        this.weight = weight;
        return true;
    }

    /**
     * Sets the speed of the animal.
     *
     * @param speed The new speed of the animal.
     * @return true if the speed was set successfully, false otherwise.
     */
    public boolean setSpeed(double speed) {
        if (speed <= 0) {
            System.out.println("Error: Speed must be greater than 0");
            return false;
        }
        this.speed = speed;
        return true;
    }

    /**
     * Sets the medals won by the animal.
     *
     * @param medals An array of medals won by the animal.
     * @return true if the medals were set successfully, false otherwise.
     */
    public boolean setMedals(Medal[] medals) {
        if (medals == null || medals.length == 0) {
            System.out.println("Error: Medals cannot be null or empty");
            return false;
        }
        // Perform a shallow copy of the medals array
        this.medals = Arrays.copyOf(medals, medals.length);
        return true;
    }

    /**
     * Sets the sound for this animal.
     *
     * @param inputSound the new sound to set. It cannot be null or empty.
     * @return true if the sound was successfully set, false otherwise.
     */
    public boolean setSound(String inputSound) {
        if (inputSound == null || inputSound.isEmpty()) {
            System.out.println("Error: Sound cannot be null or empty");
            return false;
        }
        this.sound = inputSound;
        return true;
    }

    /**
     * Sets the energy for this animal.
     *
     * @param energy the new energy level to set. Must be between 0 and {@code maxEnergy} (inclusive).
     * @return {@code true} if the energy was successfully set; {@code false} if the energy was out of range.
     */
    public boolean setEnergy(int energy) {
        if (energy >= 0 && energy <= maxEnergy) {
            this.energy = energy;
            return true;
        }
        return false;
    }

    /**
     * Sets the first image for this object.
     *
     * @param img1 the new image to set. It cannot be {@code null}.
     * @return {@code true} if the image was successfully set; {@code false} if the image was {@code null}.
     */
    public boolean setImg1(BufferedImage img1) {
        if (img1 != null) {
            this.img1 = img1;
            return true;
        }
        return false;
    }

    /**
     * Sets the second image for this object.
     *
     * @param img2 the new image to set. It cannot be {@code null}.
     * @return {@code true} if the image was successfully set; {@code false} if the image was {@code null}.
     */
    public boolean setImg2(BufferedImage img2) {
        if (img2 != null) {
            this.img2 = img2;
            return true;
        }
        return false;
    }

    /**
     * Sets the third image for this object.
     *
     * @param img3 the new image to set. It cannot be {@code null}.
     * @return {@code true} if the image was successfully set; {@code false} if the image was {@code null}.
     */
    public boolean setImg3(BufferedImage img3) {
        if (img3 != null) {
            this.img3 = img3;
            return true;
        }
        return false;
    }

    /**
     * Sets the fourth image for this object.
     *
     * @param img4 the new image to set. It cannot be {@code null}.
     * @return {@code true} if the image was successfully set; {@code false} if the image was {@code null}.
     */
    public boolean setImg4(BufferedImage img4) {
        if (img4 != null) {
            this.img4 = img4;
            return true;
        }
        return false;
    }

    /**
     * Sets the track number for this animal.
     * @param trackNumber the new track number to assign to this animal.
     */
    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    /**
     * Sets the orientation of this animal.
     * @param orien the new orientation to set for this animal.
     */
    public void setOrien(Orientation orien) {
        this.orien = orien;
    }

    /**
     * Sets the starting location of the animal at the beginning of a race or event.
     * This implementation places the animal at the origin point of the panel or race track.
     *
     * @param panelHeight The height of the panel in which the animal is displayed.
     *
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
    public void setStartPointCourier(int panelHeight, int numberParticipants, int thisParticipantNumber) {
        this.setLocation(new Point(0, 0));
    }



    //_______________________________________________Other methods_____________________________________________________
    /**
     * Compares this animal to the specified object. The result is true if and only if the argument is an Animal object
     * with the same size, id, orientation, energy, max energy, energy per meter, name, gender, weight, speed, medals,
     * position, panel, images, drawable, moveable, and cloneable attributes as this object.
     *
     * @param obj the object to compare this Animal against.
     * @return true if the given object represents an Animal equivalent to this animal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Animal)) {
            return false;
        }
        Animal other = (Animal) obj;
        return size == other.size &&
                orien == other.orien &&
                energy == other.energy &&
                maxEnergy == other.maxEnergy &&
                energyPerMeter == other.energyPerMeter &&
                Objects.equals(name, other.name) &&
                gender == other.gender &&
                Double.compare(other.weight, weight) == 0 &&
                Double.compare(other.speed, speed) == 0 &&
                Arrays.equals(medals, other.medals) &&
                Objects.equals(pan, other.pan) &&
                Objects.equals(img1, other.img1) &&
                Objects.equals(img2, other.img2) &&
                Objects.equals(img3, other.img3) &&
                Objects.equals(img4, other.img4) &&
                Objects.equals(iDrawable, other.iDrawable) &&
                Objects.equals(iMoveable, other.iMoveable) &&
                Objects.equals(iCloneable, other.iCloneable);
    }

    /**
     * Returns a string representation of the animal. The string representation includes the size, id, orientation,
     * energy, max energy, energy per meter, name, gender, weight, speed, medals, position, panel, images, drawable,
     * moveable, and cloneable attributes of the animal.
     *
     * @return a string representation of the animal.
     */
    @Override
    public String toString() {
        return "Animal{" +
                "size=" + size +
                ", orientation=" + orien +
                ", energy=" + energy +
                ", maxEnergy=" + maxEnergy +
                ", energyPerMeter=" + energyPerMeter +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", weight=" + weight +
                ", speed=" + speed +
                ", medals=" + Arrays.toString(medals) +
                ", position=" + getLocation().toString() +
                '}';
    }

    /**
     * Prints the sound made by the animal to the console.
     * The output format is: "Animal <name> said <sound>".
     * If the name or sound is not set, it will print whatever default or current value is set for them.
     */
    public void makeSound(){
        System.out.println("Animal "+"<"+this.name+"> "+"said "+"<"+this.sound+">");
    }

    /**
     * Creates and returns a deep copy of this Animal object.
     *
     * This method overrides the `clone` method from the `Object` class to provide a deep copy
     * of the Animal object. It performs a shallow copy using the `super.clone()` method and then
     * manually deep copies fields that require it, such as arrays and mutable objects.
     *
     * <p>Steps performed in this method:</p>
     * <ul>
     *   <li>Calls `super.clone()` to create a shallow copy of the object.</li>
     *   <li>Deep copies the `medals` array by creating new `Medal` objects for each element.</li>
     *   <li>Deep copies the `location` object by creating a new `Point` with the same coordinates.</li>
     *   <li>Copies references for immutable objects like `BufferedImage` and `CompetitionPanel`.</li>
     * </ul>
     *
     * @return a new `Animal` object that is a deep copy of this object.
     *
     * @throws AssertionError if the `CloneNotSupportedException` is unexpectedly thrown.
     */
    @Override
    public Animal clone() {
        try {
            Animal cloned = (Animal) super.clone(); // Call Object's clone method for shallow copy

            // Perform deep copy for fields that require it
            cloned.medals = new Medal[this.medals.length];
            for (int i = 0; i < this.medals.length; i++) {
                cloned.medals[i] = new Medal(this.medals[i]);
            }
            cloned.setLocation(new Point(this.getLocation().getX(), this.getLocation().getY()));

            // Ensure other fields are copied or initialized as needed
            cloned.img1 = this.img1; // Assuming BufferedImage is immutable or deep copy is not needed
            cloned.img2 = this.img2;
            cloned.img3 = this.img3;
            cloned.img4 = this.img4;
            cloned.pan = this.pan; // Assuming CompetitionPanel is shared
            cloned.iDrawable = this.iDrawable; // Assuming interfaces are shared
            cloned.iMoveable = this.iMoveable;
            cloned.iCloneable = this.iCloneable;

            return cloned;
        } catch (CloneNotSupportedException e) {
            // This should not happen since Animal implements Cloneable
            throw new AssertionError("Unexpected CloneNotSupportedException", e);
        }
    }

    /**
     * Moves the animal to a new location.
     *
     * This method updates the animal's current location to the specified
     * `otherLocation` if it is not null, not the same as the current location,
     * and if the animal has enough energy to move to the new location.
     * It calculates the distance traveled to the new location, updates the current
     * location, adds the distance to the total distance traveled, and deducts
     * the energy used for moving.
     *
     * @param otherLocation the new location to move to, represented as a Point object.
     * @return true if the animal successfully moves to the new location, false otherwise.
     */
    public boolean move(Point otherLocation) {
        if (this.canMove) {
            // Check if the new location is null or the same as the current location
            if (otherLocation == null || (otherLocation.equals(this.getLocation()) && this.getLocation() != null)) {
                return false;
            }

            // Calculate the distance to the new location
            double newDistance = calcDistance(otherLocation);

            // Check if there is enough energy to move to the new location
            if (this.energy == 0 || this.energy - (this.energyPerMeter * newDistance) <= 0) {
                return false; // Return false if not enough energy for the move
            }


            double deltaX = this.speed * (otherLocation.getX() - this.getLocation().getX()) / newDistance;
            double deltaY = this.speed * (otherLocation.getY() - this.getLocation().getY()) / newDistance;
            Point newPoint = new Point((int) (this.getLocation().getX() + deltaX), (int) (this.getLocation().getY() + deltaY));

            // Update the animal's location
            this.setLocation(newPoint);
            // Update the total distance moved
            this.addTotalDistance(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
            this.energy -= this.energyPerMeter * Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            return true; // Return true to indicate successful movement
        }
        return false;
    }

    /**
     * Draws the image of the animal on the specified Graphics context based on its orientation.
     *
     * This method overrides the `drawObject` method from the `IDrawable` interface to provide
     * custom drawing behavior for the animal depending on its current orientation. The animal's
     * image is drawn at a specific position on the Graphics context, adjusted for orientation.
     *
     * The image is drawn with different sizes and positions depending on the animal's direction:
     * - **East**: The image `img1` is drawn at the animal's current position with double width and normal height.
     * - **South**: The image `img2` is drawn at the animal's current position with normal width and height.
     * - **West**: The image `img3` is drawn at the animal's current position with double width and normal height.
     * - **North**: The image `img4` is drawn at the animal's current position with normal width and double height.
     *
     * The drawing positions are adjusted with a small offset to ensure proper alignment.
     *
     * @param g the Graphics context used for drawing. This parameter cannot be null.
     */
    @Override
    public void drawObject (Graphics g){
        if(orien == Orientation.EAST){// Animal move to east side
            g.drawImage(img1, getLocation().getX(), getLocation().getY()-size/10, size*2, size, pan);
        } else if (orien == Orientation.SOUTH) { // Animal move to the south side
            g.drawImage(img2, getLocation().getX() + 30, getLocation().getY()-size/10, size, size, pan);
        } else if (orien == Orientation.WEST) { // Animal move to the west side
            g.drawImage(img3, getLocation().getX(), getLocation().getY()-size/10, size*2, size, pan);
        } else if (orien == Orientation.NORTH) { // Animal move to the north side
            g.drawImage(img4, getLocation().getX()-size/2 +35, getLocation().getY()-size/10, size, size*2 - 15, pan);
        }
    }

    /**
     * Attempts to increase the animal's energy by a specified amount.
     * The energy added is capped at the animal's maximum energy limit.
     *
     * @param energy The amount of energy to be added. Must be positive.
     * @return true if the energy is successfully added, false if the energy input is zero or negative,
     *         or if the animal is already at maximum energy.
     *
     * Details:
     * - If the energy addition would cause the animal's energy to exceed its maximum,
     *   the energy is set to the maximum limit.
     * - If the animal's energy is already at the maximum, no energy is added.
     */
    @Override
    public boolean eat(int energy) {
        if (energy <= 0) {
            return false; // Ensure that energy added is always positive
        }
        if (this.getEnergy() == this.getMaxEnergy()) {
            return false; // No need to add energy if already at maximum
        }

        int newEnergy = this.getEnergy() + energy;
        if (newEnergy > this.getMaxEnergy()) {
            this.setEnergy(this.getMaxEnergy()); // Set energy to maximum if it exceeds
        } else {
            this.setEnergy(newEnergy); // Set new energy level
        }
        return true;
    }

    /**
     * Loads an image file and assigns it to the appropriate orientation image variable
     * based on the current orientation of the object.
     *
     * @param nm the path to the image file to be loaded
     */
    @Override
    public void loadImages(String nm) {
        try {
            img = ImageIO.read(new File(nm));
        } catch (IOException e) {
            System.out.println("Cannot load image " + nm);
        }
    }

    /**
     * Stops the movement of this animal.
     * This method sets the animal's ability to move to false.
     */
    public void stopMoving() {
        this.canMove = false;
    }

    /**
     * Allows the animal to continue moving.
     * This method sets the animal's ability to move to true.
     */
    public void continueMoving() {
        this.canMove = true;
    }

    /**
     * Calculates the Y position on the track for a given track number.
     * This method is used to determine the starting vertical position of an animal on its track,
     * based on the track number and the total number of tracks available.
     * @param trackNumber the track number for which the position is calculated.
     * @param totalTracks the total number of tracks on the panel.
     * @param panelHeight the total height of the panel in which the tracks are displayed.
     * @return the Y coordinate for the start of the specified track.
     */
    int calculateTrackY(int trackNumber, int totalTracks, int panelHeight) {
        int margin = 0; // No margin is used in this calculation, set to 0.
        int usableHeight = panelHeight - 2 * margin; // Calculate usable height deducting margins from both sides.
        int trackHeight = usableHeight / totalTracks; // Divide the usable height by the total number of tracks to find the height per track.
        return margin + trackHeight * (trackNumber - 1); // Calculate the Y position by multiplying the track height by (trackNumber - 1).
    }

    /**
     * Returns a category string.
     * This static method returns the string "Water" as the category.
     *
     * @return A string representing the default category: "Water".
     */
    public String getCategoryFromAnimal(){
        return "Animal";
    }

}
