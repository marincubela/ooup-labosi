package editor.actions;

import editor.location.Location;
import editor.location.LocationRange;
import editor.TextEditorModel;

public class InsertText implements EditAction{
    private final TextEditorModel model;
    private final Location cursor;
    private LocationRange range;
    private final String text;

    public InsertText(TextEditorModel model, String text) {
        this.model = model;
        this.cursor = model.getCursorLocation();
        this.text = text;
    }

    @Override
    public void execute_do() {
        model.setCursorLocation(cursor);
        for (char c : text.toCharArray()) {
            model.insertChar(c);
        }
        range = new LocationRange(cursor, model.getCursorLocation());
        model.notifyTextObservers();
    }

    @Override
    public void execute_undo() {
        model.setSelectionRange(range);
        new DeleteRange(model, range).execute_do();
    }
}
