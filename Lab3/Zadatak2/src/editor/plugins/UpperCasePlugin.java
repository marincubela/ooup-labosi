package editor.plugins;

import editor.clipboard.ClipboardStack;
import editor.TextEditorModel;
import editor.undoManager.UndoManager;

import java.util.ArrayList;
import java.util.List;

public class UpperCasePlugin implements Plugin {
    @Override
    public String getName() {
        return "To first upper case";
    }

    @Override
    public String getDescription() {
        return "Change all letters to first upper case";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        List<String> lines = new ArrayList<>();

        model.getLines().forEach(l -> {
            lines.add(toFirstUpper(l));
        });

        model.setLines(lines);
        model.notifyTextObservers();
    }

    private String toFirstUpper(String s) {
        String res = "";

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(i == 0 || Character.isWhitespace(s.charAt(i - 1))) {
                c = Character.toUpperCase(c);
            }
            res += c;
        }

        return  res;
    }
}
