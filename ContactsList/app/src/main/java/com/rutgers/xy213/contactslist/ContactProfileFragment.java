package com.rutgers.xy213.contactslist;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactProfileFragment extends Fragment {
    private TextView txt_profile_name;
    private TextView txt_profile_phone;
    private ListView lst_profile_relation;
    private String passedname;
    private ImageView img_profile_avatar;
    private Bitmap bitmap;
    private ArrayAdapter<String> mAdapter;

    public ContactProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_profile, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        passedname = bundle.getString("passname").toString();//get passed name
        txt_profile_name = (TextView)view.findViewById(R.id.txt_profile_name);
        txt_profile_phone = (TextView)view.findViewById(R.id.txt_profile_phone);
        lst_profile_relation = (ListView)view.findViewById(R.id.lst_profile_relation);
        img_profile_avatar = (ImageView)view.findViewById(R.id.img_profile_avatar);

        updateprofile();//call refresh
        //avatar click listener
        img_profile_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp=((BitmapDrawable)img_profile_avatar.getDrawable()).getBitmap();//get bitmap from imageview
                Intent intent=new Intent(getActivity(),FullScreenImage.class);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte [] bitmapByte =baos.toByteArray();//transfer to bitmapbyte
                intent.putExtra("bitmap", bitmapByte);//pass bitmap to new activity
                startActivity(intent);
            }
        });
        //relation list item lick listener
        lst_profile_relation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//under landscape
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("passname",mAdapter.getItem(position).toString());//put chosen name into bundle
                    //Toast.makeText(getApplicationContext(),bundle.getString("passname").toString(),Toast.LENGTH_SHORT).show();
                    ContactProfileFragment contactprofilefragment = new ContactProfileFragment();
                    contactprofilefragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.land_othercontainer, contactprofilefragment).commitAllowingStateLoss();//replace current profile fragment
                }
                else//under portrait mode
                {
                    Intent intent = new Intent(getActivity(), ContactProfile.class);//start contact profile avtivity
                    intent.putExtra("passname", mAdapter.getItem(position).toString());//pass parameter: name
                    startActivity(intent);
                }
            }
        });
    }

    public void updateprofile()//update and refresh function
    {
        txt_profile_name.setText(passedname);//set name as the passed text from main activity
        SharedPreferences prefs = getActivity().getSharedPreferences("phonelst", MODE_PRIVATE | MODE_APPEND);//get phone from phonelst
        String phone = prefs.getString(passedname,"");//get phone by name in name - phone pair
        txt_profile_phone.setText(phone);//set phone number
        String path= "/"+passedname+".png";//set path for image
     //   Toast.makeText(getActivity().getApplicationContext(), Environment.getExternalStorageDirectory()+path,Toast.LENGTH_SHORT).show();
        File mFile=new File(Environment.getExternalStorageDirectory(),path);//find path
        if (mFile.exists())//if image exist, use it
        {
            bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+path);
            img_profile_avatar.setImageBitmap(bitmap);
        }
        else//if image not exist, use default
        {
            img_profile_avatar.setImageResource(R.mipmap.ic_launcher);
        }

        Set<String> defValues = new HashSet<>();
        SharedPreferences prefs2 = getActivity().getSharedPreferences("relationlst",MODE_PRIVATE | MODE_APPEND);//open relationlst to store name - relation pairs
        //SharedPreferences.Editor prefsEditor2 = prefs2.edit();
        Set<String> values = prefs2.getStringSet(passedname, defValues);
        List<String> mStringList = new ArrayList<>(values);
        mAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mStringList);//trans sets into list
        // prefsEditor2.apply();
        lst_profile_relation.setAdapter(mAdapter);//update adapter
    }

    public String returnname()
    {
        return passedname;
    }

    public void setname(String setname)
    {
        passedname = setname;
    }




}
