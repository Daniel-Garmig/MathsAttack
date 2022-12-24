package com.daniel.kidsapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.daniel.kidsapp.DB.DBConnection;
import com.daniel.kidsapp.Game.Score;
import com.daniel.kidsapp.Game.Utils.GameModes;

import java.util.ArrayList;

public class AppData extends ViewModel
{
    private ArrayList<Score> scores;

    private String username;


    public AppData()
    {
        //Init DB Connection to get data.
        scores = new ArrayList<>();

    }


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public ArrayList<Score> getScores() { return scores; }

    public void loadScoresFromDB()
    {
        scores = DBConnection.getInstance().getScoresFromDB(null);
    }

    public void loadScoresFromDB(GameModes mode)
    {
        scores = DBConnection.getInstance().getScoresFromDB(mode);
    }

    public void loadUsername(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.sharedPrefsFileName), Context.MODE_PRIVATE);
        username = prefs.getString(context.getString(R.string.sharedPrefsKeyUsername), "");
    }


    public void saveUsername(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.sharedPrefsFileName), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(context.getString(R.string.sharedPrefsKeyUsername), username);
        edit.apply();
    }


}
