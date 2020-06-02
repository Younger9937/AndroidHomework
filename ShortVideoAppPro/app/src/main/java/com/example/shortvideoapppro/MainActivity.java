package com.example.shortvideoapppro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ViewPager2 viewPager2;
    private List<VideoResponse> videoResponses;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewpager2);
        videoResponses = new ArrayList<>();
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {//翻页监听
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                if(position == videoResponses.size()-1){
                    Toast.makeText(getApplicationContext(),"已经到底啦",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state){
                super.onPageScrollStateChanged(state);
            }
        });

        viewPagerAdapter.setOnItemClickListener(new ViewPagerAdapter.ListItemClickListener(){
            @Override
            public void onListItemClick(int clickedItemIndex) {//跳转到视频播放界面
                Intent intent = new Intent(MainActivity.this,VideoPlayer.class);
                intent.putExtra("videoPath", videoResponses.get(clickedItemIndex).feedurl);
                startActivity(intent);
            }
        });

        getData();
    }

    private void getData(){//异步解析网络数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideoInfo().enqueue(new Callback<List<VideoResponse>>() {//收到返回数据
            @Override
            public void onResponse(Call<List<VideoResponse>> call, Response<List<VideoResponse>> response) {
                if(response.body() != null){
                    videoResponses = response.body();
                    Log.d(TAG, videoResponses.toString());
                    if(videoResponses.size() != 0){
                        viewPagerAdapter.setData(response.body());
                        viewPagerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoResponse>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
