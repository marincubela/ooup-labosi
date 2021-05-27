package gui;

import graphics.CompositeShape;
import graphics.GraphicalObject;
import model.DocumentModel;
import renderer.SVGRendererImpl;
import state.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class GUI extends JFrame {
    private final List<GraphicalObject> objects;
    private final DocumentModel model;
    private final Canvas canvas;
    private State currentState;

    public GUI(List<GraphicalObject> objects) {
        this.objects = objects;
        model = new DocumentModel();
        currentState = new IdleState();
        canvas = new Canvas(model, this);

        model.addDocumentModelListener(canvas::repaint);

        setListeners();

        setLocation(1000, 10);
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initGUI();
    }

    private void setListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setCurrentState(new IdleState());
                } else {
                    getCurrentState().keyPressed(e.getKeyCode());
                }
            }
        });
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.getContentPane().add(canvas);
        createToolBar(this.objects);
    }

    private void createToolBar(List<GraphicalObject> objects) {
        JToolBar toolBar = new JToolBar("Tools");
        toolBar.setFocusable(false);

        objects.stream().filter(o -> o.getShapeName() != null).forEach(o -> toolBar.add(makeButton(o)));

        toolBar.add(selectButton());
        toolBar.add(eraseButton());
        toolBar.add(svgButton());
        toolBar.add(saveButton());
        toolBar.add(loadButton());

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    private JButton selectButton() {
        JButton btn = new JButton("Selektiraj");
        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentState(new SelectShapeState(model));
            }
        });
        btn.setFocusable(false);

        return btn;
    }

    private JButton eraseButton() {
        JButton btn = new JButton("Brisalo");
        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentState(new EraserState(model));
            }
        });
        btn.setFocusable(false);

        return btn;
    }

    private JButton svgButton() {
        JButton btn = new JButton("SVG Export");
        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("SVG export");
                if (jfc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                String path = jfc.getSelectedFile().getPath();

                if (!path.endsWith(".svg")) {
                    path += ".svg";
                }

                SVGRendererImpl svgRenderer = new SVGRendererImpl(path);
                model.list().forEach(o -> o.render(svgRenderer));

                try {
                    svgRenderer.close();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(GUI.this, "Unable to save the file", "Save failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn.setFocusable(false);

        return btn;
    }

    private JButton saveButton() {
        JButton btn = new JButton("Pohrani");
        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Save");
                if (jfc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                String path = jfc.getSelectedFile().getPath();

                List<String> rows = new ArrayList<>();
                model.list().forEach(o -> o.save(rows));
                try {
                    Files.write(Path.of(path), rows);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(GUI.this, "Unable to save the file", "Save failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn.setFocusable(false);

        return btn;
    }

    private JButton loadButton() {
        JButton btn = new JButton("Ucitaj");
        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Load");
                if (jfc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                String path = jfc.getSelectedFile().getPath();

                List<String> rows;
                try {
                    rows = Files.readAllLines(Path.of(path));
                    Stack<GraphicalObject> stack = new Stack<>();
                    for (String s : rows) {
                        for (var o : objects) {
                            if (s.startsWith(o.getShapeID())) {
                                o.load(stack, s);
                                break;
                            }
                        }
                        CompositeShape c = new CompositeShape(Collections.emptyList());
                        if (s.startsWith(c.getShapeID())) {
                            c.load(stack, s);
                        }
                    }

                    while (!stack.isEmpty()) {
                        model.addGraphicalObject(stack.pop());
                    }
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(GUI.this, "Unable to load the file", "Save failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn.setFocusable(false);

        return btn;
    }

    public JButton makeButton(GraphicalObject object) {
        JButton btn = new JButton(object.getShapeName());
        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentState(new AddShapeState(model, object));
            }
        });
        btn.setFocusable(false);

        return btn;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State state) {
        currentState.onLeaving();
        currentState = state;
    }
}
