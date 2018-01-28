package com.rutgers.xy213.contactslist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.R.attr.path;
import static android.R.attr.scaleHeight;
import static android.R.attr.scaleWidth;

public class ContactProfile extends AppCompatActivity {

    private String passedname;
    private ContactProfileFragment contactprofilefragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);

        Intent intent = getIntent();
        passedname = intent.getStringExtra("passname");//get passed name to show

        Bundle bundle = new Bundle();
        bundle.putString("passname",passedname);//put name into bundle to pass to profile fragment
        //Toast.makeText(getApplicationContext(),bundle.getString("passname").toString(),Toast.LENGTH_SHORT).show();
        contactprofilefragment = new ContactProfileFragment();
        contactprofilefragment.setArguments(bundle);//set argument to pass
        getFragmentManager().beginTransaction().add(R.id.land_othercontainer, contactprofilefragment).commitAllowingStateLoss();//show profile fragment
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//if under landscape show mainactivity fragment on the left
        {
            MainActivityFragment mainactivityfragment = new MainActivityFragment();
            getFragmentManager().beginTransaction().add(R.id.fl_mainactivity_container, mainactivityfragment).commitAllowingStateLoss();
        }
    }

    public void onSaveInstanceState(Bundle outState)
    {
      //  super.onSaveInstanceState(outState);
        outState.putString("passname",contactprofilefragment.returnname());//save name displaying
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        passedname = savedInstanceState.getString("passname");//restore name to show
        Bundle bundle = new Bundle();
        bundle.putString("passname",passedname);
        contactprofilefragment.setname(passedname);//restore name in profile fragment
    }
}
