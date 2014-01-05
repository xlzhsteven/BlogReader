package com.xiaolong.blogreader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainListActivity extends ListActivity {

	/*
	 * protected String[] mAndroidNames = { "Froyo", "Gingerbread", "Honeycomb",
	 * "Ice Cream Sandwich", "Jelly Bean", "KitKat" };
	 */
	protected String[] mBlogPostTitles;

	// Create variable to use to get info from the website
	public static final int NUMBER_OF_POSTS = 20;
	public static final String TAG = MainListActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);

		GetBlogPostTask getBlogPostTask = new GetBlogPostTask();
		getBlogPostTask.execute();

		// Toast.makeText(this, getString(R.string.no_items),
		// Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}

	private class GetBlogPostTask extends AsyncTask<Object, Void, String> {

		@Override
		protected String doInBackground(Object... params) {
			int responseCode = -1;
			// TODO Auto-generated method stub
			try {
				URL blogFeedURL = new URL(
						"http://blog.teamtreehouse.com/api/get_recent_summary/?count="
								+ NUMBER_OF_POSTS);
				HttpURLConnection connection = (HttpURLConnection) blogFeedURL
						.openConnection();
				connection.connect();
				responseCode = connection.getResponseCode();
				Log.i(TAG, "Code" + responseCode);
			} catch (MalformedURLException e) {
				// TODO: handle exception
				Log.e(TAG, "Exception Caught: ", e);
			} catch (IOException e) {
				// TODO: handle exception
				Log.e(TAG, "Exception Caught", e);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "Exception Caught", e);
			}
			return "Code" + responseCode;
		}

	}

}
