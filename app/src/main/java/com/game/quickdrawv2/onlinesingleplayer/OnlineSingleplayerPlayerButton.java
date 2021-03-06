package com.game.quickdrawv2.onlinesingleplayer;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

import com.game.quickdrawv2.Character;
import com.game.quickdrawv2.GameComponentManager;
import com.game.quickdrawv2.MainActivity;
import com.game.quickdrawv2.Player;
import com.game.quickdrawv2.PlayerButton;
import com.game.quickdrawv2.ResourceLoader;
import com.game.quickdrawv2.localmultiplayer.LocalMultiplayerGame;

public class OnlineSingleplayerPlayerButton extends PlayerButton {
    MainActivity activity;
    OnlineSingleplayerGame game;

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

    OnlineSingleplayerPlayerButton(MainActivity activity, OnlineSingleplayerGame game, Player player, Character playerCharacter){
        super(activity, game, player, playerCharacter);
    }

    /*public void waitReadyMode(){
        this.viewButton.setVisibility(View.VISIBLE);
        this.viewButton.setImageDrawable(imgBtnNotReady);

        this.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyMode();
            }
        });
    }*/

    /*public void readyMode(){
        character.showReady();
        viewButton.setImageDrawable(imgBtnReady);
        player.setReady(true);
        game.checkReady();
    }*/

    /*public void waitMode(){
        viewButton.setImageDrawable(imgBtnWait);

        //Add failure if pressed a button before the alarm sounds
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.preShot(v);
            }
        });
    }*/

    /*public void fireMode(){
        viewButton.setImageDrawable(imgBtnFire);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.waitShot(v);
            }
        });
    }*/

    /*public void showLose(){
        viewButton.setImageDrawable(imgBtnLose);
    }

    public void showWin(){
        viewButton.setImageDrawable(imgBtnWin);
    }*/
}
