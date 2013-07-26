/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buggy.carte;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author yoann
 */
public class CarteCanvas extends Canvas {

    public int nbCaseX;
    public int nbCaseY;
    public int sizeX;
    public int sizeY;
    private final GraphicsContext gc;

    public CarteCanvas() {
        this.setWidth(1024);
        this.setHeight(1024);

        this.nbCaseX = 10;
        this.nbCaseY = 10;
        this.sizeX = 48;
        this.sizeY = 48;
        this.gc = this.getGraphicsContext2D();

        affichageCase();

        
        
        
//        affichageBuggy(new Point(1, 0), 0.0);
//                clearBuggy(new Point(1,0));
        
//        clearBuggy(new Point(1,0));

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                getCase(t.getX(), t.getY());
            }
        });


    }

    public final void affichageCase() { // Working
        for (int i = 0; i < nbCaseX; i++) {
            for (int j = 0; j < nbCaseY; j++) {
                gc.strokeRect(i * sizeX, j * sizeY, sizeX, sizeY);
            }
        }
    }

    public final void affichageBuggy(Point x, double angle) { // Rotation not working

        gc.setFill(Color.BLACK);
        
        double x0 = sizeX / 2 + x.x * sizeX;
        double x1 = 0 + x.x * sizeX;
        double x2 = sizeX + x.x * sizeX;
        double y0 = 0 + x.y * sizeY;
        double y1 = sizeY + x.y * sizeY;
        double y2 = sizeY + x.y * sizeY;


        // Rotation not working ...
//     double Gx=(x0+x1+x2)/3+x.x*sizeX;
//     double Gy=(y0+y1+y2)/3+x.y*sizeY;
//     
//     double a0=(x0-Gx)*Math.cos(angle*(Math.PI/180.0))-(y0-Gy)*Math.sin(angle*(Math.PI/180.0))+Gx;
//     double a1=(x1-Gx)*Math.cos(angle*(Math.PI/180.0))-(y1-Gy)*Math.sin(angle*(Math.PI/180.0))+Gx;
//     double a2=(x2-Gx)*Math.cos(angle*(Math.PI/180.0))-(y2-Gy)*Math.sin(angle*(Math.PI/180.0))+Gx;
//     
//      double b0=(x0-Gx)*Math.sin(angle*(Math.PI/180.0))-(y0-Gy)*Math.cos(angle*(Math.PI/180.0))+Gy;
//     double b1=(x1-Gx)*Math.sin(angle*(Math.PI/180.0))-(y1-Gy)*Math.cos(angle*(Math.PI/180.0))+Gy;
//     double b2=(x2-Gx)*Math.sin(angle*(Math.PI/180.0))-(y2-Gy)*Math.cos(angle*(Math.PI/180.0))+Gy;

        gc.fillPolygon(new double[]{x0, x1, x2},new double[]{y0, y1, y2}, 3);
    }

    public final void clearBuggy(Point x)
    {
        
        gc.clearRect(x.x*sizeX, x.y*sizeY, sizeX, sizeY);
        gc.strokeRect(x.x*sizeX, x.y*sizeY, sizeX,sizeY);
    }
    public final Point getCase(double x, double y) { // Working 
        Point case1 = new Point();

        case1.x = (int) (x / sizeX);
        case1.y = (int) (y / sizeY);
        case1.print();
        return case1;
    }
}
