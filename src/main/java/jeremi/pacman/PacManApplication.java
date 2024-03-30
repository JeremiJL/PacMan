package jeremi.pacman;

import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import jeremi.pacman.Menu.MainMenuWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import com.formdev.flatlaf.*;


@SpringBootApplication
public class PacManApplication {

    public static void main(String[] args) {

//        SpringApplication.run(PacManApplication.class, args);

        //enable look and feel
        try {
            FlatSolarizedDarkIJTheme.setup();
            UIManager.setLookAndFeel( new FlatSolarizedDarkIJTheme() );
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        //run menu window
        try{
            SwingUtilities.invokeLater(() -> new MainMenuWindow());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
