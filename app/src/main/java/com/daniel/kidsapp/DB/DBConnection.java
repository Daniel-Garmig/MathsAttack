package com.daniel.kidsapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.daniel.kidsapp.Game.AssetManager;
import com.daniel.kidsapp.Game.Score;
import com.daniel.kidsapp.Game.Utils.GameModes;

import java.util.ArrayList;

public class DBConnection
{
    DBHelper dbHelper;

    // SINGLETON.
    private static DBConnection instance;

    public static DBConnection getInstance()
    {
        //Utilizamos la técnica del double-checked locking (DCL).
        //De esta forma, ahorramos recursos en la sincronización multithread.

        DBConnection dbConnection = instance;
        if(dbConnection != null) { return dbConnection; }

        synchronized (DBConnection.class)
        {
            if(instance == null)
            {
                instance = new DBConnection();
            }
            return instance;
        }
    }
    // END SINGLETON.

    public void initConnection(Context appContext)
    {
        dbHelper = new DBHelper(appContext);
    }

    public void addScoreToDB(Score score)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScoresContract.ScoreEntry.COLUMN_NAME_GAMEMODE, score.getMode().toString());
        values.put(ScoresContract.ScoreEntry.COLUMN_NAME_SCORE, score.getScore());
        values.put(ScoresContract.ScoreEntry.COLUMN_NAME_USERNAME, score.getUsername());

        db.insert(ScoresContract.ScoreEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Score> getAllScoresFromDB()
    {
        ArrayList<Score> scoreList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(
                ScoresContract.ScoreEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ScoresContract.ScoreEntry.COLUMN_NAME_SCORE + " DESC");

        while(c.moveToNext())
        {
            String gm = c.getString(c.getColumnIndexOrThrow(ScoresContract.ScoreEntry.COLUMN_NAME_GAMEMODE));
            int score = c.getInt(c.getColumnIndexOrThrow(ScoresContract.ScoreEntry.COLUMN_NAME_SCORE));
            String player = c.getString(c.getColumnIndexOrThrow(ScoresContract.ScoreEntry.COLUMN_NAME_USERNAME));

            Score sc = new Score(GameModes.valueOf(gm), score, player);
            scoreList.add(sc);
        }
        c.close();

        return scoreList;
    }


    public ArrayList<Score> getScoresFromDB(GameModes mode)
    {
        ArrayList<Score> scoreList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Filter gamemode data if provided.
        String where = null;
        String[] whereArgs = null;
        if(mode != null)
        {
            where = ScoresContract.ScoreEntry.COLUMN_NAME_GAMEMODE + " = ?";
            whereArgs = new String[]{ mode.toString() };
        }

        Cursor c = db.query(
                ScoresContract.ScoreEntry.TABLE_NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                ScoresContract.ScoreEntry.COLUMN_NAME_SCORE + " DESC");

        while(c.moveToNext())
        {
            String gm = c.getString(c.getColumnIndexOrThrow(ScoresContract.ScoreEntry.COLUMN_NAME_GAMEMODE));
            int score = c.getInt(c.getColumnIndexOrThrow(ScoresContract.ScoreEntry.COLUMN_NAME_SCORE));
            String player = c.getString(c.getColumnIndexOrThrow(ScoresContract.ScoreEntry.COLUMN_NAME_USERNAME));

            Score sc = new Score(GameModes.valueOf(gm), score, player);
            scoreList.add(sc);
        }
        c.close();

        return scoreList;
    }

    public void clearAllScoresOnDB()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(ScoresContract.ScoreEntry.TABLE_NAME, null, null);
    }


    public void destroy()
    {
        dbHelper.close();
    }
}
