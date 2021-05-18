package editor;

import editor.location.LocationRange;

@FunctionalInterface
public interface SelectionRangeObservers {
    void updateSelectionRange(LocationRange selectionRange);
}
