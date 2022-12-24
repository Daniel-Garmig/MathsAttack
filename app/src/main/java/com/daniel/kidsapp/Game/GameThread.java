package com.daniel.kidsapp.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.daniel.kidsapp.Game.GameObjects.Attack;
import com.daniel.kidsapp.Game.GameObjects.Enemy;
import com.daniel.kidsapp.Game.GameObjects.SpriteImage;


public class GameThread extends Thread
{
    public static final String TAG = "GameThread";

    private volatile SurfaceHolder holder;
    private volatile GameHandler handler;
    private GameManager gameMng;


    //TODO: Implementar sistema de relojes y Frames.
    private final int framesPerSecond = 60;
    private final long msPerFrame = 20;
    private final long nsPerFrame = msPerFrame * 1000000;
    //1s=1,000 ms
    //1s=1,000,000,000 ns
    //1ms=1,000,000 ns

    private long lastFrameTime;


    GameThread(SurfaceHolder holder)
    {
        this.holder = holder;
        gameMng = GameManager.getInstance();
    }

    public GameHandler getHandler() { return handler; }


    @Override
    public void run()
    {
        Looper.prepare();
        handler = new GameHandler(this);


        initRenderer();

        //Loop.
        Looper.loop();

        //Al terminar el Loop.
        Log.d(TAG, "looper quit");
    }


    public void surfaceChanged(int newWidth, int newHeight)
    {
        gameMng.setScreenSize(newWidth, newHeight);
    }

    public void shutdown()
    {
        Log.i(TAG, "Deteniendo el Looper del thread.");
        Looper.myLooper().quit();
    }


    public void doFrame(long timeNanos)
    {
        long dt = timeNanos - lastFrameTime;
        if(lastFrameTime == 0) { dt = 0; }
        lastFrameTime = timeNanos;

        gameMng.updateGame(dt);


        //Sleep till next frame if we are going toooo fast.
        // TODO: Comprobar si esta lógica tiene sentido.
        long endFrameTime = System.nanoTime();

        long frameTime = endFrameTime - timeNanos;
        if(frameTime > nsPerFrame)
        {
            return;
        }

        //Draw graphics. TODO: Mover a la clase Renderer.
        drawGame();

    }


    private void initRenderer()
    {

    }


    private void drawGame()
    {
        GameData data = GameData.getInstance();

        //Bloqueamos el Canvas.
        Canvas canvas = holder.lockCanvas();

        //Limpiamos la pantalla.
        canvas.drawARGB(255, 255, 255, 255);

        data.getBackground().render(canvas);
        data.getAttackTrash().render(canvas);

        //Render Effects.
        for(SpriteImage img : data.getEffects())
        {
            img.render(canvas);
        }

        //Render de los objetos.
        for(Enemy e : data.getEnemyList())
        {
            e.render(canvas);
        }
        data.getPlayer().render(canvas);

        Attack at = data.getAttack();
        if(at != null)
        {
            //canvas.drawCircle(at.getRect().centerX(), at.getRect().centerY(), 100, attackPaint);
            at.render(canvas);
        }

        //Desbloqueamos y devolvemos el Canvas.
        holder.unlockCanvasAndPost(canvas);
    }


    public void doOnAttackButton(int value)
    {
        gameMng.addAttack(value);
    }

    @Deprecated
    public void doOnTouchGame(int posX, int posY)
    {
        gameMng.gameTouch(posX, posY);
    }

    public void doOnTouchGame(MotionEvent e)
    {
        gameMng.gameTouch(e);
    }
}
