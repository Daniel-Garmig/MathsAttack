package com.daniel.kidsapp.Game.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daniel.kidsapp.Game.AssetManager;
import com.daniel.kidsapp.Game.Utils.Renderizable;
import com.daniel.kidsapp.Game.Utils.Sprite;
import com.daniel.kidsapp.Game.Utils.Transform;

public class Attack extends Transform implements Renderizable
{
    private static Sprite sprite;
    private static final double spriteScale = 2.5;

    private boolean isDocked;

    private int value;

    private String textValue;
    private Paint textPaint;
    private int textVerticalOffset;
    private int textHorizontalOffset;

    public Attack()
    {
        textValue = "";

        //Create the Sprite if is null
        if(sprite == null)
        {
            sprite = new Sprite(AssetManager.getInstance().getBitmap("attack"));
        }

        setSize((int) sprite.getScaledWidth(spriteScale), (int) sprite.getScaledHeight(spriteScale));

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
    }


    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public boolean isDocked() { return isDocked; }
    public void setDocked(boolean docked) { isDocked = docked; }

    public void addNumber(String num)
    {
        textValue += num;
        value = Integer.parseInt(textValue);
        updateText();
    }
    public void addNumber(int num)
    {
        textValue += String.valueOf(num);
        value = Integer.parseInt(textValue);
        updateText();
    }


    //Actualiza la posición y el tamaño del texto en función del texto que tengamos.
    private void updateText()
    {
        //Get text bounds.
        Rect textBounds = new Rect();
        textPaint.getTextBounds(textValue, 0, textValue.length(), textBounds);


        //Calculate offsets to center on mainRect.
        textHorizontalOffset = (rect.width()/2 - textBounds.width()/2);
        textVerticalOffset = (rect.height()/2 - textBounds.height()/2);


        //TODO: Change text size.

    }




    @Override
    public void render(Canvas canvas)
    {
        //Draw main sprite.
        canvas.drawBitmap(sprite.getImage(), sprite.getImageRect(), rect, sprite.getSpritePaint());

        //Draw value text inside.
        canvas.drawText(textValue, rect.left + textHorizontalOffset, rect.bottom - textVerticalOffset, textPaint);
    }
}
