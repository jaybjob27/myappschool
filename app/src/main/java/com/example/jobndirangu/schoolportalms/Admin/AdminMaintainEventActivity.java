package com.example.jobndirangu.schoolportalms.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jobndirangu.schoolportalms.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainEventActivity extends AppCompatActivity {
    private Button applyChangesBtn,deleteBtn;
    private EditText evntname,evntdate,evntdescription;
    private ImageView imageView;

    private String eventID="";
    private DatabaseReference eventRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_event);

        eventID= getIntent().getStringExtra("pid");
        eventRef=FirebaseDatabase.getInstance().getReference().child("Events").child(eventID);

        applyChangesBtn=(Button)findViewById(R.id.apply_changes_btn);
        evntname=(EditText) findViewById(R.id.event_name_maintain);
        evntdate=(EditText) findViewById(R.id.event_date_maintain);
        evntdescription=(EditText) findViewById(R.id.event_description_maintain);
        imageView=(ImageView)findViewById(R.id.event_image_maintain);
        deleteBtn=(Button)findViewById(R.id.delete_event_btn);

        DisplaySpecificProductsInfo();

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                applyChanges();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteThisEvent();
            }
        });
    }


    private void deleteThisEvent()
    {
        eventRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Intent intent = new Intent(AdminMaintainEventActivity.this,AdminCategoryActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminMaintainEventActivity.this, "The Product is deleted successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void applyChanges()
    {
        String evntName = evntname.getText().toString();
        String evntDate = evntdate.getText().toString();
        String evntDescription = evntdescription.getText().toString();

        if (evntName.equals(""))
        {
            Toast.makeText(this, "Write event name..", Toast.LENGTH_SHORT).show();
        }
        else if (evntDate.equals(""))
        {
            Toast.makeText(this, "Write event date..", Toast.LENGTH_SHORT).show();
        }
        else if (evntDescription.equals(""))
        {
            Toast.makeText(this, "Write event description..", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid",eventID);
            productMap.put("description",evntDescription);
            productMap.put("date",evntdate);
            productMap.put("evntname",evntName);

            eventRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainEventActivity.this, "Changes applied successfully.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AdminMaintainEventActivity.this,AdminCategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    private void DisplaySpecificProductsInfo()
    {
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String evntName =  dataSnapshot.child("evntname").getValue().toString();
                    String evntDate =  dataSnapshot.child("evntdate").getValue().toString();
                    String evntDescription =  dataSnapshot.child("evntdescription").getValue().toString();
                    String evntImage =  dataSnapshot.child("evntimage").getValue().toString();

                    evntname.setText(evntName);
                    evntdate.setText(evntDate);
                    evntdescription.setText(evntDescription);
                    Picasso.get().load(evntImage).into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }


}
