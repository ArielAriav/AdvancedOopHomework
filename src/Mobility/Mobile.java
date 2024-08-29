package Mobility;

/**
 * Abstract class representing a mobile entity in space.
 * This class provides a basic implementation for movement in a 2D space
 * and implements the ILocatable interface.
 */
public abstract class Mobile implements ILocatable {

    /**
     * The current location of the mobile entity.
     */
    private Point location;

    /**
     * The distance the entity has traveled.
     */
    private double totalDistance;

    /**
     * Constructs a mobile entity with default values.
     * The default location is (0, 0) and the default total distance traveled is 0.0.
     */
    public Mobile() {
        this.location = new Point(0, 0);
        this.totalDistance = 0.0;
    }

    /**
     * Constructs a mobile entity with the specified initial location.
     *
     * @param inputLocation The initial location of the mobile entity as a Point object.
     */
    public Mobile(Point inputLocation) {
        this.location = new Point(inputLocation.getX(), inputLocation.getY());
        this.totalDistance = 0.0;
    }

    /**
     * Copy constructor initializing a Mobile object with the same attributes as another Mobile object.
     *
     * @param other The Mobile object to copy.
     */
    public Mobile(Mobile other) {
        this.location = new Point(other.location.getX(), other.location.getY());
        this.totalDistance = other.totalDistance;
    }

    /**
     * Returns the current location of the mobile entity.
     *
     * @return The current location of the mobile entity as a Point object.
     */
    @Override
    public Point getLocation() {
        return location;
    }

    /**
     * Sets the location of the mobile entity.
     *
     * @param inputLocation The new location to set as a Point object.
     * @return true if the location was set successfully, false otherwise.
     */
    @Override
    public boolean setLocation(Point inputLocation) {
        if (inputLocation == null) {
            //System.out.println("The location cannot be null - the location hasn't been updated.");
            return false;
        }
        if (location.equals(inputLocation)) {
            return true;
        }
        //else
        this.location = new Point(inputLocation.getX(), inputLocation.getY());
        //System.out.println("The location has been set to " + this.location + " successfully.");
        return true;
    }

    /**
     * Returns the distance the entity has traveled.
     * @return The distance the entity has traveled.
     */
    public double getTotalDistance() {
        return totalDistance;
    }

     /**
     * Sets the total distance traveled by the mobile entity.
     *
     * @param newTotalDistance The new total distance to set.
     * @return true if the total distance was set successfully, false otherwise.
     */
    public boolean setTotalDistance(double newTotalDistance) {
        if(this.totalDistance == newTotalDistance){
            return false;
        }
        if(newTotalDistance<0){
            return false;
        }
        //else
        this.totalDistance = newTotalDistance;
        return true;
    }

     /**
     * Adds the specified distance to the total distance traveled by the mobile entity.
     *
     * @param addThisDistance The distance to add to the total distance.
     */
    public void addTotalDistance(double addThisDistance) {
        this.totalDistance += addThisDistance;
    }

     /**
     * Calculates the distance between the current mobile entity's location
     * and another specified location.
     *
     * @param otherLocation The other location to calculate the distance to.
     *                      Must not be null.
     * @return The distance between the two locations,
     *         or -1.0 if otherLocation is null.
     */
    public double calcDistance(Point otherLocation) {
        if (otherLocation == null) {
            return -1.0;
        }

        int x1 = this.location.getX();
        int y1 = this.location.getY();
        int x2 = otherLocation.getX();
        int y2 = otherLocation.getY();

        // Calculate the squared differences
        int dx = x2 - x1;
        int dy = y2 - y1;

        // Calculate the distance
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    /**
     * Moves the object to a new location.
     *
     * This method updates the object current location to the specified
     * `otherLocation` if it is not null and not the same as the current location.
     * It calculates the distance traveled to the new location, updates the current
     * location, and adds the distance to the total distance traveled.
     *
     * @param otherLocation the new location to move to, represented as a Point object.
     * @return true if the object successfully moves to the new location, false otherwise.
     */
    public boolean move(Point otherLocation) {
        // Check if the new location is null or the same as the current location
        if (otherLocation == null || (otherLocation.equals(this.location) && this.location != null)) {
            return false;
        }

        // Calculate the distance to the new location
        double newDistance = calcDistance(otherLocation);

        // Update the current location to the new location
        this.location = new Point(otherLocation.getX(), otherLocation.getY());

        // Add the distance traveled to the total distance
        this.addTotalDistance(newDistance);

        return true;
    }
}


