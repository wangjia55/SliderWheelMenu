package com.jacob.slider.wheel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


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
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
//                mSliderMenu.setPosition(i);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mSliderMenu = (SliderWheelMenu) findViewById(R.id.sliderMenu);
        mSliderMenu.setOnMenuSelected(new SliderWheelMenu.OnMenuSelected() {
            @Override
            public void menuSelect(int position) {
                mViewPager.setCurrentItem(position,true);
            }
        });

    }


    private class MenuAdapter extends FragmentPagerAdapter{

        public MenuAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return MenuFragment.newInstance("这是Fragment："+i+"");
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
