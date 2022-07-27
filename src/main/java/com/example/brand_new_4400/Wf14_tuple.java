package com.example.brand_new_4400;

import javafx.scene.control.Hyperlink;

public class Wf14_tuple {
    private Hyperlink id_wf14;
    private String name_wf14;

    public Double getPercent_owned_wf14() {
        return percent_owned_wf14;
    }

    private String author_wf14;
    private String cuisine_wf14;
    private String dietTags_wf14;
    private Double average_rating_wf14;
    private Double percent_owned_wf14;

    public Hyperlink getId_wf14() {
        return id_wf14;
    }

    public String getName_wf14() {
        return name_wf14;
    }

    public String getAuthor_wf14() {
        return author_wf14;
    }

    public String getCuisine_wf14() {
        return cuisine_wf14;
    }

    public String getDietTags_wf14() {
        return dietTags_wf14;
    }

    public Double getAverage_rating_wf14() {
        return average_rating_wf14;
    }

    public Wf14_tuple(Hyperlink id, String name, String author, String cuisine, String dietTags, Double average_rating, Double percent_owned) {
        this.id_wf14 = id;
        this.name_wf14 = name;
        this.author_wf14 = author;
        this.cuisine_wf14 = cuisine;
        this.dietTags_wf14 = dietTags;
        this.average_rating_wf14 = average_rating;
        this.percent_owned_wf14 = percent_owned;
    }
}