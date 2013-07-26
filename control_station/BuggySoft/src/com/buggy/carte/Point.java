package com.buggy.carte;

/**
 *
 * @author Yoann
 */
public class Point {

    public double x;
    public double y;

    public Point(int x, int y) {
        this((double) x,(double)y);
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }
    public Point(double x,double y)
    {
                this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Point) {
            Point o = (Point) other;
            return (o.x == x) && (o.y == y);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

 

    public void print() {
        System.out.println(toString());
    }
}
