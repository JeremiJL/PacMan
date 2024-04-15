package jeremi.pacman.GamePlay;

import jeremi.pacman.GameOver.GameOverWindow;
import jeremi.pacman.GamePlay.Figures.*;
import jeremi.pacman.GamePlay.Figures.Dynamic.*;
import jeremi.pacman.GamePlay.Figures.Dynamic.Character;
import jeremi.pacman.GamePlay.Figures.Static.*;
import jeremi.pacman.GamePlay.Figures.Static.Box;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

//Inspired by this:
//https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon

public class Board extends JTable implements Runnable {

    //Threads responsible for animation and movement of ghosts
    Thread gameThread;
    Thread playerThread;
    Thread pinkyThread;
    Thread clydeThread;
    Thread blinkyThread;

    //Game play window
    private GamePlayWindow window;

    //A period of time used to slow down the tempo of the game
    //It is used after player loosing life point
    private int PAUSE_INTERVAL = 1_000; //ms

    //Number of squares in a column / row
    //Board is a square itself so the sum of all cells os boardSize^2
    private final int boardSize;

    //Dimensions of a single cell, cell is a square so height = width
    private static int CELL_SIZE;

    //Enum to make code look better
    public enum FigureType {STATIC,DYNAMIC};

    //I call them logic-tables because they are not directly
    //responsible for visualisation but for collision prevention, movement etc.

    //Board figures table - logic staticFigData
    private BoardFigure[][] staticFigData;

    //Board figures table - logic dynamicFigData
    private BoardFigure[][] dynamicFigData;

    //Dynamic Board Figures, player and ghosts
    private Player player;
    private Pinky pinky;
    private Clyde clyde;
    private Blinky blinky;

    //Tracker
    private Tracker tracker;

    //Parameters of the gameplay
    private int coinsToCollect;
    private int coinsCollected;

    //Score multiplier
    private static int scoreMultiplier = 1;

    //PassWallsPower
    private boolean informBoxes = false;


    //Game stats
    private GameStats stats;

    public Board(GamePlayWindow window, GameStats stats, int boardSize, int cellSize) {

        //Window
        this.window = window;

        //Assign game stats panel to this board
        this.stats = stats;

        //Assign board size = number of squares
        this.boardSize = boardSize;

        //Dimension of single cell
        this.CELL_SIZE = cellSize;

        //Game parameters
        coinsToCollect = 10;

        //GenerateBoard with all BoardFigures along
        generateBoard();

        //Initialize jTable : table model and other parameters
        initializeTable();

        //KeyListener listens for 'wasd' keys to control the player
        addKeyListener(this.player.keyListener);

        //Paint whole board, with all figures
        drawWholeBoard();

        //Creating threads and starting them:

        //Player thread
        this.playerThread = new Thread(player);
        this.playerThread.start();

        //Pinky thread - ghost
        this.pinkyThread = new Thread(pinky);
        this.pinkyThread.start();

        //Blinky thread - ghost
        this.blinkyThread = new Thread(blinky);
        this.blinkyThread.start();

        //Power Up generator Thread
        this.blinky.productionThread.start();

        //Clyde thread - ghost
        this.clydeThread = new Thread(clyde);
        this.clydeThread.start();

        //Code.Main-Game thread
        this.gameThread = new Thread(this);
        gameThread.start();

        //TMP
        selectedListener();

    }

