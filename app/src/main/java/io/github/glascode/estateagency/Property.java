package io.github.glascode.estateagency;

import android.support.annotation.NonNull;

import java.util.List;

class Property {

	private final List<String> caracteristiques;
	private final List<String> images;

	private String id;
	private String titre;
	private String description;
	private int nbPieces;
	private int prix;
	private String ville;
	private String codePostal;
	private Seller vendeur;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNbPieces() {
		return nbPieces;
	}

	public void setNbPieces(int nbPieces) {
		this.nbPieces = nbPieces;
	}

	public List<String> getCaracteristiques() {
		return caracteristiques;
	}

	public void addFeature(String feature) {
		caracteristiques.add(feature);
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public Seller getVendeur() {
		return vendeur;
	}

	public void setVendeur(Seller vendeur) {
		this.vendeur = vendeur;
	}

	public List<String> getImages() {
		return images;
	}

	public void addImageUrl(String imageUrl) {
		images.add(imageUrl);
	}

	public long getDate() {
		return date * 1000;
	}

	public void setDate(long date) {
		this.date = date;
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
