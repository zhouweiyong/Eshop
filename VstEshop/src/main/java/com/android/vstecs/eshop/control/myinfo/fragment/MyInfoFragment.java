package com.android.vstecs.eshop.control.myinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.vstecs.eshop.control.base.BaseFra;


public class MyInfoFragment extends BaseFra {

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		TextView tv = new TextView(getActivity());
		tv.setText("我的");
		return tv;
	}

}
