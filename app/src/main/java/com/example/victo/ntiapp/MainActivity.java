package com.example.victo.ntiapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import java.lang.Runnable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.os.Message;
import java.util.Random;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    private TextView mTextMessage;
    private static final String TAG = "myMessage";



    private TextView snackText;
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        Log.i(TAG, "onCreate");

        this.gestureDetector = new GestureDetectorCompat(this,this);
        gestureDetector.setOnDoubleTapListener(this);

        Button randomizeButton = (Button)findViewById(R.id.randomizeButton);

        randomizeButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        String[] snacks = {"Äpple","Banan","Päron","Mars Bar","Yoghurt","Kex Choklad","Toast","Sallad","Fryst måltid","frystmåltid + Sallad", "popcorn","Pringles","Klubbor","patroner","Risifrutti","pingvinstång","vicks halstabletter","Center rulle","kinder choklad","stor skittles","skittles","haribo påse","malacco påse","delicatobollar","bounty","twix","yankee bar","Japp KING-size","Marabou 100g choklad","Mackor" };
                        String[] drinks = {"Coca Cola", "Coca Cola Cherry", "Coca Cola Vanilj", "Fanta", "Festis", "Mer", "Nocco", "Celcius", "Loka Flaska", "Loka Crush", "Loka burk", "Alovera", "Stor kaffe", "Varm Choklad", "Te", "Rosh?", "Snapple", "Pucko", "Pago juice", "Yoggi Yalla"};
                        final TextView snackText = (TextView)findViewById(R.id.snackText);
                        final TextView drinkText = (TextView)findViewById(R.id.drinkText);
                        Random rand = new Random();


                        Handler handler = new Handler(){
                          @Override
                            public void handleMessage(Message msg){
                              int randomNum = rand.nextInt(snacks.length);
                              snackText.setText(snacks[randomNum]);
                              int randomNum2 = rand.nextInt(drinks.length);
                              drinkText.setText(drinks[randomNum2]);
                          }
                        };

                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                for(int i=0;i<50;i++){
                                    handler.sendEmptyMessage(0);
                                    try {
                                        Thread.sleep(50);
                                    }catch (InterruptedException ie){
                                        Log.i(TAG, "Thread interrupted");
                                    }

                                }
                            }
                        };
                        Thread myThread = new Thread(r);
                        myThread.start();

                        int randomNum = rand.nextInt(snacks.length);
                        snackText.setText(snacks[randomNum]);
                        int randomNum2 = rand.nextInt(drinks.length);
                        drinkText.setText(drinks[randomNum2]);
                    }
                }
        );

        //randomizeButton.setOnLongClickListener(
        //        new Button.OnLongClickListener(){
        //            public boolean onLongClick(View v){
        //                final TextView snackText = (TextView)findViewById(R.id.snackText);
        //                final String[] snacks;
        //                snacks = new String[5];
        //                snacks[0] = "Äpple";
        //                snacks[1] = "Banan";
        //                snacks[2] = "Päron";
        //                snacks[3] = "Mars Bar";
        //                snacks[4] = "Yoghurt";

                        //MainActivity random = new MainActivity();
                        //int delay = 200;

                        //(new Handler()).postDelayed(new Runnable(){
                        //    @Override
                        //    public void run(){
                        //        random.randomSnack(snacks);
                        //    }
                        //}, delay);

                        //(new Handler()).postDelayed(this::randomSnack(snacks), 200);    <--- Hur det fungerade förut


                        //Slutar aldrig att randomiza... v

                        //final Handler handler = new Handler();
                        //final int delay = 200; //milliseconds

                        //handler.postDelayed(new Runnable(){
                        //    public void run(){
                        //        int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
                        //       snackText.setText(snacks[randomNum]);
                        //        handler.postDelayed(this, delay);
                        //    }
                        //}, delay);

        //return false;
        //}
        //}
        //);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void randomSnack(String[] snacks) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
        snackText.setText(snacks[randomNum]);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        snackText.setText("Ranomize");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        snackText.setText("Ranomize");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        snackText.setText("Ranomize");

        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        snackText.setText("Ranomize");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        snackText.setText("Ranomize");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        snackText.setText("Ranomize");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        snackText.setText("Ranomize");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        snackText.setText("Ranomize");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        snackText.setText("Ranomize");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG, "onInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }


}
