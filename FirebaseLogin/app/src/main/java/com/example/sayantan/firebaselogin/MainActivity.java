package com.example.sayantan.firebaselogin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button button;
    private FirebaseAuth mAuth;



    public void onRegister(View view) {

        Toast.makeText(this,"You click on Register",Toast.LENGTH_LONG).show();

        String myEmail = email.getText().toString();
        String myPass = password.getText().toString();

        if (myEmail.isEmpty() || myPass.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
       }/* else if (!(myEmail.contain)) {
            Toast.makeText(MainActivity.this, "Invalid Email @ missing", Toast.LENGTH_LONG).show();
        } else if (!(myPass.contains("."))) {
            Toast.makeText(MainActivity.this, "Invalid Email . missing", Toast.LENGTH_LONG).show();
        } */else {
            Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(myEmail, myPass)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(MainActivity.this, "Successfully Sign Up", Toast.LENGTH_LONG).show();

                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                // If sign in fails, display a message to the user.

                                try{
                                    throw  task.getException();
                                }
                                catch (Exception e){
                                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }

                            }

                            // ...
                        }
                    });

        }
    }

    public void onSignin(View view) {
        Toast.makeText(this, "You click on Sign In", Toast.LENGTH_SHORT).show();

        String myEmail = email.getText().toString();
        String myPass = password.getText().toString();

        if (myEmail.isEmpty() || myPass.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(myEmail, myPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this,"Your are sign in",Toast.LENGTH_LONG).show();
                            } else if(! task.isSuccessful()) {
                                // If sign in fails, display a message to the user.
                                try{
                                    throw  task.getException();
                                }
                                catch (Exception e){
                                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }

                            }

                            // ...
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser.equals(null)) {
            Toast.makeText(this, "You are already logged in", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);


        mAuth = FirebaseAuth.getInstance();
    }




}
