package jeremi.pacman.GameOver;

import jeremi.pacman.AbstractWindow;

public class GameOverWindow extends AbstractWindow {


    public GameOverWindow(int score, int level) {

        initializeFrame();

        GameOverPanel gameOverPanel = new GameOverPanel(this, score, level);
        this.setContentPane(gameOverPanel);

    }

}
