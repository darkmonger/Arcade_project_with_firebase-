package com.example.arcade_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Snake_activity extends AppCompatActivity implements SurfaceHolder.Callback {
    //list of snake points and snake lenght
    private  final List<SnakePoints> snakePointsList = new ArrayList<>();

    private SurfaceView surfaceView;
    private TextView score_view;


    //suface holder ile surfaceda snake çizmek için
    private SurfaceHolder surfaceHolder;


    // Snake sol sağ orta yukarı gider
    //default olarak sağa gider
    private String movingPosition ="right";

    //score
    private  int score = 0;

    //snake size point size
    // change this to make snake bigger
    private static final int pointSize = 28;

    //default snake tale
    private static final int defaultTalePoints = 3;

    //snake color
    private static final int snakeColor = Color.YELLOW;

    // snake moving speed must be between 1-1000
    private static final int snakeMovingSpeed = 800;

    //random point locations on surfaceView
    private int positionX = 0, positionY = 0;

    //timer to move snake and change position after a speficic_time(snakeMoovingSpeed)
    private Timer timer;

    //using canvas to draw snake and showing surface view
    private Canvas canvas = null;


    //point color / single point of the snake
    private Paint pointColor = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);


        //surface view ve textviewi alma xmlden
        surfaceView = findViewById(R.id.surfaceView);
        score_view = findViewById(R.id.score_view);

        //xml dosyasından imagebuttonları alıyorum
        final AppCompatImageButton up_button = findViewById(R.id.up_button);
        final AppCompatImageButton bottom_button = findViewById(R.id.bottom_button);
        final AppCompatImageButton left_button = findViewById(R.id.left_button);
        final AppCompatImageButton right_button = findViewById(R.id.right_button);

        //Callback ekliyorum
        surfaceView.getHolder().addCallback(this);

        up_button.setOnClickListener(new View.OnClickListener() {
            @Override

            //aşağı gidip gitmediğini kontrol
            //eğer snake aşağı gidiyosa direk yukarı gidemez
            //ilk önce sağ ya da sola gitmeli
            public void onClick(View view) {
                if (!movingPosition.equals("bottom")){
                    movingPosition = "top";
                }
            }
        });

        bottom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movingPosition.equals("top"))
                    movingPosition="bottom";
            }
        });

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!movingPosition.equals("right")){
                    movingPosition = "left";
                }
            }
        });

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!movingPosition.equals("left")){
                    movingPosition = "right";
                }
            }
        });
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
    //when surface is created then get surfaceHolder from it and assign to surfaceHolder
    this.surfaceHolder = surfaceHolder;

    // init data for snake and surfaceView
    init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    private void init(){
        //clear snake point and  snake length
        snakePointsList.clear();
        // setting default as  0
        score_view.setText("0");

        //make score 0
        score = 0;

        //setting default starting position on the screen
        movingPosition = "right";

        int startPositionX = (pointSize) * defaultTalePoints;

        //making snake's default length / points
        for (int i=0; i<defaultTalePoints;i++){

            //adding points to snake's tale
            SnakePoints snakePoints = new SnakePoints(startPositionX,pointSize);
            snakePointsList.add(snakePoints);

            //increasing value for the next snakes tale
            startPositionX = startPositionX -(pointSize * 2);
        }
        //add random points to screen  to be eaten by  the snake
        addPoints();

        //start moving snake and  start game
        moveSnake();
    }

    private void addPoints(){

        //getting surfaceview width and height  to add points to surface fo snake to eat
        int surfaceWidth = surfaceView.getWidth() - (pointSize * 2);
        int surfaceHeight = surfaceView.getHeight() - (pointSize *2);

        int randomXPosition = new Random().nextInt(surfaceWidth / pointSize);
        int randomYPosition = new Random().nextInt(surfaceHeight / pointSize);


        //check if randomXPosition even or odd we need even number
        if((randomXPosition %2) != 0 ){
            randomXPosition = randomXPosition +1;
        }

        if((randomYPosition % 2!=0 )){
            randomYPosition = randomYPosition +1;
        }
        positionX = (pointSize * randomXPosition) + pointSize;
        positionY = (pointSize * randomYPosition) + pointSize;
    }

    private void moveSnake(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //getting head position
                int headPositionX = snakePointsList.get(0).getPositionX();
                int headPositionY = snakePointsList.get(0).getPositionY();

                //cheack if snake ate a point
                if(headPositionX == positionX && positionY ==headPositionY){

                    //growing snake after eating a point
                    growSnake();

                    //add another random point to surface
                    addPoints();

                    //checking where snake is moving to
                    switch (movingPosition){
                        case"right":
                            //move snake's head to right
                            //other parts follows the snakes head
                            snakePointsList.get(0).setPositionX(headPositionX + (pointSize * 2));
                            snakePointsList.get(0).setPositionY(headPositionY);
                            break;
                        case "left":
                            //move snake's head to left
                            //other parts follows the snakes head
                            snakePointsList.get(0).setPositionX(headPositionX - (pointSize * 2));
                            snakePointsList.get(0).setPositionY(headPositionY);
                            break;
                        case "top":
                            //move snake's head to top
                            //other parts follows the snakes head
                            snakePointsList.get(0).setPositionX(headPositionX);
                            snakePointsList.get(0).setPositionY(headPositionY - (pointSize * 2));
                            break;
                        case "bottom":
                            //move snake's head to bottom
                            //other parts follows the snakes head
                            snakePointsList.get(0).setPositionX(headPositionX );
                            snakePointsList.get(0).setPositionY(headPositionY + (pointSize * 2));
                            break;

                    }
                    //check if snake touched itself or the edge of the map
                    if(checkGameOver(headPositionX,headPositionY)){
                        //stop moving and stop timer
                        timer.purge();
                        timer.cancel();

                        //show game over dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(Snake_activity.this);
                        builder.setMessage("your score"+score);
                        builder.setTitle("Game Over");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Start Again ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                             //restart  game  re initing data
                                init();
                            }
                        });
                        //timer arkada çalışıyor bizde main threaddde dialogu gösteririz
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                builder.show();
                            }
                        });
                    }
                    else{

                        //locking the canvas so we can draw on it
                        canvas = surfaceHolder.lockCanvas();

                        //clearing canvas with white color
                        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

                        //changing snake's head position so other parts of the snake will follow the head
                        canvas.drawCircle(snakePointsList.get(0).getPositionX(),snakePointsList.get(0).getPositionY(), pointSize,createPointColor());

                        //draw random point circle on the surface for snake to eat
                        canvas.drawCircle(positionX,positionY,pointSize,createPointColor());

                        // for other points to follow snake's head position 0 is the head's location
                        for(int i = 1; i<snakePointsList.size();i++){

                            int getTempPositionX = snakePointsList.get(i).getPositionX();
                            int getTempPositionY = snakePointsList.get(i).getPositionY();

                            //moving points across the head
                            snakePointsList.get(i).setPositionX(headPositionX);
                            snakePointsList.get(i).setPositionY(headPositionY);
                            canvas.drawCircle(snakePointsList.get(i).getPositionX(),snakePointsList.get(i).getPositionY(),pointSize,createPointColor());

                            //changing the head position
                            headPositionX = getTempPositionX;
                            headPositionY = getTempPositionY;
                        }

                        //unlocking canvas to draw on surfaceView
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }

                }
            }
        },1000-snakeMovingSpeed,1000- snakeMovingSpeed);
    }

    private  void growSnake(){
        //create new snake point
        SnakePoints snakePoints = new SnakePoints(0,0);

        //add point to the snake's tale
        snakePointsList.add(snakePoints);

        //increasing the score
        score++;

        //setting score to text views
        score_view.setText(String.valueOf(score));
    }
    private boolean checkGameOver(int headPositionX,int headPositionY){
        boolean gameOver = false;

        //cheking if snakes head touching the edges of the map
        if(snakePointsList.get(0).getPositionX() < 0 || snakePointsList.get(0).getPositionY() <0 ||
        snakePointsList.get(0).getPositionX() >= surfaceView.getWidth() || snakePointsList.get(0).getPositionY() >= surfaceView.getHeight())
        {
            gameOver = true;
        }
        else{
            //check if snake's head touching itself
            for(int i = 0; i<snakePointsList.size();i++){

                if(headPositionX == snakePointsList.get(i).getPositionX() &&
                    headPositionY == snakePointsList.get(i).getPositionY()){
                    gameOver = true;
                    break;
                }
            }
        }

        return gameOver;
    }

    private Paint  createPointColor(){

        //checking if color is defined before
        if(pointColor == null){

            pointColor = new Paint();
            pointColor.setColor(snakeColor);
            pointColor.setStyle(Paint.Style.FILL);
            pointColor.setAntiAlias(true); // aa açarak daha kaliteli gözükmesini sağlarız


        }

       return  pointColor;
    }

}