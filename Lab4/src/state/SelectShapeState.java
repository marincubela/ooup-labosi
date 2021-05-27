package state;

import graphics.AbstractGraphicalObject;
import graphics.CompositeShape;
import graphics.GraphicalObject;
import model.DocumentModel;
import renderer.Renderer;
import utils.Point;
import utils.Rectangle;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

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
            if (model.getSelectedObjects().size() == 1) {
                int ind = model.findSelectedHotPoint(obj, mousePoint);
                if (ind != -1) {
                    ((AbstractGraphicalObject) obj).deselectHotPoints();
                    obj.setHotPointSelected(ind, true);
                }
            }
        }
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (model.getSelectedObjects().size() == 1) {
            GraphicalObject obj = model.getSelectedObjects().get(0);
            if (obj instanceof AbstractGraphicalObject) {
                ((AbstractGraphicalObject) model.getSelectedObjects().get(0)).deselectHotPoints();
            }
        }
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (model.getSelectedObjects().size() != 1) {
            return;
        }

        GraphicalObject obj = model.getSelectedObjects().get(0);

        for (int i = 0; i < obj.getNumberOfHotPoints(); i++) {
            if (obj.isHotPointSelected(i)) {
                obj.setHotPoint(i, mousePoint);
            }
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_RIGHT -> model.getSelectedObjects().forEach(o -> o.translate(new Point(1, 0)));
            case KeyEvent.VK_UP -> model.getSelectedObjects().forEach(o -> o.translate(new Point(0, -1)));
            case KeyEvent.VK_DOWN -> model.getSelectedObjects().forEach(o -> o.translate(new Point(0, 1)));
            case KeyEvent.VK_LEFT -> model.getSelectedObjects().forEach(o -> o.translate(new Point(-1, 0)));
            case KeyEvent.VK_PLUS -> model.getSelectedObjects().forEach(o -> model.increaseZ(o));
            case KeyEvent.VK_MINUS -> model.getSelectedObjects().forEach(o -> model.decreaseZ(o));
            case KeyEvent.VK_G -> groupSelected();
            case KeyEvent.VK_U -> ungroupSelected();
        }
    }

    private void groupSelected() {
        List<GraphicalObject> selected = model.getSelectedObjects().stream().map(c -> c.duplicate()).collect(Collectors.toList());
        CompositeShape shape = new CompositeShape(selected);
        shape.setSelected(true);
        model.removeSelected();
        model.addGraphicalObject(shape);
    }

    private void ungroupSelected() {
        if (model.getSelectedObjects().size() == 1) {
            GraphicalObject object = model.getSelectedObjects().get(0);
            if (object instanceof CompositeShape) {
                model.removeGraphicalObject(object);
                for (GraphicalObject o : ((CompositeShape) object).getChildren()) {
                    o.setSelected(true);
                    model.addGraphicalObject(o);
                }
            }
        }
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        if (!go.isSelected()) {
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
        if (model.getSelectedObjects().size() == 1) {
            GraphicalObject obj = model.getSelectedObjects().get(0);
            for (int i = 0; i < obj.getNumberOfHotPoints(); i++) {
                drawHotBox(r, obj.getHotPoint(i));
            }
        }
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

    private void drawHotBox(Renderer r, Point p) {
        int sel = (int) DocumentModel.SELECTION_PROXIMITY;
        Rectangle rect = new Rectangle(p.getX() - sel / 2, p.getY() - sel / 2, sel, sel);
        Point[] points = getRectanglePoints(rect);

        r.drawLine(points[0], points[points.length - 1]);
        for (int i = 1; i < points.length; i++) {
            r.drawLine(points[i], points[i - 1]);
        }
    }
}
