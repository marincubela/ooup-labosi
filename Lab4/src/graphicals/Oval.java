package graphicals;

import utils.Point;
import utils.Rectangle;

public class Oval extends AbstractGraphicalObject {

    public Oval(Point r, Point b) {
        super(new Point[]{r, b});
    }

    public Oval() {
        this(new Point(10, 0), new Point(0, 10));
    }

    @Override
    public Rectangle getBoundingBox() {
        int x = 2 * getHotPoint(0).getX() - getHotPoint(1).getX();
        int y = 2 * getHotPoint(1).getY() - getHotPoint(0).getY();

        int w = 2 * (getHotPoint(0).getX() - getHotPoint(1).getX());
        int h = 2 * (getHotPoint(1).getY() - getHotPoint(0).getY());

        return new Rectangle(x, y, w, h);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return 0;
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public GraphicalObject duplicate() {
        Oval copy = new Oval(this.getHotPoint(0), this.getHotPoint(1));

        copy.setSelected(isSelected());
        copy.setHotPointSelected(0, isHotPointSelected(0));
        copy.setHotPointSelected(1, isHotPointSelected(1));

        return copy;
    }
}
