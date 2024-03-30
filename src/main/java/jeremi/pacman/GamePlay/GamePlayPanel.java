package jeremi.pacman.GamePlay;

import javax.swing.*;
import java.awt.*;

public class GamePlayPanel extends JPanel {

    //Game play window
    private GamePlayWindow window;

    //BoardSize is a number of rows and columns of board
    private final int boardSize;

    //window size
    private int frameWidth;
    private int frameHeight;

    //board size
    private int boardHeight;
    private int cellSize;

    //game stats
    private int statsHeight;

    //Board
    private Board board;

    //Game Stats panel
    private GameStats gameStats;

    public GamePlayPanel(GamePlayWindow window, int boardSize) {

        this.window = window;

        //BoardSize = number of rows and collumns
        this.boardSize = boardSize;

        //Frame dimensions
        this.frameWidth = window.getWidth();
        this.frameHeight = window.getHeight();

        //Calculates dimensions of game stats panel and table(board)
        calculateDimensions();

        //Initialize panel
        initializePanel();

    }

    private void initializePanel(){

        //Set Layout
        BorderLayout panelLayout = new BorderLayout();
        this.setLayout(panelLayout);

        //create game stats BEFORE BOARD!!!
        this.gameStats = new GameStats(this.statsHeight);

        //create board
        this.board = new Board(window,gameStats,boardSize,cellSize);

        //Add components
        this.add(board, BorderLayout.CENTER);
        this.add(gameStats, BorderLayout.PAGE_END);

    }

    public void calculateDimensions(){

        //I assume that this is maximal space for the top of window built in bar
        int correction = 100;

        //Both board and Game stats will occupy all the width possible

        //Board will occupy 4/5 of the height
        this.boardHeight = ((this.frameHeight - correction )*4)/5;

        //Game stats will occupy 1/5 of the height
        this.statsHeight = (this.frameHeight - correction) - this.boardHeight;

        //Calculate dimensions that cell of board should take
        this.cellSize =  boardHeight/boardSize;

    }


}
