package com.example.quizapp_m22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;


public class Quizz extends AppCompatActivity {

    Task<DataSnapshot> Questions;

    private DatabaseReference mDatabase;

    RadioGroup rgC;

    RadioButton rbC1;
    RadioButton rbC2;
    RadioButton rbC3;
    RadioButton rbC4;
    TextView tvQst;

    int i;
    int Score;

    String rep;
    String repU;

    Button btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        i=0;
        Score=0;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rgC=findViewById(R.id.rgC);

        rbC1=findViewById(R.id.radioButton1);
        rbC2=findViewById(R.id.radioButton2);
        rbC3=findViewById(R.id.radioButton3);
        rbC4=findViewById(R.id.radioButton4);

        tvQst=findViewById(R.id.tvQst);

        rep="";
        repU="";
        btnDone=findViewById(R.id.btnDone);
        mDatabase.child("Questions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()){
                   //Toast.makeText(getApplicationContext(), "not successful", Toast.LENGTH_LONG).show();
                }else{
                    //Toast.makeText(getApplicationContext(),"Data received",Toast.LENGTH_LONG).show();
                    Questions=task;
                    tvQst.setText(Questions.getResult().child(i+"").child("title").getValue()+"");
                    rbC1.setText(Questions.getResult().child(i+"").child("choix").child("0").child("title").getValue()+"");
                    rbC2.setText(Questions.getResult().child(i+"").child("choix").child("1").child("title").getValue()+"");
                    rbC3.setText(Questions.getResult().child(i+"").child("choix").child("2").child("title").getValue()+"");
                    rbC4.setText(Questions.getResult().child(i+"").child("choix").child("3").child("title").getValue()+"");
                    rep=Questions.getResult().child(i+"").child("reponse").getValue()+"";
                    i++;
                }

                //Log.d("TAG_FIREBASE", "onComplete: "+task.getResult().getValue());

                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/
            }


        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int a =rgC.getCheckedRadioButtonId();
                if(a==-1){
                    Toast.makeText(getApplicationContext(),"Please chose a answer",Toast.LENGTH_LONG).show();
                    return;
                }
                RadioButton rb=(RadioButton) findViewById(a);
                repU=rb.getText().toString();
                rb.setChecked(false);

                Log.d("TAG_FIREBASE", "onComplete: "+Questions.getResult().getChildrenCount());

                if(repU.equals(rep)){
                    Score=Score+10;
                }



                if(i<Questions.getResult().getChildrenCount()){
                    tvQst.setText(Questions.getResult().child(i+"").child("title").getValue()+"");
                    rbC1.setText(Questions.getResult().child(i+"").child("choix").child("0").child("title").getValue()+"");
                    rbC2.setText(Questions.getResult().child(i+"").child("choix").child("1").child("title").getValue()+"");
                    rbC3.setText(Questions.getResult().child(i+"").child("choix").child("2").child("title").getValue()+"");
                    rbC4.setText(Questions.getResult().child(i+"").child("choix").child("3").child("title").getValue()+"");
                    rep=Questions.getResult().child(i+"").child("reponse").getValue().toString()+"";
                    i++;
                }else{

                    Intent intent= new Intent(Quizz.this,Score.class);
                    intent.putExtra("Score",Score);
                    startActivity(intent);
                }



            }
        });


        /*for (int i=0;i<Questions.getResult().getChildrenCount();i++)
        {
            Log.d("TAG_FIREBASE", "onComplete: "+Questions.getResult().child(i+"").child("choix").child("0").child("title").getValue());
        }*/

    }
}