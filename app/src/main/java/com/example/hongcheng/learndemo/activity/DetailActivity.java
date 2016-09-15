package com.example.hongcheng.learndemo.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hongcheng.common.constant.BaseConstants;
import com.example.hongcheng.common.util.SafeIntentUtils;
import com.example.hongcheng.data.RetrofitManager;
import com.example.hongcheng.data.request.CardRetrofit;
import com.example.hongcheng.data.response.CardDetailResponse;
import com.example.hongcheng.data.response.models.CardRecommend;
import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.adapter.ListInfoAdapter;
import com.example.hongcheng.learndemo.adapter.models.CardModel;
import com.example.hongcheng.learndemo.adapter.models.ListInfoModel;
import com.example.hongcheng.learndemo.base.BaseActivity;
import com.example.hongcheng.learndemo.base.BaseApplication;
import com.example.hongcheng.learndemo.databinding.ActivityDetailBinding;
import com.example.hongcheng.learndemo.views.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hongcheng on 16/9/5.
 */
public class DetailActivity extends BaseActivity {

    private ListInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail, true);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_detail);
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
        CardModel model = getIntent().getParcelableExtra(SafeIntentUtils.INTENT_MODEL);
        if(model == null)
        {
            return;
        }
        ActivityDetailBinding bind = (ActivityDetailBinding) binding;
        bind.setModel(model);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_detail);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ListInfoAdapter();
        mRecyclerView.setAdapter(mAdapter);

        getData(model.getType());
    }

    private void getData(String type) {
        mSubscriptions.add(RetrofitManager.createRetrofit(BaseApplication.getInstance(), CardRetrofit.class)
                .getCardDetail(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CardDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SnackbarUtil.show(binding.getRoot(), e.getMessage());
                        List<ListInfoModel> data = new ArrayList<ListInfoModel>();
                        data.add(new ListInfoModel("http://aa", "从零开始", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "龙珠超", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "灵能百分百", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "驱魔少年", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "热诚传说", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "弹丸论破", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "海贼王", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "美食的俘虏", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "食戟之灵", "20话", "2016-09-15 00:00:00", ""));
                        data.add(new ListInfoModel("http://aa", "狐妖小红娘", "20话", "2016-09-15 00:00:00", ""));

                        mAdapter.setData(data);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(CardDetailResponse cardDetailResponse) {
                        if (cardDetailResponse == null) {
                            SnackbarUtil.show(binding.getRoot(), "cardDetailResponse is null");
                            return;
                        }

                        if (BaseConstants.STATUS_SUCCESS == cardDetailResponse.getStatus()) {
                            List<ListInfoModel> data = new ArrayList<ListInfoModel>();
                            for (CardRecommend item : cardDetailResponse.getCardRecommends()) {
                                ListInfoModel model = new ListInfoModel(item.getImageUrl(), item.getContent(), item.getDescription(), item.getDate(), item.getInfoId());
                                data.add(model);
                            }
                            mAdapter.setData(data);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            SnackbarUtil.show(binding.getRoot(), cardDetailResponse.getDescription());
                        }
                    }
                }));
    }
}
