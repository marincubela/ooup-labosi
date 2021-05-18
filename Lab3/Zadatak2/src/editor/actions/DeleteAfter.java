package editor.actions;

import editor.location.Location;
import editor.TextEditorModel;

public class DeleteAfter implements EditAction {
    private final TextEditorModel model;
    private char c;
    private Location cursor;

    public DeleteAfter(TextEditorModel model) {
        this.model = model;
        cursor = model.getCursorLocation();
    }

    @Override
    public void execute_do() {
        int x = cursor.getX();
        int y = cursor.getY();
        model.setCursorLocation(cursor);
        // Resetiraj da nema selekcije
        model.setSelectionRange(null);
        String str = model.getCurrentLine();

        if (x < str.length()) {
            String newStr = str.substring(0, x) + str.substring(x + 1);
            c = str.charAt(x);
            model.getLines().remove(y);
            model.getLines().add(y, newStr);
            model.notifyTextObservers();
        } else if (y < model.getLines().size() - 1) {
            String str2 = model.getLines().get(model.getCursorLocation().getY());
            String newStr = str + str2;
            c = '\n';
            model.getLines().remove(y);
            model.getLines().remove(y);
            model.getLines().add(y, newStr);
            model.notifyTextObservers();
        }
    }

    @Override
    public void execute_undo() {
        model.setCursorLocation(cursor);
        model.insertChar(c);
        model.moveCursorLeft();
    }
}
