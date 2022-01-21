package com.game.quickdrawv2;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.widget.ImageView;

import com.game.quickdrawv2.GameComponentManager;
import com.game.quickdrawv2.MainActivity;
import com.game.quickdrawv2.MediaPlayerManager;
import com.game.quickdrawv2.R;
import com.game.quickdrawv2.ResourceLoader;

public class Character {
    MainActivity activity;

    GameComponentManager gcm;
    ResourceLoader rl;
    MediaPlayerManager mpm;

    //Component related
    ImageView viewCharacter;
    ImageView viewGun;

    //Images used
    Drawable imgNormal;
    Drawable imgReady;
    Drawable imgShoot;
    Drawable imgShot;
    Drawable imgEarly;

    //Sounds used
    MediaPlayer mpCock;

    int playerNo;

    public Character(MainActivity activity, int playerNo){
        this.activity = activity;

        this.gcm = activity.gcm;
        this.rl = activity.rl;
        //this.mpm = activity.mpm;

        //Prepare sound
        mpCock = MediaPlayer.create(activity, R.raw.cock);

        this.playerNo = playerNo;

        //Assign proper view depending of what player it is
        if(playerNo == 1){
            this.viewCharacter = gcm.imageViewPlayer1;
            //this.viewGun =

            //Set player images
            this.imgNormal = rl.imgPlayer1;
            this.imgEarly = rl.imgPlayer1Early;
            this.imgReady = rl.imgPlayer1Ready;
            this.imgShoot = rl.imgPlayer1Shoot;
            this.imgShot = rl.imgPlayer1Shot;
        }
        else if(playerNo == 2){
            this.viewCharacter = gcm.imageViewPlayer2;
            //this.viewGun =

            //Set player images
            this.imgNormal = rl.imgPlayer2;
            this.imgEarly = rl.imgPlayer2Early;
            this.imgReady = rl.imgPlayer2Ready;
            this.imgShoot = rl.imgPlayer2Shoot;
            this.imgShot = rl.imgPlayer2Shot;
        }
    }

    public void showIdle(){
        viewCharacter.setImageDrawable(imgNormal);
    }

    public void showReady(){
        viewCharacter.setImageDrawable(imgReady);
        mpCock.start();
    }

    public void showEarly(){
        viewCharacter.setImageDrawable(imgEarly);
    }

    public void showShot(){
        viewCharacter.setImageDrawable(imgShot);
    }

    public void showShoot(){
        viewCharacter.setImageDrawable(imgShoot);
    }
}
