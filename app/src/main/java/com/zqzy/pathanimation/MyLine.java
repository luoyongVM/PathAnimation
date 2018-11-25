package com.zqzy.pathanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * 2017/2/16 0016  下午 10:02.
 */

public class MyLine extends View {
    private Paint mPaint;
    private Path mPath;
    private PathMeasure pathMeasure;
    //path路径的总长度
    private int mLenght;
    //当前路径的长度
    private int mCurrentPath;
    private float[] currentPosition;
    private Bitmap bitmap1;


    private float pointX[] = new float[3];
    private float pointY[] = new float[3];
    public MyLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPath = new Path();
        currentPosition = new float[2];
        bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.loading_icon_orange);


        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStyle(Paint.Style.STROKE);
        //初始化我们的五角星五个点坐标
        pointX[0] = 188f;pointY[0] = 430f;
        pointX[1] = 396f;pointY[1] = 76.4f;
        pointX[2] = 604f;pointY[2] = 430f;
        //将mPath移动到第一个点作为起点
        mPath.moveTo(pointX[0], pointY[0]);
        for(int i=1;i<3;i++) {
            mPath.lineTo(pointX[i],pointY[i]);
        }
        mPath.lineTo(pointX[0],pointY[0]);
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(mPath,true);
        mLenght = (int) pathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        if(mCurrentPath == 0 && currentPosition[0]==0 && currentPosition[1]==0){
            startAnimator();
        }else{
            canvas.drawBitmap(bitmap1,currentPosition[0]-bitmap1.getWidth()/2,
                    currentPosition[1]-bitmap1.getHeight()/2,mPaint);

        }
    }

    /**
     * 计算每次的位置
     */
    private void startAnimator(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mLenght);
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPath = (int) animation.getAnimatedValue();
                pathMeasure.getPosTan(mCurrentPath, currentPosition, null);
                invalidate();
            }
        });
        valueAnimator.start();
    }
}