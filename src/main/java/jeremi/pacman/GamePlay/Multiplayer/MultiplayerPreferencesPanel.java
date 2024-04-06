package jeremi.pacman.GamePlay.Multiplayer;

import jeremi.pacman.AbstractContentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiplayerPreferencesPanel extends AbstractContentPanel {

    //Buttons
    private JButton buttonHost;
    private JButton buttonJoin;

    public MultiplayerPreferencesPanel(JFrame window) {
        super(window);
        setTitle("PREFERENCES");
    }

    @Override
    protected void initializeAttributes() {

        //Attribute Panel - contains buttons enabling you to choose between hosting and joining hosted game
        this.panelAttributes = new JPanel(new GridLayout(2,1));
        this.add(panelAttributes,BorderLayout.CENTER);

        //Initialize  components
        initializeButtons();
        setListeners();
    }

    private void initializeButtons(){

        //Host button
        this.buttonHost = new JButton("Host");
        this.buttonHost.setFont(new Font("Consolas",Font.PLAIN,30));
        this.panelAttributes.add(buttonHost);

        //Join button
        this.buttonJoin = new JButton("Join");
        this.buttonJoin.setFont(new Font("Consolas",Font.PLAIN,30));
        this.panelAttributes.add(buttonJoin);

    }

    @Override
    protected void setListeners() {

        //Host button listener
        this.buttonHost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });


        //Join button listener
        this.buttonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });

    }
}
