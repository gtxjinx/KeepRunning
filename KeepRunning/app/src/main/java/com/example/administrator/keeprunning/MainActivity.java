package com.example.administrator.keeprunning;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button stepbutton;
    private Button messagebutton;
    private Button runningbutton;
    private Button tipsbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();

    }

    private void initButton() {
        stepbutton= (Button) findViewById(R.id.step_button);
        messagebutton= (Button) findViewById(R.id.message_button);
        runningbutton= (Button) findViewById(R.id.running_button);
        tipsbutton= (Button) findViewById(R.id.tips_button);
        initListener();
    }

    private void initListener() {
        stepbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StepActivity.class));
            }
        });
        runningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RunningActivity.class));
            }
        });
        tipsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TipsActivity.class));
            }
        });
        messagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserActivity.class));
            }
        });
    }
}
