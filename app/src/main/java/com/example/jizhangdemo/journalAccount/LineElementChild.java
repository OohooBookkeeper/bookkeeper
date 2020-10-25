package com.example.jizhangdemo.journalAccount;

import android.view.View;

import java.util.List;

public class LineElementChild {
    private String title;
    private String in;
    private String out;
    private List<View> bill;

    public LineElementChild(String title,String in,String out,List<View> bill){
        this.title = title;
        this.in = in;
        this.out = out;
        this.bill = bill;
    }

    public String getTitle(){return this.title;}

    public void setTitle(String title){ this.title = title;}

    public String getIn(){return this.in;}

    public void setIn(String in){ this.in = in;}

    public String getOut(){return this.out;}

    public void setOut(String out){ this.out = out;}

    public List<View> getBill(){ return this.bill;}

    public void setBill(List<View> bill){ this.bill = bill;}
}
