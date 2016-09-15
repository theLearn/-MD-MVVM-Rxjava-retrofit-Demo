package com.example.hongcheng.learndemo.base;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

/**
 * Handler的基类<br>
 */
public class BaseHandler<T extends BaseHandler.BaseHandlerCallBack> extends Handler
{
    WeakReference<T> wr;
    
    public BaseHandler(T t)
    {
        wr = new WeakReference<T>(t);
    }
    
    @Override
    public void handleMessage(Message msg)
    {
        super.handleMessage(msg);
        T t = wr.get();
        
        if (t != null)
        {
            t.callBack(msg);
        }
    }
    
    public interface BaseHandlerCallBack
    {
        public void callBack(Message msg);
    }
}
