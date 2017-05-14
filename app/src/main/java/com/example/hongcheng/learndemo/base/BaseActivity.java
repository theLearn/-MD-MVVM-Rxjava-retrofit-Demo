package com.example.hongcheng.learndemo.base;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.hongcheng.common.util.RxUtils;
import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.views.BaseDialog;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by hongcheng on 16/3/30.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ViewDataBinding binding;

    protected boolean isNeedBind = false;

    protected ActionBar actionBar;
    
    protected Dialog mLoadingDialog;

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        BaseApplication.addActivity(this);
        mSubscriptions = RxUtils.getCompositeSubscription(mSubscriptions);
    }

    protected void setContentView(@LayoutRes int layoutResID, boolean isNeedBind) {
        this.isNeedBind = isNeedBind;
        
        if (isNeedBind) {
            binding = DataBindingUtil.setContentView(this, layoutResID);
            initToolBar();
            initViewModel();
        } else {
            setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if(!isNeedBind){
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

    protected void operateLoadingDialog(boolean isOpen)
    {
        if(mLoadingDialog == null)
        {
            mLoadingDialog = BaseDialog.createLoading(this);
        }
        
        if(isOpen)
        {
            mLoadingDialog.show();
        }
        else
        {
            mLoadingDialog.dismiss();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(mSubscriptions);
        mSubscriptions = null;
        mLoadingDialog = null;
        BaseApplication.getRefWatcher().watch(this);
        BaseApplication.removeActivity(this);
    }

    public CompositeSubscription getCompositeSubscription() {
        return mSubscriptions;
    }
}
