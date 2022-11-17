package Host;

import Engines.TetrisGameEngine;

import javax.swing.*;
import java.awt.*;

public class TetrisPanel extends JFrame {
    private JLabel statusBar;

    public TetrisPanel() {
        innitUI();
    }

    private void innitUI() {
        statusBar = new JLabel("0");
        add(statusBar, BorderLayout.SOUTH);

        var tetrisGameEngine = new TetrisGameEngine(this);
        add(tetrisGameEngine);
        tetrisGameEngine.start();

        setTitle("TetrisGame.Tetris");
        setSize(200, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JLabel getStatusBar() {
        return statusBar;
    }
}
