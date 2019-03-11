package io.github.glascode.estateagency;

import androidx.room.TypeConverter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.github.glascode.estateagency.model.Seller;

import java.io.IOException;

public class SellerConverter {

	private static final Moshi moshi = new Moshi.Builder().build();
	private static final JsonAdapter<Seller> adapter = moshi.adapter(Types.newParameterizedType(Seller.class));

	@TypeConverter
	public static Seller stringToSeller(String data) {
		if (data == null)
			return null;

		try {
			return adapter.fromJson(data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@TypeConverter
	public static String sellerToString(Seller data) {
		if (data == null)
			return null;

		return adapter.toJson(data);
	}
}
