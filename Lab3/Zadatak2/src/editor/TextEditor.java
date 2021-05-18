package editor;

import editor.clipboard.ClipboardStack;
import editor.location.Location;
import editor.location.LocationRange;
import editor.undoManager.UndoManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class TextEditor extends JComponent /*implements KeyListener */ {
    private final Color textColor = Color.BLACK;
    private final Color backgroundColor = Color.LIGHT_GRAY;

    private final TextEditorModel model;
    private ClipboardStack stack;
    private int spacing = 0;
    private int leftMargin = 20;
    private int topMargin = 20;

    public TextEditor(TextEditorModel model) {
        this.model = model;
        this.stack = new ClipboardStack();
        model.addCursorObserver(l -> {
            TextEditor.this.repaint();
        });
        model.addTextObserver(TextEditor.this::repaint);

        addKeyListener(getKeyListener(model));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TextEditor.this.requestFocusInWindow();
            }
        });
    }

    private KeyAdapter getKeyListener(TextEditorModel model) {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() != '\b' && e.getKeyChar() != 127) {
                    if (InputEvent.getModifiersExText(e.getModifiersEx()).equals("Ctrl")) {
                        if (Character.toLowerCase(e.getKeyChar()) == '\u0003') {
                            copy();
                            return;
                        } else if (Character.toLowerCase(e.getKeyChar()) == '\u0016') {
                            paste();
                            return;
                        } else if (Character.toLowerCase(e.getKeyChar()) == '\u0018') {
                            cut();
                            return;
                        } else if (Character.toLowerCase(e.getKeyChar()) == '\u001a') {
                            UndoManager.getInstance().undo();
                            return;
                        } else if (Character.toLowerCase(e.getKeyChar()) == '\u0019') {
                            UndoManager.getInstance().redo();
                            return;
                        }
                    } else if (InputEvent.getModifiersExText(e.getModifiersEx()).equals("Ctrl+Shift")) {
                        if (Character.toLowerCase(e.getKeyChar()) == '\u0016') {
                            superPaste();
                            return;
                        }
                    }
                    model.insert(e.getKeyChar());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> keyUp(e);
                    case KeyEvent.VK_DOWN -> keyDown(e);
                    case KeyEvent.VK_RIGHT -> keyRight(e);
                    case KeyEvent.VK_LEFT -> keyLeft(e);
                    case KeyEvent.VK_BACK_SPACE -> keyBackSpace();
                    case KeyEvent.VK_DELETE -> keyDelete();
                }
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int y = topMargin;
        Graphics2D g2 = (Graphics2D) g;
        int h = g2.getFontMetrics().getHeight();

        LocationRange range = model.getSelectionRange();

        if (range == null) {    // Ako nije nista odabrano samo nacrtaj text
            for (Iterator<String> it = model.allLines(); it.hasNext(); ) {
                g2.drawString(it.next(), leftMargin, y);
                y += h + this.spacing;
            }
        } else {    // Ako imamo range, imamo posla
            Location l1 = range.getStart();
            Location l2 = range.getEnd();
            if (l1.compareTo(l2) > 0) {
                l2 = range.getStart();
                l1 = range.getEnd();
            }

            FontMetrics fm = g2.getFontMetrics();

            for (int i = 0; i < model.getLines().size(); i++) {
                String line = model.getLines().get(i);
                if (i < l1.getY()) {    // Text prije selekcije
                    g2.drawString(line, leftMargin, y);
                } else if (i == l1.getY()) {    // Prvi redak gdje se pojavljuje selekcija
                    String s1 = line.substring(0, l1.getX());
                    int w = leftMargin;
                    g2.drawString(s1, w, y);
                    w += fm.stringWidth(s1);

                    if (l1.getY() != l2.getY()) {   // Ako selekcija nije u jednom retku
                        String s2 = line.substring(l1.getX());
                        Rectangle2D rect = fm.getStringBounds(s2, g2);

                        g2.setColor(backgroundColor);
                        g2.fillRect(w, y - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());
                        g2.setColor(textColor);
                        g2.drawString(s2, w, y);
                    } else {    // Ako je selekcija u samo jednom retku
                        String s2 = line.substring(l1.getX(), l2.getX());
                        Rectangle2D rectangle = fm.getStringBounds(s2, g2);

                        g2.setColor(backgroundColor);
                        g2.fillRect(w, y - fm.getAscent(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
                        g2.setColor(textColor);
                        g2.drawString(s2, w, y);
                        w += fm.stringWidth(s2);

                        String s3 = line.substring(l2.getX());
                        g2.drawString(s3, w, y);
                    }
                } else if (i < l2.getY()) { // Dio selekcije gdje su citavi redovi oznaceni
                    Rectangle2D rectangle = fm.getStringBounds(line, g2);
                    g2.setColor(backgroundColor);
                    g2.fillRect(leftMargin, y - fm.getAscent(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
                    g2.setColor(textColor);
                    g2.drawString(line, leftMargin, y);
                } else if (i == l2.getY()) {    // Zadnji redak selekcije
                    String s1 = line.substring(0, l2.getX());
                    int w = leftMargin;
                    Rectangle2D rect = fm.getStringBounds(s1, g2);

                    g2.setColor(backgroundColor);
                    g2.fillRect(w, y - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());
                    g2.setColor(textColor);
                    g2.drawString(s1, w, y);
                    w += fm.stringWidth(s1);

                    String s2 = line.substring(l2.getX());
                    g2.drawString(s2, w, y);
                } else {    // Retci iza selekcije koji su opet normalni
                    g2.drawString(line, leftMargin, y);
                }
                y += h + this.spacing;
            }
        }

        Location l = model.getCursorLocation();
        String str = model.getCurrentLine().substring(0, l.getX());

        int x = leftMargin + g2.getFontMetrics().stringWidth(str);
        y = topMargin;
        y += (model.getCursorLocation().getY()) * h + (model.getCursorLocation().getY()) * spacing - h;

        g.drawLine(x, y + h / 3, x, y + h + h / 5);
    }

    private void keyDelete() {
        if (model.getSelectionRange() != null) {
            model.deleteRange(model.getSelectionRange());
        } else {
            model.deleteAfter();
        }
    }

    private void keyBackSpace() {
        if (model.getSelectionRange() != null) {
            model.deleteRange(model.getSelectionRange());
        } else {
            model.deleteBefore();
        }
    }

    private void keyUp(KeyEvent e) {
        LocationRange r = model.getSelectionRange();
        if (InputEvent.getModifiersExText(e.getModifiersEx()).equals("Shift")) {
            if (r == null) {
                r = new LocationRange(model.getCursorLocation(), model.cursorUp());
            } else {
                r.setEnd(model.cursorUp());
            }
        } else {
            r = null;
        }

        model.setSelectionRange(r);
        model.moveCursorUp();
    }

    private void keyDown(KeyEvent e) {
        LocationRange r = model.getSelectionRange();
        if (InputEvent.getModifiersExText(e.getModifiersEx()).equals("Shift")) {
            if (r == null) {
                r = new LocationRange(model.getCursorLocation(), model.cursorDown());
            } else {
                r.setEnd(model.cursorDown());
            }
        } else {
            r = null;
        }

        model.setSelectionRange(r);
        model.moveCursorDown();
    }

    private void keyLeft(KeyEvent e) {
        LocationRange r = model.getSelectionRange();
        if (InputEvent.getModifiersExText(e.getModifiersEx()).equals("Shift")) {
            if (r == null) {
                r = new LocationRange(model.getCursorLocation(), model.cursorLeft());
            } else {
                r.setEnd(model.cursorLeft());
            }
        } else {
            r = null;
        }

        model.setSelectionRange(r);
        model.moveCursorLeft();
    }

    private void keyRight(KeyEvent e) {
        LocationRange r = model.getSelectionRange();
        if (InputEvent.getModifiersExText(e.getModifiersEx()).equals("Shift")) {
            if (r == null) {
                r = new LocationRange(model.getCursorLocation(), model.cursorRight());
            } else {
                r.setEnd(model.cursorRight());
            }
        } else {
            r = null;
        }

        model.setSelectionRange(r);
        model.moveCursorRight();
    }

    public void copy() {
        if (model.getSelectionRange() != null) {
            stack.push(model.getSelectedText());
        }
    }

    public void paste() {
        if (!stack.isEmpty()) {
            model.insert(stack.peek());
        }
    }

    public void superPaste() {
        if (!stack.isEmpty()) {
            model.insert(stack.pop());
        }
    }

    public void cut() {
        if (model.getSelectionRange() != null) {
            stack.push(model.getSelectedText());
            model.deleteRange(model.getSelectionRange());
        }
    }

    public TextEditorModel getModel() {
        return model;
    }

    public ClipboardStack getClipboardStack() {
        return stack;
    }
}
