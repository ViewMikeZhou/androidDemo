package androiddemo.zhou.com.androiddemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import androiddemo.zhou.com.androiddemo.App;
import androiddemo.zhou.com.androiddemo.Artist;
import androiddemo.zhou.com.androiddemo.Genre;
import androiddemo.zhou.com.androiddemo.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Administrator on 2017/7/17.
 */

public class GenreAdapter extends ExpandableRecyclerViewAdapter<GenreAdapter.GenreViewHolder, ArtistViewHolder> {

    public GenreAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public ArtistViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ArtistViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Artist artist = ((Genre) group).getItems().get(childIndex);
        holder.setArtistName(artist.getName());
    }

    @Override
    public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGenreTitle(group);
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean groupExpanded = isGroupExpanded(group);
                Toast.makeText(App.getInstance(), "图片被点击" + groupExpanded, Toast.LENGTH_SHORT).show();

            }
        });
        holder.genreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(App.getInstance(), "字体被点击了", Toast.LENGTH_SHORT).show();

                if (onGroupClick(holder.getAdapterPosition())) {
                    holder.expand();
                }else{
                    holder.collapse();
                }
            }
        });

    }


    public class GenreViewHolder extends GroupViewHolder {

        private TextView genreName;
        private ImageView arrow;
        private ImageView icon;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genreName = (TextView) itemView.findViewById(R.id.list_item_genre_name);
            arrow = (ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
            //    icon = (ImageView) itemView.findViewById(R.id.list_item_genre_icon);
        }

        public void setGenreTitle(ExpandableGroup genre) {
            if (genre instanceof Genre) {
                genreName.setText(genre.getTitle());
                // icon.setBackgroundResource(((Genre) genre).getIconResId());
            }
           /* if (genre instanceof MultiCheckGenre) {
                genreName.setText(genre.getTitle());
                icon.setBackgroundResource(((MultiCheckGenre) genre).getIconResId());
            }
            if (genre instanceof SingleCheckGenre) {
                genreName.setText(genre.getTitle());
                icon.setBackgroundResource(((SingleCheckGenre) genre).getIconResId());
            }*/
        }

        @Override
        public void expand() {
            animateExpand();
        }

        @Override
        public void collapse() {
            animateCollapse();
        }

        private void animateExpand() {
            RotateAnimation rotate =
                    new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }

        private void animateCollapse() {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }
    }
}
