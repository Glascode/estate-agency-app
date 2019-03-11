package io.github.glascode.estateagency.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.github.glascode.estateagency.database.dao.CommentDao;
import io.github.glascode.estateagency.database.dao.PropertyDao;
import io.github.glascode.estateagency.model.Comment;
import io.github.glascode.estateagency.model.Property;
import io.github.glascode.estateagency.model.Seller;

@androidx.room.Database(entities = {Property.class, Comment.class}, version = 5, exportSchema = false)
public abstract class Database extends RoomDatabase {

	private static volatile Database INSTANCE;

	abstract PropertyDao propertyDao();
	abstract CommentDao commentDao();

	static Database getInstance(Context context) {
		if (INSTANCE == null) synchronized (Database.class) {
			if (INSTANCE == null)
				INSTANCE = Room.databaseBuilder(
						context.getApplicationContext(),
						Database.class,
						"saved.db"
				).fallbackToDestructiveMigration().build();
		}
		return INSTANCE;
	}
}
