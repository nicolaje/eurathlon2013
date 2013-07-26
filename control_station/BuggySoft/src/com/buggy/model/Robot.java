package com.buggy.model;

import com.buggy.carte.Point;


/**
 *
 * @author Yoann
 */
public class Robot {
  
    // I don't use this string because in this version ip = "192.168.1.10"+(numBuggy+1);
    // public String ip;
    
    
    // String use to send Message.
    public String order;

    // Double -> String to avoid Double.decode( );
    public String latitude="0";
    public String longitude="0";
    public String compassData="0";
    
    // for the map
    public Point position;

    public Robot() {
        this.position = new Point(0,0);
        this.order = "";
    }

    /**
     * @return the order
     */
    public synchronized String getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public synchronized void setOrder(String order) {
        this.order = order;
    }

}
