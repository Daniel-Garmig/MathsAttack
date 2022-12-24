package com.daniel.kidsapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daniel.kidsapp.AppData;
import com.daniel.kidsapp.R;


public class MainMenu extends Fragment
{
    String username;

    TextView textWelcome;
    Button btPlay;

    ImageButton btScoreboard, btSettings;

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
        View frag =  inflater.inflate(R.layout.fragment_main_menu, container, false);

        initComponents(frag);


        return frag;
    }

    private void initComponents(View view)
    {
        //Intentamos obtener el username desde los datos.
        AppData data = new ViewModelProvider(getActivity()).get(AppData.class);
        data.loadScoresFromDB();
        data.loadUsername(getContext());
        username = data.getUsername();

        //Si tenemos el nombre del usuario le mostramos el mensaje de bienvenida.
        textWelcome = view.findViewById(R.id.textInfo);
        if(username == null || username.isEmpty())
        {
            textWelcome.setVisibility(View.INVISIBLE);
        } else
        {
            String msg = getResources().getString(R.string.menuWelcomeText, username);
            textWelcome.setText(msg);
        }


        //Añadimos los Listeners a los botones.
        btPlay = view.findViewById(R.id.btPlay);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton();
            }
        });

        btScoreboard = view.findViewById(R.id.btStats);
        btScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mng = getParentFragmentManager();
                mng.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, Scoreboard.class, null)
                        .commit();
            }
        });

        btSettings = view.findViewById(R.id.btSettings);
        btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mng = getParentFragmentManager();
                mng.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, AppSettings.class, null)
                        .commit();
            }
        });
    }



    private void playButton()
    {
        //Si no tenemos el nombre, lo pedimos.
        username = new ViewModelProvider(getActivity()).get(AppData.class).getUsername();

        if(username == null || username.isEmpty())
        {
            NameInput nameDialog = new NameInput();
            nameDialog.show(getParentFragmentManager(), "nameDialog");

            return;
        }

        FragmentManager mng = getParentFragmentManager();
        mng.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, LevelSelector.class, null)
                .commit();
    }
}