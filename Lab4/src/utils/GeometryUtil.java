package utils;

public class GeometryUtil {
    /**
     * Calculate Euclid distance between two points.
     *
     * @param point1 first point
     * @param point2 second point
     * @return distance between two given points
     */
    public static double distanceFromPoint(Point point1, Point point2) {
        double x = point1.getX() - point2.getX();
        double y = point1.getY() - point2.getY();
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate distance from point P to line defined by point S and E.
     *
     * @param s start point of line segment
     * @param e end point of line segment
     * @param p point from which distance to line is returned
     * @return distance from point P to line defined by point S and E
     */
    public static double distanceFromLineSegment(Point s, Point e, Point p) {
        double xes = e.getX() - s.getX();
        double yes = e.getY() - s.getY();
        double xsp = s.getX() - p.getX();
        double ysp = s.getY() - p.getY();

        double nom = Math.abs(xes * ysp - yes * xsp);
        double denom = Math.sqrt(xes * xes + yes * yes);

        double d = nom / denom;
        double ds = distanceFromPoint(s, p);
        double de = distanceFromPoint(e, p);

        return Math.min(Math.min(ds, de), d);
    }
}
