package com.game.quickdrawv2.localmultiplayer;

import android.view.View;

import com.game.quickdrawv2.Character;
import com.game.quickdrawv2.GameComponentManager;
import com.game.quickdrawv2.MainActivity;
import com.game.quickdrawv2.MediaPlayerManager;
import com.game.quickdrawv2.Player;
import com.game.quickdrawv2.R;
import com.game.quickdrawv2.ResourceLoader;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LocalMultiplayerGame {
    MainActivity activity;
    GameComponentManager gcm;
    ResourceLoader rl;
    MediaPlayerManager mpm;

    Player player1;
    Player player2;

    LocalMultiplayerPlayerButton player1Btn;
    LocalMultiplayerPlayerButton player2Btn;

    Character player1Character;
    Character player2Character;

    TimerTask readyShootTask;
    TimerTask cleanTask;

    Timer timer = new Timer();

    public LocalMultiplayerGame(MainActivity activity){
        this.activity = activity;
        this.gcm = activity.gcm;
        this.rl = activity.rl;
        this.mpm = activity.mpm;
    }

    public void start(){
        gcm.btnStart.setVisibility(View.INVISIBLE);
        gcm.imageViewTitle.setVisibility(View.INVISIBLE);
        gcm.btnExit.setVisibility(View.INVISIBLE);
        gcm.btnOnline.setVisibility(View.INVISIBLE);
        gcm.imageViewAnnouncer.setVisibility(View.VISIBLE);

        //Start player instances
        player1 = new Player(1);
        player2 = new Player(2);

        player1Character = new Character(activity,1);
        player2Character = new Character(activity,2);

        player1Btn = new LocalMultiplayerPlayerButton(activity, this, player1, player1Character);
        player2Btn = new LocalMultiplayerPlayerButton(activity, this, player2, player2Character);

        player1Btn.waitReadyMode();
        player2Btn.waitReadyMode();
        player1Character.showIdle();
        player2Character.showIdle();
    }

    public void checkReady(){
        //Check if both players are ready
        if(player1.getReady() && player2.getReady()){
            startWaitingPeriod();
        }
    }

    //Prepare waiting period for shooting///////////////////////////
    public void startWaitingPeriod(){
        mpm.mpWind.start();

        //Show waiting buttons
        player1Btn.waitMode();
        player2Btn.waitMode();
        gcm.imageViewAnnouncer.setImageDrawable(rl.imgAnnouncerReady);

        //Get random number from 5 to 10
        Random rand = new Random();

        int delay = (5 + rand.nextInt(6)) * 1000;

        readyShootTask = new TimerTask() {
            @Override
            public void run() {

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        player1Btn.fireMode();
                        player2Btn.fireMode();

                        gcm.imageViewAnnouncer.setImageDrawable(rl.imgAnnouncerFire);

                        //Make sound and stop wind
                        mpm.mpWind.stop();
                        mpm.mpWhistle.start();
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
                activity.runOnUiThread(new Runnable() {
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
        mpm.mpEarly.start();
        mpm.mpWind.stop();
        flash();

        //Check who shot, that one will lose
        switch(view.getId()){
            case R.id.btnReady1:
                player1Btn.showLose();
                player1Character.showEarly();
                player2Btn.showWin();
                break;
            case R.id.btnReady2:
                player2Btn.showLose();
                player2Character.showEarly();
                player1Btn.showWin();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        endGame();
    }

    //Can shoot now!///////////////////////////////////////////////////////
    public void waitShot(View v){
        mpm.mpShot.start();
        flash();

        //Check who shot, that one will win
        switch(v.getId()){
            case R.id.btnReady1:
                player1Btn.showWin();
                player1Character.showShoot();
                player2Btn.showLose();
                player2Character.showShot();
                break;
            case R.id.btnReady2:
                player2Btn.showWin();
                player2Character.showShoot();
                player1Btn.showLose();
                player1Character.showShot();
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
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                // Stuff that updates the UI
                gcm.imageViewTitle.setVisibility(View.VISIBLE);
                gcm.btnStart.setVisibility(View.VISIBLE);
                gcm.btnExit.setVisibility(View.VISIBLE);
                gcm.btnOnline.setVisibility(View.VISIBLE);

                gcm.btnPlayer1.setVisibility(View.INVISIBLE);
                //btnPlayer1.setText(R.string.not_ready_message);
                gcm.btnPlayer1.setImageDrawable(rl.imgPlayer1BtnIdle);
                gcm.btnPlayer1.setEnabled(true);
                gcm.btnPlayer1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        player1Btn.readyMode();
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
                        player2Btn.readyMode();
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
            mpm.mpWind.prepare();
            mpm.mpWind.seekTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
