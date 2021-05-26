package gui;

import graphics.Oval;
import model.DocumentModel;
import renderer.G2DRendererImpl;
import renderer.Renderer;
import utils.Rectangle;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JComponent {
    private DocumentModel model;

    public Canvas(DocumentModel model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Renderer r = new G2DRendererImpl(g2d);
        model.list().forEach(o -> o.render(r));
    }
}
