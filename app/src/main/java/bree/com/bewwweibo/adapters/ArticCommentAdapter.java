package bree.com.bewwweibo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bree.com.bewwweibo.R;
import bree.com.bewwweibo.activities.PhotoViewActivity;
import bree.com.bewwweibo.activities.RePostActivity;
import bree.com.bewwweibo.entities.CommentEntity;
import bree.com.bewwweibo.entities.PicUrlsEntity;
import bree.com.bewwweibo.entities.StatusEntity;
import bree.com.bewwweibo.networks.ParameterKeySet;
import bree.com.bewwweibo.utils.CircleTransform;
import bree.com.bewwweibo.utils.RichTextUtils;
import bree.com.bewwweibo.utils.TimeFormatUtils;

/**
 * Created by bree on 2017/5/24.
 */

public class ArticCommentAdapter extends RecyclerView.Adapter {
    private final int VIEW_HEADER = 0;
    private final int VIEW_ITEM = 1;
    private StatusEntity statusEntity;
    private Context context;
    private List<CommentEntity> mList;

    public ArticCommentAdapter(Context context, StatusEntity statusEntity,List<CommentEntity> list) {
        this.context = context;
        this.statusEntity = statusEntity;
        this.mList=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view;
        if (viewType == VIEW_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weibo_content, parent, false);
            viewHolder=new HomePageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weibo_comment,parent,false);
            viewHolder = new CommonViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomePageHolder) {
           HomePageHolder homePageHolder = (HomePageHolder) holder;
            final StatusEntity entity = statusEntity;
            homePageHolder.tvUserName.setText(entity.user.screen_name);
            homePageHolder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
            homePageHolder.tvContent.setText(RichTextUtils.getRichText(context, entity.text));
            homePageHolder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            homePageHolder.tvSource.setText(Html.fromHtml(entity.source).toString());

            Glide.with(context).load(entity.user.profile_image_url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_default_header).transform(new CircleTransform(context)).into(homePageHolder.ivHeader);

            homePageHolder.ivHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PhotoViewActivity.class);
                    intent.putExtra("Photo_url",entity.user.profile_image_url);
                    context.startActivity(intent);

                }
            });

            List<PicUrlsEntity> pics = entity.pic_urls;
            if (null != pics && pics.size() > 0) {
                final PicUrlsEntity pic = pics.get(0);
                pic.original_pic = pic.thumbnail_pic.replace("thumbnail", "large");
                pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail", "bmiddle");
                homePageHolder.ivContent.setVisibility(View.VISIBLE);
                Glide.with(context).load(pic.bmiddle_pic).into(homePageHolder.ivContent);
                homePageHolder.ivContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, PhotoViewActivity.class);
                        intent.putExtra("Photo_url",pic.original_pic);
                        context.startActivity(intent);

                    }
                });
            } else {
                homePageHolder.ivContent.setVisibility(View.GONE);
            }
            final StatusEntity reStatus = entity.retweeted_status;
            String reContent="";
            if (reStatus != null) {
                try {
                    reContent = "@" + reStatus.user.screen_name + ":" + reStatus.text;
                } catch (Exception e) {
                    reContent= reStatus.text;
                }

                homePageHolder.tvReContent.setText(RichTextUtils.getRichText(context, reContent));
                homePageHolder.tvReContent.setMovementMethod(LinkMovementMethod.getInstance());
                homePageHolder.llRe.setVisibility(View.VISIBLE);
                List<PicUrlsEntity> rePics = reStatus.pic_urls;
                if (null!=rePics && rePics.size() > 0) {
                    final PicUrlsEntity pic = rePics.get(0);
                    pic.original_pic = pic.thumbnail_pic.replace("thumbnail", "large");
                    pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail", "bmiddle");
                    homePageHolder.ivReContent.setVisibility(View.VISIBLE);
                    Glide.with(context).load(pic.bmiddle_pic).into(homePageHolder.ivReContent);
                    homePageHolder.ivReContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, PhotoViewActivity.class);
                            intent.putExtra("Photo_url",pic.original_pic);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    homePageHolder.ivReContent.setVisibility(View.GONE);
                }
            } else {
                homePageHolder.llRe.setVisibility(View.GONE);
            }
            homePageHolder.tvComment.setText(entity.comments_count + "");
            homePageHolder.tvLike.setText(entity.attitudes_count + "");
            homePageHolder.tvRetween.setText(entity.reposts_count + "");


        }
        if(holder instanceof CommonViewHolder){
            CommonViewHolder commonViewHolder = (CommonViewHolder) holder;
            CommentEntity commentEntity = mList.get(position-1);
            Glide.with(context).load(commentEntity.user.profile_image_url).transform(new CircleTransform(context)).into(commonViewHolder.ivHeader);
            commonViewHolder.tvComment.setText(commentEntity.text);
            commonViewHolder.tvUserName.setText(commentEntity.user.screen_name);
            commonViewHolder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(commentEntity.created_at));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_HEADER : VIEW_ITEM;

    }

    class HomePageHolder extends RecyclerView.ViewHolder {
            private ImageView ivHeader;
            private TextView tvUserName;
            private TextView tvTime;
            private TextView tvSource;
            private TextView tvContent;
            private TextView tvReContent;
            private LinearLayout llRe;
            private ImageView ivContent, ivReContent;
            private TextView tvRetween, tvComment, tvLike;

            public HomePageHolder(View itemView) {
                super(itemView);
                initialize(itemView);

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
                tvRetween = (TextView) v.findViewById(R.id.tvRetweet);
                tvComment = (TextView) v.findViewById(R.id.tvComment);
                tvLike = (TextView) v.findViewById(R.id.tvLike);
            }
        }

    class CommonViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHeader;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvComment;

        public CommonViewHolder(View itemView) {
            super(itemView);
            initialize(itemView);
        }

        private void initialize(View view) {

            ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvComment = (TextView) view.findViewById(R.id.tvComment);
        }
    }
}
