package com.game.quickdrawv2;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GameComponentManager {
    MainActivity activity;

    public Button btnStart;
    public ImageButton btnPlayer1;
    public ImageButton btnPlayer2;
    public Button btnExit;

    public ImageView imageViewBackground;
    public ImageView imageViewTitle;
    public ImageView imageViewPlayer1;
    public ImageView imageViewPlayer2;
    public ImageView imageViewAnnouncer;
    public ImageView imageViewFlash;

    GameComponentManager(MainActivity activity){
        this.activity = activity;

        //Assign Buttons and ImageViews
        btnStart = (Button)activity.findViewById(R.id.btnStart);
        btnPlayer1 = (ImageButton)activity.findViewById(R.id.btnReady1);
        btnPlayer2 = (ImageButton)activity.findViewById(R.id.btnReady2);
        btnExit = (Button)activity.findViewById(R.id.btnExit);

        imageViewBackground = (ImageView)activity.findViewById(R.id.imgBackground);
        imageViewTitle = (ImageView)activity.findViewById(R.id.imgTitle);
        imageViewPlayer1 = (ImageView)activity.findViewById(R.id.imgPlayer1);
        imageViewPlayer2 = (ImageView)activity.findViewById(R.id.imgPlayer2);
        imageViewAnnouncer = activity.findViewById(R.id.imgAnnouncer);
        imageViewFlash = (ImageView)activity.findViewById(R.id.imgFlash);
    }
}
