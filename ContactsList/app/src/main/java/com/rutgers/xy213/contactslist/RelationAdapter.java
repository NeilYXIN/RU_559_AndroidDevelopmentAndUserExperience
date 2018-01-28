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
 * Created by yx654 on 2017/10/14.
 */

public class RelationAdapter extends BaseAdapter implements Serializable{
    List<String> mStringList;//store contact name
    Context mContext;
    Map<String,Boolean> isselected = new HashMap<>();//store selected checkbox name

    public RelationAdapter(Context context, List<String> stringList)
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
        RelationAdapter.MyViewHolder holder;
        if(convertView==null)
        {
            convertView=View.inflate(mContext,R.layout.listview_item,null);
            holder=new RelationAdapter.MyViewHolder();
            holder.txt_contactname= (TextView) convertView.findViewById(R.id.txt_detail_name);
            holder.check_delete= (CheckBox) convertView.findViewById(R.id.check_delete);
            convertView.setTag(holder);
        }
        else
        {
            holder= (RelationAdapter.MyViewHolder) convertView.getTag();
        }

        holder.txt_contactname.setText(mStringList.get(position));
        holder.check_delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked==true)
                {
                    //put into map if checkbox is selected
                    isselected.put(mStringList.get(position).toString(),true);
                    mStringList.add(0,mStringList.get(position));//junp to first if checked
                    mStringList.remove(position+1);//remove from current place since is put into first
                    refresh();
                }
                else
                {
                    isselected.remove(mStringList.get(position).toString());//remove from map if checkbox is not selected
                    refresh();
                }
            }
        });

        if(isselected !=null && isselected.containsKey(mStringList.get(position).toString()))//set checkbox from isselected
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

    public static class  MyViewHolder
    {
        TextView txt_contactname;
        CheckBox check_delete;
    }
    public void refresh()//refresh adapter when changed
    {
        notifyDataSetChanged();
    }

}
