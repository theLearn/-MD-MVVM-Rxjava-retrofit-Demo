package com.example.hongcheng.learndemo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hongcheng.common.util.FileUtils;
import com.example.hongcheng.common.util.LoggerUtils;
import com.example.hongcheng.data.RetrofitManager;
import com.example.hongcheng.data.request.BaseRequest;
import com.example.hongcheng.data.request.FileOperateRetrofit;
import com.example.hongcheng.data.response.BaseResponse;
import com.example.hongcheng.data.response.models.FileOperate;
import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.base.BaseActivity;
import com.example.hongcheng.learndemo.base.BaseApplication;
import com.example.hongcheng.learndemo.databinding.ActivityTestUploadAndDownloadBinding;
import com.example.hongcheng.learndemo.viewModel.TestUploadAndDowaloadModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class TestUploadAndDownloadActivity extends BaseActivity implements View.OnClickListener
{
	private static final String TAG = TestUploadAndDownloadActivity.class.getName();
	
	private TestUploadAndDowaloadModel model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_upload_and_download, true);
	}
	
	@Override
	protected void initToolBar()
	{
		super.initToolBar();
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.tb_test_upload_download);
		setSupportActionBar(toolbar);
		
		toolbar.setNavigationIcon(R.mipmap.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
	}
	
	@Override
	protected void initViewModel()
	{
		ActivityTestUploadAndDownloadBinding bind = (ActivityTestUploadAndDownloadBinding) binding;
		
		model = new TestUploadAndDowaloadModel();
		model.upload.set("upload");
		model.download.set("download");
		model.progress.set(0);
		
		bind.setViewModel(model);
		
		bind.btTestDownload.setOnClickListener(this);
		bind.btTestUpload.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.bt_test_upload:
				upload();
				break;
			case R.id.bt_test_download:
				download();
				break;
			default:
				break;
			
		}
	}
	
	private void download()
	{
		FileOperate fileOperate = new FileOperate();
		fileOperate.setFileName("bleach.jpg");
		
		mSubscriptions.add(RetrofitManager.createRetrofit(BaseApplication.getInstance(), FileOperateRetrofit.class)
				.download(new BaseRequest<FileOperate>("123456", fileOperate).toMap()).subscribeOn(Schedulers.io())
				.doOnSubscribe(new Action0()
				{
					@Override
					public void call()
					{
						operateLoadingDialog(true);
					}
				}).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<ResponseBody>()
				{
					@Override
					public void onCompleted()
					{
						operateLoadingDialog(false);
					}
					
					@Override
					public void onError(Throwable e)
					{
						operateLoadingDialog(false);
					}
					
					@Override
					public void onNext(ResponseBody responseBody)
					{
						
					}
				}));
	}
	
	private void upload()
	{
		String path = FileUtils.getCrashFilePath();
		File file = new File(path, "a.png");
		// 如果文件不存在
		if (!file.exists())
		{
			LoggerUtils.info(TAG, "file not exists");
			return;
		}
		
		FileOperate fileOperate = new FileOperate();
		fileOperate.setFileName(file.getName());
		fileOperate.setSize(file.getTotalSpace());
		fileOperate.setClientPath(file.getAbsolutePath());
		
		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
		// MultipartBody.Part is used to send also the actual file name
		MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
		
		// add another part within the multipart request
		String descriptionString = "hello, this is description speaking";
		RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
		
		if(body == null || description == null)
		{
			LoggerUtils.info(TAG, "body is null or description is null");
			return;
		}
		
		mSubscriptions.add(RetrofitManager.createRetrofit(BaseApplication.getInstance(), FileOperateRetrofit.class)
				.upload(description, body).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0()
				{
					@Override
					public void call()
					{
						operateLoadingDialog(true);
					}
				}).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<BaseResponse<Boolean>>()
				{
					@Override
					public void onCompleted()
					{
						operateLoadingDialog(false);
					}
					
					@Override
					public void onError(Throwable e)
					{
						operateLoadingDialog(false);
					}
					
					@Override
					public void onNext(BaseResponse<Boolean> response)
					{
						if (response.isSuccess() && response.getData())
						{
							model.progress.set(100);
						}
						else
						{
							model.progress.set(50);
						}
					}
				}));
	}
}
