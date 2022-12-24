package com.daniel.kidsapp.Game;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Choreographer.FrameCallback
{
    private static final String TAG = GameView.TAG;

    public GameThread gameThread;

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        getHolder().addCallback(this);

        Log.i(TAG, "Se ha creado el GameView!");
    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder)
    {
        Log.i(TAG, "Creada la surface.");

        GameManager mng = GameManager.getInstance();
        Rect surface = surfaceHolder.getSurfaceFrame();
        mng.setScreenSize(surface.width(), surface.height());

        //Iniciamos el GameThread si no lo estaba aún.
        if(gameThread != null)
        {
            return;
        }

        try
        {
            gameThread = new GameThread(surfaceHolder);
            gameThread.start();
            Log.i(TAG, "Se ha inicial el GameThread.");

            GameHandler gh = gameThread.getHandler();
            if (gh != null)
            {
                gh.sendSurfaceCreated();
            }

            // start the draw events
            Choreographer.getInstance().postFrameCallback(this);
        }
        catch (Exception e)
        {
            Log.w(TAG, "Error al iniciar el GameThread");
        }

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height)
    {
        Log.d(TAG, "surfaceChanged fmt=" + format + " size=" + width + "x" + height +
                " holder=" + surfaceHolder);

        GameHandler gh = gameThread.getHandler();
        if (gh != null)
        {
            gh.sendSurfaceChanged(width, height);
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder)
    {
        Log.d(TAG, "surfaceDestroyed holder=" + surfaceHolder);

        //Detener Render Thread.
        GameHandler gh = gameThread.getHandler();
        if (gh != null)
        {
            gh.sendShutdown();
            while(true)
            {
                try
                {
                    gameThread.join();
                    return;
                } catch (Exception ie)
                {
                    // Error al parar el Thread.
                    ie.printStackTrace();
                }
            }
        }
        gameThread = null;

        Log.d(TAG, "surfaceDestroyed complete");
    }

    @Override
    public void doFrame(long frameTimeNanos)
    {
        GameHandler gh = gameThread.getHandler();
        if(gh != null)
        {
            Choreographer.getInstance().postFrameCallback(this);
            gh.sendDoFrame(frameTimeNanos);
        }
    }
}
