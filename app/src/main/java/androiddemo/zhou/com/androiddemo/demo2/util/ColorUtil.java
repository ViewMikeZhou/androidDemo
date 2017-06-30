package androiddemo.zhou.com.androiddemo.demo2.util;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Administrator on 2017/6/22.
 */

public class ColorUtil {
    /**
     * 根据数据位置来给Paint循环设置颜色
     *
     * @param mPaint   Paint
     * @param position position
     */
    public static void setPaintColor(Paint mPaint, int position) {
        int pos = position % 6;
        switch (pos) {
            case 0:
                mPaint.setColor(Color.parseColor("#EC5745"));
                break;
            case 1:
                mPaint.setColor(Color.parseColor("#377caf"));
                break;
            case 2:
                mPaint.setColor(Color.parseColor("#4ebcd3"));
                break;
            case 3:
                mPaint.setColor(Color.parseColor("#6fb30d"));
                break;
            case 4:
                mPaint.setColor(Color.parseColor("#FFA500"));
                break;
            case 5:
                mPaint.setColor(Color.parseColor("#bf9e5a"));
                break;
        }
    }
}