package editor.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyComponent extends JComponent implements KeyListener {
    public MyComponent() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawLine(10, 10, 100, 10);
        g.drawLine(10, 10, 10, 100);
        g.drawString("Pozz za sve moje hejtere!", 20, 20);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.exit(0);
        }
    }
}
