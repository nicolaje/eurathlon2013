package com.buggy.socket;

import java.io.PrintWriter;
import com.buggy.main.Main;

/**
 *
 * @author Yoann
 */
public class Emission implements Runnable {

    private PrintWriter out;
    public boolean stop = false;
    private final int numRobot;
    private final String EMISSION;
    // Fonctions pour le chronometre
    private long chrono = 0;

    public Emission(int numRobot) {
        this.numRobot = numRobot;
        this.EMISSION = "EMISSION " + (numRobot + 1) + ": ";
    }

    @Override
    public void run() {
        startChrono();
        while (!stop) {

            if (chrono()>1000)
            {
                this.out.println("PING");
                this.out.flush();
                startChrono();            
            }
                

            // if need send.
            if (!Main.MyModel.robotList.get(numRobot).getOrder().equals("")) {

                System.out.println(EMISSION + Main.MyModel.robotList.get(numRobot).getOrder());


                // Send order
                this.out.println(Main.MyModel.robotList.get(numRobot).getOrder());
                this.out.flush();

                // Reset order
                Main.MyModel.robotList.get(numRobot).setOrder("");
            }
        }
        System.out.println(EMISSION + "Thread closed !");
    }
    // Lancement du chrono
    private void startChrono() {
        chrono = java.lang.System.currentTimeMillis();
    }

    // Arret du chrono
    private long chrono() {
        long chrono2 = java.lang.System.currentTimeMillis();
        long temps = chrono2 - chrono;
        return temps;
    }
    
    
    
    
    public void disconnect() {
        this.stop = true;
    }

    // To avoid new Emission.
    public void setOut(PrintWriter out) {
        this.out = out;

    }
}
