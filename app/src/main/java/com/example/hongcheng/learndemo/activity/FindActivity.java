package com.example.hongcheng.learndemo.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hongcheng.common.util.ToastUtils;
import com.example.hongcheng.learndemo.IMyAidlInterface;
import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.base.BaseActivity;
import com.example.hongcheng.learndemo.base.BaseApplication;
import com.example.hongcheng.learndemo.databinding.ActivityFindBinding;
import com.example.hongcheng.learndemo.nativeInterface.JNITest;
import com.example.hongcheng.learndemo.service.TestService;
import com.example.hongcheng.learndemo.viewModel.FindViewModel;
import com.example.hongcheng.learndemo.views.WaveView;

/**
 * Created by hongcheng on 16/9/6.
 */
public class FindActivity extends BaseActivity
{
	static
	{
		System.loadLibrary("test");
	}

	private WaveView mWaveView;
	private Intent intent;
	private JNITest jni;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find, true);

		jni = new JNITest();
		intent = new Intent(this, TestService.class);
		startService(intent);
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
	}

	@Override
	protected void initToolBar()
	{
		super.initToolBar();

		Toolbar toolbar = (Toolbar) findViewById(R.id.tb_find);
		setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.mipmap.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
	}

	@Override
	protected void initViewModel()
	{
		mWaveView = (WaveView) findViewById(R.id.wave_find);
		//        mWaveView.setDuration(5000);
		//        mWaveView.setStyle(Paint.Style.STROKE);
		//        mWaveView.setSpeed(400);
		//        mWaveView.setResColor(R.color.colorDefault);
		//        mWaveView.setInterpolator(new AccelerateInterpolator(1.2f));
		//        mWaveView.start();

		mWaveView.setDuration(5000);
		mWaveView.setStyle(Paint.Style.FILL);
		mWaveView.setResColor(R.color.colorDefault);
		mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
		mWaveView.start();

		ActivityFindBinding bind = (ActivityFindBinding) binding;
		final FindViewModel model = new FindViewModel();
		model.action.set("停止");

		bind.setViewModel(model);
		bind.btFind.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ToastUtils.show(BaseApplication.getInstance(), jni.test());

				if ("开始".equals(model.action.get()))
				{
					mWaveView.setDuration(5000);
					mWaveView.setStyle(Paint.Style.FILL);
					mWaveView.setResColor(R.color.colorDefault);
					mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
					mWaveView.start();
					model.action.set("停止");
				}
				else
				{
					mWaveView.stop();
					model.action.set("开始");
				}
			}
		});
	}

	private ServiceConnection conn = new ServiceConnection()
	{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			IMyAidlInterface.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{

		}
	};

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mWaveView.stop();

		unbindService(conn);
		stopService(intent);
	}
}
