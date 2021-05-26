package gui;

import model.DocumentModel;
import renderer.G2DRendererImpl;
import renderer.Renderer;
import utils.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas extends JComponent {
    private final DocumentModel model;
    private final GUI gui;

    public Canvas(DocumentModel model, GUI gui) {
        this.model = model;
        this.gui = gui;
        setListeners();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Renderer r = new G2DRendererImpl(g2d);
        model.list().forEach(o -> {
            o.render(r);
            gui.getCurrentState().afterDraw(r, o);
        });
        gui.getCurrentState().afterDraw(r);
    }


    private void setListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gui.getCurrentState().mouseDown(new utils.Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gui.getCurrentState().mouseUp(new utils.Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                gui.getCurrentState().mouseDragged(new Point(e.getX(), e.getY()));
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                gui.getCurrentState().mouseDragged(new Point(e.getX(), e.getY()));
            }
        });
    }
}
