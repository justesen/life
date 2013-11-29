package life;


/**
 * Holds the coordinates of a cell, as well as it's number of neighbours. Used
 * for a potentially new cell.
 */
public class NewCell extends Cell {
    /** Number of neighbours this cell has. */
    private int neighbours;

    /**
     * Set x- and y-coordinate, and the initial number of neighbours, 1.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public NewCell(final int x, final int y) {
        super(x, y);
        this.neighbours = 1;
    }

    /**
     * Add an extra neighbour.
     */
    public void addNeighbour() {
        neighbours++;
    }

    /**
     * Get number of neighbours.
     * @return Number of neighbours.
     */
    public int getNeighbours() {
        return neighbours;
    }
}
