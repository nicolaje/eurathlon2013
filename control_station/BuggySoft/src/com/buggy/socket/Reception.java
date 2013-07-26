package com.buggy.socket;

import com.buggy.main.ControlVueController;
import java.io.BufferedReader;
import java.io.IOException;
import static com.buggy.main.Main.MyModel;

/**
 *
 * @author Yoann
 */
public class Reception implements Runnable {

    protected boolean stop = false;
    private BufferedReader in;
    private String message;
    private final int numRobot;
    private String[] split;
    private final String RECEPTION;

    protected void disconnect() {
        this.stop = true;
    }

    protected Reception(int numRobot) {
        this.numRobot = numRobot;
        this.RECEPTION = "RECEPTION " + (numRobot + 1) + ": ";
    }

    protected void setIn(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {

        while (!stop) {
            try {

                // The message is null if error
                message = in.readLine();
                if (message != null) {
                    // Convention :  TYPE DATA DATA
                    split = message.split(" ");
                    switch (split[0]) {
                        case "COMPASS":
                            System.out.println(RECEPTION + "Compass " + split[1]);
                            MyModel.robotList.get(numRobot).compassData = split[1];
                            break;

                        // GPS ?
                        case "GPS":
                            System.out.println(RECEPTION + "GPS " + split[1] + split[2]);
                            MyModel.robotList.get(numRobot).longitude = split[1];
                            MyModel.robotList.get(numRobot).latitude = split[2];
                            break;
                    }
                } else {
                    System.out.println(RECEPTION + "Null Message --> Deconnection");

                    // Already disconnected ?
                    if (ControlVueController.getCheckBoxConnection(numRobot).isSelected()) {
                        ControlVueController.getCheckBoxConnection(numRobot).fire();
                    }

                    // Stop this thread.
                    this.stop = true;
                }
            } catch (IOException e) {
                System.out.println(RECEPTION + "IOException");

                // Already disconnected ?
                if (ControlVueController.getCheckBoxConnection(numRobot).isSelected()) {
                    ControlVueController.getCheckBoxConnection(numRobot).fire();
                }
                // Stop this thread.
                this.stop = true;
            }
        }
        System.out.println(RECEPTION + "Thread closed !");
    }
}
