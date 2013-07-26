package com.buggy.socket;

import com.buggy.main.ControlVueController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yoann
 */
public class Connection implements Runnable {

    private PrintWriter out;
    private BufferedReader in;
    public Emission emission;
    public Reception reception;
    private Socket socket;
    private final int numRobot;
    public String ip;

    public Connection(final int numRobot) {
        this.ip = "192.168.1.10" + (numRobot + 1);
        this.numRobot = numRobot;
        this.emission = new Emission(this.numRobot);
        this.reception = new Reception(this.numRobot);
    }

    public void setSocket(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            System.out.println("Connection to: " + ip);

            this.socket = new Socket();
            this.socket.setSoTimeout(3000);
            // We don't know if conneted
            ControlVueController.getCheckBoxConnection(numRobot).setIndeterminate(true);

            SocketAddress client = new InetSocketAddress(ip, 8000);
            this.socket.connect(client, 1000);

            this.out = new PrintWriter(this.socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));


            // Reset emission & reception (better than new Emission , new Reception)
            this.emission.stop = false;
            this.reception.stop = false;

            this.emission.setOut(this.out);
            this.reception.setIn(this.in);

            // Start Thread
            new Thread(this.emission).start();
            new Thread(this.reception).start();

            // Connected !
            System.out.println("Buggy " + (numRobot + 1) + " connected !");
            ControlVueController.getCheckBoxConnection(numRobot).setIndeterminate(false);

        } catch (IOException e) {
            // Can't connect !
            System.out.println("I can't connect Buggy " + (numRobot + 1) + "  !");
            ControlVueController.getCheckBoxConnection(numRobot).setSelected(false);
            ControlVueController.getCheckBoxConnection(numRobot).setIndeterminate(false);
        }

    }

    public void disconnect() {
        //      To play a sound , use this.
        //      new MediaPlayer(new Media(getClass().getResource("Disc" + (i + 1) + ".mp3").toString())).play();

        try {
            this.emission.disconnect();
            this.reception.disconnect();
            if (this.socket != null) {
                if (!this.socket.isClosed()) {
                    this.socket.close();
                    this.socket = null;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}