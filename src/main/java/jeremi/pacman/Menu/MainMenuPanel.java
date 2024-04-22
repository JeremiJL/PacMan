package jeremi.pacman.Menu;

import jeremi.pacman.AbstractContentPanel;
import jeremi.pacman.Constants;
import jeremi.pacman.GamePlay.Preferences.GamePreferencesWindow;
import jeremi.pacman.GamePlay.Multiplayer.MultiplayerPreferencesWindow;
import jeremi.pacman.HighScore.HighScoreWindow;
import jeremi.pacman.Options.OptionsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends AbstractContentPanel {


    //Buttons
    private JButton buttonNewGame;
    private JButton buttonMultiPlayer;
    private JButton buttonExit;
    private JButton buttonScores;
    private JButton buttonOptions;

    public MainMenuPanel(JFrame window) {
        super(window);
        setTitle("MENU");
    }

    @Override
    protected void initializeAttributes() {
        //Attribute Panel - Panel containing buttons
        //directing you to other pages

        this.panelAttributes = new JPanel(new GridLayout(5,1));
        this.add(panelAttributes,BorderLayout.CENTER);

        //Initialize components
        initializeButtons();
        setListeners();

    }

    private void initializeButtons(){

        //New Game button
        this.buttonNewGame = new JButton("NEW GAME");
        this.buttonNewGame.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));
        this.panelAttributes.add(buttonNewGame);

        //Multiplayer button
        this.buttonMultiPlayer = new JButton("MULTIPLAYER");
        this.buttonMultiPlayer.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));
        //As multiplayer gameplay is still not implemented this button is disabled
        this.buttonMultiPlayer.setEnabled(false);
        this.panelAttributes.add(buttonMultiPlayer);

        //HighScores button
        this.buttonScores = new JButton("HIGH SCORES");
        this.buttonScores.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));
        //As high-scores is still not implemented this button is disabled
        this.panelAttributes.add(buttonScores);

        //Options button
        this.buttonOptions = new JButton("OPTIONS");
        this.buttonOptions.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));
        this.panelAttributes.add(buttonOptions);

        //Exit button
        this.buttonExit = new JButton("EXIT");
        this.buttonExit.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));
        this.panelAttributes.add(buttonExit);

    }

    @Override
    protected void setListeners() {

        //New Game listener
        this.buttonNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                new GamePreferencesWindow();
            }
        });

        //Multiplayer listener
        this.buttonMultiPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                new MultiplayerPreferencesWindow();
            }
        });

        //Exit Game listener
        this.buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                System.exit(0);
            }
        });

        //High Scores listener
        this.buttonScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                new HighScoreWindow();
            }
        });

        //Options listener
        this.buttonOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                new OptionsWindow();
            }
        });

    }


}
