package androiddemo.zhou.com.androiddemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androiddemo.zhou.com.androiddemo.R;
import androiddemo.zhou.com.androiddemo.adapter.DividerGridItemDecoration;
import androiddemo.zhou.com.androiddemo.adapter.OrientRecyAdatper;
import androiddemo.zhou.com.androiddemo.util.GlobalUtil;
import androiddemo.zhou.com.androiddemo.view.BottomDialog;

/**
 * Created by Administrator on 2017/7/14.
 */

public class FabTransitionActivity extends ActionBarActivity implements View.OnClickListener {

    private List<Integer> mImgs;
    private float mimgWidth;
    private List<Integer> mImgsRed;
    private BottomDialog mBottomDialog;
    private OrientRecyAdatper mAdapter;
    private RecyclerView mRecy;

    public List<Integer> selectPostion = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabtransition);
        View fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mImgs = Arrays.asList(R.mipmap.
                        north_left
                , R.mipmap.west_left
                , R.mipmap.south_left
                , R.mipmap.north_left
                , R.mipmap.north
                , R.mipmap.west
                , R.mipmap.south
                , R.mipmap.east
                , R.mipmap.north_right
                , R.mipmap.west_right
                , R.mipmap.south_right
                , R.mipmap.west_right
                , R.mipmap.north_people
                , R.mipmap.west_people
                , R.mipmap.south_people
                , R.mipmap.east_people
        );
        mImgsRed = Arrays.asList(R.mipmap.
                        north_left_red
                , R.mipmap.west_left_red
                , R.mipmap.south_left_red
                , R.mipmap.north_left_red
                , R.mipmap.north_red
                , R.mipmap.west_red
                , R.mipmap.south_red
                , R.mipmap.east_red
                , R.mipmap.north_right_red
                , R.mipmap.west_right_red
                , R.mipmap.south_right_red
                , R.mipmap.west_right_red
                , R.mipmap.north_people_red
                , R.mipmap.west_people_red
                , R.mipmap.south_people_red
                , R.mipmap.east_people_red
        );

        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        float widthRecy = width / 9;
        Log.i("test", widthRecy + ",频幕宽度-->" + width);
        mimgWidth = (widthRecy * 8) / 4;
        int i = GlobalUtil.dip2px(4);
        Log.i("test", mimgWidth + "" + "---->" + i);


        mBottomDialog = BottomDialog.create(getFragmentManager())
                .setLayoutRes(R.layout.dialog_layout)
                .setViewListener(new BottomDialog.ViewListener() {

                    private GridLayoutManager mLayout;

                    @Override
                    public void bindView(View v) {
                        mRecy = (RecyclerView) v.findViewById(R.id.recy);

                        mLayout = new GridLayoutManager(getApplication(), 4, OrientationHelper.HORIZONTAL, false) {
                            @Override
                            public boolean canScrollHorizontally() {
                                return false;
                            }
                        };

                        mRecy.setLayoutManager(mLayout);
                        mRecy.addItemDecoration(new DividerGridItemDecoration());
                        if (mAdapter == null) {
                            mAdapter = new OrientRecyAdatper(mImgs, mImgsRed, mimgWidth, mBottomDialog, selectPostion);
                        }
                        mAdapter.setOnSelect(new OrientRecyAdatper.OnselectPositionListen() {
                            @Override
                            public void onselect(List<Integer> position) {
                                selectPostion.clear();
                                selectPostion.addAll(position);
                                Log.i("test", position.toString());
                            }
                        });
                        mRecy.setAdapter(mAdapter);
                    }
                }).setOndialogDissmissed(new BottomDialog.OndialogDissmissed() {
                    @Override
                    public void dialogDismiss() {

                    }
                });

    }



    @Override
    public void onClick(View v) {
        mBottomDialog.show();
    }

}
