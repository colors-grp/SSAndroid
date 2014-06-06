package com.colors.supersaym;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class HomeActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Resources ressources = getResources(); 
		String me = ressources.getString(R.string.me);
		String episodes = ressources.getString(R.string.episodes);
		String scoreboard = ressources.getString(R.string.scoreboard);
		String notification = ressources.getString(R.string.notification);
		String guide = ressources.getString(R.string.guide);
		
		TabHost tabHost = getTabHost(); 
 
		// Me tab
		Intent intentMe = new Intent().setClass(this, MeActivity.class);
		TabSpec tabSpecMe = tabHost
		  .newTabSpec(me)
		  .setIndicator(prepareTabView(me, R.drawable.me_icon_tab))
		  .setContent(intentMe);
		
		// Episodes tab
		Intent intentCollection = new Intent().setClass(this, EpisodesActivity.class);
		TabSpec tabSpecEpisode = tabHost
				.newTabSpec(episodes)
				.setIndicator(prepareTabView(episodes,R.drawable.episodes_icon_tab))
				.setContent(intentCollection);
 
		// Scoreboards tab
		Intent intentScore = new Intent().setClass(this, ScoreboardActivity.class);
		TabSpec tabSpecScore = tabHost
		  .newTabSpec(scoreboard)
		  .setIndicator(prepareTabView(scoreboard, R.drawable.scoreboard_icon_tab))
		  .setContent(intentScore);
 
 
		// Notifications tab
		Intent intentNotification = new Intent().setClass(this, NotificationActivity.class);
		TabSpec tabSpecNotification = tabHost
		  .newTabSpec(notification)
		  .setIndicator(prepareTabView(notification, R.drawable.notifications_icon_tab))
		  .setContent(intentNotification);
		
		// Guide tab
				Intent intentGuide = new Intent().setClass(this, GuideActivity.class);
				TabSpec tabSpecGuide = tabHost
				  .newTabSpec(guide)
				  .setIndicator(prepareTabView(guide, R.drawable.guide_icon_tab))
				  .setContent(intentGuide);
 
		// add all tabs 
		tabHost.addTab(tabSpecMe);
		tabHost.addTab(tabSpecEpisode);
		tabHost.addTab(tabSpecScore);
		tabHost.addTab(tabSpecNotification);
		tabHost.addTab(tabSpecGuide);
 
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);

	}
	
	
	private View prepareTabView(String text, int resId) {
	    View view = LayoutInflater.from(this).inflate(R.layout.tabs, null);
	    ImageView iv = (ImageView) view.findViewById(R.id.TabImageView);
	    TextView tv = (TextView) view.findViewById(R.id.TabTextView);
	    iv.setImageResource(resId);
	    tv.setText(text);
	    return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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

	
}
