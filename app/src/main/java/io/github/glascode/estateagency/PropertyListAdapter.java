package io.github.glascode.estateagency;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Picasso;
import io.github.glascode.estateagency.database.GetPropertyTask;
import io.github.glascode.estateagency.database.InsertPropertyTask;
import io.github.glascode.estateagency.database.RemovePropertyTask;
import io.github.glascode.estateagency.model.Property;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

class PropertyListAdapter extends BaseAdapter {
    private final Context context;
    private final List<Property> propertyList;

    public PropertyListAdapter(Context context, List<Property> propertyList) {
        this.context = context;
        this.propertyList = propertyList;
    }

    @Override
    public int getCount() {
        return propertyList.size();
    }

    @Override
    public Object getItem(int position) {
        return propertyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PropertyItemViewHolder propertyItemViewHolder;

        if (convertView == null) {
            propertyItemViewHolder = new PropertyItemViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.property_item, null);

            propertyItemViewHolder.propertyImageView = convertView.findViewById(R.id.image_item_property);
            propertyItemViewHolder.propertyTitleText = convertView.findViewById(R.id.text_property_item_title);
            propertyItemViewHolder.propertyPriceText = convertView.findViewById(R.id.text_property_item_price);
            propertyItemViewHolder.propertyLocationText = convertView.findViewById(R.id.text_property_item_location);
            propertyItemViewHolder.propertyPublicationDateText = convertView.findViewById(R.id.text_property_item_publication_date);
            propertyItemViewHolder.propertySaveButton = convertView.findViewById(R.id.btn_save_property_item);

            convertView.setTag(propertyItemViewHolder);
        } else
            propertyItemViewHolder = (PropertyItemViewHolder) convertView.getTag();

        String propertyPrice = String.format(Locale.FRANCE, "%d", propertyList.get(position).getPrix()) + " €";
        String propertyLocation = propertyList.get(position).getCodePostal() + " " + propertyList.get(position).getVille();

        Picasso.get().load(propertyList.get(position).getImages().get(0)).fit().centerCrop().into(propertyItemViewHolder.propertyImageView);
        propertyItemViewHolder.propertyTitleText.setText(propertyList.get(position).getTitre());
        propertyItemViewHolder.propertyPriceText.setText(propertyPrice);
        propertyItemViewHolder.propertyLocationText.setText(propertyLocation);
        propertyItemViewHolder.propertyPublicationDateText.setText(DateFormat.format("dd MMMM yyyy", propertyList.get(position).getDate() * 1000).toString());
        propertyItemViewHolder.propertySaveButton.setFocusable(false);

        try {
            Property result = new GetPropertyTask(context, propertyList.get(position).getId()).execute().get();
            if (result != null && result.getId().equals(propertyList.get(position).getId()))
                propertyItemViewHolder.propertySaveButton.setChecked(true);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        propertyItemViewHolder.propertySaveButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    new InsertPropertyTask(context, propertyList.get(position)).execute();
                else
                    new RemovePropertyTask(context, propertyList.get(position)).execute();
            }
        });

        return convertView;
    }
}

class PropertyItemViewHolder {
    ImageView propertyImageView;
    TextView propertyTitleText;
    TextView propertyPriceText;
    TextView propertyLocationText;
    TextView propertyPublicationDateText;

    ToggleButton propertySaveButton;
}
