package life;


/**
 * Holds the coordinates of a cell.
 */
public class Cell {
    /** x-coordinate of cell. */
    private final int x;
    /** y-coordinate of cell. */
    private final int y;

    /**
     * Set x- and y-coordinate.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Test if {@code obj} is equal to this object.
     * @param obj object to check for equality
     * @return {@code true} if they're equal - otherwise {@code false}.
     */
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Cell)) {
            return false;
        }
        Cell other = (Cell) obj;

        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    /**
     * Get x-coordinate
     * @return x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Get y-coordinate
     * @return y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * For equality testing (Collections.contains() doesn't work without this).
     * @return The hash code of this object.
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + x;
        result = prime * result + y;

        return result;
    }

    /**
     * String of this object of the form (x, y).
     * @return String representation of this object.
     */
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
