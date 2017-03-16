package com.example.administrator.keeprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Administrator on 2017/1/5.
 */

public class TipsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private SimpleAdapter simp_adapter;
    private ArrayList<Map<String,Object>> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        listView= (ListView) findViewById(R.id.listView);
        datalist=new ArrayList<Map<String, Object>>();
        simp_adapter=new SimpleAdapter(TipsActivity.this,getData(),R.layout.item,new String[]{"pic","text1","text2"},new int[]{R.id.pic,R.id.listText1,R.id.listText2});
//        listView.setAdapter(arr_adapter);
        listView.setAdapter(simp_adapter);
        listView.setOnItemClickListener(this);

    }
    private ArrayList<Map<String,Object>> getData()
    {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","百度经验");
        map.put("text2","跑步需要知道的知识");
        datalist.add(map);
        map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","运动常识");
        map.put("text2","跑步知识大全");
        datalist.add(map);
        map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","你真的会跑步吗？");
        map.put("text2","五个小知识教你立刻学会跑步");
        datalist.add(map);
        map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","知乎");
        map.put("text2","如何正确的跑步及跑步常识?");
        datalist.add(map);
        map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","冷知识");
        map.put("text2","跑步界你所不知道的那些事儿");
        datalist.add(map);
        map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","给越野跑者的备忘录");
        map.put("text2","15个跑前知识需记牢");
        datalist.add(map);
        map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","跑步指南");
        map.put("text2","微博达人教你如何跑步");
        datalist.add(map);
        map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","微博热文");
        map.put("text2","关于跑步的那些事儿");
        datalist.add(map);
        map=new HashMap<String, Object>();
        map.put("pic",R.drawable.shuqian);
        map.put("text1","爱上跑步的13周");
        map.put("text2","适合初学者的训练计划");
        datalist.add(map);

        return datalist;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String text=listView.getItemAtPosition(position)+"";
//        Toast.makeText(MainActivity.this,"position="+position+" text="+text,Toast.LENGTH_SHORT).show();
        HashMap<String,Object> map = (HashMap<String, Object>)parent.getItemAtPosition(position);
        Bundle bundle=new Bundle();
        bundle.putString("text1", (String) map.get("text1"));
        Intent intent=new Intent(TipsActivity.this,WebActivity.class);
        intent.putExtra("key",bundle);
        startActivity(intent);
    }
}
