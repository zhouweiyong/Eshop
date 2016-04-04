package com.android.vstecs.eshop.control.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.android.vstecs.eshop.R;
import com.android.vstecs.eshop.control.base.BaseAct;
import com.android.vstecs.eshop.control.base.BaseFra;
import com.android.vstecs.eshop.control.index.fragment.IndexFragment;
import com.android.vstecs.eshop.control.myinfo.fragment.MyInfoFragment;
import com.android.vstecs.eshop.control.nearby.fragment.NearbyFragment;
import com.android.vstecs.eshop.control.timelimit.fragment.TimeLimitFragment;
import com.android.vstecs.eshop.mode.event.CallMainPagerChangeEvent;
import com.vstecs.android.uiframework.view.jazzyviewpage.JazzyViewPager;

import java.util.HashSet;
import java.util.Set;

import de.greenrobot.event.EventBus;


public class MainActivity extends BaseAct implements ViewPager.OnPageChangeListener {

	private JazzyViewPager pager;
	private LinearLayout page_indicator;
	private Set<Integer> initFrags;
	private MainPagerAdapter pagerAdapter;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			isExit = false;
		};
	};
	/** 是否退出应用 */
	private boolean isExit;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Object instantiateItem = pagerAdapter.instantiateItem(pager, pager.getCurrentItem());
		if (instantiateItem instanceof BaseFra.FragmentKeyDown) {
			BaseFra.FragmentKeyDown iFraKeyDown = (BaseFra.FragmentKeyDown) instantiateItem;
			if (iFraKeyDown.onFraKeyDown(keyCode, event))
				return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isExit) {
				showToastMsg("再按一次退出应用");
				isExit = true;
				mHandler.sendEmptyMessageDelayed(0, 1000);
				return true;
			} else {
				// sendBroadcast(new Intent(Constant.ACTION_EXIT_SYSTEM));
			}
		}
		return super.onKeyDown(keyCode, event);
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	};

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_main);

		EventBus.getDefault().register(this);
	}

	/** 用EventBus，通知主页切换界面 */
	public void onEventMainThread(CallMainPagerChangeEvent event) {
		pager.setCurrentItem(event.changePageNo, false);
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void initWidget() {
		super.initWidget();
		
		pager = (JazzyViewPager) findViewById(R.id.pager);
		pager.setFadeEnabled(true);
		pager.setTransitionEffect(JazzyViewPager.TransitionEffect.Standard);

		pager.setOffscreenPageLimit(4);

		pager.setOnPageChangeListener(this);
		pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		// pagerAdapter = new MainPagerAdapter();
		pager.setAdapter(pagerAdapter);

		page_indicator = (LinearLayout) findViewById(R.id.page_indicator);
		int tabCount = page_indicator.getChildCount();
		for (int i = 0; i < tabCount; i++) {
			page_indicator.getChildAt(i).setTag(i);
			page_indicator.getChildAt(i).setOnClickListener(this);
		}

		initFrags = new HashSet<Integer>();
		initFrags.add(0);
		onPageSelected(0);
	}


	private class MainPagerAdapter extends FragmentStatePagerAdapter {

		public MainPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment frag = null;
			switch (position) {
			case 0:
				frag = new IndexFragment();
				break;
			case 1:
				frag = new NearbyFragment();
				break;
			case 2:
				frag = new TimeLimitFragment();
				break;
			case 3:
				frag = new MyInfoFragment();
				break;
			}
			return frag;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Object object = super.instantiateItem(container, position);
			pager.setObjectForPosition(object, position);
			return object;
		}

		@Override
		public int getCount() {
			return 4;
		}

	}

	/** viewpager监听 **************************/
	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int item) {
		final int tabCount = page_indicator.getChildCount();
		for (int i = 0; i < tabCount; i++) {
			final View child = page_indicator.getChildAt(i);
			final boolean isSelected = (i == item);
			child.setSelected(isSelected);
		}

		if (!initFrags.contains(item)) {
			initFrags.add(item);
			Object frag = pagerAdapter.instantiateItem(pager, item);
			if (frag instanceof BaseFra.OnInitShowListener) {
				((BaseFra.OnInitShowListener) frag).onInitShow();
			}
		} else {
			if (item == 4) {
				Object frag = pagerAdapter.instantiateItem(pager, item);
				if (frag instanceof BaseFra.OnInitShowListener) {
					((BaseFra.OnInitShowListener) frag).onInitShow();
				}
			}
		}

	}
	

	/** viewpager监听 end **************************/

	/**
	 * 只针对tab
	 */
	@Override
	public void widgetClick(View v) {
		int index = (Integer) v.getTag();
		if (index != pager.getCurrentItem()) {
			pager.setCurrentItem(index, false);
		}
		super.widgetClick(v);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	


}
