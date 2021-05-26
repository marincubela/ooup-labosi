package gui;

import graphics.GraphicalObject;
import model.DocumentModel;
import state.AddShapeState;
import state.IdleState;
import state.SelectShapeState;
import state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

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
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    private JButton selectButton() {
        JButton btn = new JButton("Select");
        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentState(new SelectShapeState(model));
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
