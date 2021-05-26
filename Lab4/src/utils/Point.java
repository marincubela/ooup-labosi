package utils;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns a new {@link Point} translated for given argument.
     *
     * @param dp translate by this {@link Point}
     * @return new {@link Point} which is translated by given argument
     */
    public Point translate(Point dp) {
        return new Point(x + dp.x, y + dp.y);
    }

    /**
     * Returns a new {@link Point} that represents difference between this and given {@link Point}
     *
     * @param p subtract by this {@link Point}
     * @return new difference between this and given {@link Point}
     */
    public Point difference(Point p) {
        return new Point(x - p.x, y - p.y);
    }
}
