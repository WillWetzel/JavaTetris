package Host;

import java.awt.*;

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new TetrisPanel();
            game.setVisible(true);
        });
    }
}
