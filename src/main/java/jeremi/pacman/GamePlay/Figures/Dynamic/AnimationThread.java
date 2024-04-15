package jeremi.pacman.GamePlay.Figures.Dynamic;

import javax.swing.*;

public class AnimationThread implements Runnable {

    //Object of animation
    private Character character;

    //Table contains default animation frames
    protected ImageIcon[] frames;

    //Actual animation-state of character
    private ImageIcon[] myState;

    //Period that every frame of animation will take, in milliseconds
    private int ANIMATION_TIME_INTERVAL; //ms

    public AnimationThread(Character character, int animationInterval, ImageIcon[] frames) {
        //Assign object of animation
        this.character = character;
        //Animation preferences
        this.ANIMATION_TIME_INTERVAL = animationInterval;
        //Default frames of animation
        this.frames = frames;
        //Starter animation-state
        setState(frames);
    }

    public void setState(ImageIcon[] state) {
        this.myState = state;
    }

    public void animate(ImageIcon[] frames) {

        //Iterates over the table of Images and displays them in loop
        for (ImageIcon frame : frames) {

            character.setImage(frame);

            //If movement thread is off, we should repaint character by ourselves
            if (!character.movementActive) {
                character.updateImage();
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
            animate(myState);
        }
    }


}
