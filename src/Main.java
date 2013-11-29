package life;


import java.awt.Dimension;

import javax.swing.JFrame;


public class Main {
    public static void main(final String[] args) {
        final int cellSize = 16;
        final int width = 40;
        final int height = 40;
        final int timeStep = 500;

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("Life");
        frame.setVisible(true);
        frame.getContentPane().setPreferredSize(
                new Dimension(width * cellSize, height * cellSize));
        frame.pack();

        Life life = new Life();
        View view = new View(life, cellSize, width * cellSize, height * cellSize);
        Control control = new Control(life, view, timeStep);

        frame.add(view);
        view.addKeyListener(control);
        view.addMouseListener(control);
        view.addMouseMotionListener(control);
        view.addMouseWheelListener(control);
        view.setFocusable(true);
        view.requestFocus();
        view.repaint();
        frame.validate();
    }
}
