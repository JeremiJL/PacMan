package jeremi.pacman.Options;

import jeremi.pacman.AbstractWindow;


public class OptionsWindow extends AbstractWindow {

    public OptionsWindow(){

        initializeFrame();

        OptionsPanel optionsPanel = new OptionsPanel(this);
        this.setContentPane(optionsPanel);

    }

}
