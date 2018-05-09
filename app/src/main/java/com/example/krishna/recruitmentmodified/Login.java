package com.example.krishna.recruitmentmodified;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    Button back,del;
    Dbcontroller dbcon;
    Dbcontroller3 dbcon3;
    Dbcontroller1 dbcon1;

    EditText et_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbcon=new Dbcontroller(this,"",null,1);
        dbcon3 = new Dbcontroller3(this, "", null, 1);
        dbcon1 = new Dbcontroller1(this, "", null, 1);
        back=(Button)findViewById(R.id.bt_back);
        del=(Button)findViewById(R.id.bt_del);

        LinearLayout lin = findViewById(R.id.listofstuds);

        ArrayList<String> comstuds = dbcon3.listStudsForComp(getIntent().getExtras().getString("name"));
        if(comstuds.size()==0)
        {
            TextView tv = new TextView(this);
            tv.setText("No registered candidates yet");
            lin.addView(tv);
        }
        else
        {
            int counter = 1;
            for(String s: comstuds)
            {
                TextView tv = new TextView(this);
                ModelStudent student = dbcon1.search_stu(s);
                if(dbcon.checkStudForEligibility(student, getIntent().getExtras().getString("name")))
                {
                    String text = " "+counter+".  "+student.name+"   CGPA: "+student.cgpa+" HSC: "+student.hsc+" SSC: "+student.ssc;
                    tv.setText(text);
                    counter++;
                    lin.addView(tv);
                }
            }
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=getIntent().getExtras().getString("id");
                dbcon.delete_candidates(id);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
