package io.github.glascode.estateagency;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.rd.PageIndicatorView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.github.glascode.estateagency.database.GetPropertyTask;
import io.github.glascode.estateagency.model.Property;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PropertyFragment extends Fragment {

	private Property property;

	private ViewPager propertyViewPagerSlider;

	private TextView propertyTitleText;
	private TextView propertyPriceText;
	private TextView propertyLocationText;
	private TextView propertyRoomText;
	private TextView propertyFeaturesText;
	private TextView propertyDescriptionText;
	private TextView propertyPublicationDateText;
	private TextView propertySellerNameText;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Moshi moshi = new Moshi.Builder().build();

		Type type = Types.newParameterizedType(Property.class);
		JsonAdapter<Property> adapter = moshi.adapter(type);

		try {
			property = adapter.fromJson(Objects.requireNonNull(Objects.requireNonNull(getArguments()).getString("json_property")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		((MainActivity) Objects.requireNonNull(getActivity())).setProperty(property);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_property, container, false);

		BottomAppBar bottomAppBar = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_app_bar);
		bottomAppBar.replaceMenu(R.menu.menu_property_bottomappbar);

		ExtendedFloatingActionButton extendedFloatingActionButton = getActivity().findViewById(R.id.fab);
		extendedFloatingActionButton.setIcon(getResources().getDrawable(R.drawable.ic_add));
		extendedFloatingActionButton.shrink();

		extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO: Integrate property comments
				Toast.makeText(getContext(), "Adding comment", Toast.LENGTH_LONG).show();
			}
		});

		propertyViewPagerSlider = view.findViewById(R.id.viewPager_property_slider);
		propertyTitleText = view.findViewById(R.id.text_property_title);
		propertyPriceText = view.findViewById(R.id.text_property_price);
		propertyLocationText = view.findViewById(R.id.text_property_location);
		propertyRoomText = view.findViewById(R.id.text_property_room);
		propertyFeaturesText = view.findViewById(R.id.text_property_features);
		propertyDescriptionText = view.findViewById(R.id.text_property_description);
		propertyPublicationDateText = view.findViewById(R.id.text_property_publication_date);
		propertySellerNameText = view.findViewById(R.id.text_property_seller_name);

		try {
			Property result = new GetPropertyTask(getContext(), property.getId()).execute().get();

			if (result != null) {
				bottomAppBar.getMenu().getItem(2).setChecked(result.getId().equals(property.getId()));
				bottomAppBar.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_favorite_colored));
			}
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		final PageIndicatorView pageIndicatorView = view.findViewById(R.id.pageIndicatorView_property_slider);

		propertyViewPagerSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				pageIndicatorView.setSelection(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		updateUi();

		return view;
	}

	private void updateUi() {
		if (!property.getImages().isEmpty())
			propertyViewPagerSlider.setAdapter(new ViewPagerAdapter(getContext(), property.getImages()));
		else
			((ViewGroup) propertyViewPagerSlider.getParent()).removeView(propertyViewPagerSlider);

		String propertyTitle = property.getTitre();
		int propertyPrice = property.getPrix();
		String propertyLocation = property.getCodePostal() + " " + property.getVille();
		int propertyRoomNumber = property.getNbPieces();
		String propertyDescription = property.getDescription();
		String propertyPublicationDate = DateFormat.format("dd MMMM yyyy", property.getDate() * 1000).toString();
		String propertySellerName = property.getVendeur().getPrenom() + " " + property.getVendeur().getNom();

		StringBuilder propertyFeatures = new StringBuilder();

		for (int i = 0; i < property.getCaracteristiques().size(); i++) {
			if (i < property.getCaracteristiques().size() - 1)
				propertyFeatures.append(property.getCaracteristiques().get(i)).append(", ");
			else
				propertyFeatures.append(property.getCaracteristiques().get(i));
		}

		propertyTitleText.setText(getString(R.string.title_property, propertyTitle));
		propertyPriceText.setText(getString(R.string.msg_property_price, propertyPrice));
		propertyLocationText.setText(getString(R.string.msg_property_location, propertyLocation));
		propertyRoomText.setText(getString(R.string.msg_property_room, propertyRoomNumber));
		propertyFeaturesText.setText(getString(R.string.msg_property_features, propertyFeatures.toString()));
		propertyDescriptionText.setText(getString(R.string.msg_property_description, propertyDescription));
		propertyPublicationDateText.setText(getString(R.string.msg_property_publication_date, propertyPublicationDate));
		propertySellerNameText.setText(getString(R.string.msg_property_seller_name, propertySellerName));
	}

}
