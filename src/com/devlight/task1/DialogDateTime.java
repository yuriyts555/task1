package com.devlight.task1;

import java.util.Calendar;

import com.devlight.task1.task.Task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class DialogDateTime extends Dialog{
	
	public static final int SET_START_TIME = 0; //input start data
	public static final int SET_END_TIME = 1; //input end data
	
	
	TextView tvHeader;
	DatePicker mDatePicker;
	TimePicker mTimePicker;
	
	Task mTask;  //editing task
	int curTimeType = SET_START_TIME; // start  or end  time
	Context mContext;
	Updater mUpdater; //interface to update after dialog finish
	int mPosition = -1;  //task position

	public DialogDateTime(Context context) {
		super(context);
		
		mContext = context;

		setContentView(R.layout.dialog_date_time);		
		
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		mDatePicker = (DatePicker) findViewById(R.id.datePicker);
		mTimePicker = (TimePicker) findViewById(R.id.timePicker);
		mTimePicker.setIs24HourView(true);
		
		final Button btnOk = (Button) findViewById(R.id.btnOk);
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Calendar mCalendar = Calendar.getInstance();
				
				mCalendar.set(Calendar.YEAR, mDatePicker.getYear());
				mCalendar.set(Calendar.MONTH, mDatePicker.getMonth());
				mCalendar.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
				
				mCalendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
				mCalendar.set(Calendar.MINUTE, mTimePicker.getCurrentMinute());
				
				if (curTimeType == SET_START_TIME) 
					{
					   if (mTask.getEndTime() !=-1L)
						   if (mTask.getEndTime()<=mCalendar.getTimeInMillis())
						   {
							   showErrorMsg(R.string.set_timestart_error);
							   return;
						   }
					   
					   
					   mTask.setStartTime(mCalendar.getTimeInMillis());
					   
					}
				else 
					{
					  
					   if (mTask.getStartTime() !=-1L)
						   if (mTask.getStartTime()>=mCalendar.getTimeInMillis())
						   {
							   showErrorMsg(R.string.set_timeend_error);
							   return;
						   }
					    mTask.setEndTime(mCalendar.getTimeInMillis());
					}
				
				
				mUpdater.Update(mTask, mPosition); //Update views
				
				DialogDateTime.this.dismiss();
				
			}
		});
		
		
		final Button btnCancel = (Button) findViewById(R.id.btnCancel);
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DialogDateTime.this.dismiss();
				
			}
		});
		
	}
	

	//if start date greater than end date
	void showErrorMsg(int msg)
	{
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(mContext);
		dlgAlert.setMessage(mContext.getString(msg));
		dlgAlert.setPositiveButton(android.R.string.ok,
			    new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			        	dialog.dismiss();
			        }
			    });
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
	
	
	//call to show dialog and init with task data
	public void showForTask(Updater mUdater,Task task,int curTimeType,int position)
	{
		long mTime;
		
		mUpdater = mUdater;
		mPosition = position;
		mTask = task;
		this.curTimeType = curTimeType;
		
		if (curTimeType == SET_START_TIME) 
		{
			   tvHeader.setText(R.string.set_start_time);
			   mTime = task.getStartTime();
		}
		else
		{
			   tvHeader.setText(R.string.set_end_time);
			   mTime = task.getEndTime();
		}
		
		Calendar mCalendar = Calendar.getInstance();
		if (mTime == -1) mTime = mCalendar.getTimeInMillis();
		else mCalendar.setTimeInMillis(mTime);
		
		
		mDatePicker.updateDate(mCalendar.get(Calendar.YEAR),
				                             mCalendar.get(Calendar.MONTH), 
				                             mCalendar.get(Calendar.DAY_OF_MONTH));
		
		mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
		mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
		
		show();
	}
	

}
