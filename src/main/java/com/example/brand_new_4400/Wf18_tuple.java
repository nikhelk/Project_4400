package com.example.brand_new_4400;

import javafx.scene.control.Hyperlink;

public class Wf18_tuple {
    private Hyperlink run;
    private String date;

    public void setRun(Hyperlink run) {
        this.run = run;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Hyperlink getRun() {
        return run;
    }

    public String getDate() {
        return date;
    }

    public Wf18_tuple(Hyperlink run, String date) {
        this.run = run;
        this.date = date;
    }
}