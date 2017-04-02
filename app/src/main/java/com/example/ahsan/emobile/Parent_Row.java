package com.example.ahsan.emobile;

import java.util.ArrayList;

/**
 * Created by AHSAN on 4/2/2017.
 */

public class Parent_Row {
    private String name;
    private ArrayList<Child_Row> childs;

    public Parent_Row(String name, ArrayList<Child_Row> childs) {
        this.name = name;
        this.childs = childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Child_Row> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<Child_Row> childs) {
        this.childs = childs;
    }
}
