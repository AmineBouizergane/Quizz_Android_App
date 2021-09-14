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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPwd;
    Button btnLogin;
    TextView btnRegister;
    ProgressBar pbLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail=(EditText) findViewById(R.id.etEmail);
        etPwd=(EditText) findViewById(R.id.etPsw);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        btnRegister=(TextView) findViewById(R.id.btnRegistre);
        pbLoad = findViewById(R.id.pbLoad);
        //etEmail.setText("aminebouizergane@gmail.com");
        //etPwd.setText("12345678");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etEmail.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email is required",Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if(etPwd.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password is required",Toast.LENGTH_SHORT).show();
                    etPwd.requestFocus();
                    return;
                }
                pbLoad.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            pbLoad.setVisibility(View.GONE);
                            Intent intent= new Intent(Login.this,Quizz.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"Succeful Login",Toast.LENGTH_SHORT).show();
                        }else{
                            pbLoad.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Incorrect username or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

               /*if(etEmail.getText().toString().equals("amine@gmail.com") && etPwd.getText().toString().equals("1234")){
                    Intent intent= new Intent(Login.this,Quizz.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Incorrect username or password",Toast.LENGTH_SHORT).show();
                }*/
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,Registre.class);
                startActivity(intent);
            }
        });
    }
}