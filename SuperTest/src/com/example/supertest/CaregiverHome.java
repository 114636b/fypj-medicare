package com.example.supertest;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.Toast;

public class CaregiverHome extends Activity implements ISideNavigationCallback {
	String[] items={"MR LEE","MR CHIT " ,"MR Boon","mr wazzup","???","mr wazzup","???","mr wazzup","???"};
	ArrayAdapter<CharSequence> adapter1;
	ListView lv;
	   public static final String EXTRA_TITLE = "com.devspark.sidenavigation.sample.extra.MTGOBJECT";
	    public static final String EXTRA_RESOURCE_ID = "com.devspark.sidenavigation.sample.extra.RESOURCE_ID";
	    public static final String EXTRA_MODE = "com.devspark.sidenavigation.sample.extra.MODE";
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    Intent myIntent;
    private SideNavigationView sideNavigationView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
	 private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;
	 
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caregiverhome);
		
		
		
//		lv=(ListView)findViewById(R.id.listElder);
//		//insert database code to retrieve elderly info and then store it into adapter
//		
//		adapter1 = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,items);
//		lv.setAdapter(adapter1);
		
		//sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);

        if (getIntent().hasExtra(EXTRA_TITLE)) {
            String title = getIntent().getStringExtra(EXTRA_TITLE);
            int resId = getIntent().getIntExtra(EXTRA_RESOURCE_ID, 0);
            setTitle(title);
            
            sideNavigationView.setMode(getIntent().getIntExtra(EXTRA_MODE, 0) == 0 ? Mode.LEFT : Mode.RIGHT);
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
//		
		// get the listview
        expListView = (ExpandableListView) findViewById(R.id.listElder);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
        	 
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
        
        
     // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
         
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
         
            }
        });
	}
	  private void prepareListData() {
	        listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, List<String>>();
	 
	        // Adding child data
	        listDataHeader.add("My Elders");
	  
	 
	        // Adding child data
	        List<String> Elder = new ArrayList<String>();
	        Elder.add("lim");
	        Elder.add("poi");
	        Elder.add("loi");
	        Elder.add("Pulp Fiction");
	        Elder.add("doi");
	        Elder.add("hoi");
	        Elder.add("haha");
	 
	      
	 
	        listDataChild.put(listDataHeader.get(0),Elder); // Header, Child data
	    
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            sideNavigationView.toggleMenu();
	            return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
}
	//@Override
//	public void onSideNavigationItemClick(int itemId){
//	switch (itemId) {
//    case R.id.side_navigation_menu_item1:
//    	//do what
//        break;
//
//    case R.id.side_navigation_menu_item2:
//        //do wat
//    	myIntent= new Intent(this,CaregiverHome.class);
//    	startActivity(myIntent);
//        break;
//
//  
//
//    default:
//        return;
//}
//finish();
//}
	@Override
	public void onSideNavigationItemClick(int itemId) {
		// TODO Auto-generated method stub
		
	}
}