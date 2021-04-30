package com.example.breakout;

public class Player {

    public static final int DEFAULT_SIZE = 200;
    private Point startPoint;

    public Player(Point point){
        this.startPoint = point;
    }

    public void move(Point speed){
        if(startPoint.x + speed.x >= 0 && startPoint.x + speed.x + DEFAULT_SIZE <= Arena.width)
            startPoint.x += speed.x ;
    }

    public void moveTouch(Point speed){
        if(speed.x >= 0 && speed.x + DEFAULT_SIZE <= Arena.width)
            startPoint.x = speed.x;
            startPoint.y = speed.y;
    }

    public Point getStartPoint(){
        return startPoint;
    }

}
