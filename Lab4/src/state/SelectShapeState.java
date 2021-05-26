package state;

import graphics.GraphicalObject;
import model.DocumentModel;
import renderer.Renderer;
import utils.Point;
import utils.Rectangle;

public class SelectShapeState implements State {
    private DocumentModel model;

    public SelectShapeState(DocumentModel model) {
        this.model = model;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject obj = model.findSelectedGraphicalObject(mousePoint);
        if (!ctrlDown) {
            model.deselectAll();
        }
        if (obj != null) {
            obj.setSelected(true);
        }
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

    }

    @Override
    public void mouseDragged(Point mousePoint) {

    }

    @Override
    public void keyPressed(int keyCode) {

    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        if(!go.isSelected()) {
            return;
        }
        Point[] points = getRectanglePoints(go.getBoundingBox());

        r.drawLine(points[0], points[points.length - 1]);
        for (int i = 1; i < points.length; i++) {
            r.drawLine(points[i], points[i - 1]);
        }
    }

    @Override
    public void afterDraw(Renderer r) {

    }

    @Override
    public void onLeaving() {
        model.deselectAll();
    }

    private Point[] getRectanglePoints(Rectangle r) {
        Point[] points = new Point[4];
        points[0] = new Point(r.getX(), r.getY());
        points[1] = new Point(r.getX() + r.getWidth(), r.getY());
        points[2] = new Point(r.getX() + r.getWidth(), r.getY() + r.getHeight());
        points[3] = new Point(r.getX(), r.getY() + r.getHeight());

        return points;
    }
}
