package io.github.glascode.estateagency;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
        propertyTitleText = itemView.findViewById(R.id.text_property_title);
        propertyPriceText = itemView.findViewById(R.id.text_property_price);
        propertyLocationText = itemView.findViewById(R.id.text_property_location);
        propertyPublicationDateText = itemView.findViewById(R.id.text_property_publication_date);
    }

    void bind(Property property) {
        propertyImageView.setImageURI((Uri) property.getImagesUrls());
        propertyTitleText.setText(property.getTitle());
        propertyPriceText.setText(property.getPrice());
        propertyLocationText.setText(property.getCity());
        propertyPublicationDateText.setText((CharSequence) property.getDate());
    }
}
