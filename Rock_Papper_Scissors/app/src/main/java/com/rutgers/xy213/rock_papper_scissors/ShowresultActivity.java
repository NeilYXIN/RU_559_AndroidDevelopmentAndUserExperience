package com.rutgers.xy213.rock_papper_scissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowresultActivity extends AppCompatActivity {
private TextView txtresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showresult);
        Intent intent = getIntent();
        String win = intent.getStringExtra("win");
        String lose = intent.getStringExtra("lose");
        String draw = intent.getStringExtra("draw");


        txtresult = (TextView)findViewById(R.id.txtresult);

        txtresult.setText("Win: " + win + " Lose: " + lose + " Draw: "+ draw);


    }
}
