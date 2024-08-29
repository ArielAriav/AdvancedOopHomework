package Mobility;

/**
 * A class representing a point in 2D space with non-negative coordinates.
 */
public class Point implements Cloneable{
    /**
     * The X coordinate of the point.
     * Must be non-negative.
     */
    private int x;

    /**
     * The Y coordinate of the point.
     * Must be non-negative.
     */
    private int y;

    /**
     * Constructs a point with default coordinates (0, 0).
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructs a point with the specified non-negative coordinates.
     *
     * @param x The X coordinate (must be non-negative).
     * @param y The Y coordinate (must be non-negative).
     * @throws IllegalArgumentException if any coordinate is negative.
     */
    public Point(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative.");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X coordinate of the point.
     *
     * @return The X coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate of the point.
     *
     * @return The Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the X coordinate of the point.
     *
     * @param x The new X coordinate to set.
     * @return true if the X coordinate was set successfully, false otherwise.
     *         Returns false if the input value is negative.
     */
    public boolean setX(int x) {
        if (x < 0) {
           System.out.println("Coordinate must be non-negative.");
           return false;
        }
        this.x = x;
        return true;
    }

    /**
     * Sets the Y coordinate of the point.
     *
     * @param y The new Y coordinate to set.
     * @return true if the Y coordinate was set successfully, false otherwise.
     *         Returns false if the input value is negative.
     */
    public boolean setY(int y) {
        if (y < 0) {
            System.out.println("Coordinate must be non-negative.");
            return false;
        }
        this.y = y;
        return true;
    }

    /**
     * Returns a string representation of the Point object.
     * The string includes the x and y coordinates of the point.
     *
     * @return A string in the format "Point{x=<x>, y=<y>}", where <x> and <y> are the coordinates.
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    /**
     * Compares this point to the specified object.
     * The result is true if and only if the argument is not null and is a Point object that represents the same coordinates as this object.
     *
     * @param obj The object to compare this Point against.
     * @return true if the given object represents a Point with the same coordinates as this Point, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    /**
     * Returns a clone of this Point object.
     *
     * @return A new Point object with the same coordinates as this object.
     */
    @Override
    public Point clone() {
        return new Point(this.x, this.y);
    }
}
