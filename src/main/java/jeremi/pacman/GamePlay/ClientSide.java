package jeremi.pacman.GamePlay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSide {

    private Socket meClient;

    private InputStream input;
    private OutputStream output;

    private String hostAddress;

    public ClientSide(String hostAddress){
        this.hostAddress = hostAddress;
        establishConnection();
    }


    private void establishConnection(){

        try {
            meClient = new Socket(hostAddress,HostSide.getPort());
            input = meClient.getInputStream();
            output = meClient.getOutputStream();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

}
