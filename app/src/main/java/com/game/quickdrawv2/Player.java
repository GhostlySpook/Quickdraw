package com.game.quickdrawv2;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Player {

    //Game related/////////////////////////////////////
    private int playerNo;

    private int score;
    private boolean canShoot;
    private boolean ready;
    private Animated animated;
    /////////////////////////////////////////////////////

    public Player(){
        score = 0;
        canShoot = false;
        ready = false;
    }

    public Player(int playerNo){
        score = 0;
        canShoot = false;
        ready = false;
        this.playerNo = playerNo;
    }

    public void reset(){
        score = 0;
        canShoot = false;
        ready = false;
    }

    public void setPlayerNo(int value){ playerNo = value; };

    public int getPlayerNo(){ return playerNo; };

    public void setReady(boolean value){
        ready = value;
    }

    public boolean getReady(){ return ready; }
}
