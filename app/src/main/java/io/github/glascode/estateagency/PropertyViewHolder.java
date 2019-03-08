package io.github.glascode.estateagency;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class PropertyViewHolder extends RecyclerView.ViewHolder {

	private ImageView propertyImageView;
	private TextView propertyTitleText;
	private TextView propertyPriceText;
	private TextView propertyLocationText;
	private TextView propertyPublicationDateText;

	PropertyViewHolder(@NonNull View itemView) {
		super(itemView);

		propertyImageView = itemView.findViewById(R.id.image_property);
		propertyTitleText = itemView.findViewById(R.id.text_property_item_title);
		propertyPriceText = itemView.findViewById(R.id.text_property_item_price);
		propertyLocationText = itemView.findViewById(R.id.text_property_item_location);
		propertyPublicationDateText = itemView.findViewById(R.id.text_property_item_publication_date);
	}

	void bind(Property property) {
		new DownloadImageTask(propertyImageView).execute(property.getImages().get(0));
		propertyTitleText.setText(property.getTitre());
		propertyPriceText.setText(String.format("%d", property.getPrix()) + " €");
		propertyLocationText.setText(property.getVille());
		propertyPublicationDateText.setText(DateFormat.format("dd MMMM yyyy", property.getDate()).toString());
	}
}
