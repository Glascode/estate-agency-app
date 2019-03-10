package io.github.glascode.estateagency.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import io.github.glascode.estateagency.ListStringConverter;
import io.github.glascode.estateagency.SellerConverter;

import java.util.List;

@Entity
public class Property {

	@TypeConverters(ListStringConverter.class)
	private final List<String> caracteristiques;
	@TypeConverters(ListStringConverter.class)
	private final List<String> images;
	@TypeConverters(SellerConverter.class)
	private Seller vendeur;

	@PrimaryKey
	private @NonNull String id;
	private String titre;
	private String description;
	private int nbPieces;
	private int prix;
	private String ville;
	private String codePostal;
	private long date;

	/**
	 * Constructs a {@code Property}.
	 * <p>
	 * Note: the attributes of the Property had to be written in french because
	 * of the attributes of the JSON response it depends on.
	 *
	 * @param id               the id of the Property
	 * @param titre            the title of the Property
	 * @param description      the description of the Property
	 * @param nbPieces         the number of rooms of the Property
	 * @param caracteristiques the features of the Property
	 * @param prix             the price of the Property
	 * @param ville            the location city of the Property
	 * @param codePostal       the zip code of the Property
	 * @param vendeur          the Seller of the Property
	 * @param images           the images of the Property
	 * @param date             the publication date of the Property
	 */
	public Property(String id, String titre, String description, int nbPieces,
					List<String> caracteristiques, int prix, String ville, String codePostal,
					Seller vendeur, List<String> images, long date) {
		this.id = id;
		this.titre = titre;
		this.description = description;
		this.nbPieces = nbPieces;
		this.caracteristiques = caracteristiques;
		this.prix = prix;
		this.ville = ville;
		this.codePostal = codePostal;
		this.vendeur = vendeur;
		this.images = images;
		this.date = date;
	}

	// -- GETTER --
	public String getId() {
		return id;
	}
	public String getTitre() {
		return titre;
	}
	public String getDescription() {
		return description;
	}
	public int getNbPieces() {
		return nbPieces;
	}
	public List<String> getCaracteristiques() {
		return caracteristiques;
	}
	public int getPrix() {
		return prix;
	}
	public String getVille() {
		return ville;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public Seller getVendeur() {
		return vendeur;
	}
	public List<String> getImages() {
		return images;
	}
	public long getDate() {
		return date;
	}

	// -- SETTER --
	public void setId(String id) {
		this.id = id;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public void setDescription(String description) { this.description = description; }
	public void setNbPieces(int nbPieces) {
		this.nbPieces = nbPieces;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
	public void setVendeur(Seller vendeur) {
		this.vendeur = vendeur;
	}
	public void setDate(long date) {
		this.date = date;
	}

	public void addFeature(String feature) {
		caracteristiques.add(feature);
	}

	public void addImageUrl(String imageUrl) {
		images.add(imageUrl);
	}

	@NonNull
	public String toString() {
		return "{\n\tid: " + id + ",\n" +
				"\ttitre: " + titre + ",\n" +
				"\tdescription: " + description + ",\n" +
				"\tnbPieces: " + nbPieces + "\n" +
				"\tcaracteristiques: " + caracteristiques + "\n" +
				"\tprix: " + prix + "\n" +
				"\tville: " + ville + "\n" +
				"\tcodePostal: " + codePostal + "\n" +
				"\tvendeur: " + vendeur + "\n" +
				"\tdate: " + date + "\n}\n";
	}
}
