package com.example.breakout;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.TimeAnimator;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements  View.OnTouchListener {

    private static final int NUMBER_OF_BRICKS = 30;

    MediaPlayer paddle;
    MediaPlayer wall;
    MediaPlayer lose;
    MediaPlayer win;


    TextView info;
    TextView score;
    TextView level;
    ArenaView view;
    Arena model;
    int points;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMedia();
        view = findViewById(R.id.arenaView);
        model = new Arena();
        view.setModel(model);
        score = findViewById(R.id.points);
        level = findViewById(R.id.level);
        info = findViewById(R.id.infoCard);
        view.setOnTouchListener(this);
        final TimeAnimator animator = new TimeAnimator();
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.left:
                        model.getLevel().getPlayer().move(new Point(-50, 0));

                        view.invalidate();
                        break;
                    case R.id.right:
                        model.getLevel().getPlayer().move(new Point(50, 0));
                        view.invalidate();
                        break;
                }
            }

        };



        findViewById(R.id.infoCard).setVisibility(View.INVISIBLE);
        final Button action = findViewById(R.id.actionButton);
        action.setVisibility(View.INVISIBLE);

        findViewById(R.id.left).setOnClickListener(listener);
        findViewById(R.id.right).setOnClickListener(listener);


        wallSetter();
        pointSetter();

        level.setText(Integer.toString(1));

        getWindow().getDecorView().post(new Runnable() {

            @Override
            public void run() {
                model.setBoundaries(view.getHeight(), view.getWidth());
                model.createMap(NUMBER_OF_BRICKS);
            }

        });


        animator.setTimeListener(new TimeAnimator.TimeListener() {
            int elapsedTime = 0;
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                elapsedTime += deltaTime;
                if(elapsedTime >= 15){

                    if(!model.getLevel().getBall().gameOver) {
                        elapsedTime = 0;
                        model.getLevel().getBall().move();
                        view.invalidate();
                    }

                    else  {
                        if (model.getLevel().playerWon()) {
                            win.start();
                            info.setText("You win! Next level ready!");
                            action.setText("Okay");
                            info.setVisibility(View.VISIBLE);
                            action.setVisibility((View.VISIBLE));
                            action.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    win.stop();
                                    model.getLevel().upgrade();
                                    model.createMap(NUMBER_OF_BRICKS);
                                    pointSetter();
                                    wallSetter();
                                    level.setText(Integer.toString(model.getLevel().level));
                                    info.setVisibility(View.INVISIBLE);
                                    action.setVisibility((View.INVISIBLE));
                                    }
                                });

                        } else{
                            info.setText("You Lost!");
                            lose.start();
                            info.setVisibility(View.VISIBLE);
                            action.setText("Retry?");
                            action.setVisibility((View.VISIBLE));
                            action.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    lose.stop();
                                    points = 0;
                                    setUpMedia();
                                    model.getLevel().startOver();
                                    model.createMap(NUMBER_OF_BRICKS);
                                    view.randomColor();
                                    points = 0;
                                    score.setText(Integer.toString(points));
                                    wallSetter();
                                    pointSetter();
                                    level.setText(Integer.toString(model.getLevel().level + 1));
                                    info.setVisibility(View.INVISIBLE);
                                    action.setVisibility((View.INVISIBLE));
                                }
                            });

                        }
                    }
                }
            }
        });

        animator.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopMediaPlayer();
    }


    private void stopMediaPlayer() {
        paddle.stop();
        wall.stop();
        lose.stop();
        win.stop();
    }

    private void setUpMedia() {
        paddle = MediaPlayer.create(this, R.raw.paddle);
        wall = MediaPlayer.create(this, R.raw.wall);
        lose = MediaPlayer.create(this, R.raw.lose);
        win = MediaPlayer.create(this, R.raw.win);

    }

    private void wallSetter() {
        model.getLevel().getBall().setWallListener(new Ball.wallListener() {
            @Override
            public void soundWall() {
                wall.start();
            }
        });
    }

    public void pointSetter(){
        model.getLevel().getBall().setListener(new Ball.scoreListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void addScore() {
                points++;
                score.setText(Integer.toString(points));
                wall.start();
                paddle.stop();
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
            model.getLevel().getPlayer().moveTouch(new Point(event.getX(), 0));
            v.invalidate();
            return true;
        }
        return false;
    }
}
