package jeremi.pacman;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractContentPanel extends JPanel {

    //Window
    protected JFrame window;

    //Panel
    protected JPanel panelAttributes;

    //Labels - Title and Credits
    private JLabel labelTitle;
    private JLabel labelCredits;

    public AbstractContentPanel(JFrame window){

        //Window assignment
        this.window = window;

        //Setting panel primary layout
        this.setLayout(new BorderLayout());

        //Components initialization
        initializeTitleCredit();
        initializeSpace();
        initializeAttributes();
    }

    protected abstract void initializeAttributes();

    protected abstract void setListeners();

    private void initializeTitleCredit(){

        //Title label
        this.labelTitle = new JLabel("NULL",SwingConstants.CENTER);
        this.labelTitle.setFont(Constants.getFont(Constants.FONT_TYPE.LARGE_FONT));
        this.labelTitle.setPreferredSize(Constants.getDimension(Constants.COMPONENT_TYPE.LABEL));
        this.add(labelTitle,BorderLayout.PAGE_START);

        //Credits label
        this.labelCredits = new JLabel("PJATK - Jeremi Lipniacki",SwingConstants.CENTER);
        this.labelCredits.setFont(Constants.getFont(Constants.FONT_TYPE.TINY_FONT));
        this.labelCredits.setPreferredSize(Constants.getDimension(Constants.COMPONENT_TYPE.LABEL));
        this.add(labelCredits,BorderLayout.PAGE_END);

    }

    private void initializeSpace(){

        //Space
        JLabel s1 = new JLabel(" ");
        s1.setPreferredSize(Constants.getDimension(Constants.COMPONENT_TYPE.SPACE));

        JLabel s2 = new JLabel(" ");
        s2.setPreferredSize(Constants.getDimension(Constants.COMPONENT_TYPE.SPACE));

        this.add(s1,BorderLayout.LINE_END);
        this.add(s2,BorderLayout.LINE_START);

    }

    protected void setTitle(String title){
        this.labelTitle.setText(title);
    }

}
