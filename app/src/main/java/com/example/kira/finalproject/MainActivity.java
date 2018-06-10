package com.example.kira.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView btn_play,btn_highscore,btn_out;
    Dialog scorePanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scorePanel = new Dialog(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        btn_play = (ImageView) findViewById(R.id.btnPlay);
        btn_highscore = (ImageView) findViewById(R.id.btnHighscore);
        btn_out = (ImageView) findViewById(R.id.btnOut);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GamePlayLevel1.class);

                startActivity(intent);
            }
        });

        btn_highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHighScore();
            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                finish();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getHighScore(){
        ImageView btn_close;
        TextView tv_score;
        scorePanel.setContentView(R.layout.score_panel);
        btn_close = (ImageView) scorePanel.findViewById(R.id.btnClose);
        tv_score = (TextView) scorePanel.findViewById(R.id.tvScore);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scorePanel.dismiss();
            }
        });
        //database
        QuanLyCauHoi db = new QuanLyCauHoi(this);
        try{
            db.createDatabase();
        }catch (IOException e){
            e.printStackTrace();
        }
        //close

        //lay score
        Cursor contro = db.layScoreCaoNhat();
        contro.moveToFirst();
        int score = 0;
        do{
            score = Integer.parseInt(contro.getString(1));
        }while(contro.moveToNext());
        //close
        if(score == 0){
            tv_score.setText("empty !!!");
        }else{
            tv_score.setText(score+" point");
        }
        scorePanel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        scorePanel.show();


    }
}
