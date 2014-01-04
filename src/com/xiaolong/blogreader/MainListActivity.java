package com.xiaolong.blogreader;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainListActivity extends ListActivity {

	/*protected String[] mAndroidNames = { "Froyo", "Gingerbread", "Honeycomb",
			"Ice Cream Sandwich", "Jelly Bean", "KitKat" };*/
	protected String[] mAndroidNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);
		mAndroidNames = getResources().getStringArray(R.array.android_names);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAndroidNames);
		setListAdapter(adapter);
//		Toast.makeText(this, getString(R.string.no_items), Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}

}
