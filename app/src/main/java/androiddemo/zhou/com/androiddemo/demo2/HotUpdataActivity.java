package androiddemo.zhou.com.androiddemo.demo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import androiddemo.zhou.com.androiddemo.R;


/**热修复使用 : 官方 http://tinkerpatch.com/Docs/SDK
 * 1. app buildGradle中  --> (1) apply from: 'tinkerpatch.gradle' (2)  provided('com.tencent.tinker:tinker-android-anno:1.7.11')
                                                                     compile("com.tinkerpatch.sdk:tinkerpatch-android-sdk:1.1.7")

 * 2. project buildGradle中 -->    //热修复 classpath "com.tinkerpatch.sdk:tinkerpatch-gradle-plugin:1.1.7"
 * 3.创建  tinkerpatch.gradle 名字不能写错;
 *
 * Created by Administrator on 2017/6/16.
 */

public class HotUpdataActivity extends AppCompatActivity{

    private TextView mtv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_updata);
        mtv = (TextView) findViewById(R.id.tv);
    }

    public void hotUpdata(View view) {
        mtv.setText("这是修复后的操作");
    }
}
