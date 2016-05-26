package com.devlight.task1;

import com.devlight.task1.task.CreateTaskActivity;
import com.devlight.task1.task.Task;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ListActivity {
	
	final int RESULT_CREATE_ACTIVITY =1;
	
	ItemListAdaptor itemListAdaptor;  //List adaptor
	App mApplication;  //Application (use to save global for all application variables)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        mApplication = (App)getApplicationContext();
        
        // set list adaptor
		itemListAdaptor = new ItemListAdaptor(mApplication);
		setListAdapter(itemListAdaptor);
		
		
		//set button Add click listener
		final Button  btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	
				Intent mIntent = new Intent(MainActivity.this, CreateTaskActivity.class);
				startActivityForResult(mIntent, RESULT_CREATE_ACTIVITY);
				
			}
		});

    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_CREATE_ACTIVITY) {
        	
        	//Button save was pressed, we got result
            if(resultCode == Activity.RESULT_OK){
            	
                String mHeader=data.getStringExtra(App.RESULT_HEADER).trim();
                String mComment=data.getStringExtra(App.RESULT_COMMENT).trim();
                
                if (mHeader.length()==0 && mComment.length()==0) return; //We got empty task
                
                Task mTask = new Task(mHeader,mComment);
                
                mApplication.mTasks.add(mTask);
                
                itemListAdaptor.notifyDataSetChanged();  //Update list view
                
            }
            
            //Button exit was pressed, no result
            if (resultCode == Activity.RESULT_CANCELED) {
           
            }
        }
    }//onActivityResult


}
