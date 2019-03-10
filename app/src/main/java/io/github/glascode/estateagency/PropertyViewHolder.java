package io.github.glascode.estateagency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.squareup.picasso.Picasso;
import io.github.glascode.estateagency.database.ActionPropertyTask;
import io.github.glascode.estateagency.model.Property;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

class PropertyViewHolder extends RecyclerView.ViewHolder {

	private final Context context;

	private final ImageView propertyImageView;
	private final TextView propertyTitleText;
	private final TextView propertyPriceText;
	private final TextView propertyLocationText;
	private final TextView propertyPublicationDateText;

	private final ToggleButton propertySaveButton;

	PropertyViewHolder(@NonNull View itemView) {
		super(itemView);

		context = itemView.getContext();

		propertyImageView = itemView.findViewById(R.id.image_property_item);
		propertyTitleText = itemView.findViewById(R.id.text_property_item_title);
		propertyPriceText = itemView.findViewById(R.id.text_property_item_price);
		propertyLocationText = itemView.findViewById(R.id.text_property_item_location);
		propertyPublicationDateText = itemView.findViewById(R.id.text_property_item_publication_date);

		propertySaveButton = itemView.findViewById(R.id.btn_save_property_item);
	}

	void bind(final Property property) {
		Picasso.get().load(property.getImages().get(0)).fit().centerCrop().into(propertyImageView);
		propertyTitleText.setText(property.getTitre());
		propertyPriceText.setText(String.format(Locale.FRANCE,"%d", property.getPrix()) + " €");
		propertyLocationText.setText(property.getVille());
		propertyPublicationDateText.setText(DateFormat.format("dd MMMM yyyy", property.getDate() * 1000).toString());

		try {
			String result = new ActionPropertyTask(context, property, "get").execute().get();
			if (result != null && result.equals(property.getId()))
				propertySaveButton.setChecked(true);
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		propertySaveButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					new ActionPropertyTask(context, property, "insert").execute();
				else
					new ActionPropertyTask(context, property, "remove").execute();
			}
		});
	}
}
