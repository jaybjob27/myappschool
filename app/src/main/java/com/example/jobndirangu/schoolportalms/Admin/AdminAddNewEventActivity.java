package com.example.jobndirangu.schoolportalms.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jobndirangu.schoolportalms.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class AdminAddNewEventActivity extends AppCompatActivity {
    private String CategoryName,EvntDescription,EvntDate,Evntname,saveCurrentDate,saveCurrentTime;
    private Button AddNewEventButton;
    private EditText InputEventName,InputEventDescription,InputEventDate;
    private ImageView InputEventImage;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String eventRandomKey,downloadImageUrl;
    private StorageReference EventImageRef;
    private DatabaseReference EventRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_event);

        CategoryName=getIntent().getExtras().get("category").toString();
        EventImageRef=FirebaseStorage.getInstance().getReference().child("Event Image");
        EventRef =FirebaseDatabase.getInstance().getReference().child("Events");

        AddNewEventButton=(Button)findViewById(R.id.add_new_event_btn);
        InputEventImage=(ImageView)findViewById(R.id.select_event_image);
        InputEventName= (EditText)findViewById(R.id.event_name);
        InputEventDescription=(EditText)findViewById(R.id.event_description);
        InputEventDate=(EditText)findViewById(R.id.event_date);
        loadingBar=new ProgressDialog(this);

        InputEventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        AddNewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateEventData();
            }
        });

    }


    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            InputEventImage.setImageURI(ImageUri);

        }
    }

    private void ValidateEventData()
    {
        EvntDescription=InputEventDescription.getText().toString();
        EvntDate=InputEventDate.getText().toString();
        Evntname=InputEventName.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this,"Event image is mandatory...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(EvntDescription))
        {
            Toast.makeText(this,"Please write event description...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(EvntDate))
        {
            Toast.makeText(this,"Please write event date...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Evntname))
        {
            Toast.makeText(this,"Please write event name...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }



    private void StoreProductInformation() {

        loadingBar.setTitle("Add New Event");
        loadingBar.setMessage("Dear Admin, please wait as we are adding new event...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        eventRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = EventImageRef.child(ImageUri.getLastPathSegment()+eventRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddNewEventActivity.this,"Error" +message,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewEventActivity.this,"Event image uploaded successfully",Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl=task.getResult().toString();

                            Toast.makeText(AdminAddNewEventActivity.this,"Got the image Url successfully...",Toast.LENGTH_SHORT).show();

                            SaveEventInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveEventInfoToDatabase()
    {
        HashMap<String, Object> eventMap = new HashMap<>();
        eventMap.put("pid",eventRandomKey);
        eventMap.put("date",saveCurrentDate);
        eventMap.put("time",saveCurrentTime);
        eventMap.put("evntdescription",EvntDescription);
        eventMap.put("image",downloadImageUrl);
        eventMap.put("category",CategoryName);
        eventMap.put("evntdate",EvntDate);
        eventMap.put("evntname",Evntname);

        EventRef.child(eventRandomKey).updateChildren(eventMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                            Intent intent=new Intent(AdminAddNewEventActivity.this,AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewEventActivity.this, "Event is added successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();

                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewEventActivity.this,"Error!: " +message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
