package io.github.glascode.estateagency.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys
		= @ForeignKey(
		entity = Property.class,
		parentColumns = "id",
		childColumns = "property_id"))
public class Comment {

	@PrimaryKey
	private String id;
	@ColumnInfo(name = "property_id")
	private String propertyId;
	private String content;
	private long creationDate;

	public Comment(String id, String propertyId, String content, long creationDate) {
		this.id = id;
		this.propertyId = propertyId;
		this.content = content;
		this.creationDate = creationDate;
	}

	public String getId() {
		return id;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public String getContent() {
		return content;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
}
