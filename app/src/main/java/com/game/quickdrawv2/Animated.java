package com.game.quickdrawv2;

import android.view.View;

public class Animated {
    //Frame it is currently in
    private int animationFrame;

    //Time to pass before changing to another frame (in Frames)
    private int frameDuration;

    //Next frame to be used
    private int nextFrame;
    ///////////////////////////////////////////////////

    private View view;

    public Animated(){
        this.animationFrame = 0;
        this.frameDuration = 0;
        this.nextFrame = 0;

        this.view = null;
    }

    public Animated(View view){
        this.animationFrame = 0;
        this.frameDuration = 0;
        this.nextFrame = 0;

        this.view = view;
    }
}
