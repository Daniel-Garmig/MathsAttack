package com.daniel.kidsapp.DB;

import android.provider.BaseColumns;

//Basado en el tutorial de android: https://developer.android.com/training/data-storage/sqlite#java

public class ScoresContract
{
    public static final String SQL_CREATE_SCORES =
            "CREATE TABLE " + ScoreEntry.TABLE_NAME + " (" +
                    ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    ScoreEntry.COLUMN_NAME_GAMEMODE + " TEXT," +
                    ScoreEntry.COLUMN_NAME_SCORE + " INTEGER," +
                    ScoreEntry.COLUMN_NAME_USERNAME + " TEXT)";

    public static final String SQL_DELETE_SCORES =
            "DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME;


    private ScoresContract() {}

    public static class ScoreEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_NAME_GAMEMODE = "gamemode";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_USERNAME = "username";
    }

}
