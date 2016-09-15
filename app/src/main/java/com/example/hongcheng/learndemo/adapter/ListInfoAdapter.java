package com.example.hongcheng.learndemo.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.adapter.models.ListInfoModel;
import com.example.hongcheng.learndemo.adapter.viewHolders.ListInfoViewHolder;
import com.example.hongcheng.learndemo.base.BaseListAdapter;
import com.example.hongcheng.learndemo.databinding.ItemListInfoBinding;

/**
 * Created by hongcheng on 16/9/5.
 */
public class ListInfoAdapter extends BaseListAdapter<ListInfoModel, ListInfoViewHolder> {

    @Override
    public ListInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemListInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_list_info, viewGroup, false);
        ListInfoViewHolder holder = new ListInfoViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListInfoViewHolder viewHolder, int i) {
        ListInfoModel model = data.get(i);

        viewHolder.getBinding().setViewModel(model);
        viewHolder.getBinding().executePendingBindings();
    }
}
