package com.devlight.task1;

import java.util.ArrayList;
import java.util.List;

import com.devlight.task1.task.Task;

import android.app.Application;

public class App extends Application{
	
	
	public final static String RESULT_HEADER = "header";
	public final static String RESULT_COMMENT = "comment";
	
	List<Task> mTasks;  //Save tasks list
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mTasks=new ArrayList<Task>(); //Init variable
	}
	
	

}
