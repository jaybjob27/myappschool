package com.example.jobndirangu.schoolportalms;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.example.jobndirangu.schoolportalms.Model.Fees;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeeActivity extends AppCompatActivity {

    private TextInputEditText amountInput;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference feesRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        firebaseDatabase = FirebaseDatabase.getInstance();
        feesRef = firebaseDatabase.getReference("fees");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();

        AppCompatButton saveButton = findViewById(R.id.save_button);
        amountInput = findViewById(R.id.amount_input);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        String amount = Objects.requireNonNull(amountInput.getText()).toString();

        Fees fees = new Fees("iuewhdiw", "200", amount, "180", userId);
        feesRef.push().setValue(fees).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FeeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getMyFees(){
        Query queryRef = feesRef.orderByChild("userId").equalTo(userId);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Fees> myFees = new ArrayList<>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Fees item = snapshot.getValue(Fees.class);
                    myFees.add(item);
                }

                //TODO update list with myFees list
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
