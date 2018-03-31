package com.project0.wuziqi.wuziqi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.*;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private Chronometer mChronometer;
    private RelativeLayout StartBoard;
    private PopupWindow ppw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button pvestart=(Button)findViewById(R.id.pveStart);
        final Button pvpstart=(Button)findViewById(R.id.pvpStart);
        final Button setting=(Button)findViewById(R.id.setting);
        final Button quitapp=(Button)findViewById(R.id.quitApp);
        final ImageButton returntomenu=(ImageButton)findViewById(R.id.returnToMenu);
        final Board board = (Board)findViewById(R.id.board);
        final RelativeLayout startmenu=(RelativeLayout)findViewById(R.id.startmenu);
        final RelativeLayout startboard=(RelativeLayout)findViewById(R.id.startboard);
        StartBoard = startboard;

        //set listener
        pvestart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                hideButton(startmenu,startboard);
                board.setGameMode(Board.PVE);
            }
        });
        pvpstart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                hideButton(startmenu,startboard);
                board.setGameMode(Board.PVP);
            }
        });
        setting.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                settingSelect();
            }
        });
        quitapp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
        returntomenu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayButton(startmenu,startboard);

                board.reset();
            }
        });
    }

    void hideButton(RelativeLayout startmenu,RelativeLayout startboard){
        AlphaAnimation animation = alphaAni(true,0);
        AlphaAnimation animation1 = alphaAni(false,0);
        startmenu.setAnimation(animation);
        startboard.setAnimation(animation1);

        animation.startNow();
        startmenu.setVisibility(View.GONE);

        animation1.startNow();
        startboard.setVisibility(View.VISIBLE);
        mChronometer = (Chronometer)findViewById(R.id.chronometer);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }
    void displayButton(RelativeLayout startmenu,RelativeLayout startboard){
        AlphaAnimation animation = alphaAni(true,0);
        AlphaAnimation animation1 = alphaAni(false,0);
        startmenu.setAnimation(animation1);
        startboard.setAnimation(animation);

        animation.startNow();
        startboard.setVisibility(View.GONE);

        animation1.startNow();
        startmenu.setVisibility(View.VISIBLE);
        mChronometer.stop();

    }
    AlphaAnimation alphaAni(boolean i,long delay){
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        if(i==false)
            animation = new AlphaAnimation(0, 1);
        animation.setDuration(500);
        animation.setRepeatCount(0);
        animation.setFillAfter(false);
        animation.setStartOffset(delay);
        return animation;
    }
    void settingSelect(){
        String[] settingSelect = {"关于我们"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setItems(settingSelect, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("");
                        builder1.setMessage("Powered by 徐含,彭涵钧");
                        builder1.setPositiveButton("确定",null);
                        builder1.show();
                        break;
                }
            }
        });

        builder.show();
    }
    /*
    void showPopupWindow(){

        ListView listView = new ListView(this);

        Button aboutUs = new Button(listView.getContext());
        aboutUs.setText("关于我们");
        aboutUs.setVisibility(View.VISIBLE);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog =new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("");
                dialog.setMessage("Powered by 徐含,彭涵钧");
                dialog.setPositiveButton("确定",null);
                dialog.show();
            }
        });

        Vector<Button> vector = new Vector<Button>();
        vector.add(aboutUs);
        ArrayAdapter<Button> adapter = new ArrayAdapter<Button>(this, android.R.layout.simple_expandable_list_item_1,vector);

        listView.setAdapter(adapter);

        ppw = new PopupWindow();
        ppw.setHeight(300);
        ppw.setWidth(200);
        ppw.setBackgroundDrawable(new ColorDrawable(Color.argb(255, 193, 132, 52)));
        ppw.setOutsideTouchable(true);
        ppw.setFocusable(true);
        ppw.setContentView(listView);
    }
    */
}
