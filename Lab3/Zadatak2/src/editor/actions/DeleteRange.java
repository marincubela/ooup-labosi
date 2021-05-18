package editor.actions;

import editor.location.Location;
import editor.location.LocationRange;
import editor.TextEditorModel;

public class DeleteRange implements EditAction {
    private final TextEditorModel model;
    private String text;
    private LocationRange rangeRedo;
    private Location cursorUndo;

    public DeleteRange(TextEditorModel model, LocationRange range) {
        this.model = model;
        rangeRedo = range;
        cursorUndo = rangeRedo.getStart();
        if (rangeRedo != null) {
            text = model.getSelectedText();
        }
    }

    @Override
    public void execute_do() {
        if (rangeRedo == null) return;

        Location l1 = rangeRedo.getStart();
        Location l2 = rangeRedo.getEnd();
        if (l1.compareTo(l2) > 0) {
            l2 = rangeRedo.getStart();
            cursorUndo = l1 = rangeRedo.getEnd();
        }

        for (int i = l1.getY() + 1; i < l2.getY(); i++) {
            model.getLines().remove(l1.getY() + 1);
        }

        String str1 = model.getLines().get(l1.getY()).substring(0, l1.getX());
        String str2;
        if (l1.getY() == l2.getY()) {
            str2 = model.getLines().get(l2.getY()).substring(l2.getX());
        } else {
            str2 = model.getLines().get(l1.getY() + 1).substring(l2.getX());
            model.getLines().remove(l1.getY());
        }
        model.getLines().remove(l1.getY());
        model.getLines().add(l1.getY(), str1 + str2);
        model.setSelectionRange(null);
        model.setCursorLocation(l1);
        model.notifyTextObservers();
    }

    @Override
    public void execute_undo() {
        model.setCursorLocation(cursorUndo);
        new InsertText(model, text).execute_do();
    }
}
