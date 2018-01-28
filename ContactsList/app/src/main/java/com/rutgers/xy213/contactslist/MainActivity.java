package com.rutgers.xy213.contactslist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.BoolRes;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.R.attr.defaultValue;

public class MainActivity extends Activity {
    public MainActivityFragment mainactivityfragment;
    private int flag =0;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainactivityfragment = new MainActivityFragment();
        getFragmentManager().beginTransaction().add(R.id.fl_mainactivity_container, mainactivityfragment).commitAllowingStateLoss();//add mainactivity fragment to framelayout

        SharedPreferences prefs3 = getSharedPreferences("updatepor",MODE_PRIVATE | MODE_APPEND);//open namelst to store countId - name pair and name - count pairs//**********************
        SharedPreferences.Editor prefsEditor3 = prefs3.edit();
        prefsEditor3.remove("flag");
        prefsEditor3.putInt("flag",0);//set flag to notify mainactivity fragment to update list
        prefsEditor3.apply();
    }

    public void onSaveInstanceState(Bundle outState)
    {
        //super.onSaveInstanceState(outState);//reload view when refresh, so don't use super
        if (!mainactivityfragment.mAdapter.isselected.isEmpty())//when isselected map in adapter in mainactivity fragment has context
        {
            outState.putBundle("checkdetail1",mainactivityfragment.returnmap1());//save information of selected checkbox
            flag=1;//set flag as 1
            outState.putInt("flag",flag);//save flag
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        flag = savedInstanceState.getInt("flag");//get flag to determine when it's portrait and returned from contact detail
        if (flag==1)
        {
            bundle = savedInstanceState.getBundle("checkdetail1");//get saved checkbox selection
            mainactivityfragment.setmap1(bundle);//set checkbox selection in main activity fragment
        }
    }

    public void setflag(int tflag)
    {
        flag = tflag;//set flag from outside
    }


}
