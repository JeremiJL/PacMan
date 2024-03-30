package jeremi.pacman.HighScore;

import jeremi.pacman.AbstractWindow;

public class HighScoreWindow extends AbstractWindow {


    public HighScoreWindow() {

        initializeFrame();

        HighScorePanel highScorePanel = new HighScorePanel(this);
        this.setContentPane(highScorePanel);

    }

}
