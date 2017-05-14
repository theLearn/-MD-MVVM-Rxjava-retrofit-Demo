package com.example.hongcheng.learndemo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hongcheng.data.ActionException;
import com.example.hongcheng.data.BaseSubscriber;
import com.example.hongcheng.data.RetrofitClient;
import com.example.hongcheng.data.RetrofitManager;
import com.example.hongcheng.data.request.BaseRequest;
import com.example.hongcheng.data.request.CardRetrofit;
import com.example.hongcheng.data.response.BaseResponse;
import com.example.hongcheng.data.response.models.CardRecommend;
import com.example.hongcheng.learndemo.R;
import com.example.hongcheng.learndemo.base.BaseActivity;
import com.example.hongcheng.learndemo.base.BaseApplication;
import com.example.hongcheng.learndemo.databinding.ActivityTestInterfaceBinding;
import com.example.hongcheng.learndemo.viewModel.TestInterfaceModel;
import com.example.hongcheng.learndemo.views.SnackbarUtil;

import java.util.List;

public class TestInterfaceActivity extends BaseActivity implements View.OnClickListener
{
	private TestInterfaceModel model;
	
	private long tempId = -1L;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_interface, true);
	}
	
	@Override
	protected void initToolBar()
	{
		super.initToolBar();
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.tb_test_interfaace);
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
		ActivityTestInterfaceBinding bind = (ActivityTestInterfaceBinding) binding;
		
		model = new TestInterfaceModel();
		model.delete.set("delete");
		model.post.set("post");
		model.get.set("get");
		model.put.set("put");
		
		bind.setViewModel(model);
		
		bind.btTestInterfaceDelete.setOnClickListener(this);
		bind.btTestInterfacePost.setOnClickListener(this);
		bind.btTestInterfacePut.setOnClickListener(this);
		bind.btTestInterfaceGet.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.bt_test_interface_post:
				insert();
				break;
			case R.id.bt_test_interface_delete:
				delete();
				break;
			case R.id.bt_test_interface_put:
				update();
				break;
			case R.id.bt_test_interface_get:
				get();
				break;
			default:
				break;
			
		}
	}
	
	private void insert()
	{
		operateLoadingDialog(true);
		
		CardRecommend cardRecommend = new CardRecommend("http://192.168.1.103:8080/static/one_piece.jpg", "20话", "hot", "海贼王", "1", "2016-09-15 00:00:00");
		mSubscriptions.add(RetrofitClient.getInstance()
				.map(RetrofitManager.createRetrofit(BaseApplication.getInstance(), CardRetrofit.class)
								.insertCardDetail(new BaseRequest<CardRecommend>("123456", cardRecommend)),
						new BaseSubscriber<BaseResponse<Boolean>>(BaseApplication.getInstance())
						{
							@Override
							public void onError(ActionException e)
							{
								operateLoadingDialog(false);
								SnackbarUtil.show(binding.getRoot(), e.getMessage());
							}
							
							@Override
							public void onBaseNext(BaseResponse<Boolean> cardDetailResponse)
							{
								operateLoadingDialog(false);
								if (cardDetailResponse == null)
								{
									SnackbarUtil.show(binding.getRoot(), "cardDetailResponse is null");
									return;
								}
								
								model.response.set(cardDetailResponse.toString());
							}
						}));
	}
	
	private void delete()
	{
		if(tempId <= 0)
		{
			return;
		}
		
		operateLoadingDialog(true);
		CardRecommend cardRecommend = new CardRecommend();
		cardRecommend.setId(tempId);
		
		mSubscriptions.add(RetrofitClient.getInstance()
				.map(RetrofitManager.createRetrofit(BaseApplication.getInstance(), CardRetrofit.class)
								.delCardDetail(new BaseRequest<CardRecommend>("123456", cardRecommend)),
						new BaseSubscriber<BaseResponse<Boolean>>(BaseApplication.getInstance())
						{
							@Override
							public void onError(ActionException e)
							{
								operateLoadingDialog(false);
								SnackbarUtil.show(binding.getRoot(), e.getMessage());
							}
							
							@Override
							public void onBaseNext(BaseResponse<Boolean> cardDetailResponse)
							{
								operateLoadingDialog(false);
								if (cardDetailResponse == null)
								{
									SnackbarUtil.show(binding.getRoot(), "cardDetailResponse is null");
									return;
								}
								
								model.response.set(cardDetailResponse.toString());
							}
						}));
	}
	
	private void update()
	{
		if(tempId <= 0)
		{
			return;
		}
			
		operateLoadingDialog(true);
		CardRecommend cardRecommend = new CardRecommend("http://192.168.1.103:8080/static/bleach.jpg", "20话", "hot", "死神", "1", "2016-09-15 00:00:00");
		cardRecommend.setId(tempId);
		
		mSubscriptions.add(RetrofitClient.getInstance()
				.map(RetrofitManager.createRetrofit(BaseApplication.getInstance(), CardRetrofit.class)
								.updateCardDetail(new BaseRequest<CardRecommend>("123456", cardRecommend)),
						new BaseSubscriber<BaseResponse<Boolean>>(BaseApplication.getInstance())
						{
							@Override
							public void onError(ActionException e)
							{
								operateLoadingDialog(false);
								SnackbarUtil.show(binding.getRoot(), e.getMessage());
							}
							
							@Override
							public void onBaseNext(BaseResponse<Boolean> cardDetailResponse)
							{
								operateLoadingDialog(false);
								if (cardDetailResponse == null)
								{
									SnackbarUtil.show(binding.getRoot(), "cardDetailResponse is null");
									return;
								}
								
								model.response.set(cardDetailResponse.toString());
							}
						}));
	}
	
	private void get()
	{
		operateLoadingDialog(true);
		mSubscriptions.add(RetrofitClient.getInstance()
				.map(RetrofitManager.createRetrofit(BaseApplication.getInstance(), CardRetrofit.class)
								.getCardDetail("hot"),
						new BaseSubscriber<BaseResponse<List<CardRecommend>>>(BaseApplication.getInstance())
						{
							@Override
							public void onError(ActionException e)
							{
								operateLoadingDialog(false);
								SnackbarUtil.show(binding.getRoot(), e.getMessage());
							}
							
							@Override
							public void onBaseNext(BaseResponse<List<CardRecommend>> cardDetailResponse)
							{
								operateLoadingDialog(false);
								if (cardDetailResponse == null)
								{
									SnackbarUtil.show(binding.getRoot(), "cardDetailResponse is null");
									return;
								}
								
								if (cardDetailResponse.isSuccess())
								{
									List<CardRecommend> list = cardDetailResponse.getData();
									tempId = list.get(list.size() - 1).getId();
									model.response.set(list.toString());
								}
								else
								{
									SnackbarUtil.show(binding.getRoot(), cardDetailResponse.getDescription());
								}
							}
						}));
	}
}
