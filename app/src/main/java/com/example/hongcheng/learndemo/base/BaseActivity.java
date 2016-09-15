package com.example.hongcheng.learndemo.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.hongcheng.common.util.RxUtils;
import com.example.hongcheng.learndemo.R;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by hongcheng on 16/3/30.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ViewDataBinding binding;

    protected boolean isNeed = true;

    protected ActionBar actionBar;

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        BaseApplication.addActivity(this);
        mSubscriptions = RxUtils.getCompositeSubscription(mSubscriptions);
    }

    protected void setContentView(@LayoutRes int layoutResID, boolean isNeedBind) {
        if (isNeedBind) {
            isNeed = false;
            binding = DataBindingUtil.setContentView(this, layoutResID);
        } else {
            setContentView(layoutResID);
        }
        initToolBar();
        initViewModel();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if(isNeed){
            initToolBar();
            initViewModel();
        }
    }

    protected void initToolBar() {

    }

    protected void setAbTitle(int resId) {
        if (actionBar != null) {
            actionBar.setTitle(resId);
        }
    }

    protected void setAbTitle(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected abstract void initViewModel();

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(mSubscriptions);
        mSubscriptions = null;
        BaseApplication.getRefWatcher().watch(this);
        BaseApplication.removeActivity(this);
    }

    public CompositeSubscription getCompositeSubscription() {
        return mSubscriptions;
    }
}
