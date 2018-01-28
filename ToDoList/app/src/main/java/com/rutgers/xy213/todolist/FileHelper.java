package com.rutgers.xy213.todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yx654 on 2017/9/24.
 */

public class FileHelper {
    private Context mContext;
    public FileHelper()
    {
    }
    public FileHelper(Context mContext)
    {
        super();
        this.mContext = mContext;
    }


    public void save(String filename, String filecontent) throws Exception{
        FileOutputStream output = mContext.openFileOutput(filename,Context.MODE_PRIVATE);//output stream
        output.write(filecontent.getBytes());//write into files
        output.close();//close output
    }

    public String read(String filename)throws IOException{
        FileInputStream input = mContext.openFileInput(filename);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder("");//create stringbuilder
        int len = 0;
        while ((len = input.read(temp))>0){
            sb.append(new String(temp,0,len));//add to stringbuilder sb
        }
        input.close();//close input stream
        return sb.toString();//turn into string
    }

}
