package com.jacob.slider.wheel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private SliderWheelMenu mSliderMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        MenuAdapter adapter = new MenuAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(changeListener);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mViewPager.setOnPageChangeListener(changeListener);
                return false;
            }
        });

        mSliderMenu = (SliderWheelMenu) findViewById(R.id.sliderMenu);
        mSliderMenu.setOnMenuSelected(new SliderWheelMenu.OnMenuSelected() {
            @Override
            public void menuSelect(int position) {
                mViewPager.setCurrentItem(position,true);
            }
        });
        mSliderMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                         mViewPager.setOnPageChangeListener(null);
                return false;
            }
        });

    }

    private ViewPager.OnPageChangeListener changeListener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageSelected(int i) {
            mSliderMenu.setPosition(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


    private class MenuAdapter extends FragmentPagerAdapter{

        public MenuAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return MenuFragment.newInstance("这是Fragment："+(i+1)+"");
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
