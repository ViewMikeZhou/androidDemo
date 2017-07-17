package androiddemo.zhou.com.androiddemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androiddemo.zhou.com.androiddemo.Artist;
import androiddemo.zhou.com.androiddemo.Genre;
import androiddemo.zhou.com.androiddemo.R;
import androiddemo.zhou.com.androiddemo.adapter.GenreAdapter;

/**
 * Created by Administrator on 2017/7/17.
 */

public  class ExpandRecycleViewActivity extends AppCompatActivity{

    private RecyclerView mExpandRecycleView;
    GenreAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_recycle);
        mExpandRecycleView = (RecyclerView) findViewById(R.id.expand_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator animator = mExpandRecycleView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        List<Genre> groups = new ArrayList<>();

        List<Artist> childData = Arrays.asList(new Artist("1",false),new Artist("2",false));
        Genre expandableGroup = new Genre("你好",childData);
        groups.add(expandableGroup);
        adapter = new GenreAdapter(groups);
        mExpandRecycleView.setLayoutManager(layoutManager);
        mExpandRecycleView.setAdapter(adapter);
    }
}
