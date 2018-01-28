package com.rutgers.xy213.rock_papper_scissors;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int rn;
    private Button btnshowres;
    public int win = 0;
    public int lose = 0;
    public int draw = 0;

    private TextView txttest;
    private Spinner spin;
    private ImageView imgresult;

    final int ROCK = 0;
    final int PAPER = 1;
    final int SCISSORS = 2;
    private Button btnfight;
    String rk="Rock",pp="Paper",sc="Scissors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // final String[] pics = getResources().getStringArray(R.array.rpst);


        btnshowres = (Button)findViewById(R.id.btnshowres);

        btnshowres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this,ShowresultActivity.class));
                Intent intent = new Intent(MainActivity.this, ShowresultActivity.class);
                intent.putExtra("win",String.valueOf(win));
                intent.putExtra("lose",String.valueOf(lose));
                intent.putExtra("draw",String.valueOf(draw));
                startActivity(intent);

            }
        });

        txttest = (TextView)findViewById(R.id.txttest);
        imgresult = (ImageView)findViewById(R.id.imageView);
        btnfight = (Button)findViewById(R.id.btnfight);
        spin = (Spinner)findViewById(R.id.rps);

        btnfight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                rn = rand.nextInt(3);

                String[] str= new String[]{"Rock", "Paper", "Scissors"};



                switch (rn)
                {
                    case 0:
                    {
                        imgresult.setImageResource(R.drawable.rock);
                        //      Toast.makeText(MainActivity.this, "Rock", Toast.LENGTH_SHORT).show();
                        if (spin.getSelectedItem().toString().equals(sc))
                        {
                            Toast.makeText(MainActivity.this, "You lose", Toast.LENGTH_SHORT).show();
                            lose++;
                        }

                        if (spin.getSelectedItem().toString().equals(pp))
                        {
                            Toast.makeText(MainActivity.this, "You win", Toast.LENGTH_SHORT).show();
                            win++;
                        }
                        if (spin.getSelectedItem().toString().equals(rk))
                        {
                            Toast.makeText(MainActivity.this, "Draw", Toast.LENGTH_SHORT).show();
                            draw++;
                        }
                        break;
                    }
                    case 1:
                    {
                        imgresult.setImageResource(R.drawable.paper);
                        if (spin.getSelectedItem().toString().equals(sc))
                        {
                            Toast.makeText(MainActivity.this, "You win", Toast.LENGTH_SHORT).show();
                            win++;
                        }
                        if (spin.getSelectedItem().toString().equals(rk))
                        {
                            Toast.makeText(MainActivity.this, "You lose", Toast.LENGTH_SHORT).show();
                            lose++;
                        }
                        if (spin.getSelectedItem().toString().equals(pp))
                        {
                            Toast.makeText(MainActivity.this, "Draw", Toast.LENGTH_SHORT).show();
                            draw++;
                        }
                        break;
                    }
                    case 2:
                    {
                        imgresult.setImageResource(R.drawable.scissors);
                        if (spin.getSelectedItem().toString().equals(rk))
                        {
                            Toast.makeText(MainActivity.this, "You win", Toast.LENGTH_SHORT).show();
                            win++;
                        }
                        if (spin.getSelectedItem().toString().equals(pp))
                        {
                            Toast.makeText(MainActivity.this, "You lose", Toast.LENGTH_SHORT).show();
                            lose++;
                        }
                        if (spin.getSelectedItem().toString().equals(sc))
                        {
                            Toast.makeText(MainActivity.this, "Draw", Toast.LENGTH_SHORT).show();
                            draw++;
                        }
                        break;
                    }
                }
                txttest.setText("Win: " + win + " Lose: " + lose  + " Draw: "+ draw);
            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textresult = (TextView)findViewById(R.id.textresult);
                textresult.setText("You choose " + parent.getSelectedItem());
        //        Toast.makeText(MainActivity.this, spin.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("win",win);
        outState.putInt("lose",lose);
        outState.putInt("draw",draw);

        outState.putInt("sys",rn);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        win = savedInstanceState.getInt("win");
        lose = savedInstanceState.getInt("lose");
        draw = savedInstanceState.getInt("draw");

        rn = savedInstanceState.getInt("sys");
        switch (rn)
        {
            case 0:
            {
                imgresult.setImageResource(R.drawable.rock);
                break;
            }
            case 1:
            {
                imgresult.setImageResource(R.drawable.paper);
                break;
            }
            case 2:
            {
                imgresult.setImageResource(R.drawable.scissors);
                break;
            }

        }



        txttest.setText("Win: " + win + "Lose: " + lose  + "Draw: "+ draw);
    }

}
