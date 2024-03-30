package jeremi.pacman.GamePlay.Figures.Dynamic;

import jeremi.pacman.GamePlay.Board;

import javax.swing.*;

public abstract class Ghost extends Character{

    public Ghost(Board board, int xPos, int yPos, ImageIcon image) {

        super(board, xPos, yPos, image);

        //MTI - Movement Time Interval, represents speed of character
        //value of MTI is the number of milliseconds that character
        //hast to spend on single cell before going to the next one
        setMTI(500);

        System.out.println(
                "X:" + getStartLocationX() +
                "Y:" + getStartLocationY()
        );
    }

    //Every ghost should implement his own way of moving on the board
    protected abstract void move();

    //Sets character image (ImageIcon)
    public void setImage(ImageIcon image) {
        super.setImage(image);
    }

    @Override
    public void run() {

        while (true){

            move();
            updateImage();

            try {
                Thread.sleep(getMTI());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


    }
}
