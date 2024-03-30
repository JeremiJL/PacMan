package jeremi.pacman.GamePlay.Figures.Static;

import javax.swing.*;

public class Coin extends Inanimate {

    //Coin default Image
    private static ImageIcon IMAGE = new ImageIcon("Graphics/Coin.png");

    public Coin(int xPos, int yPos) {
        super(xPos, yPos, IMAGE);
    }
}
