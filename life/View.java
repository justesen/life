package life;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


public class View extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Color BACKGROUND_COLOR = Color.white;
    private final Color CELL_COLOR = Color.black;
    private final Color MARGIN_COLOR = Color.gray;
    private final Color INFO_BACKGROUND_COLOR = Color.black;
    private final Color INFO_TEXT_COLOR = Color.white;
    private final Font INFO_FONT = new Font("Arial", Font.BOLD, 12);

    private final Life life;

    private int panelWidth;
    private int panelHeight;
    private int cellSize;
    private int margin;
    private int originX;
    private int originY;

    public View(final Life life, final int cellSize,
            final int panelWidth, final int panelHeight) {
        this.life = life;
        this.cellSize = cellSize;
        this.margin = 1;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.originX = panelWidth / 2;
        this.originY = panelHeight / 2;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void movePanel(final int dx, final int dy) {
        originX += dx;
        originY += dy;
    }

    public void paintComponent(final Graphics g) {
        // call to super classes' paintComponent should prevent flicker when
        // dragging and resizing window
        super.paintComponent(g);

        // set new panel size in case window has been resized
        panelWidth = getWidth();
        panelHeight = getHeight();

        drawBackground(g);
        drawGrid(g);
        drawCells(g);
        drawInfo(g);

        g.setColor(Color.red);
        g.fillRect(originX - 1, originY - 1, 3, 3);
    }

    public void zoomIn() {
        zoomIn(panelWidth / 2, panelHeight / 2);
    }

    public void zoomIn(final int centerX, final int centerY) {
        final int newCellSize = scaleUp(cellSize);
        final int dx = centerX - originX;
        final int dy = centerY - originY;

        // if newCellSize is the same as before we will never zoom in, therefore
        // we add 1 to it
        // e.g. cellSize = 1 then newCellSize = 1 * 3 / 2 = 1,
        if (newCellSize == cellSize) {
            cellSize++;
        } else {
            cellSize = newCellSize;
            originX = centerX - scaleUp(dx);
            originY = centerY - scaleUp(dy);
        }
        // show margins if we're not zoomed out too much
        if (cellSize > 2) {
            margin = 1;
        }
    }

    public void zoomOut() {
        zoomOut(panelWidth / 2, panelHeight / 2);
    }

    public void zoomOut(final int centerX, final int centerY) {
        final int newCellSize = scaleDown(cellSize);
        final int dx = centerX - originX;
        final int dy = centerY - originY;

        // ensure a cellSize > 0
        if (newCellSize > 0) {
            cellSize = newCellSize;
            originX = centerX - scaleDown(dx);
            originY = centerY - scaleDown(dy);

            // don't show margins if we're zoomed out too much
            if (cellSize < 3) {
                margin = 0;
            }
        }
    }

    private void drawBackground(final Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 20, panelWidth, panelHeight);
    }

    private void drawCells(final Graphics g) {
        g.setColor(CELL_COLOR);

        for (final Cell c : life.getCells()){
            g.fillRect(originX + c.getX() * cellSize + margin,
                       originY + c.getY() * cellSize + margin,
                       cellSize - margin,
                       cellSize - margin);
        }
    }

    private void drawInfo(Graphics g) {
        String generation = "GENERATION " + life.getGeneration();
        String liveCells = "LIVING CELLS " + life.getLivingCells();
        String mode = "";

        if (Control.isPaused() && Control.isDrawMode()) {
            mode = "PAUSED / DRAW MODE";
        } else if (Control.isPaused()) {
            mode = "PAUSED";
        } else if (Control.isDrawMode()) {
            mode = "DRAW MODE";
        }
        g.setColor(INFO_BACKGROUND_COLOR);
        g.fillRect(0, 0, panelWidth, 20);

        g.setColor(INFO_TEXT_COLOR);
        g.setFont(INFO_FONT);
        g.drawString(generation, 3, 12);
        g.drawString(liveCells,
                     panelWidth - 3 - g.getFontMetrics(INFO_FONT).stringWidth(liveCells),
                     12);
        g.drawString(mode,
                    (panelWidth - g.getFontMetrics(INFO_FONT).stringWidth(mode)) / 2,
                    12);
    }

    private void drawGrid(Graphics g) {
        // we need to compute an offset in case origin is not at a position,
        // which is a multiple of cellSize
        int offsetX = originX % cellSize;
        int offsetY = originY % cellSize;

        g.setColor(MARGIN_COLOR);

        for (int x = offsetX; x < panelWidth; x += cellSize) {
            g.fillRect(x, 0, margin, panelHeight);
        }
        for (int y = offsetY; y < panelHeight; y += cellSize) {
            g.fillRect(0, y, panelWidth, margin);
        }
    }

    private int scaleDown(final int n) {
        return n * 3 / 4;
    }

    private int scaleUp(final int n) {
        return n * 4 / 3;
    }
}
