package jeremi.pacman.GamePlay;

import jeremi.pacman.AbstractWindow;
import jeremi.pacman.Constants;
import jeremi.pacman.Menu.MainMenuWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GamePlayWindow extends AbstractWindow {

    private GamePlayPanel gamePlayPanel;

    public GamePlayWindow(int boardSize) {

        initializeFrame();

        this.gamePlayPanel = new GamePlayPanel(this,boardSize);
        this.setContentPane(gamePlayPanel);

        setMyCloseOperation();

    }

}
