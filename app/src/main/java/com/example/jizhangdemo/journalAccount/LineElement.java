package com.example.jizhangdemo.journalAccount;

import android.view.View;

import java.util.List;

public class LineElement {
    private String title;
    private String sum;
    private String in;
    private String out;
    private List<List<View>> bill;

    public LineElement(String title,String sum,String in,String out,List<List<View>> bill){
        this.title = title;
        this.sum = sum;
        this.in = in;
        this.out = out;
        this.bill = bill;
    }

    public String getTitle(){ return this.title;}

    public void setTitle(String title){ this.title = title;}

    public String getSum(){ return this.sum;}

    public void setSum(String sum){ this.sum = sum;}

    public String getIn(){ return this.in;}

    public void setIn(String in){ this.in = in;}

    public String getOut(){ return this.out;}

    public void setOut(String out){ this.out = out;}

    public List<List<View>> getBill(){ return this.bill;}

    public void setBill(List<List<View>> bill){ this.bill = bill;}
}
