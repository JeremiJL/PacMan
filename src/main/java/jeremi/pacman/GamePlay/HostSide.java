package jeremi.pacman.GamePlay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HostSide {

    private ServerSocket server;
    private Socket client;

    private InputStream input;
    private OutputStream output;

    private String address;
    final static private int port = 12314;


    public HostSide()  {

        establishServer();
        listenForConnections();

    }

    private void establishServer(){
        try {

            server = new ServerSocket(port);
            address = server.getInetAddress().toString();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void listenForConnections(){

        try {
            client = server.accept();

            input = client.getInputStream();
            output = client.getOutputStream();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    public void end(){
        try {
            server.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static int getPort(){
        return port;
    }

    public String getAddress(){
        return address;
    }

    public InputStream getInput() {
        return input;
    }

    public OutputStream getOutput() {
        return output;
    }
}
