package com.game.quickdrawv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Resources resources;

    Button btnStart;
    ImageButton btnPlayer1;
    ImageButton btnPlayer2;
    Button btnExit;

    ImageView imageViewBackground;
    ImageView imageViewTitle;
    ImageView imageViewPlayer1;
    ImageView imageViewPlayer2;
    ImageView imageViewAnnouncer;
    ImageView imageViewFlash;

    Player player1;
    Player player2;

    //Animation instances
    Animated animBackground;
    Animated animPlayer1;
    Animated animPlayer2;

    TimerTask readyShootTask;
    TimerTask cleanTask;

    Timer timer = new Timer();

    //Images that are going to be used
    Drawable imgPlayer1;
    Drawable imgPlayer1Early;
    Drawable imgPlayer1Ready;
    Drawable imgPlayer1Shoot;
    Drawable imgPlayer1Shot;

    Drawable imgPlayer1BtnIdle;
    Drawable imgPlayer1BtnReady;
    Drawable imgPlayer1BtnWait;
    Drawable imgPlayer1BtnFire;
    Drawable imgPlayer1BtnWin;
    Drawable imgPlayer1BtnLose;

    Drawable imgPlayer2;
    Drawable imgPlayer2Early;
    Drawable imgPlayer2Ready;
    Drawable imgPlayer2Shoot;
    Drawable imgPlayer2Shot;

    Drawable imgPlayer2BtnIdle;
    Drawable imgPlayer2BtnReady;
    Drawable imgPlayer2BtnWait;
    Drawable imgPlayer2BtnFire;
    Drawable imgPlayer2BtnWin;
    Drawable imgPlayer2BtnLose;

    Drawable imgAnnouncer;
    Drawable imgAnnouncerReady;
    Drawable imgAnnouncerFire;

    //Prepare media player
    MediaPlayer mpPlayer1;
    MediaPlayer mpPlayer2;
    MediaPlayer mpShot;
    MediaPlayer mpEarly;
    MediaPlayer mpWind;
    MediaPlayer mpWhistle;

    //Start creation//////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else{
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
                        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                        decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
                        /*ActionBar actionBar = getActionBar();
                        actionBar.hide();*/
        }

        setContentView(R.layout.activity_main);
        resources = getResources();

        //Assign Buttons and ImageViews
        btnStart = (Button)findViewById(R.id.btnStart);
        btnPlayer1 = (ImageButton)findViewById(R.id.btnReady1);
        btnPlayer2 = (ImageButton)findViewById(R.id.btnReady2);
        btnExit = (Button)findViewById(R.id.btnExit);

        imageViewBackground = (ImageView)findViewById(R.id.imgBackground);
        imageViewTitle = (ImageView)findViewById(R.id.imgTitle);
        imageViewPlayer1 = (ImageView)findViewById(R.id.imgPlayer1);
        imageViewPlayer2 = (ImageView)findViewById(R.id.imgPlayer2);
        imageViewAnnouncer = findViewById(R.id.imgAnnouncer);
        imageViewFlash = (ImageView)findViewById(R.id.imgFlash);

        //Assign animated elements
        animBackground = new Animated(imageViewBackground);
        animPlayer1 = new Animated(imageViewPlayer1);
        animPlayer2 = new Animated(imageViewPlayer2);

        //Start player instances
        player1 = new Player(animPlayer1);
        player2 = new Player(animPlayer2);

        //Load drawables
        imgPlayer1 = resources.getDrawable(R.drawable.player1);
        imgPlayer1Early = resources.getDrawable(R.drawable.player1_early);
        imgPlayer1Ready = resources.getDrawable(R.drawable.player1_ready);
        imgPlayer1Shoot = resources.getDrawable(R.drawable.player1_shoot);
        imgPlayer1Shot = resources.getDrawable(R.drawable.player1_shot);

        imgPlayer1BtnIdle = resources.getDrawable(R.drawable.player1_idle_button);
        imgPlayer1BtnReady = resources.getDrawable(R.drawable.player1_ready_button);
        imgPlayer1BtnWait = resources.getDrawable(R.drawable.player1_wait_button);
        imgPlayer1BtnFire = resources.getDrawable(R.drawable.player1_fire_button);
        imgPlayer1BtnWin = resources.getDrawable(R.drawable.player1_win_button);
        imgPlayer1BtnLose = resources.getDrawable(R.drawable.player1_lose_button);

        imgPlayer2 = resources.getDrawable(R.drawable.player2);
        imgPlayer2Early = resources.getDrawable(R.drawable.player2_early);
        imgPlayer2Ready = resources.getDrawable(R.drawable.player2_ready);
        imgPlayer2Shoot = resources.getDrawable(R.drawable.player2_shoot);
        imgPlayer2Shot = resources.getDrawable(R.drawable.player2_shot);
        imgPlayer2BtnWait = resources.getDrawable(R.drawable.player2_wait_button);

        imgPlayer2BtnIdle = resources.getDrawable(R.drawable.player2_idle_button);
        imgPlayer2BtnReady = resources.getDrawable(R.drawable.player2_ready_button);
        imgPlayer2BtnWait = resources.getDrawable(R.drawable.player2_wait_button);
        imgPlayer2BtnFire = resources.getDrawable(R.drawable.player2_fire_button);
        imgPlayer2BtnWin = resources.getDrawable(R.drawable.player2_win_button);
        imgPlayer2BtnLose = resources.getDrawable(R.drawable.player2_lose_button);

        imgAnnouncer = resources.getDrawable(R.drawable.announcer);
        imgAnnouncerReady = resources.getDrawable(R.drawable.announcer_ready);
        imgAnnouncerFire = resources.getDrawable(R.drawable.announcer_fire);

        //Load sounds
        mpPlayer1 = MediaPlayer.create(this, R.raw.cock);
        mpPlayer2 = MediaPlayer.create(this, R.raw.cock);
        mpShot = MediaPlayer.create(this, R.raw.shot);
        mpEarly = MediaPlayer.create(this, R.raw.early);
        mpWhistle = MediaPlayer.create(this, R.raw.whistle);
        mpWind = MediaPlayer.create(this, R.raw.wind);
    }

    public void onStartButtonClick(View view){
        view.setVisibility(View.INVISIBLE);
        imageViewTitle.setVisibility(View.INVISIBLE);
        btnExit.setVisibility(View.INVISIBLE);
        imageViewAnnouncer.setVisibility(View.VISIBLE);

        btnPlayer1.setVisibility(View.VISIBLE);
        btnPlayer2.setVisibility(View.VISIBLE);
    }

    //Ask if players are ready/////////////////////////////////////////////
    public void onPlayer1ButtonClick(View view){
        player1.setReady(true);
        btnPlayer1.setImageDrawable(imgPlayer1BtnReady);
        imageViewPlayer1.setImageDrawable(imgPlayer1Ready);
        mpPlayer1.start();
        checkReady();
    }

    public void onPlayer2ButtonClick(View view){
        player2.setReady(true);
        btnPlayer2.setImageDrawable(imgPlayer2BtnReady);
        imageViewPlayer2.setImageDrawable(imgPlayer2Ready);
        mpPlayer2.start();
        checkReady();
    }

    public void checkReady(){
        //Check if both players are ready
        if(player1.getReady() && player2.getReady()){
            //btnReady1.setVisibility(View.INVISIBLE);
            //btnReady2.setVisibility(View.INVISIBLE);

            startWaitingPeriod();
        }
    }

    //Prepare waiting period for shooting///////////////////////////
    public void startWaitingPeriod(){
        mpWind.start();

        //Show waiting characters
        btnPlayer1.setImageDrawable(imgPlayer1BtnWait);
        btnPlayer2.setImageDrawable(imgPlayer2BtnWait);
        imageViewAnnouncer.setImageDrawable(imgAnnouncerReady);

        //Add failure if pressed a button before the alarm sounds
        btnPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preShot(v);
            }
        });

        btnPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preShot(v);
            }
        });

        //Get random number from 5 to 10
        Random rand = new Random();

        int delay = (5 + rand.nextInt(6)) * 1000;

        readyShootTask = new TimerTask() {
            @Override
            public void run() {
                btnPlayer1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        waitShot(v);
                    }
                });

                btnPlayer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        waitShot(v);
                    }
                });

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        //btnPlayer1.setText(R.string.fire_message);
                        btnPlayer1.setImageDrawable(imgPlayer1BtnFire);
                        //btnPlayer2.setText(R.string.fire_message);
                        btnPlayer2.setImageDrawable(imgPlayer2BtnFire);
                        imageViewAnnouncer.setImageDrawable(imgAnnouncerFire);
                        //timer.cancel();

                        //Make sound and stop wind
                        mpWind.stop();
                        mpWhistle.start();
                    }
                });
            }
        };

        //Create a timer before the shot is waited for
        timer.schedule(readyShootTask, delay);
    }

    public void flash(){
        imageViewFlash.setVisibility(View.VISIBLE);

        TimerTask hideFlashTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageViewFlash.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };

        timer.schedule(hideFlashTask, 100);
    }

    //Period before a shooter is supposed to shoot//////////////////
    public void preShot(View view){
        mpEarly.start();
        imageViewAnnouncer.setImageDrawable(imgAnnouncerFire);
        flash();

        //Check who shot, that one will lose
        switch(view.getId()){
            case R.id.btnReady1:
                //btnPlayer1.setText(R.string.lose_message);
                btnPlayer1.setImageDrawable(imgPlayer1BtnLose);
                imageViewPlayer1.setImageDrawable(imgPlayer1Early);
                //btnPlayer2.setText(R.string.win_message);
                btnPlayer2.setImageDrawable(imgPlayer2BtnWin);
                break;
            case R.id.btnReady2:
                //btnPlayer1.setText(R.string.win_message);
                btnPlayer1.setImageDrawable(imgPlayer1BtnWin);
                //btnPlayer2.setText(R.string.lose_message);
                btnPlayer2.setImageDrawable(imgPlayer2BtnLose);
                imageViewPlayer2.setImageDrawable(imgPlayer2Early);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        endGame();
    }

    //Can shoot now!///////////////////////////////////////////////////////
    public void waitShot(View v){
        mpShot.start();
        flash();

        //Check who shot, that one will win
        switch(v.getId()){
            case R.id.btnReady1:
                //btnPlayer1.setText(R.string.win_message);
                btnPlayer1.setImageDrawable(imgPlayer1BtnWin);
                imageViewPlayer1.setImageDrawable(imgPlayer1Shoot);
                //btnPlayer2.setText(R.string.lose_message);
                btnPlayer2.setImageDrawable(imgPlayer2BtnLose);
                imageViewPlayer2.setImageDrawable(imgPlayer2Shot);
                break;
            case R.id.btnReady2:
                //btnPlayer1.setText(R.string.lose_message);
                btnPlayer1.setImageDrawable(imgPlayer1BtnLose);
                imageViewPlayer1.setImageDrawable(imgPlayer1Shot);
                //btnPlayer2.setText(R.string.win_message);
                btnPlayer2.setImageDrawable(imgPlayer2BtnWin);
                imageViewPlayer2.setImageDrawable(imgPlayer2Shoot);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

        endGame();
    }

    //End game///////////////////////////////////////////////////////////
    public void endGame(){
        //Cancel the game
        readyShootTask.cancel();

        //Disable both buttons
        btnPlayer1.setEnabled(false);
        btnPlayer2.setEnabled(false);

        cleanTask = new TimerTask() {
            @Override
            public void run() {
                //Turn everything back to normal
                reset();
            }
        };

        //Wait before restarting
        timer.schedule(cleanTask, 4000);
    }

    public void reset(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                // Stuff that updates the UI
                imageViewTitle.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                btnExit.setVisibility(View.VISIBLE);

                btnPlayer1.setVisibility(View.INVISIBLE);
                //btnPlayer1.setText(R.string.not_ready_message);
                btnPlayer1.setImageDrawable(imgPlayer1BtnIdle);
                btnPlayer1.setEnabled(true);
                btnPlayer1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPlayer1ButtonClick(v);
                    }
                });
                imageViewPlayer1.setImageDrawable(imgPlayer1);

                btnPlayer2.setVisibility(View.INVISIBLE);
                //btnPlayer2.setText(R.string.not_ready_message);
                btnPlayer2.setImageDrawable(imgPlayer2BtnIdle);
                btnPlayer2.setEnabled(true);
                btnPlayer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPlayer2ButtonClick(v);
                    }
                });
                imageViewPlayer2.setImageDrawable(imgPlayer2);

                imageViewAnnouncer.setImageDrawable(imgAnnouncer);
                imageViewAnnouncer.setVisibility(View.INVISIBLE);

                //timer.cancel();
            }
        });

        //readyTask.cancel();
        timer.purge();

        player1.reset();
        player2.reset();

        try{
            mpWind.prepare();
            mpWind.seekTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //End application
    public void endApp(View view){
        finish();
    }
}
