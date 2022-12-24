package com.daniel.kidsapp.Game.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite
{
    private Bitmap image;
    private Rect imageRect;

    private Paint spritePaint;

    public Sprite(Bitmap image)
    {
        this.image = image;
        imageRect = new Rect(0, 0, image.getWidth(), image.getHeight());

        spritePaint = new Paint();
    }

    public Sprite(Bitmap image, Rect imageRect)
    {
        this.image = image;
        this.imageRect = imageRect;

        spritePaint = new Paint();
    }

    public void setColor(int c)
    {
        spritePaint.setColor(c);
    }


    public Bitmap getImage() { return image; }
    public void setImage(Bitmap image) { this.image = image; }

    public Rect getImageRect() { return imageRect; }
    public void setImageRect(Rect imageRect) { this.imageRect = imageRect; }

    public Paint getSpritePaint() { return spritePaint; }
    public void setSpritePaint(Paint spritePaint) { this.spritePaint = spritePaint; }

    public int getWidth() { return imageRect.width(); }
    public int getHeight() { return imageRect.height(); }


    public double getScaledWidth(double scalar) { return imageRect.width() * scalar; }
    public double getScaledHeight(double scalar) { return imageRect.height() * scalar; }
}
