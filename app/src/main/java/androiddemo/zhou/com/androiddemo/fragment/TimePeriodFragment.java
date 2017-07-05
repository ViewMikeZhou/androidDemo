package androiddemo.zhou.com.androiddemo.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androiddemo.zhou.com.androiddemo.R;

/**
 * Created by Administrator on 2017/7/3.
 */

public class TimePeriodFragment extends Fragment {
    private static TimePeriodFragment sTimePeriodFragment;
    private View mView;

    public static TimePeriodFragment getInstance() {
        if (sTimePeriodFragment == null) {
            sTimePeriodFragment = new TimePeriodFragment();
        }
        return sTimePeriodFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_time_period, null);
        return mView;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view == null) {
            view = mView;
        }
        //    LinearLayout ruleContationer = (LinearLayout) view.findViewById(R.id.ll_rule_contationer);
     /*   NiceSpinner niceSpinner = (NiceSpinner) view.findViewById(R.id.nice_spinner);

        List<String> data = Arrays.asList("1小时","2小时","3小时","4小时","5小时");
        niceSpinner.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,data));
        niceSpinner.setPadding(GlobalUtil.dip2px(5),GlobalUtil.dip2px(5),GlobalUtil.dip2px(5),GlobalUtil.dip2px(5));
        niceSpinner.attachDataSource(data);*/





    /*    XyzRuler xyzRuler = new XyzRuler.Build()
              //  .setAnimTime(100)
                .setBegin(0)
                .setEnd(288)
                .setBorderWidth(GlobalUtil.dip2px(5))
                .setLineHeigt(GlobalUtil.dip2px(10))
                .setLinTotext(6)
                .setLineWidth(GlobalUtil.dip2px(1))
                .setTrigonSize(GlobalUtil.dip2px(10))
                .setIstop(false)
                .build();
        xyzRuler.setLayoutParams(params);
        ruleContationer.addView(xyzRuler);
        xyzRuler.setOnRulerValueChangeListener(new XyzRuler.RulerValue() {
            @Override
            public void value(int value) {
                Log.e("test","value :" + value);
            }
        });*/

    }

}
