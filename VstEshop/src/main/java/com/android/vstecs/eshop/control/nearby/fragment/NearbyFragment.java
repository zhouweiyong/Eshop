package com.android.vstecs.eshop.control.nearby.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.vstecs.eshop.control.base.BaseFra;


public class NearbyFragment extends BaseFra {

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		TextView tv = new TextView(getActivity());
		tv.setText("消息");
		return tv;
	}

}
