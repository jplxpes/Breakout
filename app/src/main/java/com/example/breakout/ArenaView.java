package com.example.breakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class ArenaView extends  View {

    Paint brushBall;
    Paint brushPlayer;
    Paint brushTiles;
    Arena model;

    public ArenaView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(Color.WHITE);

        brushPlayer = new Paint();
        brushPlayer.setColor(Color.BLACK);
        brushPlayer.setStrokeWidth(20);
        brushPlayer.setStyle(Paint.Style.STROKE);

        brushBall = new Paint();
        brushBall.setColor(Color.RED);
        brushBall.setStrokeWidth(10);
        brushBall.setStyle(Paint.Style.FILL);

        brushTiles = new Paint();
        brushTiles.setColor(Color.BLUE);
        brushTiles.setStrokeWidth(10);
        brushTiles.setStyle(Paint.Style.FILL);

        randomColor();
    }

    public void randomColor() {

        Random random=new Random();
        boolean dif = true;

        int Bred = random.nextInt(256);
        int Bgreen = random.nextInt(256);
        int Bblue = random.nextInt(256);

        int Tred = random.nextInt(256);
        int Tgreen = random.nextInt(256);
        int Tblue = random.nextInt(256);


        while(Bred + Bgreen + Bblue == 16777215 || Bred + Bgreen + Bblue == 0 || dif) {

            Bred = random.nextInt(256);
            Bgreen = random.nextInt(256);
            Bblue = random.nextInt(256);

            Tred = random.nextInt(256);
            Tgreen = random.nextInt(256);
            Tblue = random.nextInt(256);

            if((Bred != Tred && Bgreen != Tgreen && Bblue != Tblue) && (Tred + Tgreen + Tblue != 16777215 )){
                dif = false;
            }

        }

        brushPlayer.setARGB(255, Bred, Bgreen,Bblue);
        brushTiles.setARGB(255,Tred, Tgreen, Tblue);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawLine(model.getLevel().getPlayer().getStartPoint().x, Arena.BOTTOM_OF_SCREEN, model.getLevel().getPlayer().getStartPoint().x + Player.DEFAULT_SIZE, Arena.BOTTOM_OF_SCREEN, brushPlayer);
        canvas.drawCircle(model.getLevel().getBall().getCenter().x, model.getLevel().getBall().getCenter().y, model.getLevel().getBall().getRadius(), brushBall);

        for (Rectangle R : model.getLevel()) {
            if(R != null)
                canvas.drawRect(R.getCorner().x, R.getCorner().y, R.getHeight(), R.getWidth() , brushTiles);
        }


    }

    public void setModel (Arena model){
        this.model = model;
    }
}
