package com.example.hongcheng.learndemo.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.hongcheng.common.constant.BaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongcheng on 16/3/31.
 */
public class BaseListAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {
    public List<T> data;

    public BaseListAdapter(){
        data = new ArrayList<T>();
    }

    public BaseListAdapter(List<T> data){
        this.data = new ArrayList<T>();
        this.data.addAll(data);
    }

    public void setData(List<T> data){
        this.data.clear();
        this.data.addAll(data);
    }

    public List<T> getData(){
        return data;
    }

    public void addItem(T t){
        addItem(t, false);
    }

    public void addItem(T t, boolean isHead){
        if(data == null){
            return;
        }

        if(isHead){
            data.add(0, t);
        }
        else{
            data.add(t);
        }
        removeExceedItem(isHead);
    }

    public void addItem(List<T> list){
        addItem(list, false);
    }

    public void addItem(List<T> list, boolean isHead){
        if(data == null){
            return;
        }

        if(isHead){
            data.addAll(0, list);
        }
        else{
            data.addAll(list);
        }
        removeExceedItem(isHead);
    }

    /**
     * 根据recyclerview的上限数移除超出的item
     * @param isHead
     */
    private void removeExceedItem(boolean isHead){
        if(BaseConstants.IS_LIMIT){
            int exceedCount = data.size() - BaseConstants.LIMIT_NUM;
            if(exceedCount > 0){
                for(int i = 0 ; i < exceedCount ; i++){
                    int removeCount = isHead ? data.size()-1 : 0;
                    data.remove(removeCount);
                }
            }
        }
    }

    public void clear(){
        if(data == null)
        {
            data = new ArrayList<T>();
        }
        else
        {
            data.clear();
        }
    }

    public T getItem(int position){
        return data.get(position);
    }

    @Override
    public K onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(K viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
