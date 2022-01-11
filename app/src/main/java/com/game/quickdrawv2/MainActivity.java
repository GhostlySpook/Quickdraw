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

    GameComponentManager gcm;
    ResourceLoader rl;

    Player player1;
    Player player2;

    //Animation instances
    Animated animBackground;
    Animated animPlayer1;
    Animated animPlayer2;

    TimerTask readyShootTask;
    TimerTask cleanTask;

    Timer timer = new Timer();

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
        gcm = new GameComponentManager(this);
        rl = new ResourceLoader(this);
        resources = getResources();

        //Assign animated elements
        animBackground = new Animated(gcm.imageViewBackground);
        animPlayer1 = new Animated(gcm.imageViewPlayer1);
        animPlayer2 = new Animated(gcm.imageViewPlayer2);

        //Start player instances
        player1 = new Player(animPlayer1);
        player2 = new Player(animPlayer2);

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
        gcm.imageViewTitle.setVisibility(View.INVISIBLE);
        gcm.btnExit.setVisibility(View.INVISIBLE);
        gcm.imageViewAnnouncer.setVisibility(View.VISIBLE);

        gcm.btnPlayer1.setVisibility(View.VISIBLE);
        gcm.btnPlayer2.setVisibility(View.VISIBLE);
    }

    //Ask if players are ready/////////////////////////////////////////////
    public void onPlayer1ButtonClick(View view){
        player1.setReady(true);
        gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnReady);
        gcm.imageViewPlayer1.setImageDrawable(rl.imgPlayer1Ready);
        mpPlayer1.start();
        checkReady();
    }

    public void onPlayer2ButtonClick(View view){
        player2.setReady(true);
        gcm.btnPlayer2.setImageDrawable(rl.imgPlayer2BtnReady);
        gcm.imageViewPlayer2.setImageDrawable(rl.imgPlayer2Ready);
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
        gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnWait);
        gcm.btnPlayer2.setImageDrawable(rl.imgPlayer2BtnWait);
        gcm.imageViewAnnouncer.setImageDrawable(rl.imgAnnouncerReady);

        //Add failure if pressed a button before the alarm sounds
        gcm.btnPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preShot(v);
            }
        });

        gcm.btnPlayer2.setOnClickListener(new View.OnClickListener() {
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
                gcm.btnPlayer1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        waitShot(v);
                    }
                });

                gcm.btnPlayer2.setOnClickListener(new View.OnClickListener() {
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
                        gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnFire);
                        //btnPlayer2.setText(R.string.fire_message);
                        gcm.btnPlayer2.setImageDrawable(rl.imgPlayer2BtnFire);
                        gcm.imageViewAnnouncer.setImageDrawable(rl.imgAnnouncerFire);
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
        gcm.imageViewFlash.setVisibility(View.VISIBLE);

        TimerTask hideFlashTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gcm.imageViewFlash.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };

        timer.schedule(hideFlashTask, 100);
    }

    //Period before a shooter is supposed to shoot//////////////////
    public void preShot(View view){
        mpEarly.start();
        mpWind.stop();
        flash();

        //Check who shot, that one will lose
        switch(view.getId()){
            case R.id.btnReady1:
                //btnPlayer1.setText(R.string.lose_message);
                gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnLose);
                gcm.imageViewPlayer1.setImageDrawable(rl.imgPlayer1Early);
                //btnPlayer2.setText(R.string.win_message);
                gcm.btnPlayer2.setImageDrawable(rl.imgPlayer2BtnWin);
                break;
            case R.id.btnReady2:
                //btnPlayer1.setText(R.string.win_message);
                gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnWin);
                //btnPlayer2.setText(R.string.lose_message);
                gcm.btnPlayer2.setImageDrawable(rl.imgPlayer2BtnLose);
                gcm.imageViewPlayer2.setImageDrawable(rl.imgPlayer2Early);
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
                gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnWin);
                gcm.imageViewPlayer1.setImageDrawable(rl.imgPlayer1Shoot);
                //btnPlayer2.setText(R.string.lose_message);
                gcm.btnPlayer2.setImageDrawable(rl.imgPlayer2BtnLose);
                gcm.imageViewPlayer2.setImageDrawable(rl.imgPlayer2Shot);
                break;
            case R.id.btnReady2:
                //btnPlayer1.setText(R.string.lose_message);
                gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnLose);
                gcm.imageViewPlayer1.setImageDrawable(rl.imgPlayer1Shot);
                //btnPlayer2.setText(R.string.win_message);
                gcm.btnPlayer2.setImageDrawable(rl.imgPlayer2BtnWin);
                gcm.imageViewPlayer2.setImageDrawable(rl.imgPlayer2Shoot);
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
        gcm.btnPlayer1.setEnabled(false);
        gcm.btnPlayer2.setEnabled(false);

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
                gcm.imageViewTitle.setVisibility(View.VISIBLE);
                gcm.btnStart.setVisibility(View.VISIBLE);
                gcm.btnExit.setVisibility(View.VISIBLE);

                gcm.btnPlayer1.setVisibility(View.INVISIBLE);
                //btnPlayer1.setText(R.string.not_ready_message);
                gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnIdle);
                gcm.btnPlayer1.setEnabled(true);
                gcm.btnPlayer1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPlayer1ButtonClick(v);
                    }
                });
                gcm.imageViewPlayer1.setImageDrawable(rl.imgPlayer1);

                gcm.btnPlayer2.setVisibility(View.INVISIBLE);
                //btnPlayer2.setText(R.string.not_ready_message);
                gcm.btnPlayer2.setImageDrawable(rl.imgPlayer2BtnIdle);
                gcm.btnPlayer2.setEnabled(true);
                gcm.btnPlayer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPlayer2ButtonClick(v);
                    }
                });
                gcm.imageViewPlayer2.setImageDrawable(rl.imgPlayer2);

                gcm.imageViewAnnouncer.setImageDrawable(rl.imgAnnouncer);
                gcm.imageViewAnnouncer.setVisibility(View.INVISIBLE);

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
