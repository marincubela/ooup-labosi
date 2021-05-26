package graphics;

import renderer.Renderer;
import utils.GeometryUtil;
import utils.Point;
import utils.Rectangle;

import java.util.List;
import java.util.Stack;

public class Oval extends AbstractGraphicalObject {
    private final int NUMBER_OF_POINTS = 360;

    private Point center;

    public Oval(Point right, Point bottom) {
        super(new Point[]{right, bottom});

        center = new Point(bottom.getX(), right.getY());
    }

    public Oval() {
        this(new Point(10, 0), new Point(0, 10));
    }

    @Override
    public Rectangle getBoundingBox() {
        int x = 2 * getHotPoint(1).getX() - getHotPoint(0).getX();
        int y = 2 * getHotPoint(0).getY() - getHotPoint(1).getY();

        int w = Math.abs(2 * (getHotPoint(0).getX() - getHotPoint(1).getX()));
        int h = Math.abs(2 * (getHotPoint(1).getY() - getHotPoint(0).getY()));

        return new Rectangle(x, y, w, h);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return 0;
    }

    @Override
    public void render(Renderer r) {
        // horizontal axis
        double A = getBoundingBox().getWidth() * 1.f / 2;
        // vertical axis
        double B = getBoundingBox().getHeight() * 1.f / 2;;

        Point[] points = new Point[NUMBER_OF_POINTS];

        // draw the ellipse
        for (int i = 0; i < 360; i++) {
            // from parametric equation of ellipse
            double x = A * Math.cos(Math.toRadians(i));
            double y = B * Math.sin(Math.toRadians(i));
            points[i] = (new Point((int) x, (int) y)).translate(center);
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
