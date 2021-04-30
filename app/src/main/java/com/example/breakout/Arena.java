package com.example.breakout;


public class Arena {

    public static final int BOTTOM_OF_SCREEN = 1150;
    public static int height;
    public static int width;

    private Level level;

    public Arena(){
        level = new Level();
    }

    public Level getLevel(){
        return level;
    }

    public void createMap(int x){
        level.addRect(x);
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public void setBoundaries(int height, int width) {
        Arena.height = height;
        Arena.width = width;
    }



}
