package com.example.jobndirangu.schoolportalms.Admin;

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

import com.example.jobndirangu.schoolportalms.R;
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

public class AdminFeeActivity extends AppCompatActivity
{
    private String TotalFee,FeePaid,FeeBal,FeeId,saveCurrentDate,saveCurrentTime;
    private Button UpdateFeeButton;
    private EditText InputFeeBal,InputTotalFee,InputFeePaid,InputFeeId;
    private DatabaseReference RootRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fee);

        RootRef =FirebaseDatabase.getInstance().getReference().child("fee");

        UpdateFeeButton=(Button)findViewById(R.id.fee_update);
        InputFeeId=(EditText) findViewById(R.id.regno);
        InputTotalFee= (EditText)findViewById(R.id.total_fee);
        InputFeePaid=(EditText)findViewById(R.id.fee_paid);
        InputFeeBal=(EditText)findViewById(R.id.fee_bal);
        loadingBar=new ProgressDialog(this);

        UpdateFeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateFeeData();
            }
        });

    }

    private void ValidateFeeData()
    {
        FeeId=InputFeeId.getText().toString();
        TotalFee=InputTotalFee.getText().toString();
        FeePaid=InputFeePaid.getText().toString();
        FeeBal=InputFeeBal.getText().toString();

        if (FeeId == null)
        {
            Toast.makeText(this,"Fee Number equivalent to student reg no is mandatory...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(TotalFee))
        {
            Toast.makeText(this,"Please write the total fee...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(FeePaid))
        {
            Toast.makeText(this,"Please write paid amount...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(FeeBal))
        {
            Toast.makeText(this,"Please write fee balance...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreFeeInformation();
        }
    }



    private void StoreFeeInformation() {

        loadingBar.setTitle("Update Fee Status");
        loadingBar.setMessage("Dear Admin, please wait as we update fee details...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        SaveFeeInfoToDatabase();
    }
    private void SaveFeeInfoToDatabase()
    {
        HashMap<String, Object> feeMap = new HashMap<>();
        feeMap.put("date",saveCurrentDate);
        feeMap.put("time",saveCurrentTime);
        feeMap.put("feeid",FeeId);
        feeMap.put("totalfee",TotalFee);
        feeMap.put("feepaid",FeePaid);
        feeMap.put("feebal",FeeBal);

        RootRef.child("Feeid").updateChildren(feeMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                            Intent intent=new Intent(AdminFeeActivity.this,AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminFeeActivity.this, "Fee status is added successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();

                            String message = task.getException().toString();
                            Toast.makeText(AdminFeeActivity.this,"Error!: " +message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
