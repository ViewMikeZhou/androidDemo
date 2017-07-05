package androiddemo.zhou.com.androiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import androiddemo.zhou.com.androiddemo.demo2.HotUpdataActivity;
import androiddemo.zhou.com.androiddemo.demo2.RecycleViewheadStickActivity;

public class MainActivity extends AppCompatActivity implements Myadatper.OnItemClick {

    private Myadatper mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyview = (RecyclerView) findViewById(R.id.recyview);
        recyview.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList();
        mList.add("热更新");
        mList.add("Recyview头部悬浮");
        mList.add("wifi连接");
        mList.add("信号机Demo");
        mAdapter = new Myadatper(mList);
        mAdapter.setOnItemClick(this);
        recyview.setAdapter(mAdapter);
}

   /* *//**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     *//*
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }*/

    @Override
    public void click(String message) {
       switch (message){
           case "热更新" :
               startActivity(new Intent(this,HotUpdataActivity.class));
               break;
           case "Recyview头部悬浮":
               startActivity(new Intent(this,RecycleViewheadStickActivity.class));
               break;
           case "wifi连接":
               startActivity(new Intent(this,WifiActivity.class));
               break;
           case "信号机Demo":
               startActivity(new Intent(this,SingalActivity.class));
               break;
       }
    }
}
