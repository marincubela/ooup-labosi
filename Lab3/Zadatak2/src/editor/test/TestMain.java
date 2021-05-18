package editor.test;

import javax.swing.*;

public class TestMain extends JFrame {
    public TestMain() {
        super();
        setLocation(10, 10);
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initGUI();
    }

    private void initGUI() {
        MyComponent c = new MyComponent();
        this.getContentPane().add(c);
        this.addKeyListener(c);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TestMain().setVisible(true);
        });
    }
}
