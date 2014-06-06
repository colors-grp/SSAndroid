package com.colors.supersaym;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.colors.supersaym.Adapters.ScoreBoardAdapter;
import com.colors.supersaym.controller.communication.AsyncTaskInvoker;
import com.colors.supersaym.controller.communication.ConnectionDetector;
import com.colors.supersaym.controller.communication.Task;
import com.colors.supersaym.controller.tasks.AllScoreBoardTask;
import com.colors.supersaym.dataobjects.UserData;
import com.colors.supersaym.dataprovider.DataRequestor;
import com.colors.supersaym.storage.UIManager;

public class ScoreboardActivity extends Activity implements DataRequestor {

	TextView headerTxt;
	ListView scoreBoardList;
	ScoreBoardAdapter mAdapter;
	ProgressDialog mSpinnerProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoreboard);
		initUI();

	}

	private void initUI() {
		headerTxt = (TextView) findViewById(R.id.header_txt);
		headerTxt.setText(R.string.scoreboard);
		scoreBoardList = (ListView) findViewById(R.id.scroreBoardList);
		requestdata();
		// scroreBoardList
	}

	private void requestdata() {

		if (ConnectionDetector.getInstance(getApplicationContext())
				.isConnectingToInternet()) {
			mSpinnerProgress = new ProgressDialog(ScoreboardActivity.this);
			mSpinnerProgress.setIndeterminate(true);
			mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mSpinnerProgress.setMessage("Loading ....");
			mSpinnerProgress.show();
			Task scoreboard = new AllScoreBoardTask(this,
					this.getApplicationContext(), "1", "sallySyamak", 0, 10);
			AsyncTaskInvoker.RunTaskInvoker(scoreboard);
		} else {
			Toast.makeText(getApplicationContext(), "No Internet Connection",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scoreboard, menu);
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

	@Override
	public void onStart(Task task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(Task task) {
		final ArrayList<UserData>list= UIManager.getInstance()
				.getScoreBoardArray();
		Log.d("Shaimaa", "ScoreBoardList "+list.size());

		mAdapter = new ScoreBoardAdapter(ScoreboardActivity.this,list);
		scoreBoardList.setAdapter(mAdapter);
		scoreBoardList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("Shaimaa", "Id " + position);
				UserData clcikedItem = list.get(position);
				// UIManager.getInstance().startPrivalageActivity(CategoriesActivity.this);
				// finish();
			}
		});
		
		mSpinnerProgress.cancel();
		Log.d("Shaimaa", "ScoreBoardList "+list.size());
	}

}
