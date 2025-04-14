package com.faiza1.intent;

public class Safety {
    private String title, detail, id;

    public Safety(String tip) {
        this.title = tip;
    }

    public Safety() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public String getId() {
        return id;
    }
}
