package jeremi.pacman.GamePlay.Preferences;

import jeremi.pacman.AbstractWindow;

public class GamePreferencesWindow extends AbstractWindow {

    public GamePreferencesWindow() {

        initializeFrame();

        GamePreferencesPanel gamePreferencesPanel = new GamePreferencesPanel(this);
        this.setContentPane(gamePreferencesPanel);

        setMyCloseOperation();
    }





}
