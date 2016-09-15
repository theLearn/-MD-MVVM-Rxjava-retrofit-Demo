package com.example.hongcheng.learndemo.bindingAdapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.base.BaseApplication;
import com.squareup.picasso.Picasso;

/**
 * Created by hongcheng on 16/9/11.
 */
public class CommonBindingAdapter {
    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).error(BaseApplication.getInstance().getResources().getDrawable(R.mipmap.example)).into(view);
    }
}
