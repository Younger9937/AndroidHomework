package com.example.shortvideoapppro;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayer extends AppCompatActivity {

    private VideoView videoView;
    private static final String TAG = "VideoPlayer";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Toast.makeText(VideoPlayer.this, "双击可点赞哦", Toast.LENGTH_SHORT).show();

        videoView = findViewById(R.id.VideoView);

        Intent intent = getIntent();
        final String videoPath = intent.getStringExtra("videoPath");
        videoView.setVideoPath(videoPath);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(//使视频宽度布满屏幕，高度根据宽度缩放
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        videoView.setLayoutParams(layoutParams);

        videoView.setOnTouchListener(new MyClickListener(new MyClickListener.MyClickCallBack() {
            @Override
            public void oneClick() {//监测到单击事件
                if(videoView.isPlaying()){
                    Log.d(TAG,"isPlaying");
                    videoView.pause();
                    toastRest();
                }
                else{
                    Log.d(TAG,"notPlaying");
                    videoView.start();
                }
            }

            @Override
            public void doubleClick() {
                Log.d(TAG,"双击");
            }
        }));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
    }

    public void toastRest(){//视频暂停时，弹出休息一下的消息
        Toast toast=new Toast(getApplicationContext());
        LayoutInflater inflater = LayoutInflater.from(VideoPlayer.this);//用于填充Toast
        View view =inflater.inflate(R.layout.toast,null);//填充物为xml文件，将xml转为View

        toast.setView(view);//将填充物放入Toast
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}

