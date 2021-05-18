package editor.actions;

import editor.location.Location;
import editor.TextEditorModel;

public class Insert implements EditAction {
    private TextEditorModel model;
    private char c;
    private Location cursorDo;

    public Insert(TextEditorModel model, char c) {
        this.model = model;
        this.c = c;
        cursorDo = model.getCursorLocation();
    }

    @Override
    public void execute_do() {
        model.setCursorLocation(cursorDo);
        model.insertChar(c);
    }

    @Override
    public void execute_undo() {
        model.setCursorLocation(cursorDo);
        new DeleteAfter(model).execute_do();
    }
}
