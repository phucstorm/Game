package com.example.kira.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class QuanLyCauHoi extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.example.kira.finalproject/databases/";
    private static String DB_NAME = "gamedb.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "cauhoi";
    private static final String KEY_ID = "ID_cauhoi";
    private static final String KEY_CAUHOI = "cauhoi";
    private static final String KEY_DAHINH = "dapanhinh";
    private static final String KEY_DACHU = "dapanchu";

    private SQLiteDatabase myDatabase;
    private final Context myContext;
    public QuanLyCauHoi(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
        myContext = context;
    }
    public Cursor layNcauhoi(int socau){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor contro = db.rawQuery("select * from cauhoi order by random() limit "+socau,null);
        return contro;
    }
    public Cursor layScoreCaoNhat(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor contro = db.rawQuery("select * from Score order by score desc limit 1",null);
        return contro;
    }
    public void themScore(int scr){
        SQLiteDatabase db = this.getReadableDatabase();
       db.execSQL("insert into Score (score) values ("+scr+")");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() throws SQLException{
        //open database
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void Close() {
        if(myDatabase != null){
            myDatabase.close();
            super.close();
        }
    }

    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){
            //DB KO TON TAI
        }

        if (checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }
    private void copyDatabase() throws IOException{

        //mo db trong thu muc assets nhu mot input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //duong dan den db se tao
        String outFileName = DB_PATH + DB_NAME;

        //mo db giong nhu mot output stream
        OutputStream myOutPut = new FileOutputStream(outFileName);

        //truyen du lieu tu inputfile san outputfile
        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer)) > 0){
            myOutPut.write(buffer, 0, length);
        }

        //dong
        myOutPut.flush();
        myOutPut.close();
        myInput.close();
    }
    public void createDatabase() throws IOException{
        boolean dbExist = checkDatabase();
        if(dbExist){
            //ko lam gi ca  , db da co roi
        }
        else{
            this.getReadableDatabase();
            try{
                copyDatabase();
            }catch (IOException e){
                throw new Error("Error copying database");
            }
        }
    }


}
