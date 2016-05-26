package com.devlight.task1;

import com.devlight.task1.task.Task;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListAdaptor extends BaseAdapter{
	
	App mApplication;
	LayoutInflater mLayoutInflater;	
	
	
	public ItemListAdaptor(App mApplication)
	{
		this.mApplication = mApplication;
		mLayoutInflater = LayoutInflater.from(mApplication);
	}

	@Override
	public int getCount() {

		return mApplication.mTasks.size();
	}

	@Override
	public Task getItem(int position) {
		
		if (position>=mApplication.mTasks.size()) return null;
		return mApplication.mTasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) convertView=mLayoutInflater.inflate(R.layout.task_item,null);
		
		Task mTask = getItem(position);
		
		TextView tvHeader = (TextView) convertView.findViewById(R.id.tvHeader); 
		TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment); 
		
		tvHeader.setText(mTask.getHeader());
		tvComment.setText(mTask.getComment());
			
		
		return convertView;
	}

}
