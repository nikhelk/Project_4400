package com.example.brand_new_4400;

import javafx.scene.control.Hyperlink;

public class Wf7_tuple {
    private Hyperlink id;
    private String name;
    private String average_rating_string;
    private Double average_rating_double;
    Wf7_tuple(Hyperlink id, String name, Double average_rating) {
        this.id = id;
        this.name = name;
        this.average_rating_double= average_rating;
    }

    Wf7_tuple(Hyperlink id, String name, String average_rating) {
        this.id = id;
        this.name = name;
        this.average_rating_string = average_rating;
    }

    public Hyperlink getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAverage_rating_string() {
        return average_rating_string;
    }
    public Double getAverage_rating_double() {
        return average_rating_double;
    }
}