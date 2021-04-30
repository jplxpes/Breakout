package com.example.breakout;
import java.util.LinkedList;



public class Ball {

    public interface wallListener{
        void soundWall();
    }

    public interface scoreListener{
        void addScore();
    }

    public final int RADIUS = 10;

    public LinkedList<Rectangle> map;

    public Point center;
    private Point velocity;
    private Player player;
    public boolean gameOver;
    private boolean created;
    private scoreListener listener;
    private wallListener wListener;

    private int speedX = 5;
    private int speedY = 10;


    public Ball(Point center, Player player, LinkedList<Rectangle> map) {
        this.center = center;
        this.player = player;
        this.map = map;
        speedX = 5;
        speedY = 10;
        velocity = new Point(speedX, -speedY);
    }

    public void setSpeed(int x){
        speedX += 2*x;
        velocity = new Point(speedX , speedY);
    }

    public void setListener(scoreListener listener){
        this.listener = listener;
    }

    public void setWallListener(wallListener listener){
        wListener = listener;
    }


    public void setMap(LinkedList<Rectangle> map){
        this.map = map;
    }


    public void move() {

        // GAME OVER
        if (Arena.height != 0 && center.y > Arena.height || created && map.size() == 0) {
            gameOver = true;
        }


        // BOUNCE MECHANIC
        else if (center.y + 2 * RADIUS == Arena.BOTTOM_OF_SCREEN) {
            if (center.x - RADIUS >= player.getStartPoint().x && center.x - RADIUS <= player.getStartPoint().x + Player.DEFAULT_SIZE)
                velocity = new Point(velocity.x, velocity.y * -1);
        }

        if (map.size() != 0) {

            created = true;

            for (int i = map.size() - 1; i >= 0 ; i--) {

                if ((center.x >= map.get(i).getCorner().x) && (center.x <= (map.get(i).getCorner().x + 205)) &&
                        (center.y >= map.get(i).getCorner().y) && (center.y <= (map.get(i).getCorner().y + 100))) {


                    if(velocity.x < 0 && velocity.y > 0)
                        velocity = new Point(-speedX, -speedY);
                    else if(velocity.x > 0 && velocity.y < 0)
                        velocity = new Point(speedX, speedY);
                    else if(velocity.x > 0 && velocity.y > 0)
                        velocity = new Point(speedX, -speedY);
                    else if(velocity.x < 0 && velocity.y < 0)
                        velocity = new Point(-speedX, speedY);


                    wListener.soundWall();
                    listener.addScore();
                    map.remove(i);

                    break;

                }

            }

        }


        // WALL
        if (center.x - RADIUS <= 0 || center.x + RADIUS >= Arena.width) {
            velocity = new Point(velocity.x * -1, velocity.y);
        } else if (center.y - RADIUS <= 0) {
            velocity = new Point(velocity.x, velocity.y * -1);

        }

        center = center.add(velocity);
    }






    public Point getCenter() {
        return center;
    }

    public int getRadius(){
        return RADIUS;
    }
}
