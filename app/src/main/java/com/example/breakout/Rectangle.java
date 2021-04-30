package com.example.breakout;

public class Rectangle {

    public Point corner;
    public float height;
    public float width;

    public Rectangle(Point corner, float height, int width){
        this.corner = corner;
        this.height = height;
        this.width = width;
    }


    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public Point getCorner() {
        return corner;
    }
}
