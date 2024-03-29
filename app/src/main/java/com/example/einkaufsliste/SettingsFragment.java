package com.example.einkaufsliste;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements OnCheckedChangeListener {

	Switch sw;
	SharedPreferences preferences;

	public SettingsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		View v = this.getView();
		sw = v.findViewById(R.id.sw_theme);
		preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
		sw.setChecked(preferences.getBoolean(getString(R.string.setting_theme),false));
		sw.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		savePref(getString(R.string.setting_theme), isChecked);
//		if (isChecked) {
//			getActivity().getApplication().setTheme(R.style.DarkTheme);
//		} else {
//			getActivity().getApplication().setTheme(R.style.AppTheme);
//		}
//		TaskStackBuilder.create(getActivity())
//				.addNextIntent(new Intent(getActivity(), MainActivity.class))
//				.addNextIntent(getActivity().getIntent()).addNextIntent(getFragmentManager().beginTransaction().replace(this, this))
//				.startActivities();
		getActivity().recreate();
//		getFragmentManager().beginTransaction().attach(this).commit();
//		((MainActivity)getActivity()).loadFragment(getFragmentManager().findFragmentById(this.getId()));

	}

	protected void savePref(String key, boolean isChecked) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, isChecked);
		editor.commit();
	}
}
