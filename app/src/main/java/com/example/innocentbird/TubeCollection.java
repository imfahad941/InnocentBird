package com.example.innocentbird;

import java.util.Random;

public class TubeCollection {
    private int xTube; //This will hold the X Coordinate
    private int upTubeCollection_Y;
    private Random rand;

    public TubeCollection(int xTube,int upTubeCollection_Y)
    {
        this.xTube=xTube;
        this.upTubeCollection_Y=upTubeCollection_Y;
        rand =new Random();
    }

    public int getUpTubeCollection_Y()
    {
        return  upTubeCollection_Y;
    }

    public int getXtube()
    {
        return xTube;
    }

    public int getUpTube_Y()
    {
        return upTubeCollection_Y - AppHolder.getBitmapControl().getTubeHeight();
    }
    public int getDownTube_Y()
    {
        return upTubeCollection_Y + AppHolder.tubeGap;
    }

    public void setXtube(int x_tube)
    {
        this.xTube=x_tube;
    }

    public void setUpTubeCollection_Y(int upTubeCollection_Y)
    {
        this.upTubeCollection_Y=upTubeCollection_Y;
    }
}
