package io.github.glascode.estateagency;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.rd.PageIndicatorView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import io.github.glascode.estateagency.database.ActionPropertyTask;
import io.github.glascode.estateagency.model.Property;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PropertyActivity extends AppCompatActivity {

	private Property property;

	private ViewPager propertyViewPagerSlider;

	private TextView propertyTitleText;
	private TextView propertyPriceText;
	private TextView propertyLocationText;
	private TextView propertyDescriptionText;
	private TextView propertyPublicationDateText;
	private TextView propertySellerNameText;
	private TextView propertySellerMailText;
	private TextView propertySellerNumberText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Objects.requireNonNull(getSupportActionBar()).hide();

		setContentView(R.layout.activity_property);

		propertyViewPagerSlider = findViewById(R.id.viewPager_property_slider);
		propertyTitleText = findViewById(R.id.text_property_title);
		propertyPriceText = findViewById(R.id.text_property_price);
		propertyLocationText = findViewById(R.id.text_property_location);
		propertyDescriptionText = findViewById(R.id.text_property_description);
		propertyPublicationDateText = findViewById(R.id.text_property_publication_date);
		propertySellerNameText = findViewById(R.id.text_property_seller_name);
		propertySellerMailText = findViewById(R.id.text_property_seller_mail);
		propertySellerNumberText = findViewById(R.id.text_property_seller_number);

		ToggleButton propertySaveButton = findViewById(R.id.btn_save_property);

		Moshi moshi = new Moshi.Builder().build();
		JsonAdapter<Property> adapter = moshi.adapter(Property.class);

		String jsonPropertyString = getIntent().getStringExtra("json_property");

		final PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView_property_slider);

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

		try {
			property = adapter.fromJson(jsonPropertyString);

			String result = new ActionPropertyTask(getApplicationContext(), property, "get").execute().get();
			if (result != null && result.equals(property.getId()))
				propertySaveButton.setChecked(true);
		} catch (IOException | ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		propertySaveButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					new ActionPropertyTask(getApplicationContext(), property, "insert").execute();
				else
					new ActionPropertyTask(getApplicationContext(), property, "remove").execute();
			}
		});

		updateUI();
	}

	private void updateUI() {
		propertyViewPagerSlider.setAdapter(new ViewPagerAdapter(this, property.getImages()));

		String propertyTitle = property.getTitre();
		int propertyPrice = property.getPrix();
		String propertyLocation = property.getVille();
		String propertyDescription = property.getDescription();
		String propertyPublicationDate = DateFormat.format("dd MMMM yyyy", property.getDate() * 1000).toString();
		String propertySellerName = property.getVendeur().getPrenom() + " " + property.getVendeur().getNom();
		String propertySellerMail = property.getVendeur().getEmail();
		String propertySellerNumber = property.getVendeur().getTelephone();

		propertyTitleText.setText(getString(R.string.title_property, propertyTitle));
		propertyPriceText.setText(getString(R.string.msg_property_price, propertyPrice));
		propertyLocationText.setText(getString(R.string.msg_property_location, propertyLocation));
		propertyDescriptionText.setText(getString(R.string.msg_property_description, propertyDescription));
		propertyPublicationDateText.setText(getString(R.string.msg_property_publication_date, propertyPublicationDate));
		propertySellerNameText.setText(getString(R.string.msg_property_seller_name, propertySellerName));
		propertySellerMailText.setText(getString(R.string.msg_property_seller_mail, propertySellerMail));
		propertySellerNumberText.setText(getString(R.string.msg_property_seller_number, propertySellerNumber));
	}

}
