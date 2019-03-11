package io.github.glascode.estateagency;

import androidx.room.TypeConverter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ListStringConverter {

	private static final Moshi moshi = new Moshi.Builder().build();
	private static final JsonAdapter<List<String>> adapter = moshi.adapter(Types.newParameterizedType(List.class, String.class));

	@TypeConverter
	public static List<String> stringToList(String data) {
		if (data == null)
			return Collections.emptyList();

		try {
			return adapter.fromJson(data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	@TypeConverter
	public static String listToString(List<String> data) {
		if (data == null)
			return null;

		return adapter.toJson(data);
	}
}
