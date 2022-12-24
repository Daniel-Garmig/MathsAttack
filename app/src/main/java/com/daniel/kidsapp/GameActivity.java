package com.daniel.kidsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daniel.kidsapp.DB.DBConnection;
import com.daniel.kidsapp.Game.GameData;
import com.daniel.kidsapp.Game.GameHandler;
import com.daniel.kidsapp.Game.GameManager;
import com.daniel.kidsapp.Game.GameView;
import com.daniel.kidsapp.Game.AssetManager;
import com.daniel.kidsapp.Game.Score;
import com.daniel.kidsapp.Game.Utils.GameModes;
import com.daniel.kidsapp.Fragments.GameOverDialog;
import com.daniel.kidsapp.Fragments.PauseDialog;


public class GameActivity extends AppCompatActivity
{

    GameView gameSurface;

    Button btNum1, btNum2, btNum3, btNum4, btNum5, btNum6, btNum7, btNum8, btNum9, btNum0;

    ImageButton btPause;
    LinearLayout healContainer;
    TextView textScore;

    Button btGenEnemy;

    BroadcastReceiver uiUpdater;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Creamos el juego.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameSurface = findViewById(R.id.gameSurface);

        //Inicializamos los botones.
        initComponents();


        //Inicializamos el juego.
        AssetManager.getInstance().loadAsssets(getApplicationContext());

        GameData data = GameData.getInstance();
        data.initGame();
        data.setAppContext(getApplicationContext());

        GameManager mng = GameManager.getInstance();
        mng.setScreenSize(gameSurface.getWidth(), gameSurface.getHeight());

        mng.initGame();

        createHealth(data.getInitialHealth());
        UpdateHealth(data.getInitialHealth(), data.getRemainingHealth());
        textScore.setText(getString(R.string.gameTextScore, data.getGameScore()));


        //Creamos el receptor de las actualizaciones para la vida.
        uiUpdater = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction())
                {
                    case "HEALTH_UPDATE":
                        //Extraemos los datos.
                        Bundle healthData = intent.getExtras();
                        int totalHealth = healthData.getInt("TOTAL_HEALTH");
                        int remaining = healthData.getInt("REMAINING_HEALTH");
                        UpdateHealth(totalHealth, remaining);
                        break;
                    case "SCORE_UPDATE":
                        Bundle scoreData = intent.getExtras();
                        int score = scoreData.getInt("SCORE");
                        UpdateScore(score);
                        break;
                }
            }
        };


    }


    //////////////////////////


    @Override
    protected void onPause()
    {
        super.onPause();
        Choreographer.getInstance().removeFrameCallback(gameSurface);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(gameSurface.gameThread != null)
        {
            Log.d("GameActivity", "onResume re-hooking choreographer");
            Choreographer.getInstance().postFrameCallback(gameSurface);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("HEALTH_UPDATE");
        filter.addAction("SCORE_UPDATE");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver((uiUpdater), filter);
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(uiUpdater);
        super.onStop();
    }

    //Don't return on back press.
    @Override
    public void onBackPressed() {}


    /////////////////////////////


    private void initComponents()
    {



        gameSurface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //onTouchGameSurface((int)event.getX(), (int)event.getY());
                onTouchGameSurface(event);
                return true;
            }
        });

        healContainer = findViewById(R.id.healContainer);
        textScore = findViewById(R.id.gameTextScore);


        btPause = findViewById(R.id.btPause);
        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseDialog pd = new PauseDialog();
                pd.show(getSupportFragmentManager(), "PauseDialog");
                GameData.getInstance().setPaused(true);
            }
        });


        btNum1 = findViewById(R.id.btNum1);
        btNum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(1);
            }
        });

        btNum2 = findViewById(R.id.btNum2);
        btNum2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(2);
            }
        });

        btNum3 = findViewById(R.id.btNum3);
        btNum3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(3);
            }
        });

        btNum4 = findViewById(R.id.btNum4);
        btNum4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(4);
            }
        });

        btNum5 = findViewById(R.id.btNum5);
        btNum5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(5);
            }
        });

        btNum6 = findViewById(R.id.btNum6);
        btNum6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(6);
            }
        });

        btNum7 = findViewById(R.id.btNum7);
        btNum7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(7);
            }
        });

        btNum8 = findViewById(R.id.btNum8);
        btNum8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(8);
            }
        });

        btNum9 = findViewById(R.id.btNum9);
        btNum9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(9);
            }
        });

        btNum0 = findViewById(R.id.btNum0);
        btNum0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttackButtonClick(0);
            }
        });
    }

    private void onAttackButtonClick(int value)
    {
        GameHandler gh = gameSurface.gameThread.getHandler();
        gh.sendAttackButtonClick(value);
    }

    private void onTouchGameSurface(int posX, int posY)
    {
        GameHandler gh = gameSurface.gameThread.getHandler();
        gh.sendTouchPosition(posX, posY);
    }

    private void onTouchGameSurface(MotionEvent e)
    {
        GameHandler gh = gameSurface.gameThread.getHandler();
        gh.sendTouchEvent(e);
    }


    /**
     * Add heal containers to the layout.
     * @param amount Health amount.
     */
    private void createHealth(int amount)
    {
        //Create hearth contaniner.
        for(int i = 0; i < amount; i++)
        {
            ImageView heart = new ImageView(getApplicationContext());
            heart.setImageDrawable(getResources().getDrawable(R.drawable.hud_heartfull));
            heart.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
            heart.setPadding(8, 8, 8, 8);
            healContainer.addView(heart);
        }
    }


    private void UpdateHealth(int total, int remaining)
    {
        if(remaining < 0 || remaining >= total) { return; }

        Log.i("GameActivity", "UPDATE HEALTH:" + total + " / " + remaining);

        for(int i = remaining; i < total; i++)
        {
            //Añadimos la vida al contenedor.
            ImageView heart = (ImageView) healContainer.getChildAt(i);
            heart.setImageDrawable(getResources().getDrawable(R.drawable.hud_heartempty));
        }

        //If out of health, it's game over.
        if(remaining <= 0)
        {
            gameOver();
        }
    }


    private void UpdateScore(int newScore)
    {
        textScore.setText(getString(R.string.gameTextScore, newScore));
    }



    private void gameOver()
    {
        //Stop game and show gameOver dialog.
        GameData data = GameData.getInstance();
        data.setPaused(true);

        GameOverDialog god = new GameOverDialog();
        god.show(getSupportFragmentManager(), "gameOver");

        //If Score is > 0 -> Generate score data and save on DB.


        String username = GameData.getInstance().getUsername();
        int score = GameData.getInstance().getGameScore();
        GameModes mode = GameData.getInstance().getMode();

        if(score > 0)
        {
            Score sc = new Score(mode, score, username);

            DBConnection.getInstance().addScoreToDB(sc);
        }
    }
}