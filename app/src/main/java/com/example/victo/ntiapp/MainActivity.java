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
                        String[] snacks;
                        snacks = new String[5];
                        snacks[0] = "Äpple";
                        snacks[1] = "Banan";
                        snacks[2] = "Päron";
                        snacks[3] = "Mars Bar";
                        snacks[4] = "Yoghurt";

                        int randomNum = ThreadLocalRandom.current().nextInt(0,5);

                        TextView snackText = (TextView)findViewById(R.id.snackText);
                        snackText.setText(snacks[randomNum]);
                    }
                }
        );

        randomizeButton.setOnLongClickListener(
                new Button.OnLongClickListener(){
                    public boolean onLongClick(View v){
                        TextView snackText = (TextView)findViewById(R.id.snackText);
                        String[] snacks;
                        snacks = new String[5];
                        snacks[0] = "Äpple";
                        snacks[1] = "Banan";
                        snacks[2] = "Päron";
                        snacks[3] = "Mars Bar";
                        snacks[4] = "Yoghurt";

                        int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
                        snackText.setText(snacks[randomNum]);

                        return false;
                    }
                }
        );
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
