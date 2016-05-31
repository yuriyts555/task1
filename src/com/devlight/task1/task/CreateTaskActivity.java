package com.devlight.task1.task;


import com.devlight.task1.DialogDateTime;
import com.devlight.task1.MainActivity;
import com.devlight.task1.R;
import com.devlight.task1.Updater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateTaskActivity extends Activity implements Updater{
	
	DialogDateTime mDialogDateTime;
	Task mTask;
	int mPosition;
	
	EditText etHeader = null;
	EditText etComment = null;
	TextView tvStartTimeDate = null;
	TextView tvEndTimeDate = null;
	TextView tvSpentTime =  null;
	
	
    @Override
	protected void onSaveInstanceState(Bundle outState) {
    	
    	mTask.setStrings(etHeader.getText().toString(),etComment.getText().toString());
    	
    	outState.putParcelable(MainActivity.DATA, mTask);
    	outState.putInt(MainActivity.POSITION, mPosition);
		super.onSaveInstanceState(outState);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.create_task_activity);
        
        
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
            	mTask= new Task("", "");
            } else {
            	mTask= extras.getParcelable(MainActivity.DATA);
            	mPosition= extras.getInt(MainActivity.POSITION);
            }
        } else {
        	mTask=  savedInstanceState.getParcelable(MainActivity.DATA);
        	mPosition=  savedInstanceState.getInt(MainActivity.POSITION);
        }
        
        if (mTask == null) mTask = new Task("", "");
        
        mDialogDateTime = new DialogDateTime(this);
        
          etHeader = (EditText) findViewById(R.id.etHeader);
          etComment = (EditText) findViewById(R.id.etComment);
        
      //set button Save click listener
        final Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				//return task to previous activity
		        Intent returnIntent = new Intent();

		        mTask.setStrings(etHeader.getText().toString(),etComment.getText().toString());
		        returnIntent.putExtra(MainActivity.DATA, mTask);
		        returnIntent.putExtra(MainActivity.POSITION, mPosition);
		        setResult(Activity.RESULT_OK,returnIntent);
		        finish();
				
			}
		});
        
        
      //set button Exit click listener
        final Button btnExit = (Button)findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

		        Intent returnIntent = new Intent();
		        setResult(Activity.RESULT_CANCELED, returnIntent);
		        finish();
				
			}
		});
     
        
       final  OnClickListener mOnClickStartTime = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			//Change start date
			mDialogDateTime.showForTask(CreateTaskActivity.this,mTask,DialogDateTime.SET_START_TIME,mPosition);
			
		}
	  };
	  
	  
      final  OnClickListener mOnClickEndTime = new OnClickListener() {
  		
		@Override
		public void onClick(View v) {
			
			//Change end date
			mDialogDateTime.showForTask(CreateTaskActivity.this,mTask,DialogDateTime.SET_END_TIME,mPosition);
			
		}
	  };
	  
	  
	  
	  
	  tvStartTimeDate = (TextView) findViewById(R.id.tvStartTimeDate);	  
	  tvEndTimeDate = (TextView) findViewById(R.id.tvEndTimeDate);	 
	  tvSpentTime = (TextView) findViewById(R.id.tvSpentTime);
	  
	  
	  final TextView tvEndTimeHeader = (TextView) findViewById(R.id.tvEndTimeHeader);
	  final TextView tvStartTimeHeader = (TextView) findViewById(R.id.tvStartTimeHeader);
	  //final TextView tvSpentTimeHeader = (TextView) findViewById(R.id.tvSpentTimeHeader);
	  
	  etComment.setText(mTask.mHeader);
	  etHeader.setText(mTask.mComment);
	  
	  tvStartTimeDate.setText(mTask.getStrStartTime());
	  tvEndTimeDate.setText(mTask.getStrEndTime());
	  tvSpentTime.setText(mTask.getStrSpentTime());
	  
	  
	  
	  tvStartTimeHeader.setOnClickListener(mOnClickStartTime);
	  tvStartTimeDate.setOnClickListener(mOnClickStartTime);	  
	  tvEndTimeHeader.setOnClickListener(mOnClickEndTime);
	  tvEndTimeDate.setOnClickListener(mOnClickEndTime);
        

    }



	@Override
	public void Update(Task task, int position) {
		
	       //Update views
		  tvStartTimeDate.setText(mTask.getStrStartTime());
		  tvEndTimeDate.setText(mTask.getStrEndTime());
		  tvSpentTime.setText(mTask.getStrSpentTime());
		
	}
    
    
    
    
    

}
