package jeremi.pacman.GamePlay.Figures.Dynamic;

import jeremi.pacman.GamePlay.Board;
import jeremi.pacman.GamePlay.Tracker;
import jeremi.pacman.GamePlay.Tracker.Square;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class Clyde extends Ghost {

    //Clyde default Image
    private static ImageIcon DEFAULT_IMAGE = new ImageIcon("Graphics/Ghosts/Clyde/ClydeDefault1.png");

    //Animator class handles animation it's a Thread
    public ClydeAnimator clydeAnimator;
    private Thread animationThread;

    //Movement
    private MotionThread clydeMotion;

    // Movement speed
    private int movementSpeed = 110;

    // Regeneration between consecutive hunts
    private int regeneration = 5_000;

    public Clyde(Board board, int xPos, int yPos) {
        super(board, xPos, yPos,DEFAULT_IMAGE);

        //As default
        this.movementActive = false;

        //Initialize trail
        this.trail = new LinkedList<>();

        //Movement speed of clyde
        setMTI(movementSpeed);

        //Assignment
        this.clydeAnimator = new ClydeAnimator();

        //Starting Animation Thread
        this.animationThread = new Thread(this.clydeAnimator);
        this.animationThread.start();

        //Start tracking thread
        this.clydeMotion = new MotionThread(this,board.getTracker(),this.regeneration);
        this.clydeMotion.start();
    }

    public void setMovementActive(boolean movementActive) {
        this.movementActive = movementActive;
    }

    //Moving along tracking algorithm
    protected void move(){
        clydeMotion.move();
    }

    @Override
    public String toString() {
        return "Clyde";
    }


    }

}
