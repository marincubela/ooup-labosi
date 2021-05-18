package editor.plugins;

import editor.clipboard.ClipboardStack;
import editor.TextEditorModel;
import editor.undoManager.UndoManager;

public interface Plugin {
    public String getName(); // ime plugina (za izbornicku stavku)

    public String getDescription(); // kratki opis

    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack);
}
