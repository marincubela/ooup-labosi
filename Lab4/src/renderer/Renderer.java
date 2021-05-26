package renderer;

import utils.Point;

public interface Renderer {
    /**
     * Draw line from {@link Point} s to {@link Point} e using this renderer.
     *
     * @param s start point of line segment
     * @param e end point of line segment
     */
    void drawLine(Point s, Point e);

    /**
     * Fill polygon bounded by given points using this renderer.
     *
     * @param points vertices of polygon
     */
    void fillPolygon(Point[] points);
}