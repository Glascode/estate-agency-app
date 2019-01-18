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

}
