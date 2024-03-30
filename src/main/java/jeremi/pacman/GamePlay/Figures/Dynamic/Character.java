package jeremi.pacman.GamePlay.Figures.Dynamic;

import jeremi.pacman.GamePlay.Board;
import jeremi.pacman.GamePlay.Figures.BoardFigure;

import javax.swing.*;

public abstract class Character extends BoardFigure implements Runnable{

    //Coordinates of character starting location
    private final int startLocationX;
    private final int startLocationY;

    //Assigning board to access it is function
    protected Board board;

    //Movement Time Interval, represents speed of character
    //value of MTI is the number of milliseconds that character
    //hast to spend on single cell before going to the next one
    private int MOVEMENT_TIME_INTERVAL; //ms

    public Character(Board board, int xPos, int yPos, ImageIcon image) {
        super(xPos, yPos, image);

        //Assignment
        this.board = board;

        //Setting starter coordinates
        this.startLocationX = xPos;
        this.startLocationY = yPos;

        //Setting default efault speed for all  character classes
        this.MOVEMENT_TIME_INTERVAL = 200;
    }

    public int getStartLocationX() {
        return startLocationX;
    }

    public int getStartLocationY() {
        return startLocationY;
    }

    public void setMTI(int MTI) {
        MOVEMENT_TIME_INTERVAL = MTI;
    }

    public int getMTI() {
        return MOVEMENT_TIME_INTERVAL;
    }

    protected void updateImage(){
        this.board.drawBoardFigure(this.getYPos(),this.getXPos(), Board.FigureType.DYNAMIC);
    }


}
