package com.example.sayantan.life_saver;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    Spinner bloodSpinner,dorr;

    String selectedbloodGroup,selectedType;

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference;

    EditText name,city;
    EditText phoneno;
    EditText codetext;
    Button verify_btn,resend_btn;

    ProgressDialog loadingbar,progressDialog;
    String Phoneno,Name,CityName;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken  resendToken;

    private android.support.v7.widget.Toolbar toolbar;





    private static final String[] bloodgroup={"Blood Group","A+","A-","B+","B-","AB+","AB-","O+","O-"};

    private static final String[] type={"Type","Donor","Recipient","Not Now"};

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth=FirebaseAuth.getInstance();


        name = (EditText) findViewById(R.id.Name);
        phoneno = (EditText) findViewById(R.id.Phoneno);
        codetext=(EditText)findViewById(R.id.Codetext) ;
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        city=(EditText)findViewById(R.id.city);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        button = (Button) findViewById(R.id.Signup_btn);
        verify_btn=(Button)findViewById(R.id.Verify);
        resend_btn=(Button)findViewById(R.id.Resend);

        loadingbar=new ProgressDialog(this);
        progressDialog=new ProgressDialog(this);

        bloodSpinner = (Spinner) findViewById(R.id.b);
        dorr = (Spinner) findViewById(R.id.dorr);

        ArrayAdapter<String> adapterBlood = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_dropdown_item, bloodgroup);
        bloodSpinner.setAdapter(adapterBlood);

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_dropdown_item, type);
        dorr.setAdapter(adapterType);

        resend_btn.setEnabled(false);
        resend_btn.setVisibility(View.INVISIBLE);
        verify_btn.setEnabled(false);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbloodGroup = bloodSpinner.getSelectedItem().toString();
                selectedType = dorr.getSelectedItem().toString();
                 Name = name.getText().toString();
                Phoneno = phoneno.getText().toString();
                Phoneno=((Phoneno.length()==10)?("+91"+Phoneno):Phoneno);
                CityName=city.getText().toString();




                SignupAccount(Name, Phoneno, selectedbloodGroup, selectedType,CityName);
            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = codetext.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    codetext.setError("Enter code...");
                    codetext.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }


    private void SignupAccount( String name,String phoneno,String bloodGroup,String type,String cityName){

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(SignUp.this, "Name Field is Empty", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(cityName)) {
            Toast.makeText(SignUp.this, "City Field is Empty", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(phoneno)) {
            Toast.makeText(SignUp.this, "Phone No. is Empty", Toast.LENGTH_LONG).show();
        }

        else if(phoneno.length()>13 || phoneno.length()<10){

            Toast.makeText(SignUp.this,"Phone Number must be of 10digits", Toast.LENGTH_LONG).show();
        }


        else if(bloodGroup.equals("Blood Group")){
            Toast.makeText(SignUp.this, "Blood Group Field is Empty", Toast.LENGTH_LONG).show();
        }
        else if(type.equals("Type")){
            Toast.makeText(SignUp.this, "Type Field is Empty", Toast.LENGTH_LONG).show();
        }

        else {

            button.setEnabled(false);
            button.setVisibility(View.INVISIBLE);
            resend_btn.setEnabled(true);
            resend_btn.setVisibility(View.VISIBLE);
            verify_btn.setEnabled(true);

            loadingbar.setMessage("Sending the OTP");
            loadingbar.setMessage("Please wait while we send the OTP");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            sendCode(phoneno);


        }
    }

    private void sendCode(String phoneno){

        setUpVerificationCallback();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneno,
                90,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);

    }

    private void setUpVerificationCallback(){

        verificationCallbacks=
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        String code = phoneAuthCredential.getSmsCode();
                        if (code != null) {
                            codetext.setText(code);
                        }

                        loadingbar.dismiss();
                    }







                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        loadingbar.dismiss();

                        Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onCodeSent(String verificationId,PhoneAuthProvider.ForceResendingToken token){
                        loadingbar.dismiss();
                        phoneVerificationId=verificationId;
                        resendToken=token;


                    }

                };

    }
    public void verifyCode(String code){



        if(TextUtils.isEmpty(code)){
            Toast.makeText(getApplicationContext(),"Verify Code is Empty",Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog.setTitle("Setting Up your Account");
            progressDialog.setMessage("Hold on while we verify your details");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signWithPhoneAuthCredential(credential);
        }

    }
    private void signWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                           String currentUid= mAuth.getCurrentUser().getUid();



                            storeUserDefaultDataReference= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid);



                            storeUserDefaultDataReference.child("History").setValue(null);
                            storeUserDefaultDataReference.child("userName").setValue(Name);
                            storeUserDefaultDataReference.child("userPh").setValue(Phoneno);
                            storeUserDefaultDataReference.child("userBlood").setValue(selectedbloodGroup);
                            storeUserDefaultDataReference.child("userCity").setValue(CityName);
                            storeUserDefaultDataReference.child("userType").setValue(selectedType).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {
                                        Intent startPage = new Intent(SignUp.this, MainPage.class);
                                        startPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(startPage);
                                        progressDialog.dismiss();
                                        finish();
                                    }

                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Error while updating your data",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });





                        } else {
                            progressDialog.dismiss();

                            try {
                                throw task.getException();
                            }
                            catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
}
                });
    }

    public void resendCode(View view){

        String Phoneno=phoneno.getText().toString();
        Phoneno=((Phoneno.length()==10)?("+91"+Phoneno):Phoneno);
        setUpVerificationCallback();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                Phoneno
                ,
                20,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

}



