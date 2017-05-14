package com.example.hongcheng.learndemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.hongcheng.learndemo.IMyAidlInterface;

public class TestService extends Service
{
	public TestService()
	{
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return new MyBinder();
	}


	public class MyBinder extends IMyAidlInterface.Stub
	{

		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException
		{

		}

		@Override
		public void add() throws RemoteException
		{

		}
	}

}
