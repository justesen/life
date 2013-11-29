package life;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;


public class Control extends MouseInputAdapter
        implements ActionListener, KeyListener {
    private static boolean drawMode = true;
    private static boolean paused = true;

    private final Life life;
    private final Timer timer;
    private final View view;

    private boolean leftMousePressed;
    private boolean rightMousePressed;
    private int prevMouseX;
    private int prevMouseY;

    public Control(final Life life, final View view, final int timeStep) {
        this.life = life;
        this.timer = new Timer(timeStep, this);
        this.view = view;
        this.leftMousePressed = false;
        this.rightMousePressed = false;
    }

    public static boolean isDrawMode() {
		return drawMode;
	}

	public static boolean isPaused() {
		return paused;
	}

    public void actionPerformed(final ActionEvent e) {
        life.update();
        view.repaint();
    }

    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_PAUSE:
            case KeyEvent.VK_P:
                if (paused) {
                    paused = false;
                    timer.start();
                } else {
                    paused = true;
                    timer.stop();
                }
                break;
            case KeyEvent.VK_SPACE:
                drawMode = (drawMode ? false : true);
                break;
            case KeyEvent.VK_DELETE:
                if (drawMode) {
                    life.clear();
                }
                break;
            case KeyEvent.VK_PLUS:
            case KeyEvent.VK_ADD:
                if (e.isControlDown()) {
                    if (!paused) {
                        speedUp();
                    }
                } else {
                    view.zoomIn();
                }
                break;
            case KeyEvent.VK_MINUS:
            case KeyEvent.VK_SUBTRACT:
                if (e.isControlDown()) {
                    if (!paused) {
                        speedDown();
                    }
                } else {
                    view.zoomOut();
                }
                break;
        }
        view.repaint();
    }

    public void keyReleased(final KeyEvent e) {}
    public void keyTyped(final KeyEvent e) {}

    public void mouseDragged(final MouseEvent e) {
    	if (drawMode) {
	        if (leftMousePressed) {
	            life.live(getX(e), getY(e));
	        } else if (rightMousePressed) {
	            life.die(getX(e), getY(e));
	        }
    	} else if (leftMousePressed) {
    		final int currentMouseX = e.getX();
    		final int currentMouseY = e.getY();

    		view.movePanel(currentMouseX - prevMouseX,
    					   currentMouseY - prevMouseY);

    		prevMouseX = currentMouseX;
    		prevMouseY = currentMouseY;
    	}
        view.repaint();
    }

    public void mouseMoved(final MouseEvent e) {
    	prevMouseX = e.getX();
    	prevMouseY = e.getY();
    }

    public void mousePressed(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftMousePressed = true;

            if (drawMode) {
            	life.live(getX(e), getY(e));
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightMousePressed = true;

            if (drawMode) {
            	life.die(getX(e), getY(e));
            }
        }
        view.repaint();
    }

    public void mouseReleased(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftMousePressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightMousePressed = false;
        }
    }

    public void mouseWheelMoved(final MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            view.zoomIn(e.getX(), e.getY());
        } else if (e.getWheelRotation() > 0) {
            view.zoomOut(e.getX(), e.getY());
        }
        view.repaint();
    }

    private int getX(final MouseEvent e) {
    	// ensure round down (also with negative coordinates)
    	return (int) Math.floor((double) (e.getX() - view.getOriginX())
    			/ view.getCellSize());
    }

	private int getY(final MouseEvent e) {
    	// ensure round down (also with negative coordinates)
    	return (int) Math.floor((double) (e.getY() - view.getOriginY())
    			/ view.getCellSize());
    }

    private void speedDown() {
        timer.setDelay(timer.getDelay() + 50);
    }

	private void speedUp() {
	    timer.setDelay(timer.getDelay() - 50);
	}
}
