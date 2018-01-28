package com.rutgers.xy213.contactslist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yx654 on 2017/10/11.
 */

public class MyAdapter extends BaseAdapter implements Serializable
{
    List<String> mStringList;//store contact name
    Context mContext;
    Map<Integer,Boolean> isselected = new HashMap<>();//store selected checkbox id

    public MyAdapter(Context context, List<String> stringList)
    {
        mStringList = stringList;
        mContext=context;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        MyViewHolder holder;
        if(convertView==null)
        {
            convertView=View.inflate(mContext,R.layout.listview_item,null);
            holder=new MyViewHolder();
            holder.txt_contactname= (TextView) convertView.findViewById(R.id.txt_detail_name);
            holder.check_delete= (CheckBox) convertView.findViewById(R.id.check_delete);
            convertView.setTag(holder);
        }
        else
        {
            holder= (MyAdapter.MyViewHolder) convertView.getTag();
        }

        holder.txt_contactname.setText(mStringList.get(position));
        holder.check_delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked==true)
                {
                    isselected.put(position,true);//put into map if checkbox is selected
                    refresh();
                }
                else
                {
                    isselected.remove(position);//remove from map if checkbox is not selected
                    refresh();
                }
            }
        });
        if(isselected !=null && isselected.containsKey(position))//set checkbox according to isselected map
        {
            holder.check_delete.setChecked(true);
        }
        else
        {
            holder.check_delete.setChecked(false);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }


    @Override
    public Object getItem(int position)
    {
        return null;
    }

    public static class  MyViewHolder implements  Serializable
    {
        TextView txt_contactname;
        CheckBox check_delete;
    }
    public void refresh() {

        notifyDataSetChanged();
    }
}
