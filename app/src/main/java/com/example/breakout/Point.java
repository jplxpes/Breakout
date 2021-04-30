package com.example.breakout;

public class Point {

    public float x, y;

    public Point(float x, float y){
        this.x =x;
        this.y =y;
    }


    public Point add(Point velocity) {
        x += velocity.x;
        y += velocity.y;
        return this;
    }
}
