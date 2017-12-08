package com.example.nikhil.tictactoe;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Multiple extends AppCompatActivity {

    int activeplayer=0;
    int[] gamestate={2,2,2,2,2,2,2,2,2};
    int[][] winpos={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean active=true;
    MediaPlayer med1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple);
        med1=MediaPlayer.create(this,R.raw.chibi);
        med1.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        med1.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        med1.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        med1.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        med1.start();
    }


    public void drop(View view) {

        ImageView imageView = (ImageView) view;

        int tapped = Integer.parseInt(imageView.getTag().toString());

        if (gamestate[tapped] == 2 && active) {

            gamestate[tapped]=activeplayer;
            imageView.setTranslationY(-1000f);
            if (activeplayer == 0) {
                imageView.setImageResource(R.drawable.red);
                activeplayer = 1;
            } else {
                imageView.setImageResource(R.drawable.blue);
                activeplayer = 0;

            }
            imageView.animate().translationYBy(1000f).setDuration(300);

            for(int[] winary:winpos)
            {
                if(gamestate[winary[0]] == gamestate[winary[1]]  && gamestate[winary[1]]==gamestate[winary[2]] && gamestate[winary[0]]!=2)
                {
                    active=false;
                    String winner="X";
                    if(gamestate[winary[0]]==1)
                    {
                        winner="O";
                    }
                    TextView t=(TextView)findViewById(R.id.textView);
                    t.setText(winner+" has won the game!!");


                    LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearlayout);
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    boolean gameover=true;
                    for(int counter:gamestate)
                    {
                        if(counter==2)
                            gameover=false;
                    }
                    if(gameover)
                    {
                        TextView t=(TextView)findViewById(R.id.textView);
                        t.setText("It is a draw!!");


                        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearlayout);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }


    public void playagain(View view)
    {
        active=true;
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearlayout);
        linearLayout.setVisibility(View.INVISIBLE);

        activeplayer=0;
        for(int i=0;i<gamestate.length;i++)
        {
            gamestate[i]=2;
        }

        GridLayout gridLayout=(GridLayout)findViewById(R.id.gridlayout);

        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }



}
