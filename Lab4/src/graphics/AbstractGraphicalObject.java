package graphics;

import renderer.Renderer;
import utils.GeometryUtil;
import utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public abstract class AbstractGraphicalObject implements GraphicalObject {
    private Point[] hotPoints;
    private boolean[] hotPointSelected;
    private boolean selected;
    private List<GraphicalObjectListener> listeners = new ArrayList<>();

    public AbstractGraphicalObject(Point[] hotPoints) {
        this.hotPoints = hotPoints;
        hotPointSelected = new boolean[hotPoints.length];
        Arrays.fill(hotPointSelected, false);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        notifySelectionListeners();
    }

    @Override
    public int getNumberOfHotPoints() {
        return hotPoints.length;
    }

    @Override
    public Point getHotPoint(int index) {
        return hotPoints[index];
    }

    @Override
    public void setHotPoint(int index, Point point) {
        hotPoints[index] = point;
        notifyListeners();
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return hotPointSelected[index];
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        hotPointSelected[index] = selected;
        notifySelectionListeners();
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return GeometryUtil.distanceFromPoint(hotPoints[index], mousePoint);
    }

    @Override
    public void translate(Point delta) {
        for (int i = 0; i < getNumberOfHotPoints(); i++) {
            setHotPoint(i, hotPoints[i].translate(delta));
        }
        notifyListeners();
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.remove(l);
    }

    public void notifyListeners() {
        listeners.forEach(l -> l.graphicalObjectChanged(this));
    }

    public void notifySelectionListeners() {
        listeners.forEach(l -> l.graphicalObjectSelectionChanged(this));
    }
}
