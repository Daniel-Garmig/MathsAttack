package com.daniel.kidsapp.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.daniel.kidsapp.Game.GameData;
import com.daniel.kidsapp.R;

public class GameOverDialog extends DialogFragment
{
    TextView textScore, textTime;

    Button btExit;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View frag =  inflater.inflate(R.layout.fragment_gameover_dialog, container, false);

        initComponents(frag);

        return frag;
    }

    private void initComponents(View view)
    {
        textScore = view.findViewById(R.id.textGameOverScore);
        textScore.setText(getString(R.string.gameOverScore, GameData.getInstance().getGameScore()));

        textTime = view.findViewById(R.id.textGameOverTime);
        textTime.setText(getString(R.string.gameOverTime, GameData.getInstance().getGameTime()/1000));

        btExit = view.findViewById(R.id.btGameOverExit);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.getInstance().setPaused(true);
                GameData.getInstance().endGame();
                getActivity().finish();
            }
        });
    }
}
