package com.example.krishna.recruitmentmodified;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Stu_Login extends AppCompatActivity {
    Spinner cmpname;
    Button apply,dele;
    EditText uniroll;
    Dbcontroller1 dbcon1;
    Dbcontroller3 dbcon3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu__login);

        final ArrayList<CheckBox> selectedCompanies = new ArrayList<>();

        dbcon1 = new Dbcontroller1(this, "", null, 1);
        dbcon3 = new Dbcontroller3(this, "", null, 1);
       apply=(Button)findViewById(R.id.bt_apply);
       dele=(Button)findViewById(R.id.bt_studel);


       Dbcontroller dbcomp = new Dbcontroller(this, "", null, 1);
       final ArrayList<String> companies = dbcomp.list_candidates();


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String coms = "";
                int counter = 0;

                String Roll=getIntent().getExtras().getString("roll");
                ((TextView)findViewById(R.id.studname)).setText("Hello, "+getIntent().getExtras().getString("name")+"!");

                for(int i=0;i<companies.size();i++)
                {
                    if(selectedCompanies.get(i).isChecked())
                    {
                        coms += " "+companies.get(i);
                        counter++;
                        dbcon3.insert_stu(Roll,companies.get(i));
                    }
                }

                if(counter == 0)
                {
                    Toast.makeText(Stu_Login.this, "Please select a company to apply", Toast.LENGTH_SHORT).show();
                }

            }
        });



        dele.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String Roll=getIntent().getExtras().getString("roll");
               dbcon1.delete_stu(Roll);
               Toast.makeText(Stu_Login.this,"Student Deleted Successfully",Toast.LENGTH_LONG).show();
               finish();
           }
       });

        LinearLayout lin = findViewById(R.id.companylist);
        for(String c: companies)
        {
            CheckBox cb = new CheckBox(this);
            selectedCompanies.add(cb);
            cb.setText(c);
            lin.addView(cb);
        }

    }
}


