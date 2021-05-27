package state;

import graphics.GraphicalObject;
import renderer.Renderer;
import utils.Point;

public class IdleState implements State {
    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        // Do nothing
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        // Do nothing
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        // Do nothing
    }

    @Override
    public void keyPressed(int keyCode) {
        // Do nothing
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        // Do nothing
    }

    @Override
    public void afterDraw(Renderer r) {
        // Do nothing
    }

    @Override
    public void onLeaving() {
        // Do nothing
    }
}
