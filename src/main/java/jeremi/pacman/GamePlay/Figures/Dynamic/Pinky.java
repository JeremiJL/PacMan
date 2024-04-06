package jeremi.pacman.GamePlay.Figures.Dynamic;

import jeremi.pacman.GamePlay.Board;
import jeremi.pacman.GamePlay.Tracker;
import jeremi.pacman.GamePlay.Tracker.Square;

import javax.swing.*;
import java.util.Queue;

public class Pinky extends Ghost {

    //Inky default Image
    private static ImageIcon DEFAULT_IMAGE = new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault1.png");

    //Animator class handles animation it's a Thread
    public PinkyAnimator pinkyAnimator;
    private Thread animationThread;

    //Enables / disables ghosts movement
    private boolean movementActive;

    //Movement

    //Next sequence of moves
    private Queue<Square> trail;

    //Default movement speed
    private int defaultMovementSpeed = 800;

    public Pinky(Board board, int xPos, int yPos) {
        super(board, xPos, yPos,DEFAULT_IMAGE);

        //As default
        this.movementActive = true;

        //Movement speed of clyde
        setMTI(defaultMovementSpeed);

        //Assignment
        this.pinkyAnimator = new PinkyAnimator();

        //Starting Animation Thread
        this.animationThread = new Thread(this.pinkyAnimator);
        this.animationThread.start();

        // Compute next moves
        this.trail = Tracker.convertStackToQueue(board.getTracker().sniff(xPos,yPos,0,0));

        //test
        test();
    }

    public void setMovementActive(boolean movementActive) {
        this.movementActive = movementActive;
    }

    @Override
    public String toString() {
        return "Pinky";
    }

    //Moving in random directions
    protected void move(){

        if(movementActive){

            int newX = getXPos();
            int newY = getYPos();

            board.moveGhost(this,newX,newY);
        }
    }



    private void test(){
        while(!trail.isEmpty())
            System.out.println(trail.poll());

    }


    public class PinkyAnimator implements Runnable {

        //Table contains default animation frames, in case of Ghost it's just looking around and simple animation of 'hovering'
        protected static ImageIcon[] DEFAULT_FRAMES = new ImageIcon[]{
                new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault1.png"),
                new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault2.png"),
                new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault3.png"),
                new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault4.png")
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

        public PinkyAnimator() {
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
