package jeremi.pacman.GamePlay.Figures.Static;

import jeremi.pacman.GamePlay.Board;
import jeremi.pacman.GamePlay.Figures.Dynamic.Player;
import jeremi.pacman.GamePlay.GameStats;

import javax.swing.*;

public class PowerUp extends Inanimate{

    //PowerUp default Image
    private static ImageIcon IMAGE = new ImageIcon("Graphics/PowerUp/PowerUp1.png");

    //Board
    private Board board;

    //Game stats
    private GameStats stats;

    public PowerUp(Board board, GameStats stats, int xPos, int yPos) {
        super(xPos, yPos, IMAGE);

        //Board assignment
        this.board = board;

        //Stats assignment
        this.stats = stats;

        //Random Image
        updateImage();

    }

    //Animation requires this
    private void updateImage(){

        int random = (int)(Math.random()*4);
        ImageIcon newImage = IMAGE;

        switch (random){
            case 0 -> newImage = new ImageIcon("Graphics/PowerUp/PowerUp1.png");
            case 1 -> newImage = new ImageIcon("Graphics/PowerUp/PowerUp2.png");
            case 2 -> newImage = new ImageIcon("Graphics/PowerUp/PowerUp3.png");
            case 3 -> newImage = new ImageIcon("Graphics/PowerUp/PowerUp4.png");
        }

        setImage(newImage);
        this.board.drawBoardFigure(this.getYPos(),this.getXPos(), Board.FigureType.STATIC);
    }

    public void rollPower(Player player){

        int random = (int)(Math.random()*4);

        //tmp - test purposes
        //random = 3;

        switch (random){

            case 0 :
                speedPower(player);
                //stats.powerUp("Super Speed");
                break;
            case 1 :
                scorePower();
                //stats.powerUp("Score Multiplier");
                break;
            case 2 :
                coinManiaPower();
                break;
            case 3 :
                informBoxesPower();
                break;

        }

    }


    private void informBoxesPower(){

        int powerUpTime = 6_000;

        new Thread(){
            @Override
            public void run() {
                board.setInformBoxes(true);
                try {
                    Thread.sleep(powerUpTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                board.setInformBoxes(false);
            }
        }.start();

    }

    private void speedPower(Player player){
        player.setMTI(50);
    }

    private void scorePower(){
        Board.setScoreMultiplier(5);
    }

    private void coinManiaPower(){

        int numberOfNewCoins = 8;

        board.generateCoins(numberOfNewCoins);
        board.drawWholeBoard();

    }

    private void immaterialityPower(){

    }

    public static void disableScorePower(){
        //disable score power
        Board.setScoreMultiplier(1);
    }

    public static void disableSpeedPower(Player player){
        //disable speed power
        player.setMTI(player.getDefaultSpeed());
    }

    public static void disableAllPowers(Player player){
        disableScorePower();
        disableSpeedPower(player);
    }

}
