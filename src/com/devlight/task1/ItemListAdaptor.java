package com.devlight.task1;

import java.util.ArrayList;

import com.devlight.task1.task.Task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListAdaptor extends BaseAdapter{
	
	Context mContext;
	LayoutInflater mLayoutInflater;	
	ArrayList<Task> mTasks; //Tasks list

	
	
	public ItemListAdaptor(Context context,ArrayList<Task> mTasks)
	{
		this.mTasks = mTasks;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	
	public ArrayList<Task> getTasks() //return tasks list
	{
		return mTasks;
	}
	
	public void  AddTask(Task task) //Add task
	{
		mTasks.add(task);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return mTasks.size();
	}

	@Override
	public Task getItem(int position) {
		
		if (position>=mTasks.size()) return null;
		return mTasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolderItem viewHolder;

		if (convertView == null) 
			{
			   convertView=mLayoutInflater.inflate(R.layout.task_item,null);
			   viewHolder = new ViewHolderItem();
			   
			   viewHolder.tvHeader = (TextView) convertView.findViewById(R.id.tvHeader); 
			   viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment); 
			   
			   convertView.setTag(viewHolder);
			} else 
			{
				viewHolder = (ViewHolderItem) convertView.getTag();
			}
		
		Task mTask = getItem(position);
		

		
		if (mTask !=null)
		{
			
			viewHolder.tvHeader.setText(mTask.getHeader());
			viewHolder.tvComment.setText(mTask.getStrTimePresentation() );

			//set background colors if task started, finished or didn't started
			if(mTask.getStartTime() ==-1 && mTask.getEndTime()==-1) 
				convertView.setBackgroundColor( mContext.getResources().getColor(R.color.green_bkg));
			
			if(mTask.getStartTime() !=-1 && mTask.getEndTime()==-1) 
				convertView.setBackgroundColor(mContext.getResources().getColor(R.color.yellow_bkg));
			
			if(mTask.getStartTime() !=-1 && mTask.getEndTime()!=-1) 
				convertView.setBackgroundColor(mContext.getResources().getColor(R.color.red_bkg));
		}
			
		
		return convertView;
	}
	
	
	static class ViewHolderItem {
		TextView tvHeader;
		TextView tvComment;
	}

}
