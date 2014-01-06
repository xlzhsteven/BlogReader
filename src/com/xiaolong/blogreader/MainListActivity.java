package com.xiaolong.blogreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

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
		if (isNetworkAvailable()) {
			GetBlogPostTask getBlogPostTask = new GetBlogPostTask();
			getBlogPostTask.execute();
		}else {
			Toast.makeText(this, "Network is unavailable!!", Toast.LENGTH_LONG).show();
		}

		// Toast.makeText(this, getString(R.string.no_items),
		// Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}
		return isAvailable;
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
				if (responseCode==HttpURLConnection.HTTP_OK) {
					InputStream inputStream = connection.getInputStream();
					Reader reader = new InputStreamReader(inputStream);
					int contentLength = connection.getContentLength();
					char[] charArray = new char[contentLength];
					reader.read(charArray);
					String responseData = new String(charArray);
					
					// Handle JSON response
					JSONObject jsonResponse = new JSONObject(responseData);
					String status = jsonResponse.getString("status");
					Log.v(TAG, status);
					JSONArray jsonPosts = jsonResponse.getJSONArray("posts");
					for (int i = 0; i < jsonPosts.length(); i++) {
						JSONObject jsonPost = jsonPosts.getJSONObject(i);
						String title = jsonPost.getString("title");
						Log.v(TAG, "Post " + i + ": " + title);
					}
				}else {
					Log.i(TAG, "Unsuccessful Http Code" + responseCode);
				}
				
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
