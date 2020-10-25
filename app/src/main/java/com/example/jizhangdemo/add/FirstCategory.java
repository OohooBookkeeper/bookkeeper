package com.example.jizhangdemo.add;

import com.xuexiang.xui.widget.picker.wheelview.interfaces.IPickerViewItem;

import java.util.List;

public class FirstCategory implements IPickerViewItem {

    private String name;
    private List<String> category;

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public List<String> getCategory(){return category;}

    public void setCategory(List<String> category){this.category = category;}

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
