package com.game.quickdrawv2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class ResourceLoader {
    Resources resources;
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

    ResourceLoader(MainActivity activity){
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
