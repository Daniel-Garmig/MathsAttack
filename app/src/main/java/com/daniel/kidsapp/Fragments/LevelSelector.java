package com.daniel.kidsapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.daniel.kidsapp.AppData;
import com.daniel.kidsapp.Game.GameData;
import com.daniel.kidsapp.Game.Utils.GameModes;
import com.daniel.kidsapp.GameActivity;
import com.daniel.kidsapp.R;

public class LevelSelector extends Fragment
{
    ImageButton btReturn;

    Button btLvl1, btLvl2, btLvl3, btLvl4, btLvl5;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View frag =  inflater.inflate(R.layout.fragment_level_selector, container, false);

        initComponents(frag);


        return frag;
    }

    private void initComponents(View view)
    {
        btReturn = view.findViewById(R.id.btLvlReturn);
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnButton();
            }
        });

        btLvl1 = view.findViewById(R.id.btLvl1);
        btLvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(GameModes.addition);
            }
        });

        btLvl2 = view.findViewById(R.id.btLvl2);
        btLvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(GameModes.subtraction);
            }
        });

        btLvl3 = view.findViewById(R.id.btLvl3);
        btLvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(GameModes.multiply);
            }
        });

        btLvl4 = view.findViewById(R.id.btLvl4);
        btLvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(GameModes.divide);
            }
        });

        btLvl5 = view.findViewById(R.id.btLvl5);
        btLvl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(GameModes.all);
            }
        });
    }


    public void returnButton()
    {
        FragmentManager mng = getParentFragmentManager();
        mng.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, MainMenu.class, null)
                .commit();
    }


    public void startGame(GameModes mode)
    {
        GameData.getInstance().setMode(mode);

        AppData data = new ViewModelProvider(getActivity()).get(AppData.class);
        GameData.getInstance().setUsername(data.getUsername());


        Intent newAct = new Intent(getContext(), GameActivity.class);
        startActivity(newAct);

        //TODO: Modificar parámetros del juego y pasarlos. Probablemente iniciar la "pantalla de carga".

    }
}
