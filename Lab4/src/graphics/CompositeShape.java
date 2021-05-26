package graphics;

import renderer.Renderer;
import utils.Point;
import utils.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class CompositeShape implements GraphicalObject {
    private boolean selected = false;
    private List<GraphicalObjectListener> listeners = new ArrayList<>();
    private List<GraphicalObject> children;

    public CompositeShape(List<GraphicalObject> children) {
        this.children = children;
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
        return 0;
    }

    @Override
    public Point getHotPoint(int index) {
        return null;
    }

    @Override
    public void setHotPoint(int index, Point point) {
        // DO nothing
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return false;
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        // Do nothing
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return 0;
    }

    @Override
    public void translate(Point delta) {
        children.forEach(c -> c.translate(delta));
        notifyListeners();
    }

    @Override
    public Rectangle getBoundingBox() {
        if(children.isEmpty()) {
            throw new IllegalStateException();
        }

        List<Rectangle> list = children.stream().map(GraphicalObject::getBoundingBox).collect(Collectors.toList());

        int x = list.stream().mapToInt(Rectangle::getX).min().getAsInt();
        int y = list.stream().mapToInt(Rectangle::getY).min().getAsInt();

        int x2 = list.stream().mapToInt(r -> r.getX() + r.getWidth()).max().getAsInt();
        int y2 = list.stream().mapToInt(r -> r.getY() + r.getHeight()).max().getAsInt();


        return new Rectangle(x, y, Math.abs(x2-x), Math.abs(y2-y));
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return 123443211;
    }

    @Override
    public void render(Renderer r) {
        children.forEach(c -> c.render(r));
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.remove(l);
    }

    @Override
    public String getShapeName() {
        return null;
    }

    @Override
    public GraphicalObject duplicate() {
        List<GraphicalObject> copyList = children.stream().map(GraphicalObject::duplicate).collect(Collectors.toList());
        CompositeShape copy = new CompositeShape(copyList);
        copy.selected = selected;

        return copy;
    }

    @Override
    public String getShapeID() {
        return null;
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {

    }

    @Override
    public void save(List<String> rows) {

    }

    public List<GraphicalObject> getChildren() {
        return children;
    }

    public void notifyListeners() {
        listeners.forEach(l -> l.graphicalObjectChanged(this));
    }

    public void notifySelectionListeners() {
        listeners.forEach(l -> l.graphicalObjectSelectionChanged(this));
    }
}
