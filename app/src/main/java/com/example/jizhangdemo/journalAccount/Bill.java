package com.example.jizhangdemo.journalAccount;

import java.math.BigDecimal;

public class Bill {
    private int type;
    private String day;
    private String dayOfWeek;
    private String secondCategory;
    private String time;
    private String account;
    private String remark;
    private BigDecimal money;

    public Bill(int type,String day,String dayOfWeek,String secondCategory,String time,String account,String remark,BigDecimal money){
        this.type = type;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.secondCategory = secondCategory;
        this.time = time;
        this.account = account;
        this.remark = remark;
        this.money = money;
    }

    public int getType(){ return this.type;}

    public void setType(int type){ this.type = type;}

    public String getDay(){ return this.day;};

    public void setDay(String day){ this.day = day;}

    public String getDayOfWeek(){ return this.dayOfWeek;};

    public void setDayOfWeek(String dayOfWeek){ this.dayOfWeek = dayOfWeek;}

    public String getSecondCategory(){ return this.secondCategory;};

    public void setSecondCategory(String secondCategory){ this.secondCategory = secondCategory;}

    public String getTime(){ return this.time;};

    public void setTime(String time){ this.time = time;}

    public String getAccount(){ return this.account;};

    public void setAccount(String account){ this.account = account;}

    public String getRemark(){ return this.remark;};

    public void setRemark(String remark){ this.remark = remark;}

    public BigDecimal getMoney(){ return this.money;};

    public void setMoney(BigDecimal money){ this.money = money;}
}
