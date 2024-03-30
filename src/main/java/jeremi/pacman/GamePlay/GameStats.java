package jeremi.pacman.GamePlay;

import javax.swing.*;
import java.awt.*;

public class GameStats extends JPanel {

    //size dimension
    private int height;

    //values
    private int coinsCount;
    private int livesCount;
    private int levelCount;
    private String powerUpValue;

    //components

    private JLabel coinsCountLabel;
    private JLabel coinsInfoLabel;

    private JLabel livesCountLabel;
    private JLabel livesInfoLabel;

    private JLabel levelCountLabel;
    private JLabel levelInfoLabel;

//    private JLabel powerUpValueLabel;
//    private JLabel powerUpInfoLabel;

    public GameStats(int height) {

        //firstly assign height
        this.height = height;
        //afterwards initialization
        initializePanel();

    }

    private void initializePanel() {

        rescale(this.height);


        //Initialize variables
        this.coinsCount = 0;
        this.levelCount = 1;
//        this.powerUpValue = "none";
        this.livesCount = 3;

        //Create labels
        coinsInfoLabel = new JLabel("Score : " + coinsCount);
        initializeLabel(coinsInfoLabel, 0);

        livesInfoLabel = new JLabel("Lives : " + livesCount);
        initializeLabel(livesInfoLabel, 0);

        levelInfoLabel = new JLabel("Level : " + levelCount);
        initializeLabel(levelInfoLabel, 0);

//        powerUpInfoLabel = new JLabel("Power-Up : " + powerUpValue);
//        initializeLabel(powerUpInfoLabel, 0);

        GridLayout gridLayout = new GridLayout(3, 1);
        setLayout(gridLayout);

        //Add components
        add(coinsInfoLabel);

        add(livesInfoLabel);

        add(levelInfoLabel);

//        add(powerUpInfoLabel);

    }

    private void initializeLabel(JLabel label, int type) {

        switch (type) {
            case 0 -> label.setHorizontalAlignment(JLabel.CENTER);
            case 1 -> label.setHorizontalAlignment(JLabel.LEFT);
        }

        label.setFont(new Font("Consolas", Font.BOLD, this.height / 7));
    }

    public void scorePoint(int multiplier) {
        coinsCount += 1 * (multiplier);
        coinsInfoLabel.setText("Score : " + coinsCount);
    }

    public void loseALife() {
        this.livesCount--;
        this.livesInfoLabel.setText("Lives : " + livesCount);
    }

    public void levelUp() {
        this.levelCount++;
        levelInfoLabel.setText("Level : " + levelCount);
    }

//    public void powerUp(String value){
//        this.powerUpValue = value;
//        powerUpInfoLabel.setText("Power-Up : " + powerUpValue);
//    }


    public void rescale(int height) {
        this.setPreferredSize(new Dimension(height, height));
    }

    public int getLivesCount() {
        return livesCount;
    }

    public int getLevelCount() {
        return levelCount;
    }

//    public String getPowerUpValue() {
//        return powerUpValue;
//    }

    public int getCoinsCount() {
        return coinsCount;
    }

}
