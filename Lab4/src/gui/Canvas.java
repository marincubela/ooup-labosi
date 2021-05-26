package gui;

import model.DocumentModel;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JComponent {
    private DocumentModel model;

    public Canvas(DocumentModel model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


    }
}
