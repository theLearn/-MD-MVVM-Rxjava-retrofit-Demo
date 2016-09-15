package com.example.hongcheng.learndemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hongcheng.common.util.RxUtils;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by hongcheng on 16/9/4.
 */
public abstract class BaseFragment extends Fragment {

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    protected Context mContext;
    protected View rootView;

    protected abstract int getLayoutResId();
    protected abstract void initViewModel(Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;

        mSubscriptions = RxUtils.getCompositeSubscription(mSubscriptions);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        RxUtils.unsubscribe(mSubscriptions);
        mSubscriptions = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResId(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(hidden){
            onPause();
        }
        else{
            onResume();
        }
    }
}
