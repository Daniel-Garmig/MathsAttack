package com.daniel.kidsapp.Game;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class GameHandler extends Handler
{
    private static final String TAG = "GameHandler";

    //Los diferentes mensajes que podemos tener.
    private static final int MSG_SURFACE_CREATED = 0;
    private static final int MSG_SURFACE_CHANGED = 1;
    private static final int MSG_DO_FRAME = 2;
    private static final int MSG_SHUTDOWN = 5;
    private static final int MSG_ATTACKBUTTON_CLICK = 6;
    private static final int MSG_TOUCH_GAME = 7;
    private static final int MSG_TOUCH_EVENT = 8;



    //Referencia al thread para mandarle las cosas.
    GameThread gt;


    public GameHandler(GameThread thread)
    {
        gt = thread;
    }

    public void sendSurfaceCreated()
    {
        sendMessage(obtainMessage(MSG_SURFACE_CREATED));
    }

    public void sendSurfaceChanged(int width, int height)
    {
        sendMessage(obtainMessage(MSG_SURFACE_CHANGED, width, height));
    }

    public void sendShutdown()
    {
        sendMessage(obtainMessage(MSG_SHUTDOWN));
    }

    public void sendDoFrame(long frameTimeNanos)
    {
        sendMessage(obtainMessage(MSG_DO_FRAME,
                (int) (frameTimeNanos >> 32), (int) frameTimeNanos));
    }

    public void sendAttackButtonClick(int value)
    {
        sendMessage(obtainMessage(MSG_ATTACKBUTTON_CLICK, value, 0));
    }

    public void sendTouchPosition(int posX, int posY)
    {
        sendMessage(obtainMessage(MSG_TOUCH_GAME, posX, posY));
    }

    public void sendTouchEvent(MotionEvent e)
    {
        sendMessage(obtainMessage(MSG_TOUCH_EVENT, e));
    }



    @Override
    public void handleMessage(@NonNull Message msg)
    {
        int msgCode = msg.what;

        if(gt == null)
        {
            Log.w(TAG, "RenderHandler.handleMessage: No hay referencia al thread");
            return;
        }

        if(msgCode == MSG_SHUTDOWN)
        {
            Log.i(TAG, "SHUTDOWN!!!");
        }

        switch (msgCode)
        {
            case MSG_SURFACE_CREATED:
                Log.i(TAG, "Enviar un SurfaceCreated");
                break;
            case MSG_SURFACE_CHANGED:
                gt.surfaceChanged(msg.arg1, msg.arg2);
                break;
            case MSG_DO_FRAME:
                long timestamp = (((long) msg.arg1) << 32) |
                        (((long) msg.arg2) & 0xffffffffL);
                gt.doFrame(timestamp);
                break;
            case MSG_SHUTDOWN:
                gt.shutdown();
                break;
            case MSG_ATTACKBUTTON_CLICK:
                gt.doOnAttackButton(msg.arg1);
                break;
            case MSG_TOUCH_GAME:
                gt.doOnTouchGame(msg.arg1, msg.arg2);
                break;
            case MSG_TOUCH_EVENT:
                gt.doOnTouchGame((MotionEvent) msg.obj);
                break;
            default:
                throw new RuntimeException("unknown message " + msgCode);
        }

    }
}
