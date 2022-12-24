package com.daniel.kidsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.Menu;

import com.daniel.kidsapp.DB.DBConnection;
import com.daniel.kidsapp.Fragments.MainMenu;

public class MainActivity extends AppCompatActivity
{

    FragmentContainerView fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, MainMenu.class, null)
                .commit();

        DBConnection.getInstance().initConnection(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.scoreboard_nav_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, MainMenu.class, null)
                .commit();
    }

    @Override
    protected void onDestroy()
    {
        DBConnection.getInstance().destroy();
        super.onDestroy();
    }
}