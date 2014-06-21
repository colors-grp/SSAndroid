package com.colors.supersaym.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colors.supersaym.R;

public class GuideDetailsActivity extends Fragment {

	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_guide_details, container,
				false);

		return rootView;
	}





}
