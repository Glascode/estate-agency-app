package io.github.glascode.estateagency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.*;

import java.io.IOException;

public class PropertyActivity extends AppCompatActivity {

	private Property property;

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
		setContentView(R.layout.activity_property);

		propertyTitleText = findViewById(R.id.text_property_title);
		propertyPriceText = findViewById(R.id.text_property_price);
		propertyLocationText = findViewById(R.id.text_property_location);
		propertyDescriptionText = findViewById(R.id.text_property_description);
		propertyPublicationDateText = findViewById(R.id.text_property_publication_date);
		propertySellerNameText = findViewById(R.id.text_property_seller_name);
		propertySellerMailText = findViewById(R.id.text_property_seller_mail);
		propertySellerNumberText = findViewById(R.id.text_property_seller_number);

		makeHttpRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/immobilier.json");
		updateUI();
	}

	private void updateUI() {
		String propertyTitle = property.getTitre();
		int propertyPrice = property.getPrix();
		String propertyLocation = property.getVille();
		String propertyDescription = property.getDescription();
		String propertyPublicationDate = DateFormat.format("dd MMMM yyyy", property.getDate()).toString();
		String propertySellerName = property.getVendeur().getNom();
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

	private void updateUI(String responseBody) {
		property = makePropertyFromJson(responseBody);

		String propertyTitle = property.getTitre();
		int propertyPrice = property.getPrix();
		String propertyLocation = property.getVille();
		String propertyDescription = property.getDescription();
		String propertyPublicationDate = DateFormat.format("dd MMMM yyyy", property.getDate()).toString();
		String propertySellerName = property.getVendeur().getNom();
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

	private void makeHttpRequest(String url) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(url).build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try (ResponseBody responseBody = response.body()) {
					if (!response.isSuccessful()) {
						throw new IOException("Unexpected HTTP code " + response);
					}

					Headers responseHeaders = response.headers();
					for (int i = 0, size = responseHeaders.size(); i < size; i++) {
						Log.i("JML", responseHeaders.name(i) + ": "
								+ responseHeaders.value(i));
					}

					property = makePropertyFromJson(responseBody.string());

					final String body = responseBody.string();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							updateUI(body);
						}
					});
				}
			}
		});
	}

	private Property makePropertyFromJson(String jsonResponse) {
		Moshi moshi = new Moshi.Builder().build();  // create Moshi

		// Create the adapter for Property
		Property property = null;
		PropertyResponse propertyResponse;
		JsonAdapter<PropertyResponse> jsonAdapter = moshi.adapter(PropertyResponse.class);

		try {
			Log.d("Execution", "Adapting the property from JSON");
			propertyResponse = jsonAdapter.fromJson(jsonResponse);
			property = propertyResponse.getResponse();
			Log.d("Execution", "Adapted!");
			Log.d("Execution", "Property:\n" + property);
		} catch (IOException e) {
			Log.i("JML", "Erreur I/O");
		}

		return property;
	}

}
