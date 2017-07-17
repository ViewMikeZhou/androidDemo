package androiddemo.zhou.com.androiddemo.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import androiddemo.zhou.com.androiddemo.R;
import androiddemo.zhou.com.androiddemo.demo2.adapter.ContactAdapter;
import androiddemo.zhou.com.androiddemo.demo2.bean.ContactBean;
import androiddemo.zhou.com.androiddemo.demo2.util.CommonUtil;
import androiddemo.zhou.com.androiddemo.view.CustomItemDecoration;
import androiddemo.zhou.com.androiddemo.view.SideBar;

/**
 * Created by Administrator on 2017/6/22.
 */

public class RecycleViewheadStickActivity extends AppCompatActivity{
    ContactAdapter mAdapter;
    List<ContactBean> nameList = new ArrayList<>();
    private final Rect mBounds = new Rect();
    SideBar side_bar;
    private CustomItemDecoration decoration;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy_head_stick);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

      //  side_bar = (SideBar) findViewById(R.id.side_bar);
        /*side_bar.setIndexChangeListener(new SideBar.indexChangeListener() {
            @Override
            public void indexChanged(String tag) {
                if (TextUtils.isEmpty(tag) || nameList.size() <= 0) return;
                for (int i = 0; i < nameList.size(); i++) {
                    if (tag.equals(nameList.get(i).getIndexTag())) {
                        mLayoutManager.scrollToPositionWithOffset(i, 0);
//                        layoutManager.scrollToPosition(i);
                        return;
                    }
                }
            }
        });*/
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(decoration =new CustomItemDecoration(this));

        mAdapter = new ContactAdapter(this);
        initDatas();
        recyclerView.setAdapter(mAdapter);

    }

  /*  private void drawTitleBar(Canvas canvas, RecyclerView parent, View child, ContactBean bean, int position) {
        final int left = 0;
        final int right = parent.getWidth();
        //返回一个包含Decoration和Margin在内的Rect
        parent.getDecoratedBoundsWithMargins(child, mBounds);
        final int top = mBounds.top;
        final int bottom = mBounds.top + Math.round(ViewCompat.getTranslationY(child)) + dividerHeight;
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(left, top, right, bottom, mPaint);
        //根据位置不断变换Paint的颜色
        ColorUtil.setPaintColor(mPaint, position);
        mPaint.setTextSize(40);
        canvas.drawCircle(DpUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 2, 35, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(bean.getIndexTag(), DpUtil.dp2px(mContext, 42.5f), bottom - dividerHeight / 3, mPaint);
    }*/

    private void initDatas() {
        String[] names = {"孙尚香", "安其拉", "白起", "不知火舞", "@小马快跑", "_德玛西亚之力_", "妲己", "狄仁杰", "典韦", "韩信",
                "老夫子", "刘邦", "刘禅", "鲁班七号", "墨子", "孙膑", "孙尚香", "孙悟空", "项羽", "亚瑟",
                "周瑜", "庄周", "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔", "王昭君", "虞姬",
                "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操", "芈月", "荆轲", "高渐离",
                "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "吕布", "嬴政", "娜可露露", "武则天", "赵云", "姜子牙",};
        for (String name : names) {
            ContactBean bean = new ContactBean();
            bean.setName(name);
            nameList.add(bean);
        }
        //对数据源进行排序
        CommonUtil.sortData(nameList);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(nameList);
      //  side_bar.setIndexStr(tagsStr);
        decoration.setDatas(nameList, tagsStr);
        mAdapter.addAll(nameList);
    }
}

