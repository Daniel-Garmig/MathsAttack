package com.daniel.kidsapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.kidsapp.AppData;
import com.daniel.kidsapp.Game.Score;
import com.daniel.kidsapp.Game.Utils.GameModes;
import com.daniel.kidsapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Scoreboard extends Fragment
{

    ImageButton btReturn;

    TextView textNoEntry;
    TextView textModeInfo;

    RecyclerView rView;

    Toolbar navBar;

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
        View frag =  inflater.inflate(R.layout.fragment_scoreboard, container, false);

        initComponents(frag);


        return frag;
    }

    private void initComponents(View view)
    {
        rView = view.findViewById(R.id.scoreboardData);

        textNoEntry = view.findViewById(R.id.textScoreBoardNoEntry);

        navBar = view.findViewById(R.id.scoreboardNavBar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(navBar);

        navBar.setTitle("");

        navBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.sbMenuReturn:
                        returnButton();
                        return true;
                    case R.id.sbMenuAdd:
                        loadViewData(GameModes.addition);
                        return true;
                    case R.id.sbMenuSub:
                        loadViewData(GameModes.subtraction);
                        return true;
                    case R.id.sbMenuMult:
                        loadViewData(GameModes.multiply);
                        return true;
                    case R.id.sbMenuDiv:
                        loadViewData(GameModes.divide);
                        return true;
                    case R.id.sbMenuLvl5:
                        loadViewData(GameModes.all);
                        return true;
                }

                return false;
            }
        });

        loadViewData(GameModes.addition);

        btReturn = view.findViewById(R.id.btScoreReturn);
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnButton();
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


    private void loadViewData(GameModes mode)
    {
        //Get scoreData.
        AppData data = new ViewModelProvider(getActivity()).get(AppData.class);
        data.loadScoresFromDB(mode);
        ArrayList<Score> scoreData = data.getScores();

        if(scoreData.size() <= 0)
        {
            textNoEntry.setVisibility(View.VISIBLE);
        } else
        {
            textNoEntry.setVisibility(View.GONE);
        }

        ScoreAdapter scoreAdapter = new ScoreAdapter(scoreData);


        RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity());
        rView.setLayoutManager(layout);

        rView.setAdapter(scoreAdapter);
    }
}
