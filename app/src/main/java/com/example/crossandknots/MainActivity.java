package com.example.crossandknots;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ImageView[][] position = new ImageView[3][3];
    GameEngine ttt = new GameEngine();

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        position[0][0] = (ImageView) findViewById(R.id.i1);
        position[0][1] = (ImageView) findViewById(R.id.i2);
        position[0][2] = (ImageView) findViewById(R.id.i3);
        position[1][0] = (ImageView) findViewById(R.id.i4);
        position[1][1] = (ImageView) findViewById(R.id.i5);
        position[1][2] = (ImageView) findViewById(R.id.i6);
        position[2][0] = (ImageView) findViewById(R.id.i7);
        position[2][1] = (ImageView) findViewById(R.id.i8);
        position[2][2] = (ImageView) findViewById(R.id.i9);

        ttt.initBoard();
        List<Integer> coord = ttt.moveGeneratorMain( -1, -1);
        position[coord.get(0)][coord.get(1)].setImageResource(R.drawable.x);
        Log.d("Auto call", "This is the first call");
        count++;

        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position[i].length; j++) {
                position[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int ux = -1, uy = -1;
                        ImageView viewImg = (ImageView) view;
                        if (ttt.winner() != -1) {
                            Toast.makeText(MainActivity.this, ttt.winner() == 1 ? "Player Wins" : "Computer Wins", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (count % 2 != 0) {

                            switch (view.getId()) {
                                case R.id.i1:
                                    ux = 0;
                                    uy = 0;
                                    break;
                                case R.id.i2:
                                    ux = 0;
                                    uy = 1;
                                    break;
                                case R.id.i3:
                                    ux = 0;
                                    uy = 2;
                                    break;
                                case R.id.i4:
                                    ux = 1;
                                    uy = 0;
                                    break;
                                case R.id.i5:
                                    ux = 1;
                                    uy = 1;
                                    break;
                                case R.id.i6:
                                    ux = 1;
                                    uy = 2;
                                    break;
                                case R.id.i7:
                                    ux = 2;
                                    uy = 0;
                                    break;
                                case R.id.i8:
                                    ux = 2;
                                    uy = 1;
                                    break;
                                case R.id.i9:
                                    ux = 2;
                                    uy = 2;
                                    break;


                            }


                            viewImg.setImageResource(R.drawable.o);

                            ttt.moveGeneratorMain( ux, uy);
                            count++;
                            if (ttt.winner() != -1) {
                                Toast.makeText(MainActivity.this, ttt.winner() == 1 ? "Player Wins" : "Computer Wins", Toast.LENGTH_SHORT).show();
                                return;
                            }


                        }

                        if (ttt.winner() != -1) {
                            Toast.makeText(MainActivity.this, ttt.winner() == 1 ? "Player Wins" : "Computer Wins", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        List<Integer> coord = ttt.moveGeneratorMain( -1, -1);
                        position[coord.get(0)][coord.get(1)].setImageResource(R.drawable.x);
                        count++;
                        if (ttt.winner() != -1) {
                            Toast.makeText(MainActivity.this, ttt.winner() == 1 ? "Player Wins" : "Computer Wins", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                });
            }
        }


    }

    public void onResetClick(View view) {
        ttt.initBoard();
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position[i].length; j++) {

                position[i][j].setImageResource(R.drawable.ic_launcher_background);

            }
        }
        recreate();
    }


//    public static int runGame(boolean user) {
//        GameEngine ttt = new GameEngine();
//
//        ttt.initBoard();
//        Scanner scn = new Scanner(System.in);
//        int ux = 0, uy = 0;
//
//        int count = 0;
//        while (ttt.winner() == -1 && count < 9) {
//            if (count % 2 != 0) {
//
//                System.out.println("Enter x");
//                ux = scn.nextInt();
//                System.out.println("Enter y");
//                uy = scn.nextInt();
//
//                ttt.moveGeneratorMain(true, ux, uy);
//            } else {
//                ttt.moveGeneratorMain(true, -1, -1);
//            }
//            count++;
//        }
//        return ttt.winner();
//    }


}
