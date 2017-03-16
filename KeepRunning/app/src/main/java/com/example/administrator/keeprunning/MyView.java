package com.example.administrator.keeprunning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/1/5.
 */

public class MyView extends View {
    Paint mPaint = new Paint();
    RectF mRectF=new RectF();
    int mProgress=0;
    int mAim=100;
    int mPercent;
    static int mPercentold=0;
    public MyView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mPaint.setColor(Color.rgb(0xe9, 0xe9, 0xe9));
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mRectF.left = 100; // 左上角x
        mRectF.top = 100; // 左上角y
        mRectF.right = 400; // 左下角x
        mRectF.bottom = 400; // 右下角y
        canvas.drawArc(mRectF, -90, 360, false, mPaint);
        mPaint.setColor(Color.rgb(0xf8, 0x60, 0x30));
        canvas.drawArc(mRectF, -90, ((float)mProgress/mAim)*360, false, mPaint);
//        String text=mProgress+"步";
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setTextSize(50);
//        canvas.drawText(text,150,300,mPaint);
    }
    public void setPercent(int percent) {

        setCurPercent(percent);


    }
    private void setCurPercent(int percent) {

        mPercent = percent;
        if(mPercent<mPercentold)
        {
            mPercentold=0;
        }
        else if(mPercent>mAim)
            mPercent=mAim;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = mPercentold;i<=mPercent;i++){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mProgress = i;
                    MyView.this.postInvalidate();
                }
            }

        }).start();
        mPercentold=mPercent;

    }
   /* public void setmProgress(int progress)
    {
        mProgress=progress;
    }*/

    public void setmAim(int aim)
    {
        mAim=aim;
    }
}
