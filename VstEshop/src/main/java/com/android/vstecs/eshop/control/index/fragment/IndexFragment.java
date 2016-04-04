package com.android.vstecs.eshop.control.index.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.vstecs.eshop.control.base.BaseFra;


public class IndexFragment extends BaseFra {

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        TextView tv = new TextView(getActivity());
        tv.setText("主页");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                net();
            }
        });

        return tv;
    }

    private void net() {

    }



}
