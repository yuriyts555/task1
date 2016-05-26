package com.devlight.task1.task;

import com.devlight.task1.App;
import com.devlight.task1.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreateTaskActivity extends Activity{
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.create_task_activity);
        
        final EditText etHeader = (EditText) findViewById(R.id.etHeader);
        final EditText etComment = (EditText) findViewById(R.id.etComment);
        
      //set button Save click listener
        final Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

		        Intent returnIntent = new Intent();
		        returnIntent.putExtra(App.RESULT_HEADER,etHeader.getText().toString());
		        returnIntent.putExtra(App.RESULT_COMMENT,etComment.getText().toString());
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
     
        

    }
    
    
    
    
    

}
