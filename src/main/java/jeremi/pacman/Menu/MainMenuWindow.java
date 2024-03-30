package jeremi.pacman.Menu;

import jeremi.pacman.AbstractWindow;


public class MainMenuWindow extends AbstractWindow {

    public MainMenuWindow() {

        initializeFrame();

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        this.setContentPane(mainMenuPanel);
    }
}
