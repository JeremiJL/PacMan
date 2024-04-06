package jeremi.pacman.GamePlay.Figures.Dynamic;

import jeremi.pacman.Constants;
import jeremi.pacman.GamePlay.Board;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Character {

    //Player default Image
    private static ImageIcon DEFAULT_IMAGE = new ImageIcon("Graphics/Player/PlayerDefault1.png");

    //Animator class handles animation it's a Thread
    public PlayerAnimator playerAnimator;
    private Thread animationThread;

    //Movement disables/enables character to move
    public boolean movementActive;

    //PlayerMovementListener listens for 'wasd' keys
    public PlayerMovementListener keyListener;

    //This values represent current X-shift and Y-shift
    private int GO_Y = 0, GO_X = 0;

    //Default movement speed
    private final int defaultSpeed = 104;

    public Player(Board board, int xPos, int yPos) {

        super(board, xPos, yPos, DEFAULT_IMAGE);

        //MTI - Movement Time Interval, represents speed of character
        //value of MTI is the number of milliseconds that character
        //hast to spend on single cell before going to the next one
        setMTI(defaultSpeed);

        //As default
        this.movementActive = true;

        //Assignment
        this.keyListener = new PlayerMovementListener();

        //Assignment
        this.playerAnimator = new PlayerAnimator();

        //Starting Animation Thread
        this.animationThread = new Thread(this.playerAnimator);
        this.animationThread.start();

    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public void move(int xShift, int yShift) {

        if (movementActive){
            //If the x-shift, y-shift values are non-zero
            if (xShift != 0 || yShift != 0) {

                //New coordinates of character after successful move
                int newX = getXPos() + xShift;
                int newY = getYPos() + yShift;

                //Board does rest of the job
                board.movePlayer(newX, newY);
            }
        }

    }

    @Override
    public String toString() {
        return "PacMan";
    }

    //Sets character image (ImageIcon)
    public void setImage(ImageIcon image) {
        super.setImage(image);
    }

    public void switchMovementActive() {

        //Blocks character movement
        //Used in case of hp point
        try {

            this.movementActive = false;//!this.movementActive;
            Thread.sleep(3_000);
            this.movementActive = true;//!this.movementActive;

            this.playerAnimator.setState(PlayerAnimator.State.DEFAULT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void run() {

        //While character is allowed to move :
        //Move then update graphics
        while (true) {

            //test - acceleration



                if((GO_X != 0 || GO_Y != 0 ) && getMTI() > (defaultSpeed - 20) ){
                    setMTI(getMTI() - 1);
                } else if (getMTI() < defaultSpeed) {
                    setMTI(getMTI() + 2);
                }

            //test - acceleration

            move(GO_X, GO_Y);
            updateImage();

            //Sleeps for a period to control the speed of characters
            try {
                Thread.sleep(getMTI());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public class PlayerAnimator implements Runnable {

        //Table contains default animation frames, in case of Player - nervous look
        protected static ImageIcon[] DEFAULT_FRAMES = new ImageIcon[]{
                new ImageIcon("Graphics/Player/PlayerDefault3.png"),
                new ImageIcon("Graphics/Player/PlayerDefault3.png"),
                new ImageIcon("Graphics/Player/PlayerDefault4.png"),
                new ImageIcon("Graphics/Player/PlayerDefault2.png"),
                new ImageIcon("Graphics/Player/PlayerDefault1.png"),
        };

        //table contains action animation frames, in case of Player - eating animation
        private static ImageIcon[] ACTION_FRAMES = new ImageIcon[]{
                new ImageIcon("Graphics/Player/PlayerEating1.png"),
                new ImageIcon("Graphics/Player/PlayerEating2.png"),
                new ImageIcon("Graphics/Player/PlayerEating3.png"),
                new ImageIcon("Graphics/Player/PlayerEating4.png"),
                new ImageIcon("Graphics/Player/PlayerEating5.png"),
        };

        //Table contains animation of character's death frames
        private static ImageIcon[] DEAD_FRAMES = new ImageIcon[]{
                new ImageIcon("Graphics/Player/PlayerDead1.png"),
                new ImageIcon("Graphics/Player/PlayerDead2.png"),
        };

        //Table contains animation of character's death frames
        private static ImageIcon[] HAPPY_FRAMES = new ImageIcon[]{
                new ImageIcon("Graphics/Player/PlayerHappy1.png"),
                new ImageIcon("Graphics/Player/PlayerHappy2.png"),
                new ImageIcon("Graphics/Player/PlayerHappy3.png"),
                new ImageIcon("Graphics/Player/PlayerHappy4.png"),
        };

        //Role of this enum is to make code more clear
        //State corresponds with ImageIcon tables
        public enum State {

            ACTION(ACTION_FRAMES), DEAD(DEAD_FRAMES), DEFAULT(DEFAULT_FRAMES),
            HAPPY(HAPPY_FRAMES);

            public ImageIcon[] framesTab;

            State(ImageIcon[] frames) {
                this.framesTab = frames;
            }

        }

        //Actual animation-state of character
        private State myState;

        //Period that every frame of animation will take, in milliseconds
        private static int ANIMATION_TIME_INTERVAL = 130; //ms

        public PlayerAnimator() {
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

                //If movement thread is off, we should repaint character by ourselves
                if (!movementActive) {
                    updateImage();
                }

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

    public class PlayerMovementListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Constants.KEY_CODES.UP.codeValue) {
                GO_Y = -1;
                GO_X = 0;
            } else if (key == Constants.KEY_CODES.DOWN.codeValue) {
                GO_Y = 1;
                GO_X = 0;
            } else if (key == Constants.KEY_CODES.LEFT.codeValue) {
                GO_X = -1;
                GO_Y = 0;
            } else if (key == Constants.KEY_CODES.RIGHT.codeValue) {
                GO_X = 1;
                GO_Y = 0;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Constants.KEY_CODES.UP.codeValue || key == Constants.KEY_CODES.DOWN.codeValue) {
                GO_Y = 0;
            } else if (key == Constants.KEY_CODES.LEFT.codeValue || key == Constants.KEY_CODES.RIGHT.codeValue) {
                GO_X = 0;
            }

        }


    }


}
