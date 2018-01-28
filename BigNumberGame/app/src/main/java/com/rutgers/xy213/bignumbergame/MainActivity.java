package com.rutgers.xy213.bignumbergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int leftNumber;
    private int rightNumber;
    private int middleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNumbers();
    }

    public void setNumbers(){
        Random rand = new Random();
        leftNumber = rand.nextInt(10);
        rightNumber = rand.nextInt(10);
        middleNumber = rand.nextInt(10);

        Button leftButton = (Button) findViewById(R.id.leftButton);
        leftButton.setText(leftNumber+"");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftNumber >= rightNumber && leftNumber >= middleNumber)
                    Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Duh!",Toast.LENGTH_SHORT).show();
                setNumbers();
            }
        });

        Button rightButton = (Button) findViewById(R.id.rightButton);
        rightButton.setText(rightNumber+"");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightNumber >= leftNumber && rightNumber >= middleNumber)
                    Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Duh!",Toast.LENGTH_SHORT).show();
                setNumbers();
            }
        });

        Button middleButton = (Button) findViewById(R.id.middleButton);
        middleButton.setText(middleNumber+"");
        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (middleNumber >= rightNumber && middleNumber >= leftNumber)
                    Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Duh!",Toast.LENGTH_SHORT).show();
                setNumbers();
            }
        });


    }
/*
    public void onClickLeft(View view){
        if (leftNumber >= rightNumber)
            Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"Duh!",Toast.LENGTH_SHORT).show();
        setNumbers();
    }
*/

}
