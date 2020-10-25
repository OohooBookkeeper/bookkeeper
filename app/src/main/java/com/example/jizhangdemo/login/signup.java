package com.example.jizhangdemo.login;
import android.app.Activity;
import android.content.Context;

import com.example.jizhangdemo.AccountHelper;

import javax.crypto.Cipher;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class signup extends Activity {
    static AccountHelper accountHelper;

    public static int signup(Context context, String username, String password, String password_rp, int problem_num, String ans)
    {
        int error_code = -1;//定义错误码
        int len=password.length();//获取密码长度
        boolean password_is_ok1= Password_rule(password);//判断密码是否符合规范
        boolean password_is_ok2= (len>=8&&len<=16);//判断密码长度是否在8-16之间
        accountHelper = new AccountHelper(context,"account.db");

        if(!password.equals(password_rp)) {  //判断两次输入密码是否一致
            error_code = 3;//两次输入密码不一致
        }
        else if(!(password_is_ok1 && password_is_ok2)) {  //判断密码是否符合规范
            error_code = 4;//密码不符合规范
        }
        else if(!Username_rule(username)) {  //判断用户名是否符合规范
            error_code = 1;//用户名不符合规范
        }
        else if(accountHelper.query(username).getCount()!=0) {  //判断用户输入的用户名是否已经存在
            error_code = 2;//用户名已经存在
        }
//        else if(!ans.equals("")){  //判断用户是否输入密保问题答案
//            error_code = 5;//用户没有输入密保问题答案
//        }
        else {  //登录成功
            error_code = 0;
            int patternEnabled = 0;//设置图形密码未启用
            String pattern = "";//图形密码置空
            String Key = "0123456789ABCDEF";//设置固定密钥
            byte[] encryptResult_password = encrypt(password, Key);
            String encryptResultStr_password = parseByte2HexStr(encryptResult_password);//将密码明文转为密文
            int defaultlogin = AccountHelper.PASSWD;
            accountHelper.signup(username, encryptResultStr_password, problem_num, ans, patternEnabled , pattern, defaultlogin);//将注册信息存入数据库
        }
        return error_code;
    }

    public static boolean Password_rule(String password){
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for(int i=0 ; i<password.length() ; i++){ //循环遍历字符串
            if(Character.isDigit(password.charAt(i))){     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if(Character.isLetter(password.charAt(i))){   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        if(password.contains(" ")){  //用char包装类中的方法来判断密码中是否含有空格
            return false;
        }
        if(isDigit && isLetter) return true;
        return false;
    }

    public static boolean Username_rule(String username){
        boolean first_is_letter = false;//定义一个boolean值，判断用户名首个字符是否为字母
        boolean length_less_sixteen = false;//定义一个boolean值，判断用户名长度是否小于16
        int len = username.length();//获取用户名长度
        if(Character.isLetter(username.charAt(0))){  //用char包装类中的方法来判断用户名首字符是否为字母
            first_is_letter = true;
        }
        if(username.length() <= 15){  //判断用户名长度是否小于16
            length_less_sixteen = true;
        }
        for(int i=0 ; i<username.length() ; i++){
            if(!(Character.isDigit(username.charAt(i)) || Character.isLetter(username.charAt(i)))){  //用char包装类中的方法来判断用户名中是否只含有字母和数字
                return false;
            }
        }
        if(first_is_letter && length_less_sixteen  ) {
            return true;
        }
        else{
            return false;
        }
    }

    public static byte[] encrypt(String content, String key) {  //AES-128加密算法
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));
            return result;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }  //AES-128加密算法结束
}
