package editor.location;

import java.util.Objects;

public class Location implements Comparable<Location> {
    private int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location l) {
        this.x = l.x;
        this.y = l.y;
    }

    public Location() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return getX() == location.getX() && getY() == location.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    public int compareTo(Location l2) {
        if (this.y == l2.y) {
            return Integer.compare(this.x, l2.getX());
        } else {
            return Integer.compare(this.y, l2.getY());
        }
    }
}
