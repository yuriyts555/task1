package utils;

import java.util.ArrayList;

import com.devlight.task1.task.Task;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefsHelper {
	
	final static String TASKS_COUNT_KEY = "TASKSCOUNT"; 
	final static String TASK_KEY = "TASK"; 
	final static String PREFS_NAME = "PREFS"; 
	
	SharedPreferences mPrefs;
	Editor mEditor;
	
	public PrefsHelper(Context context)
	{
		mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);	
		mEditor = mPrefs.edit();
	}
	
	
	public void save(String name,String value)
	{
		mEditor.putString(name, value);
		mEditor.commit();
	}
	
	public void save(String name,ArrayList<String> value)
	{
		for (int i=0;i<value.size();i++) 
		{
			mEditor.putString(name+Integer.toString(i), value.get(i));
		}
		mEditor.commit();
	}
	
	public void saveTasks(ArrayList<Task> tasks)
	{
		
		mEditor.putInt(TASKS_COUNT_KEY, tasks.size());
		
		for (int i=0;i<tasks.size();i++) 
		{
			String mStr = tasks.get(i).getJSON().toString();
			mEditor.putString(TASK_KEY+Integer.toString(i), mStr);
		}
		mEditor.commit();
	}
	
	
	public void loadTasks(ArrayList<Task> tasks)
	{
		
		int count=mPrefs.getInt(TASKS_COUNT_KEY, 0);
		
		for (int i=0;i<count;i++) 
		{
			String mStr =mPrefs.getString(TASK_KEY+Integer.toString(i), "");
			Task mTask = new Task(mStr);
			tasks.add(mTask);

		}
		mEditor.commit();
	}

}
