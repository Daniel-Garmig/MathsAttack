package com.daniel.kidsapp.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.daniel.kidsapp.AppData;
import com.daniel.kidsapp.DB.DBConnection;
import com.daniel.kidsapp.Game.GameManager;
import com.daniel.kidsapp.Game.Score;
import com.daniel.kidsapp.Game.Utils.GameModes;
import com.daniel.kidsapp.R;

import java.util.Random;


public class AppSettings extends PreferenceFragmentCompat
{
    EditTextPreference editUsername;
    Preference prefClearDB, prefClearUsername, prefShowInfo;

    Preference prefDebugDummyScores;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey)
    {
        setPreferencesFromResource(R.xml.app_settings, rootKey);

        initComponents();
    }

    private void initComponents()
    {
        editUsername = findPreference(getString(R.string.prefsChangeNameKey));
        editUsername.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue)
            {
                changeUsername(newValue);
                return true;
            }
        });
        AppData data = new ViewModelProvider(getActivity()).get(AppData.class);
        editUsername.setText(data.getUsername());

        prefClearDB = findPreference(getString(R.string.prefsClearDBKey));
        prefClearDB.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference)
            {
                DBConnection.getInstance().clearAllScoresOnDB();
                Toast.makeText(getContext(), R.string.prefsClearDBToast, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        prefClearUsername = findPreference(getString(R.string.prefsClearNameKey));
        prefClearUsername.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                changeUsername(null);
                editUsername.setText("");
                Toast.makeText(getContext(), R.string.prefsClearNameToast, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        prefShowInfo = findPreference(getString(R.string.prefsShowInfoKey));
        prefShowInfo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Toast.makeText(getContext(), R.string.appInfoText, Toast.LENGTH_LONG).show();
                return true;
            }
        });

        prefDebugDummyScores = findPreference(getString(R.string.prefsAddDummyScoresKey));
        prefDebugDummyScores.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                generateDummyScores();
                return true;
            }
        });
    }


    private void changeUsername(Object newValue)
    {
        AppData data = new ViewModelProvider(getActivity()).get(AppData.class);

        try
        {
            String clearedUsername = null;
            if(newValue != null)
            {
                clearedUsername = ((String) newValue).trim();
            }
            data.setUsername(clearedUsername);
            data.saveUsername(getContext());
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), R.string.nameDialog_error, Toast.LENGTH_SHORT).show();
        }

    }


    //DEBUG.
    private void generateDummyScores()
    {
        String[] names = {"Fulanito", "Luis", "Maria", "Menganita"};
        Random generator = new Random();
        DBConnection db = DBConnection.getInstance();

        for(int i = 0; i < 4; i++)
        {
            int juegos = generator.nextInt(5)+1;
            for(int j = 0; j <= juegos; j++)
            {
                int modeInt = generator.nextInt(5);
                GameModes mode = GameModes.addition;
                switch (modeInt)
                {
                    case 0:
                        mode = GameModes.addition;
                        break;
                    case 1:
                        mode = GameModes.subtraction;
                        break;
                    case 2:
                        mode = GameModes.multiply;
                        break;
                    case 3:
                        mode = GameModes.divide;
                        break;
                    case 4:
                        mode = GameModes.all;
                        break;
                }
                int score = generator.nextInt(1000);
                Score sc = new Score(mode, score, names[i]);

                db.addScoreToDB(sc);
            }
        }

    }
}
