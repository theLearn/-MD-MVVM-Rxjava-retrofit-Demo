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
    public static void setImageUrl(final ImageView view, final String url) {
        view.post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(view.getContext()).load(url)
                        .resize(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .centerCrop()
                        .error(BaseApplication.getInstance().getResources().getDrawable(R.mipmap.example))
                        .into(view);
            }
        });
    }
}
