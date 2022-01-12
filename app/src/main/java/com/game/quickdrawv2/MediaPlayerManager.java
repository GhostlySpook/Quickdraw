package com.game.quickdrawv2;

import android.media.MediaPlayer;

public class MediaPlayerManager {
    MainActivity activity;

    //Prepare media player
    public MediaPlayer mpPlayer1;
    public MediaPlayer mpPlayer2;
    public MediaPlayer mpShot;
    public MediaPlayer mpEarly;
    public MediaPlayer mpWind;
    public MediaPlayer mpWhistle;

    MediaPlayerManager(MainActivity activity){
        this.activity = activity;

        //Load sounds
        //mpPlayer1 = MediaPlayer.create(activity, R.raw.cock);
        //mpPlayer2 = MediaPlayer.create(activity, R.raw.cock);
        mpShot = MediaPlayer.create(activity, R.raw.shot);
        mpEarly = MediaPlayer.create(activity, R.raw.early);
        mpWhistle = MediaPlayer.create(activity, R.raw.whistle);
        mpWind = MediaPlayer.create(activity, R.raw.wind);
    }
}
