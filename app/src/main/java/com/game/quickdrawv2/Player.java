package com.game.quickdrawv2;

public class Player {

    //Game related/////////////////////////////////////
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

    public Player(Animated animated){
        score = 0;
        canShoot = false;
        ready = false;
        this.animated = animated;
    }

    public void setReady(boolean value){
        ready = value;
    }

    public boolean getReady(){ return ready; }

    public void reset(){
        score = 0;
        canShoot = false;
        ready = false;
    }
}
