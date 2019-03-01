package io.github.glascode.estateagency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class PropertyActivity extends AppCompatActivity {

	private String propertyTitle;
	private int propertyPrice;
	private String propertyLocation;
	private String propertyDescription;
	private String propertyPublicationDate;
	private String propertySellerName;
	private String propertySellerMail;
	private String propertySellerNumber;

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

		generateVariables();
	}

	private void generateVariables() {
		propertyTitle = getIntent().getExtras().getString("propertyTitle");
		propertyPrice = getIntent().getExtras().getInt("propertyPrice");
		propertyLocation = getIntent().getExtras().getString("propertyLocation");
		propertyDescription = getIntent().getExtras().getString("propertyDescription");
		propertyPublicationDate = getIntent().getExtras().getString("propertyPublicationDate");
		propertySellerName = getIntent().getExtras().getString("propertySellerName");
		propertySellerMail = getIntent().getExtras().getString("propertySellerMail");
		propertySellerNumber = getIntent().getExtras().getString("propertySellerNumber");

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
