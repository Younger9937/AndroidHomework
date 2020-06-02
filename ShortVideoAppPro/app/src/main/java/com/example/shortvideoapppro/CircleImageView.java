package com.example.shortvideoapppro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class CircleImageView extends AppCompatImageView {//自定义View
    private Paint mPaint;//画笔
    private int mRadius;//半径
    private float mScale;//图片的缩放比例

    public CircleImageView(Context context) {
        super(context);
    }
    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//测量计算圆的半径
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = size / 2;
        setMeasuredDimension(size, size);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();//将图片转为bitmap位图
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mScale = (mRadius * 2.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth()); //计算缩放比例

            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            bitmapShader.setLocalMatrix(matrix);//缩放bitmapShader

            //使用画笔结合bitmapShader在画布上画一个圆形
            mPaint.setShader(bitmapShader);
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        }
        else {
            super.onDraw(canvas);
        }
    }
}
