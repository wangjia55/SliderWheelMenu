package com.jacob.slider.wheel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jacob-wj on 2015/4/2.
 */
public class MenuFragment extends Fragment {

    public static final String ARGUMENT= "argument";

    public static MenuFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, title);
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        if (getArguments() != null){
            String title = getArguments().getString(ARGUMENT);
            textView.setText(title);
        }
    }
}
