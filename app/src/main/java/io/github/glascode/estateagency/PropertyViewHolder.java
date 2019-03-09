package io.github.glascode.estateagency;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.Locale;

class PropertyViewHolder extends RecyclerView.ViewHolder {

	private final ImageView propertyImageView;
	private final TextView propertyIdText;
	private final TextView propertyTitleText;
	private final TextView propertyPriceText;
	private final TextView propertyLocationText;
	private final TextView propertyPublicationDateText;

	PropertyViewHolder(@NonNull View itemView) {
		super(itemView);

		propertyImageView = itemView.findViewById(R.id.image_property_item);
		propertyIdText = itemView.findViewById(R.id.text_property_item_id);
		propertyTitleText = itemView.findViewById(R.id.text_property_item_title);
		propertyPriceText = itemView.findViewById(R.id.text_property_item_price);
		propertyLocationText = itemView.findViewById(R.id.text_property_item_location);
		propertyPublicationDateText = itemView.findViewById(R.id.text_property_item_publication_date);
	}

	void bind(Property property) {
		Picasso.get().load(property.getImages().get(0)).fit().centerCrop().into(propertyImageView);
		propertyIdText.setText(property.getId());
		propertyTitleText.setText(property.getTitre());
		propertyPriceText.setText(String.format(Locale.FRANCE,"%d", property.getPrix()) + " €");
		propertyLocationText.setText(property.getVille());
		propertyPublicationDateText.setText(DateFormat.format("dd MMMM yyyy", property.getDate()).toString());
	}
}
