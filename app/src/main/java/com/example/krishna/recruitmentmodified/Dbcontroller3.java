package com.example.krishna.recruitmentmodified;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;

/*
* this class is meant to handle the roll of students who applied to a particular company
*/
public class Dbcontroller3 extends SQLiteOpenHelper {
    public Dbcontroller3(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,"Test.db2", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db3) {
        db3.execSQL("CREATE TABLE CMP(ID INTEGER PRIMARY KEY AUTOINCREMENT,ROLL TEXT UNIQUE,CMPNY TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db3, int oldVersion, int newVersion) {
        db3.execSQL("DROP TABLE IF EXISTS CMP");
        onCreate(db3);

    }
    public void insert_stu(String roll,String cmpny) {
        ContentValues cv = new ContentValues();
        cv.put("ROLL", roll);
        cv.put("CMPNY", cmpny);


        this.getWritableDatabase().insertOrThrow("CMP", "", cv);
    }


    public void list_allstu(TextView tv){
        Cursor c=this.getReadableDatabase().rawQuery("SELECT * FROM CMP",null);
        tv.setText("");
        while(c.moveToNext())
        {
            tv.append("\n"+c.getString(1)+"  "+c.getString(2));
        }
    }

    public ArrayList<String> listStudsForComp(String compid){
        Cursor c=this.getReadableDatabase().rawQuery("SELECT * FROM CMP WHERE CMPNY='"+compid+"'",null);
        ArrayList<String> studsbyCom = new ArrayList<>();
        while(c.moveToNext())
        {
            studsbyCom.add(c.getString(1));
        }
        return studsbyCom;
    }

}

