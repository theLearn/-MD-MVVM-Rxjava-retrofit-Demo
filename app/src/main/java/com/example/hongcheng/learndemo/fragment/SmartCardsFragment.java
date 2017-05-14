package com.example.hongcheng.learndemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.example.hongcheng.common.util.LoggerUtils;
import com.example.hongcheng.data.ActionException;
import com.example.hongcheng.data.BaseSubscriber;
import com.example.hongcheng.data.RetrofitClient;
import com.example.hongcheng.data.RetrofitManager;
import com.example.hongcheng.data.request.CardRetrofit;
import com.example.hongcheng.data.response.BaseResponse;
import com.example.hongcheng.data.response.models.Card;
import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.adapter.CardAdapter;
import com.example.hongcheng.learndemo.adapter.models.CardModel;
import com.example.hongcheng.learndemo.base.BaseApplication;
import com.example.hongcheng.learndemo.base.BaseFragment;
import com.example.hongcheng.learndemo.views.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongcheng on 16/9/4.
 */
public class SmartCardsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout srf;
    private CardAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_smart_cards;
    }

    @Override
    protected void initViewModel(Bundle savedInstanceState) {
        srf = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_fragment_cards);
        srf.setProgressBackgroundColorSchemeColor(Color.WHITE);
        srf.setColorSchemeResources(R.color.colorDefault);
        srf.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources()
                        .getDisplayMetrics()));
        srf.setOnRefreshListener(this);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_smart_cards);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new CardAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        srf.post(new Runnable() {
            @Override
            public void run() {
                srf.setRefreshing(true);
                getData();
            }
        });
    }

    private void getData() {
        mSubscriptions.add(RetrofitClient.getInstance().map(RetrofitManager.createRetrofit(BaseApplication.getInstance(), CardRetrofit.class)
                .listCards(), new BaseSubscriber<BaseResponse<List<Card>>>(BaseApplication.getInstance()) {
            @Override
            public void onError(ActionException e) {
                SnackbarUtil.show(rootView, e.getMessage());
                srf.setRefreshing(false);

                List<CardModel> data = new ArrayList<CardModel>();
                data.add(new CardModel("我家网络", "http://aa", "可以管理wifi哦", ""));
                data.add(new CardModel("我家看看", "http://aa", "可以看视频哦", ""));
                data.add(new CardModel("我家存储", "http://aa", "里面有好东西哦", ""));
                data.add(new CardModel("能耗管理", "http://aa", "节约用电，人人有责", ""));
                data.add(new CardModel("家庭安防", "http://aa", "让你的家更安全", ""));
                data.add(new CardModel("环境监控", "http://aa", "随时感知房间环境的变化，是生活更舒适", ""));

                mAdapter.setData(data);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onBaseNext(BaseResponse<List<Card>> cardResponse) {
                srf.setRefreshing(false);

                if(cardResponse == null){
                    SnackbarUtil.show(rootView, "cardResponse is null");
                    return;
                }

                if (cardResponse.isSuccess()) {
                    LoggerUtils.error(SmartCardsFragment.class.getName(), cardResponse.toString());
                    List<CardModel> data = new ArrayList<CardModel>();
                    for (Card card : cardResponse.getData()) {
                        CardModel model = new CardModel(card.getName(), card.getImageUrl(), card.getDescription(), card.getType());
                        data.add(model);
                    }

                    mAdapter.setData(data);
                    mAdapter.notifyDataSetChanged();
                } else {
                    SnackbarUtil.show(rootView, cardResponse.getDescription());
                }
            }
        }));
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
