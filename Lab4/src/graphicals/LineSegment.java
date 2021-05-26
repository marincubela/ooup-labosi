package graphicals;

import utils.GeometryUtil;
import utils.Point;
import utils.Rectangle;

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
}
