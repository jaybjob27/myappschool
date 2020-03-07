package com.example.jobndirangu.schoolportalms.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jobndirangu.schoolportalms.HomeActivity;
import com.example.jobndirangu.schoolportalms.MainActivity;
import com.example.jobndirangu.schoolportalms.R;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView sports,meetings,drama,timetable;
    private Button LogoutBtn,PostResultsBtn, maintainEventssBtn,feeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn=(Button)findViewById(R.id.admin_logout_btn);
        PostResultsBtn=(Button)findViewById(R.id.post_results_btn);
        maintainEventssBtn=(Button)findViewById(R.id.maintain_events_btn);
        feeBtn=(Button)findViewById(R.id.fees_btn);


        maintainEventssBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminMaintainEventActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
            }
        });


        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
// revisit
        PostResultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               Intent intent = new Intent(AdminCategoryActivity.this,AdminCategoryActivity.class);
               startActivity(intent);
            }
        });
        PostResultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminFeeActivity.class);
                startActivity(intent);
            }
        });

        sports=(ImageView) findViewById(R.id.c_sports);
        meetings=(ImageView) findViewById(R.id.c_meetings);
        drama=(ImageView) findViewById(R.id.c_drama);
        timetable=(ImageView) findViewById(R.id.c_timetable);


        sports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewEventActivity.class);
                intent.putExtra("category","Sports");
                startActivity(intent);
            }
        });

        meetings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewEventActivity.class);
                intent.putExtra("category","Meetings");
                startActivity(intent);
            }
        });

        drama.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewEventActivity.class);
                intent.putExtra("category","drama");
                startActivity(intent);
            }
        });

        timetable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewEventActivity.class);
                intent.putExtra("category","Timetable");
                startActivity(intent);
            }
        });

    }
}
