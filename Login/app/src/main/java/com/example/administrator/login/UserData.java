package com.example.administrator.login;

/**
 * Created by Administrator on 10/17/2017.
 */

public class UserData {
    int _id;
    String _account;
    String _password;

    public UserData(){}

    public UserData(int id, String account, String password){
        this._id=id;
        this._account=account;
        this._password=password;
    }
    public UserData(String account, String password){
        this._account=account;
        this._password=password;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._account;
    }

    // setting name
    public void setName(String account){
        this._account = account;
    }

    // getting password
    public String getPassword(){
        return this._password;
    }

    // setting phone number
    public void setPhoneNumber(String password){
        this._password = password;
    }





}
