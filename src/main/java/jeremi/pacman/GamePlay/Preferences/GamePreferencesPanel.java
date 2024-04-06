package jeremi.pacman.GamePlay.Preferences;

import jeremi.pacman.AbstractContentPanel;
import jeremi.pacman.GamePlay.GamePlayWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePreferencesPanel extends AbstractContentPanel {


    //Labels
    private JLabel labelValueOfSlider;

    //Slider
    private JSlider sliderBoardSize;

    //Button
    private JButton buttonStart;

    public GamePreferencesPanel(JFrame window){

        super(window);
        setTitle("PREFERENCES");

    }

    @Override
    protected void initializeAttributes() {
        //Attribute Panel - contains slider enabling you to choose board size

        this.panelAttributes = new JPanel(new GridLayout(3,1));
        this.add(panelAttributes,BorderLayout.CENTER);

        //Initialize  components
        initializeSliders();
        initializeLabels();
        initializeButtons();
        setListeners();
    }

    private void initializeLabels(){

        //Value of slider label
        this.labelValueOfSlider = new JLabel("Board Size : " + this.sliderBoardSize.getValue(),SwingConstants.CENTER);
        this.labelValueOfSlider.setFont(new Font("Consolas",Font.PLAIN,30));
        this.panelAttributes.add(labelValueOfSlider);

    }

    private void initializeSliders(){

        //Board size slider
        this.sliderBoardSize = new JSlider();
        this.panelAttributes.add(sliderBoardSize);

        //Maximum and minimum values
        this.sliderBoardSize.setMinimum(10);
        this.sliderBoardSize.setMaximum(100);

        //Default value
        this.sliderBoardSize.setValue(10);

    }

    private void initializeButtons(){

        //Start button
        this.buttonStart = new JButton("Start");
        this.buttonStart.setFont(new Font("Consolas",Font.PLAIN,30));
        this.panelAttributes.add(buttonStart);

    }

    protected void setListeners(){

        //Slider listener
        this.sliderBoardSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = sliderBoardSize.getValue();

                if (value > 20){
                    labelValueOfSlider.setText("Board Size : " + sliderBoardSize.getValue() + " (Not recommended)");
                }else {
                    labelValueOfSlider.setText("Board Size : " + sliderBoardSize.getValue());
                }
            }
        });

        //New game listener
        this.buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                new GamePlayWindow(sliderBoardSize.getValue());
            }
        });

    }


}
