package renderer;

import utils.Point;

import java.awt.*;
import java.util.function.Function;

public class G2DRendererImpl implements Renderer {
    private Graphics2D g2d;

    public G2DRendererImpl(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void drawLine(Point s, Point e) {
        g2d.setColor(Color.blue);
        g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
    }

    @Override
    public void fillPolygon(Point[] points) {
        g2d.setColor(Color.blue);
        g2d.fillPolygon(getPointsValues(points, Point::getX), getPointsValues(points, Point::getY), points.length);

        g2d.setColor(Color.red);

        g2d.drawLine(points[0].getX(), points[0].getY(), points[points.length - 1].getX(), points[points.length - 1].getY());
        for (int i = 1; i < points.length; i++) {
            g2d.drawLine(points[i].getX(), points[i].getY(), points[i - 1].getX(), points[i - 1].getY());
        }
    }

    private static int[] getPointsValues(Point[] points, Function<Point, Integer> c) {
        int[] arr = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            arr[i] = c.apply(points[i]);
        }

        return arr;
    }
}