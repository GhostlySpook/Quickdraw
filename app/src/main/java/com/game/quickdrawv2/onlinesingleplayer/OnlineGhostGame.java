package com.game.quickdrawv2.onlinesingleplayer;

import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.game.quickdrawv2.Game;
import com.game.quickdrawv2.MainActivity;
import com.game.quickdrawv2.R;
import com.game.quickdrawv2.Reaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;

public class OnlineGhostGame extends OnlineSingleplayerGame {
    TextView lblInitials;
    TextView txtInputInitials;
    Button btnUpload;
    Button btnCancel;

    long playerReactionTime;
    long startTime;
    long endTime;

    public OnlineGhostGame(MainActivity activity, int time){
        super(activity, new Reaction());

        this.reaction.setTime(time);

        playerReactionTime = 0;
        startTime = 0;
        endTime = 0;
    }

    @Override
    public void start(){
        lblInitials = activity.findViewById(R.id.lblHighscore1);
        txtInputInitials = activity.findViewById(R.id.txtInputInitials);
        btnUpload = activity.findViewById(R.id.btnUpload);
        btnCancel = activity.findViewById(R.id.btnCancel);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadHighscoreClick(playerReactionTime);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        super.start();
    }

    //Prepare waiting period for shooting///////////////////////////
    @Override
    public void startWaitingPeriod(){
        //Start wind
        mpm.mpWind.start();

        //Show waiting buttons
        player1Btn.waitMode();
        //Prepare computer
        player2Character.showReady();
        //player2Btn.waitMode();
        gcm.imageViewAnnouncer.setImageDrawable(rl.imgAnnouncerReady);

        //Get random number from 5 to 10
        Random rand = new Random();

        int delay = (5 + rand.nextInt(6)) * 1000;

        readyShootTask = new TimerTask() {
            @Override
            public void run() {
                startTime = System.currentTimeMillis();

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //Make it available for player to shoot
                        player1Btn.fireMode();

                        //Activate time of reaction for computer!
                        computerShootTask = new TimerTask(){
                            @Override
                            public void run() {
                                activity.runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        mpm.mpShot.start();
                                        flash();
                                        //player2Btn.showWin();
                                        player2Character.showShoot();
                                        player1Btn.showLose();
                                        player1Character.showShot();

                                        winner = WINNER_2;

                                        endGame();
                                    }
                                });

                            }
                        };

                        timer.schedule(computerShootTask, reaction.getTime());

                        //player2Btn.fireMode();

                        gcm.imageViewAnnouncer.setImageDrawable(rl.imgAnnouncerFire);

                        //Make sound and stop wind
                        mpm.mpWind.stop();
                        mpm.mpWhistle.start();
                    }
                });
            }
        };

        //Create a timer before the shot is waited for
        timer.schedule(readyShootTask, delay);
    }

    @Override
    public void waitShot(View view){
        endTime = System.currentTimeMillis();

        playerReactionTime = endTime - startTime;

        super.waitShot(view);
    }

    @Override
    public void endGame(){
        //Cancel the game
        readyShootTask.cancel();

        //Disable both buttons
        gcm.btnPlayer1.setEnabled(false);
        //gcm.btnPlayer2.setEnabled(false);

        cleanTask = new TimerTask() {
            @Override
            public void run() {

                //Turn everything back to normal

                //Upload highscore if player 1 won
                if(winner == WINNER_1){
                    showUploadPrompt();
                }else{
                    reset();
                }
            }
        };

        //Wait before restarting
        timer.schedule(cleanTask, 4000);
    }

    public void showUploadPrompt(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblInitials.setVisibility(View.VISIBLE);
                txtInputInitials.setVisibility(View.VISIBLE);
                btnUpload.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);

                txtInputInitials.setText("SNK");
            }
        });
    }

    public void onUploadHighscoreClick(long time) {
        System.out.println("Pressed upload button");

        System.out.println(String.valueOf(time));
        try{
            if(time > 0){

                String name = txtInputInitials.getText().toString();

                System.out.println("Name is : " + name);

                //Correct initials
                if("".equals(name) && name.length() > 3 && name.length() < 1){
                    name = "SNK";
                }

                //Send information of highscore

                //Prepare JSON to send
                /*JSONObject jsonBody = new JSONObject();
                jsonBody.put("Name", initials);
                jsonBody.put("Time", playerReactionTime);
                final String requestBody = jsonBody.toString();*/

                RequestQueue queue = Volley.newRequestQueue(activity);
                String url ="https://quickdraw-game.herokuapp.com/reaction";

                final String finalName = name;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            //Get table
                            @Override
                            public void onResponse(String response) {
                                try{
                                    System.out.println("Highscore sent!");
                                }
                                catch(Exception error){
                                    System.out.println("Error after getting normal response: " + error);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Error after trying to get response");
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() {
                        // below line we are creating a map for
                        // storing our values in key and value pair.
                        Map<String, String> params = new HashMap<String, String>();

                        // on below line we are passing our key
                        // and value pair to our parameters.
                        params.put("Name", finalName);
                        params.put("Time", Long.toString(playerReactionTime));

                        System.out.println("Parameters being returned");

                        // at last we are
                        // returning our params.
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }

                    /*@Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            return null;
                        }
                    }*/

                };

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        }
        catch(Exception error){
            System.out.println("Exception after pressing upload button: " + error);
        }
    }
}
