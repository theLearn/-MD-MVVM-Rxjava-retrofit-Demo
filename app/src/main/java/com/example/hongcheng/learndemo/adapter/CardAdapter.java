package com.example.hongcheng.learndemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongcheng.common.util.SafeIntentUtils;
import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.activity.DetailActivity;
import com.example.hongcheng.learndemo.adapter.models.CardModel;
import com.example.hongcheng.learndemo.adapter.viewHolders.CardViewHolder;
import com.example.hongcheng.learndemo.base.BaseListAdapter;
import com.example.hongcheng.learndemo.databinding.ItemSmartCardBinding;

/**
 * Created by hongcheng on 16/9/4.
 */
public class CardAdapter extends BaseListAdapter<CardModel, CardViewHolder> {

    private Context mContext;

    public CardAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemSmartCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_smart_card, viewGroup, false);
        CardViewHolder holder = new CardViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder viewHolder, int i) {
        final CardModel model = data.get(i);
        viewHolder.getBinding().setViewModel(model);
        viewHolder.getBinding().executePendingBindings();
        viewHolder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(SafeIntentUtils.INTENT_MODEL, model);
                mContext.startActivity(intent);
            }
        });
    }
}
