package com.rutgers.xy213.contactslist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FullScreenImage extends AppCompatActivity {
    private Bitmap bitmap;
    private ImageView img_full;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        img_full = (ImageView) findViewById(R.id.img_full);
        Intent intent = getIntent();
        if(intent !=null)
        {
            byte [] bis=intent.getByteArrayExtra("bitmap");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
            img_full.setImageBitmap(bitmap);
        }
        img_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullScreenImage.this.finish();
            }
        });
    }
}
