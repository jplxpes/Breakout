package com.example.breakout;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.LinkedList;

import static com.example.breakout.Arena.BOTTOM_OF_SCREEN;

public class Level implements Iterable<Rectangle> {


    private LinkedList<Rectangle> map;
    private Ball ball;
    private Player player;
    public int level;


    public Level(){
        level = 0;
        map = new LinkedList<>();
        ball = new Ball(new Point(50, 800 ),  player = new Player(new Point(50, BOTTOM_OF_SCREEN)) ,map);
    }

    public boolean playerWon(){
        return map.size() == 0;
    }

    public Player getPlayer() {
        return player;
    }

    public Ball getBall() {
        return ball;
    }


    public void addRect(int n) {
        int x = 0;
        int y = 0;

        float size = (float) (Arena.width/(5.15));
        int gap = Arena.width/200;

        for (int i = 0; i < n; i++) {

            if(x + size > Arena.width) {
                x = 0;
                y += 100;
            }


            // 215 = GAP + SIZE
            // 200 = SIZE
            // 5 = GAP


            map.add(new Rectangle(new Point(x + gap, y + gap), (size + gap + x), (100 + y)));


            x+= (size + gap);

        }

        ball.setMap(map);

    }


    @NonNull
    @Override
    public Iterator<Rectangle> iterator() {
        return map.iterator();
    }

    public void upgrade() {
        ball = new Ball(new Point(50, 800 ),  player = new Player(new Point(Arena.width/2, BOTTOM_OF_SCREEN)) ,map);
        ball.setSpeed(++level);
    }

    public void startOver() {
        level = 0;
        map = new LinkedList<>();
        ball = new Ball(new Point(50, 800 ),  player = new Player(new Point(50, BOTTOM_OF_SCREEN)) ,map);
    }
}
