package com.example.shortvideoapppro;

import java.util.List;
        import retrofit2.Call;
        import retrofit2.http.GET;

public interface ApiService {//GET网络请求
    @GET("api/invoke/video/invoke/video")
    Call<List<VideoResponse>> getVideoInfo();
}
