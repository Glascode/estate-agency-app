package io.github.glascode.estateagency;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.github.glascode.estateagency.model.Property;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PropertyListFragment extends Fragment {

	private String jsonPropertyList;
	private List<Property> propertyList;

	private RecyclerView propertyListRecyclerView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_property_list, container, false);

		propertyListRecyclerView = view.findViewById(R.id.fragment_property_list_view);
		propertyListRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

		Moshi moshi = new Moshi.Builder().build();

		Type type = Types.newParameterizedType(List.class, Property.class);
		JsonAdapter<List<Property>> adapter = moshi.adapter(type);

		if (getArguments() != null) {
			jsonPropertyList = getArguments().getString("json_property_list");
		}

		try {
			if (jsonPropertyList != null) {
				propertyList = adapter.fromJson(jsonPropertyList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return view;
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
	}

	public void viewItem(View view) throws JSONException {
		int itemPos = propertyListRecyclerView.getChildLayoutPosition(view);
		String jsonPropertyString = new JSONArray(jsonPropertyList).get(itemPos).toString();
	}

	public void updateUI() {
		propertyListRecyclerView.setAdapter(new PropertyAdapter(propertyList));
	}
}