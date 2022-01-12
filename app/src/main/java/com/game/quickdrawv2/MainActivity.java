package com.game.quickdrawv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.game.quickdrawv2.localmultiplayer.LocalMultiplayerGame;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Resources resources;

    public GameComponentManager gcm;
    public ResourceLoader rl;
    public MediaPlayerManager mpm;

    //Animation instances
    Animated animBackground;
    Animated animPlayer1;
    Animated animPlayer2;

    //Start creation//////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else{
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
                        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                        decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
                        /*ActionBar actionBar = getActionBar();
                        actionBar.hide();*/
        }

        setContentView(R.layout.activity_main);
        gcm = new GameComponentManager(this);
        rl = new ResourceLoader(this);
        mpm = new MediaPlayerManager(this);
        resources = getResources();

        //Assign animated elements
        animBackground = new Animated(gcm.imageViewBackground);
        animPlayer1 = new Animated(gcm.imageViewPlayer1);
        animPlayer2 = new Animated(gcm.imageViewPlayer2);
    }

    public void onStartButtonClick(View view){
        LocalMultiplayerGame game = new LocalMultiplayerGame(this);
        game.start();
    }

    //End application
    public void endApp(View view){
        finish();
    }
}
