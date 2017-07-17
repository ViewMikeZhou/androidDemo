package androiddemo.zhou.com.androiddemo.adapter;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import androiddemo.zhou.com.androiddemo.R;

/**
 * Created by Administrator on 2017/7/17.
 */

public class ArtistViewHolder extends ChildViewHolder {

    private TextView childTextView;
    public ArtistViewHolder(View itemView) {
        super(itemView);
        childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    }
    public void setArtistName(String name) {
        childTextView.setText(name);
    }
}
