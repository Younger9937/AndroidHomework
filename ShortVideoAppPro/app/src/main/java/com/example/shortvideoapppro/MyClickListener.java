package com.example.shortvideoapppro;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class MyClickListener implements View.OnTouchListener{
    private static int timeout=400;//双击的延时
    private int clickCount = 0;
    private Handler handler;
    private MyClickCallBack myClickCallBack;

    public interface MyClickCallBack{
        void oneClick();//点击一次的回调
        void doubleClick();//连续点击两次的回调
    }

    public MyClickListener(MyClickCallBack myClickCallBack) {
        this.myClickCallBack = myClickCallBack;
        handler = new Handler();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            clickCount++;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (clickCount == 1) {//单击
                        myClickCallBack.oneClick();
                    }else {//双击
                        myClickCallBack.doubleClick();
                    }
                    handler.removeCallbacksAndMessages(null);
                    clickCount = 0;//计数清零
                }
            },timeout);//延时timeout后执行run方法中的代码
        }
        return false;
    }
}