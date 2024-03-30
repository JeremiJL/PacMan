package jeremi.pacman.GamePlay.Figures.Static;

import javax.swing.*;

public class Box extends Inanimate {

    //Box default Image
    private static ImageIcon IMAGE = new ImageIcon("Graphics/Box.png");

    public Box(int xPos, int yPos) {
        super(xPos, yPos, IMAGE);
    }
}
