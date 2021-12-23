package edu.rutgers.winlab.gruteser.rockpaperscissorsspinner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final int ROCK = 0;
    final int PAPER = 1;
    final int SCISSORS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Spinner)findViewById(R.id.user_choice)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spin, View v, int i, long id) {
                score((int)spin.getSelectedItemId());
            }

            public void onNothingSelected(AdapterView<?> spin) {}
        });
    }


    void score(int userChoice) {

        // Random choice for opponent
        Random randy = new Random();
        int opponentsChoice = randy.nextInt(3);

        // Update image according to opponentsChoice
        ImageView opponentImage = (ImageView) findViewById(R.id.opponent_image);
        int imageResource=R.drawable.paper;
        switch(opponentsChoice) {
            case ROCK:
                imageResource=R.drawable.rock;
                break;
            case PAPER:
                imageResource=R.drawable.paper;
                break;
            case SCISSORS:
                imageResource=R.drawable.scissors;
        }
        opponentImage.setImageResource(imageResource);

        // Notify about outcome with Toast
        if(userChoice == opponentsChoice) {
            Toast.makeText(this,"Redo!",Toast.LENGTH_SHORT).show();
        } else if(userChoice == (opponentsChoice+1)%3) {
            Toast.makeText(this,"Win!",Toast.LENGTH_SHORT).show();
            //TextView tv = (TextView) findViewById(R.id.score);
            //tv.setText(Integer.parseInt(tv.getText().toString()) + 1 + "");
        } else {
            Toast.makeText(this,"Loss!", Toast.LENGTH_SHORT).show();
        }
    }

}