package editor.actions;

import editor.location.Location;
import editor.TextEditorModel;

public class DeleteBefore implements EditAction {
    private final TextEditorModel model;
    private char c;
    private Location cursorDo;
    private Location cursorUndo;

    public DeleteBefore(TextEditorModel model) {
        this.model = model;
        cursorDo = model.getCursorLocation();
    }

    @Override
    public void execute_do() {
        int x = cursorDo.getX();
        int y = cursorDo.getY();
        model.setCursorLocation(cursorDo);
        // Resetiraj da nema selekcije
        model.setSelectionRange(null);
        String str = this.model.getCurrentLine();

        if (x > 0) {
            String newStr = str.substring(0, x - 1) + str.substring(x);
            c = str.charAt(x - 1);
            model.getLines().remove(y);
            model.getLines().add(y, newStr);
            model.setCursorLocation(x - 1, y);
            cursorUndo = new Location(model.getCursorLocation());
        } else if (y > 0) {
            String str2 = model.getLines().get(model.getCursorLocation().getY() - 1);
            String newStr = str2 + str;
            c = '\n';
            model.getLines().remove(y);
            model.getLines().remove(y - 1);
            model.getLines().add(y - 1, newStr);
            model.setCursorLocation(new Location(str2.length(), y - 1));
            cursorUndo = new Location(model.getCursorLocation());
        }
    }

    @Override
    public void execute_undo() {
        model.setCursorLocation(cursorUndo);
        // TODO A ovo nece moci vako
        model.insertChar(c);
    }
}
