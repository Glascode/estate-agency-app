package io.github.glascode.estateagency;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyViewHolder> {

	private List<Property> propertyList;

	PropertyAdapter(List<Property> propertyList) {
		this.propertyList = propertyList;
	}

	@NonNull
	@Override
	public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
		return new PropertyViewHolder(LayoutInflater
				.from(viewGroup.getContext())
				.inflate(R.layout.property_item, viewGroup, false)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull PropertyViewHolder propertyViewHolder, int position) {
		Property property = propertyList.get(position);
		propertyViewHolder.bind(property);
	}

	@Override
	public int getItemCount() {
		return propertyList.size();
	}

}
