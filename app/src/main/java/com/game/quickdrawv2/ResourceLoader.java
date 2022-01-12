package com.game.quickdrawv2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class ResourceLoader {
    MainActivity activity;
    Resources resources;
    //Images that are going to be used
    public Drawable imgPlayer1;
    public Drawable imgPlayer1Early;
    public Drawable imgPlayer1Ready;
    public Drawable imgPlayer1Shoot;
    public Drawable imgPlayer1Shot;

    public Drawable imgPlayer1BtnIdle;
    public Drawable imgPlayer1BtnReady;
    public Drawable imgPlayer1BtnWait;
    public Drawable imgPlayer1BtnFire;
    public Drawable imgPlayer1BtnWin;
    public Drawable imgPlayer1BtnLose;

    public Drawable imgPlayer2;
    public Drawable imgPlayer2Early;
    public Drawable imgPlayer2Ready;
    public Drawable imgPlayer2Shoot;
    public Drawable imgPlayer2Shot;

    public Drawable imgPlayer2BtnIdle;
    public Drawable imgPlayer2BtnReady;
    public Drawable imgPlayer2BtnWait;
    public Drawable imgPlayer2BtnFire;
    public Drawable imgPlayer2BtnWin;
    public Drawable imgPlayer2BtnLose;

    public Drawable imgAnnouncer;
    public Drawable imgAnnouncerReady;
    public Drawable imgAnnouncerFire;

    ResourceLoader(MainActivity activity){
        this.activity = activity;

        resources = activity.getResources();

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
    }
}
