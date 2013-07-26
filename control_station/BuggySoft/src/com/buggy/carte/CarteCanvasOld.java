package com.buggy.carte;

import com.buggy.main.Main;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author Yoann
 */
public class CarteCanvasOld extends Canvas {

    private ArrayList<Point> positionList = new ArrayList<>();
    public int echelle = 7;
    public Point centre=new Point(0,0);

    public CarteCanvasOld() {
        this.setWidth(4200);
        this.setHeight(2100);
        
        initialization();
        
        affichagePosition();
    }

    public final void initialization() {
        this.positionList.clear();
        for (int i = 0; i < Main.MyModel.robotList.size(); i++) {
            positionList.add(this.latitudeToCoordonate(Main.MyModel.robotList.get(i).latitude, Main.MyModel.robotList.get(i).longitude));
        }
        affichagePosition();
    }


    public final void affichagePosition() {
        this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());
        for (int i = 0; i < positionList.size(); i++) {
            this.getGraphicsContext2D().strokeOval(positionList.get(i).x - 10, positionList.get(i).y - 10, 20, 20);
            this.getGraphicsContext2D().fillText("" + (i+1), positionList.get(i).x -5, positionList.get(i).y + 5);
            this.getGraphicsContext2D().fillText(Main.MyModel.robotList.get(i).longitude + " : " + Main.MyModel.robotList.get(i).latitude, positionList.get(i).x - 30, positionList.get(i).y + 25);
        }
    }

    public final Point latitudeToCoordonate( String longitude,String latitude) {
        double x, y;
        x = this.getWidth() / 2.0 + (echelle * (Double.valueOf(longitude) - centre.y));
        y = this.getHeight() / 2.0 + (echelle * (Double.valueOf(latitude)- centre.x));
        return new Point(x, y);
    }
}
