package com.daniel.kidsapp.Game.Utils;

import android.graphics.Rect;
import android.graphics.RectF;

public abstract class Transform
{
    protected Rect rect;
    RectF rect2;

    protected Transform()
    {
        rect = new Rect();
    }

    public Transform(Rect rect)
    {
        this.rect = rect;
    }

    public Transform(int posX, int posY, int sizeX, int sizeY)
    {
        rect = new Rect(posX, posY, posX + sizeX, posY + sizeY);
    }


    public void setPosition(int x, int y)
    {
        rect.offsetTo(x, y);
    }

    public void setSize(int sizeX, int sizeY)
    {
        int posX = rect.left;
        int posY = rect.top;
        rect.set(posX, posY, posX + sizeX, posY + sizeY);
    }


    public void moveAmount(int movX, int movY)
    {
        rect.offset(movX, movY);
    }

    public Rect getRect() { return rect; }


}
