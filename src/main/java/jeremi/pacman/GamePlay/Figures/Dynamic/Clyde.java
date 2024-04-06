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

    //Enables / disables ghosts movement
    private boolean movementActive;

    //Movement
    private ClydeTracking clydeTracking;

    // Movement speed
    int movementSpeed = 110;

    //Next sequence of moves
    private Queue<Square> trail = new LinkedList<>();

    public Clyde(Board board, int xPos, int yPos) {
        super(board, xPos, yPos,DEFAULT_IMAGE);

        //As default
        this.movementActive = false;

        //Movement speed of clyde
        setMTI(movementSpeed);

        //Assignment
        this.clydeAnimator = new ClydeAnimator();

        //Starting Animation Thread
        this.animationThread = new Thread(this.clydeAnimator);
        this.animationThread.start();

        //Start tracking thread
        this.clydeTracking = new ClydeTracking();
        this.clydeTracking.start();
    }

    public void setMovementActive(boolean movementActive) {
        this.movementActive = movementActive;
    }

    //Moving along tracking algorithm
    protected void move(){

        if (movementActive){

            if (!trail.isEmpty()){
                Square square = trail.poll();
                System.out.println(square);
                int newX = square.x;
                int newY = square.y;

                board.moveGhost(this,newX,newY);
            }
        }
    }

    class ClydeTracking extends Thread{

        @Override
        public void run() {
            while(true) {
                try {

                    // Compute next moves if clyde can move
                    if (movementActive && trail.isEmpty()){
                        trail = Tracker.convertStackToQueue(board.getTracker().sniff(getXPos(), getYPos(), board.getPacManX(), board.getPacManY()));
                    }
                    // If clyde can not move clear the trail
                    else {
                        trail.clear();
                    }

                    Thread.sleep(5_000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Clyde";
    }

    public class ClydeAnimator implements Runnable {

        //Table contains default animation frames, in case of Ghost it's just looking around and simple animation of 'hovering'
        protected static ImageIcon[] DEFAULT_FRAMES = new ImageIcon[]{
                new ImageIcon("Graphics/Ghosts/Clyde/ClydeDefault1.png"),
                new ImageIcon("Graphics/Ghosts/Clyde/ClydeDefault2.png"),
                new ImageIcon("Graphics/Ghosts/Clyde/ClydeDefault3.png"),
                new ImageIcon("Graphics/Ghosts/Clyde/ClydeDefault4.png")
        };

        private static ImageIcon[] ACTION_FRAMES = null;

        private static ImageIcon[] DEAD_FRAMES = null;

        //Role of this enum is to make code more clear
        //State corresponds with ImageIcon tables
        public enum State {

            DEFAULT(DEFAULT_FRAMES);

            public ImageIcon[] framesTab;

            State(ImageIcon[] frames) {
                this.framesTab = frames;
            }

        }

        //Actual animation-state of character
        private State myState;

        //Period that every frame of animation will take, in milliseconds
        private static int ANIMATION_TIME_INTERVAL = 200; //ms

        public ClydeAnimator() {
            //Starter animation-state
            setState(State.DEFAULT);
        }

        public void setState(State state) {
            this.myState = state;
        }

        public void animate(ImageIcon[] frames) {

            //Iterates over the table of Images and displays them in loop
            for (ImageIcon frame : frames) {

                setImage(frame);

                //Sleeps for the period that frame should take
                try {
                    Thread.sleep(ANIMATION_TIME_INTERVAL);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }

        @Override
        public void run() {

            //Animation in the loop
            while (true) {

                animate(myState.framesTab);

            }

        }


    }

}
