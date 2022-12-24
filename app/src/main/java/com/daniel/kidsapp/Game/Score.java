package com.daniel.kidsapp.Game;

import com.daniel.kidsapp.Game.Utils.GameModes;

public class Score
{
    GameModes mode;
    int score;
    String username;

    public Score(GameModes mode, int score, String username)
    {
        this.mode = mode;
        this.score = score;
        this.username = username;
    }

    public GameModes getMode() { return mode; }
    public int getScore() { return score; }
    public String getUsername() { return username; }
}
