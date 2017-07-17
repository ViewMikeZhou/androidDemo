package androiddemo.zhou.com.androiddemo;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */

public class Genre extends ExpandableGroup<Artist> {
    public Genre(String title, List<Artist> items) {
        super(title, items);
    }

}