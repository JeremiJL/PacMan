package jeremi.pacman.GameOver;

import jeremi.pacman.AbstractContentPanel;
import jeremi.pacman.Constants;
import jeremi.pacman.HighScore.HighScoreWindow;
import jeremi.pacman.Menu.MainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends AbstractContentPanel {

//    //Panels
//    private JPanel panelButtons;

    //Labels
    private JLabel labelScore;
    private JLabel labelLevel;

    //Buttons
    private JButton buttonScores;
    private JButton buttonMenu;

    //Player results for display
    public int score;
    protected int level;


    public GameOverPanel(JFrame window,int score, int level) {
        super(window);
        setTitle("GAME OVER");

        this.score = score;
        this.level = level;

        setLabelsText();

    }

    //Sets the text of labels corresponding to players result
    private void setLabelsText(){
        this.labelLevel.setText("Your level : " + level);
        this.labelScore.setText("Your score : " + score);
    }


    @Override
    protected void initializeAttributes() {
        //Attribute Panel - simply informing about player score
        //contains buttons directing you to menu page and high-scores page

        this.panelAttributes = new JPanel(new GridLayout(5,1));
        this.add(panelAttributes,BorderLayout.CENTER);

        //Initialize components
        initializeLabels();
        initializeButtons();
        setListeners();
    }

    @Override
    protected void setListeners() {

        //High Scores listener
        this.buttonScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                new HighScoreWindow();
            }
        });

        //Menu listener
        this.buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                new MainMenuWindow();
            }
        });


    }

    private void initializeButtons(){

        //HighScores redirect button
        this.buttonScores = new JButton("HIGH SCORES");
        this.buttonScores.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.panelAttributes.add(buttonScores);

        //Menu redirect button
        this.buttonMenu = new JButton("EXIT");
        this.buttonMenu.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.panelAttributes.add(buttonMenu);

    }


    private void initializeLabels(){

        //Your score label
        this.labelScore = new JLabel("" + score,SwingConstants.CENTER);
        this.labelScore.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));

        this.panelAttributes.add(this.labelScore);

        //Your level label
        this.labelLevel = new JLabel("" + level,SwingConstants.CENTER);
        this.labelLevel.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));

        this.panelAttributes.add(this.labelLevel);

        //Space
        JLabel s1 = new JLabel(" ");
        s1.setPreferredSize(Constants.getDimension(Constants.COMPONENT_TYPE.SPACE));

        this.panelAttributes.add(s1);

    }


}
