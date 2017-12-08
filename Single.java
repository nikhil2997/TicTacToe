package com.example.nikhil.tictactoe;


import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Single extends AppCompatActivity {

    int[][] board=new int[3][3];
    boolean active = true;
    boolean gamestatus;
    MediaPlayer med1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 2;
            }
        }
        med1=MediaPlayer.create(this,R.raw.chibi);
        med1.start();

    }


    boolean hasMovesLeft() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 2)
                    return true;
        return false;
    }


    int haswon() {

        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] &&
                    board[row][1] == board[row][2]) {
                if (board[row][0] == 1)
                    return +10;
                else if (board[row][0] == 0)
                    return -10;
            }
        }


        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] &&
                    board[1][col] == board[2][col]) {
                if (board[0][col] == 1)
                    return +10;

                else if (board[0][col] == 0)
                    return -10;
            }
        }


        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 1)
                return +10;
            else if (board[0][0] == 0)
                return -10;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 1)
                return +10;
            else if (board[0][2] == 0)
                return -10;
        }

        return 0;
    }


    public void drop(View view) {

        ImageView imageView = (ImageView) view;
        String str = imageView.getTag().toString();

        int[] arr = new int[2];
        String s1 = String.valueOf(str.charAt(0));
        String s2 = String.valueOf(str.charAt(1));
        arr[0] = Integer.parseInt(s1);
        arr[1] = Integer.parseInt(s2);


        if (active && board[arr[0]][arr[1]] == 2 && !gamestatus)
        {


            board[arr[0]][arr[1]] = 0;

            imageView.setTranslationY(-1000f);
            imageView.setImageResource(R.drawable.red);
            imageView.animate().translationYBy(1000f).setDuration(300);

            gamestatus= result();


            if (!gamestatus && active) {

                  Points best = findBestMove();


                  int x1=best.x;
                  int y2=best.y;

                board[x1][y2]=1;

                String str1= String.valueOf(x1);
                String str2=String.valueOf(y2);
                String res=str1+str2;




                ImageView imageView1=(ImageView) findViewById(R.id.activity_single).findViewWithTag(res);
                imageView1.setTranslationY(-1000f);
                imageView1.setImageResource(R.drawable.blue);
                imageView1.animate().translationYBy(1000f).setDuration(600);

                gamestatus= result();


            }


        }

    }


    Points findBestMove() {
        int bestVal = -1000;
        Points bestMove = new Points(-1,-1);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (board[i][j] == 2) {

                    board[i][j] = 1;


                    int moveVal = minimax(0, false);


                    board[i][j] = 2;


                    if (moveVal > bestVal) {
                        bestMove.x = i;
                        bestMove.y = j;
                        bestVal = moveVal;
                    }
                }
            }
        }


        return bestMove;
    }


    int minimax(int depth, boolean isMax)
    {
        int score = haswon();


        if (score == 10)
            return score;


        if (score == -10)
            return score;


        if (!hasMovesLeft())
            return 0;


        if (isMax) {
            int best = -1000;


            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == 2) {

                        board[i][j] = 1;


                        best = Math.max(best,
                                minimax(depth + 1, !isMax));


                        board[i][j] = 2;
                    }
                }
            }
            return best;
        }
        else {

            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == 2) {

                        board[i][j] = 0;


                        best = Math.min(best,
                                minimax(depth + 1, !isMax));


                        board[i][j] = 2;
                    }
                }
            }
            return best;
        }
    }


    public boolean result() {

        int res = haswon();

        if (res == -10) {
            active = false;
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText("You have won the game!!");

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
            linearLayout.setVisibility(View.VISIBLE);

            return true;
        } else if (res == 10) {
            active = false;
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText("You have lost the game!!");

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
            linearLayout.setVisibility(View.VISIBLE);

            return true;

        } else if (hasMovesLeft() == false) {
            active = false;
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText("It is a draw");

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
            linearLayout.setVisibility(View.VISIBLE);

            return true;

        }

        return false;

    }

    public void playagain(View view) {
        active = true;
        gamestatus=false;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        linearLayout.setVisibility(View.INVISIBLE);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 2;
            }
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
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



}

