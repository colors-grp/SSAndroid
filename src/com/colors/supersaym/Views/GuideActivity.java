package com.colors.supersaym.Views;

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

import com.colors.supersaym.GuideAdapter;
import com.colors.supersaym.R;
import com.colors.supersaym.controller.communication.AsyncTaskInvoker;
import com.colors.supersaym.controller.communication.ConnectionDetector;
import com.colors.supersaym.controller.communication.Task;
import com.colors.supersaym.controller.communication.Task.TaskID;
import com.colors.supersaym.controller.tasks.GuideTask;
import com.colors.supersaym.controller.tasks.MeScoreBoardTask;
import com.colors.supersaym.dataobjects.GuideData;
import com.colors.supersaym.dataprovider.DataRequestor;
import com.colors.supersaym.storage.UIManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GuideActivity extends Fragment implements DataRequestor{
	// All static variables
	static final String URL = "http://gloryette.org/mobile/index.php?/api/hitcall/tv_guide/format/json";
	// JSON node keys
	public static final String KEY_NAME = "name"; // parent node
	public static final String KEY_TIME = "time";
	public static final String KEY_THUMB_URL = "link";
	public static final String KEY_DETAILS = "details";
	private ProgressDialog pDialog;

	// Guide JSONArray
	JSONArray series = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> guideList;

	ListView list;
	GuideAdapter adapter;

	TextView headerTxt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_guide, container, false);
		
		headerTxt = (TextView) rootView.findViewById(R.id.header_txt);
		headerTxt.setText(R.string.guide);

		list = (ListView) rootView.findViewById(R.id.list);
		guideList = new ArrayList<HashMap<String, String>>();
//		new HttpAsyncTask().execute();

requestdata();
		

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//
//				Intent intent = new Intent(getActivity(),
//						GuideDetailsActivity.class);
//				startActivity(intent);
				GuideDetailsActivity details=new GuideDetailsActivity();
				addContentFragment(details,true);

			}
		});
		return rootView;
	}
	


	private void requestdata() {
		
		
		if (ConnectionDetector.getInstance(getActivity())
				.isConnectingToInternet()) {
		
			pDialog = new ProgressDialog(getActivity());
			pDialog.setIndeterminate(true);
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
			Task task = new GuideTask(this, getActivity());
			AsyncTaskInvoker.RunTaskInvoker(task);
		} else {
			Toast.makeText(getActivity(), "No Internet Connection",
					Toast.LENGTH_LONG).show();
		}

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



	@Override
	public void onStart(Task task) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onFinish(Task task) {

		if(task.getId()==TaskID.GuideTask)
		{
			ArrayList<GuideData>result=(ArrayList<GuideData>) task.getResult();
			adapter = new GuideAdapter(getActivity(), result);
			list.setAdapter(adapter);
			
		}
		pDialog.cancel();
	}
	public void addContentFragment(Fragment fragment, boolean addToBackStack) {
		FragmentManager fManager = getActivity().getSupportFragmentManager();
		Fragment currentFragment = fManager.findFragmentByTag(fragment
				.getClass().getName());
		if (currentFragment == null) {
			FragmentTransaction fTransaction = fManager.beginTransaction();
			fTransaction.replace(R.id.content_fram, fragment, fragment
					.getClass().getName());
			if (addToBackStack) {
				fTransaction.addToBackStack(null);
			}
			fTransaction.commit();
		}
	}
}
