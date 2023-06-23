package com.example.innocentbird;

public class BgImage {
    private int image_X_background;
    private int image_Y_background;
    private int backgroundSpeed;

    public BgImage()
    {
        image_X_background=0;
        image_Y_background=0;
        backgroundSpeed=4;

    }

    public int getX()
    {
        return image_X_background;
    }

    public int getY()
    {
        return image_Y_background;
    }

    public void setX(int backgroundImageX)
    {
        this.image_X_background=backgroundImageX;
    }

    public void setY(int backgroundImageY)
    {
        this.image_Y_background=backgroundImageY;
    }

    public int getVelocity()
    {
        return backgroundSpeed;
    }



}
