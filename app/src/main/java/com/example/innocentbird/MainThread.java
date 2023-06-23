package com.example.innocentbird;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    SurfaceHolder mySurfaceHolder;
    long timeSpent;
    long kickOfTime;
    long WAIT = 31; // the time it takes for the frame to refresh in milliseconds
    boolean Running;
    private static Canvas canvas;


    public MainThread(SurfaceHolder surfaceHolder) {
        this.mySurfaceHolder = surfaceHolder;
        Running = true;

    }

    @Override
    public void run() {
        while (Running) {
            kickOfTime = SystemClock.uptimeMillis();
            canvas = null;
            try {
                canvas = mySurfaceHolder.lockCanvas();
                synchronized (mySurfaceHolder) {
                    AppHolder.getGameManager().backgroundAnimation(canvas);
                    AppHolder.getGameManager().birdAnimation(canvas);
                    AppHolder.getGameManager().scrollingTube(canvas);
                }

            } catch (Exception e) {
                e.getMessage();
            } finally {
                if (canvas != null) {
                    try {
                        mySurfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
            timeSpent = SystemClock.uptimeMillis() - kickOfTime;
            if (timeSpent < WAIT) {
                try {
                    Thread.sleep(WAIT - timeSpent);
                } catch (Exception e) {
                    e.getMessage();
                }
            }

        }
    }
    public boolean isRunning()
    {
        return Running;
    }
    public void setIsRunning(boolean state)
    {
        Running =state;
    }
}

/* Difference Between Sleep and Join Method

--> The Sleep method causes the currently running thread to temporarily cease execution for
certain period of time in milliseconds which is subjected to the precision and accuracy of system timers
and schedulers;


-->The join method is used whenever one thread execution depends on another thread completion.
This implies that the mainThread has to wait for the other threads to finish execution.

 */
