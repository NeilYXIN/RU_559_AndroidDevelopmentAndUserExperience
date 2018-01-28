package com.rutgers.xy213.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;
import static com.rutgers.xy213.todolist.R.id.boxdelete;
import static com.rutgers.xy213.todolist.R.id.inputdescription;
import static com.rutgers.xy213.todolist.R.id.inputtitile;

public class  MainActivity extends AppCompatActivity {
    private Context mContext;//write file
    private EditText inputtitle;
    private EditText inputdescription;
    private ListView tasklist;
    private TextView generaltitle;
    private  Button btnadd;

    private MyDatabaseHelper myDatabaseHelper;//declare DatabaseHelper
    private SQLiteDatabase dbW;
    private SQLiteDatabase dbR;
    private MySimpleCursorAdapter mSimpleCursorAdapter;//declare customized CursorAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize widgets
        mContext = getApplicationContext();
        myDatabaseHelper = new MyDatabaseHelper(this,"task.db",null,1);
        dbW = myDatabaseHelper.getWritableDatabase();
        dbR = myDatabaseHelper.getReadableDatabase();
        generaltitle = (TextView) findViewById(R.id.General_Titile);
        inputtitle = (EditText) findViewById(R.id.inputtitile);//input Title
        inputdescription = (EditText) findViewById(R.id.inputdescription);//input Description
        tasklist = (ListView) findViewById(R.id.tasklist);
        btnadd = (Button) findViewById(R.id.btnadd);

        //set cursor adapter
        mSimpleCursorAdapter = new MySimpleCursorAdapter(MainActivity.this, R.layout.listview_item, null, new String[]{"title", "description"}, new int[]{R.id.title, R.id.description},
               R.id.boxdelete ,"_id",
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        tasklist.setAdapter(mSimpleCursorAdapter);//set adapter for listview

        refreshListView();//initialize to show listview

        tasklist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() //longclick to delete
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id)
            {
                new AlertDialog.Builder(MainActivity.this)//create new dialog
                        .setTitle("Alert")//set dialog title
                        .setMessage("Delete this item?")//set dialog message
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() //set yes button
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                deleteData(position);//when click yes,delete data
                            }
                        })
                        .setNegativeButton("Cancel",null)//set no button
                        .show();
                return true;
            }
        });
    }

    public void refreshListView() {
        Cursor mCursor = dbW.query("task", null, null, null, null, null, null);//query database
        mSimpleCursorAdapter.changeCursor(mCursor);//refresh cursor adapter
    }

    public void insertData(){
        ContentValues mContentValues = new ContentValues();//put value into content
        mContentValues.put("title", inputtitle.getText().toString());
        mContentValues.put("description", inputdescription.getText().toString());
        dbW.insert("task", null, mContentValues);//insert into database
        Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_SHORT).show();//toast success add message
        refreshListView();//refresh after added
        inputtitle.setText("");//clear edittext for next input
        inputdescription.setText("");//clear edittext for next input

    }

    private void deleteData(int position) {
        Cursor mCursor = mSimpleCursorAdapter.getCursor();//create new Cursor
        mCursor.moveToPosition(position);//move to the item
        int itemId = mCursor.getInt(mCursor.getColumnIndex("_id"));//get item id
        dbW.delete("task", "_id=?", new String[]{itemId + ""});//delete from database
        refreshListView();//reload adapter after deleted
    }

    public void btnOnClick(View v)//click add to show in listview, write to file and store in database
    {
        String tasktitle = inputtitle.getText().toString();//prepare data
        String taskdescription = inputdescription.getText().toString();//prepare data
        FileHelper fHelper = new FileHelper(mContext);//create  filehelper object
        try {
            fHelper.save(tasktitle, taskdescription);//call weite function
            Toast.makeText(getApplicationContext(), "Write Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Write Failed", Toast.LENGTH_SHORT).show();
        }
        insertData();//add into ListView
    }



    public class MySimpleCursorAdapter extends SimpleCursorAdapter
    {
        private ArrayList<Integer> selection = new ArrayList<Integer>();//store seletcet item id
        private int mCheckBoxId = 0;//R.id.boxdelete
        private String mIdColumn;//_id in database

        public MySimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int checkBoxId, String idColumn, int flags)//checkboxid & idcolumn added
        {
            super(context, layout, c, from, to, flags);//extend from super class
            mCheckBoxId = checkBoxId;//set mCheckBoxId
            mIdColumn = idColumn;//set mIdColumn
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)//get current item position
        {
            View view = super.getView(position, convertView, parent);
            CheckBox boxdelete = (CheckBox)view.findViewById(mCheckBoxId);//bind chechbox
            boxdelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Cursor cursor = getCursor();//create new cursor
                    cursor.moveToPosition(position);//set cursor to current item
                    int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(mIdColumn));//get item _id
                    int index = selection.indexOf(rowId);//get
                    if (index != -1) {
                        selection.remove(index);//if not selected, remove from arraylist
                    } else {
                        selection.add(rowId);//if selected, add to arraylist
                        deleteData(position);//delete directly
                    }
                }
            });
            Cursor cursor = getCursor();//create cursor
            cursor.moveToPosition(position);//set cursor to current place
            int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(mIdColumn));//get item _id
            if (selection.indexOf(rowId)!= -1)
            {
                boxdelete.setChecked(true);//set selected item true
            } else
                {
                boxdelete.setChecked(false);//set unselected item false
            }
            return view;
        }

    }

}
