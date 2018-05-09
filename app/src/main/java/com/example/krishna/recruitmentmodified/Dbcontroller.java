package com.example.krishna.recruitmentmodified;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/*
 *This class manages the companies registered for placement
 */
public class Dbcontroller extends SQLiteOpenHelper {

    public Dbcontroller(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Test.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CANDIDATES(ID INTEGER PRIMARY KEY AUTOINCREMENT,COMPANYID TEXT UNIQUE,COMPANYNAME TEXT,PASSWORD TEXT, HSCCUT TEXT, SSCCUT TEXT, CGPACUT TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CANDIDATES");
        onCreate(db);
    }

    public void insert_candidates(String userid, String cmpname, String pwd, String hscut, String sscut, String cgpacut) {
        ContentValues cv = new ContentValues();
        cv.put("COMPANYID", userid);
        cv.put("COMPANYNAME", cmpname);
        cv.put("PASSWORD", pwd);
        cv.put("HSCCUT",hscut);
        cv.put("SSCCUT",sscut);
        cv.put("CGPACUT",cgpacut);
        this.getWritableDatabase().insertOrThrow("CANDIDATES", "", cv);
    }

    public int search(String userid, String cmpname, String pwd) {
        int b = 0;
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM CANDIDATES", null);
        while (c.moveToNext()) {
            if (userid.equals(c.getString(1)) && cmpname.equals(c.getString(2)) && pwd.equals(c.getString(3))) {
                b = 1;
            }
        }
        return b;
    }

    public void delete_candidates(String userid) {
        this.getWritableDatabase().delete("CANDIDATES", "COMPANYID='" + userid + "'", null);
    }

    public ArrayList<String> list_candidates() {
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM CANDIDATES", null);
        ArrayList<String> coms = new ArrayList<>();
        while (c.moveToNext()) {
            coms.add(c.getString(2));
        }
        return coms;
    }


    public boolean checkStudForEligibility(ModelStudent student, String comp) {
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM CANDIDATES", null);

        while (c.moveToNext()) {
            if(c.getString(2).equalsIgnoreCase(comp))
            {
                int hsc,ssc, cgpa;
                hsc = Integer.parseInt(c.getString(4));
                ssc = Integer.parseInt(c.getString(5));
                cgpa = Integer.parseInt(c.getString(6));

                if(student.hsc<hsc || student.ssc<ssc || student.cgpa<cgpa)
                    return false;
            }
        }
        return true;
    }
}
