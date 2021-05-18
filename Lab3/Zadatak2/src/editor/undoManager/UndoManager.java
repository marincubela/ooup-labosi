package editor.undoManager;

import editor.actions.EditAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UndoManager {
    private static final UndoManager undoManager = new UndoManager();
    private Stack<EditAction> undoStack;
    private Stack<EditAction> redoStack;
    private List<UndoManagerObserver> observers;

    private UndoManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.observers = new ArrayList<>();
    }

    public static UndoManager getInstance() {
        return undoManager;
    }

    public void undo() {
        boolean isRedo = redoStack.isEmpty();
        boolean isUndo = undoStack.isEmpty();
        if (!isUndo) {
            EditAction c = undoStack.pop();
            redoStack.push(c);
            c.execute_undo();

            if(undoStack.isEmpty()) {
                notifyUndoObservers(true);
            }

            if(isRedo) {
                notifyRedoObservers(false);
            }
        }
    }

    public void redo() {
        boolean isRedo = redoStack.isEmpty();
        boolean isUndo = undoStack.isEmpty();
        if (!isRedo) {
            EditAction c = redoStack.pop();
            undoStack.push(c);
            c.execute_do();

            if(redoStack.isEmpty()) {
                notifyRedoObservers(true);
            }

            if(isUndo) {
                notifyUndoObservers(false);
            }
        }
    }

    public void push(EditAction c) {
        boolean isRedo = redoStack.isEmpty();
        boolean isUndo = undoStack.isEmpty();
        redoStack.clear();
        undoStack.push(c);
        if(isUndo) {
            notifyUndoObservers(false);
        }
        if(!isRedo) {
            notifyRedoObservers(true);
        }

    }

    public boolean addUndoManagerObserver(UndoManagerObserver o) {
        return this.observers.add(o);
    }

    public boolean removeUndoManagerObserver(UndoManagerObserver o) {
        return this.observers.remove(o);
    }

    private void notifyUndoObservers(boolean isEmpty) {
        observers.forEach(o -> o.updateUndoStack(isEmpty));
    }

    private void notifyRedoObservers(boolean isEmpty) {
        observers.forEach(o -> o.updateRedoStack(isEmpty));
    }

}
