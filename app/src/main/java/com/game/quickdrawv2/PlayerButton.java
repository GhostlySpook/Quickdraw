package com.game.quickdrawv2;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

import com.game.quickdrawv2.localmultiplayer.LocalMultiplayerGame;

public class PlayerButton {
    MainActivity activity;
    Game game;

    GameComponentManager gcm;
    ResourceLoader rl;

    ImageButton viewButton;

    //Images for button
    Drawable imgBtnNotReady;
    Drawable imgBtnReady;
    Drawable imgBtnWait;
    Drawable imgBtnFire;
    Drawable imgBtnWin;
    Drawable imgBtnLose;

    Player player;
    int playerNo;

    Character character;

    public PlayerButton(MainActivity activity, Game game, Player player, Character playerCharacter){
        this.activity = activity;
        this.gcm = activity.gcm;
        this.rl = activity.rl;

        this.game = game;
        this.player = player;
        this.character = playerCharacter;

        playerNo = player.getPlayerNo();

        //Assign proper view depending of what player it is
        if(playerNo == 1){
            this.viewButton = gcm.btnPlayer1;

            //Set button images
            this.imgBtnNotReady = rl.imgPlayer1BtnIdle;
            this.imgBtnReady = rl.imgPlayer1BtnReady;
            this.imgBtnWait = rl.imgPlayer1BtnWait;
            this.imgBtnFire = rl.imgPlayer1BtnFire;
            this.imgBtnWin = rl.imgPlayer1BtnWin;
            this.imgBtnLose = rl.imgPlayer1BtnLose;
        }
        else if(playerNo == 2){
            this.viewButton = gcm.btnPlayer2;

            //Set button images
            this.imgBtnNotReady = rl.imgPlayer2BtnIdle;
            this.imgBtnReady = rl.imgPlayer2BtnReady;
            this.imgBtnWait = rl.imgPlayer2BtnWait;
            this.imgBtnFire = rl.imgPlayer2BtnFire;
            this.imgBtnWin = rl.imgPlayer2BtnWin;
            this.imgBtnLose = rl.imgPlayer2BtnLose;
        }
    }

    public void waitReadyMode(){
        this.viewButton.setVisibility(View.VISIBLE);
        this.viewButton.setImageDrawable(imgBtnNotReady);

        this.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyMode();
            }
        });
    }

    public void readyMode(){
        character.showReady();
        viewButton.setImageDrawable(imgBtnReady);
        player.setReady(true);
        game.checkReady();
    }

    public void waitMode(){
        viewButton.setImageDrawable(imgBtnWait);

        //Add failure if pressed a button before the alarm sounds
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.preShot(v);
            }
        });
    }

    public void fireMode(){
        viewButton.setImageDrawable(imgBtnFire);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.waitShot(v);
            }
        });
    }

    public void showLose(){
        viewButton.setImageDrawable(imgBtnLose);
    }

    public void showWin(){
        viewButton.setImageDrawable(imgBtnWin);
    }

    public void hide() { viewButton.setVisibility(View.INVISIBLE); }
}
