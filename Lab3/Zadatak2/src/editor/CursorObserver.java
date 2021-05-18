package editor;

import editor.location.Location;

@FunctionalInterface
public interface CursorObserver {
    void updateCursorLocation(Location loc);
}
