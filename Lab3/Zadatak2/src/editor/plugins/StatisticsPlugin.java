package editor.plugins;

import editor.clipboard.ClipboardStack;
import editor.TextEditorModel;
import editor.undoManager.UndoManager;

import javax.swing.*;
import java.util.Arrays;

public class StatisticsPlugin implements Plugin {
    @Override
    public String getName() {
        return "Statistics";
    }

    @Override
    public String getDescription() {
        return "Statistika za dokument";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        int numLines = model.getLines().size();
        int numWords = model.getLines().stream().mapToInt(s -> (int) Arrays.stream(s.split("\\s+")).count()).sum();
        int numChars = model.getLines().stream().mapToInt(s -> Arrays.stream(s.split("\\s+")).mapToInt(String::length).sum()).sum();

        String s = "Number of lines in this document is " + numLines
                + ", number of words " + numWords
                + " and number of characters is " + numChars + ".";
        JOptionPane.showMessageDialog(null, s);
    }
}
