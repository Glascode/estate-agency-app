package io.github.glascode.estateagency;

import java.util.Date;
import java.util.List;

public class Property {

	private int id;
	private String title;
	private String description;
	private int rooms;
	private List<String> features;
	private int price;
	private String city;
	private int zipCode;
	private Seller seller;
	private List<String> imagesUrls;
	private Date date;

	public Property(int id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void addFeature(String feature) {
		features.add(feature);
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public List<String> getImagesUrls() {
		return imagesUrls;
	}

	public void addImageUrl(String imageUrl) {
		imagesUrls.add(imageUrl);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
