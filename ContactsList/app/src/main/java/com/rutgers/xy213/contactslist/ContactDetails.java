package com.rutgers.xy213.contactslist;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.defaultValue;
import static android.R.attr.scaleHeight;
import static android.R.attr.scaleWidth;
import static com.rutgers.xy213.contactslist.R.mipmap.ic_launcher;

public class ContactDetails extends Activity  {
    private int flag = 0;
    public ContactDetailsFragment contactdetailfragment;
    public   MainActivityFragment mainactivityfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//when under landscape
        {
            mainactivityfragment = new MainActivityFragment();
            getFragmentManager().beginTransaction().add(R.id.fl_mainactivity_container, mainactivityfragment).commitAllowingStateLoss();//show mainactivity fragment
        }
        contactdetailfragment = new ContactDetailsFragment();
        getFragmentManager().beginTransaction().add(R.id.land_othercontainer, contactdetailfragment).commitAllowingStateLoss();//show contact detail fragment anyway
    }

    public void onSaveInstanceState(Bundle outState)
    {
        //  super.onSaveInstanceState(outState);
        outState.putString("edtname",contactdetailfragment.returnname());//save name in edittext
        outState.putString("edtphone",contactdetailfragment.returnphone());//save phone in edittext
        if (!contactdetailfragment.mAdapter.isselected.isEmpty())//while relation checkbox is not empty
        {
            outState.putBundle("checkdetail",contactdetailfragment.returnmap());//save checkbox selection
            flag=1;//update flag to show need restore relation checkbox selection
            outState.putInt("flag",flag);//save flag
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        contactdetailfragment.setname(savedInstanceState.getString("edtname"));//restore input name
        contactdetailfragment.setphone(savedInstanceState.getString("edtphone"));//restore input phone
        flag = savedInstanceState.getInt("flag");//restore flag
        if (flag == 1)//when need to restore relationship checkbox selection
        {
            Bundle bundle = savedInstanceState.getBundle("checkdetail");//get map bundle
            contactdetailfragment.setmap(bundle);//set map to isselection in contact detail fragment to restore checkbox selection
        }
        flag =0;//reset flag to 0: no element selected so no need to restore
    }

    public void setflag(int tflag)
    {
        flag = tflag;
    }//call from outside to change flag
}
