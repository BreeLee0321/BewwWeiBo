package bree.com.bewwweibo.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bree.com.bewwweibo.R;
import bree.com.bewwweibo.entities.StatusEntity;
import bree.com.bewwweibo.utils.TimeFormatUtils;

/**
 * Created by bree on 2017/5/10.
 */

public class HomePageAdapter extends RecyclerView.Adapter{
    private List<StatusEntity> list;
    private OnItemClickListener onItemClickListener;

    public HomePageAdapter(List<StatusEntity> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weibo_content, parent, false);
        return new HomePageHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomePageHolder){
            HomePageHolder homePageHolder= (HomePageHolder) holder;
            StatusEntity entity=list.get(position);
            ((HomePageHolder) holder).tvUserName.setText(entity.user.screen_name);
            homePageHolder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
            homePageHolder.tvContent.setText(entity.text);
            homePageHolder.tvSource.setText(Html.fromHtml(entity.source).toString());
            StatusEntity reStarus=entity.retweeted_status;
            if (null!=reStarus){
                homePageHolder.tvReContent.setText(reStarus.text);
                homePageHolder.llRe.setVisibility(View.VISIBLE);
            }else {
                homePageHolder.llRe.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HomePageHolder extends RecyclerView.ViewHolder{
        private ImageView ivHeader;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvSource;
        private TextView tvContent;
        private TextView tvReContent;
        private LinearLayout llRe;
        private ImageView ivContent, ivReContent;
        private TextView tvRetween,tvComment,tvLike;
        public HomePageHolder(View itemView) {
            super(itemView);
            initialize(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,getLayoutPosition());
                }
            });

        }

        private void initialize(View v) {

            ivHeader = (ImageView) v.findViewById(R.id.ivHeader);
            tvUserName = (TextView) v.findViewById(R.id.tvUserName);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
            tvSource = (TextView) v.findViewById(R.id.tvSource);
            tvContent = (TextView) v.findViewById(R.id.tvContent);
            tvReContent = (TextView) v.findViewById(R.id.tvReContent);
            llRe = (LinearLayout) v.findViewById(R.id.llRe);
            ivContent = (ImageView) v.findViewById(R.id.ivContent);
            ivReContent = (ImageView) v.findViewById(R.id.ivReContent);
//            tvRetween = (TextView) v.findViewById(R.id.tvRetweet);
//            tvComment = (TextView) v.findViewById(R.id.tvComment);
//            tvLike = (TextView) v.findViewById(R.id.tvLike);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }
}
