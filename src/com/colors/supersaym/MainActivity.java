package com.colors.supersaym;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.colors.supersaym.storage.Constants;
import com.colors.supersaym.storage.UIManager;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class MainActivity extends Activity {
	TextView welcome;
	ProfilePictureView profilePictureView;
	Button loginButotn;
	Activity parentActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		parentActivity=this;
		welcome = (TextView) findViewById(R.id.welcome);
		profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);

		loginButotn = (Button) findViewById(R.id.loginBtn);
		
		loginButotn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginFB();
			}
		});
	}

			private void loginFB() {
//				if (Session.getActiveSession() != null
//						&& Session.getActiveSession().isOpened()) {
//					
//					Log.d("Shaimaa", "u r already connected with facebook");
//					return;
//				}
				// start Facebook Login
				Session.openActiveSession(this, true,
						new Session.StatusCallback() {

							// callback when session changes state
							@Override
							public void call(Session session,
									SessionState state, Exception exception) {
								if (session.isOpened()) {
									// make request to the /me API
									Request.newMeRequest(session,
											new Request.GraphUserCallback() {

												// callback after Graph API
												// response with user
												// object
												@Override
												public void onCompleted(
														GraphUser user,
														Response response) {
													if (user != null) {

														profilePictureView
																.setProfileId(user
																		.getId());
														welcome.setText(user
																.getName());
														SharedPreferences appPreferences=getSharedPreferences(Constants.appPreferencesName, Context.MODE_PRIVATE);
														SharedPreferences.Editor editor = appPreferences.edit();
														editor.putString(Constants.userName, user.getName());
														editor.putString(Constants.userID, user.getId());
														editor.commit();

														Log.d("Shaimaa",
																"" + appPreferences.getString(Constants.userName, ""));
														UIManager.getInstance().setName(user.getFirstName());
														UIManager.getInstance().setId(user.getId());
													UIManager.getInstance().startMainActivity(parentActivity);
													}
												}
											}).executeAsync();
								}
							}
						});
		

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}
}
