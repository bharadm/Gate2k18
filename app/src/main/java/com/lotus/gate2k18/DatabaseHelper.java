package com.lotus.gate2k18;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Bharadwaj on 29/09/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DBPath="/data/data/com.lotus.gate2k18/databases/";
    private static String DBName="DatabaseF.db";
    private SQLiteDatabase sqLiteDatabase;
    private final Context mycontext;

    public DatabaseHelper(Context context) {
        super(context, DBName, null, 1);
        this.mycontext=context;
    }
    public void createdatabase() throws IOException {
        boolean dbExist=checkDatabase();
        if(dbExist){

        }else{
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB=null;
        try{
            String myPath=DBPath+DBName;
            checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){

        }
        if (checkDB!=null){
                checkDB.close();
        }
        return checkDB!=null;
    }

    private void copyDatabase() throws IOException{
        InputStream myInput=mycontext.getAssets().open(DBName);
        String OutFileName=DBPath+DBName;
        OutputStream myOutput=new FileOutputStream(OutFileName);
        byte[] buffer=new byte[1024];
        int length;
        while((length=myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();myOutput.close();myInput.close();
    }

    public void openDatabase() throws IOException{
        String mypath=DBPath+DBName;
        sqLiteDatabase=SQLiteDatabase.openDatabase(mypath,null,SQLiteDatabase.OPEN_READWRITE);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String Retrieve_data(String s, int column,int row) {
        String query="select * from "+s+" ;";
        String data = null;
        Cursor c=sqLiteDatabase.rawQuery(query,null);
        try {
            if (c.moveToFirst()) {
                if(c.moveToPosition(row)) {
                    data = c.getString(column);
                }
            }
        }catch (CursorIndexOutOfBoundsException e){
            throw new Error(e);
        }
        c.close();
        return data;
    }
    String matt[]=new String[]{"Topic","L","C","N","Pr","Py","M","A","R1","R2","R3"};
    String matt1[]=new String[]{"Topic","L","C","N","Gb","Gr","Py","M","R1","R2","R3"};
    public int Insert_data(String s,int x,int y){
        int row=x/100;int column=x%100;
        int value=0;
        Cursor c=sqLiteDatabase.rawQuery("select * from "+s+";",null);
        if(c.moveToFirst()) {
            if (c.moveToPosition(row)) {
                String topic = c.getString(0);
                value = c.getInt(column);
                if ((value == 0)) {
                    value = 1;
                } else if (value == 1) {
                    value = 2;
                } else if (value==2){
                    value=0;
                }
                if(y==0){
                    sqLiteDatabase.execSQL("update " + s + " set " + matt1[column] + "=" + value + " where Topic='" + topic + "';");
                    c.close();
                }else {
                    sqLiteDatabase.execSQL("update " + s + " set " + matt[column] + "=" + value + " where Topic='" + topic + "';");
                    c.close();
                }
            }
        }
        return value;
    }
}
