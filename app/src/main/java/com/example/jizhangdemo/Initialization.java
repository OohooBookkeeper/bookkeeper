package com.example.jizhangdemo;

import android.app.Activity;
import android.content.Context;

import com.example.jizhangdemo.add.BookHelper;

public class Initialization extends Activity {
    static BookHelper bookHelper;
    public static void Initialization(Context context,String username){
        bookHelper = new BookHelper(context, username + ".db");
        bookHelper.addAccount("现金");
        bookHelper.addAccount("微信");
        bookHelper.addAccount("支付宝");
        bookHelper.addAccount("银行卡");
        bookHelper.addAccount("其他");

        bookHelper.addCategory(BookHelper.INCOME,"工作");
        bookHelper.addCategory(BookHelper.INCOME,"投资");
        bookHelper.addCategory(BookHelper.EXPENSE,"餐饮");
        bookHelper.addCategory(BookHelper.EXPENSE,"交通");
        bookHelper.addCategory(BookHelper.EXPENSE,"购物");
        bookHelper.addCategory(BookHelper.EXPENSE,"服务");
        bookHelper.addCategory(BookHelper.EXPENSE,"娱乐");
        bookHelper.addCategory(BookHelper.EXPENSE,"医疗");

        bookHelper.addSubcategory(1,"工资");
        bookHelper.addSubcategory(1,"奖金");
        bookHelper.addSubcategory(1,"外快");
        bookHelper.addSubcategory(2,"股票");
        bookHelper.addSubcategory(2,"利息");
        bookHelper.addSubcategory(3,"早餐");
        bookHelper.addSubcategory(3,"午餐");
        bookHelper.addSubcategory(3,"晚餐");
        bookHelper.addSubcategory(3,"水果零食");
        bookHelper.addSubcategory(3,"食材");
        bookHelper.addSubcategory(4,"公共交通");
        bookHelper.addSubcategory(4,"停车过路");
        bookHelper.addSubcategory(4,"出租车");
        bookHelper.addSubcategory(4,"开车养车");
        bookHelper.addSubcategory(5,"生活用品");
        bookHelper.addSubcategory(5,"化妆护肤");
        bookHelper.addSubcategory(5,"保健食品");
        bookHelper.addSubcategory(5,"服装衣帽");
        bookHelper.addSubcategory(5,"数码电器");
        bookHelper.addSubcategory(5,"书籍音像");
        bookHelper.addSubcategory(5,"家居家纺");
        bookHelper.addSubcategory(5,"文具玩具");
        bookHelper.addSubcategory(5,"宝宝用品");
        bookHelper.addSubcategory(6,"网络增值");
        bookHelper.addSubcategory(6,"通讯话费");
        bookHelper.addSubcategory(6,"美容美发");
        bookHelper.addSubcategory(6,"物流费用");
        bookHelper.addSubcategory(6,"学习进修");
        bookHelper.addSubcategory(6,"运动健身");
        bookHelper.addSubcategory(6,"水电煤网");
        bookHelper.addSubcategory(6,"家政服务");
        bookHelper.addSubcategory(6,"临时服务");
        bookHelper.addSubcategory(7,"聚餐派对");
        bookHelper.addSubcategory(7,"外出旅行");
        bookHelper.addSubcategory(7,"游戏充值");
        bookHelper.addSubcategory(8,"挂号诊疗");
        bookHelper.addSubcategory(8,"药品针剂");
        bookHelper.addSubcategory(8,"住院治疗");

        bookHelper.addMember("无");
        bookHelper.addMember("爸爸");
        bookHelper.addMember("妈妈");
        bookHelper.addMember("另一半");
        bookHelper.addMember("朋友");
        bookHelper.addMember("同事");

        bookHelper.addTrader("淘宝");
        bookHelper.addTrader("天猫");
        bookHelper.addTrader("京东");
        bookHelper.addTrader("超市");
        bookHelper.addTrader("便利店");
        bookHelper.addTrader("肯德基");
        bookHelper.addTrader("麦当劳");
        bookHelper.addTrader("食堂");

        bookHelper.addProject("周末放松");
        bookHelper.addProject("约会");
        bookHelper.addProject("节假日");
        bookHelper.addProject("过年");
        bookHelper.addProject("社交活动");
        bookHelper.addProject("出差");
    }
}
