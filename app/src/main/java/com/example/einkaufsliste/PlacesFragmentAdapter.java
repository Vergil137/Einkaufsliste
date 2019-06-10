package com.example.einkaufsliste;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacesFragmentAdapter extends ArrayAdapter {
	//to reference the Activity
	private final Activity context;

	//to store the animal images
	private final Integer[] imageIDarray;

	//to store the list of countries
	private final String[] nameArray;

	//to store the list of countries
	private final String[] infoArray;

	public PlacesFragmentAdapter(Activity context, String[] nameArrayParam, String[] infoArrayParam, Integer[] imageIDArrayParam) {

		super(context, R.layout.row_places_layout, nameArrayParam);

		this.context = context;
		this.imageIDarray = imageIDArrayParam;
		this.nameArray = nameArrayParam;
		this.infoArray = infoArrayParam;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.row_places_layout, null, true);

		//this code gets references to objects in the listview_row.xml file
		TextView nameTextField = rowView.findViewById(R.id.shopTitle);
		TextView infoTextField = rowView.findViewById(R.id.shopDetails);
		ImageView imageView = rowView.findViewById(R.id.imageView);

		//this code sets the values of the objects to values from the arrays
		nameTextField.setText(nameArray[position]);
		infoTextField.setText(infoArray[position]);
		imageView.setImageResource(imageIDarray[position]);

		return rowView;
//		return super.getView(position, view, parent);
	}


}
