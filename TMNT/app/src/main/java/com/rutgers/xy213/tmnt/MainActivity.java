package com.rutgers.xy213.tmnt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spin = (Spinner)findViewById(R.id.tmnt);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)//first argument infers current spinner, can be spin in this case
            {
                TextView result = (TextView)findViewById(R.id.result);
                result.setText("You choose " + parent.getSelectedItem());//inner class matches argument above, can be spin in this case

                ImageView imgresult = (ImageView)findViewById(R.id.imageView);
                if (position == 0)
                {
                    imgresult.setImageResource(R.drawable.leo);
                }
               if (position == 1)
               {
                   imgresult.setImageResource(R.drawable.micky);
               }
               if (position == 2)
               {
                   imgresult.setImageResource(R.drawable.don);
               }
               if (position == 3)
               {
                   imgresult.setImageResource(R.drawable.raph);
               }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
