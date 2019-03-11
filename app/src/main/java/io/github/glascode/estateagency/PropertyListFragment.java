package io.github.glascode.estateagency;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;
import io.github.glascode.estateagency.model.Property;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PropertyListFragment extends ListFragment {
	private OnPropertySelectedListener callback;

	private String jsonPropertyList;
	private List<Property> propertyList;

	private ArrayList<HashMap<String, Object>> data;

	public interface OnPropertySelectedListener {
		void onItemSelected(int position);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		jsonPropertyList = getArguments().getString("json_property_list");

		data = new ArrayList<>();

		Moshi moshi = new Moshi.Builder().build();

		Type type = Types.newParameterizedType(List.class, Property.class);
		JsonAdapter<List<Property>> adapter = moshi.adapter(type);

		try {
			propertyList = adapter.fromJson(jsonPropertyList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		HashMap<String, Object> map;

		for (Property property : propertyList) {
			map = new HashMap<>();

			map.put("property_image", Picasso.get().load(property.getImages().get(0)));
			map.put("property_title", property.getTitre());
			map.put("property_price", String.format(Locale.FRANCE,"%d", property.getPrix()) + " €");
			map.put("property_location", property.getVille());
			map.put("property_publication_date", DateFormat.format("dd MMMM yyyy", property.getDate() * 1000).toString());

			data.add(map);
		}

		String[] from = {"property_image", "property_title", "property_price", "property_location", "property_publication_date"};

		int[] to = {R.id.image_item_property, R.id.text_property_item_title, R.id.text_property_item_price, R.id.text_property_item_location, R.id.text_property_item_publication_date};

		setListAdapter(new PropertyListAdapter(getContext(), propertyList));

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);

		callback = (OnPropertySelectedListener) context;
	}

	@Override
	public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		callback.onItemSelected(position);
	}
}

