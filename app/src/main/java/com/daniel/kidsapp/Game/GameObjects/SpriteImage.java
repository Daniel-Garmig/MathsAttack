package com.daniel.kidsapp.Game.GameObjects;

import android.graphics.Canvas;

import com.daniel.kidsapp.Game.AssetManager;
import com.daniel.kidsapp.Game.Utils.Renderizable;
import com.daniel.kidsapp.Game.Utils.Sprite;
import com.daniel.kidsapp.Game.Utils.Transform;

/**
 * Class for displaying images on the screen.
 */
public class SpriteImage extends Transform implements Renderizable
{

    private Sprite image;

    private double spriteScale;


    public SpriteImage(String bitmapName)
    {
        image = new Sprite(AssetManager.getInstance().getBitmap(bitmapName));
    }


    public Sprite getImage() { return image; }

    public double getSpriteScale() { return spriteScale; }
    public void setSpriteScale(double spriteScale) { this.spriteScale = spriteScale; }
    public void reduceSpriteScale(double amount) { this.spriteScale -= amount; }


    /**
     * Set rect size to scaled sprite size.
     */
    public void setRectToScaledSpriteSize()
    {
        setSize((int) image.getScaledWidth(spriteScale), (int) image.getScaledHeight(spriteScale));
    }


    @Override
    public void render(Canvas canvas)
    {
        canvas.drawBitmap(image.getImage(), image.getImageRect(), rect, image.getSpritePaint());
    }
}