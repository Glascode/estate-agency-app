package io.github.glascode.estateagency.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.github.glascode.estateagency.database.dao.PropertyDao;
import io.github.glascode.estateagency.model.Property;

@androidx.room.Database(entities = {Property.class}, version = 2, exportSchema = false)
public abstract class Database extends RoomDatabase {

	private static volatile Database INSTANCE;

	public abstract PropertyDao propertyDao();

	public static Database getInstance(Context context) {
		if (INSTANCE == null) synchronized (Database.class) {
			if (INSTANCE == null)
				INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "saved.db").fallbackToDestructiveMigration().build();
		}
		return INSTANCE;
	}
}
