package com.daniel.kidsapp.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.kidsapp.Game.Score;
import com.daniel.kidsapp.R;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreCard>
{

    ArrayList<Score> scores;

    public ScoreAdapter(ArrayList<Score> scores)
    {
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoreCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_card, parent, false);
        return new ScoreCard(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreCard holder, int position)
    {
        Score sc = scores.get(position);

        holder.getTextUsername().setText(sc.getUsername());
        String scoreText = String.valueOf(sc.getScore());
        holder.textScore.setText(scoreText);
    }

    @Override
    public int getItemCount()
    {
        return scores.size();
    }

    public static class ScoreCard extends RecyclerView.ViewHolder
    {
        private TextView textUsername, textScore;


        public ScoreCard(View itemView)
        {
            super(itemView);

            textUsername = itemView.findViewById(R.id.textScoreUser);
            textScore = itemView.findViewById(R.id.textScoreScore);
        }

        public TextView getTextUsername() { return textUsername; }
        public TextView getTextScore() { return textScore; }
    }

}
