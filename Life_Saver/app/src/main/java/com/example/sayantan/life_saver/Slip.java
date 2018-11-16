package com.example.sayantan.life_saver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Slip extends AppCompatActivity {


    private static final String[] bloodgroup = {"Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    Spinner bloodSpinner;
    Button submit;
    TextView units,loc;
    String selectedbloodGroup, unit;

    String n;

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference, getUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip);

        mAuth = FirebaseAuth.getInstance();
        units = findViewById(R.id.unit);
        loc=findViewById(R.id.location);


        bloodSpinner = (Spinner) findViewById(R.id.bloodgroup);
        ArrayAdapter<String> adapterBlood = new ArrayAdapter<String>(Slip.this, android.R.layout.simple_spinner_dropdown_item, bloodgroup);
        bloodSpinner.setAdapter(adapterBlood);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbloodGroup = bloodSpinner.getSelectedItem().toString();
                unit = units.getText().toString();

                check(selectedbloodGroup, unit,(loc.getText().toString()).trim());
            }
        });

    }


    public void check(String type, String unit,String loc) {

        if (TextUtils.isEmpty(unit)) {
            Toast.makeText(Slip.this, "Unit Field is Empty", Toast.LENGTH_LONG).show();
        } else if (type.equals("Blood Group")) {
            Toast.makeText(Slip.this, "Blood Group Field is Empty", Toast.LENGTH_LONG).show();
        } else {
            upload(type, unit,loc);
        }

    }

    public void upload(final String type, String unit,String loc) {

        String g=type+" "+unit+" "+loc;

        String cur_uid = mAuth.getCurrentUser().getUid();
        final String[] u = new String[1];

        String time=new SimpleDateFormat("yyyy+MM+dd+HH+mm+ss").format(new Date());
    try {
        storeUserDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(cur_uid).child("History");
        storeUserDefaultDataReference.child(time).setValue(g).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    catch (Exception e)
    {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }





    }



















}

   /* private void change(String n,String type) {

        int j1=0, j2=0, j3=0, j4=0, j5=0, j6=0, j7=0, j8=0;
        int k1 = 0, k2 = 0, k3 = 0, k4 = 0, k5 = 0, k6 = 0, k7 = 0, k8 = 0;


        try {
            j1 = Integer.parseInt(n.charAt(0)+ "");
            j2 = Integer.parseInt(n.charAt(1) + "");
        j3 = Integer.parseInt(n.charAt(2)+ "");
        j4 = Integer.parseInt(n.charAt(3)+ "");
        j5 = Integer.parseInt(n.charAt(4)+ "");
        j6 = Integer.parseInt(n.charAt(5)+ "");
        j7 = Integer.parseInt(n.charAt(6)+ "");
        j8 = Integer.parseInt(n.charAt(7)+ "");
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }



        if (type.equals("A+")) {
            k1 = (Integer.parseInt(unit));
        }
        if (type.equals("B+")) {
            k2 = (Integer.parseInt(unit));
        }
        if (type.equals("AB+")) {
            k3 = (int) (Integer.parseInt(unit));
        }
        if (type.equals("O+")) {
            k4 = (int) (Integer.parseInt(unit));
        }
        if (type.equals("A-")) {
            k5 = (int) (Integer.parseInt(unit));
        }
        if (type.equals("B-")) {
            k6 = (int) (Integer.parseInt(unit));
        }
        if (type.equals("Ab-")) {
            k7 = (int) (Integer.parseInt(unit));
        }
        if (type.equals("O-")) {
            k8 = (int) (Integer.parseInt(unit));
        }


        String a="";
        a = ("" + (k1 + j1) + "" + (k1 + j1) + "" + (k1 + j1) + "" + (k1 + j1) + "" + (k1 + j1) + "" + (k1 + j1) + "" + (k1 + j1) + "" + (k1 + j1));
        Toast.makeText(Slip.this, a, Toast.LENGTH_LONG).show();

        /*storeUserDefaultDataReference.child("slip").setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Requisition is Updated", Toast.LENGTH_SHORT).show();
                    Intent startPage = new Intent(Slip.this, MainPage.class);
                    startPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startPage);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error while updating your data", Toast.LENGTH_SHORT).show();

                }
            }
        });*/










