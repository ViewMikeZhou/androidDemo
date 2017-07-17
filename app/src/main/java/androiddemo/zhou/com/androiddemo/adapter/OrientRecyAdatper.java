package androiddemo.zhou.com.androiddemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androiddemo.zhou.com.androiddemo.R;
import androiddemo.zhou.com.androiddemo.view.BottomDialog;

/**
 * Created by Administrator on 2017/7/14.
 */

public class OrientRecyAdatper extends RecyclerView.Adapter {

    private final List<Integer> imgsRed;
    private final List<Integer> selectPostion;  //会显被选中的位置
    private List<Integer> imgs;
    private float mItemWidth;
    private List<Integer> addPosition =new ArrayList<>();  //选中的view
    OnselectPositionListen mOnSelectPositionListen;

    public void setOnSelect(OnselectPositionListen onSelect) {
        this.mOnSelectPositionListen = onSelect;
    }

    public OrientRecyAdatper(List<Integer> imgs, List<Integer> imgsRed, float mimgWidth, BottomDialog bottomDialog, List<Integer> selectPostion) {
        this.imgs = imgs;
        this.imgsRed = imgsRed;
        this.mItemWidth = mimgWidth;
        this.selectPostion = selectPostion;
        bottomDialog.setOndialogDissmissed(new BottomDialog.OndialogDissmissed() {
            @Override
            public void dialogDismiss() {
                if (mOnSelectPositionListen != null){
                    mOnSelectPositionListen.onselect(addPosition);
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orient_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mImg.setImageResource(imgs.get(position));
        if (addPosition.size() != 0){
            for (Integer integer : addPosition) {
                if (integer ==position ){
                    viewHolder.mImg.setImageResource(imgsRed.get(position));
                    viewHolder.mImg.setSelected(true);
                }
            }
        }
        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addPosition.size() != 0){
                    if (addPosition.contains(position)){
                        ImageView v1 = (ImageView) v;
                        addPosition.remove((Object)position);
                        v.setSelected(false);
                        v1.setImageResource(imgs.get(position));
                        return;
                    }
                }
                if (!v.isSelected()){
                    ImageView v1 = (ImageView) v;
                    v1.setImageResource(imgsRed.get(position));
                    v.setSelected(true);
                    addPosition.add(position);
                }else{
                    ImageView v1 = (ImageView) v;
                    v1.setImageResource(imgs.get(position));
                    v.setSelected(false);
                    addPosition.remove((Object)position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.orient_img);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mImg.getLayoutParams();
            layoutParams.width = (int) mItemWidth;
            layoutParams.gravity = Gravity.CENTER;
            mImg.setLayoutParams(layoutParams);
        }
    }
    public interface OnselectPositionListen {
        void onselect(List<Integer> addPosition);
      //  void onremove(List<Integer> removePostion);
    }
}
