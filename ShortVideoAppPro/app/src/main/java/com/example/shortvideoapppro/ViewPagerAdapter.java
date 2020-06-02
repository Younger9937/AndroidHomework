package com.example.shortvideoapppro;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import java.math.BigDecimal;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.VideoViewHolder> {

    private static final String TAG = "ViewPagerAdapter";
    private List<VideoResponse> videoInfos;
    private Context mContext;
    private ListItemClickListener mOnClickListener;

    public ViewPagerAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.video_item, viewGroup, false);
        VideoViewHolder vh = new VideoViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoViewHolder holder, final int position) {
        Log.d(TAG, String.valueOf(position));

        String CoverPath = videoInfos.get(position).feedurl;
        String HeadPhotoPath = videoInfos.get(position).avatar;

        Glide.with(mContext).asBitmap()//设置封面大小
                .load(CoverPath)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition){
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        if(height > width){//布满屏幕
                            holder.ImageCover.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        else{//置于屏幕中央
                            holder.ImageCover.setScaleType(ImageView.ScaleType.CENTER);
                        }
                    }
                });

        //加载视频第一帧作为封面图
        Glide.with(mContext).setDefaultRequestOptions(new RequestOptions()
                .frame(1000000).fitCenter().error(R.mipmap.failure).placeholder(R.mipmap.loading))
                .load(CoverPath)
                .into(holder.ImageCover);

        //加载圆形头像
        Glide.with(mContext).load(HeadPhotoPath)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.ImageHeadPhoto);

        holder.PlayPhoto.setAlpha(0.5f);//设置透明度

        holder.Nickname.setText("@" + videoInfos.get(position).nickname);
        TextPaint tp = holder.Nickname.getPaint();
        tp.setFakeBoldText(true);//加粗

        holder.Description.setText(videoInfos.get(position).description);

        int likeNum = videoInfos.get(position).likecount;
        if(likeNum < 10000){
            holder.LikeNum.setText(String.valueOf(likeNum));
        }
        else{//以w为单位
            float likeNumThous = (float)likeNum/10000;
            BigDecimal bigDecimal = new BigDecimal(likeNumThous);
            double newlikeNum = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//保留一位小数
            holder.LikeNum.setText(Double.toString(newlikeNum)+"w");
        }

        if(mOnClickListener != null){
            holder.ImageCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onListItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return videoInfos == null ? 0:videoInfos.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        public ImageView ImageCover;
        public TextView Nickname;
        public TextView Description;
        public CircleImageView PlayPhoto;
        public ImageView ImageHeadPhoto;
        public TextView LikeNum;

        public VideoViewHolder(View videoView){
            super(videoView);
            ImageCover = videoView.findViewById(R.id.ImageCover);
            Description = videoView.findViewById(R.id.Description);
            Nickname = videoView.findViewById(R.id.Nickname);
            PlayPhoto = videoView.findViewById(R.id.ImagePlay);
            ImageHeadPhoto = videoView.findViewById(R.id.ImageHeadPhoto);
            LikeNum = videoView.findViewById(R.id.LikeNum);
        }
    }

    public void setData(List<VideoResponse> responses){
        videoInfos = responses;
        Log.d(TAG,videoInfos.toString());
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public void setOnItemClickListener(ListItemClickListener listItemClickListener){
        this.mOnClickListener = listItemClickListener;
    }
}