    private void gameLoop(){

        while (this.gameThread.getState() == Thread.State.RUNNABLE){

            repaint();
            try {
                Thread.sleep(1000/30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void generateBoard(){

        //--Creating staticFigData table before dynamic one
        this.staticFigData = new BoardFigure[boardSize][boardSize];

        //--Creating dynamicFigData table
        this.dynamicFigData = new BoardFigure[boardSize][boardSize];

        //--Initially fill everything with empty space
        for (int row = 0; row < boardSize; row++){
            for (int col = 0; col < boardSize; col++){
                this.staticFigData[row][col] = new Space(col,row);
                this.dynamicFigData[row][col] = new Space(col,row);
            }
        }

        //Create box pattern for 10x10 board or a random one for greater ones
        generateMaze();

        //Create some randomly spread coins on game board
        generateCoins(coinsToCollect);

        //Tracker have to be initialized after maze generation and before potential clients
        this.tracker = new Tracker(this);

        //creates new player
        this.player = new Player(this, 0,0);
        dynamicFigData[player.getYPos()][player.getXPos()] = player;

        //Creates new ghosts
        this.pinky = new Pinky(this,boardSize-1, 0);
        dynamicFigData[pinky.getYPos()][pinky.getXPos()] = pinky;

        this.clyde = new Clyde(this,0,boardSize-1);
        dynamicFigData[clyde.getYPos()][clyde.getXPos()] = clyde;

        this.blinky = new Blinky(this,boardSize-1,boardSize-1);
        dynamicFigData[blinky.getYPos()][blinky.getXPos()] = blinky;

    }

    private void newLevel(){

        //If you play on greater board than the standard one
        //Maze will be generated each level
        if (boardSize > 10){
            generateRandomMaze();
        }

        //Removes all power ups
        removeAllPowerUps();

        //Disables all power-ups
        PowerUp.disableAllPowers(player);

        //Disables movement of ghosts
        clyde.setMovementActive(false);
        pinky.setMovementActive(false);
        blinky.setMovementActive(false);


        //Reset parameters
        this.coinsCollected = 0;
        this.coinsToCollect = coinsToCollect + (int)(Math.random()*boardSize);

        //Create some randomly spread coins on game board
        generateCoins(coinsToCollect);

        //Update stats
        stats.levelUp();

        //Reset positions of player
        resetCharacterPosition(player);

        //Reset position of ghosts
        resetCharacterPosition(pinky);
        resetCharacterPosition(clyde);
        resetCharacterPosition(blinky);

        //Sets player's animation of death
        player.playerAnimator.setState(Player.PlayerAnimator.State.HAPPY);

        //Little break to slow down the tempo
        player.switchMovementActive();

        //Redraw whole board
        drawWholeBoard();
    }

    private void generateMaze(){

        if (boardSize == 10){
            //Create box pattern for 10x10 board
            ArrayList<Box> boxes = new ArrayList();

            boxes.add(new Box(1,1));
            boxes.add(new Box(3,1));
            boxes.add(new Box(4,1));
            boxes.add(new Box(5,1));
            boxes.add(new Box(6,1));
            boxes.add(new Box(8,1));

            boxes.add(new Box(1,2));
            boxes.add(new Box(3,2));
            boxes.add(new Box(8,2));

            boxes.add(new Box(3,3));
            boxes.add(new Box(5,3));
            boxes.add(new Box(6,3));
            boxes.add(new Box(8,3));

            boxes.add(new Box(1,4));

            boxes.add(new Box(1,5));
            boxes.add(new Box(2,5));
            boxes.add(new Box(6,5));

            boxes.add(new Box(5,6));
            boxes.add(new Box(6,6));
            boxes.add(new Box(7,6));

            boxes.add(new Box(0,7));
            boxes.add(new Box(2,7));
            boxes.add(new Box(3,7));

            boxes.add(new Box(0,8));
            boxes.add(new Box(3,8));
            boxes.add(new Box(6,8));
            boxes.add(new Box(8,8));

            for (Box b : boxes){
                staticFigData[b.getYPos()][b.getXPos()] = b;
            }
        }else{
            generateRandomMaze();
        }
    }

    private void generateRandomMaze(){

        //Firstly fill all the board with boxes
        //Leave space only on the edges of the board
        for (int row = 1; row < boardSize - 1; row++){

            for (int col = 2; col < boardSize - 2; col++){

                staticFigData[row][col] = new Box(col,row);
            }
        }

        //Next carve a net
        int frequency = 7;
        for (int row = frequency; row < boardSize; row+=frequency){
            for (int col = 0; col < boardSize; col++){
                staticFigData[row][col] = new Space(col,row);
                staticFigData[col][row] = new Space(col, row);
            }
        }

        //Next carve out a path horizontally 1st time
        carvePath(boardSize/2, 0);
        //horizontal again 2nd time
        carvePath(boardSize, 0);
        //horizontal for the  3rd time
        carvePath(boardSize, 0);

        //Carve out a path vertically 1st time
        carvePath(boardSize/2, 1);
        //vertically again for the 2nd time
        carvePath(0, 1);
        //vertically for the 3rd time
        carvePath(boardSize, 1);

        // Update tracker to work on new maze
        this.tracker = new Tracker(this);

    }

    public void carvePath(int swerve, int rotate){

        System.out.println("---CARVING PATH---");

        //Tier is a row or column of a board
        //It depends on the value of rotation
        //if rotate is true 'tier' is equivalent to 'column'
        //if rotate is false 'tier' is equivalent to 'row'


        int tier = 1;
        int limit = boardSize - 1;

        while(tier < limit){


            //Shift - direction of path
            int tierShift = carvingShift();
            int swerveShift = carvingShift();

            if (tierShift == 0){

                swerve += swerveShift;
            } else {

                tier += tierShift;
            }

            //Decrement or increment second coordinate to match board size
            while (swerve < 0)swerve++;
            while (swerve >= boardSize)swerve--;

            while (tier < 0)tier++;


            //If rotate is true then we carve the path vertically
            if (rotate % 2 == 1){
                staticFigData[swerve][tier] = new Space(swerve,tier);
                System.out.println("Row : "+swerve + " Col : " + tier);

            }
            //Else if rotate is false we carve path horizontally
            else {
                staticFigData[tier][swerve]  = new Space(tier,swerve);;
                System.out.println("Row : "+tier + " Col : " + swerve);
            }
        }
    }

    private int carvingShift(){

        //produce a random generated shift that determines
        //direction of the craving path
        int randomShift = (int)(Math.random()*4);

        switch (randomShift){
            case 0 -> randomShift = 0;
            case 1 -> randomShift = 1;
            case 2 -> randomShift = -1;
            case 3 -> randomShift = 1;

        }

        return randomShift;
    }

    public void generateCoins(int amount){

        //An array that keeps coordinates of free squares
        ArrayList<int[]> freeSpaces = new ArrayList<>();

        //Collecting coordinates of free squares on map
        for (int y = 0; y < boardSize; y++){

            for (int x = 0; x < boardSize; x++){

                if (staticFigData[y][x] instanceof Space){
                    int[] tmp = {y,x};
                    freeSpaces.add(tmp);
                }

            }
        }

        //Prevents an attempt of placing more coins on board than free space
        if (amount > freeSpaces.size()){
            amount = freeSpaces.size();
        }

        //Placing set amount of coins in random locations that are accessible
        for (int c = 0; c < amount; c++){

            int randomIndex = (int)(Math.random()*freeSpaces.size());
            int randomPosY = freeSpaces.get(randomIndex)[0];
            int randomPosX = freeSpaces.get(randomIndex)[1];

            Coin coin = new Coin(randomPosX,randomPosY);
            staticFigData[coin.getYPos()][coin.getXPos()] = coin;

            freeSpaces.remove(randomIndex);
        }

    }

    public void generatePowerUp(int x, int y){

        //If the power up is about to show up where the coin is placed, score the coin
        checkCoinCollision(x,y);

        //Create new PowerUp
        PowerUp newPowerUp = new PowerUp(this,stats,x,y);

        //Put it to the table
        staticFigData[newPowerUp.getYPos()][newPowerUp.getXPos()] = newPowerUp;

    }

    public boolean movePlayer(int newX, int newY){

        clyde.setMovementActive(true);
        pinky.setMovementActive(true);
        blinky.setMovementActive(true);

        //Move only if it's allowed
        if (checkPlayerCollision(newX,newY)){

            //Clearing previous square

            //Logic board figures data table

            int previousY = player.getYPos();
            int previousX = player.getXPos();

            dynamicFigData[previousY][previousX] = new Space(previousX,previousY);

            //Visualisation firstly paint dynamic layer as Space
            //because character presumably has changed his position
            //afterwards paint static layer for example hovered coin
            //player collects coin but adding drawBoardFigure(STATIC) may help me spot some bugs
            drawBoardFigure(previousY,previousX,FigureType.DYNAMIC);
            drawBoardFigure(previousY,previousX,FigureType.STATIC);

            //Setting new coordinates
            player.setXPos(newX);
            player.setYPos(newY);

            //Updating new coordinates of character in board figures logic table
            dynamicFigData[player.getYPos()][player.getXPos()] = player;

            //Coin collision code is placed here
            // Because I want coin collision to be checked after the move not before
            checkCoinCollision(newX,newY);

            //Power up collision code is placed here
            // Because I want power up collision to be checked after the move not before
            checkPowerUpCollision(newX,newY);

            return true;

        }
        return false;

    }

    private void checkCoinCollision(int newX, int newY){

        if (staticFigData[newY][newX] instanceof Coin){

            //Collision with coin is equivalent to collecting coin
            scorePoint(newY,newX);
            //Coin collision does not return true in case there is a ghost hovering the same square where coin is placed

            //Turn off powers
            PowerUp.disableSpeedPower(player);
        }
    }

    private void checkPowerUpCollision(int newX, int newY){
        if (staticFigData[newY][newX] instanceof PowerUp){

            //Collision with coin is equivalent to earning the power up
            earnPowerUp(newY,newX);

        }
    }

    private void earnPowerUp(int powerUpY , int powerUpX){

        //Disabling previous power-ups
        PowerUp.disableAllPowers(player);

        //Variable
        PowerUp collectedPowerUp = (PowerUp) staticFigData[powerUpY][powerUpX];

        //Removes power up from logic data table
        staticFigData[powerUpY][powerUpX] = new Space(powerUpY,powerUpX);

        //Set power
        collectedPowerUp.rollPower(player);

        //Just in case, repaint
        drawBoardFigure(powerUpY,powerUpX,FigureType.STATIC);

    }

    public void moveGhost(Ghost ghost, int newX, int newY){

        if (checkGhostCollision(newX,newY)){

            //Clearing previous square

            //Logic board figures data table
            int previousY = ghost.getYPos();
            int previousX = ghost.getXPos();

            dynamicFigData[previousY][ghost.getXPos()] = new Space(previousX,previousY);

            //Visualisation firstly paint dynamic layer as Space
            //because character presumably has changed his position
            //afterwards paint static layer for example hovered coin
            drawBoardFigure(previousY,previousX,FigureType.DYNAMIC);
            drawBoardFigure(previousY,previousX,FigureType.STATIC);

            //Setting new coordinates of ghost
            ghost.setXPos(newX);
            ghost.setYPos(newY);

            //Updating coordinates of ghost in board figures logic table
            dynamicFigData[ghost.getYPos()][ghost.getXPos()] = ghost;
        }

    }

    private boolean checkPlayerCollision(int newX, int newY){

        //Setting default animation-state
        player.playerAnimator.setState(Player.PlayerAnimator.State.DEFAULT);

        //Checking possible collision with every kind of object that can be found on board:

        //Edge collision
        if (newX >= boardSize || newX < 0 || newY >= boardSize || newY < 0){

            //Edges block the player
            return false;
        }

        //Box collision
        if (staticFigData[newY][newX] instanceof Box && (!informBoxes)){

            //Box simply blocks player
            return false;
        }

        //Ghost collision
        if (dynamicFigData[newY][newX] instanceof Ghost){

            //If player collides with ghost it's loosing hp-point and resets the board
            loseALife();
            return false;
        }

        //Power-Up collision

        //Finally returns true if collision does not interfere with movement
        return true;

    }

    private boolean checkGhostCollision(int newX, int newY){

        //Edge collision
        if (newX >= boardSize || newX < 0 || newY >= boardSize || newY < 0){

            //Edges block the player
            return false;
        }

        //Box collision
        if (staticFigData[newY][newX] instanceof Box){

            //Just to prevent some bugs, box will block ghost's aswell
            return false;
        }

        //Colliding with player
        if (dynamicFigData[newY][newX] instanceof Player){

            //Ghost-Player collision is equivalent to Player-Ghost collision
            loseALife();
            return false;
        }

        return true;
    }

    private void scorePoint(int coinY, int coinX){

        //Sets player's animation of eating
        player.playerAnimator.setState(Player.PlayerAnimator.State.ACTION);

        //Removes coin from logic data table
        staticFigData[coinY][coinX] = new Space(coinX,coinY);

        //Increment amount of coins collected on this level
        this.coinsCollected++;

        //Updates score in game stats
        stats.scorePoint(scoreMultiplier);

        //Just in case, repaint
        drawBoardFigure(coinY,coinX,FigureType.STATIC);

        //Levels up in case it was last coin
        if (coinsToCollect == coinsCollected){
            newLevel();
        }
    }

    private void loseALife(){

        //Turn off powers
        PowerUp.disableAllPowers(player);

        //Removes all power-ups
        removeAllPowerUps();

        //Disables movement of ghosts
        clyde.setMovementActive(false);
        pinky.setMovementActive(false);
        blinky.setMovementActive(false);

        //Sets player's animation of death
        player.playerAnimator.setState(Player.PlayerAnimator.State.DEAD);

        //Lose life point in game stats
        stats.loseALife();

        //Reset position of player
        resetCharacterPosition(player);

        //Reset position of ghosts
        resetCharacterPosition(pinky);
        resetCharacterPosition(clyde);
        resetCharacterPosition(blinky);

        //Blocks movement for short period to slow the tempo of gameplay
        player.switchMovementActive();

        if(stats.getLivesCount() == 0){
            gameOver();
        }

    }

    private void resetCharacterPosition(Character character){

        //Clearing previous square in logic figures data
        dynamicFigData[character.getYPos()][character.getXPos()] = new Space(character.getXPos(),character.getYPos());

        //Getting character starter coordinates
        int newX = character.getStartLocationX();
        int newY = character.getStartLocationY();

        //Set character at his start location
        character.setXPos(newX);
        character.setYPos(newY);

        //Update character's coordinates in logic data table
        dynamicFigData[character.getYPos()][character.getXPos()] = character;

        //Repaint of whole board
        drawWholeBoard();
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }

    private void initializeTable(){

        //Set my custom table model
        this.setModel(new myTableModel(new ImageIcon[boardSize][boardSize], new String[boardSize]));

        //Disable cell selection
        this.setCellSelectionEnabled(false);


        //Draw lines, I think they look cool
        this.showHorizontalLines = true;
        this.showVerticalLines = true;

        //Set rows size
        this.rowHeight = getCellSize();


    }

    public void drawWholeBoard(){

        //Iterates through every row and column
        //to repaint every figure

        for (int row = 0; row < boardSize; row++){
            for (int e = 0; e < boardSize; e++){

                //displaying static figures
                drawBoardFigure(row,e,FigureType.STATIC);
                //rescale
                staticFigData[row][e].setScalar(CELL_SIZE);


                //displaying dynamic figures
                drawBoardFigure(row,e,FigureType.DYNAMIC);
                //Rescale
                dynamicFigData[row][e].setScalar(CELL_SIZE);

            }
        }

    }

    public void drawBoardFigure(int y, int x, FigureType type){


        //Setting image icon to null as default
        ImageIcon image;

        //Firstly paint static content
        if (type == FigureType.STATIC){

            image = staticFigData[y][x].getImage();

            this.setValueAt(image, y, x);

        //Secondly paint dynamic content
        } else if (type == FigureType.DYNAMIC){

            image = dynamicFigData[y][x].getImage();

            //do not paint space as dynamic figures
            if (image != null)
                this.setValueAt(image, y, x);
        }

    }

    private void removeAllPowerUps(){

        //Removes all power ups
        for (int row = 0; row < boardSize; row++){
            for (int e = 0; e < boardSize; e++){

                if (staticFigData[row][e] instanceof PowerUp){

                    staticFigData[row][e] = new Space(e,row);
                }

            }
        }

    }

    //TMP
    private void selectedListener(){
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println(
//                        "--------------------------------\n"+
//                        "row : " + getSelectedRow() + "\n" +
//                        "column : " + getSelectedColumn()
//                );
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {



            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void gameOver(){

        new GameOverWindow(stats.getCoinsCount(),stats.getLevelCount());
        window.dispose();
    }

    @Override
    public void run() {
        gameLoop();
    }

    public static void setScoreMultiplier(int scoreMultiplier) {
        Board.scoreMultiplier = scoreMultiplier;
    }

    public static int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public void setInformBoxes(boolean informBoxes) {
        this.informBoxes = informBoxes;
    }

    public BoardFigure[][] getStaticFigData() {
        return staticFigData;
    }

    public BoardFigure[][] getDynamicFigData() {
        return dynamicFigData;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getPacManX() {
        return this.player.getXPos();
    }

    public int getPacManY() {
        return this.player.getYPos();
    }

    public Tracker getTracker() {
        return tracker;
    }
}
