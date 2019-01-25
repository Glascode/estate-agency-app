package io.github.glascode.estateagency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchPropertyActivity(View v) {
    	Intent intent = new Intent(this, PropertyActivity.class);
    	intent.putExtra("propertyTitle", "Maison 250m2 proche Caen");
    	intent.putExtra("propertyPrice", 350000);
    	intent.putExtra("propertyLocation", "Caen");
    	intent.putExtra("propertyDescription", "Maison traditionnelle");
    	intent.putExtra("propertyPublicationDate", "12 january 2019");
    	intent.putExtra("propertySellerName", "CarlImmo");
    	intent.putExtra("propertySellerMail", "carl@immo.com");
    	intent.putExtra("propertySellerNumber", "06.56.87.51.89");

    	startActivity(intent);
    }
}