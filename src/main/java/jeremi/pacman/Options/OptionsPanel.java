package jeremi.pacman.Options;

import jeremi.pacman.AbstractContentPanel;
import jeremi.pacman.Constants;
import jeremi.pacman.Menu.MainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends AbstractContentPanel {

    //Panel
    private JPanel panelButtonsSectionExit;
    private JPanel panelRadioSectionDim;
    private JPanel panelRadioSectionKeys;

    //ButtonsGroups
    private ButtonGroup groupRadioDim;
    private ButtonGroup groupRadioKeys;

    //RadioButtons
    private JRadioButton radioSetDim1;
    private JRadioButton radioSetDim2;
    private JRadioButton radioSetDim3;
    private JRadioButton radioSetDim4;
    private JRadioButton[] tabRadioDim;

    private JRadioButton radioSetKeys1;
    private JRadioButton radioSetKeys2;


    //Buttons
    private JButton buttonApply;
    private JButton buttonExit;

    public OptionsPanel(JFrame window) {
        super(window);
        setTitle("OPTIONS");
    }


    @Override
    protected void initializeAttributes() {
        //Attributes Panel - Panel for (radio) buttons
        this.panelAttributes = new JPanel(new GridLayout(3,1));
        this.add(panelAttributes,BorderLayout.CENTER);

        //Initialize components
        initializeSections();
        initializeRadioButtons();
        initializeButtons();

        //ActionListeners
        setListeners();
    }

    private void initializeSections(){
        //Adding inner panel containing sections of options
        this.panelButtonsSectionExit = new JPanel(new FlowLayout());
        this.panelRadioSectionDim = new JPanel(new FlowLayout());
        this.panelRadioSectionKeys = new JPanel(new FlowLayout());

        this.panelAttributes.add(panelRadioSectionDim);
        this.panelAttributes.add(panelRadioSectionKeys);
        this.panelAttributes.add(panelButtonsSectionExit);

    }

    private void initializeRadioButtons(){

        //Section of choosing window dimensions

        this.groupRadioDim = new ButtonGroup();

        //Setting text representing certain option
        String textDim1 = " " + Constants.POSSIBLE_DIMENSIONS[0][0] + " x " + Constants.POSSIBLE_DIMENSIONS[0][1] + " ";
        String textDim2 = " " + Constants.POSSIBLE_DIMENSIONS[1][0] + " x " + Constants.POSSIBLE_DIMENSIONS[1][1] + " ";
        String textDim3 = " " + Constants.POSSIBLE_DIMENSIONS[2][0] + " x " + Constants.POSSIBLE_DIMENSIONS[2][1] + " ";
        String textDim4 = " " + Constants.POSSIBLE_DIMENSIONS[3][0] + " x " + Constants.POSSIBLE_DIMENSIONS[3][1] + " ";

        //Creating individual radio buttons
        this.radioSetDim1 = new JRadioButton(textDim1);
        this.radioSetDim1.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.radioSetDim1.setSelected(false);

        this.radioSetDim2 = new JRadioButton(textDim2);
        this.radioSetDim2.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.radioSetDim2.setSelected(false);

        this.radioSetDim3 = new JRadioButton(textDim3);
        this.radioSetDim3.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.radioSetDim3.setSelected(false);

        this.radioSetDim4 = new JRadioButton(textDim4);
        this.radioSetDim4.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.radioSetDim4.setSelected(false);

        //Inserting radio buttons into an array in order to make iteration possible
        tabRadioDim = new JRadioButton[]{radioSetDim1, radioSetDim2, radioSetDim3, radioSetDim4};

        //Set radio button selected corresponding to actual frame width
        setRadioDimActive();

        //Grouping radio buttons
        groupRadioDim.add(radioSetDim1);
        groupRadioDim.add(radioSetDim2);
        groupRadioDim.add(radioSetDim3);
        groupRadioDim.add(radioSetDim4);

        //Adding them to panel
        panelRadioSectionDim.add(radioSetDim1);
        panelRadioSectionDim.add(radioSetDim2);
        panelRadioSectionDim.add(radioSetDim3);
        panelRadioSectionDim.add(radioSetDim4);


        //Section of choosing set of keys responsible for moving PacMan
        this.groupRadioKeys = new ButtonGroup();

        //Setting text representing certain option
        String textKeys1 = "Arrows";
        String textKeys2 = "WASD";

        //Creating individual radio buttons
        this.radioSetKeys1 = new JRadioButton(textKeys1);
        this.radioSetKeys1.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.radioSetKeys1.setSelected(false);

        this.radioSetKeys2 = new JRadioButton(textKeys2);
        this.radioSetKeys2.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.radioSetKeys2.setSelected(false);

        setRadioKeysActive();

        //Grouping radio buttons
        groupRadioKeys.add(radioSetKeys1);
        groupRadioKeys.add(radioSetKeys2);

        //Adding to panel
        panelRadioSectionKeys.add(radioSetKeys1);
        panelRadioSectionKeys.add(radioSetKeys2);

    }

    private void setRadioDimActive(){

        for (int i = 0; i < Constants.POSSIBLE_DIMENSIONS.length; i++){
            if (Constants.FRAME_WIDTH == Constants.POSSIBLE_DIMENSIONS[i][0]){
                tabRadioDim[i].setSelected(true);
            }
        }

    }

    private void setRadioKeysActive(){

        if (Constants.keySetChosen.equals("wasd")){
            radioSetKeys1.setSelected(false);
            radioSetKeys2.setSelected(true);
        }
        else if (Constants.keySetChosen.equals("arrows")) {
            radioSetKeys1.setSelected(true);
            radioSetKeys2.setSelected(false);
        }

    }

    private void initializeButtons(){

        //Start button
        this.buttonApply = new JButton("Apply");
        this.buttonApply.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));
        this.buttonApply.setPreferredSize(Constants.getDimension(Constants.COMPONENT_TYPE.BUTTON));
        this.panelButtonsSectionExit.add(buttonApply);

        //Start button
        this.buttonExit = new JButton("Exit");
        this.buttonExit.setFont(Constants.getFont(Constants.FONT_TYPE.MEDIUM_FONT));
        this.buttonExit.setPreferredSize(Constants.getDimension(Constants.COMPONENT_TYPE.BUTTON));
        this.panelButtonsSectionExit.add(buttonExit);

    }

    protected void setListeners(){

        buttonApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //windows dimensions
                for (int i = 0; i < tabRadioDim.length; i++){

                    if (tabRadioDim[i].isSelected()){
                        Constants.refresh(i);
                    }

                }


                //gameplay keys-set
                if (radioSetKeys1.isSelected()){

                    Constants.keySetChosen = "arrows";

                    Constants.KEY_CODES.UP.codeValue = Constants.arrowsCodes[0];
                    Constants.KEY_CODES.LEFT.codeValue = Constants.arrowsCodes[1];
                    Constants.KEY_CODES.DOWN.codeValue = Constants.arrowsCodes[2];
                    Constants.KEY_CODES.RIGHT.codeValue = Constants.arrowsCodes[3];
                }

                if (radioSetKeys2.isSelected()){

                    Constants.keySetChosen = "wasd";

                    Constants.KEY_CODES.UP.codeValue = Constants.wasdCodes[0];
                    Constants.KEY_CODES.LEFT.codeValue = Constants.wasdCodes[1];
                    Constants.KEY_CODES.DOWN.codeValue = Constants.wasdCodes[2];
                    Constants.KEY_CODES.RIGHT.codeValue = Constants.wasdCodes[3];
                }

                window.dispose();
                new OptionsWindow();

            }
        });

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                window.dispose();
                new MainMenuWindow();

            }
        });

    }


}
