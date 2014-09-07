package com.test.dvb;

import com.test.dvb.adapter.RecentFileAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
	private Button btn_open, btn_recent, btn_exit;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        btn_open = (Button) findViewById(R.id.button_open);
        btn_recent =(Button) findViewById(R.id.button_recent);
        btn_exit =(Button) findViewById(R.id.button_exit);
        
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new RecentFileAdapter().getFile2Vector(MainActivity.this);
			}
		}).start();
        
        btn_open.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Open();
			}
		});
        btn_recent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Recent();
			}
		});
        
        btn_exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
    }
    
    public void Open(){
    	Intent myIntent = new Intent(MainActivity.this, OpenActivity.class);
    	startActivity(myIntent);
    }
    
    public void Recent(){
    	Intent myIntent = new Intent(MainActivity.this, RecentActivity.class);
    	startActivity(myIntent);
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	new RecentFileAdapter().saveVector2File(this);
    	super.onDestroy();
    }
}
