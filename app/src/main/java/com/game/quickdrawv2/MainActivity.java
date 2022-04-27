package com.game.quickdrawv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.game.quickdrawv2.localmultiplayer.LocalMultiplayerGame;
import com.game.quickdrawv2.onlinesingleplayer.OnlineGhostGame;
import com.game.quickdrawv2.onlinesingleplayer.OnlineSingleplayerGame;
import com.game.quickdrawv2.singleplayer.GhostSingleplayerGame;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Resources resources;
    MainActivity activity;

    Button btnChallenge;

    public GameComponentManager gcm;
    public ResourceLoader rl;
    public MediaPlayerManager mpm;

    int time;

    //Start creation//////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

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
        /*animBackground = new Animated(gcm.imageViewBackground);
        animPlayer1 = new Animated(gcm.imageViewPlayer1);
        animPlayer2 = new Animated(gcm.imageViewPlayer2);*/
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else{
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(!hasFocus) {
            if (Build.VERSION.SDK_INT < 16) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
            else{
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
        }
    }

    public void onStartButtonClick(View view){
        gcm.update();
        //LocalMultiplayerGame game = new LocalMultiplayerGame(this);
        Game game = new Game(this);
        game.start();
    }

    /*public void onOnlineButtonClick(View view){
        setContentView(R.layout.online_multiplayer);

        //Get saved users and display on the table
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://quickdraw-game.herokuapp.com/reaction";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    //Get table
                    Context ctx = getApplicationContext();
                    TableLayout tblLayout = findViewById(R.id.tblReactions);

                    @Override
                    public void onResponse(String response) {
                        try{
                            //Create button to get reaction
                            class PlayButton extends androidx.appcompat.widget.AppCompatButton{
                                public Reaction reaction;

                                PlayButton(Context ctx, Reaction reaction){
                                    super(ctx);
                                    this.reaction = reaction;
                                }
                            }

                            System.out.println("Response: " + response);

                            //Good response
                            if(!(response.equals("null"))){

                                JSONArray reader = new JSONArray(response);

                                int len = reader.length();

                                System.out.println("Length of array: " + len);

                                //Fill table from obtained array
                                for(int i = 0; i < len; i++){
                                    JSONObject obj = reader.getJSONObject(i);

                                    String sId = obj.getString("id");
                                    String sName = obj.getString("name");
                                    String sTime = obj.getString("time");

                                    final PlayButton btnReaction = new PlayButton(ctx, new Reaction(Integer.parseInt(sId), sName, Integer.parseInt(sTime)));

                                    btnReaction.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            setContentView(R.layout.activity_main);
                                            gcm.update();

                                            System.out.println("Changed activity");
                                            OnlineSingleplayerGame game = new OnlineSingleplayerGame(activity, btnReaction.reaction);
                                            System.out.println("Started game");
                                            game.start();
                                        }
                                    });

                                    TableRow newRow = new TableRow(ctx);

                                    TextView id = new TextView(ctx);
                                    TextView name = new TextView(ctx);
                                    TextView time = new TextView(ctx);

                                    //Add information to row and add row
                                    id.setText(sId);
                                    id.setTextColor(Color.WHITE);

                                    name.setText(sName);
                                    name.setTextColor(Color.WHITE);

                                    time.setText(sTime);
                                    time.setTextColor(Color.WHITE);

                                    btnReaction.setText("Play!");
                                    btnReaction.setBackgroundDrawable(rl.imgBtnBackground);
                                    btnReaction.setTextColor(Color.BLACK);

                                    newRow.addView(id);
                                    newRow.addView(name);
                                    newRow.addView(time);
                                    newRow.addView(btnReaction);

                                    tblLayout.addView(newRow);
                                }
                            }
                            else{
                                TableRow newRow = new TableRow(ctx);

                                TextView id = new TextView(ctx);
                                System.out.println("Nothing was obtained");
                                id.setText("Didn't find any saved reactions");

                                newRow.addView(id);
                                tblLayout.addView(newRow);
                            }
                        }
                        catch(Exception error){
                            System.out.println("Error after getting normal response: " + error);
                        }
                    }
                },
                new Response.ErrorListener() {
                    //Get table
                    Context ctx = getApplicationContext();
                    TableLayout tblLayout = findViewById(R.id.tblReactions);

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error response: " + error);
                        TableRow newRow = new TableRow(ctx);

                        TextView id = new TextView(ctx);
                        id.setText("Connection error");

                        newRow.addView(id);
                        tblLayout.addView(newRow);
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }*/

    public void onOnlineButtonClick(View view){
        setContentView(R.layout.online_multiplayer);

        TextView txtInitials = findViewById(R.id.txtInitials);
        txtInitials.setText("Loading...");

        btnChallenge = findViewById(R.id.btnChallengeFastest);
        btnChallenge.setVisibility(View.INVISIBLE);

        //Get saved users and display on the table
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://quickdraw-game.herokuapp.com/reaction/fastest";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    //Get table
                    Context ctx = getApplicationContext();
                    TextView txtInitials = findViewById(R.id.txtInitials);
                    TableLayout tblLayout = findViewById(R.id.tblReactions);

                    @Override
                    public void onResponse(String response) {
                        try{
                            System.out.println("Response: " + response);

                            //Good response
                            if(!(response.equals("null"))){

                                JSONObject reader = new JSONObject(response);

                                //Fill table from obtained array
                                String sId = reader.getString("id");
                                String sName = reader.getString("name");
                                String sTime = reader.getString("time");

                                time = Integer.parseInt(sTime);

                                btnChallenge.setVisibility(View.VISIBLE);
                                txtInitials.setText(sName);
                            }
                            else{
                                System.out.println("Nothing was obtained");
                                txtInitials.setText("Not found");
                            }
                        }
                        catch(Exception error){
                            System.out.println("Error after getting normal response: " + error);
                        }
                    }
                },
                new Response.ErrorListener() {
                    //Get table
                    //Context ctx = getApplicationContext();
                    TextView txtInitials = findViewById(R.id.txtInitials);

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error response: " + error);
                        txtInitials.setText("Error");
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void onChallengeButtonClick(View view){
        setContentView(R.layout.activity_main);
        //this.activity = this;
        gcm.update();

        System.out.println("Changed activity");
        System.out.println("Time: ");
        System.out.println(Integer.toString(time));
        OnlineGhostGame game = new OnlineGhostGame(this, time);
        System.out.println("Started ghost game");
        game.start();
    }

    public void onUploadButtonClick(View view){
        setContentView(R.layout.activity_main);
        gcm.update();

        int time = 1000;

        GhostSingleplayerGame game = new GhostSingleplayerGame(activity, time);
        game.start();
        System.out.println("Started ghost activity");
    }

    public void onReturnButtonClick(View view){
        btnChallenge.setVisibility(View.INVISIBLE);
        setContentView(R.layout.activity_main);
    }

    //End application
    public void endApp(View view){
        finish();
    }
}
