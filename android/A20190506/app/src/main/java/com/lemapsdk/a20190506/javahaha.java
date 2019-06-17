package com.lemapsdk.a20190506;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class javahaha extends Activity{
    Thread startTimer;
    long startTime=3000;
    boolean isPaused=false;
    boolean isActive=true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.start);
        setStartTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused=false;
    }

    void setStartTime(){
        startTimer=new Thread(){
            public void run(){
                try {
                    long ms = 0;
                    while (isActive && ms < startTime) {
                        sleep(100);
                        if (!isPaused)
                            ms += 100;
                    }
                    Intent qq=new Intent(javahaha.this,hahaha.class);
                    startActivity(qq);

                }
                catch (Exception ee){
                    Log.v("start",ee.getMessage());
                }
                finally {
                    finish();
                }


            }
        };
        startTimer.start();

    }
    void setFullScreen(){

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flog= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window mywindow=this.getWindow();
        mywindow.setFlags(flog,flog);
    }
}
