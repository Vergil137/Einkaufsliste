package com.example.einkaufsliste;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {

	ListView listView;
	String[] nameArray;
	String[] infoArray;
	Integer[] imageArray;

	public PlacesFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		PlacesFragmentAdapter adapter = new PlacesFragmentAdapter(getActivity(), nameArray, infoArray, imageArray);

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_places, container, false);
	}

}
