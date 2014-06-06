package com.colors.supersaym;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class EpisodesActivity extends Activity {
	ImageView alfleila, mnqatel, mosalsalat, sallyseyamak;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_episodes);
		Log.v("episode", "-----------ep");
		
		alfleila = (ImageView) findViewById(R.id.alfleila);
		mnqatel = (ImageView) findViewById(R.id.mnqatel);
		mosalsalat = (ImageView) findViewById(R.id.mosalsalat);
		sallyseyamak = (ImageView) findViewById(R.id.sallyseyamak);

		alfleila.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alfleila.setImageResource(R.drawable.alfleilaselected);
				mnqatel.setImageResource(R.drawable.mnqatel);
				mosalsalat.setImageResource(R.drawable.mosalsalat);
				sallyseyamak.setImageResource(R.drawable.sallyseyamak);
			}
		});
		
		mnqatel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alfleila.setImageResource(R.drawable.alfleila);
				mnqatel.setImageResource(R.drawable.mnqatelselected);
				mosalsalat.setImageResource(R.drawable.mosalsalat);
				sallyseyamak.setImageResource(R.drawable.sallyseyamak);
			}
		});
		
		mosalsalat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alfleila.setImageResource(R.drawable.alfleila);
				mnqatel.setImageResource(R.drawable.mnqatel);
				mosalsalat.setImageResource(R.drawable.mosalsalatselected);
				sallyseyamak.setImageResource(R.drawable.sallyseyamak);
			}
		});
		
		sallyseyamak.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alfleila.setImageResource(R.drawable.alfleila);
				mnqatel.setImageResource(R.drawable.mnqatel);
				mosalsalat.setImageResource(R.drawable.mosalsalat);
				sallyseyamak.setImageResource(R.drawable.sallyseyamakselected);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.episodes, menu);
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
