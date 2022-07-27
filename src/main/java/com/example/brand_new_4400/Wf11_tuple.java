package com.example.brand_new_4400;

import javafx.scene.control.Hyperlink;

import java.util.DuplicateFormatFlagsException;

public class Wf11_tuple {
    private Hyperlink id_wf11;
    private String name_wf11;
    private String author_wf11;
    private String cuisine_wf11;
    private String dietTags_wf11;
    private Double average_rating_wf11;

    public Hyperlink getId_wf11() {
        return id_wf11;
    }

    public String getName_wf11() {
        return name_wf11;
    }

    public String getAuthor_wf11() {
        return author_wf11;
    }

    public String getCuisine_wf11() {
        return cuisine_wf11;
    }

    public String getDietTags_wf11() {
        return dietTags_wf11;
    }

    public Double getAverage_rating_wf11() {
        return average_rating_wf11;
    }

    public Wf11_tuple(Hyperlink id, String name, String author, String cuisine, String dietTags, Double average_rating) {
        this.id_wf11 = id;
        this.name_wf11 = name;
        this.author_wf11 = author;
        this.cuisine_wf11 = cuisine;
        this.dietTags_wf11 = dietTags;
        this.average_rating_wf11 = average_rating;
    }
}