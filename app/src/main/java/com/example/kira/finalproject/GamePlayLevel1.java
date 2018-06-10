package com.example.kira.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GamePlayLevel1 extends AppCompatActivity {
    ImageView screen_play,screen_score,pic_one,pic_two,pic_three,text_one,text_two,text_three,pause_btn;
    int socau = 3,thutudahinh,thutudachu,idpicduocchon,idtextduocchon,score = 0;
    boolean imgIsOpen = false,textIsOpen = false;
    boolean pic1Right = false,pic2Right = false,pic3Right = false,text1Right = false,text2Right = false,text3Right = false;
    TextView tv_check,tv_score,tv_time;
    Dialog pauseMenu;
    CountDownTimer Timer;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pauseMenu = new Dialog(this);
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //close

        setContentView(R.layout.activity_game_play_level1);

        // khung chua hinh anh
        screen_play = (ImageView) findViewById(R.id.ScreenPlay);
        screen_play.setAlpha(80);
        //close

        //khung chua diem
        screen_score = (ImageView) findViewById(R.id.screenScore);
        screen_score.setAlpha(80);
        //close

        // code animation hinh anh
        final RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //Setup anim with desired properties
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
        anim.setDuration(700); //Put desired duration per anim cycle here, in milliseconds
        //close

        //database
        QuanLyCauHoi db = new QuanLyCauHoi(this);
        try{
            db.createDatabase();
        }catch (IOException e){
            e.printStackTrace();
        }
        //close

        //lay cau hoi
        Cursor contro = db.layNcauhoi(3);
        contro.moveToFirst();
        int[] idhinh = new int[socau];
        String[] cauhoi = new String [socau];
        String[] pichinh = new String [socau];
        String[] texthinh = new String [socau];
        int count = 0;
        do{
            count++;
            idhinh[count - 1] = Integer.parseInt(contro.getString(0));
            cauhoi[count - 1] = contro.getString(1);
            pichinh[count - 1] = contro.getString(2);
            texthinh[count -1] = contro.getString(3);
        }while(contro.moveToNext());
        //close

        //random image
        ArrayList<Integer> listdahinh = new ArrayList<Integer>();
        ArrayList<Integer> listdachu = new ArrayList<Integer>();
        for(int i=0;i<socau;i++){
            listdahinh.add(new Integer(i));
            listdachu.add(new Integer(i));
        }

        Collections.shuffle(listdahinh);


        String dahinh_1 = pichinh[listdahinh.get(0)];
        String dahinh_2 = pichinh[listdahinh.get(1)];
        String dahinh_3 = pichinh[listdahinh.get(2)];
        String dachu_1 = texthinh[listdachu.get(0)];
        String dachu_2 = texthinh[listdachu.get(1)];
        String dachu_3 = texthinh[listdachu.get(2)];
        //close

        //lay id va cau hoi cua hinh random
        final int iddahinh_1 = idhinh[listdahinh.get(0)];
        final int iddahinh_2 = idhinh[listdahinh.get(1)];
        final int iddahinh_3 = idhinh[listdahinh.get(2)];
        final int iddachu_1 = idhinh[listdachu.get(0)];
        final int iddachu_2 = idhinh[listdachu.get(1)];
        final int iddachu_3 = idhinh[listdachu.get(2)];
        //close

        pic_one = (ImageView) findViewById(R.id.picOne);
        pic_two = (ImageView) findViewById(R.id.picTwo);
        pic_three = (ImageView) findViewById(R.id.picThree);
        final int dahinh_one = getResources().getIdentifier(dahinh_1, "drawable", getPackageName());
        final int dahinh_two = getResources().getIdentifier(dahinh_2, "drawable", getPackageName());
        final int dahinh_three = getResources().getIdentifier(dahinh_3, "drawable", getPackageName());


        pic_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgIsOpen == false && pic1Right == false) {
                    pic_one.startAnimation(anim);
                    //Animation ngung sau 1 giay
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pic_one.clearAnimation();
                            pic_one.setImageResource(dahinh_one);
                            imgIsOpen = true;
                            thutudahinh = 1;
                            idpicduocchon = iddahinh_1;
                            checkSamePicture();
                            endLevel();
                        }
                    }, 1000);
                }
            }

        });
        pic_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgIsOpen == false && pic2Right == false) {
                    pic_two.startAnimation(anim);
                    //Animation ngung sau 1 giay
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pic_two.clearAnimation();
                            pic_two.setImageResource(dahinh_two);
                            imgIsOpen = true;
                            thutudahinh = 2;
                            idpicduocchon = iddahinh_2;
                            checkSamePicture();
                            endLevel();
                        }
                    }, 1000);
                }

            }

        });
        pic_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgIsOpen == false && pic3Right == false) {
                    pic_three.startAnimation(anim);
                    //Animation ngung sau 1 giay
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pic_three.clearAnimation();
                            pic_three.setImageResource(dahinh_three);
                            imgIsOpen = true;
                            thutudahinh = 3;
                            idpicduocchon = iddahinh_3;
                            checkSamePicture();
                            endLevel();
                        }
                    }, 1000);
                }

            }

        });

        text_one = (ImageView) findViewById(R.id.textOne);
        text_two = (ImageView) findViewById(R.id.textTwo);
        text_three = (ImageView) findViewById(R.id.textThree);
        final int dachu_one = getResources().getIdentifier(dachu_1, "drawable", getPackageName());
        final int dachu_two = getResources().getIdentifier(dachu_2, "drawable", getPackageName());
        final int dachu_three = getResources().getIdentifier(dachu_3, "drawable", getPackageName());

        text_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textIsOpen == false && text1Right == false) {
                    text_one.startAnimation(anim);
                    //Animation ngung sau 1 giay
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_one.clearAnimation();
                            text_one.setImageResource(dachu_one);
                            textIsOpen = true;
                            thutudachu = 1;
                            idtextduocchon = iddachu_1;
                            checkSamePicture();
                            endLevel();
                        }
                    }, 1000);
                }
            }

        });
        text_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textIsOpen == false && text2Right == false) {
                    text_two.startAnimation(anim);
                    //Animation ngung sau 1 giay
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_two.clearAnimation();
                            text_two.setImageResource(dachu_two);
                            textIsOpen = true;
                            thutudachu = 2;
                            idtextduocchon = iddachu_2;
                            checkSamePicture();
                            endLevel();
                        }
                    }, 1000);
                }

            }

        });
        text_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textIsOpen == false && text3Right == false) {
                    text_three.startAnimation(anim);
                    //Animation ngung sau 1 giay
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_three.clearAnimation();
                            text_three.setImageResource(dachu_three);
                            textIsOpen = true;
                            thutudachu = 3;
                            idtextduocchon = iddachu_3;
                            checkSamePicture();
                            endLevel();
                        }
                    }, 1000);
                }

            }

        });

        //dem gio choi
        tv_time = (TextView) findViewById(R.id.tvTime);
        Timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                tv_time.setText(String.valueOf(millisUntilFinished/1000));

            }
            @Override
            public void onFinish() {
                tv_time.setText("time's up");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishGame();
                    }
                }, 1500);
            }

        }.start();
        //close

        //pause option
        pause_btn = (ImageView) findViewById(R.id.pauseBtn);
        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GamePlayLevel1.super.onPause();
                showPausePopup();
            }
        });
        //close

        //close

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

    //ham kiem tra hinh
    public void checkSamePicture(){
        tv_check = (TextView) findViewById(R.id.tvCheck);
        tv_score = (TextView) findViewById(R.id.tvScore);
        //kt xem 2 hinh co khop nhau khong
        if(imgIsOpen == true && textIsOpen == true){
            if(idpicduocchon == idtextduocchon){
                if(thutudahinh == 1){
                    pic1Right = true;
                }else if(thutudahinh == 2){
                    pic2Right = true;
                }else if(thutudahinh == 3){
                    pic3Right = true;
                }
                if(thutudachu == 1){
                    text1Right = true;
                }else if(thutudachu == 2){
                    text2Right = true;
                }else if(thutudachu == 3){
                    text3Right = true;
                }
                imgIsOpen = false;
                textIsOpen = false;
                tv_check.setText("RIGHT");
                score += 100;
                tv_score.setText(score+" pt");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_check.setText("");
                    }
                }, 1000);

            }else {
                if(thutudahinh == 1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pic_one.setImageResource(R.drawable.mickey);
                        }
                    }, 1000);
                }else if(thutudahinh == 2){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pic_two.setImageResource(R.drawable.mickey);
                        }
                    }, 1000);
                }else if(thutudahinh == 3){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pic_three.setImageResource(R.drawable.mickey);
                        }
                    }, 1000);
                }
                if(thutudachu == 1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_one.setImageResource(R.drawable.pikachu);
                        }
                    }, 1000);
                }else if(thutudachu == 2){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_two.setImageResource(R.drawable.pikachu);
                        }
                    }, 1000);
                }else if(thutudachu == 3){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text_three.setImageResource(R.drawable.pikachu);
                        }
                    }, 1000);
                }
                imgIsOpen = false;
                textIsOpen = false;
                if(score > 0){
                    score = score - 20;
                    tv_score.setText(score+" pt");
                }
                tv_check.setText("WRONG");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_check.setText("");
                    }
                }, 1000);
            }
        }
        //close
    }
    //close

    //ham show popup pause
    public void showPausePopup(){
        ImageView btn_resume;
        ImageView btn_home;
        pauseMenu.setContentView(R.layout.pause_menu);
        btn_resume = (ImageView) pauseMenu.findViewById(R.id.btnResume);
        btn_home = (ImageView) pauseMenu.findViewById(R.id.btnHome);
        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GamePlayLevel1.super.onResume();
                pauseMenu.dismiss();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishGame();
            }
        });
        pauseMenu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pauseMenu.show();
    }
    //close

    //ham finish game
    public void finishGame(){
        //database
        QuanLyCauHoi db = new QuanLyCauHoi(this);
        try{
            db.createDatabase();
        }catch (IOException e){
            e.printStackTrace();
        }
        //close
        db.themScore(score);
        finish();
    }

    //ham ket thuc man
    public void endLevel(){
        if ( pic1Right == true && pic2Right == true && pic3Right == true){
            //database
            QuanLyCauHoi db = new QuanLyCauHoi(this);
            try{
                db.createDatabase();
            }catch (IOException e){
                e.printStackTrace();
            }
            //close
            db.themScore(score);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(GamePlayLevel1.this, finishGame.class);
                    startActivity(intent);;
                }
            }, 1000);
        }
    }



}
