package com.colors.supersaym;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.colors.supersaym.controller.communication.AsyncTaskInvoker;
import com.colors.supersaym.controller.communication.ConnectionDetector;
import com.colors.supersaym.controller.communication.Task;
import com.colors.supersaym.controller.tasks.MeScoreBoardTask;
import com.colors.supersaym.dataprovider.DataRequestor;
import com.colors.supersaym.storage.Constants;
import com.colors.supersaym.storage.UIManager;

public class MeActivity extends Activity implements DataRequestor {

	TextView headerTxt, userName, mosalast, mnlqatel, sallyseyamk;
	private ProgressDialog mSpinnerProgress;
	SharedPreferences appPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_me);
		 appPreferences = getSharedPreferences(
				Constants.appPreferencesName, Context.MODE_PRIVATE);

		headerTxt = (TextView) findViewById(R.id.header_txt);
		headerTxt.setText(R.string.me);

		userName = (TextView) findViewById(R.id.name);
		if (appPreferences.contains(Constants.userName)) {

			userName.setText(appPreferences.getString(Constants.userName, ""));
			Log.d("Shaimaa", "FB USerID "+appPreferences.getString(Constants.userID, ""));

		}
		mosalast = (TextView) findViewById(R.id.alfScore);
		sallyseyamk = (TextView) findViewById(R.id.games);
		mnlqatel = (TextView) findViewById(R.id.killer);

		requestData();
	}

	private void requestData() {

		if (ConnectionDetector.getInstance(getApplicationContext())
				.isConnectingToInternet()) {
			mSpinnerProgress = new ProgressDialog(MeActivity.this);
			mSpinnerProgress.setIndeterminate(true);
			mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mSpinnerProgress.setMessage("Loading ....");
			mSpinnerProgress.show();
			Task task = new MeScoreBoardTask(this, getApplicationContext(),
					appPreferences.getString(Constants.userID, ""));
			AsyncTaskInvoker.RunTaskInvoker(task);
		} else {
			Toast.makeText(getApplicationContext(), "No Internet Connection",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.me, menu);
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

	}

	@Override
	public void onFinish(Task task) {
		mnlqatel.setText(UIManager.getInstance().getMmanElQatel());
		mosalast.setText(UIManager.getInstance().getmMosalslatScore());
		sallyseyamk.setText(UIManager.getInstance().getmSallySyamakScore());
		mSpinnerProgress.cancel();

	}

}
