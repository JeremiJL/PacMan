package jeremi.pacman.GamePlay;

import jeremi.pacman.AbstractWindow;
import jeremi.pacman.Constants;
import jeremi.pacman.Menu.MainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GamePreferencesWindow extends AbstractWindow {

    public GamePreferencesWindow() {

        initializeFrame();

        GamePreferencesPanel gamePreferencesPanel = new GamePreferencesPanel(this);
        this.setContentPane(gamePreferencesPanel);

        setMyCloseOperation();
    }





}
