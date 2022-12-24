package com.daniel.kidsapp.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.daniel.kidsapp.Game.GameData;
import com.daniel.kidsapp.R;

public class PauseDialog extends DialogFragment
{

    Button btContinue;
    Button btExit;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pause_dialog, container, false);

        initComponents(view);

        return view;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        GameData.getInstance().setPaused(false);
    }


    private void initComponents(View view)
    {
        btContinue = view.findViewById(R.id.btPauseContinue);
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                GameData.getInstance().setPaused(false);
                dismiss();
            }
        });


        btExit = view.findViewById(R.id.btPauseExit);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.getInstance().endGame();
                getActivity().finish();
            }
        });
    }

}
