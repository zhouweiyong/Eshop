package com.android.vstecs.eshop.control.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;


import com.android.vstecs.eshop.R;
import com.vstecs.android.funframework.ui.fragment.BaseFragment;
import com.vstecs.android.uiframework.view.LoadingDialog;
import com.vstecs.android.uiframework.view.jazzyviewpage.ToastMsg;
import com.vstecs.android.uiframework.view.overlay.OverlayLayout;

import java.util.HashSet;
import java.util.Set;


public abstract class BaseFra extends BaseFragment implements OnCancelListener, OnClickListener{
	/**
	 * 嵌套viewpager时，第一次显示回调
	 * @author fanxing 创建于 Dec 3, 2014
	 */
	public interface OnInitShowListener{
		void onInitShow();
	}
	protected View netErrorView;
	
	
	/** 分页加载数据，每页数据量 */
	protected static final int PAGE_SIZE = 10;
	/** 当前页，用于分页加载数据 */
	protected int CURRENT_PAGE = 1;
	
	protected LoadingDialog loadingDialog;
	protected OverlayLayout mOverlayLayout;
	
	protected void initLoadingDialog(boolean isTrans){
		if (null == loadingDialog) {
			loadingDialog = new LoadingDialog(getActivity(), isTrans);
			loadingDialog.setOnCancelListener(this);
		}
	}
	
	public void showLoadingDialog(String parameter) {
		initLoadingDialog(true);//透明
		loadingDialog.setTitle(parameter);
		if(!loadingDialog.isShowing())
			loadingDialog.show();
	}
	
	/**
	 * show to @param(cls)，but can't finish activity
	 */
	public void showActivity(Activity aty, Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(aty, cls);
		aty.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
	}

	/**
	 * show to @param(cls)，but can't finish activity
	 */
	public void showActivity(Activity aty, Intent it) {
		aty.startActivity(it);
		getActivity().overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
	}

	/**
	 * show to @param(cls)，but can't finish activity
	 */
	public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(aty, cls);
		aty.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {}

	public void dismissLoadingDialog() {
		if (null != loadingDialog) {
			LoadingDialog.dismissDialog(loadingDialog);
		}
	}
	
	public void showToastMsg(String msg) {
		ToastMsg.showToastMsg(getActivity(), msg);
	}

	public void showToastMsg(int strId) {
		ToastMsg.showToastMsg(getActivity(), strId);
	}
	
	private Set<Object> tags = new HashSet<Object>();
//	protected void executeRequest(Request request){
//		VolleyRequestManager.addRequest(request, this);
//		tags.add(this);
//	}
//	/**
//	 * 添加网络请求
//	 * @param request
//	 */
//	protected void executeRequest(Request request, Object tag){
//		VolleyRequestManager.addRequest(request, tag);
//		tags.add(tag);
//	}
//	/**
//	 * 取消网络请求
//	 * @param tag
//	 */
//	protected void cancelRequest(Object tag){
//		VolleyRequestManager.cancelAll(tag);
//	}
	
//	@Override
//	public void onStop() {
//		super.onStop();
//		for (Object tag : tags) {
//			VolleyRequestManager.cancelAll(tag);
//		}
//	}
	
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
	}
	
	//默认网络请求异常回调
//	@Override
//	public void onErrorResponse(VolleyError error) {
//		dismissLoadingDialog();
//
//		String msg = "网络异常";
//		if(error instanceof ServerFlagError){
//			msg = ((ServerFlagError)error).result.msg;
//		}else{
//			try {
//				if (error.networkResponse != null && error.networkResponse.data != null)
//					msg = new String(error.networkResponse.data, "utf-8");
//			}catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//		showToastMsg(msg);
//	}
	
	/**要在Fragment中监听按键需要实现该接口*/
	public interface FragmentKeyDown{
		boolean onFraKeyDown(int keyCode, KeyEvent event);
	}
	

	/**覆盖页 end******************************/
	
	/* 获取输入内容 */
	public String getInputStr(EditText et) {
		return et.getText().toString().trim();
	}
	/**
	 * @Description:关闭键盘事件
	 * @author: 刘成伟（wwwlllll@126.com）
	 * @Title: closeInput
	 * @param
	 * @return void
	 * @throws
	 * @date 2014-4-6 上午11:18:36
	 */
	public void closeInput() {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null && getActivity().getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}



	private OverlayLayout loadingOverlay,emptyOverlay,netErrorOverlay;
	private ImageView loadIv;
	protected void showOverLoading(ViewGroup viewGroup){
		if (loadingOverlay==null){
			loadingOverlay = new OverlayLayout(getActivity());
			View loadView =loadingOverlay.setOverlayView(R.layout.common_load_view);
			loadingOverlay.attachTo(viewGroup);
			loadingOverlay.setIsHideTargetViewWhenOverlayShown(true);
			loadIv = (ImageView) loadView.findViewById(R.id.loadingImageView);
			loadingOverlay.hideOverlay();
			loadingOverlay.showOverlay();
		}else{
			loadingOverlay.showOverlay();
		}
		RotateAnimation mAnim = new RotateAnimation(360, 0, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
		mAnim.setDuration(1000);
		mAnim.setRepeatCount(Animation.INFINITE);// 重复次数
		mAnim.setRepeatMode(Animation.RESTART);
		mAnim.setStartTime(Animation.START_ON_FIRST_FRAME);
		mAnim.setInterpolator(new LinearInterpolator());// 旋转不停顿
		loadIv.startAnimation(mAnim);
	}

	protected void hideOverLoading(){
		if (loadingOverlay!=null){
			loadingOverlay.hideOverlay();
			loadIv.clearAnimation();
		}
	}

	protected void showEmptyView(ViewGroup viewGroup,int emptyLayout){
		if (emptyOverlay==null){
			emptyOverlay = new OverlayLayout(getActivity());
			emptyOverlay.setOverlayView(emptyLayout);
			emptyOverlay.attachTo(viewGroup);
			emptyOverlay.setIsHideTargetViewWhenOverlayShown(true);
			emptyOverlay.hideOverlay();
			emptyOverlay.showOverlay();
		}else {
			emptyOverlay.showOverlay();
		}
	}

	protected void hideEmptyView(){
		if (emptyOverlay!=null){
			emptyOverlay.hideOverlay();
		}
	}



	protected void setNetErrorView(ViewGroup viewGroup,OnClickListener reloadListener){
		if (netErrorOverlay==null){
			netErrorOverlay = new OverlayLayout(getActivity());
			View netErrorView =netErrorOverlay.setOverlayView(R.layout.common_load_error);
			netErrorOverlay.attachTo(viewGroup);
			netErrorOverlay.setIsHideTargetViewWhenOverlayShown(true);
			netErrorView.findViewById(R.id.click_reload).setOnClickListener(reloadListener);
			netErrorView.findViewById(R.id.click_checknetwork).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 检查网络设置
					Intent intent = new Intent(Settings.ACTION_SETTINGS);
					startActivity(intent);
				}
			});

			netErrorOverlay.hideOverlay();
			netErrorOverlay.showOverlay();
		}else {
			netErrorOverlay.showOverlay();
		}
	}

	protected void hideNetErrorView(){
		if (netErrorOverlay!=null){
			netErrorOverlay.hideOverlay();
		}
	}
	
}
