package gui;

import graphics.GraphicalObject;
import model.DocumentModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    private final List<GraphicalObject> objects;
    private final DocumentModel model;
    private final Canvas canvas;

    public GUI(List<GraphicalObject> objects) {
        this.objects = objects;
        model = new DocumentModel();
        canvas = new Canvas(model);
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

        objects.forEach(o -> {
            toolBar.add(new JButton(o.getShapeName()));
        });

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }
}
