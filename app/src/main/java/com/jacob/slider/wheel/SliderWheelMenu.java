package com.jacob.slider.wheel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jacob-wj on 2015/4/2.
 */
public class SliderWheelMenu extends View{

    private Bitmap mBitmapBackground;
    private Bitmap mBitmapIndicator;
    private Bitmap mBitmapMaskBg;
    private Bitmap mBitmapMask;

    private Rect mRectBackground = new Rect();
    private Rect mRectIndicator = new Rect();
    private Rect mRectMaskBg = new Rect();
    private Rect mRectMask = new Rect();

    private int mCenterXY;

    private boolean hasInit = false;

    public SliderWheelMenu(Context context) {
        this(context, null);
    }

    public SliderWheelMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderWheelMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mBitmapBackground = BitmapFactory.decodeResource(getResources(),R.drawable.roulette_switch_bg);
        mBitmapIndicator = BitmapFactory.decodeResource(getResources(),R.drawable.roulette_indicator);
        mBitmapMaskBg = BitmapFactory.decodeResource(getResources(),R.drawable.roulette_switch_mask_bg);
        mBitmapMask = BitmapFactory.decodeResource(getResources(),R.drawable.roulette_switch_mask);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width,height;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            width = mBitmapBackground.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            height = mBitmapBackground.getHeight();
        }

        int layoutSize = Math.max(width,height);
        mCenterXY = (int) (layoutSize/2.0f);
        setMeasuredDimension(layoutSize,layoutSize);

        if (!hasInit){
            mRectBackground.set(mCenterXY-mBitmapBackground.getWidth()/2,mCenterXY-mBitmapBackground.getHeight()/2,
                    mCenterXY+mBitmapBackground.getWidth()/2,mCenterXY+mBitmapBackground.getHeight()/2);

            mRectIndicator.set(mCenterXY-mBitmapIndicator.getWidth()/2,mCenterXY-mBitmapIndicator.getHeight()/2,
                    mCenterXY+mBitmapIndicator.getWidth()/2,mCenterXY+mBitmapIndicator.getHeight()/2);

            mRectMaskBg.set(mCenterXY-mBitmapMaskBg.getWidth()/2,mCenterXY-mBitmapMaskBg.getHeight()/2,
                    mCenterXY+mBitmapMaskBg.getWidth()/2,mCenterXY+mBitmapMaskBg.getHeight()/2);

            mRectMask.set(mCenterXY-mBitmapMask.getWidth()/2,mCenterXY-mBitmapMask.getHeight()/2,
                    mCenterXY+mBitmapMask.getWidth()/2,mCenterXY+mBitmapMask.getHeight()/2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawBitmap(mBitmapBackground,null,mRectBackground,null);
        canvas.drawBitmap(mBitmapMaskBg,null,mRectMaskBg,null);
        canvas.drawBitmap(mBitmapIndicator,null,mRectIndicator,null);
        canvas.drawBitmap(mBitmapMask,null,mRectMask,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);


    }
}
