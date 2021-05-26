package graphics;

import renderer.Renderer;
import utils.GeometryUtil;
import utils.Point;
import utils.Rectangle;

import java.util.List;
import java.util.Stack;

public class Oval extends AbstractGraphicalObject {
    private Point center;

    public Oval(Point r, Point b) {
        super(new Point[]{r, b});

        center = new Point(b.getX(), r.getY());
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
    public void render(Renderer r) {

        // horizontal axis
        double A = GeometryUtil.distanceFromPoint(center, getHotPoint(0));
        // vertical axis
        double B = GeometryUtil.distanceFromPoint(center, getHotPoint(1));

        Point[] points = new Point[360];

        // draw the ellipse
        for (int i = 0; i <= 360; i++) {
            // from parametric equation of ellipse
            double x = A * Math.sin(Math.toRadians(i));
            double y = B * Math.cos(Math.toRadians(i));
            points[i] = new Point((int) x, (int) y);

            if (i != 0) {
                // draw a line joining previous and new point .
                r.drawLine(points[i].translate(center), points[i-1].translate(center));
            }
        }

        r.fillPolygon(points);
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
