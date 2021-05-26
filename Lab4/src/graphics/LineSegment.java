package graphics;

import renderer.Renderer;
import utils.GeometryUtil;
import utils.Point;
import utils.Rectangle;

import java.util.List;
import java.util.Stack;

public class LineSegment extends AbstractGraphicalObject {

    public LineSegment(Point s, Point e) {
        super(new Point[]{s, e});
    }

    public LineSegment() {
        this(new Point(0, 0), new Point(10, 0));
    }

    @Override
    public Rectangle getBoundingBox() {
        int x = Math.min(getHotPoint(0).getX(), getHotPoint(1).getX());
        int y = Math.min(getHotPoint(0).getY(), getHotPoint(1).getY());

        int w = Math.abs(getHotPoint(0).getX() - getHotPoint(1).getX());
        int h = Math.abs(getHotPoint(0).getY() - getHotPoint(1).getY());

        return new Rectangle(x, y, w, h);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return GeometryUtil.distanceFromLineSegment(getHotPoint(0), getHotPoint(1), mousePoint);
    }

    @Override
    public void render(Renderer r) {
        r.drawLine(getHotPoint(0), getHotPoint(1));
    }

    @Override
    public String getShapeName() {
        return "Linija";
    }

    @Override
    public GraphicalObject duplicate() {
        LineSegment copy = new LineSegment(this.getHotPoint(0), this.getHotPoint(1));

        copy.setSelected(isSelected());
        copy.setHotPointSelected(0, isHotPointSelected(0));
        copy.setHotPointSelected(1, isHotPointSelected(1));

        return copy;
    }

    @Override
    public String getShapeID() {
        // TODO later
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(List<String> rows) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
