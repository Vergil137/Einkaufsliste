package com.example.einkaufsliste;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static android.support.v4.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

	private static final int MY_LOCATION_REQUEST_CODE = 25;
	private MapView mapView;
	private GoogleMap mMap;
	private static final String TAG = MapFragment.class.getSimpleName();

	public MapFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_map, container, false);

		mapView = v.findViewById(R.id.map_view);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(this);
		return v;
	}

	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		checkPermission();

		UiSettings uiSettings = mMap.getUiSettings();
		uiSettings.setMapToolbarEnabled(true);
		uiSettings.setCompassEnabled(true);
		uiSettings.setAllGesturesEnabled(true);
		uiSettings.setZoomControlsEnabled(true);
		uiSettings.setMyLocationButtonEnabled(true);

		if (mMap.isMyLocationEnabled()) {
			locate();
		} else {
			if (Geocoder.isPresent()) {
				try {
					String location = "Bern";
					Geocoder gc = new Geocoder(getContext());
					List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects

					List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
					for (Address a : addresses) {
						if (a.hasLatitude() && a.hasLongitude()) {
							ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
						}
					}
				} catch (IOException e) {
					// handle the exception
				}
			}
		}

//		LatLng sydney = new LatLng(40.7143528, -74.0059731);
//		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
	}

	protected void checkPermission() {
		if (checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
				== PackageManager.PERMISSION_GRANTED) {
			if (!mMap.isMyLocationEnabled()) {
				enableLocation();

				mMap.setMyLocationEnabled(true);
			}
		} else {
			if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
					android.Manifest.permission.ACCESS_FINE_LOCATION)) {
				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case DialogInterface.BUTTON_POSITIVE:
								requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
										MY_LOCATION_REQUEST_CODE);
								break;
							default:
								break;
						}
					}
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setMessage(R.string.ask_location_permission).setPositiveButton(R.string.yes, dialogListener).setNegativeButton(R.string.no, dialogListener).show();
				Log.println(Log.ASSERT, TAG, "check1");
			} else {
				// No explanation needed; request the permission
				requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
						MY_LOCATION_REQUEST_CODE);
				Log.println(Log.ASSERT, TAG, "check2");

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		}
	}

	protected void enableLocation() {
		DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				checkPermission();
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						Log.println(Log.ASSERT, TAG, "true");
						startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
						mMap.setMyLocationEnabled(true);
						break;
					default:
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMessage(R.string.ask_location).setIcon(R.drawable.ic_place_black_24dp).setTitle(R.string.menu_maps).setPositiveButton(R.string.yes, dialogListener).setNegativeButton(R.string.no, dialogListener).show();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case MY_LOCATION_REQUEST_CODE: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// Permission granted
					enableLocation();

				} else {
					Log.println(Log.ASSERT, TAG, "RequestFalse");
					mMap.setMyLocationEnabled(false);
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}

			// Other Permissions
		}
	}

	private void locate() throws SecurityException {
		LocationManager locM = getSystemService(getContext(), LocationManager.class);
		if (locM == null) {
			return;
		}
		Location loc = locM.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		if (loc != null) {
			LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
//			mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
		}
	}
}
