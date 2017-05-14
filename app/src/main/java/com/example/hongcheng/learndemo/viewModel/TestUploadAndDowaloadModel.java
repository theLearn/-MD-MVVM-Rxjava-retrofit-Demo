package com.example.hongcheng.learndemo.viewModel;

import android.databinding.ObservableField;

/**
 * Created by hongcheng on 17/5/7.
 */

public class TestUploadAndDowaloadModel
{
	public final ObservableField<String> upload = new ObservableField<String>();
	
	public final ObservableField<String> download = new ObservableField<String>();
	
	public final ObservableField<Integer> progress = new ObservableField<Integer>();
}
