package com.example.einkaufsliste;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		applyTheme();
//		setTheme(R.style.DarkTheme);
		setContentView(R.layout.activity_main);
		BottomNavigationView navView = findViewById(R.id.nav_view);
		navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		loadFragment(new ShopListFragment());
	}

	protected void applyTheme() {
		SharedPreferences theme = getPreferences(Context.MODE_PRIVATE);
		boolean b = theme.getBoolean(getString(R.string.setting_theme), false);
		if (b) {
			setTheme(R.style.DarkTheme);
		} else {
			setTheme(R.style.AppTheme);
		}
	}

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			Fragment fragment;
			switch (item.getItemId()) {
				case R.id.navigation_list:
					fragment = new ShopListFragment();
					loadFragment(fragment);
					return true;
				case R.id.navigation_places:
					fragment = new PlacesFragment();
					loadFragment(fragment);
					return true;
				case R.id.navigation_map:
					fragment = new MapFragment();
					loadFragment(fragment);
					return true;
				case R.id.navigation_settings:
					fragment = new SettingsFragment();
					loadFragment(fragment);
					return true;
			}
			return false;
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		BottomNavigationView navView = findViewById(R.id.nav_view);
		MenuItem item = navView.getMenu().getItem(0);
		navView.setSelectedItemId(item.getItemId());
	}

	private void loadFragment(Fragment fragment) {
		// load fragment
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
