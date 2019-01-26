package com.example.sayantan.life_saver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Slip extends AppCompatActivity {


    private static final String[] bloodgroup = {"Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    Spinner bloodSpinner;
    String naem, loc, phno;
    Button submit;
    private EditText name,location,ph;
    String selectedbloodGroup, unit;
    String n;

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference, getUserData, mdatabase, db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip);

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        location=findViewById(R.id.location);


        bloodSpinner = (Spinner) findViewById(R.id.bloodgroup);
        ArrayAdapter<String> adapterBlood = new ArrayAdapter<String>(Slip.this, android.R.layout.simple_spinner_dropdown_item, bloodgroup);
        bloodSpinner.setAdapter(adapterBlood);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());

        db = FirebaseDatabase.getInstance().getReference().child("Slip").child(mAuth.getUid());

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    naem = dataSnapshot.child("userName").getValue().toString();
                    loc  = dataSnapshot.child("userCity").getValue().toString();
                    phno = dataSnapshot.child("userPh").getValue().toString();


                    name.setText(naem);
                    location.setText(loc);



                }

                Toast.makeText(getApplicationContext(),"Name :- " + naem,Toast.LENGTH_LONG).show();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbloodGroup = bloodSpinner.getSelectedItem().toString();
                unit = name.getText().toString();

                check(selectedbloodGroup, unit,(location.getText().toString()).trim(),phno);
            }
        });


    }


    public void check(String type, String unit,String loc, String pn) {

        if (TextUtils.isEmpty(unit)) {
            Toast.makeText(Slip.this, "Unit Field is Empty", Toast.LENGTH_LONG).show();
        } else if (type.equals("Blood Group")) {
            Toast.makeText(Slip.this, "Blood Group Field is Empty", Toast.LENGTH_LONG).show();
        } else {
            upload(type, unit,loc, pn);
        }

    }

    private void upload(String type, String unit, String loc, String pn) {
        String g=type;
        String u=unit;
        String l=loc;
        String ph = pn;
        String time=new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss").format(new Date());

        Map slipmap = new HashMap();
        slipmap.put("type",g);
        slipmap.put("unit",u);
        slipmap.put("loc",l);
        slipmap.put("phno",ph);

        slipmap.put("timesapp", time);

        DatabaseReference slipdb =  db.push();

        slipdb.setValue(slipmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent startPage = new Intent(Slip.this, MainPage.class);
                    startPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startPage);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error while updating your data", Toast.LENGTH_SHORT).show();

                }
            }

        });
    }




}

