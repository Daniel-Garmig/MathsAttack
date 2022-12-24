package com.daniel.kidsapp.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.daniel.kidsapp.R;

import java.util.HashMap;

public class AssetManager 
{
    private Context appContext;
    private boolean isLoaded;

    HashMap<String, Bitmap> assets;



    // SINGLETON.
    private static AssetManager instance;

    public static AssetManager getInstance()
    {
        //Utilizamos la técnica del double-checked locking (DCL).
        //De esta forma, ahorramos recursos en la sincronización multithread.

        AssetManager assetManager = instance;
        if(assetManager != null) { return assetManager; }

        synchronized (AssetManager.class)
        {
            if(instance == null)
            {
                instance = new AssetManager();
            }
            return instance;
        }
    }
    // END SINGLETON.


    private AssetManager()
    {
        isLoaded = false;
        assets = new HashMap<>();
    }

    //Debe llamarse antes de pedir cualquier asset.
    public void loadAsssets(Context appContext)
    {
        this.appContext = appContext;

        //Cargar manualmente los assets.
        //Lo bonito sería usar un JSON o algún fichero con los datos de los bitmaps a cargar.
        //loadBitmap("enemyWalk1", R.drawable.p1_walk01);
        loadBitmap("enemy2", R.drawable.shipgreen_manned);


        loadBitmap("blueDialog", R.drawable.blue_button06);
        loadBitmap("attack", R.drawable.bombattack);

        loadBitmap("blackBG", R.drawable.black);
        loadBitmap("attackTrash", R.drawable.trash);

        loadBitmap("player", R.drawable.player);

        loadBitmap("attackExplosion", R.drawable.sonicexplosion00);
        loadBitmap("damageExplosion", R.drawable.regularexplosion02);


        isLoaded = true;
    }


    /**
     * Get a bitmap from loaded assets.
     * @param name Asset name.
     * @return Returns the asset or null if there is no asset with that name.
     */
    public Bitmap getBitmap(String name)
    {
        return assets.get(name);
    }


    /**
     * Utilizado para cargar todos los Assets.
     * @param name Nombre que se le asigna en el HashMap.
     * @param bitmapAssetID ID del Bitmap a descodificar.
     * @return True si se ha cargado y guardado en el Hashmap correctamente. False en caso de error.
     */
    private boolean loadBitmap(String name, int bitmapAssetID)
    {
        Bitmap img = BitmapFactory.decodeResource(appContext.getResources(), bitmapAssetID);

        //Si no se ha podido cargar, retornamos error.
        if(img == null) { return false; }

        assets.put(name, img);

        return true;
    }

}

// TODO: Probar a implementar la lista de Assets utilizando una Enum para los nombres.