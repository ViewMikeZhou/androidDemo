package androiddemo.zhou.com.androiddemo.util;

import androiddemo.zhou.com.androiddemo.App;

/**
 * Created by Administrator on 2017/7/3.
 */

public class GlobalUtil {
    //dp装px
    public static int dip2px(float dpValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
