package androiddemo.zhou.com.androiddemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androiddemo.zhou.com.androiddemo.R;
import androiddemo.zhou.com.androiddemo.fragment.TimePeriodFragment;
import androiddemo.zhou.com.androiddemo.util.GlobalUtil;

/**
 * Created by Administrator on 2017/7/3.
 */

public  class SingalActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singal);
        ImageView imgAdd = (ImageView) findViewById(R.id.add);
        LinearLayout timePeriod = (LinearLayout) findViewById(R.id.timePeriod);
        RelativeLayout rlContainer = (RelativeLayout) findViewById(R.id.rl_container);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                int childCount = timePeriod.getChildCount();
                TextView tv = new TextView(SingalActivity.this);
                tv.setText(childCount+1+"");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(GlobalUtil.dip2px(5), 0, 0, 0);
                tv.setLayoutParams(params); //设置参数给TextView;
                tv.setGravity(Gravity.CENTER);
                tv.setBackground(getResources().getDrawable(R.drawable.text_selector));
                tv.setPadding(GlobalUtil.dip2px(5), GlobalUtil.dip2px(5), GlobalUtil.dip2px(5), GlobalUtil.dip2px(5));
                tv.setTag(childCount+1);
                timePeriod.addView(tv);
                TimePeriodFragment fragment = TimePeriodFragment.getInstance();
                Bundle args = new Bundle();
                args.putInt("index",childCount+1);
                fragment.setArguments(args);
                fragmentTransaction.replace(R.id.rl_container,fragment).commit();
            }
        });


    }
}
