package com.example.quizapp_m22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Registre extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnRegister;
    EditText etName;
    EditText etEmail;
    EditText etPsw;
    EditText etConfpsw;
    ProgressBar pbLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPsw=findViewById(R.id.etPsw);
        etConfpsw=findViewById(R.id.etConfPsw);
        btnRegister=findViewById(R.id.btnRegistre2);
        pbLoad=findViewById(R.id.pbLoad);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ConfPsw=etConfpsw.getText().toString();

                if(etEmail.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email is required",Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()){
                    Toast.makeText(getApplicationContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if(etPsw.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password is required",Toast.LENGTH_SHORT).show();
                    etPsw.requestFocus();
                    return;
                }
                if(etPsw.getText().toString().length()<6){
                    Toast.makeText(getApplicationContext(),"The length of your password must exceed 6 characters",Toast.LENGTH_SHORT).show();
                    etPsw.requestFocus();
                    return;
                }
                if(!ConfPsw.equals(etPsw.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Password not compatible",Toast.LENGTH_SHORT).show();
                    etConfpsw.requestFocus();
                    return;
                }

                String Name=etName.getText().toString();
                String Email = etEmail.getText().toString();
                String Psw=etPsw.getText().toString();

                pbLoad.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(Email,Psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user1=new User(Name,Email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pbLoad.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(),"Successful registration",Toast.LENGTH_SHORT).show();
                                    }else{
                                        pbLoad.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(),"Failed to registration! Try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            pbLoad.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Failed to registration! Try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                });
            }
        });
    }
}