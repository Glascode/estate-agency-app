package io.github.glascode.estateagency;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.github.glascode.estateagency.model.Property;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class PropertyListFragment extends ListFragment {
	private OnPropertySelectedListener callback;

	private List<Property> propertyList;

	public interface OnPropertySelectedListener {
		void onItemSelected(int position);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Moshi moshi = new Moshi.Builder().build();

		Type type = Types.newParameterizedType(List.class, Property.class);
		JsonAdapter<List<Property>> adapter = moshi.adapter(type);

		try {
			propertyList = adapter.fromJson(Objects.requireNonNull(Objects.requireNonNull(getArguments()).getString("json_property_list")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		setListAdapter(new PropertyListAdapter(getContext(), propertyList));

		BottomAppBar bottomAppBar = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_app_bar);
		bottomAppBar.replaceMenu(R.menu.menu_main_bottomappbar);

		ExtendedFloatingActionButton extendedFloatingActionButton = getActivity().findViewById(R.id.fab);
		extendedFloatingActionButton.setIcon(getResources().getDrawable(R.drawable.ic_add));
		extendedFloatingActionButton.setText(R.string.action_add_property);
		extendedFloatingActionButton.extend();

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

