package jeremi.pacman.HighScore;

import jeremi.pacman.AbstractContentPanel;
import jeremi.pacman.Constants;
import jeremi.pacman.Menu.MainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class HighScorePanel extends AbstractContentPanel {

    //Table headers
    private final String[] headers = {"Date","Score","Level","Time"};

    //Buttons
    private JButton buttonMenu;

    //Lists
    private JTable table;

    //Scroll Pane
    private JScrollPane scrollPane;

    //ScoreController responsible for fetching data from db


    public HighScorePanel(JFrame window) {
        super(window);
        setTitle("HIGH SCORES");

    }

    @Override
    protected void initializeAttributes() {
        //Attribute Panel - displaying the table of player results
        //also contains button directing you to menu page

        this.panelAttributes = new JPanel(new GridLayout(2,1));
        this.add(panelAttributes,BorderLayout.CENTER);

        //Initialize components
        initializeTable();
        initializeButtons();
        setListeners();
    }

    @Override
    protected void setListeners() {


        //Menu listener
        this.buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                new MainMenuWindow();
            }
        });


    }

    private void initializeButtons(){

        //Menu redirect button
        this.buttonMenu = new JButton("EXIT");
        this.buttonMenu.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        this.panelAttributes.add(buttonMenu);

    }


    private void initializeTable(){

        //Table presenting players previous scores
        this.table = new JTable();

        //Wrapped by a scroll pane
        this.scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        //Add scroll pane to panel attributes
        this.panelAttributes.add(this.scrollPane);

    }



}
