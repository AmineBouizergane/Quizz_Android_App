package com.example.quizapp_m22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

import static com.google.android.gms.common.util.CollectionUtils.listOf;

public class Score extends AppCompatActivity {

    TextView tvScore;
    DonutProgressView dpv;
    TextView tvDesc;
    int score;
    Button btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btnMap=findViewById(R.id.btnMapP);

        dpv=findViewById(R.id.donut_view);
        tvDesc=findViewById(R.id.txtDesc);

        tvScore=findViewById(R.id.TxtScore);
        Bundle Score = getIntent().getExtras();
        score= (int) Score.get("Score");
        tvScore.setText(score+"");




        DonutSection ds=new DonutSection("Score", Color.parseColor("#F44336"), (float) score);

        ArrayList<DonutSection> lds= new ArrayList<DonutSection>();

        lds.add(ds);

        dpv.setCap(100);

        dpv.submitData(lds);

        if(score>=70){
            tvDesc.setText("Great job \uD83D\uDD25");
            tvDesc.setTextColor(getResources().getColor(R.color.green));
        }else if(score>=40){
            tvDesc.setText("Good job \uD83D\uDC4D");
            tvDesc.setTextColor(getResources().getColor(R.color.orange));
        }else{
            tvDesc.setText("You can do better \uD83D\uDE15");
            tvDesc.setTextColor(getResources().getColor(R.color.red));
        }

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Score.this,Maps.class);
                intent.putExtra("Score",score);
                startActivity(intent);
            }
        });

    }
}