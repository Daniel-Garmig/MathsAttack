package com.daniel.kidsapp.Game.GameObjects;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daniel.kidsapp.Game.AssetManager;
import com.daniel.kidsapp.Game.Utils.Renderizable;
import com.daniel.kidsapp.Game.Utils.Sprite;
import com.daniel.kidsapp.Game.Utils.Transform;

public class Enemy extends Transform implements Renderizable
{
    double movSpeed;

    private String mathExpresesion;
    private int result;
    private int score;

    public final static double spriteScale = 2f;

    //Datos sobre el sprite.
    private static Sprite enemySprite;

    private static Sprite dialogSprite;
    private static int dialogTopOffset;
    private static int dialogLeftOffset;

    private int textVerticalOffset;
    private int textLeftOffset;
    private static Paint textPaint;


    private Rect dialogRect;


    public Enemy()
    {
        //get Sprites if null.
        if(enemySprite == null)
        {
            enemySprite = new Sprite(AssetManager.getInstance().getBitmap("enemy2"));
            //enemySprite.setColor(Color.RED);
        }
        if(dialogSprite == null)
        {
            dialogSprite = new Sprite(AssetManager.getInstance().getBitmap("blueDialog"));
        }

        setSize((int) (enemySprite.getScaledWidth(spriteScale)), (int) (enemySprite.getScaledHeight(spriteScale)));

        //Dialog params.
        dialogRect = new Rect(rect.left, rect.top, rect.left + 300, rect.top + 120);

        dialogTopOffset = 15 + dialogRect.height();
        dialogLeftOffset = (dialogRect.width() / 2 - rect.width() / 2 );


        //Text params.
        mathExpresesion = "";
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);
    }


    public double getMovSpeed() { return movSpeed; }
    public void setMovSpeed(double movSpeed) { this.movSpeed = movSpeed; }

    public String getOperation() { return mathExpresesion; }
    public void setOperation(String operation)
    {
        this.mathExpresesion = operation;

        Rect textRect = new Rect();
        textPaint.getTextBounds(mathExpresesion, 0, mathExpresesion.length(), textRect);

        //Calculamos el offset para centrar el textRect en el dialogRect.
        textLeftOffset = (dialogRect.width()/2 - textRect.width()/2);
        textVerticalOffset = (dialogRect.height()/2 - textRect.height()/2);
    }

    public int getResult() { return result; }
    public void setResult(int result) { this.result = result; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    @Override
    public void render(Canvas canvas)
    {
        //If there is no sprite, don't draw.
        if(enemySprite == null) { return; }

        canvas.drawBitmap(enemySprite.getImage(), enemySprite.getImageRect(), rect, enemySprite.getSpritePaint());

        //Actualizamos la posición del rect para el dialog.
        dialogRect.offsetTo(rect.left - dialogLeftOffset, rect.top - dialogTopOffset);
        canvas.drawBitmap(dialogSprite.getImage(), dialogSprite.getImageRect(), dialogRect, dialogSprite.getSpritePaint());

        //Dibujamos en el dialogo la expresión matemática correspondiente.
        canvas.drawText(mathExpresesion, dialogRect.left + textLeftOffset, dialogRect.bottom - textVerticalOffset, textPaint);

    }
}
