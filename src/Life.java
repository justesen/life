package life;


import java.util.HashSet;


/**
 * An abstract model of an infinite grid of cells in Conway's game of Life.
 */
public class Life {
    /** Alive cells */
    private final HashSet<Cell> cells;
    /** Cells that has survived the current generation. */
    private final HashSet<Cell> survivors;
    /** Potential new cells in the current generation. */
    private final HashSet<NewCell> potentials;

    /** Generation. */
    private int generation;

    /**
     * Initially the there are no cells alive, and it is the zero'th generation.
     */
    public Life() {
        this.cells = new HashSet<Cell>();
        this.survivors = new HashSet<Cell>();
        this.potentials = new HashSet<NewCell>();
        this.generation = 0;
    }

    /**
     * Is the cell alive?
     * @param x x-coordinate of cell
     * @param y y-coordinate of cell
     * @return {@code true} if the cell is alive - otherwise {@code false}.
     */
    public boolean alive(final int x, final int y) {
        return cells.contains(new Cell(x, y));
    }

    /**
     * Update the grid of cells according to the rules:
     * <ul>
     *  <li>Any live cell with fewer than two live neighbours dies, as if caused
     *   by under-population.</li>
     *  <li>Any live cell with two or three live neighbours lives on to the next
     *   generation.</li>
     *  <li>Any live cell with more than three live neighbours dies, as if by
     *   overcrowding.</li>
     *  <li>Any dead cell with exactly three live neighbours becomes a live
     *   cell, as if by reproduction.</li>
     * </ul>
     */
    public void update() {
        for (final Cell c : cells) {
            int neighbours = 0;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if ((dx != 0 || dy != 0)
                     && alive(c.getX() + dx, c.getY() + dy)) {
                        neighbours++;
                    } else if (dx != 0 || dy != 0) {
                        addNeighbour(c.getX() + dx, c.getY() + dy);
                    }
                }
            }
            if (neighbours >= 2 && neighbours <= 3) {
                survivors.add(c);
            }
        }
        advance();
    }

    /**
     * Clear the grid of cells and set the generation to 0.
     */
    public void clear() {
    	generation = 0;

        cells.clear();
        survivors.clear();
        potentials.clear();
    }

    /**
     * Kill cell.
     * @param x x-coordinate of cell
     * @param y y-coordinate of cell
     */
    public void die(final int x, final int y) {
        cells.remove(new Cell(x, y));
    }

    /**
     * Get living cells.
     * @return Living cells.
     */
    public HashSet<Cell> getCells() {
        return cells;
    }

    /**
     * Bring cell to life.
     * @param x x-coordinate of cell
     * @param y y-coordinate of cell
     */
    public void live(final int x, final int y) {
        cells.add(new Cell(x, y));
    }

    /**
     * Get current generation.
     * @return The generation.
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Get number of living cells.
     * @return Number of living cells.
     */
    public int getLivingCells() {
        return cells.size();
    }

    /**
     * Add the cell as a neighbour. If the cell exists add one to its number of
     * neighbours. Otherwise create the cell.
     * @param x x-coordinate of cell
     * @param y y-coordinate of cell
     */
    private void addNeighbour(final int x, final int y) {
        final NewCell nc = new NewCell(x, y);

        if (potentials.contains(nc)) {
            for (final NewCell c : potentials) {
                if (c.equals(nc)) {
                    c.addNeighbour();
                    return;
                }
            }
        } else {
            potentials.add(nc);
        }
    }

    /**
     * Advance one generation, i.e. add one to the generation and make the
     * survivors and the potential cells with enough neighbours the set of
     * living cells.
     */
    private void advance() {
        generation++;

        cells.clear();
        cells.addAll(survivors);
        addPotentials();

        survivors.clear();
        potentials.clear();
    }

    /**
     * Add the potentials with enough neighbours to the living cells.
     */
    private void addPotentials() {
        for (final NewCell c : potentials) {
            if (c.getNeighbours() == 3) {
                cells.add(c);
            }
        }
    }
}
