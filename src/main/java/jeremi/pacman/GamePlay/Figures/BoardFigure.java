package jeremi.pacman.GamePlay.Figures;

import jeremi.pacman.GamePlay.Board;

import javax.swing.*;
import java.awt.*;

public abstract class BoardFigure {

    private int xPos;
    private int yPos;

    private ImageIcon image;
    private int scalar;

    public BoardFigure(int xPos, int yPos, ImageIcon image) {

        this.xPos = xPos;
        this.yPos = yPos;

        this.scalar = Board.getCellSize();
        this.image = scaleImage(image);

    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon imageToSet) {
        this.image = scaleImage(imageToSet);
    }

    public void setScalar(int value){
        this.scalar = value;
    }

    public /*synchronized */void setXPos(int value){
        this.xPos = value;
    }

    public /*synchronized*/ void setYPos(int value){
        this.yPos = value;
    }

    public /*synchronized*/ int getXPos() {
        return xPos;
    }

    public /*synchronized*/ int getYPos() {
        return yPos;
    }

    private ImageIcon scaleImage(ImageIcon imageToScale){
        return new ImageIcon(imageToScale.getImage().getScaledInstance(this.scalar,this.scalar, Image.SCALE_DEFAULT));
    }
}
