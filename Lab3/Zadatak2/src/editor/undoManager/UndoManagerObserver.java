package editor.undoManager;

public interface UndoManagerObserver {
    void updateUndoStack(boolean isEmpty);

    void updateRedoStack(boolean isEmpty);
}
