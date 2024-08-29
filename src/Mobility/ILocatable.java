package Mobility;


/**
 * Interface representing a locatable object.
 * This interface provides methods to get and set the location of an object.
 */
public interface ILocatable {

    /**
     * Gets the current location of the object.
     *
     * @return the current location as a Point object.
     */
    public Point getLocation();

    /**
     * Sets the location of the object.
     *
     * @param inputLocation the new location as a Point object.
     * @return true if the location was set successfully, false otherwise.
     */
    public boolean setLocation(Point inputLocation);
}
