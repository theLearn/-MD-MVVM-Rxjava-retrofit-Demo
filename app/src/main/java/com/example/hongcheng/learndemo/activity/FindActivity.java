package com.example.hongcheng.learndemo.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.base.BaseActivity;
import com.example.hongcheng.learndemo.databinding.ActivityFindBinding;
import com.example.hongcheng.learndemo.viewModel.FindViewModel;
import com.example.hongcheng.learndemo.views.WaveView;

/**
 * Created by hongcheng on 16/9/6.
 */
public class FindActivity extends BaseActivity {

    private WaveView mWaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find, true);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_find);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initViewModel() {
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
        bind.btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("开始".equals(model.action.get())) {
                    mWaveView.setDuration(5000);
                    mWaveView.setStyle(Paint.Style.FILL);
                    mWaveView.setResColor(R.color.colorDefault);
                    mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
                    mWaveView.start();
                    model.action.set("停止");
                } else {
                    mWaveView.stop();
                    model.action.set("开始");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWaveView.stop();
    }
}
