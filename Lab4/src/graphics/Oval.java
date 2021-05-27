package graphics;

import renderer.Renderer;
import utils.GeometryUtil;
import utils.Point;
import utils.Rectangle;

import java.util.Arrays;
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
        Point right = getHotPoint(0);
        Point bottom = getHotPoint(1);

        int x = right.getX() > bottom.getX() ? 2 * bottom.getX() - right.getX() : right.getX();
        int y = bottom.getY() > right.getY() ? 2 * right.getY() - bottom.getY() : bottom.getY();

        int w = Math.abs(2 * (right.getX() - bottom.getX()));
        int h = Math.abs(2 * (bottom.getY() - right.getY()));

        return new Rectangle(x, y, w, h);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        // horizontal axis
        double A = getBoundingBox().getWidth() * 1.f / 2;
        // vertical axis
        double B = getBoundingBox().getHeight() * 1.f / 2;

        double x = mousePoint.getX() - center.getX();
        double y = mousePoint.getY() - center.getY();
        double eq = (x * x) / (A * A) + (y * y) / (B * B);

        if (eq <= 1) {
            return 0;
        }
        Point[] points = getPoints();

        return Arrays.stream(points).mapToDouble((point) -> GeometryUtil.distanceFromPoint(point, mousePoint)).min().getAsDouble();
    }

    @Override
    public void render(Renderer r) {
        Point[] points = getPoints();
        r.fillPolygon(points);
    }

    private Point[] getPoints() {
        // horizontal axis
        double A = getBoundingBox().getWidth() * 1.f / 2;
        // vertical axis
        double B = getBoundingBox().getHeight() * 1.f / 2;

        Point[] points = new Point[NUMBER_OF_POINTS];

        // draw the ellipse
        for (int i = 0; i < 360; i++) {
            // from parametric equation of ellipse
            double x = A * Math.cos(Math.toRadians(i));
            double y = B * Math.sin(Math.toRadians(i));
            points[i] = (new Point((int) x, (int) y)).translate(getCenter());
        }
        return points;
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
        return "@OVAL";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] lines = data.split(" ");
        Point s = new Point(Integer.parseInt(lines[1]), Integer.parseInt(lines[2]));
        Point e = new Point(Integer.parseInt(lines[3]), Integer.parseInt(lines[4]));
        Oval oval = new Oval(s, e);
        stack.push(oval);
    }

    @Override
    public void save(List<String> rows) {
        String line = getShapeID();
        line += " " + getHotPoint(0).getX();
        line += " " + getHotPoint(0).getY();
        line += " " + getHotPoint(1).getX();
        line += " " + getHotPoint(1).getY();
        rows.add(line);
    }

    public Point getCenter() {
        return center;
    }

    private void setCenter() {
        center = new Point(getHotPoint(1).getX(), getHotPoint(0).getY());
    }

    @Override
    public void setHotPoint(int index, Point point) {
        super.setHotPoint(index, point);
        setCenter();
    }
}
