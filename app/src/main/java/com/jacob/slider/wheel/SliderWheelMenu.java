package com.jacob.slider.wheel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jacob-wj on 2015/4/2.
 */
public class SliderWheelMenu extends View {

    private Bitmap mBitmapBackground;
    private Bitmap mBitmapIndicator;
    private Bitmap mBitmapSelectColor;
    private Bitmap mBitmapMaskBg;
    private Bitmap mBitmapMask;
    private Bitmap mBitmapMenuHeart;
    private Bitmap mBitmapMenuMusic;
    private Bitmap mBitmapMenuDisc;
    private Bitmap mBitmapMenuUser;

    private Rect mRectBackground = new Rect();
    private Rect mRectSelectColor= new Rect();
    private Rect mRectIndicator = new Rect();
    private Rect mRectMaskBg = new Rect();
    private Rect mRectMask = new Rect();

    /**
     * 中心点的坐标
     */
    private int mCenterXY;

    private boolean hasInit = false;
    private int mPosition = 0;
    private float mStartAngle = 0;
    private float mTouchAngle = 0;

    /**
     * 每个菜单对应的角度
     */
    private  static float sPerAngle = 360/4.0f;

    private OnMenuSelected mSelectListener;

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
        mBitmapBackground = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_bg);
        mBitmapIndicator = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_indicator);

        mBitmapMaskBg = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_mask_bg);
        mBitmapMask = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_mask);

        mBitmapMenuHeart = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_color_heart);
        mBitmapMenuMusic = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_color_music);
        mBitmapMenuDisc= BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_color_disc);
        mBitmapMenuUser = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_color_user);
        mBitmapSelectColor= mBitmapMenuHeart;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width, height;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mBitmapBackground.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mBitmapBackground.getHeight();
        }

        int layoutSize = Math.max(width, height);
        mCenterXY = (int) (layoutSize / 2.0f);
        setMeasuredDimension(layoutSize, layoutSize);

        if (!hasInit) {
            mRectBackground.set(mCenterXY - mBitmapBackground.getWidth() / 2,
                    mCenterXY - mBitmapBackground.getHeight() / 2,
                    mCenterXY + mBitmapBackground.getWidth() / 2,
                    mCenterXY + mBitmapBackground.getHeight() / 2);

            mRectSelectColor.set(mCenterXY - mBitmapSelectColor.getWidth() / 2,
                    mCenterXY - mBitmapSelectColor.getHeight() / 2,
                    mCenterXY + mBitmapSelectColor.getWidth() / 2,
                    mCenterXY + mBitmapSelectColor.getHeight() / 2);

            mRectIndicator.set(mCenterXY - mBitmapIndicator.getWidth() / 2,
                    mCenterXY - mBitmapIndicator.getHeight() / 2,
                    mCenterXY + mBitmapIndicator.getWidth() / 2,
                    mCenterXY + mBitmapIndicator.getHeight() / 2);

            mRectMaskBg.set(mCenterXY - mBitmapMaskBg.getWidth() / 2,
                    mCenterXY - mBitmapMaskBg.getHeight() / 2,
                    mCenterXY + mBitmapMaskBg.getWidth() / 2,
                    mCenterXY + mBitmapMaskBg.getHeight() / 2);

            mRectMask.set(mCenterXY - mBitmapMask.getWidth() / 2,
                    mCenterXY - mBitmapMask.getHeight() / 2,
                    mCenterXY + mBitmapMask.getWidth() / 2,
                    mCenterXY + mBitmapMask.getHeight() / 2);
            hasInit = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.save();
        canvas.drawBitmap(mBitmapBackground, null, mRectBackground, null);
        canvas.drawBitmap(mBitmapSelectColor, null, mRectSelectColor, null);
        canvas.drawBitmap(mBitmapMaskBg, null, mRectMaskBg, null);
        canvas.rotate( mTouchAngle, mCenterXY, mCenterXY);
        canvas.drawBitmap(mBitmapIndicator, null, mRectIndicator, null);
        canvas.restore();
        canvas.drawBitmap(mBitmapMask, null, mRectMask, null);
    }

    private float mLastX;
    private float mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                 float startAngle = getAngle(mLastX,mLastY);
                 float endAngle = getAngle(x,y);

                int quadrant = getQuadrant(x,y);
                switch (quadrant){
                    case 1:
                    case 4:
                        mTouchAngle += endAngle-startAngle;
                        break;
                    case 2:
                    case 3:
                        mTouchAngle += startAngle-endAngle;
                        break;
                }
                //对角度范围进行处理
                mTouchAngle = mTouchAngle%360;

                int pos = getPositionWhenRotate(mTouchAngle);
                mBitmapSelectColor = getBitmapByPosition(pos);
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int position = getPositionWhenRotate(mTouchAngle);

                int index = (int) (mTouchAngle/45.0f);
                if (index % 2== 0 ){
                    mTouchAngle = index*45;
                }else{
                    if (index>=0){
                        mTouchAngle = (index+1)*45;
                    }else{
                        mTouchAngle = (index - 1) * 45;
                    }
                }
                if (mSelectListener != null){
                    mSelectListener.menuSelect(position);
                    mPosition =position;
                }
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 根据当期的位置给出选中菜单的背景
     */
    private Bitmap getBitmapByPosition(int pos) {
        if (pos<0){
            pos = 4+pos;
        }
        switch (pos){
            case 0:
                return  mBitmapMenuHeart;
            case 1:
                return  mBitmapMenuMusic;
            case 2:
                return  mBitmapMenuDisc;
            case 3:
                return  mBitmapMenuUser;
        }
        return mBitmapMenuHeart;
    }

    /**
     * 当在旋转的过程中获得当前的位置
     * 如果顺时针旋转角度超过45度就直接进入下一个菜单
     * 反之，还是回到原来的菜单位置
     */
    private int getPositionWhenRotate(float touchAngle) {
        int index = (int) (touchAngle/45.0f);
        if (index % 2== 0 ){
            touchAngle = index*45;
        }else{
            if (index>=0){
                touchAngle = (index+1)*45;
            }else{
                touchAngle = (index - 1) * 45;
            }
        }
        int position = (int) (touchAngle/sPerAngle);
        if (position<=0){
            return  Math.abs(position);
        }else{
            return 4-position;
        }
    }


    /**
     * 获取触摸点所在的象限
     */
    private int getQuadrant(float touchX, float touchY) {
        float deltaX = touchX - mCenterXY;
        float deltaY = touchY - mCenterXY;
        if (deltaX>=0){
            return  deltaY>=0?4:1;
        }else{
            return deltaY>=0?3:2;
        }
    }

    /**
     * 获取触摸点的角度
     */
    private float getAngle(float x ,float y ){
        float deltaX = x-mCenterXY;
        float deltaY = y-mCenterXY;
        float distance = (float) Math.sqrt(deltaX*deltaX+deltaY*deltaY);
        return (float) (Math.asin(deltaY/distance)*180/Math.PI);
    }

    /**
     * 外部可以指定需要的菜单
     */
    public void setPosition(int position){
        mBitmapSelectColor = getBitmapByPosition(position);
        if (position>=mPosition){
            mTouchAngle = -position*sPerAngle;
        }else{
            mTouchAngle = position*sPerAngle;
        }
        invalidate();
    }

    /**
     * 菜单按钮滑动的事件
     */
    public interface OnMenuSelected {
        void menuSelect(int position);
    }

    public void setOnMenuSelected(OnMenuSelected listener) {
        this.mSelectListener = listener;
    }
}
