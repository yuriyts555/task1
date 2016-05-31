package com.devlight.task1;

import java.util.ArrayList;
import java.util.List;

import com.devlight.task1.task.CreateTaskActivity;
import com.devlight.task1.task.Task;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import utils.PrefsHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class MainActivity extends AppCompatActivity   implements Updater{
	


   Toolbar toolBar;




	public final static String DATA ="DATA";  
	public final static String POSITION ="POSITION";
	
	final static int RESULT_CREATE_ACTIVITY =1;
	
	ItemListAdaptor itemListAdaptor;  //List adaptor
	ListView mListView;
	
	boolean breakCycle; //use to break cycle in fill list 
	DialogDateTime mDialogSetDateTime;  //dialog to change data and time
	PrefsHelper mPrefsHelper;  //save list in sharedpreferences helper
	
	
	@Override
	public void onBackPressed() {
	
		super.onBackPressed();
	}

	
	
    
    @Override
	protected void onSaveInstanceState(Bundle outState) {
    	if (itemListAdaptor.getTasks()!=null)
    		outState.putParcelableArrayList(DATA, itemListAdaptor.getTasks()); //save list 
		super.onSaveInstanceState(outState);
	}

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        toolBar = (Toolbar) findViewById(R.id.toolbar); //Use toolbar
        setSupportActionBar(toolBar);  
        
        mListView = (ListView) findViewById(R.id.listView);
        
        
        mDialogSetDateTime = new DialogDateTime(this); //Create dialog
        

     	
        mPrefsHelper = new PrefsHelper(this);
        
         ArrayList<Task> mTasks;
        
        //Restore tasks list     
        if (savedInstanceState != null) {
        	mTasks = savedInstanceState.getParcelableArrayList(DATA); //was screen rotation, restore tasks from savedInstance
        	
            // set list adaptor
    		itemListAdaptor = new ItemListAdaptor(this,mTasks);
    		mListView.setAdapter(itemListAdaptor);
    		
    		
        } else
        {
        	mTasks =new ArrayList<Task>(); //App run, restore from sharedPreference
        	
            // set list adaptor
    		itemListAdaptor = new ItemListAdaptor(this,mTasks);
    		mListView.setAdapter(itemListAdaptor);
    		
    		
        	loadFromPrefs();
        }
       
                

        

		
		

        
        
       

		
		
		//set button Add click listener
		final Button  btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	
				Intent mIntent = new Intent(MainActivity.this, CreateTaskActivity.class);
				mIntent.putExtra(POSITION, -1);
				startActivityForResult(mIntent, RESULT_CREATE_ACTIVITY);
				
			}
		});
		
		
		//Long click on item in ListView
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				
				Intent mIntent = new Intent(MainActivity.this, CreateTaskActivity.class);	
				mIntent.putExtra(DATA, itemListAdaptor.getItem(position));
				mIntent.putExtra(POSITION, position);
				startActivityForResult(mIntent, RESULT_CREATE_ACTIVITY);
				return true;
			}
		});
		
		
		// click on item in ListView
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {               
				
				Task mTask = itemListAdaptor.getItem(position);
				
				if (mTask.getStartTime()!=-1L && mTask.getEndTime()!=-1L) return; //All time filled, to correct it - edit item
				
				int curTimeType = DialogDateTime.SET_START_TIME;
				if (mTask.getStartTime()!=-1L) curTimeType = DialogDateTime.SET_END_TIME;
				
				mDialogSetDateTime.showForTask(MainActivity.this, mTask, curTimeType, position);
			}
		});

    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_CREATE_ACTIVITY) {
        	
        	//Button save was pressed, we got result
            if(resultCode == Activity.RESULT_OK){
            	
            	Task task = data.getParcelableExtra(DATA);           
            	int mPosition = data.getIntExtra(POSITION, -1);
                
                if (mPosition == -1 ) itemListAdaptor.AddTask(task);  
                else itemListAdaptor.getTasks().set(mPosition, task);
                
                
                mPrefsHelper.saveTasks(itemListAdaptor.getTasks());
                itemListAdaptor.notifyDataSetChanged();
                
            }
            
            //Button exit was pressed, no result
            if (resultCode == Activity.RESULT_CANCELED) {
           
            }
        }
    }//onActivityResult
    
    
    






	@Override
	public void Update(Task task, int position) {
		
		//Update listView
		mPrefsHelper.saveTasks(itemListAdaptor.getTasks());
		itemListAdaptor.notifyDataSetChanged();
		
	}
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_fill) { //Fill list
        	
        	addTasksToListView();        	
           return true;
        }
        
/*        if (id == R.id.action_load) {
        	
        	loadFromPrefs();        	
           return true;
        }*/
        
        


        return super.onOptionsItemSelected(item);
    }
    
    
    
    void loadFromPrefs()  //Load list from prefs
    {
        this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mPrefsHelper.loadTasks(itemListAdaptor.getTasks());
				itemListAdaptor.notifyDataSetChanged();
				
			}
		});
    }

    
    void addTasksToListView() //Fill list, with item count three time much more, than items visible
    {
    	
    	mListView.setSelection(0);  //set first item visible 
    	
    	breakCycle  = false;
    	checkIsListFull();
    	
    	if (breakCycle) return; //check is list full
    	
    	final Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this) {
                    	
                    	
                    	while (!breakCycle)
                    	{

                    	
                    		wait(55);

                    		runOnUiThread(new Runnable() {
                    			@Override
                    			public void run() {
                    				
                    				int index = (itemListAdaptor.getTasks().size()+1);
                    				
                    				
                    				//Add new task and save tasks
                            		Task mTask = new Task("Test"+Integer.toString(index),"test"+Integer.toString(index));
                            		itemListAdaptor.getTasks().add(mTask);
                            		mPrefsHelper.saveTasks(itemListAdaptor.getTasks());
                            		
                    				checkIsListFull();
 
                    			}
                    		}); //runOnUiThread
                        
                    	}//while (true)
                    	
                    	
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            };
        };  
    	
        thread.start();
    	
    	
    	
    	

    }

    void checkIsListFull()
    {
    	itemListAdaptor.notifyDataSetChanged();
    	   	
    	int mLastVisible = mListView.getLastVisiblePosition(); 
    	int mFirstVisible = mListView.getFirstVisiblePosition();
    	 
        if (mFirstVisible!=-1 && mLastVisible!=-1)
        	if (itemListAdaptor.getTasks().size()>(mLastVisible - mFirstVisible+1)*3) breakCycle = true; //if added items in three times more than is visible break cycle
        

    }

}
