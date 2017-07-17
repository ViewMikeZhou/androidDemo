package androiddemo.zhou.com.androiddemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */

public class Myadatper extends RecyclerView.Adapter<Myadatper.holder> {
    private  List<String> list;
    public OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public Myadatper(List<String> list) {
        this.list=list;
    }

    @Override
    public Myadatper.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder holder = new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(final Myadatper.holder holder, final int position) {
       holder.mTv.setOnClickListener((v) ->
               {if (mOnItemClick != null){
                   mOnItemClick.click(list.get(position));
               }});
        holder.mTv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list ==null? 0 : list.size();
    }
    static class holder extends RecyclerView.ViewHolder {

        public   TextView mTv;

        public holder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv);
        }
    }



    public interface OnItemClick{
        void click(String messaga);
    }
}
