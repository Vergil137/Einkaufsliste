package com.example.einkaufsliste;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter {
	private final Activity context;

	//to store the animal images

	//to store the list of countries
	private final String title;

	//to store the list of countries
	private final String[] items;

	public ListAdapter(Activity context, String title, String[] items) {

		super(context, R.layout.row_places_layout, items);

		this.context = context;
		this.title = title;
		this.items = items;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.row_places_layout, null, true);

		//this code gets references to objects in the listview_row.xml file
		TextView nameTextField = rowView.findViewById(R.id.shopTitle);
		TextView infoTextField = rowView.findViewById(R.id.shopDetails);

		//this code sets the values of the objects to values from the arrays
		nameTextField.setText(title);
		infoTextField.setText(items[position]);

		return rowView;
//		return super.getView(position, view, parent);
	}
}
