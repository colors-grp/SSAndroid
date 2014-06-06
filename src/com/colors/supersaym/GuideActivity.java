package com.colors.supersaym;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GuideActivity extends Activity {
	// All static variables
	static final String URL = "http://gloryette.org/mobile/index.php?/api/hitcall/tv_guide/format/json";
	// JSON node keys
	static final String KEY_NAME = "name"; // parent node
	static final String KEY_TIME = "time";
	static final String KEY_THUMB_URL = "link";
	static final String KEY_DETAILS = "details";
	private ProgressDialog pDialog;

	// Guide JSONArray
	JSONArray series = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> guideList;

	ListView list;
	GuideAdapter adapter;

	TextView headerTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		headerTxt = (TextView) findViewById(R.id.header_txt);
		headerTxt.setText(R.string.guide);

		list = (ListView) findViewById(R.id.list);
		guideList = new ArrayList<HashMap<String, String>>();
		new HttpAsyncTask().execute();


		

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(getApplicationContext(),
						GuideDetailsActivity.class);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guide, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, Void> {
		private String response;
		HttpEntity httpEntity = null;
		HttpResponse httpResponse = null;
		List<NameValuePair> params = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.v("----pre execute-----", "---");
			// Showing progress dialog
			pDialog = new ProgressDialog(GuideActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			Log.v("----background-----", "---");
			// http client
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Checking http request method type
			HttpGet httpGet = new HttpGet(URL);
			// adding post params
			params = new ArrayList<NameValuePair>();
			try {
				httpResponse = httpClient.execute(httpGet);
				httpEntity = httpResponse.getEntity();
				response = EntityUtils.toString(httpEntity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Log.d("----------Response: -----------", "> " + response);

			if (response != null) {
				try {
					JSONArray series = new JSONArray(response);

					Log.d("----------array length: -----------", "> " + series.length());
					// looping through All series
					for (int i = 0; i < series.length(); i++) {
						JSONObject c = series.getJSONObject(i);

						String name = c.getString(KEY_NAME);
						String time = c.getString(KEY_TIME);
						String details = c.getString(KEY_DETAILS);
						String image = c.getString(KEY_THUMB_URL);

						// tmp hashmap for single series
						HashMap<String, String> mosalsal = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						mosalsal.put(KEY_NAME, name);
						mosalsal.put(KEY_TIME, time);
						mosalsal.put(KEY_DETAILS, details);
						mosalsal.put(KEY_THUMB_URL, image);

						// adding contact to contact list
						guideList.add(mosalsal);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;

		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

			Toast.makeText(getBaseContext(), "Done !", Toast.LENGTH_LONG)
					.show();
			
			Log.v("----guide list size---", guideList.size()+"");
			// Getting adapter by passing xml data ArrayList
			adapter = new GuideAdapter(GuideActivity.this, guideList);
			list.setAdapter(adapter);
		}
	}

}
