package com.buggy.model;

import java.util.ArrayList;

/**
 *
 * @author Yoann
 */
public class Model {

    public int nombreScreen = 0;
    public final ArrayList<Robot> robotList = new ArrayList<>();
    public final int nombreRobot = 5;

//    public int[][][] map;
    public Model() {
        for (int i = 0; i < nombreRobot; i++) {
            this.robotList.add(new Robot());
        }
    }
}
