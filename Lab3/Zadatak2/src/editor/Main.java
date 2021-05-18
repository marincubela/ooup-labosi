package editor;

import editor.plugins.Plugin;
import editor.plugins.PluginFactory;
import editor.undoManager.UndoManager;
import editor.undoManager.UndoManagerObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private TextEditor editor;

    public Main() {
        super();
        setLocation(1000, 10);
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        editor = new TextEditor(new TextEditorModel("Ovo je tekst neki!\nKa\nMos mislit\nPozz"));
        initGUI();
    }

    private void initGUI() {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.white);
        this.setTitle("Moj editor");

        JPanel statusBar = getStatusBar();

        JScrollPane scrollPane = new JScrollPane(editor);
        scrollPane.setAutoscrolls(true);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(statusBar, BorderLayout.PAGE_END);

        editor.requestFocusInWindow();
        createMenu(editor);
        createToolBar(editor);
    }

    private JPanel getStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setFocusable(false);
        JLabel l1 = new JLabel("Ln: 1");
        editor.getModel().addCursorObserver(l -> {
            l1.setText("Ln: " + (l.getY() + 1));
        });
        JLabel l2 = new JLabel("Col: 0");
        editor.getModel().addCursorObserver(l -> {
            l2.setText("Col: " + (l.getX() + 1));
        });
        JLabel l3 = new JLabel("Rows: 0");
        editor.getModel().addTextObserver(() -> {
            l3.setText("Rows: " + editor.getModel().getLines().size());
        });

        statusBar.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.gray));
        l1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
        l2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
        l3.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));

        statusBar.add(l1);
        statusBar.add(l2);
        statusBar.add(l3);
        return statusBar;
    }

    private void createMenu(TextEditor editor) {
        AbstractAction undoAction = getUndoAction(editor);
        AbstractAction redoAction = getRedoAction(editor);
        AbstractAction cutAction = getCutAction(editor);
        AbstractAction copyAction = getCopyAction(editor);
        AbstractAction pasteAction = getPasteAction(editor);
        AbstractAction pasteAndTakeAction = getPasteAndTakeAction(editor);
        AbstractAction deleteSelectionAction = getDeleteSelectionAction(editor);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setFocusable(false);
        menuBar.setFocusTraversalKeysEnabled(false);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFocusable(false);
        menuBar.add(fileMenu);

        JMenuItem open = new JMenuItem("Open");
        open.setFocusable(false);
        fileMenu.add(open);
        JMenuItem save = new JMenuItem("Save");
        save.setFocusable(false);
        fileMenu.add(save);
        JMenuItem exit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.this.dispose();
            }
        });
        exit.setFocusable(false);
        fileMenu.add(exit);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setFocusable(false);
        menuBar.add(editMenu);

        var undoItem = new JMenuItem(undoAction);
        undoItem.setFocusable(false);
        editMenu.add(undoItem);

        JMenuItem redo = new JMenuItem(redoAction);
        redo.setFocusable(false);
        editMenu.add(redo);

        JMenuItem cut = new JMenuItem(cutAction);
        cut.setFocusable(false);
        editMenu.add(cut);

        JMenuItem copy = new JMenuItem(copyAction);
        copy.setFocusable(false);
        editMenu.add(copy);

        editMenu.add(new JMenuItem(pasteAction));
        editMenu.add(new JMenuItem(pasteAndTakeAction));
        editMenu.add(new JMenuItem(deleteSelectionAction));
        editMenu.add(new JMenuItem("Clear document"));

        JMenu moveMenu = new JMenu("Move");
        menuBar.add(moveMenu);

        moveMenu.add(new JMenuItem(new AbstractAction("Cursor to document start") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.getModel().cursorToStart();
            }
        }));
        moveMenu.add(new JMenuItem(new AbstractAction("Cursor to document end") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.getModel().cursorToEnd();
            }
        }));

        menuBar.add(getPluginMenu());

        this.setJMenuBar(menuBar);
    }

    public JMenu getPluginMenu() {
        JMenu pluginMenu = new JMenu("Plugins");

        for (Plugin p : PluginFactory.getPlugins()) {
            JMenuItem item = new JMenuItem(new AbstractAction(p.getName()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.execute(editor.getModel(), UndoManager.getInstance(), editor.getClipboardStack());
                }
            });
            pluginMenu.add(item);
        }

        return pluginMenu;
    }

    private void createToolBar(TextEditor editor) {
        JToolBar toolBar = new JToolBar("Tools");
        toolBar.setFocusable(false);
        toolBar.setFocusTraversalKeysEnabled(false);

        JButton undoBtn = new JButton(getUndoAction(editor));
        undoBtn.setFocusable(false);
        toolBar.add(undoBtn);
        toolBar.add(new JButton(getRedoAction(editor)));
        toolBar.add(new JButton(getCutAction(editor)));
        toolBar.add(new JButton(getCopyAction(editor)));
        toolBar.add(new JButton(getPasteAction(editor)));
        toolBar.add(new JButton(getPasteAndTakeAction(editor)));

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    private AbstractAction getUndoAction(TextEditor editor) {
        var undoAction = new AbstractAction("Undo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                UndoManager.getInstance().undo();
            }
        };

        UndoManager.getInstance().addUndoManagerObserver(new UndoManagerObserver() {
            @Override
            public void updateUndoStack(boolean isEmpty) {
                undoAction.setEnabled(!isEmpty);
            }

            @Override
            public void updateRedoStack(boolean isEmpty) {
            }
        });
        undoAction.setEnabled(false);
        return undoAction;
    }

    private AbstractAction getRedoAction(TextEditor editor) {
        var redoAction = new AbstractAction("Redo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                UndoManager.getInstance().redo();
            }
        };

        UndoManager.getInstance().addUndoManagerObserver(new UndoManagerObserver() {
            @Override
            public void updateUndoStack(boolean isEmpty) {
            }

            @Override
            public void updateRedoStack(boolean isEmpty) {
                redoAction.setEnabled(!isEmpty);
            }
        });
        redoAction.setEnabled(false);
        return redoAction;
    }

    private AbstractAction getPasteAction(TextEditor editor) {
        var pasteAction = new AbstractAction("Paste") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.paste();
            }
        };

        editor.getClipboardStack().addClipboardObserver(() -> pasteAction.setEnabled(!editor.getClipboardStack().isEmpty()));
        pasteAction.setEnabled(false);
        return pasteAction;
    }

    private AbstractAction getPasteAndTakeAction(TextEditor editor) {
        var pasteAndTakeAction = new AbstractAction("Paste and Take") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.superPaste();
            }
        };

        editor.getClipboardStack().addClipboardObserver(() -> pasteAndTakeAction.setEnabled(!editor.getClipboardStack().isEmpty()));
        pasteAndTakeAction.setEnabled(false);
        return pasteAndTakeAction;
    }

    private AbstractAction getCutAction(TextEditor editor) {
        var cutAction = new AbstractAction("Cut") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.cut();
            }
        };

        editor.getModel().addSelectionObserver(r -> cutAction.setEnabled(r != null));
        cutAction.setEnabled(false);
        return cutAction;
    }

    private AbstractAction getCopyAction(TextEditor editor) {
        var copyAction = new AbstractAction("Copy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.copy();
            }
        };

        editor.getModel().addSelectionObserver(r -> copyAction.setEnabled(r != null));
        copyAction.setEnabled(false);
        return copyAction;
    }

    private AbstractAction getDeleteSelectionAction(TextEditor editor) {
        var deleteSelectionAction = new AbstractAction("Delete selection") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.getModel().deleteRange(editor.getModel().getSelectionRange());
            }
        };

        editor.getModel().addSelectionObserver(r -> deleteSelectionAction.setEnabled(r != null));
        deleteSelectionAction.setEnabled(false);
        return deleteSelectionAction;
    }

    @Override
    public boolean requestFocusInWindow() {
        return editor.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
