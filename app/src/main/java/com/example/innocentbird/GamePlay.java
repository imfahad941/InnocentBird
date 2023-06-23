package com.example.innocentbird;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GamePlay extends SurfaceView implements SurfaceHolder.Callback {
    MainThread mainThread;

    public GamePlay(Context context) {
        super(context);
        SurfaceHolder myHolder = getHolder();
        myHolder.addCallback(this);
        mainThread = new MainThread(myHolder);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mainThread.setIsRunning(true);
        mainThread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                mainThread.setIsRunning(false);
                mainThread.join();
            } catch (InterruptedException e) {
                e.getMessage();
            }
            retry = false;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(AppHolder.getGameManager().gameState ==0){
            AppHolder.getGameManager().gameState = 1;
            AppHolder.getSoundPlay().playSwoosh();

        }else
        {
            AppHolder.getSoundPlay().playWing();
        }
        AppHolder.getGameManager().gameState=1;
        AppHolder.getGameManager().bird.setVelocity(AppHolder.JUMP_VELOCITY);
        return true;
    }
}
