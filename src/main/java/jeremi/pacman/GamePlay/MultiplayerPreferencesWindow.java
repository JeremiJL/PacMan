package jeremi.pacman.GamePlay;

import jeremi.pacman.AbstractWindow;

public class MultiplayerPreferencesWindow extends AbstractWindow {

    public MultiplayerPreferencesWindow() {

        initializeFrame();

        MultiplayerPreferencesPanel multiplayerPreferencesPanel = new MultiplayerPreferencesPanel(this);
        this.setContentPane(multiplayerPreferencesPanel);

        setMyCloseOperation();
    }

}
