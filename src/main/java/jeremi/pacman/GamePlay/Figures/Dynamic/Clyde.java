package jeremi.pacman.GamePlay.Figures.Dynamic;

import jeremi.pacman.GamePlay.Board;

import javax.swing.*;

public class Clyde extends Ghost {

    //Clyde default Image
    private static ImageIcon DEFAULT_IMAGE = new ImageIcon("Graphics/Ghosts/Clyde/ClydeDefault1.png");

    //Animator class handles animation it's a Thread
    public ClydeAnimator clydeAnimator;
    private Thread animationThread;

    //Enables / disables ghosts movement
    private boolean movementActive;

    //Movement
    private int shiftX = 0;
    private int shiftY = 0;
    private int randomVal = 0;

    //Default and current movement speed
    private int defaultMovementSpeed = 800;
    private int currentMovementSpeed = defaultMovementSpeed;

    public Clyde(Board board, int xPos, int yPos) {
        super(board, xPos, yPos,DEFAULT_IMAGE);

        //As default
        this.movementActive = true;

        //Movement speed of clyde
        setMTI(defaultMovementSpeed);

        //Assignment
        this.clydeAnimator = new ClydeAnimator();

        //Starting Animation Thread
        this.animationThread = new Thread(this.clydeAnimator);
        this.animationThread.start();
    }

    public void setMovementActive(boolean movementActive) {
        this.movementActive = movementActive;
    }

    //Moving in random directions
    protected void move(){

        if(movementActive){

            shiftX = 0;
            shiftY = 0;

            randomVal = (int)(Math.random()*4);

            switch(randomVal){
                case 0 :
                    shiftX++;
                    break;
                case 1 :
                    shiftY++;
                    accelerateMovementSpeed(10);
                    break;
                case 2 :
                    shiftX-- ;
                    accelerateMovementSpeed(10);
                    break;
                case 3 :
                    shiftY--;
                    break;
            }

            int newX = getXPos() + shiftX;
            int newY = getYPos() + shiftY;

            board.moveGhost(this,newX,newY);

        }else {
            System.out.println("CLYDE - MOVEMENT NOT ACTIVE");
            currentMovementSpeed = defaultMovementSpeed;
            setMTI(currentMovementSpeed);
        }

    }

    private void accelerateMovementSpeed(int value){

        if (currentMovementSpeed > 100){
            currentMovementSpeed-=value;
        }

        setMTI(currentMovementSpeed);

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
        private static int ANIMATION_TIME_INTERVAL = 400; //ms

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
