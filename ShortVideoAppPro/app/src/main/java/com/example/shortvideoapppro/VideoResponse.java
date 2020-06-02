package com.example.shortvideoapppro;

public class VideoResponse {

    public String feedurl;//视频url
    public String nickname;//用户昵称
    public String description;//视频简介
    public String avatar;//用户头像
    public int likecount;//点赞数

    @Override
    public String toString() {
        return "VideoInfo{" +
                "feedurl=" + feedurl +
                ", nickname='" + nickname  +
                ", description=" + description +
                ", likecount=" + likecount +
                ", avatar=" + avatar +
                '}';
    }

}
