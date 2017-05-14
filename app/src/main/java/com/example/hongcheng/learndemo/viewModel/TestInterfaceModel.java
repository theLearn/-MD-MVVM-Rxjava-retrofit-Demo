package com.example.hongcheng.learndemo.viewModel;

import android.databinding.ObservableField;

/**
 * Created by hongcheng on 17/5/4.
 */

public class TestInterfaceModel
{
	public final ObservableField<String> post = new ObservableField<String>();
	
	public final ObservableField<String> delete = new ObservableField<String>();
	
	public final ObservableField<String> put = new ObservableField<String>();
	
	public final ObservableField<String> get = new ObservableField<String>();
	
	public final ObservableField<String> response = new ObservableField<String>();
}
