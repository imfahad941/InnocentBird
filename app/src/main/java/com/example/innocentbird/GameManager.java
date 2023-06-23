package com.example.innocentbird;

import android.app.Activity;
import android.app.AsyncNotedAppOp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    BgImage bgImage;
    FlyingBird bird;
    static int gameState;
    ArrayList<TubeCollection>tubeCollections;
    Random rand;
    int scoreCount;
    int winningTube;
    Paint designPaint;

    public GameManager() {
        bgImage=new BgImage();
        bird=new FlyingBird();
        gameState=0;
        tubeCollections=new ArrayList<>();
        rand=new Random();
        generateTubeObject();
        initScoreVariables();

    }

    public void initScoreVariables()
    {
        scoreCount=0;
        winningTube=0;
        designPaint=new Paint();
        designPaint.setColor(Color.YELLOW);
        designPaint.setTextSize(250);
        designPaint.setStyle(Paint.Style.FILL);
        designPaint.setFakeBoldText(true);
        designPaint.setShadowLayer(5,0f,20.0f,Color.BLACK);

    }

    /*
    gameState==0 : not running
    gameState==1 : the game is running
    gameState==2 : the game is over
     */

    public void generateTubeObject()
    {
        for(int j=0 ;j<AppHolder.tubeGap;j++)
        {
            int tubeX=AppHolder.SCRN_WIDTH_X + j*AppHolder.tubeDistance;
            int upTubeCollection_Y= AppHolder.minimumTubeCollection_Y;
            rand.nextInt(AppHolder.maximumTubeCollection_Y - AppHolder.minimumTubeCollection_Y +1);
            TubeCollection tubeCollection=new TubeCollection(tubeX,upTubeCollection_Y);
            tubeCollections.add(tubeCollection);
        }
    }

    public void scrollingTube(Canvas can)
    {
        if(gameState==1)
        {

            if((tubeCollections.get(winningTube).getXtube() < bird.getX() + AppHolder.getBitmapControl().getBirdWidth())
             && (tubeCollections.get(winningTube).getUpTube_Y() > bird.getY()
            || tubeCollections.get(winningTube).getDownTube_Y() < (bird.getY() +
                    AppHolder.getBitmapControl().getBirdHeight()))) {
                gameState=2;
                AppHolder.getSoundPlay().playCrash();
                Context mContext=AppHolder.gameActivityContext;
                Intent mIntent=new Intent(mContext,GameOver.class);
                mIntent.putExtra("score",scoreCount);
                mContext.startActivity(mIntent);
                ((Activity)mContext).finish();
            }
            if(tubeCollections.get(winningTube).getXtube() < bird.getX() - AppHolder.getBitmapControl().getTubeWidth())
            {
                scoreCount++;
                winningTube++;
                AppHolder.getSoundPlay().playPing();
                if(winningTube > AppHolder.tube_numbers -1)
                {
                    winningTube=0;
                }
            }
            for(int j=0;j<AppHolder.tube_numbers;j++)
            {
                if(tubeCollections.get(j).getXtube() < -AppHolder.getBitmapControl().getTubeWidth())
                {
                    tubeCollections.get(j).setXtube(tubeCollections.get(j).getXtube()
                    +AppHolder.tube_numbers*AppHolder.tubeDistance);
                    int upTubeCollectionY= AppHolder.minimumTubeCollection_Y +
                            rand.nextInt(AppHolder.maximumTubeCollection_Y - AppHolder.minimumTubeCollection_Y + 1);
                    tubeCollections.get(j).setUpTubeCollection_Y(upTubeCollectionY);

                }
                tubeCollections.get(j).setXtube(tubeCollections.get(j).getXtube() - AppHolder.tubeVelocity);
                can.drawBitmap(AppHolder.getBitmapControl().getUpTube(),tubeCollections.get(j).getXtube(),tubeCollections.get(j).getUpTube_Y(),null);
                can.drawBitmap(AppHolder.getBitmapControl().getDownTube(),tubeCollections.get(j).getXtube(),tubeCollections.get(j).getDownTube_Y(),null);
            }
            can.drawText(""+scoreCount,620,400,designPaint);
        }

    }

    public void birdAnimation(Canvas canvas)
    {
        if(gameState==1) {
            if (bird.getY() < (AppHolder.SCRN_HEIGHT_Y - AppHolder.getBitmapControl().getBirdHeight()) || bird.getVelocity() < 0) {
                bird.setVelocity(bird.getVelocity() + AppHolder.gravityPull);
                bird.setY(bird.getY() + bird.getVelocity());
            }
        }
        int currentFrame= bird.getCurrentFrame();
        canvas.drawBitmap(AppHolder.getBitmapControl().getBird(currentFrame),bird.getX(),bird.getY(),null);
        currentFrame++;
        if(currentFrame > bird.maximumFrame)
        {
            currentFrame=0;
        }
        bird.setCurrentFrame(currentFrame);
    }

    public void backgroundAnimation(Canvas canvas)
    {
        bgImage.setX(bgImage.getX()-bgImage.getVelocity());
        if(bgImage.getX() < -AppHolder.getBitmapControl().getBackgroundWidth()){
            bgImage.setX(0);
        }
        canvas.drawBitmap(AppHolder.getBitmapControl().getBackground(),bgImage.getX(),bgImage.getY(),null);
        if(bgImage.getX()<-(AppHolder.getBitmapControl().getBackgroundWidth() - AppHolder.SCRN_WIDTH_X)){
            canvas.drawBitmap(AppHolder.getBitmapControl().getBackground(),bgImage.getX() +
                    AppHolder.getBitmapControl().getBackgroundWidth(),bgImage.getY(),null);
        }
    }
}
