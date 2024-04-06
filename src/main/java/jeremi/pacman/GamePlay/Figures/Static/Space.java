package jeremi.pacman.GamePlay.Figures.Static;

import javax.swing.*;

public class Space extends Inanimate{

    //Empty space on board
    private static ImageIcon IMAGE  = null;

    public Space(int xPos, int yPos) {
        super(xPos, yPos, IMAGE);
    }

}
