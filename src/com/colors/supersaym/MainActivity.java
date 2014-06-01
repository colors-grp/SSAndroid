package com.colors.supersaym;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.ProfilePictureView;

import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends Activity {
	TextView welcome;
	ProfilePictureView    profilePictureView ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		welcome = (TextView) findViewById(R.id.welcome);
		    profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);


		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {
					// make request to the /me API
					Request.newMeRequest(session,
							new Request.GraphUserCallback() {

								// callback after Graph API response with user
								// object
								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									if (user != null) {
										
			            profilePictureView.setProfileId(user.getId());
			            welcome.setText( user.getFirstName());
			        
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
