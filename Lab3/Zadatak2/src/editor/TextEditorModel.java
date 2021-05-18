package editor;

import editor.actions.*;
import editor.location.Location;
import editor.location.LocationRange;
import editor.undoManager.UndoManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TextEditorModel {
    private List<String> lines;
    private LocationRange selectionRange;
    private Location cursorLocation;

    private List<CursorObserver> cursorObservers;
    private List<TextObserver> textObservers;
    private List<SelectionRangeObservers> selectionObservers;

    public TextEditorModel(String text) {
        this.lines = new ArrayList<>();
        this.cursorObservers = new ArrayList<>();
        this.textObservers = new ArrayList<>();
        this.selectionObservers = new ArrayList<>();
        this.cursorLocation = new Location();
        this.selectionRange = null;
        lines.addAll(Arrays.asList(text.split("\n")));
    }

    public List<String> getLines() {
        return lines;
    }

    public Iterator<String> allLines() {
        return lines.iterator();
    }

    public String getCurrentLine() {
        return lines.get(cursorLocation.getY());
    }

    public Iterator<String> linesRange(int index1, int index2) {
        return lines.subList(index1, index2).iterator();
    }

    public Location getCursorLocation() {
        return cursorLocation;
    }

    public void setCursorLocation(Location cursorLocation) {
        if (!getCursorLocation().equals(cursorLocation)) {
            this.cursorLocation = cursorLocation;
            notifyCursorObservers();
        }
    }

    public void setCursorLocation(int x, int y) {
        this.setCursorLocation(new Location(x, y));
    }

    public boolean addCursorObserver(CursorObserver o) {
        return this.cursorObservers.add(o);
    }

    public boolean removeCursorObserver(CursorObserver o) {
        return this.cursorObservers.remove(o);
    }

    private void notifyCursorObservers() {
        cursorObservers.forEach(c -> c.updateCursorLocation(cursorLocation));
    }

    public boolean addSelectionObserver(SelectionRangeObservers o) {
        return this.selectionObservers.add(o);
    }

    public boolean removeSelectionObserver(SelectionRangeObservers o) {
        return this.selectionObservers.remove(o);
    }

    private void notifySelectionObservers(LocationRange selectionRange) {
        selectionObservers.forEach(r -> r.updateSelectionRange(selectionRange));
    }


    public void moveCursorLeft() {
        setCursorLocation(cursorLeft());
    }

    public Location cursorLeft() {
        int x = cursorLocation.getX();
        int y = cursorLocation.getY();
        if (x > 0) {
            return new Location(x - 1, y);
        } else if (y > 0) {
            return new Location(lines.get(y - 1).length(), y - 1);
        }
        return getCursorLocation();
    }

    public void moveCursorRight() {
        setCursorLocation(cursorRight());
    }

    public Location cursorRight() {
        int x = cursorLocation.getX();
        int y = cursorLocation.getY();

        if (x < lines.get(y).length()) {
            return new Location(x + 1, y);
        } else if (y < lines.size() - 1) {
            return new Location(0, y + 1);
        }

        return getCursorLocation();
    }

    public void moveCursorUp() {
        setCursorLocation(cursorUp());
    }

    public Location cursorUp() {
        int x = cursorLocation.getX();
        int y = cursorLocation.getY();
        if (y > 0) {
            if (x > lines.get(y - 1).length()) {
                return new Location(lines.get(y - 1).length(), y - 1);
            } else {
                return new Location(x, y - 1);
            }
        } else if (x > 0) {
            return new Location(0, 0);
        }
        return getCursorLocation();
    }

    public void moveCursorDown() {
        setCursorLocation(cursorDown());
    }

    public Location cursorDown() {
        int x = cursorLocation.getX();
        int y = cursorLocation.getY();
        if (y < lines.size() - 1) {
            if (x > lines.get(y + 1).length()) {
                return new Location(lines.get(y + 1).length(), y + 1);
            } else {
                return new Location(x, y + 1);
            }
        } else if (x < lines.get(y).length()) {
            return new Location(lines.get(y).length(), y);
        }
        return getCursorLocation();
    }

    public boolean addTextObserver(TextObserver o) {
        return this.textObservers.add(o);
    }

    public boolean removeTextObserver(TextObserver o) {
        return this.textObservers.remove(o);
    }

    public void notifyTextObservers() {
        textObservers.forEach(TextObserver::updateText);
    }

    public void deleteBefore() {
        int x = cursorLocation.getX();
        int y = cursorLocation.getY();

        if (x > 0 || y > 0) {
            EditAction c = new DeleteBefore(this);
            c.execute_do();
            UndoManager.getInstance().push(c);
            notifyTextObservers();
        }
    }

    public void deleteAfter() {
        int x = cursorLocation.getX();
        int y = cursorLocation.getY();
        String str = this.getCurrentLine();

        if (x < str.length() || y < lines.size() - 1) {
            EditAction c = new DeleteAfter(this);
            c.execute_do();
            UndoManager.getInstance().push(c);
            notifyTextObservers();
        }
    }

    public void deleteRange(LocationRange r) {
        if (r == null) return;

        EditAction c = new DeleteRange(this, selectionRange);
        c.execute_do();
        UndoManager.getInstance().push(c);
        notifyTextObservers();
    }

    public LocationRange getSelectionRange() {
        return selectionRange;
    }

    public void setSelectionRange(LocationRange selectionRange) {
        this.selectionRange = selectionRange;
        notifySelectionObservers(selectionRange);
    }

    public void insertChar(char c) {
        deleteRange(getSelectionRange());
        int x = cursorLocation.getX();
        int y = cursorLocation.getY();
        String line = lines.get(y);
        if (c == '\n') {
            String s1 = line.substring(0, x);
            String s2 = line.substring(x);

            lines.remove(y);
            lines.add(y, s2);
            lines.add(y, s1);
            setCursorLocation(0, y + 1);
        } else {
            String newLine = line.substring(0, x) + c + line.substring(x);
            lines.remove(y);
            lines.add(y, newLine);
            setCursorLocation(x + 1, y);
        }
        notifyTextObservers();
    }

    public void insert(char c) {
        EditAction e = new Insert(this, c);
        e.execute_do();
        UndoManager.getInstance().push(e);
        notifyTextObservers();
    }

    public void insert(String text) {
        EditAction c = new InsertText(this, text);
        c.execute_do();
        UndoManager.getInstance().push(c);
        notifyTextObservers();
    }

    public String getSelectedText() {
        String res = "\n";
        if (selectionRange == null) return res;

        Location l1 = selectionRange.getStart();
        Location l2 = selectionRange.getEnd();
        if (l1.compareTo(l2) > 0) {
            l2 = selectionRange.getStart();
            l1 = selectionRange.getEnd();
        }

        for (int i = l1.getY() + 1; i < l2.getY(); i++) {

            res += lines.get(i) + "\n";
        }

        String str1 = "";
        String str2 = "";
        if (l1.getY() == l2.getY()) {
            return lines.get(l1.getY()).substring(l1.getX(), l2.getX());
        } else {
            str1 = lines.get(l1.getY()).substring(l1.getX());
            str2 = lines.get(l2.getY()).substring(0, l2.getX());
        }

        return str1 + res + str2;
    }

    public void cursorToStart() {
        this.setCursorLocation(0, 0);
    }

    public void cursorToEnd() {
        int y = lines.size() - 1;
        int x = lines.get(y).length();
        this.setCursorLocation(x, y);
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
        notifyTextObservers();
    }
}
