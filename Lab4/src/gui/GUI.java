package gui;

import graphics.GraphicalObject;
import model.DocumentModel;
import state.IdleState;
import state.State;

import javax.swing.*;
import java.awt.*;
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

        setLocation(1000, 10);
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());

        this.getContentPane().add(canvas);

        createToolBar(this.objects);
    }

    private void createToolBar(List<GraphicalObject> objects) {
        JToolBar toolBar = new JToolBar("Tools");

        objects.forEach(o -> toolBar.add(new JButton(o.getShapeName())));

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State state) {
        currentState.onLeaving();
        currentState = state;
    }
}
