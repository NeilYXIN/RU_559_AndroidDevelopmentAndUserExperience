package com.rutgers.xy213.contactslist;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.defaultValue;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {
    private Button btn_add;
    private Button btn_delete;
    private ListView lst_contacts;
    private List<String> mStringList;
    public MyAdapter mAdapter;
    private int count;
    private int num;
    private String s;
    public Bundle bundle;
    public int flag =1;

    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //bind widgets in fragment
        btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_delete = (Button) view.findViewById(R.id.btn_delete);
        lst_contacts = (ListView) view.findViewById(R.id.lst_contacts);

        //onclick item in the list, go to contact profile
        lst_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.isselected.clear();//clear saved checkbox in contact detail fragment
                if (getActivity().getClass().equals(MainActivity.class)) //parent activity is mainactivity
                {
                    MainActivity mainac = (MainActivity) getActivity();
                    mainac.setflag(0);//set mainactivity flag to 0
                } else  if (getActivity().getClass().equals(ContactDetails.class))//parent activity is contact detail
                {
                    ContactDetails cond = (ContactDetails) getActivity();
                    cond.setflag(0);//set contactdetail flag to 0
                }


                flag = 1;//set flag as 1 to store checkbox in relationship
                String passname = mAdapter.mStringList.get(position);//get item name by position
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//if landscape
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("passname",passname);//pass name to profile
                    //Toast.makeText(getApplicationContext(),bundle.getString("passname").toString(),Toast.LENGTH_SHORT).show();
                    ContactProfileFragment contactprofilefragment = new ContactProfileFragment();
                    contactprofilefragment.setArguments(bundle);//pass name to profile from fragment to fragment
                    getFragmentManager().beginTransaction().replace(R.id.land_othercontainer, contactprofilefragment).commitAllowingStateLoss();//show profile fragment
                }
                else//when under portrait view
                {
                    Intent intent = new Intent(getActivity(), ContactProfile.class);//start contact profile avtivity
                    intent.putExtra("passname", passname);//pass parameter name
                    startActivity(intent);
                }

            }
        });


        //OnClick add button
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.isselected.clear();//clear stored selection information
                if (getActivity().getClass().equals(MainActivity.class)) //when parent is main
                {
                    MainActivity mainac = (MainActivity) getActivity();
                    mainac.setflag(0);//reset parent'sflag
                }
                else  if (getActivity().getClass().equals(ContactDetails.class))//when parent is contact detail
                {
                    ContactDetails cond = (ContactDetails) getActivity();
                    cond.setflag(0);//reset parent's flag
                }

                flag = 1;//set flag as 1 to store
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//when under landscape
                {
                    ContactDetailsFragment contactdetailfragment = new ContactDetailsFragment();
                    getFragmentManager().beginTransaction().replace(R.id.land_othercontainer, contactdetailfragment).commitAllowingStateLoss();//show contact detail fragment
                }
                else//when under portrait
                {
                    Intent intent = new Intent(getActivity(), ContactDetails.class);//start contact details activity
                    startActivity(intent);
                }
                updatenames();//update information

            }
        });

        //OnClick delete button
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<Integer, Boolean> selection = mAdapter.isselected;//copy selection info from Adapter
                for (int j = 0; j < count; j++) {
                    if (selection.get(j) != null)
                    {
                        if (selection.get(j) == true) {
                            String currntname = mAdapter.mStringList.get(j);//get name from adapter mStringList
                            Toast.makeText(getContext(), currntname, Toast.LENGTH_SHORT).show();
                            SharedPreferences prefs = getActivity().getSharedPreferences("namelst", MODE_PRIVATE | MODE_APPEND);//delete namelst, countID and name
                            SharedPreferences.Editor prefsEditor = prefs.edit();
                            String count = prefs.getString(currntname,"");
                            prefsEditor.remove("name"+count);//remove countid - name pair
                            prefsEditor.remove(currntname);//remove name -count pair
                            prefsEditor.apply();

                            SharedPreferences prefs1 = getActivity().getSharedPreferences("phonelst", MODE_PRIVATE | MODE_APPEND);//delete phonelst
                            SharedPreferences.Editor prefsEditor1 = prefs1.edit();
                            prefsEditor1.remove(currntname);//remove name - phone pair
                            prefsEditor1.apply();

                            SharedPreferences prefs2 = getActivity().getSharedPreferences("relationlst", MODE_PRIVATE | MODE_APPEND);//delete relationlst
                            SharedPreferences.Editor prefsEditor2 = prefs2.edit();
                            prefsEditor2.remove(currntname);//remove name - phone pair
                            prefsEditor2.apply();

                            String temp = "/"+currntname+".png";//get picture name
                            File file = new File(Environment.getExternalStorageDirectory()+temp);//get picture file path
                            //delete picture file
                            if(file.isFile()){
                                file.delete();
                            }
                            if (file.exists())
                            {
                                Toast.makeText(getContext(),"file delete fail",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(),"file delete success",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                updatenames();
            }
        });
        updatenames();
    }

    public void onResume()
    {
        super.onResume();

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (getActivity().getClass().equals(MainActivity.class))
            {
                SharedPreferences prefs3 = getActivity().getSharedPreferences("updatepor",MODE_PRIVATE | MODE_APPEND);//get flag info to see whether need update information
                int temp = prefs3.getInt("flag",0);//get flag
                if (temp == 1)//in portrait mode and returned from detail activity, need to update list
                {
                    updatenames();//update list
                    SharedPreferences.Editor prefsEditor3 = prefs3.edit();
                    prefsEditor3.remove("flag");
                    prefsEditor3.putInt("flag",0);//reset flag
                    prefsEditor3.apply();
                }
            }
        }
    }

    public void updatenames()//refresh list
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
        for (num =0; num < count; num ++)//for every item in list
        {
            s = "name" + String.valueOf(num);//get key of countid - name pairs
            if (prefs.getString(s, "defaultValue")!= "defaultValue")// if key exists
            {
                mStringList.add(prefs.getString(s, "defaultValue"));//add value into StringList
                // Toast.makeText(getApplicationContext(), prefs.getString(s, ""), Toast.LENGTH_SHORT).show();
            }
        }
        mAdapter = new MyAdapter(this.getContext(), mStringList);//fill Adapter
        lst_contacts.setAdapter(mAdapter);//set Adapter
    }

    public class SerializableMap1 implements Serializable//define this class to pass hashmap in adapter to restore checkbox selection information
    {
        private static final long serialVersionUID=125;
        private Map<Integer,Boolean> map;
        public Map<Integer, Boolean> getMap()
        {
            return map;
        }
        public void setMap(Map<Integer, Boolean> map)
        {
            this.map = map;
        }
    }
    public Bundle returnmap1()//call from outside to get map bundle
    {
        final SerializableMap1 myMap=new SerializableMap1();
        myMap.setMap(mAdapter.isselected);//put isselected map into muMap
        Bundle bundle=new Bundle();
        bundle.putSerializable("map1", myMap);
        return bundle;
    }
    public void setmap1(Bundle bundle)//call from outside to update isselected map in adapter
    {
        SerializableMap1 serializableMap = (SerializableMap1) bundle.get("map1");
        updatenames();
        mAdapter.isselected = serializableMap.getMap();
    }





}
