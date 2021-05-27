package state;

import graphics.GraphicalObject;
import renderer.Renderer;
import utils.Point;

public interface State {
    /**
     * Called when left mouse button is pressed
     *
     * @param mousePoint point at which was button pressed
     * @param shiftDown  {@code true} if ctrl was down during click, else {@code false}
     * @param ctrlDown   {@code true} if shift was down during click, else {@code false}
     */
    void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown);

    /**
     * Called when left mouse button is released
     *
     * @param mousePoint point at which was button pressed
     * @param shiftDown  {@code true} if ctrl was down during click, else {@code false}
     * @param ctrlDown   {@code true} if shift was down during click, else {@code false}
     */
    void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown);

    /**
     * Called when user drags mouse while key is pressed
     *
     * @param mousePoint point at which was button pressed
     */
    void mouseDragged(Point mousePoint);

    /**
     * Called when key press was registered
     *
     * @param keyCode pressed key
     */
    void keyPressed(int keyCode);

    /**
     * Called after canvas drew graphical object given as argument
     *
     * @param r  renderer responsible for drawing objects
     * @param go object to be drawn
     */
    void afterDraw(Renderer r, GraphicalObject go);

    /**
     * Called after canvas drew any object
     *
     * @param r renderer responsible for drawing objects
     */
    void afterDraw(Renderer r);

    /**
     * Called when program leaves this state
     */
    void onLeaving();
}