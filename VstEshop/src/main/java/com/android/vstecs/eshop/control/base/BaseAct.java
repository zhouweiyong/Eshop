package com.android.vstecs.eshop.control.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;


import com.android.vstecs.eshop.R;
import com.vstecs.android.funframework.ui.activity.BaseActivity;
import com.vstecs.android.uiframework.view.LoadingDialog;
import com.vstecs.android.uiframework.view.jazzyviewpage.ToastMsg;
import com.vstecs.android.uiframework.view.overlay.OverlayLayout;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseAct extends BaseActivity implements
        OnCancelListener {
    /**
     * 分页加载数据，每页数据量
     */
    public final int PAGE_SIZE = 10;
    /**
     * 当前页，用于分页加载数据
     */
    public int CURRENT_PAGE = 1;
    protected LayoutInflater inflater;
    protected Context context;
    public LoadingDialog loadingDialog;
    protected View netErrorView;

    public BaseAct() {
        setHiddenActionBar(true);
        // setBackListener(false);
        // setScreenOrientation(ScreenOrientation.VERTICAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        inflater = LayoutInflater.from(context);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
    }

    protected void initLoadingDialog(boolean isTrans) {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this, isTrans);
            loadingDialog.setOnCancelListener(this);
        }
    }

    public void showLoadingDialog(String parameter) {
        initLoadingDialog(true);//透明
        loadingDialog.setTitle(parameter);
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (null != loadingDialog) {
            LoadingDialog.dismissDialog(loadingDialog);
        }
    }

    public void showToastMsg(String msg) {
        ToastMsg.showToastMsg(this, msg);
    }

    public void showToastMsg(int strId) {
        ToastMsg.showToastMsg(this, strId);
    }




    private Set<Object> tags = new HashSet<Object>();


//    protected void executeRequest(ERequest eRequest){
//        OkHttpManage.getInstance().execute(eRequest);
//    }
//
//    protected void executeRequest(ERequest eRequest,Object tag){
//        OkHttpManage.getInstance().execute(eRequest,tag);
//    }

//    protected void cancelRequest(Object tag){
//        OkHttpManage.getInstance().cancelRequest(tag);
//    }
//
//    protected void cancelAllRequest(){
//        OkHttpManage.getInstance().cancelAllRequest();
//    }


//    @Override
//    public void onError(OkhttpError okhttpError) {
//        dismissLoadingDialog();
//        if (CURRENT_PAGE > 1) {// 加载异常回退到当前页
//            CURRENT_PAGE--;
//        }
//        String msg = "网络异常";
//        if (!TextUtils.isEmpty(okhttpError.errMsg)){
//            msg = okhttpError.errMsg;
//        }
//        showToastMsg(msg);
//    }

    /**
     * 校验输入
     *************************/
    /* 获取输入内容 */
    public String getInputStr(EditText et) {
        return et.getText().toString().trim();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_right_in,
                R.anim.activity_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_right_in,
                R.anim.activity_right_out);
    }

    /**
     * loadingdialog取消监听
     */
    @Override
    public void onCancel(DialogInterface dialog) {

    }



    /** 覆盖页 end ******************************/

    /**
     * @param
     * @return void
     * @throws
     * @Description: TODO:：关闭键盘事件
     * @author: 刘成伟（wwwlllll@126.com）
     * @Title: closeInput
     * @date 2014-4-6 上午11:18:36
     */
    public void closeInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private OverlayLayout loadingOverlay,emptyOverlay,netErrorOverlay;
    private ImageView loadIv;
    protected void showOverLoading(ViewGroup viewGroup){
        if (loadingOverlay==null){
            loadingOverlay = new OverlayLayout(this);
            loadingOverlay.setOverlayView(R.layout.common_load_view);
            loadingOverlay.attachTo(viewGroup);
            loadingOverlay.setIsHideTargetViewWhenOverlayShown(true);
            loadingOverlay.hideOverlay();
            loadingOverlay.showOverlay();
        }else{
            loadingOverlay.showOverlay();
        }
        if (loadIv==null){
            loadIv = (ImageView) findViewById(R.id.loadingImageView);
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
            emptyOverlay = new OverlayLayout(this);
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
            netErrorOverlay = new OverlayLayout(this);
            netErrorOverlay.setOverlayView(R.layout.common_load_error);
            netErrorOverlay.attachTo(viewGroup);
            netErrorOverlay.setIsHideTargetViewWhenOverlayShown(true);
            findViewById(R.id.click_reload).setOnClickListener(reloadListener);
            findViewById(R.id.click_checknetwork).setOnClickListener(new OnClickListener() {
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
