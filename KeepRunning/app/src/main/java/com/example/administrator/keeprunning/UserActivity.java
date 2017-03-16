package com.example.administrator.keeprunning;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/1/5.
 */

public class UserActivity extends AppCompatActivity {
    private Button height;
    private Button weight;
    private Button run_length;
    private Button walk_length;
    private String value;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        height= (Button) findViewById(R.id.button1);
        weight= (Button) findViewById(R.id.button2);
        run_length= (Button) findViewById(R.id.button3);
        walk_length= (Button) findViewById(R.id.button4);
        sharedPreferences = getSharedPreferences("useInfo", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("height","190");
//        editor.putString("weight","70");
//        editor.putString("run_length","5");
//        editor.putString("walk_length","10");
//        editor.commit();
        height.setText("身高："+sharedPreferences.getString("height","")+"cm");
        weight.setText("体重："+sharedPreferences.getString("weight","")+"kg");
        run_length.setText("目标每天跑步里程："+sharedPreferences.getString("run_length","")+"km");
        walk_length.setText("目标每天步行步数："+sharedPreferences.getString("walk_length","")+"步");
//        editText=new EditText(UseInfoActivity.this);
        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_dialog(height);
            }
        });
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_dialog(weight);
            }
        });
        run_length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_dialog(run_length);
            }
        });
        walk_length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_dialog(walk_length);
//                Intent intent=new  Intent(UserActivity.this,StepActivity.class);
//                Bundle bundle =new Bundle();
//                bundle.putString("walk_length", sharedPreferences.getString("walk_length",""));
//                intent.putExtra("key", bundle);
            }
        });

    }
    private void init_dialog(Button bt)
    {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Button button=bt;
        final EditText et=new EditText(this);
        Dialog dialog=new AlertDialog.Builder(UserActivity.this).setTitle("请输入").setIcon(
                android.R.drawable.ic_dialog_info).setView(
                et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                value=et.getText().toString();
                if (button.getId()==R.id.button1)
                {
                    button.setText("身高："+value+"cm");
                    editor.putString("height",value);
                }
                else if (button.getId()==R.id.button2)
                {
                    button.setText("体重："+value+"kg");
                    editor.putString("weight",value);
                }
                else if (button.getId()==R.id.button3)
                {
                    button.setText("目标每天跑步里程："+value+"km");
                    editor.putString("run_length",value);

                }
                else
                {
                    button.setText("目标每天步行步数："+value+"步");
                    editor.putString("walk_length",value);
                }
                editor.commit();
//                startActivity(new Intent(UseInfoActivity.this,UseInfoActivity.class));
//                finish();

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                value="";
            }
        }).show();
    }
}
