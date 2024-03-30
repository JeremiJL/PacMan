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


    //Buttons
    private JButton buttonMenu;

    //Lists
    private JList<String> listScoreData;


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
        initializeLists();
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


    private void initializeLists(){

        //List inside a scroll pane containing data of previous scores set

        this.listScoreData.setSelectionMode(1);
        this.listScoreData.setFont(Constants.getFont(Constants.FONT_TYPE.SMALL_FONT));
        JScrollPane scrollPane = new JScrollPane(listScoreData);
        this.panelAttributes.add(scrollPane);

    }

//    private JList<String> listFromScoresDataQuery(){
//
//        this.listScoreData = new JList<>();
//
//        try{
//
//            //create the connection object
//            String user = "s26743";
//            String password = "oracle12";
//            String url = "jdbc:oracle:thin:@//db-oracle02.pjwstk.edu.pl:1521/baza.pjwstk.edu.pl";
//
//            Connection connection = DriverManager.getConnection(url,user,password);
//
//            //create the statement object
//
//            Statement statement = connection.createStatement();
//
//            //execute select query
//            String query = "SELECT * FROM PACMAN_HIGH_SCORES";
//            ResultSet resultSet = statement.executeQuery(query);
//
//
//            //read results
//
//            String tmp = "";
//
//            while(resultSet.next()) {
//
//                String date = resultSet.getString("GAME_DATE");
//                String score = resultSet.getString("GAME_SCORE");
//                String level = resultSet.getString("GAME_LEVEL");
//
//                tmp = "Date : " + date + "\tScore : " + score + "\tLevel : " + level;
//            }
//
//
//            //close the connection object
//            resultSet.close();
//            statement.close();
//            connection.close();
//
//        }catch(SQLException e){
//            listData = "Failed to establish connection with data base!";
//        }
//
//        return listData;
//    }


}
