package com.rutgers.xy213.rps1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int rn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnrock = (Button)findViewById(R.id.btnrock);
        Button btnpaper = (Button)findViewById(R.id.btnpaper);
        Button btnscissors = (Button)findViewById(R.id.btnscissors);
        btnrock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random rand = new Random();
                rn = rand.nextInt(3);
                TextView text1 = (TextView)findViewById(R.id.text);
                switch (rn){
                    default:
                    case 0:
                        text1.setText("Rock");
                        Toast.makeText(getApplicationContext(),"Tied",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        text1.setText("Paper");
                        Toast.makeText(getApplicationContext(),"You lose",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        text1.setText("Scissors");
                        Toast.makeText(getApplicationContext(),"You win",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        btnpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView)findViewById(R.id.text);
                Random rand = new Random();
                rn = rand.nextInt(3);
                switch (rn){
                    default:
                    case 0:
                        text.setText("Rock");
                        Toast.makeText(getApplicationContext(),"You win",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        text.setText("Paper");
                        Toast.makeText(getApplicationContext(),"Tied",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        text.setText("Scissors");
                        Toast.makeText(getApplicationContext(),"You lose",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        btnscissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView)findViewById(R.id.text);
                Random rand = new Random();
                rn = rand.nextInt(3);
                switch (rn){
                    default:
                    case 0:
                        text.setText("Rock");
                        Toast.makeText(getApplicationContext(),"You lose",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        text.setText("Paper");
                        Toast.makeText(getApplicationContext(),"You win",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        text.setText("Scissors");
                        Toast.makeText(getApplicationContext(),"Tied",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}

