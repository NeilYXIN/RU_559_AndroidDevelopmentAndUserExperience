package com.rutgers.xy213.contactslist;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.defaultValue;
import static android.R.attr.mipMap;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import static com.rutgers.xy213.contactslist.R.mipmap.ic_launcher;



public class ContactDetailsFragment extends Fragment{
    private ImageView img_avatar;
    private ListView lst_detail_relation;
    private Button btn_addcon;
    private EditText edt_name;
    private EditText edt_phone;
    private Bitmap bitmap;
    private String bitname;
    private int count;
    private String s;//to get countId
    private int clicked = 0;

    private List<String> mStringList;
    public RelationAdapter mAdapter;
    private int num;
    final static int CAMERA_RESULT = 0;
    private static int REQUEST_EXTERNAL_STRONGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_details, container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //determine android version and set strict mode for permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //bind widgets
        img_avatar = (ImageView)view.findViewById(R.id.img_avatar);
        btn_addcon = (Button)view.findViewById(R.id.btn_addper);
        edt_name = (EditText)view.findViewById(R.id.edt_name);
        edt_phone = (EditText)view.findViewById(R.id.edt_phone);
        img_avatar.setImageResource(R.mipmap.ic_launcher);
        clicked = 0;
        lst_detail_relation = (ListView)view.findViewById(R.id.lst_detail_relation);//bind list
        lst_detail_relation.setAdapter(mAdapter);//set adapter
        //get permission
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STRONGE);
        }
        updatenames();//update list
        //list item click listener
        lst_detail_relation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.isselected.clear();//clear relation selection
                //determine parent activity to reset falg
                if (getActivity().getClass().equals(MainActivity.class)) {
                    MainActivity mainac = (MainActivity) getActivity();
                    mainac.setflag(0);
                } else  if (getActivity().getClass().equals(ContactDetails.class)){
                    ContactDetails cond = (ContactDetails) getActivity();
                    cond.setflag(0);
                }
                //determine orientation to start activity or show fragment
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//show profile fragment when landscape
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("passname",mAdapter.mStringList.get(position));
                    //Toast.makeText(getApplicationContext(),bundle.getString("passname").toString(),Toast.LENGTH_SHORT).show();
                    ContactProfileFragment contactprofilefragment = new ContactProfileFragment();
                    contactprofilefragment.setArguments(bundle);//pass chosen name
                    getFragmentManager().beginTransaction().replace(R.id.land_othercontainer, contactprofilefragment).commitAllowingStateLoss();//replace detail fragment
                }
                else//under portrait
                {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ContactProfile.class);//start contact profile avtivity
                    intent.putExtra("passname", mAdapter.mStringList.get(position));//pass parameter: name
                    startActivity(intent);
                }
            }
        });
        //avatar click listener
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked == 0) //the very first click ask for permission
                {
                    if (Build.VERSION.SDK_INT >= 23) {
                        int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 222);
                            return;
                        }
                       else //clear relation selection according to parent activity
                            {
                            mAdapter.isselected.clear();
                            if (getActivity().getClass().equals(MainActivity.class))
                            {
                                MainActivity mainac = (MainActivity) getActivity();
                                mainac.setflag(0);
                            }
                            else
                            {
                                ContactDetails cond = (ContactDetails) getActivity();
                                cond.setflag(0);
                            }
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//call system camera activity
                            startActivityForResult(intent, CAMERA_RESULT);//start camera
                        }
                    }
                    else
                        {
                        mAdapter.isselected.clear();
                        if (getActivity().getClass().equals(MainActivity.class))
                        {
                            MainActivity mainac = (MainActivity) getActivity();
                            mainac.setflag(0);
                        }
                        else
                            {
                            ContactDetails cond = (ContactDetails) getActivity();
                            cond.setflag(0);
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//call system camera activity
                        startActivityForResult(intent, CAMERA_RESULT);
                    }
                    clicked = 1;
                }
                else
                {
                    mAdapter.isselected.clear();
                    if (getActivity().getClass().equals(MainActivity.class)) {
                        MainActivity mainac = (MainActivity) getActivity();
                        mainac.setflag(0);
                    } else {
                        ContactDetails cond = (ContactDetails) getActivity();
                        cond.setflag(0);
                    }
                    //show image taken as  avatar
                    Bitmap bmp=((BitmapDrawable)img_avatar.getDrawable()).getBitmap();
                    Intent intent=new Intent(getActivity().getApplicationContext(),FullScreenImage.class);
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte [] bitmapByte =baos.toByteArray();
                    intent.putExtra("bitmap", bitmapByte);//save image
                    startActivity(intent);
                }
            }
        });


        btn_addcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sharedpreferences
                SharedPreferences prefs = getActivity().getSharedPreferences("namelst",MODE_PRIVATE | MODE_APPEND);//open namelst to store countId - name pair and name - count pairs//**********************
                SharedPreferences.Editor prefsEditor = prefs.edit();
                count = prefs.getInt("count", defaultValue);//get latest count
                s = "name" + String.valueOf(count);//set countID
                prefsEditor.putString(s, edt_name.getText().toString());//store countID - name pair
                prefsEditor.putString(edt_name.getText().toString(), String.valueOf(count));//store name - count pair

                prefsEditor.remove("count");//update count: delete
                count++;
                prefsEditor.putInt("count",count);//update new count
                prefsEditor.apply();
                //   Toast.makeText(getActivity().getApplicationContext(), edt_name.getText().toString(), Toast.LENGTH_SHORT).show();
                //        Toast.makeText(getContext(), "count "+count, Toast.LENGTH_SHORT).show();
                SharedPreferences prefs1 = getActivity().getSharedPreferences("phonelst",MODE_PRIVATE | MODE_APPEND);//open phonelst to store name - phone pairs
                SharedPreferences.Editor prefsEditor1 = prefs1.edit();
                prefsEditor1.putString(edt_name.getText().toString(), edt_phone.getText().toString());//store name - phone pair
                prefsEditor1.apply();

                SharedPreferences prefs2 =getActivity().getSharedPreferences("relationlst",MODE_PRIVATE | MODE_APPEND);//open relationlst to store name - relation pairs
                SharedPreferences.Editor prefsEditor2 = prefs2.edit();
                Set<String> values = new HashSet<String>();
                for (int i = 0; i < mAdapter.mStringList.size();i++)
                {
                    if (!mAdapter.mStringList.get(i).isEmpty() && mAdapter.isselected.containsKey(mAdapter.mStringList.get(i).toString()))
                    {
                        values.add(mAdapter.mStringList.get(i).toString());
                        SharedPreferences prefs3 = getActivity().getSharedPreferences("relationlst",MODE_PRIVATE | MODE_APPEND);//open relationlst to store name - relation pairs
                        SharedPreferences.Editor prefsEditor3 = prefs3.edit();
                        Set<String> defValues = new HashSet<>();
                        Set<String> opposite = prefs3.getStringSet(mAdapter.mStringList.get(i).toString(), defValues);
                        opposite.add(edt_name.getText().toString());
                        prefsEditor3.putStringSet(mAdapter.mStringList.get(i).toString(),opposite);
                        prefsEditor3.apply();
                        //         Toast.makeText(getContext(),mAdapter.mStringList.get(i).toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                prefsEditor2.putStringSet(edt_name.getText().toString(),values);
                prefsEditor2.apply();
                if (clicked != 0)
                {
                    bitname = edt_name.getText().toString()+".png";
                    try {
                        saveBitmap(bitmap,bitname);
                    }
                    catch (IOException e)
                    {
                        //           Toast.makeText(getContext(),"Write bitmap failed!",Toast.LENGTH_SHORT).show();
                    }
                    clicked = 0;
                }
                else
                {
                    img_avatar.setImageResource(ic_launcher);//set default avatar
                }
              if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//landscape
                {
                    android.app.FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                    fragmentTransaction.hide(getActivity().getFragmentManager().findFragmentById(R.id.land_othercontainer));//return main, hide contact detail
                    MainActivityFragment mf = (MainActivityFragment) getActivity().getFragmentManager().findFragmentById(R.id.fl_mainactivity_container);//update main list
                    mf.updatenames();//update list
                    fragmentTransaction.commit();//commit update
                }
                else//portrait
                {
                    getActivity().finish();//finish contact detail activity
                    SharedPreferences prefs3 = getActivity().getSharedPreferences("updatepor",MODE_PRIVATE | MODE_APPEND);//open namelst to store countId - name pair and name - count pairs//**********************
                    SharedPreferences.Editor prefsEditor3 = prefs3.edit();
                    prefsEditor3.remove("flag");
                    prefsEditor3.putInt("flag",1);//set flag to notify mainactivity fragment to update list
                    prefsEditor3.apply();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode==RESULT_OK){
            Bundle extras=intent.getExtras();//get extras from intent
            bitmap=(Bitmap) extras.get("data");//get image from extras
            img_avatar.setImageBitmap(bitmap);//show image
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //determine source
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Permission Granted
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//call system camera activity
                    startActivityForResult(intent, CAMERA_RESULT);
                } else
                {
                    // Permission Denied
                    //     Toast.makeText(getContext(), "Please open the Camera Permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (grantResults.length > 0 && requestCode == REQUEST_EXTERNAL_STRONGE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    //save bitmap to file
    private void saveBitmap(Bitmap thisbitmap,String bitName) throws IOException
    {
        File file = new File(Environment.getExternalStorageDirectory(),bitName);
        //    Toast.makeText(getContext(),Environment.getExternalStorageDirectory().toString(),Toast.LENGTH_SHORT).show();
        if(file.exists())//replace if already exists
        {
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(thisbitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
            {
                out.flush();
                out.close();
            }
            //       Toast.makeText(getContext(),"Write bitmap success!",Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            //         Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            //        Toast.makeText(getContext(), "IOExcetion", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatenames()//refresh relation
    {
        SharedPreferences prefs = getActivity().getSharedPreferences("namelst",MODE_PRIVATE | MODE_APPEND);//add name into adapter
        if (prefs.getBoolean("empty", true))//first time open
        {
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putInt("count", 0);//initialize count
            prefsEditor.remove("empty");//reset empty flag
            prefsEditor.putBoolean("empty", false);//set empty flag as flase
            prefsEditor.apply();
        }
        count = prefs.getInt("count", defaultValue);
        mStringList = new ArrayList<>();
        for (num =0; num < count; num ++)
        {
            s = "name" + String.valueOf(num);//get key of countid - name pairs
            if (prefs.getString(s, "defaultValue")!= "defaultValue")// if key exists
            {
                mStringList.add(prefs.getString(s, "defaultValue"));//add value into StringList
                // Toast.makeText(getApplicationContext(), prefs.getString(s, ""), Toast.LENGTH_SHORT).show();
            }
        }
        mAdapter = new RelationAdapter(this.getContext(), mStringList);//fill Adapter
        lst_detail_relation.setAdapter(mAdapter);//set Adapter
    }

    public String returnname()
    {
        return edt_name.getText().toString();
    }

    public void setname(String setname)
    {
        edt_name.setText(setname);
    }

    public String returnphone()
    {
        return edt_phone.getText().toString();
    }

    public void setphone(String setphone)
    {
        edt_phone.setText(setphone);
    }

    public class SerializableMap implements Serializable//to pass map in isselected map in adapter
    {
        private static final long serialVersionUID=124;
        private Map<String,Boolean> map ;
        public Map<String, Boolean> getMap()
        {
            return map;
        }
        public void setMap(Map<String, Boolean> map)
        {
            this.map = map;
        }
    }
    public Bundle returnmap()//put map in bundle to transfer
    {
        final SerializableMap myMap=new SerializableMap();
        myMap.setMap(mAdapter.isselected);//put isselected map into myMap
        Bundle bundle=new Bundle();
        bundle.putSerializable("map", myMap);
        return bundle;
    }
    public void setmap(Bundle bundle)//update isselected map in adapter
    {
        SerializableMap serializableMap = (SerializableMap) bundle.get("map");
        updatenames();
        mAdapter.isselected = serializableMap.getMap();
    }


}
