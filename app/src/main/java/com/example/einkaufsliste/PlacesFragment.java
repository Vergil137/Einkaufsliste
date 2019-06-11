package com.example.einkaufsliste;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

//https://developers.google.com/android/guides/setup


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {

	ListView listView;
	String[] nameArray;
	String[] infoArray;
	Integer[] imageArray;

	PlacesClient placesClient;



	public PlacesFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		Places.initialize(getContext(),getString(R.string.google_maps_key));
		placesClient = Places.createClient(getContext());

		List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME);

// Use the builder to create a FindCurrentPlaceRequest.
		FindCurrentPlaceRequest request =
				FindCurrentPlaceRequest.builder(placeFields).build();

// Call findCurrentPlace and handle the response (first check that the user has granted permission).
		if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
			placeResponse.addOnCompleteListener(task -> {
				if (task.isSuccessful()){
					FindCurrentPlaceResponse response = task.getResult();
					for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
						Log.i("Places", String.format("Place '%s' has likelihood: %f",
								placeLikelihood.getPlace().getName(),
								placeLikelihood.getLikelihood()));
					}
				} else {
					Exception exception = task.getException();
					if (exception instanceof ApiException) {
						ApiException apiException = (ApiException) exception;
						Log.e("t", "Place not found: " + apiException.getStatusCode());
					}
				}
			});
		} else {
			// A local method to request required permissions;
			// See https://developer.android.com/training/permissions/requesting
			getLocationPermission();
		}
//		PlacesFragmentAdapter adapter = new PlacesFragmentAdapter(getActivity(), nameArray, infoArray, imageArray);
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_places, container, false);
	}

}
