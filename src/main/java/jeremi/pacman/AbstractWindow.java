package jeremi.pacman;

import jeremi.pacman.Menu.MainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AbstractWindow extends JFrame {

    protected int FRAME_WIDTH = Constants.FRAME_WIDTH;
    protected int FRAME_HEIGHT = Constants.FRAME_HEIGHT;

    protected void initializeFrame(){
        this.setTitle("Pacman");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        setKeyListener();
    }

    protected void setKeyListener(){



        new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {

                System.out.println(e.getKeyCode());

            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println(e.getKeyCode());
            }
        };

    }

    protected void setMyCloseOperation(){

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                displayPopUp();

            }
        });
    }

    private void displayPopUp(){

        Object[] options = { "MENU", "EXIT" , "CANCEL" };
        String title = "Exit";
        String message = "Exit or go back to menu ?";
        ImageIcon icon = new ImageIcon("Graphics/Player/PlayerDead2.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT));

        int choice = JOptionPane.showOptionDialog(this,message,title,JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,icon,options,options[0]);

        switch (choice){

            //Menu
            case 0 :
                this.dispose();
                new MainMenuWindow();
                break;

            //Exit
            case 1 :
                this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                this.dispose();
                System.exit(0);
                break;

            //Cancel
            case 2 :
                break;
            //do nothing
        }

    }

}
