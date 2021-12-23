package com.example.administrator.login;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button bt_ADD;
    private Button bt_LOGIN;
    private EditText username;
    private EditText password;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= new DatabaseHandler(this);
        bt_ADD=(Button)findViewById(R.id.bt_ADD);
        bt_LOGIN=(Button)findViewById(R.id.bt_LOGIN);
        username=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);

    }

    public void registerClick(View view){
        username=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        UserData newUser=new UserData(username.getText().toString(),password.getText().toString());
        Log.d("add",(username.getText().toString()+" "+password.getText().toString()));

        db.addUser(newUser);
    }
    public void loginClick (View view){
        username=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        if (db.getUserCount()==0){
            Toast toast=Toast.makeText(getApplicationContext(),"No data yet!",Toast.LENGTH_LONG);
            toast.show();
        } else {
            if (username.getText().toString()!=null && db.login(username.getText().toString(),password.getText().toString())){
                Toast toast=Toast.makeText(getApplicationContext(),"Welcome Back!",Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Toast toast=Toast.makeText(getApplicationContext(),"Password is not matched!",Toast.LENGTH_LONG);
                toast.show();
            }
        }





    }




}
