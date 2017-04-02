package com.example.ahsan.emobile;

import android.graphics.Bitmap;

/**
 * Created by AHSAN on 4/2/2017.
 */

public class Child_Row {
    boolean user;
    private Bitmap icon;
    private String text;
    private String id;
    private String pic;

    public Child_Row(Bitmap icon, String text, String id, boolean user, String pic) {
        this.icon = icon;
        this.text = text;
        this.id = id;
        this.user = user;
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
