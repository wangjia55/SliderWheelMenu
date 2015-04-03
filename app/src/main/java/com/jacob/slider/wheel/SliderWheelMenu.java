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

    private Rect mRectBackground = new Rect();
    private Rect mRectSelectColor= new Rect();
    private Rect mRectIndicator = new Rect();
    private Rect mRectMaskBg = new Rect();
    private Rect mRectMask = new Rect();

    private int mCenterXY;

    private boolean hasInit = false;

    private float mStartAngle = 0;
    private float mTouchAngle = 0;

    private int mPosition = 0;

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
        mBitmapSelectColor= BitmapFactory.decodeResource(getResources(), getResourceByPosition(mPosition));
        mBitmapMaskBg = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_mask_bg);
        mBitmapMask = BitmapFactory.decodeResource(getResources(), R.drawable.roulette_switch_mask);
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
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.save();
        canvas.drawBitmap(mBitmapBackground, null, mRectBackground, null);
        canvas.drawBitmap(mBitmapSelectColor, null, mRectSelectColor, null);
        canvas.drawBitmap(mBitmapMaskBg, null, mRectMaskBg, null);
        canvas.rotate(mStartAngle + mTouchAngle, mCenterXY, mCenterXY);
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
                Log.e("TAG-angle",""+mTouchAngle);
                //对角度范围进行处理
                mTouchAngle = mTouchAngle%360;
//                int index = getPistionWhenRotate();
//                mBitmapSelectColor = BitmapFactory.decodeResource(getResources(),getResourceByPosition(index));
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int position = getPistionWhenRotate();
                mTouchAngle = position*sPerAngle;
                Log.e("TAG-position", "" + position);

                break;
        }
        invalidate();
        return true;
    }

    private int getPistionWhenRotate() {
        int position = getPositionByAngle();
        if (mTouchAngle > 0){
            float angle = mTouchAngle%sPerAngle;
            if (angle>=45){
                position++;
            }
            mTouchAngle = position*sPerAngle;
        }else{
            float angle = mTouchAngle%sPerAngle;
            if (angle<=-45){
                position--;
            }
        }
        return position;
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
     * 根据当前的指示的index给出对应的资源文件id
     */
    private int getResourceByPosition(int position){
        switch (position){
            case 0:
                return R.drawable.roulette_switch_color_heart;
            case 1:
                return R.drawable.roulette_switch_color_music;
            case 2:
                return R.drawable.roulette_switch_color_disc;
            case 3:
                return R.drawable.roulette_switch_color_user;
            default:
                return R.drawable.roulette_switch_color_heart;
        }
    }

    private int getPositionByAngle(){
        int position = (int) ((mTouchAngle+mStartAngle)/sPerAngle);
        return position;
    }

    public void setOnMenuSelected(OnMenuSelected listener) {
        this.mSelectListener = listener;
    }

    public interface OnMenuSelected {
        void menuSelect(int position);
    }
}
